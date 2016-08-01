package be.ugent.verkeer4.verkeerdomain;

import javafx.geometry.Point3D;

/**
 * http://stackoverflow.com/a/33173338/694640
 */
public class GeoCoordinateHelper {

    /**
     * Semi-major axis of earth in meter
     */
    public static final double WGS84_A = 6378137.0;

    /**
     * Semi-minor axis of earth in meter
     */
    public static final double WGS84_B = 6356752.314245;

    /**
     * Eccentricity of earth
     */
    public static final double WGS84_E
            = Math.sqrt((WGS84_A * WGS84_A) / (WGS84_B * WGS84_B) - 1);

    public static final double DEGREES_TO_RADIANS = Math.PI / 180;

    /**
     * Calculates a three-dimensional point in the World Geodetic System (WGS84)
     * from latitude and longitude in meters
     */
    public static Point3D latLonToPoint3D(double lat, double lon) {
        double clat = Math.cos(lat * DEGREES_TO_RADIANS);
        double slat = Math.sin(lat * DEGREES_TO_RADIANS);
        double clon = Math.cos(lon * DEGREES_TO_RADIANS);
        double slon = Math.sin(lon * DEGREES_TO_RADIANS);

        double N = WGS84_A / Math.sqrt(1.0 - WGS84_E * WGS84_E * slat * slat);

        double x = N * clat * clon;
        double y = N * clat * slon;
        double z = N * (1.0 - WGS84_E * WGS84_E) * slat;
        return new Point3D(x, y, z);
    }

    /**
     * Calculates distance of projection p of vector a on vector b.
     *
     * Use formula for projection, with p being the projection point:
     * <p>
     * p = a X b / |b|^2 * b
     * </p>
     * X being the dot product, * being multiplication of vector and constant
     *
     * @param a
     * @param b
     * @return
     */
    private static Point3D calculateProjection(Point3D a, Point3D b) {
        return b.multiply(a.dotProduct(b) / (b.dotProduct(b)));
    }

    /**
     * Calculates shortest distance of vector x and the line defined by the
     * vectors a and b.
     */
    private static double calculateDistanceToLine(Point3D x, Point3D a, Point3D b) {
        Point3D projectionOntoLine
                = calculateProjection(x.subtract(a), b.subtract(a)).add(a);
        return projectionOntoLine.distance(x);
    }

    public static double getClosestDistanceToLineSegment(Point3D point, Point3D segmentPoint1, Point3D segmentPoint2) {

        
        return Math.max(calculateDistanceToLine(point, segmentPoint1, segmentPoint2),
                Math.min(point.distance(segmentPoint1), point.distance(segmentPoint2)));
    }
}
