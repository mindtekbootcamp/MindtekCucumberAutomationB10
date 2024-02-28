package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import pages.SmartbearLoginPage;
import pages.SmartbearMainPage;
import pages.SmartbearOrderPage;
import utilities.BrowserUtils;
import utilities.ConfigReader;
import utilities.Driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SmartbearSteps {

    WebDriver driver = Driver.getDriver();
    SmartbearLoginPage smartbearLoginPage = new SmartbearLoginPage();
    SmartbearMainPage smartbearMainPage = new SmartbearMainPage();
    SmartbearOrderPage smartbearOrderPage = new SmartbearOrderPage();
    List<Map<String, Object>> data = new ArrayList<>();

    @Given("user navigates to smartbear application")
    public void user_navigates_to_smartbear_application() {
        driver.get(ConfigReader.getProperty("smartbearUrl"));
    }

    @When("user logs in with username {string} and password {string}")
    public void user_logs_in_with_username_and_password(String username, String password) {
        smartbearLoginPage.usernameInput.sendKeys(username);
        smartbearLoginPage.passwordInput.sendKeys(password);
        smartbearLoginPage.loginBtn.click();
    }

    @Then("user is successfully logged in and the title is {string}")
    public void user_is_successfully_logged_in_and_the_title_is(String title) {
        Assert.assertEquals(title, driver.getTitle());
    }

    @Then("user is not logged in and sees the error message {string}")
    public void user_is_not_logged_in_and_sees_the_error_message(String errorMessage) {
        Assert.assertEquals(errorMessage, smartbearLoginPage.errorMessage.getText());
    }

    @Given("user clicks on Order tab")
    public void user_clicks_on_order_tab() {
        smartbearMainPage.orderTab.click();
    }

    @When("user selects product {string} and quantity {int}")
    public void user_selects_product_and_quantity(String product, Integer quantity) {
        BrowserUtils.selectOptionByValue(smartbearOrderPage.productDropdown, product);
        smartbearOrderPage.quantityInput.sendKeys(quantity.toString());
        smartbearOrderPage.calculateBtn.click();
    }

    @Then("user validates the price is calculated correctly for {int}")
    public void user_validates_the_price_is_calculated_correctly_for(Integer quantity) throws IOException {
        int priceInt = Integer.parseInt(smartbearOrderPage.pricePerUnit.getAttribute("value"));
        int discountInt = Integer.parseInt(smartbearOrderPage.discount.getAttribute("value"));
        int actualTotal = Integer.parseInt(smartbearOrderPage.totalAmount.getAttribute("value"));
        int expectedTotal;

        if (quantity >= 10) {
            expectedTotal = (priceInt-(priceInt * discountInt /100))*quantity;
//            expectedTotal = quantity*priceInt*(100-discountInt)/100;
        } else {
            expectedTotal = quantity * priceInt;
        }
        BrowserUtils.takeScreenshot("discount_calculator");
        Assert.assertEquals("Total amount is incorrect", expectedTotal, actualTotal);
    }

    @When("user places an order with data and validates with success message {string}")
    public void user_places_an_order_with_data_and_validates_with_success_message(String successMessage, DataTable dataTable) {
        data = dataTable.asMaps(String.class, Object.class);
        for (int i=0 ; i<data.size() ; i++){
            BrowserUtils.selectOptionByValue(smartbearOrderPage.productDropdown, data.get(i).get("PRODUCT").toString());
            smartbearOrderPage.quantityInput.sendKeys(Keys.BACK_SPACE+data.get(i).get("QUANTITY").toString());
            smartbearOrderPage.customerNameInput.sendKeys(data.get(i).get("CUSTOMER NAME").toString());
            smartbearOrderPage.streetInput.sendKeys(data.get(i).get("STREET").toString());
            smartbearOrderPage.cityInput.sendKeys(data.get(i).get("CITY").toString());
            smartbearOrderPage.stateInput.sendKeys(data.get(i).get("STATE").toString());
            smartbearOrderPage.zipInput.sendKeys(data.get(i).get("ZIP").toString());
            switch (data.get(i).get("CARD").toString()) {
                case "Visa":
                    smartbearOrderPage.visaBtn.click();
                    break;
                case "Amex":
                    smartbearOrderPage.amexBtn.click();
                    break;
                case "Master":
                    smartbearOrderPage.masterCardBtn.click();
                    break;
            }
            smartbearOrderPage.cardNumInput.sendKeys(data.get(i).get("CARD NUM").toString());
            smartbearOrderPage.expDateInput.sendKeys(data.get(i).get("EXP DATE").toString());
            smartbearOrderPage.processBtn.click();
            Assert.assertEquals(successMessage, smartbearOrderPage.successMessage.getText());
            driver.navigate().refresh();
        }
    }

    @Then("user validates the created order is in the list of all orders")
    public void user_validates_the_created_order_is_in_the_list_of_all_orders() {
        smartbearOrderPage.viewOrdersTab.click();
        Assert.assertEquals(data.get(2).get("CUSTOMER NAME"), smartbearMainPage.firstRowCustomerName.getText());
    }
}
