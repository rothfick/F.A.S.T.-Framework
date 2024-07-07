package tests.UI;

import base.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


public class DemoQATests extends TestBase {

    @Test
    @Description("Verify the login functionality works correctly")
    @Epic("Basic UI Tests")
    @Feature("Login")
    public void testLoginFunctionality() {
        try {
            testStep("Navigating to home page");
            driver.get("https://demoqa.com");
            seleniumElementsHelper.waitForPageLoad();

            testStep("Clicking on 'Book Store Application' tile");
            seleniumElementsHelper.scrollToElement(By.xpath("//h5[text()='Book Store Application']"));
            seleniumElementsHelper.clickSafely(By.xpath("//h5[text()='Book Store Application']"));
            seleniumElementsHelper.waitForPageLoad();

            testStep("Clicking on 'Login' section");
            seleniumElementsHelper.clickSafely(By.xpath("//span[text()='Login']"));
            seleniumElementsHelper.waitForPageLoad();

            testStep("Entering username");
            seleniumElementsHelper.sendKeys(By.id("userName"), "testUser");

            testStep("Entering password");
            seleniumElementsHelper.sendKeys(By.id("password"), "Test@1234");

            testStep("Clicking on login button");
            seleniumElementsHelper.clickSafely(By.id("login"));
            seleniumElementsHelper.waitForPageLoad();

            testStep("Verifying user is logged in");
            seleniumElementsHelper.waitForElementToBeVisible(By.id("userName-label"));
            if (!seleniumElementsHelper.isElementPresent(By.id("userName-label"))) {
                String errorMessage = seleniumElementsHelper.getElementText(By.id("name"));
                assert false : "User is not logged in. Error message: " + errorMessage;
            }

            testStep("Verifying 'Log out' button is visible");
            seleniumElementsHelper.waitForElementToBeVisible(By.id("submit")); // Assuming "submit" is the id for Log out button
            if (!seleniumElementsHelper.isElementPresent(By.id("submit"))) {
                assert false : "Log out button is not visible after login.";
            }

            testDone("Login functionality test completed");
        } catch (Exception e) {
            e.printStackTrace();
            assert false : "Test failed due to exception: " + e.getMessage();
        }
    }

    @Test
    @Description("Verify that button click changes the state")
    @Epic("Basic UI Tests")
    @Feature("Button Click")
    public void testButtonClick() {
        testStep("Navigating to home page");
        driver.get("https://demoqa.com");
        seleniumElementsHelper.waitForPageLoad();
        testStep("Clicking on 'Elements' tile");
        seleniumElementsHelper.clickSafely(By.xpath("//h5[text()='Elements']"));
        seleniumElementsHelper.waitForPageLoad();
        testStep("Clicking on 'Buttons' section");
        seleniumElementsHelper.clickSafely(By.xpath("//span[text()='Buttons']"));
        seleniumElementsHelper.waitForPageLoad();
        testStep("Clicking on a button");
        seleniumElementsHelper.clickSafely(By.xpath("//button[text()='Click Me']"));
        testStep("Verifying the button has been clicked");
        assert seleniumElementsHelper.isElementPresent(By.id("dynamicClickMessage")) : "Button was not clicked";
        testDone("Button click test completed");
    }

    @Test
    @Description("Test scrolling on the page")
    @Epic("Basic UI Tests")
    @Feature("Scrolling")
    public void testScrollingFunctionality() {
        testStep("Navigating to home page");
        driver.get("https://demoqa.com");
        seleniumElementsHelper.waitForPageLoad();
        testStep("Clicking on 'Elements' tile");
        seleniumElementsHelper.clickSafely(By.xpath("//h5[text()='Elements']"));
        seleniumElementsHelper.waitForPageLoad();
        testStep("Clicking on 'Links' section");
        seleniumElementsHelper.clickSafely(By.xpath("//span[text()='Links']"));
        seleniumElementsHelper.waitForPageLoad();
        testStep("Scrolling to the bottom of the page");
        seleniumElementsHelper.scrollToEndOfPage();
        seleniumElementsHelper.waitForElementToBeVisible(By.id("fixedban"));
        testStep("Verifying the footer is visible");
        assert seleniumElementsHelper.isElementPresent(By.id("fixedban")) : "Footer is not visible after scrolling";
        testDone("Scrolling functionality test completed");
    }

    @Test
    @Description("Verify the dropdown functionality works correctly")
    @Epic("Basic UI Tests")
    @Feature("Dropdown")
    public void testDropdownFunctionality() {
        testStep("Navigating to home page");
        driver.get("https://demoqa.com");
        seleniumElementsHelper.waitForPageLoad();
        testStep("Clicking on 'Widgets' tile");
        seleniumElementsHelper.clickSafely(By.xpath("//h5[text()='Widgets']"));
        seleniumElementsHelper.waitForPageLoad();
        testStep("Clicking on 'Select Menu' section");
        seleniumElementsHelper.scrollToElement(By.xpath("//span[text()='Select Menu']"));
        seleniumElementsHelper.clickSafely(By.xpath("//span[text()='Select Menu']"));
        seleniumElementsHelper.waitForPageLoad();
        testStep("Selecting an option from dropdown");
        seleniumElementsHelper.selectDropdownByVisibleText(By.id("oldSelectMenu"), "Blue");
        testStep("Verifying the option is selected");
        assert seleniumElementsHelper.getElementText(By.id("oldSelectMenu")).contains("Blue") : "Option is not selected";
        testDone("Dropdown functionality test completed");
    }

    @Test
    @Description("Test alert functionality")
    @Epic("Basic UI Tests")
    @Feature("Alerts")
    public void testAlertFunctionality() {
        testStep("Navigating to home page");
        driver.get("https://demoqa.com");
        seleniumElementsHelper.waitForPageLoad();
        testStep("Clicking on 'Alerts, Frame & Windows' tile");
        seleniumElementsHelper.clickSafely(By.xpath("//h5[text()='Alerts, Frame & Windows']"));
        seleniumElementsHelper.waitForPageLoad();
        testStep("Clicking on 'Alerts' section");
        seleniumElementsHelper.clickSafely(By.xpath("//span[text()='Alerts']"));
        seleniumElementsHelper.waitForPageLoad();
        testStep("Triggering an alert");
        seleniumElementsHelper.clickSafely(By.id("alertButton"));
        seleniumElementsHelper.waitForAlertToBePresent();
        seleniumElementsHelper.acceptAlert();
        testStep("Verifying alert was accepted");
        assert !seleniumElementsHelper.isAlertPresent() : "Alert was not accepted";
        testDone("Alert functionality test completed");
    }

    @Test
    @Description("Verify multi-section form handling")
    @Epic("Advanced UI Tests")
    @Feature("Form Handling")
    public void testMultiSectionFormHandling() {
        testStep("Navigating to home page");
        driver.get("https://demoqa.com");
        seleniumElementsHelper.waitForPageLoad();

        testStep("Clicking on 'Forms' tile");
        seleniumElementsHelper.scrollToElementAndClick(By.xpath("//h5[text()='Forms']"));
        seleniumElementsHelper.waitForPageLoad();

        testStep("Clicking on 'Practice Form' section");
        seleniumElementsHelper.clickSafely(By.xpath("//span[text()='Practice Form']"));
        seleniumElementsHelper.waitForPageLoad();

        testStep("Filling out multiple sections of the form");
        seleniumElementsHelper.sendKeys(By.id("firstName"), "John");
        seleniumElementsHelper.sendKeys(By.id("lastName"), "Doe");
        seleniumElementsHelper.sendKeys(By.id("userEmail"), "john.doe@example.com");
        seleniumElementsHelper.clickSafely(By.cssSelector("label[for='gender-radio-1']"));  // Click on Male
        seleniumElementsHelper.sendKeys(By.id("userNumber"), "1234567890");

        testStep("Filling out Date of Birth");
        seleniumElementsHelper.clickSafely(By.id("dateOfBirthInput"));
        seleniumElementsHelper.waitForElementToBeVisible(By.cssSelector(".react-datepicker__day--001"));
        seleniumElementsHelper.clickSafely(By.cssSelector(".react-datepicker__day--001"));  // Click on the first day of the month

        testStep("Filling out Subjects");
        seleniumElementsHelper.sendKeys(By.id("subjectsInput"), "Maths");
        seleniumElementsHelper.sendKeys(By.id("subjectsInput"), Keys.ENTER.toString());  // Press Enter to add subject

        testStep("Selecting Hobbies");
        seleniumElementsHelper.scrollToElement(By.cssSelector("label[for='hobbies-checkbox-1']"));
        seleniumElementsHelper.waitForElementToBeClickable(By.cssSelector("label[for='hobbies-checkbox-1']"));
        seleniumElementsHelper.clickElementByJS(By.cssSelector("label[for='hobbies-checkbox-1']"));  // Sports
        seleniumElementsHelper.scrollToElement(By.cssSelector("label[for='hobbies-checkbox-3']"));
        seleniumElementsHelper.waitForElementToBeClickable(By.cssSelector("label[for='hobbies-checkbox-3']"));
        seleniumElementsHelper.clickElementByJS(By.cssSelector("label[for='hobbies-checkbox-3']"));  // Music

        testStep("Uploading Picture");
        seleniumElementsHelper.sendKeys(By.id("uploadPicture"), "C:\\Users\\dokto\\Desktop\\WP_P_1.jpg");

        // Adding an explicit wait to ensure picture upload is fully completed
        try {
            Thread.sleep(3000); // Wait for 3 seconds to ensure the picture is uploaded
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        testStep("Filling out Address");
//        seleniumElementsHelper.scrollToElement(By.id("currentAddress"));
//        seleniumElementsHelper.waitForElementToBeClickable(By.id("currentAddress"));
//        seleniumElementsHelper.sendKeys(By.id("currentAddress"), "123 Main St");
//
//        testStep("Selecting State and City");
//        seleniumElementsHelper.scrollToElement(By.id("state"));
//        seleniumElementsHelper.waitForElementToBeClickable(By.id("state"));
//        seleniumElementsHelper.clickElementByJS(By.id("state"));
//        seleniumElementsHelper.waitForElementToBeVisible(By.xpath("//div[contains(text(),'NCR')]"));
//        seleniumElementsHelper.clickElementByJS(By.xpath("//div[contains(text(),'NCR')]"));
//
//        seleniumElementsHelper.scrollToElement(By.id("city"));
//        seleniumElementsHelper.waitForElementToBeClickable(By.id("city"));
//        seleniumElementsHelper.clickElementByJS(By.id("city"));
//        seleniumElementsHelper.waitForElementToBeVisible(By.xpath("//div[contains(text(),'Delhi')]"));
//        seleniumElementsHelper.clickElementByJS(By.xpath("//div[contains(text(),'Delhi')]"));

        testStep("Verifying form submission");
        seleniumElementsHelper.waitForElementToBeVisible(By.id("example-modal-sizes-title-lg"));
        assert seleniumElementsHelper.isElementPresent(By.id("example-modal-sizes-title-lg")) : "Form not submitted";

        testStep("Verifying submission message");
        String expectedMessage = "Thanks for submitting the form";
        String actualMessage = seleniumElementsHelper.getElementText(By.id("example-modal-sizes-title-lg"));
        assert actualMessage.contains(expectedMessage) : "Unexpected submission message: " + actualMessage;

        testDone("Multi-section form handling test completed");
    }

@Test
@Description("Test handling of interactive table")
@Epic("Advanced UI Tests")
@Feature("Interactive Table")
public void testInteractiveTableFunctionality() {
    testStep("Navigating to home page");
    driver.get("https://demoqa.com");
    seleniumElementsHelper.waitForPageLoad();
    testStep("Clicking on 'Elements' tile");
    seleniumElementsHelper.clickSafely(By.xpath("//h5[text()='Elements']"));
    seleniumElementsHelper.waitForPageLoad();
    testStep("Clicking on 'Web Tables' section");
    seleniumElementsHelper.clickSafely(By.xpath("//span[text()='Web Tables']"));
    seleniumElementsHelper.waitForPageLoad();
    testStep("Adding a new record to the table");
    seleniumElementsHelper.clickSafely(By.id("addNewRecordButton"));
    seleniumElementsHelper.sendKeys(By.id("firstName"), "Alice");
    seleniumElementsHelper.sendKeys(By.id("lastName"), "Johnson");
    seleniumElementsHelper.sendKeys(By.id("userEmail"), "alice.johnson@example.com");
    seleniumElementsHelper.sendKeys(By.id("age"), "30");
    seleniumElementsHelper.sendKeys(By.id("salary"), "50000");
    seleniumElementsHelper.sendKeys(By.id("department"), "Quality Assurance");
    seleniumElementsHelper.clickSafely(By.id("submit"));
    seleniumElementsHelper.waitForPageLoad();
    testStep("Verifying record addition");
    assert seleniumElementsHelper.isElementPresent(By.xpath("//div[contains(text(), 'Alice')]")) : "Record was not added";
    testDone("Interactive table functionality test completed");
}

@Test
@Description("Test handling of dynamic loads")
@Epic("Advanced UI Tests")
@Feature("Dynamic Loads")
public void testDynamicLoadsHandling() {
    testStep("Navigating to home page");
    driver.get("https://demoqa.com");
    seleniumElementsHelper.waitForPageLoad();
    testStep("Clicking on 'Elements' tile");
    seleniumElementsHelper.clickSafely(By.xpath("//h5[text()='Elements']"));
    seleniumElementsHelper.waitForPageLoad();
    testStep("Clicking on 'Dynamic Properties' section");
    seleniumElementsHelper.scrollToElementAndClick(By.xpath("//span[text()='Dynamic Properties']"));
//    seleniumElementsHelper.click(By.xpath("//span[text()='Dynamic Properties']"));
    seleniumElementsHelper.waitForPageLoad();
    testStep("Waiting for dynamically loaded button to appear");
    seleniumElementsHelper.waitForElementToBeClickable(By.id("visibleAfter"));
    testStep("Clicking on the dynamically loaded button");
    seleniumElementsHelper.clickSafely(By.id("visibleAfter"));
    testStep("Verifying button click");
    assert seleniumElementsHelper.isElementPresent(By.id("visibleAfter")) : "Dynamically loaded button was not clickable";
    testDone("Dynamic loads handling test completed");
}

@Test
@Description("Test advanced JavaScript manipulations")
@Epic("Advanced UI Tests")
@Feature("JavaScript Manipulation")
public void testAdvancedJavaScriptManipulations() {
    testStep("Navigating to home page");
    driver.get("https://demoqa.com");
    seleniumElementsHelper.waitForPageLoad();
    testStep("Clicking on 'Elements' tile");
    seleniumElementsHelper.clickSafely(By.xpath("//h5[text()='Elements']"));
    seleniumElementsHelper.waitForPageLoad();
    testStep("Clicking on 'Buttons' section");
    seleniumElementsHelper.clickSafely(By.xpath("//span[text()='Buttons']"));
    seleniumElementsHelper.waitForPageLoad();
    testStep("Using JavaScript to perform double click");
    WebElement button = driver.findElement(By.id("doubleClickBtn"));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
    ((JavascriptExecutor) driver).executeScript("arguments[0].dispatchEvent(new MouseEvent('dblclick', {bubbles:true, cancelable:true, view:window}));", button);
    testStep("Verifying JavaScript manipulation");
    assert seleniumElementsHelper.isElementPresent(By.id("doubleClickMessage")) : "JavaScript manipulation did not trigger expected action";
    testDone("Advanced JavaScript manipulations test completed");
}

@Test
@Description("Test network error handling and recovery")
@Epic("Complex UI Tests")
@Feature("Network Errors")
public void testNetworkErrorHandling() {
    testStep("Navigating to home page");
    driver.get("https://demoqa.com");
    seleniumElementsHelper.waitForPageLoad();
    testStep("Simulating network error");
    seleniumElementsHelper.executeJavaScript("window.stop();");
    testStep("Attempting to recover from network error");
    seleniumElementsHelper.refreshPageByJS();
    seleniumElementsHelper.waitForPageLoad();
    testStep("Verifying page recovery");
    assert seleniumElementsHelper.isElementPresent(By.className("home-banner")) : "Page did not recover from network error correctly";
    testDone("Network error handling test completed");
}

@Test
@Description("Test handling of browser sessions and cookies")
@Epic("Complex UI Tests")
@Feature("Session and Cookies")
public void testSessionAndCookieHandling() {
    testStep("Navigating to home page");
    driver.get("https://demoqa.com");
    seleniumElementsHelper.waitForPageLoad();
    testStep("Setting a session cookie");
    seleniumElementsHelper.executeJavaScript("document.cookie = 'sessionTest=123456; path=/';");
    testStep("Verifying the cookie is set correctly");
    String cookies = (String) seleniumElementsHelper.executeScript("return document.cookie;");
    assert cookies.contains("sessionTest=123456") : "Session cookie was not set correctly";
    testDone("Session and cookie handling test completed");
}
}