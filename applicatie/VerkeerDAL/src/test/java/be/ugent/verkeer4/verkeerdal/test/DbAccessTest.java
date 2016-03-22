package be.ugent.verkeer4.verkeerdal.test;

import be.ugent.verkeer4.verkeerdal.IUnitOfWork;
import be.ugent.verkeer4.verkeerdal.UnitOfWork;
import be.ugent.verkeer4.verkeerdomain.data.ProviderEnum;
import be.ugent.verkeer4.verkeerdomain.data.Route;
import be.ugent.verkeer4.verkeerdomain.data.RouteData;
import be.ugent.verkeer4.verkeerdomain.data.WeatherConditionEnum;
import be.ugent.verkeer4.verkeerdomain.data.WeatherData;
import be.ugent.verkeer4.verkeerdomain.data.WeatherDirectionEnum;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DbAccessTest extends TestCase {

    private IUnitOfWork repo;

    public DbAccessTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    @Override
    public void setUp() throws ClassNotFoundException {
        repo = new UnitOfWork("//localhost:3306/verkeer", "root", "");
    }

    @After
    @Override
    public void tearDown() {
    }

    @Test
    public void testInsertAndUpdateRouteData() throws Exception {
        RouteData rd = new RouteData();
        rd.setRouteId(11);
        rd.setDelay(123);
        rd.setTravelTime(1234);
        rd.setProvider(ProviderEnum.Coyote);
        rd.setDistance(5678);
        rd.setTimestamp(new Date());
        int newId = repo.getRouteDataSet().insert(rd);
      
        rd.setId(newId);
        
        rd.setDelay(987);
        rd.setProvider(ProviderEnum.HereMaps);
        repo.getRouteDataSet().update(rd);
        
    }
    
    
    public void testGetRoutes() {
        try {
            List<Route> trajecten = repo.getRouteSet().getItems();
            assertFalse("Geen trajecten gevonden", trajecten.isEmpty());
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    public void testGetSingleTraject() {
        try {
            Route firstRoute = repo.getRouteSet().getItems().get(0);
            Route traject = repo.getRouteSet().getItem(firstRoute.getId());
            assertNotNull("Traject niet gevonden", traject);
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }
    
    public void testInsertWeather()
    {
        WeatherData data = new WeatherData();
        data.setTimestamp(new Date());
        data.setLatitude(51);
        data.setLongitude(51);
        data.setTemperature(10.5);
        data.setWindSpeed(3.1);
        data.setWindDirection(WeatherDirectionEnum.North.getValue());
        data.setCondition(WeatherConditionEnum.Clear.getValue());
       
        int newId = repo.getWeatherSet().insert(data);
    }
    
    public void testGetWeather() {
        try {
            List<WeatherData> weather = repo.getWeatherSet().getItems();
            assertNotNull("Weer Items niet gevonden", weather);
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }

    public void testInsertTraject() {
        Route t = new Route();
        t.setName("Test traject");
        t.setDistance(1234);
        t.setFromAddress("From");
        t.setFromLatitude(51);
        t.setFromLongitude(4);
        t.setToAddress("To");
        t.setToLatitude(51.5);
        t.setToLongitude(4.5);

        int id = repo.getRouteSet().insert(t);
        assertNotSame("Id is 0", 0, id);

        t.setId(id);

        Route storedRoute = repo.getRouteSet().getItem(id);

        assertEquals("Naam is niet hetzelfde", t.getName(), storedRoute.getName());
        assertEquals("Afstand is niet hetzelfde", t.getDistance(), storedRoute.getDistance());
        
        repo.getRouteSet().delete(id);
        
    }

    public void testUpdateRoute() {

        Route t = repo.getRouteSet().getItems().get(0);
        t.setId(0);
        int id = repo.getRouteSet().insert(t); // copy
        t.setId(id);
        t.setName("Updated naam" + t.getName());
        try {
            repo.getRouteSet().update(t);
        } catch (Exception ex) {
            fail("Error: " + ex.getMessage());
        }

        Route storedTraject = repo.getRouteSet().getItem(id);
        assertEquals("Naam is niet hetzelfde", t.getName(), storedTraject.getName());
    }

    public void testDeleteRoute() {
        List<Route> trajecten = repo.getRouteSet().getItems();
        Route lastTraject = trajecten.get(trajecten.size() - 1);
        repo.getRouteSet().delete(lastTraject.getId());

        Route storedTraject = repo.getRouteSet().getItem(lastTraject.getId());
        assertNull("Traject is niet verwijderd", storedTraject);
    }
}
