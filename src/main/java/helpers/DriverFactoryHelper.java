package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DriverFactoryHelper {

    private static final String CHROME_DRIVER_NAME = "chromedriver.exe";
    private static final String YANDEX_DRIVER_NAME = "yandexdriver.exe";

    public static WebDriver setupDriver(String driverType) {
        Path driversDir = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "drivers");

        if (driverType.equals("chrome")) {
            return initializeWebDriver(Paths.get(driversDir.toString(), CHROME_DRIVER_NAME).toString());
        }
        return initializeWebDriver(Paths.get(driversDir.toString(),  YANDEX_DRIVER_NAME).toString());
    }

    private static WebDriver initializeWebDriver(String pathToDriver) {
        System.setProperty("webdriver.chrome.driver", pathToDriver);
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }
}
