/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class SeleniumTestDetail {
    
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private int maxRouteId;
    

    @Before
    public void setUp() throws Exception {
        //driver = new FirefoxDriver();
        baseUrl = "http://verkeer-4.vop.tiwi.be/";
        int maxRouteId = 54; //huidig hoogste id van routes
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.automatic-ntlm-auth.trusted-uris", baseUrl);
        driver = new FirefoxDriver(profile);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.navigate().to("http://guest:1RRBpmM0KC@verkeer-4.vop.tiwi.be");
        driver.get(baseUrl);

    }

    @Test
    public void testTTT() throws Exception {
        baseUrl = "http://verkeer-4.vop.tiwi.be/route/detail/";
        for(int i=11; i<13;i++) {
            driver.get(baseUrl + i);
            
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr/td[2]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[2]/td[2]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[3]/td[2]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[4]/td[2]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[5]/td[2]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[6]/td[2]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[7]/td[2]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[8]/td[2]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
        }
    }
    

    @Test
    public void testDelay() throws Exception {
        baseUrl = "http://verkeer-4.vop.tiwi.be/route/detail/";
        for(int i=11; i<13;i++) {
            driver.get(baseUrl + i);
            
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr/td[3]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[2]/td[3]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[3]/td[3]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[4]/td[3]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[5]/td[3]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[6]/td[3]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[7]/td[3]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
            try {
                assertTrue(Pattern.compile(" [0-9][0-9]' *").matcher(driver.findElement(By.xpath("//tbody[@id='summary-table-body']/tr[8]/td[3]/span")).getText()).find());
            } catch (Error e) {
                verificationErrors.append(e.toString());
            }
        }
    }
    
    @Test
    public void testFrom() throws Exception {
        baseUrl = "http://verkeer-4.vop.tiwi.be/route/detail/";
        for(int i=11; i<13;i++) {
            
            driver.get(baseUrl + i);
            
            try {
              assertTrue(Pattern.compile("[A-Z][a-z]*").matcher(driver.findElement(By.xpath("//dd[3]")).getText()).find());
            } catch (Error e) {
              verificationErrors.append(e.toString());
            }
            
        }
        
    }
    
    @Test
    public void testTo() throws Exception {
        baseUrl = "http://verkeer-4.vop.tiwi.be/route/detail/";
        for(int i=11; i<13;i++) {
            driver.get(baseUrl + i);
            
            try {
              assertTrue(Pattern.compile("[A-Z][a-z]*").matcher(driver.findElement(By.xpath("//dd[4]")).getText()).find());
            } catch (Error e) {
              verificationErrors.append(e.toString());
            }
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
