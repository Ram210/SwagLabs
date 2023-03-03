package swagLabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class LaunchBrowser {
    WebDriver driver;
    @Test
    public void lBNavigateToPage() {
        System.setProperty("webdriver.chrome.driver", "E:\\Browsers\\chromedriver.exe");
        driver = new ChromeDriver();
        //driver.navigate().to("https://ui.cogmento.com/");
        driver.get("https://www.saucedemo.com/");
        //driver.findElement(By.xpath(image)).isDisplayed();
    }
}
