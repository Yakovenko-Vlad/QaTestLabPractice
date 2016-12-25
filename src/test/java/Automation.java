import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Яковенко Влад
 */
public class Automation {
    RemoteWebDriver driver;
    public WebDriverWait wait;
    Actions builder;

    public Automation(RemoteWebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        builder = new Actions(driver);
    }

    public void openBing() {
        driver.get("https://www.bing.com/");
    }

    public void searchWord(String word, String completeWord){
        driver.findElement(By.id("sb_form_q")).sendKeys(word);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("sa_ul"))));
        int count = driver.findElements(By.xpath(".//*[@id='sa_ul']/li")).size();//.//*[@id='sa_ul']/li[5]
        for(int i=1;i<count;i++) {
            WebElement searchElement = driver.findElement(By.xpath(".//*[@id='sa_ul']/li[" + i + "]/div"));
            if (searchElement.getText().equals(completeWord)){
                searchElement.click();
                break;
            }
        }
    }
    ArrayList<String> url;
    public void getUrl(){
        url = new ArrayList<>();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("b_results"))));
        List<WebElement> webelements= driver.findElements(By.xpath(".//*[@id='b_results']/li/div[2]/div/cite"));
        for(WebElement element: webelements){
            url.add(element.getText());
            System.out.println(element.getText());
        }
        System.out.println(url.size());
    }

    public Object[][] createObjectArray(){
        getUrl();
        Object[][] objectsArray = new Object[url.size()][2];
        for(int i=0, j=0;i<url.size();i++, j++){
            if(j==3) j++;
                objectsArray[i][0] = ".//*[@id='b_results']/li[" + (j + 1) + "]/div[1]/h2/a";
                objectsArray[i][1] = url.get(i);
        }
        return objectsArray;
    }

    public void openPage(String xpath){
        driver.findElement(By.xpath(xpath)).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}