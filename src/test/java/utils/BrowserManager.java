package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BrowserManager {

    private static final String DRIVER_PATH = System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\";

    public WebDriver getDriver(String browserFromProperties) {
        switch (browserFromProperties.toLowerCase()) {
            case "chrome":
                return initChromeDriver();
            case "firefox":
                return initFirefoxDriver();
            case "edge":
                return initEdgeDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserFromProperties);
        }
    }

    private WebDriver initChromeDriver() {
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH + "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        configureChromeOptions(options);
        return new ChromeDriver(options);
    }

    private WebDriver initFirefoxDriver() {
        System.setProperty("webdriver.gecko.driver", DRIVER_PATH + "geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        configureFirefoxOptions(options);
        return new FirefoxDriver(options);
    }

    private WebDriver initEdgeDriver() {
        System.setProperty("webdriver.edge.driver", DRIVER_PATH + "msedgedriver.exe");
        return new EdgeDriver(); // EdgeOptions can be configured similarly
    }

    private void configureChromeOptions(ChromeOptions options) {
        options.addArguments("--start-maximized");
        // Add other Chrome-specific options here
    }

    private void configureFirefoxOptions(FirefoxOptions options) {
        // Configure Firefox-specific options here
    }
}