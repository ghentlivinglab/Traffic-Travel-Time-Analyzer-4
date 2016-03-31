package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerweb.viewmodels.LogOverviewVM;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() throws ClassNotFoundException {
        
        //logs overview model opbouwen
        LogOverviewVM logOverview = getLogOverviewModel();
        
        // geef mee als model aan view
        ModelAndView model = new ModelAndView("home/index");
        model.addObject("logOverview", logOverview);
        
        return model;
    }
    
    private LogOverviewVM getLogOverviewModel(ILogService logService) throws ClassNotFoundException {
        
        List<Logging> lst = logService.getLogs();
        
        return 
    }
}

