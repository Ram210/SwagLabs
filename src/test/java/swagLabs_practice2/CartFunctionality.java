package swagLabs_practice2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CartFunctionality extends BaseClass {
    /*Product Names*/
    private final String productName1 = "Sauce Labs Backpack";
    private final String productName2 = "Sauce Labs Bike Light";
    private final String productName3 = "Sauce Labs Bolt T-Shirt";
    private final String productName4 = "Sauce Labs Fleece Jacket";

    private final String addCart = "//div[text()='%s']/ancestor::div[@class='inventory_item_description']/div[@class='pricebar']/button";
    private final String cartCount = "//span[@class='shopping_cart_badge']";
    private final String[] productsAdding = {productName1, productName2, productName3, productName4};
    private final String[] productsRemoving = {productName1, productName2};

    @BeforeMethod
    public void launchB() {
        launch("chrome");
    }

    public void addToCartCountVerification() {
            /*adding four items to cart*/
            for (String productName : productsAdding) {
                String productToBeAdded = String.format(addCart, productName);
                WebElement cart = driver.findElement(By.xpath(productToBeAdded));
               /*Getting text at the button if it is ADD TO CART it should click on add or if it is REMOVE it will click twice*/
                String verifyCartPosition = cart.getText();
                if (verifyCartPosition.equalsIgnoreCase("ADD TO CART")) {
                    driver.findElement(By.xpath(productToBeAdded)).click();
                }else {
                    driver.findElement(By.xpath(productToBeAdded)).click();
                    driver.findElement(By.xpath(productToBeAdded)).click();
                }
            }
            /*Check whether cartCount is Displayed or not*/
            driver.findElement(By.xpath(cartCount)).isDisplayed();

            String actual = driver.findElement(By.xpath(cartCount)).getText();
            System.out.println(actual);
            Assert.assertEquals(String.valueOf(productsAdding.length), actual);
    }
    public void removeFromCart() {
        for (String productName1 : productsRemoving) {
            String productToBeRemoved = String.format(addCart, productName1);
            driver.findElement(By.xpath(productToBeRemoved)).click();
        }
        int finalp = productsAdding.length - productsRemoving.length;
        String actual = driver.findElement(By.xpath(cartCount)).getText();
        Assert.assertEquals(actual,String.valueOf(finalp));
    }
    @Test(priority = 1)
    public void cCVUsingSU() throws InterruptedException {
        standardUser();
        addToCartCountVerification();
        removeFromCart();
    }
    @Test(priority = 2)
    public void cCVUsingLU() {
        lockedUser();
        addToCartCountVerification();
    }
    @Test(priority = 3)
    public void cCVUsingPU() {
        problemUser();
        addToCartCountVerification();
    }
    @Test(priority = 4)
    public void cCVUsingGU() {
        glitchUser();
        addToCartCountVerification();
    }
    @AfterMethod
    public void close() {
        driver.close();
    }
}
