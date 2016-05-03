package be.ugent.verkeer4.verkeerweb.viewmodels;

import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CompareController {

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
}
