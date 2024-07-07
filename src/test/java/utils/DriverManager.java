package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    private static final String DRIVER_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\";
    private static final String GRID_URL = "http://192.168.1.5:4444/wd/hub"; // Selenium Grid
    private static final String SAUCE_USERNAME = "oauth-robertoo256-ae5d4";
    private static final String SAUCE_ACCESS_KEY = "a855a789-9239-416d-a1f1-b115f727c361";

    public enum DriverType {
        LOCAL, REMOTE, SAUCELABS
    }

    public WebDriver getDriver(String browserType, DriverType driverType) throws MalformedURLException {
        switch (driverType) {
            case LOCAL:
                return initLocalDriver(browserType);
            case REMOTE:
                return initGridDriver(browserType);
            case SAUCELABS:
                return initSauceLabsDriver(browserType);
            default:
                throw new IllegalArgumentException("Unsupported driver type: " + driverType);
        }
    }

    private WebDriver initLocalDriver(String browserType) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", DRIVER_PATH + "chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
                configureChromeOptions(chromeOptions);
                return new ChromeDriver(chromeOptions);
            case "firefox":
                System.setProperty("webdriver.gecko.driver", DRIVER_PATH + "geckodriver.exe");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                configureFirefoxOptions(firefoxOptions);
                return new FirefoxDriver(firefoxOptions);
            case "edge":
                System.setProperty("webdriver.edge.driver", DRIVER_PATH + "msedgedriver.exe");
                return new EdgeDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserType);
        }
    }

    private WebDriver initGridDriver(String browserType) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browserType);
        return new RemoteWebDriver(new URL(GRID_URL), capabilities);
    }

    private WebDriver initSauceLabsDriver(String browserType) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Windows 10");
        capabilities.setCapability("browserVersion", "latest");
        capabilities.setCapability("sauce:options", getSauceOptions());

        ChromeOptions browserOptions = new ChromeOptions();
        Map<String, Object> sauceOptions = new HashMap<>();
        sauceOptions.put("username", "oauth-robertoo256-ae5d4");
        sauceOptions.put("accessKey", "a855a789-9239-416d-a1f1-b115f727c361");
        sauceOptions.put("build", "selenium-build-M1WPL");
        sauceOptions.put("name", "F.A.S.T. RothFick");
        browserOptions.setCapability("sauce:options", sauceOptions);
        return new RemoteWebDriver(new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub"), capabilities);
    }

    private DesiredCapabilities getSauceOptions() {
        DesiredCapabilities options = new DesiredCapabilities();
        options.setCapability("username", SAUCE_USERNAME);
        options.setCapability("accessKey", SAUCE_ACCESS_KEY);
        return options;
    }

    private void configureChromeOptions(ChromeOptions options) {
        options.addArguments("--start-maximized");
    }

    private void configureFirefoxOptions(FirefoxOptions options) {
        // Configure specific Firefox options here if needed
    }
}