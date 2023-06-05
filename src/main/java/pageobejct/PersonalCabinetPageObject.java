package pageobejct;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static helpers.Constants.DEFAULT_WAITING;

public class PersonalCabinetPageObject {
    private final WebDriver driver;

    public PersonalCabinetPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = ".//a[@href='/account/profile']")
    private WebElement profileButton;

    @FindBy(xpath = ".//div[contains(@class,'logo')]/a[@href='/']")
    private WebElement stellarBurgerLogoLink;

    @FindBy(xpath = ".//button[text()='Выход']")
    private WebElement logoutButton;

    public void waitUntilProfileButtonWillBeLoaded() {
        new WebDriverWait(driver, DEFAULT_WAITING)
                .until(ExpectedConditions.visibilityOf(profileButton));
    }

    public boolean isProfileButtonDisplayed() {
        return profileButton.isDisplayed();
    }

    public void clickToStellarBurgerLogo() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click()", stellarBurgerLogoLink);
    }

    public void clickToLogoutButton() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click()", logoutButton);
    }
}
