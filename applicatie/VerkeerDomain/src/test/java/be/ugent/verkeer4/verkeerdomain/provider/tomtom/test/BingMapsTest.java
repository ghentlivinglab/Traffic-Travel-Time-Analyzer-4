/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider.tomtom.test;

import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.provider.BingMapsProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tomas Bolckmans
 */
public class BingMapsTest {
    
    public BingMapsTest() {
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
    public void testRoute(){
        BingMapsProvider provider = new BingMapsProvider();
        
        be.ugent.verkeer4.verkeerdomain.data.Route r = new be.ugent.verkeer4.verkeerdomain.data.Route();
        r.setFromLatitude(51.038663);
        r.setFromLongitude(3.725996);
        r.setToLatitude(51.056146);
        r.setToLongitude(3.695183);

        RouteData rData = provider.useAPI(r);
        
    }
}
