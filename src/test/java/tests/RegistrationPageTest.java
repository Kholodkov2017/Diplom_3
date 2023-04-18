package tests;

import io.qameta.allure.junit4.DisplayName;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import model.CreateUserModel;
import org.javatuples.Triplet;
import org.junit.Test;
import org.junit.runner.RunWith;
import pageobejct.LoginPageObject;
import pageobejct.OrderPageObject;
import pageobejct.RegistrationPageObject;

import java.net.MalformedURLException;

import static helpers.Constants.FRONT_REG_PAGE;
import static org.junit.Assert.assertTrue;


@RunWith(JUnitParamsRunner.class)
public class RegistrationPageTest extends TestBase {
    @Test
    @Parameters({"chrome", "firefox", "edge", "yandex"})
    @DisplayName("Attempt to register new user with positive result")
    public void checkTheAbbilityToRegisterUserWithPositiveResultTest(String driverType) throws MalformedURLException {
        CreateUserModel userShouldBeCreated = CreateUserModel.createFakeUser("");

        driver = setupDriver(driverType, FRONT_REG_PAGE);

        RegistrationPageObject registrationPageObject = new RegistrationPageObject(driver);
        OrderPageObject orderPageObject = new OrderPageObject(driver);
        LoginPageObject loginPageObject = new LoginPageObject(driver);

        registrationPageObject.waitUntilRegistrationPageHeaderWillBeLoaded(driver);

        registrationPageObject.fillForm(userShouldBeCreated);

        registrationPageObject.clickToRegistrationButton();

        loginPageObject.waitUntilLoginPageHeaderWillBeLoaded();

        loginPageObject.fillLoginForm(userShouldBeCreated.getEmail(), userShouldBeCreated.getPassword());

        loginPageObject.clickToLoginButton();

        orderPageObject.waitUntilMainPageHeaderWillBeLoaded();

        String token = orderPageObject.getAccessTokenFromLocalStorage(driver);

        assertTrue(!token.isBlank() && !token.isEmpty());

    }

    @Test
    @Parameters({"chrome, pass", "firefox, pass", "edge, pass", "yandex, pass"})
    @DisplayName("Attempt to fill registration form with incorrect password")
    public void attemptToFillRegistrationFormWithIncorrectPasswordErrorMessageWrongPasswordShouldBeDisplayedTest(
            String driverType, String password) throws MalformedURLException {
        CreateUserModel userShouldBeCreated = CreateUserModel.createFakeUser(new Triplet<>(null, null, password));

        driver = setupDriver(driverType, FRONT_REG_PAGE);

        RegistrationPageObject registrationPageObject = new RegistrationPageObject(driver);

        registrationPageObject.waitUntilRegistrationPageHeaderWillBeLoaded(driver);

        registrationPageObject.fillForm(userShouldBeCreated);

        assertTrue(registrationPageObject.isIncorrectPasswordErrorMessageDisplayed());
    }
}
