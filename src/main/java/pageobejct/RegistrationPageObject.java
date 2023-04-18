package pageobejct;

import model.CreateUserModel;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static helpers.Constants.DEFAULT_WAITING;

public class RegistrationPageObject extends LoginBasePageObject {
    private final WebDriver driver;
    @FindBy(xpath = ".//h2[text()='Регистрация']")
    private WebElement registrationPageHeader;

    @FindBy(xpath = ".//button[text()='Зарегистрироваться']")
    private WebElement registrationButton;

    @FindBy(xpath = ".//p[text()='Некорректный пароль']")
    private WebElement incorrectPasswordError;

    private final By registrationFields = By.xpath(".//form/fieldset/div/div/input");
    public RegistrationPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickToRegistrationButton() {
        JavascriptExecutor js = ((JavascriptExecutor)driver);
        js.executeScript("arguments[0].click()", registrationButton);
    }

    public boolean isIncorrectPasswordErrorMessageDisplayed() {
        return incorrectPasswordError.isDisplayed();
    }

    public void fillForm(CreateUserModel model) {
        driver.findElements(registrationFields).get(0).sendKeys(model.getName());
        driver.findElements(registrationFields).get(0).sendKeys(Keys.TAB);
        driver.findElements(registrationFields).get(1).sendKeys(model.getEmail());
        driver.findElements(registrationFields).get(1).sendKeys(Keys.TAB);
        driver.findElements(registrationFields).get(2).sendKeys(model.getPassword());
        driver.findElements(registrationFields).get(2).sendKeys(Keys.TAB);
    }

    public void waitUntilRegistrationPageHeaderWillBeLoaded(WebDriver driver) {
        new WebDriverWait(driver, DEFAULT_WAITING)
                .until(driver1 -> registrationPageHeader.isDisplayed());
    }
}
