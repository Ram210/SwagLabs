package swagLabs_practice2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class PreferenceDPDWN extends BaseClass {

    String priceBar = "//div[@class='inventory_item_description']/div[@class='pricebar']/div";

    public List<Integer> extractPrices(List<WebElement> priceElements) {
        List<Integer> prices = new ArrayList<>();
        for (WebElement element : priceElements) {
            String priceText = element.getText().replace("$", "");
            int price = Integer.parseInt(priceText);
            prices.add(price);
        }
        return prices;
    }

    public void testPriceSort(String sortValue) {
        // Navigate to the SwagLabs website
        driver.get("https://www.saucedemo.com/inventory.html");

        // Get the initial list of prices
        List<WebElement> initialPriceElements = driver.findElements(By.xpath(priceBar));
        List<Integer> initialPrices = extractPrices(initialPriceElements);

        // Select the price sort dropdown option
        Select sortDropdown = new Select(driver.findElement(By.xpath("//select[@class='product_sort_container']")));
        sortDropdown.selectByValue(sortValue);

        // Get the sorted list of prices
        List<WebElement> sortedPriceElements = driver.findElements(By.xpath(priceBar));
        List<Integer> sortedPrices = extractPrices(sortedPriceElements);

        // Verify that the prices are sorted correctly
        List<Integer> expectedPrices = new ArrayList<>(sortedPrices);
        Collections.sort(expectedPrices);
        assertEquals(sortedPrices, expectedPrices);

        // Verify that the list of prices matches the initial list
        assertEquals(sortedPrices, initialPrices);
    }

    @Test
    public void testLowToHighPriceSort() {
        testPriceSort("lohi");
    }

    @Test
    public void testHighToLowPriceSort() {
        testPriceSort("hilo");
    }

    @DataProvider(name = "invalidSortValues")
    public Object[][] getInvalidSortValues() {
        return new Object[][]{{"foo"}, {"123"}, {"lohihilo"}, {"hilofoo"}};
    }

    @Test(dataProvider = "invalidSortValues")
    public void testInvalidPriceSortValues(String sortValue) {
        // Navigate to the SwagLabs website
        driver.get("https://www.saucedemo.com/inventory.html");

        // Select an invalid price sort dropdown option
        Select sortDropdown = new Select(driver.findElement(By.xpath("//select[@class='product_sort_container']")));
        sortDropdown.selectByValue(sortValue);

        // Verify that the sort dropdown option is not selected
        String selectedOption = sortDropdown.getFirstSelectedOption().getAttribute("value");
        Assert.assertNotEquals(selectedOption, sortValue);
    }
}






