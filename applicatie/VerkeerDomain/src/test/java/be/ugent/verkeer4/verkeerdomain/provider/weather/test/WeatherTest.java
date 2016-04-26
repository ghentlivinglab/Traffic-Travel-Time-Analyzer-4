/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.ugent.verkeer4.verkeerdomain.provider.weather.test;

import be.ugent.verkeer4.verkeerdomain.data.WeatherData;
import be.ugent.verkeer4.verkeerdomain.provider.WeatherProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Niels
 */
public class WeatherTest {
    public WeatherTest() {
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
        WeatherProvider provider = new WeatherProvider();
        WeatherData d = provider.poll("IVLAAMSG103");
        
        if(d == null)
        {
            System.out.print("leeg");
        }
        else
        {
            System.out.print(d.getWeatherCondition() + " " + d.getWindDirection() + " " + d.getWindSpeed() + " " + d.getTimestamp());
        }       
    }
}
