import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Яковенко Влад
 */
public class Automation {
    public WebDriver driver;
    public WebDriverWait wait;
    public Automation() {
        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,15);
    }

    /**
     * Открытие поисковой системы и переход в раздел изображений
     */
    public void openImagesPage(){
        driver.get("https://www.bing.com/");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("sb_form_q"))));
        driver.findElement(By.id("scpl1")).click();
        wait.until(ExpectedConditions.titleContains("Лента изображений Bing"));
    }

    /**
     * Скрол страницы с изображениями
     */
    public void scrollPage(){
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        for(int i=0;i<2;i++) {
            int countOfImages1 = driver.findElements(By.className("mimg")).size();
            jse.executeScript("window.scrollBy(0, document.body.scrollHeight)");
            WebElement element = driver.findElement(By.xpath(".//*/li[" + ((i*6)+13) + "]/div/div[1]/a/div/img"));
            wait.until(ExpectedConditions.visibilityOf(element));
            int countOfImages2 = driver.findElements(By.className("mimg")).size();
            if(countOfImages1>=countOfImages2)
                throw new JavascriptException("Scroll failed");
        }
    }

    /**
     * Ввод слова без последней буквы и выбор нужного варианта
     */
    public void searchImages(){
        driver.findElement(By.id("sb_form_q")).sendKeys("automatio");
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("sa_ul"))));
        int count = driver.findElements(By.xpath(".//*[@id='sa_ul']/li")).size();//.//*[@id='sa_ul']/li[5]
        for(int i=1;i<count;i++) {
            WebElement searchElement = driver.findElement(By.xpath(".//*[@id='sa_ul']/li[" + i + "]/div"));
            if (searchElement.getText().equals("automation")){
                searchElement.click();
                break;
            }
        }
    }

    /**
     * Изменение фильтра даты для отображения изображений
     * @throws InterruptedException
     */
    public void setDate() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a/img"))));
        Thread.sleep(5000);
        driver.findElement(By.xpath(".//*[@id='ftrB']/ul/li[6]/span/span")).click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(".//*[@id='ftrB']/ul/li[6]/div/div/a[4]"))));
        driver.findElement(By.xpath(".//*[@id='ftrB']/ul/li[6]/div/div/a[4]")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a/img"))));
    }

    /**
     * Метод открывающий режим слайд-шоу, переключается между изображениями,
     * переходит на новую вкладку с выбраным изображением
     */
    public void openImage() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Actions builder = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@id='dg_c']/div[1]/div/div[1]/div/a/img"))));
        driver.findElement(By.xpath(".//*[@id='dg_c']/div/div/div[1]/div/a/img")).click();
        driver.switchTo().frame(driver.findElement(By.id("OverlayIFrame")));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@id='iol_imw']/div[1]/span/span/img"))));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("iol_navr"))));
        driver.findElement(By.id("iol_navr")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("iol_navl"))));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.xpath(".//*[@id='iol_fsc']/div/a[2]")), "class","iol_fst iol_fsst"));
        driver.findElement(By.id("iol_navl")).click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(".//*[@id='iol_imw']/div[1]/span/span/img"))));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.xpath(".//*[@id='iol_fsc']/div/a[1]")), "class","iol_fst iol_fsst"));
        String imgUrl = driver.findElement(By.xpath(".//*[@id='iol_imw']/div[1]/span/span/img")).getAttribute("src");
        driver.findElement(By.xpath(".//*[@id='iol_imw']/div[1]/span/span/img")).click();
        for(String t:driver.getWindowHandles()){
            driver.switchTo().window(t);
        }
        wait.until(ExpectedConditions.urlToBe(imgUrl));
        driver.quit();
    }
}
