package tests;

import helpers.DriverFactoryHelper;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.CreateUserModel;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;

import static api.client.UserClient.getUserCreationResponse;
import static helpers.Constants.BASE_URL;

public class TestBase {

    protected WebDriver driver;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @After
    public void teardown() {
        driver.quit();
    }

    protected Pair<String, CreateUserModel> createTestUser() {
        CreateUserModel userShouldBeCreated = CreateUserModel.createFakeUser("");
        ValidatableResponse response = getUserCreationResponse(userShouldBeCreated);
        return new Pair<>(response.extract().path("accessToken"), userShouldBeCreated) ;
    }

    protected WebDriver setupDriver(String driverType) throws MalformedURLException {
        WebDriver driver = DriverFactoryHelper.setupDriver(driverType);
        driver.get(BASE_URL);
        return driver;
    }

    protected WebDriver setupDriver(String driverType, String urlPath) throws MalformedURLException {
        WebDriver driver = DriverFactoryHelper.setupDriver(driverType);
        driver.get(BASE_URL + urlPath);
        return driver;
    }
}
