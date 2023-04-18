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
            "chrome,Булки",
            "chrome,Соусы",
            "chrome,Начинки",
            "edge,Булки",
            "edge,Соусы",
            "edge,Начинки",
            "firefox,Булки",
            "firefox,Соусы",
            "firefox,Начинки",
            "yandex,Булки",
            "yandex,Соусы",
            "yandex,Начинки"
    })
    public void checkThatTheSectionNavigationIsWorkingTest(String driverType, String ingredientType) throws MalformedURLException, InterruptedException {
        driver = setupDriver(driverType);
        OrderPageObject orderPageObject = new OrderPageObject(driver);
        Double oldElementPosition = orderPageObject.clickByIngredientTypeButton(ingredientType);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        // Использовал не самый кашерный подход поскольку необходимо дождаться, пока нужный
        // ингирдиент проскролится к верхней части блока
        driver.manage().timeouts().implicitlyWait(DEFAULT_WAITING, TimeUnit.SECONDS);
        orderPageObject.waitUntilIngredientTypeWillBeChanged(ingredientType);
        Thread.sleep(300);
        assertTrue(orderPageObject.isIngredientsBlockChangedPosition(ingredientType, oldElementPosition));
    }
}
