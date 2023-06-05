package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactoryHelper {
    private static final String DEFAULT_BROWSER_VERSION = "111.0";
    private static final String YANDEX_BROWSER_VERSION = "110.0.5481.208";
    private static final String SELENIUM_GRID_ADDRESS = "http://localhost:4444/wd/hub";

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
                return initializeRemoteWebDriver(BrowserType.CHROME, YANDEX_BROWSER_VERSION);
            }
        }
    }

    private static WebDriver initializeRemoteWebDriver(String driverType, String driverVersion) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(driverType);
        caps.setVersion(driverVersion);
        WebDriver driver = new RemoteWebDriver(new URL(SELENIUM_GRID_ADDRESS), caps);
        driver.manage().window().maximize();
        return driver;
    }
}
