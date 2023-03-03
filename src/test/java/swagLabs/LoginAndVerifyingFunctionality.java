package swagLabs;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginAndVerifyingFunctionality {
    WebDriver driver;
    WebDriverWait wait;
    /*URL*/
    private final String Url = "https://www.saucedemo.com/";
    /*ReUsable Paths*/
    String userNameField = "user-name";
    String passwordField = "password";
    private final String loginButton = "login-button";
    private final String image = "//div[@class=\"login_logo\"]";
    private final String productsIcon = "//span[text()='Products']";
    private final String errorMSZ = "//h3[@data-test=\"error\"]";
    private final String cartCount = "//span[@class=\"shopping_cart_badge\"]";
    String itemPrice = "//div[text()='%s']/ancestor::div[@class='inventory_item_description']/div[@class='pricebar']/div";
    private final String cartIcon = "//a[@class='shopping_cart_link']";
    String addCart = "//div[text()='%s']/ancestor::div[@class='inventory_item_description']/div[@class='pricebar']/button";
    private final String PriceAtLast = "//div[@class='summary_subtotal_label']";

    /*Product Names*/
    private final String productName1 = "Sauce Labs Backpack";
    private final String productName2 = "Sauce Labs Bike Light";
    private final String productName3 = "Sauce Labs Bolt T-Shirt";
    private final String productName4 = "Sauce Labs Fleece Jacket";

    @BeforeMethod
    private void lBNavigateToPage() {
        System.setProperty("webdriver.chrome.driver", "E:\\Browsers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.navigate().to(Url);
        driver.findElement(By.xpath(image)).isDisplayed();
    }

    private void loginDetails(String userName, String password) {
        /*Reusable Method with login details.*/
        driver.findElement(By.id(userNameField)).sendKeys(userName);
        driver.findElement(By.id(passwordField)).sendKeys(password);
        driver.findElement(By.id(loginButton)).click();

        /*Verifying the element present in the HomePage and checking other functionalities.*/
        try {
            /*Home Page Verification*/
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(productsIcon)));

            /*Adding 4 Items to cart*/

            String[] productsToAdd = {productName1, productName2, productName3, productName4};
            for (String productName : productsToAdd) {
                String productToBeAdded = String.format(addCart, productName);
                driver.findElement(By.xpath(productToBeAdded)).click();
            }
            String actual = driver.findElement(By.xpath(cartCount)).getText();
            System.out.println(actual);
            Assert.assertEquals(String.valueOf(productsToAdd.length), actual);

            /*if Element not present in home page then check with error message at login page.*/
        } catch (TimeoutException time) {
            String expectedTitle = "Epic sadface: Sorry, this user has been locked out.";
            String actualTitle = driver.findElement(By.xpath(errorMSZ)).getText();
            Assert.assertEquals(actualTitle, expectedTitle);
            System.out.println(expectedTitle + "  =  " + actualTitle);
        }
    }

    @DataProvider(name = "testData")
    private Object[][] provideData() {
        return new Object[][]{
                {"standard_user", "secret_sauce"},
                {"locked_out_user", "secret_sauce"},
                {"problem_user", "secret_sauce"},
                {"performance_glitch_user","secret_sauce"},
        };
    }
    @Test(dataProvider = "testData")
    private void testMethod(String userName, String password) {
        // Test method code that uses the username and password parameters
        loginDetails(userName,password);
    }

    @Test(priority = 4)
    private void verifyingCostAtHomePageAndCart() {
        /*Login with Standard UserName and Password*/
        driver.findElement(By.id(userNameField)).sendKeys("standard_user");
        driver.findElement(By.id(passwordField)).sendKeys("secret_sauce");
        driver.findElement(By.id(loginButton)).click();

        /*Checking price at home and getting price*/
        String firstItemPrice = String.format(itemPrice, productName1);
        String firstItemPriceAtHomePage = driver.findElement(By.xpath(firstItemPrice)).getText();

        /*Click on add to cartButton*/
        String product1ToBeAdded = String.format(addCart, productName1);
        driver.findElement(By.xpath(product1ToBeAdded)).click();

        /*Click on cartIcon*/
        driver.findElement(By.xpath(cartIcon)).click();

        /* Click on CheckOut*/
        driver.findElement(By.id("checkout")).click();

        /*Providing some required details */
        driver.findElement(By.id("first-name")).sendKeys("siva");
        driver.findElement(By.id("last-name")).sendKeys("sai");
        driver.findElement(By.id("postal-code")).sendKeys("534233");

        /*Click on continue*/
        driver.findElement(By.id("continue")).click();

        /*Verifying both prices at the HomePage and LastPage*/
        WebElement priceAtLastPage = driver.findElement(By.xpath(PriceAtLast));
        String actual = priceAtLastPage.getText().split("Item total: ")[1];
        System.out.println(actual);
        Assert.assertEquals(actual, firstItemPriceAtHomePage);
        System.out.println("Both the prices at different places are same");
    }
    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}