package pageobejct;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginBasePageObject {
    @FindBy(xpath = ".//a[@href='/login']")
    protected WebElement loginButton;

    public void clickToLoginButton(WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click()", loginButton);
    }
}
