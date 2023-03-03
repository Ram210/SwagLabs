package swagLabs_practice2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class LaunchBrowser {
    WebDriver driver;
    private final String Url = "https://www.saucedemo.com/";
    private final String LogoEle = "//div[@class='login_logo']";
    public void launch(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "E:\\Browsers\\chromedriver.exe");
            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "E:\\Browsers\\geckodriver.exe");
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "E:\\Browsers\\msedgedriver.exe");
            driver = new EdgeDriver();
        }
        driver.navigate().to(Url);
        driver.findElement(By.xpath(LogoEle)).isDisplayed();
        driver.manage().window().maximize();
    }
}
