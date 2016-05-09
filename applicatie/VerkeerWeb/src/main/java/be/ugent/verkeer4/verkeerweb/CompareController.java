package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.*;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerweb.dataobjects.CompareData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CompareController {

    /**
     * Het interval waar de waarden van de verschillende providers samengenomen
     * worden tot 1 gemiddelde waarde
     */
    private static final int COMPARISON_THRESHOLD_MILLIESECONDS = 60 * 5 * 1000;


    /**
     * Bouwt de view op om 2 verschillende routes, op hetzelfde tijdsstip te vergelijken
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping(value = "/compare/routes", method = RequestMethod.GET)
    public ModelAndView routesCompare() throws ClassNotFoundException {
        // get provider names sorted
        String[] providers = ProviderEnum.getProviderNamesSorted();

        // get the route names and id
        RouteService routeService = new RouteService();
        List<Route> routes = routeService.getRoutesInfo();

        // send view
        ModelAndView model = new ModelAndView("compare/routes");
        model.addObject("providers", providers);
        model.addObject("routes", routes);
        return model;
    }

    /**
     * Bouwt de view op 1 oute op twee verschillende tijdsstippen te vergelijken
     * @return
     * @throws ClassNotFoundException
     */
    @RequestMapping(value = "/compare/route", method = RequestMethod.GET)
    public ModelAndView routeTimeCompare() throws ClassNotFoundException {
        // get provider names sorted
        String[] providers = ProviderEnum.getProviderNamesSorted();

        // get the route names and id
        RouteService routeService = new RouteService();
        List<Route> routes = routeService.getRoutesInfo();

        // send view
        ModelAndView model = new ModelAndView("compare/route");
        model.addObject("providers", providers);
        model.addObject("routes", routes);
        return model;
    }

    /**
     * Berekent de data om 2 verschillende routes, op hetzelfde tijdsstip te vergelijken
     * @param routeId1 eerste route
     * @param routeId2 tweede route
     * @param startDate startdatum
     * @param endDate einddatum
     * @param providers welke providers incl.
     * @return
     * @throws ClassNotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "compare/routesREST", method = RequestMethod.GET)
    public CompareData compareRoute(@RequestParam("routeId1") int routeId1, @RequestParam("routeId2") int routeId2,
                                          @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate,
                                          @RequestParam("providers") String[] providers)
            throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService(routeService);
        IProviderService providerService = new ProviderService(routeService, poiService);

        int[] providerIds = ProviderEnum.providerNamesToIds(providers);
        List<RouteData> routeData1 = providerService.getRouteDataForRoute(routeId1, startDate, endDate, "Timestamp", providerIds);
        List<RouteData> routeData2 = providerService.getRouteDataForRoute(routeId2, startDate, endDate, "Timestamp", providerIds);
        return routeDatasToCompareData(startDate.getTime(), routeData1, routeData2);
    }

    /**
     *  Berekent de data om 1 route op twee verschillende tijdsstippen te vergelijken
     * @param routeId route
     * @param providers de providers
     * @param startDate1 startdatum interval 1
     * @param endDate1 einddatum interval 1
     * @param startDate2 startdatum 2
     * @param endDate2 einddatum interval 2
     * @return
     * @throws ClassNotFoundException
     */
    @ResponseBody
    @RequestMapping(value = "compare/routeREST", method = RequestMethod.GET)
    public CompareData compareRoutes(@RequestParam("routeId") int routeId, @RequestParam("providers") String[] providers,
                                          @RequestParam("startDate1") Date startDate1, @RequestParam("endDate1") Date endDate1,
                                          @RequestParam("startDate2") Date startDate2, @RequestParam("endDate2") Date endDate2)
            throws ClassNotFoundException {
        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService(routeService);
        IProviderService providerService = new ProviderService(routeService, poiService);

        int[] providerIds = ProviderEnum.providerNamesToIds(providers);
        List<RouteData> routeData1 = providerService.getRouteDataForRoute(routeId, startDate1, endDate1, "Timestamp", providerIds);
        List<RouteData> routeData2 = providerService.getRouteDataForRoute(routeId, startDate2, endDate2, "Timestamp", providerIds);
        Date startDate = startDate1.compareTo(startDate2) > 0 ? startDate2 : startDate1;
        return routeDatasToCompareData(startDate.getTime(), routeData1, routeData2);
    }

    /**
     * Vergelijkt de data van twee routes vanaf een startdatum en berekent de gegevens
     * @param startDate start van het interval (eind datum zit in de objecten)
     * @param routeData1 routedata reeks1
     * @param routeData2 idem reeks 2
     * @return CompareData object met de juiste data
     */
    private CompareData routeDatasToCompareData(long startDate, List<RouteData> routeData1,  List<RouteData> routeData2){
        CompareData data = new CompareData();

        // De "delegates"
        ICompareDataMember travelTimeGetter = RouteData::getTravelTime;
        ICompareDataMember delayGetter = RouteData::getDelay;

        // Fill the data object
        data.setRoute1TravelTime(calculateAvgRoute(routeData1, startDate, travelTimeGetter));
        data.setRoute1Delay(calculateAvgRoute(routeData1, startDate, delayGetter));
        data.setRoute2TravelTime(calculateAvgRoute(routeData2, startDate, travelTimeGetter));
        data.setRoute2Delay(calculateAvgRoute(routeData2, startDate, delayGetter));
        return data;
    }

    /**
     * Neemt het gemiddelde van een reeks waarden dit in een gegeven interval liggen
     * @param routeData de route data
     * @param startDate begin interval
     * @param dataMember welke data er berekend wordt
     * @return Een lijst coorinaten (array)
     */
    private List<long[]> calculateAvgRoute(List<RouteData> routeData, long startDate, ICompareDataMember dataMember) {
        List<long[]> results = new ArrayList<>();
        int index = 0;
        int sum = 0;
        int amount = 0;
        // overloop alle data
        while (index < routeData.size()) {
            int previousAmount = amount;
            // indien huidig object in huidig interval ligt, wordt het opgenomen
            if (routeData.get(index).getTimestamp().getTime() < startDate + COMPARISON_THRESHOLD_MILLIESECONDS) {
                sum += dataMember.get(routeData.get(index));
                amount++;
                index++;
            }

            // check if no data match in this time frame
            if (previousAmount == amount) {
                // calculate avg for this interval
                if (amount > 0) {
                    int avg = sum / amount;
                    long[] data = {startDate, avg};
                    results.add(data);
                }
                // init vars for next iteration
                sum = 0;
                amount = 0;
                startDate += COMPARISON_THRESHOLD_MILLIESECONDS;
            }
        }
        return results;
    }
}
