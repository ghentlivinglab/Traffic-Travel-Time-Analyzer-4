package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InfoController {

    @RequestMapping(value = "info/contact", method = RequestMethod.GET)
    public ModelAndView contact() throws ClassNotFoundException {
        ModelAndView model = new ModelAndView("info/contact");
        return model;
    }
    
    @RequestMapping(value = "info/providers", method = RequestMethod.GET)
    public ModelAndView providers() throws ClassNotFoundException {
        ModelAndView model = new ModelAndView("info/providers");
        return model;
    }
    
    @RequestMapping(value = "info/tos", method = RequestMethod.GET)
    public ModelAndView tos() throws ClassNotFoundException {
        ModelAndView model = new ModelAndView("info/tos");
        return model;
    }
    
    @RequestMapping(value = "info/about", method = RequestMethod.GET)
    public ModelAndView about() throws ClassNotFoundException {
        ModelAndView model = new ModelAndView("info/about");
        return model;
    }
}
