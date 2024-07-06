package base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.BrowserManager;
import utils.DriverManager;
import utils.SeleniumElementsHelper;
import utils.ThreadLogger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestBase {

    protected WebDriver driver;
    protected WebDriverWait driverWait;
    protected SeleniumElementsHelper seleniumElementsHelper;

    private static final int WEB_DRIVER_TIMEOUT = 75;
    protected boolean disableWebSecurity = false;

    private ThreadLogger LOG = ThreadLogger.getLogger(TestBase.class);

    private List<Boolean> testStepsResults = new ArrayList<>();
    private int testStepCounter = 0;

    @BeforeMethod(alwaysRun = true)
    protected void setupBeforeTest() throws IOException {
        LOG.info("Setup before the test has just started.");
        setupDriver();
        setupHelpers();
        LOG.info("Setup done.");
    }

    @AfterMethod(alwaysRun = true)
    protected void cleanUpAfterTest(ITestResult testResult) {
        LOG.info("Cleaning up after test.");
        testDone("TEST DONE", false); // Zamyka ostatni test
        DriverManager.closeDriver();
        LOG.info("Cleanup done.");
    }

    protected void setupDriver() throws IOException {
        BrowserManager browserManager = new BrowserManager();
        driver = browserManager.getDriver(getBrowserFromProperties());
        driver.manage().timeouts().pageLoadTimeout(WEB_DRIVER_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driverWait = new WebDriverWait(driver, WEB_DRIVER_TIMEOUT);
    }

    private String getBrowserFromProperties() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationConfig.properties");
        properties.load(inputStream);
        return properties.getProperty("browser", "chrome");
    }

    private void setupHelpers() {
        seleniumElementsHelper = new SeleniumElementsHelper(driver, driverWait);
        // Inicjalizacja innych helperów tutaj
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

    private void testDone(String message, boolean result) {
        if (!testStepsResults.isEmpty()) {
            LOG.info("[TEST RESULT]: " + (result ? "PASSED" : "FAILED"));
            testStepsResults.clear();
        }
    }

    protected void testDone(String message) {
        testDone(message, true);
    }
}