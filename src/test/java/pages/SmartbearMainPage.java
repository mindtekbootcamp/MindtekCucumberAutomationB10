package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class SmartbearMainPage {

    WebDriver driver;

    public SmartbearMainPage (){
        driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Order")
    public WebElement orderTab;

    @FindBy(xpath = "//table[@id='ctl00_MainContent_orderGrid']//tr[2]/td[2]")
    public WebElement firstRowCustomerName;
}
