package tests;

import io.qameta.allure.junit4.DisplayName;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pageobejct.*;

import java.util.concurrent.TimeUnit;

import static helpers.Constants.FRONT_REG_PAGE;
import static helpers.Constants.FRONT_RESTORE_PAGE;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class LoginPageTest extends TestBase {

    @Test
    @Parameters({"chrome", "yandex"})
    @DisplayName("Attempt to login user with go to account button")
    public void checkTheAbbilityToLogInUsingGoToAccounButtonWithPositiveResultTest(String driverType) {
        driver = setupDriver(driverType);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        OrderPageObject orderPageObject = new OrderPageObject(driver);
        LoginPageObject loginPageObject = new LoginPageObject(driver);

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();
        orderPageObject.clickByLoginToAccountButton();

        loginPageObject.waitUntilLoginPageHeaderWillBeLoaded();

        loginPageObject.fillLoginForm(userData.getValue0().getEmail(), userData.getValue0().getPassword());

        loginPageObject.clickToLoginButton();

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        assertTrue(orderPageObject.isAccessTokenExistsInLocalStorage());
    }

    @Test
    @DisplayName("Attempt to login user with personal cabinet button")
    @Parameters({"chrome", "yandex"})
    public void checkTheAbbilityToLogInUsingWithPersonalCabinetButtonWithPositiveResultTest(String driverType) {
        driver = setupDriver(driverType);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        OrderPageObject orderPageObject = new OrderPageObject(driver);
        LoginPageObject loginPageObject = new LoginPageObject(driver);

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        orderPageObject.clickByLoginToPersonalCabinetButton();

        loginPageObject.waitUntilLoginPageHeaderWillBeLoaded();

        loginPageObject.fillLoginForm(userData.getValue0().getEmail(), userData.getValue0().getPassword());

        loginPageObject.clickToLoginButton();

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        assertTrue(orderPageObject.isAccessTokenExistsInLocalStorage());
    }

    @Test
    @DisplayName("Attempt to login user with login button on registration page")
    @Parameters({"chrome", "yandex"})
    public void checkTheAbbilityToLogInUsingLoginButtonOnRegistrationPageWithPositiveResultTest(String driverType) {
        driver = setupDriver(driverType, FRONT_REG_PAGE);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        OrderPageObject orderPageObject = new OrderPageObject(driver);
        RegistrationPageObject registrationPageObject = new RegistrationPageObject(driver);
        LoginPageObject loginPageObject = new LoginPageObject(driver);

        registrationPageObject.waitUntilRegistrationPageHeaderWillBeLoaded(driver);

        registrationPageObject.clickToLoginButton(driver);

        loginPageObject.waitUntilLoginPageHeaderWillBeLoaded();

        loginPageObject.fillLoginForm(userData.getValue0().getEmail(), userData.getValue0().getPassword());

        loginPageObject.clickToLoginButton();

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        assertTrue(orderPageObject.isAccessTokenExistsInLocalStorage());
    }

    @Test
    @DisplayName("Attempt to login user with login button on restore password page")
    @Parameters({"chrome", "yandex"})
    public void checkTheAbbilityToLogInUsingLoginButtonOnRestorePsswordPageWithPositiveResultTest(String driverType) {
        driver = setupDriver(driverType, FRONT_RESTORE_PAGE);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        OrderPageObject orderPageObject = new OrderPageObject(driver);
        RestorePasswordPageObject restorePasswordPageObject = new RestorePasswordPageObject(driver);
        LoginPageObject loginPageObject = new LoginPageObject(driver);

        restorePasswordPageObject.waitUntilRestorePageHeaderWillBeLoaded(driver);

        restorePasswordPageObject.clickToLoginButton(driver);

        loginPageObject.waitUntilLoginPageHeaderWillBeLoaded();

        loginPageObject.fillLoginForm(userData.getValue0().getEmail(), userData.getValue0().getPassword());

        loginPageObject.clickToLoginButton();

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        assertTrue(orderPageObject.isAccessTokenExistsInLocalStorage());
    }

    @Test
    @DisplayName("Attempt to login user with login button on restore password page")
    @Parameters({"chrome", "yandex"})
    public void checkTheAbbilityToGoToPersonalCabinetPageByClickOnPersonalCabinetButtonWithPositiveResultTest(String driverType) {
        driver = setupDriver(driverType);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        OrderPageObject orderPageObject = new OrderPageObject(driver);
        LoginPageObject loginPageObject = new LoginPageObject(driver);
        PersonalCabinetPageObject personalCabinetPageObject = new PersonalCabinetPageObject(driver);

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        orderPageObject.clickByLoginToAccountButton();

        loginPageObject.waitUntilLoginPageHeaderWillBeLoaded();

        loginPageObject.fillLoginForm(userData.getValue0().getEmail(), userData.getValue0().getPassword());

        loginPageObject.clickToLoginButton();

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        orderPageObject.clickByLoginToPersonalCabinetButton();

        personalCabinetPageObject.waitUntilProfileButtonWillBeLoaded();

        personalCabinetPageObject.clickToStellarBurgerLogo();

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        assertTrue(orderPageObject.isMainPageHeaderDisplayed());
    }
}
