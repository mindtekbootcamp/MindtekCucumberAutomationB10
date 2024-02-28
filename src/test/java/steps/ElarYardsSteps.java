package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.eo.Se;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pages.ElarAppHomePage;
import pages.ElarAppLoginPage;
import pages.ElarAppYardsPage;
import utilities.ConfigReader;
import utilities.Driver;
import utilities.JDBCUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ElarYardsSteps {

    WebDriver driver= Driver.getDriver();
    ElarAppYardsPage yardsPage=new ElarAppYardsPage();
    Map<String, Object> yardData;
    String yardName;

    @Given("user navigates to elar application")
    public void user_navigates_to_elar_application() {
        driver.get("https://elarbridgelogisticsmindtek.space/");
    }

    @When("user logs in to Elar app")
    public void user_logs_in_to_elar_app() {
        ElarAppLoginPage loginPage=new ElarAppLoginPage();
        loginPage.loginIn();
    }

    @When("user clicks on Yards tab")
    public void user_clicks_on_yards_tab() {
        ElarAppHomePage homePage=new ElarAppHomePage();
        homePage.yardsTab.click();
    }

    @When("user clicks on add yard button")
    public void user_clicks_on_add_yard_button() {
        yardsPage.addYardButton.click();
    }

    @When("user creates yard with data")
    public void user_creates_yard_with_data(io.cucumber.datatable.DataTable dataTable) {
        yardData=dataTable.asMap(String.class,Object.class);
        yardName=yardData.get("Name").toString()+new Random().nextInt(100000);
        yardsPage.name.sendKeys(yardName);
        yardsPage.address.sendKeys(yardData.get("Street").toString());
        yardsPage.city.sendKeys(yardData.get("City").toString());
        Select select=new Select(yardsPage.state);
        select.selectByValue(yardData.get("State").toString());
        yardsPage.zipCode.sendKeys(yardData.get("Zip code").toString());
        yardsPage.spots.sendKeys(yardData.get("spots").toString());
        yardsPage.addButton.click();
    }

    @Then("user validates success message {string}")
    public void user_validates_success_message(String successMessage) {
        Assert.assertEquals(yardName+"\n"+successMessage,yardsPage.successMessage.getText());
    }

    @Then("user validates yard is persisted in DB")
    public void user_validates_yard_is_persisted_in_db() throws SQLException {
        JDBCUtils.establishDBConnection(
                ConfigReader.getProperty("ElarDBURL"),
                ConfigReader.getProperty("ElarDBUsername"),
                ConfigReader.getProperty("ElarDBPassword")
        );
        List<Map<String,Object>> data=JDBCUtils.executeQuery("select * from core_yard where location='"+yardName+"'");
                        //  UI      , DB
        Assert.assertEquals(yardName, data.get(0).get("location"));
        Assert.assertEquals(yardData.get("Street").toString(), data.get(0).get("address"));
        Assert.assertEquals(yardData.get("City").toString(), data.get(0).get("city"));
        Assert.assertEquals(yardData.get("State").toString(), data.get(0).get("state"));
        Assert.assertEquals(yardData.get("Zip code").toString(), data.get(0).get("zip_code"));
        Assert.assertEquals(yardData.get("spots").toString(), data.get(0).get("spots"));
    }

    @When("user goes to created yard page and clicks edit button")
    public void user_goes_to_created_yard_page_and_clicks_edit_button() {
        yardsPage.goToCurrentPageButton.click();
        yardsPage.editButton.click();
    }

    @When("user updates name with {string}")
    public void user_updates_name_with(String name) throws InterruptedException {
        Thread.sleep(5000);
        yardName=name+new Random().nextInt(100000);
        yardsPage.name.clear();
        yardsPage.name.sendKeys(yardName);
        yardsPage.saveButton.click();
    }


}













