package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class ElarAppLoginPage {

    WebDriver driver;

    public ElarAppLoginPage(){
        driver= Driver.getDriver();
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//input[@name='login']")
    public WebElement username;

    @FindBy(xpath = "//input[@name='password']")
    public WebElement password;

    @FindBy(tagName = "button")
    public WebElement loginButton;

    public void loginIn(){
        username.sendKeys("student1@mindtekbootcamp.com");
        password.sendKeys("mindtek109");
        loginButton.click();
    }


}
