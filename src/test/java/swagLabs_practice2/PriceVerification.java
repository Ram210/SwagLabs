package swagLabs_practice2;

import org.checkerframework.common.value.qual.StringVal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class PriceVerification extends BaseClass {
    private final String productName1 = "Sauce Labs Backpack";
   /* private final String productName2 = "Sauce Labs Bike Light";
    private final String productName3 = "Sauce Labs Bolt T-Shirt";
    private final String productName4 = "Sauce Labs Fleece Jacket";*/
    String itemPrice = "//div[text()='%s']/ancestor::div[@class='inventory_item_description']/div[@class='pricebar']/div";
    private final String PriceAtLast = "//div[@class='summary_subtotal_label']";
    String addCart = "//div[text()='%s']/ancestor::div[@class='inventory_item_description']/div[@class='pricebar']/button";
    private final String cartIcon = "//a[@class='shopping_cart_link']";
    @BeforeMethod
    public void lb1() {
    launch("chrome");
    }
    public void verifyCostAtHomePage() {
        String firstItemPrice = String.format(itemPrice, productName1);
        String firstItemPriceAtHomePage = driver.findElement(By.xpath(firstItemPrice)).getText();

        String product1ToBeAdded = String.format(addCart, productName1);
        driver.findElement(By.xpath(product1ToBeAdded)).click();
        driver.findElement(By.xpath(cartIcon)).isDisplayed();

        driver.findElement(By.xpath(cartIcon)).click();
        driver.findElement(By.id("checkout")).isDisplayed();

        /* Click on CheckOut*/
        driver.findElement(By.id("checkout")).click();
        driver.findElement(By.id("first-name")).isDisplayed();

        /*Providing some required details */
        adress("siva","sai",534237);

        /*Click on continue*/
        driver.findElement(By.id("continue")).click();
        WebElement priceAtLastPage = driver.findElement(By.xpath(PriceAtLast));
        String actual = priceAtLastPage.getText().split("Item total: ")[1];
        System.out.println(actual);
        Assert.assertEquals(actual, firstItemPriceAtHomePage);
        System.out.println("Both the prices at different places are same");
    }
    @Test(priority = 1)
    public void usingSU() {
        standardUser();
        verifyCostAtHomePage();
    }
    @Test (priority = 2)
    public void usingPU() {
        problemUser();
        verifyCostAtHomePage();
    }@Test(priority = 3)
    public void usingGU() {
        glitchUser();
        verifyCostAtHomePage();
    }
    @Test(priority = 4)
    public void usingLU() {
        lockedUser();
        verifyCostAtHomePage();
    }
    @AfterMethod
    void close() {
        driver.close();
    }
}
