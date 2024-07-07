package base;

import helpers.AppHelper;
import io.qameta.allure.Step;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.BrowserManager;
import helpers.SeleniumElementsHelper;
import utils.ThreadLogger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class TestBase {

    protected WebDriver driver;
    protected WebDriverWait driverWait;
    protected SeleniumElementsHelper seleniumElementsHelper;

    private static final int WEB_DRIVER_TIMEOUT = 150;
    protected boolean disableWebSecurity = false;

    private static final Logger LOG = ThreadLogger.getLogger(TestBase.class);

    private List<Boolean> testStepsResults = new ArrayList<>();
    private int testStepCounter;

    private Map<Class, Object> allHelpers = new HashMap<>();

    @BeforeMethod(alwaysRun = true)
    protected void setupBeforeTest() throws Exception {
        testStepCounter = 0;  // Reset step counter before each test method
        LOG.info("Setup before the test has just started.");
        setupDriver(BrowserManager.DriverType.LOCAL);  // Przykładowe użycie SAUCELABS, można zmienić w zależności od potrzeb
        setupHelpers();
        LOG.info("Setup done.");
    }

    @AfterMethod(alwaysRun = true)
    protected void cleanUpAfterTest(ITestResult testResult) {
        LOG.info("Cleaning up after test.");
        testDone("TEST DONE", !testResult.isSuccess());  // Zamyka ostatni test
        if (driver != null) {
            driver.quit();
        }
        LOG.info("Cleanup done.");
    }

    protected void setupDriver(BrowserManager.DriverType driverType) throws Exception {
        BrowserManager browserManager = new BrowserManager();
        driver = browserManager.getDriver(getBrowserFromProperties(), driverType);
        configureWebDriver(driver);
        driverWait = new WebDriverWait(driver, WEB_DRIVER_TIMEOUT);
    }

    protected void configureWebDriver(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(WEB_DRIVER_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    private String getBrowserFromProperties() {
        final String defaultBrowser = "chrome";
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationConfig.properties")) {
            if (inputStream == null) {
                LOG.warn("Property file 'applicationConfig.properties' not found in the classpath; using default browser: " + defaultBrowser);
                return defaultBrowser;
            }
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error("Failed to load 'applicationConfig.properties'; using default browser: " + defaultBrowser, e);
            return defaultBrowser;
        }
        return properties.getProperty("browser", defaultBrowser);
    }

    @Step("{message}")
    protected void testStep(String message, boolean result) {
        testStepCounter++;
        testStepsResults.add(result);
        LOG.info("[STEP " + testStepCounter + "] " + message);
    }

    protected void testStep(String message) {
        testStep(message, true);
    }

    protected void testDone(String message, boolean result) {
        if (!testStepsResults.isEmpty()) {
            LOG.info("[TEST RESULT]: " + message + " - " + (result ? "PASSED" : "FAILED"));
            testStepsResults.clear();
        }
    }

    protected void testDone(String message) {
        testDone(message, true);
    }

    private void setupHelpers() {
        this.seleniumElementsHelper = new SeleniumElementsHelper(driver, driverWait);
        initializeHelpers(Arrays.asList(seleniumElementsHelper));
    }

    private void initializeHelpers(List<Object> helpers) {
        for (Object helper : helpers) {
            try {
                allHelpers.putAll(AppHelper.initAppHelpers((SeleniumElementsHelper) helper));
                injectHelpersInto(helper);
            } catch (Exception e) {
                LOG.error("Error initializing helpers: " + e.getMessage(), e);
            }
        }
    }

    private void injectHelpersInto(Object target) throws IllegalAccessException {
        for (Field field : target.getClass().getDeclaredFields()) {
            if (allHelpers.containsKey(field.getType())) {
                field.setAccessible(true);
                field.set(target, allHelpers.get(field.getType()));
            }
        }
    }
}