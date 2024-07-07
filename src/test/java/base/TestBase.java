package base;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import helpers.AppHelper;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.specification.RequestSpecification;
import lombok.NonNull;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverManager;
import helpers.SeleniumElementsHelper;
import utils.ThreadLogger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class TestBase extends TestWithHelpers{

    protected WebDriver driver;
    protected WebDriverWait driverWait;
    protected SeleniumElementsHelper seleniumElementsHelper;

    private static final int WEB_DRIVER_TIMEOUT = 150;
    protected boolean disableWebSecurity = false;

    private static final Logger LOG = ThreadLogger.getLogger(TestBase.class);

    private List<Boolean> testStepsResults = new ArrayList<>();
    private int testStepCounter;

    private Map<Class, Object> allHelpers = new HashMap<>();

    protected RequestSpecification requestSpec;
    protected WireMockServer wireMockServer;


    @BeforeMethod(alwaysRun = true)
    protected void setupBeforeTest(ITestContext context) throws Exception {
        testStepCounter = 0; // Reset step counter before each test method
        LOG.info("Setup before the test has just started.");

        boolean useRestAssured = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("useRestAssured"));
        boolean wireMockEnabled = Boolean.parseBoolean(context.getCurrentXmlTest().getParameter("wireMockEnabled"));

        if (useRestAssured) {
            setupRestAssured();
            LOG.info("RestAssured setup completed.");
        } else if (wireMockEnabled) {
            setupWireMock();
        }else {
            setupDriver(DriverManager.DriverType.LOCAL);
            setupHelpers();
            LOG.info("Selenium UI setup completed.");
        }

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

    protected void setupRestAssured() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        requestSpec = RestAssured.given().contentType("application/json");
        // Tutaj można dodać więcej konfiguracji
    }

    protected void setupWireMock() {
        // Initialize the WireMock server on a dynamic port
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        wireMockServer.start();

        // Configure RestAssured to use the base URI where WireMock is running
        RestAssured.baseURI = "http://localhost:" + wireMockServer.port();
        requestSpec = RestAssured.given()
                .contentType("application/json")
                .baseUri(RestAssured.baseURI);

        LOG.info("WireMock started on port: " + wireMockServer.port());
    }

    protected void setupDriver(DriverManager.DriverType driverType) throws Exception {
        DriverManager driverManager = new DriverManager();
        driver = driverManager.getDriver(getBrowserFromProperties(), driverType);
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

    private void setupHelpers(){
        this.seleniumElementsHelper = new SeleniumElementsHelper(driver, driverWait);
        try{
            allHelpers.putAll(AppHelper.initAppHelpers(seleniumElementsHelper));
            propertyHelper.loadProperties();
            injectHelpers(this, allHelpers);

            for(Object helper : allHelpers.values()){
                injectHelpers(helper, allHelpers);
            }}catch (Exception ex){
            LOG.error(ex.getMessage());
        }
    }

    private void injectHelpers(@NonNull Object target, @NonNull Map<Class, Object> helperMap) throws IllegalAccessException {
        Class clazz = target.getClass();
        while(clazz!=null){
            for(Field field : clazz.getDeclaredFields()){
                if(helperMap.containsKey(field.getType())){
                    field.setAccessible(true);
                    field.set(target, helperMap.get(field.getType()));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }
}