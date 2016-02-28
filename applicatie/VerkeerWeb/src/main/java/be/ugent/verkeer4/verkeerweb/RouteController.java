package be.ugent.verkeer4.verkeerweb;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import be.ugent.verkeer4.verkeerdomain.*;
import be.ugent.verkeer4.verkeerdomain.data.*;
import be.ugent.verkeer4.verkeerweb.dataobjects.*;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteDetails;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteEdit;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteOverview;
import be.ugent.verkeer4.verkeerweb.viewmodels.RouteSummaryEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RouteController {

    @RequestMapping(value = "route/list", method = RequestMethod.GET)
    public ModelAndView getList() throws ClassNotFoundException {

        IRouteService routeService = new RouteService(); // eventueel dependency injection

        // haal routes op
        List<Route> lst = routeService.getRoutes();

        // haal meest recentste gegevens op voor alle routes
        List<RouteData> mostRecentRouteSummaries = routeService.getMostRecentRouteSummaries();

        RouteOverview overview = new RouteOverview();

        // hou per route id een RouteSummaryEntry bij
        Map<Integer, RouteSummaryEntry> entries = new HashMap<>();

        // overloop alle routes en maak een nieuw routeSummaryEntry en steek het in de entries map
        for (Route r : lst) {
            RouteSummaryEntry entry = new RouteSummaryEntry();
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
            RouteSummaryEntry entry = entries.get(r.getId());
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

        // geef mee als model aan view
        ModelAndView model = new ModelAndView("route/list");
        model.addObject("overview", overview);

        return model;
    }

    @RequestMapping(value = "route/detail", method = RequestMethod.GET)
    public ModelAndView getDetail(int id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService();
        IProviderService providerService = new ProviderService(routeService, poiService);

        Route route = routeService.getRoute(id);

        // TODO valid range en filter opties
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 1, 0, 0, 0, 0);
        Date start = calendar.getTime();
        calendar.set(2016, 1, 29, 23, 59, 59);
        Date end = calendar.getTime();

        List<RouteData> data = providerService.getRouteDataForRoute(id, start, end);
        List<RouteData> summaries = routeService.getMostRecentRouteSummariesForRoute(id);
        RouteDetails detail = new RouteDetails(route, data, summaries);
        ModelAndView model = new ModelAndView("route/detail");
        model.addObject("detail", detail);
        return model;
    }

    @RequestMapping(value = "route/map", method = RequestMethod.GET)
    public ModelAndView getMap() throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        List<Route> lst = routeService.getRoutes();

        ModelAndView model = new ModelAndView("route/map");
        model.addObject("routes", lst);

        return model;
    }

    @ResponseBody
    @RequestMapping(value = "route/mapdata", method = RequestMethod.GET)
    public MapData ajaxGetMapRoutes(HttpServletRequest req) throws ClassNotFoundException {
        if (req.getParameter("id") == null || req.getParameter("id").equalsIgnoreCase("")) {
            return getAllRouteMapData();
        } else {
            int id = Integer.parseInt(req.getParameter("id"));
            return getRouteMapData(id);
        }
    }

    @RequestMapping(value = "route/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") Integer id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        Route r = routeService.getRoute(id);
        RouteEdit re = new RouteEdit();
        re.setName(r.getName());
        re.setFromAddress(r.getFromAddress());
        re.setToAddress(r.getToAddress());
        re.setFromLatLng(r.getFromLatitude() + "," + r.getFromLongitude());
        re.setToLatLng(r.getToLatitude() + "," + r.getToLongitude());

        ModelAndView model = new ModelAndView("route/edit");
        model.addObject("routeEdit", re);

        return model;
    }

    @RequestMapping(value = "route/edit/{id}", method = RequestMethod.POST)
    public ModelAndView edit(@Valid
            @ModelAttribute("routeEdit") RouteEdit route, BindingResult result) throws ClassNotFoundException {

        // validation, moet manueel aangezien geen hibernate-validators toegevoegd is
        if (route.getName() == null || route.getName().isEmpty()) {
            result.rejectValue("name", "error.name", "Naam is verplicht");
        }

        double fromLat = 0;
        double fromLng = 0;
        try {
            fromLat = Double.parseDouble(route.getFromLatLng().split(",")[0]);
            fromLng = Double.parseDouble(route.getFromLatLng().split(",")[1]);
        } catch (Exception ex) {
            result.rejectValue("fromLatLng", "error.fromLatLng", "Ongeldige van positie");
        }

        double toLat = 0;
        double toLng = 0;
        try {
            toLat = Double.parseDouble(route.getToLatLng().split(",")[0]);
            toLng = Double.parseDouble(route.getToLatLng().split(",")[1]);
        } catch (Exception ex) {
            result.rejectValue("toLatLng", "error.toLatLng", "Ongeldige naar positie");
        }

        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("route/edit", result.getModel());
            model.addObject("errors", result);
            return model;
        } else {

            IRouteService routeService = new RouteService();
            Route r = routeService.getRoute(route.getId());

            r.setName(route.getName());

            boolean updateWaypoints = r.getFromLatitude() != fromLat || r.getFromLongitude() != fromLng || r.getToLatitude() != toLat || r.getToLongitude() != toLng;

            r.setFromAddress(route.getFromAddress());
            r.setToAddress(route.getToAddress());
            r.setFromLatitude(fromLat);
            r.setFromLongitude(fromLng);

            r.setToLatitude(toLat);
            r.setToLongitude(toLng);

            routeService.updateRoute(r, updateWaypoints);

            return new ModelAndView(
                    "redirect:/route/detail?id=" + r.getId());
        }
    }

    private MapData getRouteMapData(int id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        Route r = routeService.getRoute(id);

        List<RouteWaypoint> waypoints = routeService.getRouteWaypointsForRoute(id);

        MapData data = new MapData();
        MapRoute mr = new MapRoute();
        mr.setName(r.getName());
        mr.setDistance(r.getDistance());
        mr.setId(r.getId());

        // TODO delay berekenen op delay kolom in routedata
        List<RouteData> summaries = routeService.getMostRecentRouteSummariesForRoute(id);
        double trafficDelayPercentage = getTrafficDelayPercentage(r, summaries.stream().toArray(RouteData[]::new));
        mr.setTrafficDelayPercentage(trafficDelayPercentage);

        int totalDelay = summaries.stream().mapToInt(RouteData::getDelay).sum();
        double avgDelay = totalDelay / (float) summaries.size();

        data.getRoutes().add(mr);

        for (RouteWaypoint waypoint : waypoints) {
            MapWaypoint wp = new MapWaypoint(waypoint.getLatitude(), waypoint.getLongitude());
            mr.getWaypoints().add(wp);
        }

        return data;
    }

    private double getTrafficDelayPercentage(Route r, RouteData[] summaries) {
        // TODO move to route service?
        if (summaries.length <= 0) {
            return 0;
        }

        double baseTravelTime = Arrays.stream(summaries).mapToDouble(rd -> rd.getBaseTime()).sum();
        double avgBaseTravelTime = baseTravelTime / (float) summaries.length;

        int totalTravelTime = Arrays.stream(summaries).mapToInt(rd -> rd.getTravelTime()).sum();
        double avgTravelTime = totalTravelTime / (float) summaries.length;

        double percentage = (avgTravelTime / avgBaseTravelTime) - 1;
        return percentage;
    }

    private MapData getAllRouteMapData() throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService();
        
        List<Route> routes = routeService.getRoutes();

        List<RouteWaypoint> waypoints = routeService.getRouteWaypoints();

        MapData data = new MapData();
        Map<Integer, MapRoute> mapRoutesPerId = new HashMap<>();
        Map<Integer, List<RouteData>> summariesPerRouteId = new HashMap<>();

        for (RouteData summary : routeService.getMostRecentRouteSummaries()) {
            List<RouteData> lst;
            if (!summariesPerRouteId.containsKey(summary.getRouteId())) {
                summariesPerRouteId.put(summary.getRouteId(), lst = new ArrayList<>());
            } else {
                lst = summariesPerRouteId.get(summary.getRouteId());
            }

            lst.add(summary);
        }

        List<POI> pois = poiService.getActivePOIs();
        // TODO group pois on same location
        for (POI poi : pois) {
            MapPOI mp = new MapPOI();
            mp.setId(poi.getId());
            mp.setLatitude(poi.getLatitude());
            mp.setLongitude(poi.getLongitude());
            mp.setInfo(poi.getInfo());
            data.getPois().add(mp);
        }
        
        for (Route r : routes) {
            MapRoute mr = new MapRoute();
            mr.setName(r.getName());
            mr.setDistance(r.getDistance());
            mr.setId(r.getId());

            List<RouteData> routeSummaries;
            if (!summariesPerRouteId.containsKey(r.getId())) {
                routeSummaries = new ArrayList<>();
            } else {
                routeSummaries = summariesPerRouteId.get(r.getId());
            }

            double traficDelayPercentage = getTrafficDelayPercentage(r, routeSummaries.stream().toArray(RouteData[]::new));
            mr.setTrafficDelayPercentage(traficDelayPercentage);

            mapRoutesPerId.put(r.getId(), mr);
            data.getRoutes().add(mr);
        }

        for (RouteWaypoint waypoint : waypoints) {
            MapWaypoint wp = new MapWaypoint(waypoint.getLatitude(), waypoint.getLongitude());
            mapRoutesPerId.get(waypoint.getRouteId()).getWaypoints().add(wp);
        }

        return data;
    }

}
