package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.POINearRoute;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point3D;

public class POIService extends BaseService implements IPOIService {

    private final IRouteService routeService;

    public POIService(IRouteService routeService) throws ClassNotFoundException {
        super();
        this.routeService = routeService;
    }

    @Override
    public Map<String, POI> getActivePOIPerReferenceIdForProvider(ProviderEnum provider) {
        return repo.getPOISet().getActivePOIPerReferenceIdForProvider(provider);
    }

    @Override
    public void update(POI poi) {
        try {
            repo.getPOISet().update(poi);
        } catch (Exception ex) {
            Logger.getLogger(POIService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(POI poi) {
        repo.getPOISet().insert(poi);
    }

    @Override
    public List<POI> getActivePOIs() {
        return repo.getPOISet().getActivePOI();
    }

    @Override
    public List<POI> getPOIsNearRoute(int routeId, Date from, Date to) {
        // voeg de actieve toe als de huidige tijd in de range zit
        boolean includeActive = to.getTime() >= new Date().getTime();
        return repo.getPOISet().getPOIsNearRoute(routeId, from, to, includeActive);
    }
    
    private class Waypoint {

        private RouteWaypoint routeWaypoint;
        private Point3D coordinates;

        public Waypoint(RouteWaypoint wp) {
            this.routeWaypoint = wp;
            this.coordinates = GeoCoordinateHelper.latLonToPoint3D(wp.getLatitude(), wp.getLongitude());
        }

        /**
         * @return the routeWaypoint
         */
        public RouteWaypoint getRouteWaypoint() {
            return routeWaypoint;
        }

        /**
         * @param routeWaypoint the routeWaypoint to set
         */
        public void setRouteWaypoint(RouteWaypoint routeWaypoint) {
            this.routeWaypoint = routeWaypoint;
        }

        /**
         * @return the coordinates
         */
        public Point3D getCoordinates() {
            return coordinates;
        }

        /**
         * @param coordinates the coordinates to set
         */
        public void setCoordinates(Point3D coordinates) {
            this.coordinates = coordinates;
        }
    }

    @Override
    public void matchPOIsWithRoute() throws ClassNotFoundException {
        double maxDistance = Settings.getInstance().getMaxDistanceForPOIRouteMatching();

        // groepeer de waypoints per route id en wrap ze in Waypoint objecten die ook direct
        // de coordinaten berekent, hierdoor moet dit maar 1x berekend worden.
        Map<Integer, List<Waypoint>> waypointsPerRoute = new HashMap<>();

        for (RouteWaypoint wp : routeService.getRouteWaypoints()) {
            List<Waypoint> routeWaypoints = waypointsPerRoute.get(wp.getRouteId());
            if (routeWaypoints == null) {
                waypointsPerRoute.put(wp.getRouteId(), routeWaypoints = new ArrayList<>());
            }

            routeWaypoints.add(new Waypoint(wp));
        }

        List<POI> unmatchedPOIs = repo.getPOISet().getUnmatchedPOIs();
        for (POI poi : unmatchedPOIs) {

            for (Map.Entry<Integer, List<Waypoint>> entry : waypointsPerRoute.entrySet()) {
                int routeId = entry.getKey();
                List<Waypoint> waypoints = entry.getValue();

                boolean notMatchedForRoute = true;
                for (int i = 0; i < waypoints.size() - 1 && notMatchedForRoute; i++) {

                    Waypoint segmentPoint1 = waypoints.get(i);
                    Waypoint segmentPoint2 = waypoints.get(i + 1);

                    Point3D coord = GeoCoordinateHelper.latLonToPoint3D(poi.getLatitude(), poi.getLongitude());
                    double distance = GeoCoordinateHelper.getClosestDistanceToLineSegment(coord, segmentPoint1.getCoordinates(), segmentPoint2.getCoordinates());

                    if (distance / 1000 < maxDistance) {
                        // POI valt binnen de max afstand
                        POINearRoute near = new POINearRoute();
                        near.setPoiId(poi.getId());
                        near.setRouteId(routeId);
                        near.setDistance(distance / 1000);

                        try {
                            repo.getPOISet().insertPOINearRoute(near);
                        }
                        catch(Exception ex) {
                            Logger.getLogger(POIService.class.getName()).log(Level.WARNING, null, ex);
                        }
                        notMatchedForRoute = false;
                    }
                }
            }
            
            repo.getPOISet().updatePOIMatchedWithRoute(poi.getId(), true);
        }
    }
}
