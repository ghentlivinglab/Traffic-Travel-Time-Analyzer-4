/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider.google.test;

import be.ugent.verkeer4.verkeerdomain.provider.GoogleProvider;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niels
 */
public class GoogleMapsClientTest {
    public GoogleMapsClientTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRoute() {
        RouteData data;
        
        GoogleProvider google = new GoogleProvider();
        Route route = new Route();
        route.setFromLatitude(51.0560905);
        route.setFromLongitude(3.6951634);
        route.setToLatitude(51.0663037);
        route.setToLongitude(3.6996797);
        
        data = google.poll(route);
        
        System.out.print(data.getDistance());

    }
}
