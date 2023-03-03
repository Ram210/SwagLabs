package swagLabs_practice2;

import org.openqa.selenium.By;
import org.testng.Assert;

public class BaseClass extends  LaunchBrowser{
    private final String userNameField = "user-name";
    private final String passwordField = "password";
    private final String loginButton = "login-button";
    private final String errorMSZ = "//h3[@data-test='error']";
    String productsText = "//span[@class='title']";
    public void enterLoginDetails(String userName, String password) {
        /*Reusable Method with login details.*/
            driver.findElement(By.id(userNameField)).sendKeys(userName);
            driver.findElement(By.id(passwordField)).sendKeys(password);
            driver.findElement(By.id(loginButton)).click();
    }
    public void standardUser() {
        enterLoginDetails("standard_user", "secret_sauce");
        Assert.assertTrue(driver.findElement(By.xpath(productsText)).isDisplayed());
    }
    public void problemUser() {
        enterLoginDetails("problem_user", "secret_sauce");
        Assert.assertTrue(driver.findElement(By.xpath(productsText)).isDisplayed());
    }
    public void glitchUser() {
        enterLoginDetails("performance_glitch_user", "secret_sauce");
        Assert.assertTrue(driver.findElement(By.xpath(productsText)).isDisplayed());
    }
    public void lockedUser() {
       enterLoginDetails("locked_out_user", "secret_sauce");
    }
    public void adress(String firstName, String lastName, int postalCode) {
        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(String.valueOf(postalCode));
    }
}