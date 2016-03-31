package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.ILogService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.data.Logging;
import be.ugent.verkeer4.verkeerweb.viewmodels.LogOverviewVM;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() throws ClassNotFoundException {
        
        //dependency injection
        ILogService logService = new LogService(); 
        
        //logs overview model opbouwen
        LogOverviewVM logOverview = getLogOverviewModel(logService);
        
        // geef mee als model aan view
        ModelAndView model = new ModelAndView("home/index");
        model.addObject("logOverview", logOverview);
        
        return model;
    }
    
    private LogOverviewVM getLogOverviewModel(ILogService logService) throws ClassNotFoundException {
        
        //haal logs op
        List<Logging> lst = logService.getLogs();
        
        //maak het viewmodel object aan
        LogOverviewVM logOverview = new LogOverviewVM();
        
        for(Logging l : lst) {
            LogEntryVM entry = new LogEntryVM();
            entry.setLog(l);
            
            logOverview.getLogEntries().add(entry);
            
        }
        
        return logOverview;
    }
}