package be.ugent.verkeer4.verkeerdomain;

import be.ugent.verkeer4.verkeerdomain.data.LogTypeEnum;
import be.ugent.verkeer4.verkeerdomain.data.POI;
import be.ugent.verkeer4.verkeerdomain.data.POINearRoute;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.RouteWaypoint;
import be.ugent.verkeer4.verkeerdomain.data.composite.POIWithDistanceToRoute;
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

    /**
     * Geeft alle actieve POI's en indexeer ze per hun reference id
     * @param provider
     * @return 
     */
    @Override
    public Map<String, POI> getActivePOIPerReferenceIdForProvider(ProviderEnum provider) {
        return repo.getPOISet().getActivePOIPerReferenceIdForProvider(provider);
    }

    /**
     * Update de gegeven poi in de database
     * @param poi 
     */
    @Override
    public void update(POI poi) {
        try {
            repo.getPOISet().update(poi);
        } catch (Exception ex) {
            LogService.getInstance().insert(LogTypeEnum.Error, "POI Service Error", LogService.class.getName() + ex.getMessage()); 
            //Logger.getLogger(POIService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Voegt de poi toe in de database
     * @param poi 
     */
    @Override
    public void insert(POI poi) {
        repo.getPOISet().insert(poi);
    }

    /**
     * Geeft alle actieve POIs terug (POI's waarvan Until nog niet is ingevuld en dus
     * nog teruggeven wordt bij de polling)
     * @return 
     */
    @Override
    public List<POI> getActivePOIs() {
        return repo.getPOISet().getActivePOI();
    }

    /**
     * Geeft alle POI's terug met hun afstand tot de de gegeven route en tussen de gegeven periode
     * @param routeId
     * @param from
     * @param to
     * @return 
     */
    @Override
    public List<POIWithDistanceToRoute> getPOIsNearRoute(int routeId, Date from, Date to) {
        // voeg de actieve toe als de huidige tijd in de range zit
        boolean includeActive = to.getTime() >= new Date().getTime();
        return repo.getPOISet().getPOIsNearRoute(routeId, from, to, includeActive);
    }
    
    /**
     * Wrapper voor een route waypoint met zijn cartesische coordinaten
     */
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

    /**
     * Matched nog niet gematchede POI's met dichtsbijzijnde routes
     * @throws ClassNotFoundException 
     */
    @Override
    public void matchPOIsWithRoute() throws ClassNotFoundException {
        double maxDistance = Settings.getInstance().getMaxDistanceForPOIRouteMatching();

        // groepeer de waypoints per route id en wrap ze in Waypoint objecten die ook direct
        // de coordinaten berekent, hierdoor moet dit maar 1x berekend worden.
        Map<Integer, List<Waypoint>> waypointsPerRoute = new HashMap<>();

        // wrap alle waypoints van de routes als WayPoint object zodat 
        // de cartesische coordinaten maar 1x per route waypoint berekend worden
        for (RouteWaypoint wp : routeService.getRouteWaypoints()) {
            List<Waypoint> routeWaypoints = waypointsPerRoute.get(wp.getRouteId());
            if (routeWaypoints == null) {
                waypointsPerRoute.put(wp.getRouteId(), routeWaypoints = new ArrayList<>());
            }
            routeWaypoints.add(new Waypoint(wp));
        }

        // vraag alle POI's op die nog niet gematched zijn
        List<POI> unmatchedPOIs = repo.getPOISet().getUnmatchedPOIs();
        for (POI poi : unmatchedPOIs) {

            // overloop routes met hun waypoints
            for (Map.Entry<Integer, List<Waypoint>> entry : waypointsPerRoute.entrySet()) {
                int routeId = entry.getKey();
                List<Waypoint> waypoints = entry.getValue();

                boolean notMatchedForRoute = true;
                // bepaal alle segmenten door de huidige en volgende waypoints te overlopen
                // en stop zodra we zeker zijn dat de route gematched is
                for (int i = 0; i < waypoints.size() - 1 && notMatchedForRoute; i++) {

                    Waypoint segmentPoint1 = waypoints.get(i);
                    Waypoint segmentPoint2 = waypoints.get(i + 1);

                    // bepaal de cartesische coordinaten van de poi
                    Point3D coord = GeoCoordinateHelper.latLonToPoint3D(poi.getLatitude(), poi.getLongitude());
                    // en bereken de afstand (in meter)
                    double distance = GeoCoordinateHelper.getClosestDistanceToLineSegment(coord, segmentPoint1.getCoordinates(), segmentPoint2.getCoordinates());

                    if (distance / 1000 < maxDistance) {
                        // POI valt binnen de max afstand
                        // save een POI Near Route entry
                        POINearRoute near = new POINearRoute();
                        near.setPoiId(poi.getId());
                        near.setRouteId(routeId);
                        near.setDistance(distance / 1000);

                        try {
                            repo.getPOISet().insertPOINearRoute(near);
                        }
                        catch(Exception ex) {
                            LogService.getInstance().insert(LogTypeEnum.Error, "POI Service Error", LogService.class.getName() + ex.getMessage()); 
                            //Logger.getLogger(POIService.class.getName()).log(Level.WARNING, null, ex);
                        }
                        notMatchedForRoute = false;
                    }
                }
            }
            // flag de POI dat de POI gematched is
            repo.getPOISet().updatePOIMatchedWithRoute(poi.getId(), true);
        }
    }
}
