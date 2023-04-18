package pageobejct;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageObject extends LoginBasePageObject {

    private final WebDriver driver;
    public LoginPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
    @FindBy(xpath = ".//h2[text()='Вход']")
    private WebElement loginPageHeader;

    @FindBy(xpath = ".//input[@name='name']")
    private WebElement emailInput;

    @FindBy(xpath = ".//input[@name='Пароль']")
    private WebElement passwordInput;

    @FindBy(xpath = ".//button[text()='Войти']")
    private WebElement loginButton;

    public void clickToLoginButton() {
        JavascriptExecutor js = ((JavascriptExecutor)driver);
        js.executeScript("arguments[0].click()", loginButton);
    }

    public void waitUntilLoginPageHeaderWillBeLoaded() {
        new WebDriverWait(driver, 3)
                .until(ExpectedConditions.visibilityOf(loginPageHeader));
    }
    private void setEmail(String email) {
        emailInput.sendKeys(email);
    }

    private void setPassword(String password) {
        passwordInput.sendKeys(password);
    }
    
    public void fillLoginForm(String email, String password) {
        setEmail(email);
        setPassword(password);
    }

}
