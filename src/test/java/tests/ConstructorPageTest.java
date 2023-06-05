package tests;

import io.qameta.allure.junit4.DisplayName;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pageobejct.OrderPageObject;
import javax.annotation.concurrent.NotThreadSafe;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import static helpers.Constants.DEFAULT_WAITING;
import static org.junit.Assert.assertTrue;

@NotThreadSafe
@RunWith(JUnitParamsRunner.class)
public class ConstructorPageTest extends TestBase {

    @Test
    @DisplayName("Check that the section navigations is working ")
    @Parameters({
            "chrome,Булки,Начинки",
            "chrome,Соусы,Булки",
            "chrome,Начинки,Булки",
            "edge,Булки,Начинки",
            "edge,Соусы,Булки",
            "edge,Начинки,Булки",
            "firefox,Булки,Начинки",
            "firefox,Соусы,Булки",
            "firefox,Начинки,Булки",
            "yandex,Булки,Начинки",
            "yandex,Соусы,Булки",
            "yandex,Начинки,Булки"
    })
    public void checkThatTheSectionNavigationIsWorkingTest(
            String driverType,
            String ingredientType,
            String elementShouldBeHidden)
            throws MalformedURLException, InterruptedException {
        driver = setupDriver(driverType);
        OrderPageObject orderPageObject = new OrderPageObject(driver);
        if (ingredientType.equals("Булки")) {
            orderPageObject.clickByIngredientTypeButton("Начинки");
            orderPageObject.waitUntilIngredientTypeWillBeChanged("Булки");
        }
        Double oldElementPosition = orderPageObject.clickByIngredientTypeButton("Начинки");
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        driver.manage().timeouts().implicitlyWait(DEFAULT_WAITING, TimeUnit.SECONDS);
        orderPageObject.clickByIngredientTypeButton(ingredientType);
        orderPageObject.waitUntilIngredientTypeWillBeChanged(elementShouldBeHidden);
        assertTrue(Double.compare(
                oldElementPosition,
                orderPageObject.getIngredientsBlockChangedPosition(ingredientType)) > 0 );
    }
}
