import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Яковенко Влад
 */
public class Automation {
    EventFiringWebDriver driver;
    public WebDriverWait wait;
    Actions builder;
    public Automation(EventFiringWebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
        builder = new Actions(driver);
    }

    public void openBing() {
        driver.get("https://www.bing.com/");
    }

    public void imagePage(){
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("sb_form_q"))));
        driver.findElement(By.id("scpl1")).click();
        wait.until(ExpectedConditions.titleContains("Лента изображений Bing"));
    }

    public int[] scrollPage(int i){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        int [] array = new int[2];
        array[0] = driver.findElements(By.className("mimg")).size();
        jse.executeScript("window.scrollBy(0, document.body.scrollHeight)");
        WebElement element = driver.findElement(By.xpath(".//*/li[" + ((i * 6) + 13) + "]/div/div[1]/a/div/img"));
        wait.until(ExpectedConditions.visibilityOf(element));
        array[1] = driver.findElements(By.className("mimg")).size();
        return array;
    }

    public void scrollPageUp(){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollTo(0, 0)");
    }

    public ArrayList<String> readFile(){
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("words.txt")))){
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {}
        return words;
    }

    public String search(String word){
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("sb_form_q"))));
        driver.findElement(By.id("sb_form_q")).clear();
        driver.findElement(By.id("sb_form_q")).sendKeys(word);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("sb_form_go")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a/img"))));
        return driver.findElement(By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a/img")).getAttribute("alt")+"";
    }

    public boolean showImageInfo(){
        builder.moveToElement(driver.findElement(By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a/img"))).build().perform();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(".//*[@id='detail']/img"))));
        if(driver.findElement(By.cssSelector(".ovrf.ovrfIconMS")).isDisplayed() && driver.findElement(By.cssSelector(".ovrf.ovrfIconFA")).isDisplayed() &&
                driver.findElement(By.cssSelector(".collicon.gen.favSav")).isDisplayed())
                return true;
        return false;
    }

    public void openSlideShow(){
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        builder.click(driver.findElement(By.cssSelector(".ovrf.ovrfIconMS"))).build().perform();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@id='iol_imw']/div[1]/span/span/img"))));
        driver.findElement(By.xpath(".//*[@id='iol_imw']/div[1]/span/span/img"));

    }

    public int showRelatedImages(){
        driver.findElement(By.xpath(".//*[@id='mmComponent_images_4_1_1_exp']/span")).click();
        return driver.findElements(By.xpath(".//*[@id='mmComponent_images_4_1_1_list']/li")).size();
    }

}
