import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by Яковенко Влад on 17.12.2016.
 */
public class MainTest {
    Automation automation;
    EventFiringWebDriver driver;

    @BeforeClass
    public void run(){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
        driver = new EventFiringWebDriver(new ChromeDriver());
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.register(new EventHandler());
        automation = new Automation(driver);
    }

    @AfterClass
    public void closeDriver(){
        driver.quit();
    }

    @Test(priority = 0)
    public void openBingTest(){
        automation.openBing();
        assertEquals(driver.getCurrentUrl(), "https://www.bing.com/");
    }
    @Test(dependsOnMethods = {"openBingTest"})
    public void imagePageTest(){
        automation.imagePage();
        assertEquals(driver.getTitle(), "Лента изображений Bing");
    }

    @Test(dependsOnMethods = {"imagePageTest"})
    public void scrollPageTest(){
        int scrollCount = 2;
        for(int i=0;i<scrollCount;i++){
            boolean bool=false;
            int [] array = automation.scrollPage(i);
            if(array[0]<array[1]) bool=true;
            assertEquals(bool,true);
        }
        automation.scrollPageUp();
    }

    @DataProvider
    public Object[][] dataProvide(){
        ArrayList<String> words = automation.readFile();
        return new Object[][]{
            {words.get(0),"automation"},
            {words.get(1),"testing"},
            {words.get(2),"QaTestLab"}
        };
    }

    @Test(dependsOnMethods = {"scrollPageTest"}, dataProvider = "dataProvide")
    public void searchTest(String ar, String er){
        assertEquals(automation.search(ar), "Результаты поиска изображений по запросу \"" +er+"\"");
    }

    @Test(dependsOnMethods = {"searchTest"})
    public void showImageInfoTest(){
        assertEquals(automation.showImageInfo(), true);

    }
    @Test(dependsOnMethods = {"showImageInfoTest"})
    public void openSlideShowTest(){
        automation.openSlideShow();
        assertEquals(driver.findElement(By.xpath(".//*[@id='iol_imw']/div[2]")).isEnabled(), true);
    }


    @Parameters({"minElementCount"})
    @Test(dependsOnMethods = {"openSlideShowTest"})
    public void showRelatedImagesTest(String minElementCount){
        int count = Integer.parseInt(minElementCount);
        Assert.assertTrue(automation.showRelatedImages()>=count);
    }
}
