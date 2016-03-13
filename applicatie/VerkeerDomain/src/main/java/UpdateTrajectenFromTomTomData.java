
import be.ugent.verkeer4.verkeerdomain.IRouteService;
import be.ugent.verkeer4.verkeerdomain.RouteService;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.CalculateRouteResponse;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Leg;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.Point;
import be.ugent.verkeer4.verkeerdomain.provider.tomtom.TomTomClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class UpdateTrajectenFromTomTomData {

    public static void UpdateTrajectenFromTomTom(String[] args) { 
        try {
            IRouteService routes = new RouteService();

            for (Route r : routes.getRoutes()) {

                CalculateRouteResponse response = TomTomClient.GetRoute(r.getFromLatitude(), r.getFromLongitude(), r.getToLatitude(), r.getToLongitude(), false, r.getAvoidHighwaysOrUseShortest());

                System.out.println("Processing route " + r.getName());

                if (response.getRoutes().size() > 0) {
                    be.ugent.verkeer4.verkeerdomain.provider.tomtom.Route tomtomRoute = response.getRoutes().get(0);

                    r.setDistance(tomtomRoute.getSummary().getLengthInMeters());
                    r.setDefaultTravelTime(tomtomRoute.getSummary().getTravelTimeInSeconds());

                    routes.updateRoute(r, false);
                    
                    int idx = 0;
                    for (Leg leg : tomtomRoute.getLegs()) {
                        for (Point pt : leg.getPoints()) {
                            RouteWaypoint wp = new RouteWaypoint();
                            wp.setIndex(idx);
                            wp.setLatitude(pt.getLatitude());
                            wp.setLongitude(pt.getLongitude());
                            wp.setRouteId(r.getId());

                            routes.insertRouteWaypoint(wp);
                            System.out.println("Inserting new waypoint " + idx);
                            idx++;
                        }
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdateTrajectenFromTomTomData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UpdateTrajectenFromTomTomData.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
