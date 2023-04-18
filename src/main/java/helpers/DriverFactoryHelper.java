package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactoryHelper {
    private static final String DEFAULT_BROWSER_VERSION = "111.0";
    private static final String YANDEX_BROWSER_NAME = "yandexdriver";
    public static WebDriver setupDriver(String driverType) throws MalformedURLException {


        switch (driverType) {
            case "chrome": {
                return initializeRemoteWebDriver(BrowserType.CHROME, DEFAULT_BROWSER_VERSION);
            }
            case "edge": {
                return initializeRemoteWebDriver(BrowserType.EDGE, DEFAULT_BROWSER_VERSION);
            }
            case "firefox": {
                return initializeRemoteWebDriver(BrowserType.FIREFOX, DEFAULT_BROWSER_VERSION);
            }
            default: {
                ChromeOptions options = new ChromeOptions();
                options.setBinary(YANDEX_BROWSER_NAME);
                WebDriver driver = new ChromeDriver();
                driver.manage().window().maximize();
                return driver;
            }
        }
    }

    private static WebDriver initializeRemoteWebDriver(String driverType, String driverVersion) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(driverType);
        caps.setVersion(driverVersion);
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps);
        driver.manage().window().maximize();
        return driver;
    }
}
