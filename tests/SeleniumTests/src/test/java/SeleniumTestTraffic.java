import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumTestTraffic {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
        //driver = new FirefoxDriver();
        baseUrl = "http://verkeer-4.vop.tiwi.be";
        int maxRouteId = 54; //huidig hoogste id van routes
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.automatic-ntlm-auth.trusted-uris", baseUrl);
        driver = new FirefoxDriver(profile);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to("http://guest:1RRBpmM0KC@verkeer-4.vop.tiwi.be");
        driver.get(baseUrl);
  }

  @Test
  public void testSeleniumTestTraffic() throws Exception {
    String begin = "01/04/2016 00:00";
    String einde = "01/04/2016 23:59";
      
    driver.get(baseUrl + "/");
    driver.get("http://verkeer-4.vop.tiwi.be/route/list");
    //driver.findElement(By.linkText("Overview (current)")).click();
    driver.findElement(By.linkText("Kennedylaan (R4) northbound")).click();
    //driver.findElement(By.cssSelector("span.glyphicon.glyphicon-calendar")).click();
    //driver.findElement(By.xpath("//div[@id='datetimepicker-begin']/div/ul/li/div/div/table/tbody/tr/td[6]")).click();
    //driver.findElement(By.linkText("Previous")).click();
    //driver.findElement(By.linkText("1")).click();
    //driver.findElement(By.cssSelector("#datetimepicker-end > span.input-group-addon")).click();
    //driver.findElement(By.xpath("//div[@id='datetimepicker-end']/div/ul/li/div/div/table/tbody/tr/td[6]")).click();
    //driver.findElement(By.id("update-btn")).click();
    //WebElement tblJamsBody = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("tblJamsBody")));
    //driver.findElement(By.id("update-btn")).click();
    //driver.findElement(By.id("update-btn")).click();
    driver.findElement(By.id("datetimepicker-begin-input")).clear();
    driver.findElement(By.id("datetimepicker-begin-input")).click();
    WebElement element_enter = driver.findElement(By.id("datetimepicker-begin-input"));
    element_enter.findElement(By.id("datetimepicker-begin-input")).sendKeys(begin);

    //element_enter.sendKeys(Keys.RETURN);
    
    driver.findElement(By.id("datetimepicker-end-input")).clear();
    driver.findElement(By.id("datetimepicker-end-input")).click();
    WebElement element_enter2 = driver.findElement(By.id("datetimepicker-end-input"));
    element_enter2.findElement(By.id("datetimepicker-end-input")).sendKeys(einde);

    //element_enter2.sendKeys(Keys.RETURN);
    
    driver.findElement(By.id("update-btn")).click();
    WebElement tblJamsBody = (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("tblJamsBody")));
    
    try {
      assertTrue(Pattern.compile("HAZARD_ON_ROAD_CONSTRUCTION").matcher(driver.findElement(By.xpath("//tbody[@id='tblJamsBody']/tr[2]/td[6]")).getText()).find());
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
