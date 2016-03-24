package be.ugent.verkeer4.service;

import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrafficJamAnalysisService extends BaseService {

    public TrafficJamAnalysisService() {
        super(300000, "Traffic Jam Analysis");
    }

    

    @Override
    protected void action() {

        // today    
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date today = cal.getTime();

        try {
            IRouteService routeService = new RouteService();
            List<Route> routes = routeService.getRoutes();

            for (Route route : routes) {
                routeService.finalizeTrafficJams(route, today);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TrafficJamAnalysisService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
