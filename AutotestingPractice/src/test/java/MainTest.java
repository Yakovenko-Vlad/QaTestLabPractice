import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Created by Яковенко Влад on 17.12.2016.
 */
public class MainTest {
    WebDriver driver;
    @BeforeTest
    public void run(){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.get("https://www.bing.com/");
    }
    @Test
    public void openBing(){
        System.out.println("Hello");
    }
    @Test
    public void openGoogle(){
        System.out.println("Hello");
    }
    @AfterTest
    public void close(){
        driver.quit();
    }
}
