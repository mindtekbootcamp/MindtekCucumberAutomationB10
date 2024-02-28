package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import pages.EtsyAppMainPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.List;

public class EtsyAppSteps {

    WebDriver driver = Driver.getDriver();
    EtsyAppMainPage etsyAppMainPage = new EtsyAppMainPage();

    @Given("user navigates to etsy application")
    public void user_navigates_to_etsy_application() {
        driver.get(ConfigReader.getProperty("etsyUrl"));
    }

    @When("user searches for keyword {string}")
    public void user_searches_for_keyword(String keyword) {
        etsyAppMainPage.searchBar.sendKeys(keyword + Keys.ENTER);
    }
    @Then("user validates search result contains")
    public void user_validates_search_result_contains(DataTable dataTable) {
        List<String> keywords = dataTable.asList();
        for (WebElement item : etsyAppMainPage.items){
            boolean isFound = false;
            String wordNotFound = "";
            String itemDescription = item.getText();
            for (int i=0 ; i< keywords.size() ; i++){
                if (itemDescription.toLowerCase().contains(keywords.get(i))){
                    isFound = true;
                } else {
                    wordNotFound = keywords.get(i);
                }
            }
            Assert.assertTrue("["+itemDescription+"] does not contain keyword: ["+wordNotFound+"]", isFound);
        }
    }

    @When("user selects price range over thousand dollars")
    public void user_selects_price_range_over_thousand_dollars() {
       etsyAppMainPage.allFiltersBtn.click();
       BrowserUtils.moveToElementAndClick(etsyAppMainPage.overThousandBtn);
       etsyAppMainPage.showResultsBtn.click();
    }


    @Then("user validates price range for items over {double}")
    public void user_validates_price_range_for_items_over(Double priceRange) {
        for (WebElement itemPrice : etsyAppMainPage.itemPrices){
            double priceDouble = Double.parseDouble(itemPrice.getText().replace(",", ""));
            Assert.assertTrue(priceDouble>=priceRange);
        }
    }

    @When("user clicks on {string} tab")
    public void user_clicks_on_tab(String tab) {
        switch (tab) {
            case "Shop Deals":
                etsyAppMainPage.shopDealsTab.click();
                break;
            case "Home Favorites":
                etsyAppMainPage.homeFavoritesTab.click();
                break;
            case "Fashion Finds":
                etsyAppMainPage.fashionFindsTab.click();
                break;
            case "Gift Guides":
                etsyAppMainPage.giftGuidesTab.click();
                break;
            case "Registry":
                etsyAppMainPage.registryTab.click();
                break;
        }
    }

    @Then("user validates the {string} title")
    public void user_validates_the_title(String title) {
        Assert.assertEquals("Title is incorrect", title, driver.getTitle());
    }
}
