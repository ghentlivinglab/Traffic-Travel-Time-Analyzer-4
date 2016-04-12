package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.ILogService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.Logging;
import be.ugent.verkeer4.verkeerdomain.data.composite.LogCount;
import be.ugent.verkeer4.verkeerweb.viewmodels.LogHomeEntryVM;
import be.ugent.verkeer4.verkeerweb.viewmodels.LogHomeOverviewVM;
import java.text.SimpleDateFormat;
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
        LogHomeOverviewVM logHomeOverview = getLogHomeOverviewModel(logService);
        
        // geef mee als model aan view
        ModelAndView model = new ModelAndView("home/index");
        model.addObject("logHomeOverview", logHomeOverview);
        
        return model;
    }
    
    private LogHomeOverviewVM getLogHomeOverviewModel(ILogService logService) throws ClassNotFoundException {
        
        
        //MAAK HIERVOOR EEN NIEUW TYPE OBJECT AAN IN DE VerkeerDomain.Data.composite, dit mag een speciaal object zijn voor deze weergave.
        //Zie voorbeeld GroupedRouteTrafficJamCause
        
        //haal logs op
        List<LogCount> lst = logService.getLogCount();
        
        //maak het viewmodel object aan
        LogHomeOverviewVM logHomeOverview = new LogHomeOverviewVM();
        
        //overlopen van de logEntries in de database
        //enkel de laatste 100 entries weergeven
        for(int i = lst.size()-1; i >= (lst.size() - 50); i--){
            Logging l = lst.get(i);
            LogHomeEntryVM entry = new LogHomeEntryVM();
            entry.setId(l.getId());
            entry.setDate(localDateFormat.format(l.getDate()));
            entry.setTime(localTimeFormat.format(l.getDate()));
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
            
            logHomeOverview.getLogEntries().add(entry);
        }
  
        return logHomeOverview;
    }
}