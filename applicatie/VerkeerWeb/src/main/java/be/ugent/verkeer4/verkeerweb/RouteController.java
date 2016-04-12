package be.ugent.verkeer4.verkeerweb;

import java.util.List;

import be.ugent.verkeer4.verkeerweb.viewmodels.RouteSummaryEntryVM;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import be.ugent.verkeer4.verkeerdomain.*;
import be.ugent.verkeer4.verkeerdomain.data.*;
import be.ugent.verkeer4.verkeerdomain.data.composite.POIWithDistanceToRoute;
import be.ugent.verkeer4.verkeerdomain.data.composite.GroupedRouteTrafficJamCause;
import be.ugent.verkeer4.verkeerweb.dataobjects.*;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteDetailsVM;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteEditVM;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteOverviewVM;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
public class RouteController {

    /**
     * Toont het overzicht van alle routes
     * @return
     * @throws ClassNotFoundException 
     */
    @RequestMapping(value = "route/list", method = RequestMethod.GET)
    public ModelAndView getList() throws ClassNotFoundException {

        IRouteService routeService = new RouteService(); // eventueel dependency injection
        // route overview model opbouwen
        RouteOverviewVM overview = getRouteOverviewModel(routeService);

        // geef mee als model aan view
        ModelAndView model = new ModelAndView("route/list");
        model.addObject("overview", overview);

        return model;
    }

    /**
     * Bouwt een route overview viewmodel op om te gebruiken bij de route list
     * @param routeService
     * @return
     * @throws ClassNotFoundException 
     */
    private RouteOverviewVM getRouteOverviewModel(IRouteService routeService) throws ClassNotFoundException {
        // haal routes op
        List<Route> lst = routeService.getRoutes();
        // haal meest recentste gegevens op voor alle routes
        List<RouteData> mostRecentRouteSummaries = routeService.getMostRecentRouteSummaries();
        RouteOverviewVM overview = new RouteOverviewVM();
        // hou per route id een RouteSummaryEntry bij
        Map<Integer, RouteSummaryEntryVM> entries = new HashMap<>();
        // overloop alle routes en maak een nieuw routeSummaryEntry en steek het in de entries map
        for (Route r : lst) {
            RouteSummaryEntryVM entry = new RouteSummaryEntryVM();
            entry.setRoute(r);

            overview.getSummaries().add(entry);
            entries.put(r.getId(), entry);
        }
        Date maxDate = mostRecentRouteSummaries.stream().map(rs -> rs.getTimestamp()).max(Date::compareTo).get();
        overview.setRecentRouteDateFrom(maxDate);

        // overloop alle recente route data en steek vul de routesummaryentry object aan
        for (RouteData sum : mostRecentRouteSummaries) {

            Map<ProviderEnum, RouteData> summaryPerProvider = entries.get(sum.getRouteId()).getRecentSummaries();
            summaryPerProvider.put(sum.getProvider(), sum);
        }
        // nu dat alle summary per provider per route entry toegevoegd zijn overlopen
        // we nogmaals alle routes om de gemiddelden te berekenen
        for (Route r : lst) {
            RouteSummaryEntryVM entry = entries.get(r.getId());
            Map<ProviderEnum, RouteData> summaryPerProvider = entry.getRecentSummaries();

            // bereken gemiddelde van delay
            int totalDelay = summaryPerProvider.values().stream().mapToInt(RouteData::getDelay).sum();
            double avgDelay = totalDelay / (float) summaryPerProvider.size();
            entry.setDelay(avgDelay);

            // bereken traffic delay percentage ( travelTime / baseTime) - 1
            double delayPercentage = getTrafficDelayPercentage(r, summaryPerProvider.values().stream().toArray(RouteData[]::new));
            entry.setTrafficDelayPercentage(delayPercentage);

            // bereken gemiddelde travel time (met traffic)
            int totalTravelTime = summaryPerProvider.values().stream().mapToInt(RouteData::getTravelTime).sum();
            double avgCurrentTravelTime = totalTravelTime / (float) summaryPerProvider.size();
            entry.setAverageCurrentTravelTime(avgCurrentTravelTime);
        }
        return overview;
    }

    /**
     * Geeft de details van een route in json terug
     * @param id
     * @param startDate
     * @param endDate
     * @return
     * @throws ClassNotFoundException 
     */
    @ResponseBody
    @RequestMapping(value = "route/routedata", method = RequestMethod.GET)
    public RouteDetailData ajaxGetRouteData(@RequestParam("id") int id, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate)
            throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService(routeService);
        IProviderService providerService = new ProviderService(routeService, poiService);
        
        RouteDetailData data = new RouteDetailData();
        
        // vraag de vertraging van alle providers tussen de start & end date op
        List<RouteData> routeData = providerService.getRouteDataForRoute(id, startDate, endDate, "Timestamp");
        data.setValues(routeData);
        
        // vraag alle files op voor de route & de oorzaken
        List<RouteTrafficJam> jams = routeService.getRouteTrafficJamsForRouteBetween(id, startDate, endDate);
        List<GroupedRouteTrafficJamCause> causes = routeService.getRouteTrafficJamCausesForRouteBetween(id, startDate, endDate);
        // groepeer de oorzaken per file
        Map<Integer, List<GroupedRouteTrafficJamCause>> causesByTrafficJamId = causes.stream().collect(Collectors.groupingBy(c -> c.getRouteTrafficJamId()));
        
        // bouw per file de oorzaken op in de data
        List<RouteDetailTrafficJam> detailJams = new ArrayList<>();
        for (RouteTrafficJam j : jams) {
            RouteDetailTrafficJam detailJam = new RouteDetailTrafficJam(j);
            
            List<GroupedRouteTrafficJamCause> lst = causesByTrafficJamId.get(j.getId());
            if(lst != null)
                detailJam.setCauses(lst);
            
                        
            detailJams.add(detailJam);
        }
        data.setJams(detailJams);
        
        return data;
    }

    /**
     * Geeft de detailpagina van een route
     * @param id
     * @return
     * @throws ClassNotFoundException 
     */
    @RequestMapping(value = "route/detail/{id}", method = RequestMethod.GET)
    public ModelAndView getDetail(@PathVariable("id") int id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        Route route = routeService.getRoute(id);

        // vraag de laatste provider gegevens op uit de database
        List<RouteData> summaries = routeService.getMostRecentRouteSummariesForRoute(id);
        // bouw view model op
        RouteDetailsVM detail = new RouteDetailsVM(route, summaries);
        ModelAndView model = new ModelAndView("route/detail");
        model.addObject("detail", detail);
        return model;
    }

    /**
     * Toont de kaartweergave
     * @return
     * @throws ClassNotFoundException 
     */
    @RequestMapping(value = "route/map", method = RequestMethod.GET)
    public ModelAndView getMap() throws ClassNotFoundException {
        IRouteService routeService = new RouteService();

        // geef het overzicht van alle routes
        RouteOverviewVM overview = getRouteOverviewModel(routeService);
        // en voeg het toe als model
        ModelAndView model = new ModelAndView("route/map");
        model.addObject("overview", overview);

        return model;
    }

    /**
     * Geeft de map gegevens als json terug
     * @param req
     * @return
     * @throws ClassNotFoundException 
     */
    @ResponseBody
    @RequestMapping(value = "route/mapdata", method = RequestMethod.GET)
    public MapData ajaxGetMapRoutes(HttpServletRequest req) throws ClassNotFoundException {
        // als id leeg is geef alle routes gegevens terug
        if (req.getParameter("id") == null || req.getParameter("id").equalsIgnoreCase("")) {
            return getAllRouteMapData();
        } else {
            // anders geef maar de map gegevens voor 1 route terug
            int id = Integer.parseInt(req.getParameter("id"));
            return getRouteMapData(id);
        }
    }

    /**
     * Toont de edit pagine van een route
     * @param id
     * @return
     * @throws ClassNotFoundException 
     */
    @RequestMapping(value = "route/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Integer id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        Route r = routeService.getRoute(id);

        // maak route edit view model
        RouteEditVM re = new RouteEditVM();
        re.setId(r.getId());
        re.setName(r.getName());
        re.setFromAddress(r.getFromAddress());
        re.setToAddress(r.getToAddress());
        re.setFromLatLng(r.getFromLatitude() + "," + r.getFromLongitude());
        re.setToLatLng(r.getToLatitude() + "," + r.getToLongitude());
        re.setAvoidHighwaysOrUseShortest(r.getAvoidHighwaysOrUseShortest());
        ModelAndView model = new ModelAndView("route/edit");
        model.addObject("routeEdit", re);

        return model;
    }

    /**
     * Valideert en slaat de gewijzigde route gegevens op
     * @param route
     * @param result
     * @return
     * @throws ClassNotFoundException 
     */
    @RequestMapping(value = "route/edit/{id}", method = RequestMethod.POST)
    public ModelAndView edit(@Valid
            @ModelAttribute("routeEdit") RouteEditVM route, BindingResult result) throws ClassNotFoundException {

        // validation, moet manueel aangezien geen hibernate-validators toegevoegd is
        if (route.getName() == null || route.getName().isEmpty()) {
            result.rejectValue("name", "error.name", "Naam is verplicht");
        }

        // parse de start lat & lng
        double fromLat = 0;
        double fromLng = 0;
        try {
            fromLat = Double.parseDouble(route.getFromLatLng().split(",")[0]);
            fromLng = Double.parseDouble(route.getFromLatLng().split(",")[1]);
        } catch (Exception ex) {
            result.rejectValue("fromLatLng", "error.fromLatLng", "Ongeldige van positie");
        }

        // parse de end lat & lng
        double toLat = 0;
        double toLng = 0;
        try {
            toLat = Double.parseDouble(route.getToLatLng().split(",")[0]);
            toLng = Double.parseDouble(route.getToLatLng().split(",")[1]);
        } catch (Exception ex) {
            result.rejectValue("toLatLng", "error.toLatLng", "Ongeldige naar positie");
        }

        // als er validation erros zijn toon de errors bij de input velden
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("route/edit", result.getModel());
            model.addObject("errors", result);
            return model;
        } else {
            // vraag de bestaande route op
            IRouteService routeService = new RouteService();
            Route r = routeService.getRoute(route.getId());

            // pas de gegevens aan
            r.setName(route.getName());

            // als de waypoints of kortste/snelste route is geupdate
            // dan moet de waypoints geupdate worden
            boolean updateWaypoints = r.getFromLatitude() != fromLat
                    || r.getFromLongitude() != fromLng
                    || r.getToLatitude() != toLat
                    || r.getToLongitude() != toLng
                    || r.getAvoidHighwaysOrUseShortest() != route.getAvoidHighwaysOrUseShortest();

            r.setFromAddress(route.getFromAddress());
            r.setToAddress(route.getToAddress());
            r.setFromLatitude(fromLat);
            r.setFromLongitude(fromLng);

            r.setToLatitude(toLat);
            r.setToLongitude(toLng);
            r.setAvoidHighwaysOrUseShortest(route.getAvoidHighwaysOrUseShortest());

            // update het route object
            routeService.updateRoute(r, updateWaypoints);

            return new ModelAndView(
                    "redirect:/route/detail/" + r.getId());
        }
    }

    
    /**
     * Geeft de route kaart gegevens terug van een bepaald id
     * @param id
     * @return
     * @throws ClassNotFoundException 
     */
    private MapData getRouteMapData(int id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        Route r = routeService.getRoute(id);
        IPOIService poiService = new POIService(routeService);

        // vraag alle waypoints van de route
        List<RouteWaypoint> waypoints = routeService.getRouteWaypointsForRoute(id);
        // vraag de laatste provider gegevens van de route
        List<RouteData> summaries = routeService.getMostRecentRouteSummariesForRoute(id);

        MapData data = new MapData();
        // verzamel de map route gegevens op basis van de route en de laatste provider gegevens
        MapRoute mr = getMapRoute(r, summaries);

        // vraag alle POI gegevens op van de laatste 10min
        Date from = Date.from(java.time.LocalDateTime.now().minusMinutes(5).toInstant(ZoneOffset.UTC));
        Date to = Date.from(java.time.LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.UTC));
        List<POIWithDistanceToRoute> pois = poiService.getPOIsNearRoute(r.getId(), from, to);
        // bouw een map poi object van het poi object op
        for (POI poi : pois) {
            MapPOI mp = getMapPOIFromPOI(poi);
            data.getPois().add(mp);
        }

        data.getRoutes().add(mr);

        // maak van alle waypoints map waypoints objecten
        for (RouteWaypoint waypoint : waypoints) {
            MapWaypoint wp = new MapWaypoint(waypoint.getLatitude(), waypoint.getLongitude());
            mr.getWaypoints().add(wp);
        }

        return data;
    }

    /**
     * Berekent de percentage van de vertraging van de route aan de hand van
     * de laatste provider gegevens
     * @param r
     * @param summaries
     * @return 
     */
    private double getTrafficDelayPercentage(Route r, RouteData[] summaries) {
        if (summaries.length <= 0) {
            return 0;
        }

        // tel alle base travel times op
        double baseTravelTime = Arrays.stream(summaries).mapToDouble(rd -> rd.getBaseTime()).sum();
        // bereken gemiddelde travel time
        double avgBaseTravelTime = baseTravelTime / (float) summaries.length;

        // tel alle travel times (die delay bevatten)
        int totalTravelTime = Arrays.stream(summaries).mapToInt(rd -> rd.getTravelTime()).sum();
        double avgTravelTime = totalTravelTime / (float) summaries.length;

        // bereken percentage
        double percentage = (avgTravelTime / avgBaseTravelTime) - 1;
        return percentage;
    }

    /**
     * Verzamelt alle map gegevens voor alle routes
     * @return
     * @throws ClassNotFoundException 
     */
    private MapData getAllRouteMapData() throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService(routeService);
        // vraag alle trajecten met hun waypoints op
        List<Route> routes = routeService.getRoutes();
        List<RouteWaypoint> waypoints = routeService.getRouteWaypoints();

        MapData data = new MapData();
        Map<Integer, MapRoute> mapRoutesPerId = new HashMap<>();
        Map<Integer, List<RouteData>> summariesPerRouteId = new HashMap<>();

        // vraag alle meest recente route data op 
        // en voeg ze toe in de map
        for (RouteData summary : routeService.getMostRecentRouteSummaries()) {
            List<RouteData> lst;
            if (!summariesPerRouteId.containsKey(summary.getRouteId())) {
                summariesPerRouteId.put(summary.getRouteId(), lst = new ArrayList<>());
            } else {
                lst = summariesPerRouteId.get(summary.getRouteId());
            }

            lst.add(summary);
        }

        // vraag alle actieve pois op en map ze op map poi objectne
        List<POI> pois = poiService.getActivePOIs();
        for (POI poi : pois) {
            MapPOI mp = getMapPOIFromPOI(poi);
            data.getPois().add(mp);
        }

        // overloop alle trajecten en bereken aan de hand van de verzamelde route data's in
        // de map de gemiddelde delay & percentage
        for (Route r : routes) {
            List<RouteData> routeSummaries;
            if (!summariesPerRouteId.containsKey(r.getId())) {
                routeSummaries = new ArrayList<>();
            } else {
                routeSummaries = summariesPerRouteId.get(r.getId());
            }

            // map de route en laatste gegevens op een map route object
            MapRoute mr = getMapRoute(r, routeSummaries);

            // en voeg het object toe
            mapRoutesPerId.put(r.getId(), mr);
            data.getRoutes().add(mr);
        }

        // voeg waypoints toe aan elk traject
        for (RouteWaypoint waypoint : waypoints) {
            MapWaypoint wp = new MapWaypoint(waypoint.getLatitude(), waypoint.getLongitude());
            mapRoutesPerId.get(waypoint.getRouteId()).getWaypoints().add(wp);
        }

        return data;
    }

    /**
     * Mapped een POI object op een MapPOI
     * @param poi
     * @return 
     */
    private MapPOI getMapPOIFromPOI(POI poi) {
        MapPOI mp = new MapPOI();
        mp.setId(poi.getId());
        mp.setLatitude(poi.getLatitude());
        mp.setLongitude(poi.getLongitude());
        mp.setInfo(poi.getInfo());
        mp.setCategory(poi.getCategory().getValue());
        mp.setSince(poi.getSince().toString());
        mp.setSource(poi.getProvider().toString());
        return mp;
    }

    /**
     * Bouwt een map route object op adhv van een route en de recenste
     * provider gegevens
     * @param r
     * @param routeSummaries
     * @return 
     */
    private MapRoute getMapRoute(Route r, List<RouteData> routeSummaries) {
        MapRoute mr = new MapRoute();
        mr.setName(r.getName());
        mr.setDistance(r.getDistance());
        mr.setId(r.getId());
        double traficDelayPercentage = getTrafficDelayPercentage(r, routeSummaries.stream().toArray(RouteData[]::new));
        mr.setTrafficDelayPercentage(traficDelayPercentage);
        int totalDelay = routeSummaries.stream().mapToInt(RouteData::getDelay).sum();
        double avgDelay = totalDelay / (float) routeSummaries.size();
        mr.setCurrentDelay(avgDelay);
        int totalTravelTime = routeSummaries.stream().mapToInt(rd -> rd.getTravelTime()).sum();
        double avgTravelTime = totalTravelTime / (float) routeSummaries.size();
        mr.setAverageCurrentTravelTime(avgTravelTime);
        return mr;
    }

    @RequestMapping(value = "/route/compare", method = RequestMethod.GET)
    public ModelAndView compare() throws ClassNotFoundException {
        // get provider names sorted
        ProviderEnum[] providersEnum = ProviderEnum.values();
        String[] providers = new String[providersEnum.length];
        for (int i = 0; i < providers.length; i++) {
            providers[i] = providersEnum[i].name();
        }
        Arrays.sort(providers);

        // get the route names and id
        RouteService routeService = new RouteService();
        List<Route> routes = routeService.getRoutesInfo();

        // send view
        ModelAndView model = new ModelAndView("route/compare");
        model.addObject("providers", providers);
        model.addObject("routes", routes);
        return model;
    }
}