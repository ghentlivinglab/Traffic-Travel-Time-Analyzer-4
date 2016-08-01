package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.ILogService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.Logging;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.composite.LogCount;
import be.ugent.verkeer4.verkeerweb.viewmodels.LogDetailEntryVM;
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

    private ILogService logService;
    
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public ModelAndView logs() throws ClassNotFoundException {
        
        //dependency injection
        logService = new LogService(); 
        
        //logs overview model opbouwen
        List<LogCount> logCounts = getLogCount(logService);
        
        // geef mee als model aan view
        ModelAndView model = new ModelAndView("logs/logs");
        model.addObject("logs", logCounts);
                
        return model;
    }
    
    @ResponseBody
    @RequestMapping(value = "/logdata", method = RequestMethod.GET)
    public LogDetailOverviewVM ajaxGetLogData(@RequestParam("category") String category, @RequestParam("startDate") Date startDate, @RequestParam("endDate") Date endDate) throws ClassNotFoundException {
        
        String prefix = "be.ugent.verkeer4.";
        category = prefix + category;
        
        List<Logging> lst = logService.getLogsByCategoryAndDate(category, startDate, endDate);
        
        //maak het viewmodel object aan
        LogDetailOverviewVM logOverview = new LogDetailOverviewVM();
        
        //Datum converteren naar enkel datum (zonder tijd)
        SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        
        //Datum converteren naar tijd.
        SimpleDateFormat localTimeFormat = new SimpleDateFormat("HH:mm:ss");
        
        //overlopen van de logEntries in de database
        for(Logging l : lst){
            LogDetailEntryVM entry = new LogDetailEntryVM();
            entry.setId(l.getId());
            entry.setDate(localDateFormat.format(l.getDate()));
            entry.setTime(localTimeFormat.format(l.getDate()));
            entry.setCategory(l.getCategory());
            entry.setMessage(l.getMessage());
            
            //aanpassen naar het gewenste type voor de ViewModel
            if (l.getType() == LogTypeEnum.Warning){
                entry.setType("Warning");
                logOverview.getLogEntries().add(entry);
            }
            if (l.getType() == LogTypeEnum.Error){
                entry.setType("Error");
                logOverview.getLogEntries().add(entry);
            }
        }
        
        return logOverview;
    }    

    private List<LogCount> getLogCount(ILogService logService){
        //logs overview model opbouwen
        List<LogCount> logCounts = logService.getLogCount();
        for(int i = 0; i < logCounts.size(); i++){
            String category = logCounts.get(i).getCategory();
            
            logCounts.get(i).setCategory(category.replaceFirst("be.ugent.verkeer4.", ""));
        }
        return logCounts;
    }

}