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
        List<Route> lst = routeService.getRoutes();

        List<RouteData> mostRecentRouteSummaries = routeService.getMostRecentRouteSummaries();
        // TODO maak van routes een map van route viewmodels en overloop alle summaries en lookup route in de map om de recente gegevens aan te vullen

        RouteOverview overview = new RouteOverview();

        Map<Integer, RouteSummaryEntry> entries = new HashMap<>();

        for (Route r : lst) {
            RouteSummaryEntry entry = new RouteSummaryEntry();
            entry.setRoute(r);
            overview.getSummaries().add(entry);
            entries.put(r.getId(), entry);
        }

        for (RouteData sum : mostRecentRouteSummaries) {
            Map<ProviderEnum, RouteData> summaryPerProvider = entries.get(sum.getRouteId()).getRecentSummaries();
            summaryPerProvider.put(sum.getProvider(), sum);
        }

        for (Route r : lst) {
            RouteSummaryEntry entry =  entries.get(r.getId());
            Map<ProviderEnum, RouteData> summaryPerProvider =entry.getRecentSummaries();
            
            double delayPercentage = getTrafficDelayPercentage(r, summaryPerProvider.values().stream().toArray(RouteData[]::new));
            double currentTravelTime = r.getDefaultTravelTime() * (1 + delayPercentage);
            double delay = currentTravelTime - r.getDefaultTravelTime();
            if(delay < 0)
                delay = 0;
            entry.setDelay(delay);
            entry.setAverageCurrentTravelTime(currentTravelTime);
        }

        ModelAndView model = new ModelAndView("route/list");

        model.addObject("overview", overview);

        return model;
    }

    @RequestMapping(value = "route/detail", method = RequestMethod.GET)
    public ModelAndView getDetail(int id) throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        IProviderService providerService = new ProviderService(routeService);
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

        int totalTravelTime = Arrays.stream(summaries).mapToInt(RouteData::getTravelTime).sum();
        double avg = totalTravelTime / summaries.length;

        double percentage = (avg / r.getDefaultTravelTime()) - 1;
        return percentage;
    }

    private MapData getAllRouteMapData() throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
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
