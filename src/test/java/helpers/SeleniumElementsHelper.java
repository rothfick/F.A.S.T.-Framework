package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class SeleniumElementsHelper {

    private final WebDriver driver;
    private final WebDriverWait driverWait;
    private final FluentWait<WebDriver> fluentWait;

    private static final int WEB_DRIVER_TIMEOUT = 150;
    private static final int POLLING_INTERVAL = 5;

    public SeleniumElementsHelper(WebDriver driver, WebDriverWait driverWait) {
        this.driver = driver;
        this.driverWait = driverWait;
        this.fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(WEB_DRIVER_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(POLLING_INTERVAL))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class);
    }

    public WebElement findElement(By locator) {
        return fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void click(By locator) {
        WebElement element = findElement(locator);
        fluentWait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void sendKeys(By locator, String text) {
        WebElement element = findElement(locator);
        fluentWait.until(ExpectedConditions.elementToBeClickable(element)).clear();
        element.sendKeys(text);
    }

    public void clearAndSendKeys(By locator, String text) {
        WebElement element = findElement(locator);
        fluentWait.until(ExpectedConditions.elementToBeClickable(element)).clear();
        element.sendKeys(text);
    }

    public void waitForElementToBeClickable(By locator) {
        fluentWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForVisibilityOfElement(By locator) {
        fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean isElementPresent(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty();
    }

    public String getElementText(By locator) {
        return findElement(locator).getText();
    }

    public String getElementAttribute(By locator, String attribute) {
        return findElement(locator).getAttribute(attribute);
    }

    public void waitForElementToDisappear(By locator) {
        fluentWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void scrollToElement(By locator) {
        WebElement element = findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void highlightElement(By locator) {
        WebElement element = findElement(locator);
        String originalStyle = element.getAttribute("style");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
        try {
            fluentWait.until(ExpectedConditions.stalenessOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
        js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element);
    }

    public void executeJavaScript(String script, Object... args) {
        ((JavascriptExecutor) driver).executeScript(script, args);
    }

    public void selectDropdownByVisibleText(By locator, String text) {
        WebElement dropdownElement = findElement(locator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(text);
    }

    public void selectDropdownByValue(By locator, String value) {
        WebElement dropdownElement = findElement(locator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByValue(value);
    }

    public void selectDropdownByIndex(By locator, int index) {
        WebElement dropdownElement = findElement(locator);
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(index);
    }

    public void moveToElement(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void doubleClick(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
    }

    public void rightClick(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.contextClick(element).perform();
    }

    public void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement sourceElement = findElement(sourceLocator);
        WebElement targetElement = findElement(targetLocator);
        Actions actions = new Actions(driver);
        actions.dragAndDrop(sourceElement, targetElement).perform();
    }

    public void switchToFrame(By frameLocator) {
        WebElement frameElement = findElement(frameLocator);
        driver.switchTo().frame(frameElement);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void acceptAlert() {
        fluentWait.until(ExpectedConditions.alertIsPresent()).accept();
    }

    public void dismissAlert() {
        fluentWait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }

    public void clickAndHold(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.clickAndHold(element).perform();
    }

    public void releaseClick(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.release(element).perform();
    }

    public void moveByOffset(int xOffset, int yOffset) {
        Actions actions = new Actions(driver);
        actions.moveByOffset(xOffset, yOffset).perform();
    }

    public void keyDown(By locator, CharSequence key) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.keyDown(element, key).perform();
    }

    public void keyUp(By locator, CharSequence key) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.keyUp(element, key).perform();
    }

    public void moveToElementAndClick(By locator) {
        WebElement element = findElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }

    public void clickAtCoordinates(int x, int y) {
        Actions actions = new Actions(driver);
        actions.moveByOffset(x, y).click().perform();
    }

    public void scrollDown(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0," + pixels + ")");
    }

    public void scrollUp(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,-" + pixels + ")");
    }

    public void scrollToEndOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToTopOfPage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");
    }

    public void highlightElementAndClick(By locator) {
        WebElement element = findElement(locator);
        String originalStyle = element.getAttribute("style");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid red;');", element);
        try {
            fluentWait.until(ExpectedConditions.stalenessOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
        js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element);
        element.click();
    }

    public void waitForPageLoad() {
        fluentWait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void clickElementByJS(By locator) {
        WebElement element = findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void setAttributeByJS(By locator, String attribute, String value) {
        WebElement element = findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attribute, value);
    }

    public void removeAttributeByJS(By locator, String attribute) {
        WebElement element = findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].removeAttribute(arguments[1]);", element, attribute);
    }

    public Object executeScript(String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script, args);
    }

    public void navigateToURLByJS(String url) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.location.href='" + url + "'");
    }

    public void refreshPageByJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(0)");
    }

    public void setElementTextByJS(By locator, String text) {
        WebElement element = findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + text + "';", element);
    }

    public String getElementTextByJS(By locator) {
        WebElement element = findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].innerText || arguments[0].textContent;", element);
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void waitForAlertToBePresent() {
        fluentWait.until(ExpectedConditions.alertIsPresent());
    }

    public void waitForElementToBeVisible(By locator) {
        fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}