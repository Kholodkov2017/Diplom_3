package tests;

import io.qameta.allure.junit4.DisplayName;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pageobejct.LoginPageObject;
import pageobejct.OrderPageObject;
import pageobejct.PersonalCabinetPageObject;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static helpers.Constants.FRONT_LOGIN_PAGE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(JUnitParamsRunner.class)
public class PersonalCabinetTest extends TestBase{

    @Test
    @DisplayName("Attempt to go to personal cabinet page by click on personal cabinet button on main page")
    @Parameters({"chrome", "edge", "firefox", "yandex"})
    public void checkTheAbbilityToGoToPersonalCabinetPageByClickOnPersonalCabinetButtonWithPositiveResultTest(String driverType) throws MalformedURLException {
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

        assertTrue(personalCabinetPageObject.isProfileButtonDisplayed());
    }

    @Test
    @DisplayName("Attempt to go to constructor page from personal cabinet by click to the stellar burgers logo")
    @Parameters({"chrome", "edge", "firefox", "yandex"})
    public void checkTheAbbilityToGoToTheConstructorPageByClickToTheStellarsBurgerLogoTest(String driverType) throws MalformedURLException {
        driver = setupDriver(driverType, FRONT_LOGIN_PAGE);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        OrderPageObject orderPageObject = new OrderPageObject(driver);
        LoginPageObject loginPageObject = new LoginPageObject(driver);
        PersonalCabinetPageObject personalCabinetPageObject = new PersonalCabinetPageObject(driver);

        loginPageObject.waitUntilLoginPageHeaderWillBeLoaded();

        loginPageObject.fillLoginForm(userData.getValue0().getEmail(), userData.getValue0().getPassword());

        loginPageObject.clickToLoginButton();

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();
        orderPageObject.clickByLoginToPersonalCabinetButton();

        personalCabinetPageObject.waitUntilProfileButtonWillBeLoaded();
        personalCabinetPageObject.clickToStellarBurgerLogo();

        assertTrue(orderPageObject.isMainPageHeaderDisplayed());
    }
    @Test
    @DisplayName("Attempt to go to logout from personal cabinet")
    @Parameters({"chrome", "edge", "firefox", "yandex"})
    public void checkTheAbilityToLogoutFromPersonalCabinetWithPostitveResultTest(String driverType) throws MalformedURLException {
        driver = setupDriver(driverType, FRONT_LOGIN_PAGE);

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        OrderPageObject orderPageObject = new OrderPageObject(driver);
        LoginPageObject loginPageObject = new LoginPageObject(driver);
        PersonalCabinetPageObject personalCabinetPageObject = new PersonalCabinetPageObject(driver);

        loginPageObject.waitUntilLoginPageHeaderWillBeLoaded();

        loginPageObject.fillLoginForm(userData.getValue0().getEmail(), userData.getValue0().getPassword());

        loginPageObject.clickToLoginButton();

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        orderPageObject.clickByLoginToPersonalCabinetButton();

        personalCabinetPageObject.waitUntilProfileButtonWillBeLoaded();

        personalCabinetPageObject.clickToLogoutButton();

        loginPageObject.waitUntilLoginPageHeaderWillBeLoaded();

        assertFalse(orderPageObject.isAccessTokenExistsInLocalStorage());
    }
}
