package pageobejct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static helpers.Constants.DEFAULT_WAITING;

public class RestorePasswordPageObject extends LoginBasePageObject {
    @FindBy(xpath = ".//h2[text()='Восстановление пароля']")
    private WebElement restorePasswordPageHeader;
    public RestorePasswordPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void waitUntilRestorePageHeaderWillBeLoaded(WebDriver driver) {
        new WebDriverWait(driver, DEFAULT_WAITING)
                .until(driver1 -> restorePasswordPageHeader.isDisplayed());
    }
}
