package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.ILogService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.Logging;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.composite.LogCount;
import be.ugent.verkeer4.verkeerweb.viewmodels.LogDetailOverviewVM;
import be.ugent.verkeer4.verkeerweb.viewmodels.LogHomeEntryVM;
import be.ugent.verkeer4.verkeerweb.viewmodels.LogHomeOverviewVM;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogController {

    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public ModelAndView logs() throws ClassNotFoundException {
        
        //dependency injection
        ILogService logService = new LogService(); 
        
        //logs overview model opbouwen
        LogHomeOverviewVM logHomeOverview = getLogHomeOverviewModel(logService);
        List<LogCount> logCounts = logService.getLogCount();
        
        // geef mee als model aan view
        ModelAndView model = new ModelAndView("logs/logs");
        model.addObject("logHomeOverview", logHomeOverview);
        model.addObject("logs", logCounts);
                
        return model;
    }
    
    @ResponseBody
    @RequestMapping(value = "/logdata", method = RequestMethod.GET)
    public LogDetailOverviewVM ajaxGetLogData(@RequestParam("category") String category,@RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) throws ClassNotFoundException {
        //dependency injection
        ILogService logService = new LogService(); 
        
        //haal logs op
        List<Logging> lst = logService.getLogs();
        
        //maak het viewmodel object aan
        LogDetailOverviewVM logOverview = new LogDetailOverviewVM();
        /*
        //Datum converteren naar enkel datum (zonder tijd)
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        
        //Datum converteren naar tijd.
        SimpleDateFormat localTimeFormat = new SimpleDateFormat("HH:mm:ss");
        
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
            
            logOverview.getLogEntries().add(entry);
            }
        */
        return logOverview;
    }    

    private LogHomeOverviewVM getLogHomeOverviewModel(ILogService logService) throws ClassNotFoundException {
        //haal logCounts op
        List<LogCount> lst = logService.getLogCount();
        
        //maak het viewmodel object aan
        LogHomeOverviewVM logHomeOverview = new LogHomeOverviewVM();
        
        //overlopen van de logCount voor elke category.
        for(LogCount l : lst){
            LogHomeEntryVM entry = new LogHomeEntryVM();
            entry.setCategory(l.getCategory());
            entry.setInfoCount(l.getInfoCount());
            entry.setWarningCount(l.getWarningCount());
            entry.setErrorCount(l.getErrorCount());
            
            logHomeOverview.getLogEntries().add(entry);
        }
  
        return logHomeOverview;
    }
}