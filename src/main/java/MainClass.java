import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by Яковенко Влад on 02.12.2016.
 */
public class MainClass {
    public static void main(String[] args){
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver,10);
        driver.get("https://www.bing.com/");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("sb_form_q"))));
        driver.findElement(By.id("sb_form_q")).sendKeys("automation");
        driver.findElement(By.id("sb_form_go")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("b_title"))));
        System.out.println("Search title is \""+driver.getTitle()+"\"");
        System.out.println("\nResult Titles:");
        /*List<WebElement> list = driver.findElements(By.xpath(".//*[@id='b_results']/li/div[1]/h2/a"));
        for (int i=0;i<list.size();i++)
            System.out.println(list.get(i).getText());*/

        List<WebElement> list = driver.findElements(By.className("b_title"));
        for(int i=0;i<list.size();i++)
            System.out.println(list.get(i).getText().replaceFirst("Перевести эту страницу"," "));
        driver.quit();
    }
}


