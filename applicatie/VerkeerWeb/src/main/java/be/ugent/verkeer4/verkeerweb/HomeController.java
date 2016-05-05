package be.ugent.verkeer4.verkeerweb;

import be.ugent.verkeer4.verkeerdomain.ILogService;
import be.ugent.verkeer4.verkeerdomain.IPOIService;
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.LogService;
import be.ugent.verkeer4.verkeerdomain.POIService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.Logging;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.composite.LogCount;
import be.ugent.verkeer4.verkeerdomain.data.composite.POICount;
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
        IRouteService routeService = new RouteService();
        IPOIService poiService = new POIService(routeService);
        
        //logs overview model opbouwen
        LogHomeOverviewVM logHomeOverview = getLogHomeOverviewModel(logService);
        
        //POI's ophalen
        List<POICount> pois = poiService.getPOICount();
        
        // geef mee als model aan view
        ModelAndView model = new ModelAndView("home/index");
        model.addObject("logHomeOverview", logHomeOverview);
        model.addObject("POIs", pois);
        
        return model;
    }
    
    private LogHomeOverviewVM getLogHomeOverviewModel(ILogService logService) throws ClassNotFoundException {
        //haal logCounts op
        List<LogCount> lst = logService.getLogCount();
        
        //maak het viewmodel object aan
        LogHomeOverviewVM logHomeOverview = new LogHomeOverviewVM();
        
        if(lst.size() != 0){
            //overlopen van de logCount voor elke category.
            for(LogCount l : lst){
                LogHomeEntryVM entry = new LogHomeEntryVM();
                entry.setCategory(l.getCategory());
                entry.setInfoCount(l.getInfoCount());
                entry.setWarningCount(l.getWarningCount());
                entry.setErrorCount(l.getErrorCount());

                logHomeOverview.getLogEntries().add(entry);
            }
        }
       
        return logHomeOverview;
    }
}