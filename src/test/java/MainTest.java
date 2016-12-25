import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by Яковенко Влад
 */
public class MainTest {
    Automation automation;
    RemoteWebDriver driver;
    @BeforeClass
    public void run(){
        String browser = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("browser.txt")));
            browser=reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DesiredCapabilities capabilities=null;
        switch (browser){
            case "Chrome": capabilities = DesiredCapabilities.chrome(); break;
            case "FireFox": capabilities = DesiredCapabilities.firefox(); break;
            case "Android": capabilities = DesiredCapabilities.android(); break;
        }
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        automation = new Automation(driver);
    }

    @AfterClass
    public void closeDriver(){
        driver.quit();
    }

    @Test(priority = 0)
    public void openBingTest(){
        automation.openBing();
        assertEquals(driver.findElement(By.xpath(".//*[@id='sbox']/div[1]")).getText(),"Bing");
    }

    @Test(dependsOnMethods = "openBingTest")
    public void searchLineTest(){
        assertEquals(driver.findElement(By.id("sb_form_q")).isEnabled(), true);
        assertEquals(driver.findElement(By.id("sb_form_go")).isEnabled(), true);
    }

    @DataProvider
    public Object[][] dataProvider(){
        return new Object[][]{
                {"automatio","automation"}
        };
    }

    @Test(dependsOnMethods = {"searchLineTest"}, dataProvider = "dataProvider")
    public void searchWordTest(String incompleteWord, String completeWord){
        automation.searchWord(incompleteWord, completeWord);
        assertEquals(driver.getTitle(),completeWord+" - Bing");
    }

    @DataProvider
    public Object[][] urlProvider(){
        return automation.createObjectArray();
    }

    @Test(dependsOnMethods = {"searchWordTest"}, dataProvider = "urlProvider")
    public void checkUrl(String xpath,String url){
        automation.openPage(xpath);
        assertEquals(driver.getCurrentUrl().contains(url), true);
        driver.navigate().back();
    }
}