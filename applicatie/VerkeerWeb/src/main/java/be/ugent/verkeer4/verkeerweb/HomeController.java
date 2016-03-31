package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.ILogService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.Logging;
import be.ugent.verkeer4.verkeerweb.viewmodels.LogEntryVM;
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
        
        //overlopen van de logEntries in de database
        //enkel de laatste 100 entries weergeven
        for(int i = lst.size()-1; i >= (lst.size() - 100); i--){
            Logging l = lst.get(i);
            LogEntryVM entry = new LogEntryVM();
            entry.setId(l.getId());
            entry.setDate(l.getDate());
            entry.setCategory(l.getCategory());
            entry.setMessage(l.getMessage());
            
            //aanpassen naar het gewenste type voor de ViewModel
            if(l.getType() == LogTypeEnum.Info){
                entry.setType("info");
            } 
            else if (l.getType() == LogTypeEnum.Warning){
                entry.setType("warning");
            }
            else{
                entry.setType("danger");
            }
            
            logOverview.getLogEntries().add(entry);
        }
  
        return logOverview;
    }
}