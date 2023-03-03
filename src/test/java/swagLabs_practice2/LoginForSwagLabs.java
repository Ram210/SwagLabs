package swagLabs_practice2;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class LoginForSwagLabs extends BaseClass {
    @BeforeMethod
    public void lb() {
        launch("chrome");
    }

    @Test(priority = 1)
    public void standardUserTest() {
        standardUser();
    }
    @Test(priority = 2)
    public void glitchUserTest() {
        glitchUser();
    }
    @Test(priority = 3)
    public void problemUserTest() {
        problemUser();
    }
    @Test(priority = 4)
    public void lockedUserTest() {
        lockedUser();
    }
    @AfterMethod
    void close() {
        driver.close();
    }
}
