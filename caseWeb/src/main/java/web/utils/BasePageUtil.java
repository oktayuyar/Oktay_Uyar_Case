package web.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import web.base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BasePageUtil extends BaseTest {

    protected WebDriverWait wait;
    protected Actions actions;

    public BasePageUtil(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    public WebElement findElement(By selector) {
        try {
            Log.info("findElement method called:  finding " + selector);
            return wait.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception e) {
            Log.error(selector + " element can not find!");
            throw e;
        }
    }

    public List<WebElement> findElements(By selector) {
        try {
            Log.info("findElements method called:  finding" + selector);
            List<WebElement> elements = driver.findElements(selector);
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
            return elements;
        } catch (Exception e) {
            Log.error(selector + " elements can not find!");
            throw e;
        }
    }

    public void hoverOverElement(By selector) {
        actions.moveToElement(findElement(selector)).perform();
        System.out.println("Hovered: " + selector);
    }

    public void waitUntilElementVisible(By selector) {
        Log.info("waitUntilElementVisible method called: " + selector);
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public void clickElement(By selector) {
        try {
            Log.info("clickElement method called:  clicking " + selector);
            wait.until(ExpectedConditions.elementToBeClickable(selector)).click();
        } catch (Exception e) {
            Log.error(selector + " element can not clicked!");
            throw e;
        }
    }

    public void sendKeys(By selector, String text) {
        try {
            Log.info("sendKeys method called: sending " + text + " to " + selector + " element");
            findElement(selector).sendKeys(text);
        } catch (Exception e) {
            Log.error("Can not send keys to " + selector + " element!");
            throw e;
        }
    }

    public void sendKeyCode(By selector, Keys text) {
        try {
            Log.info("sendKeyCode method called: sending " + text + " to " + selector + " element");
            findElement(selector).sendKeys(text);
        } catch (Exception e) {
            Log.error("Can not send keycode to " + selector + " element!");
            throw e;
        }
    }

    public String getText(By selector) {
        try {
            Log.info("getText method called: getting text " + selector);
            return findElement(selector).getText();
        } catch (Exception e) {
            Log.error("Can not get text from " + selector + " !");
            throw e;
        }
    }

    public String getTextWithIndex(By selector, int index) {
        try {
            Log.info("getTextWithIndex method called: getting text " + selector);
            String text = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(selector)).get(index).getText();
            Log.info("Text is ---> " + text + " at " + index);
            return text;
        } catch (Exception e) {
            Log.error("Can not get text from " + selector + " !");
            throw e;
        }
    }

    public String waitForText(By selector, String text) {
        try {
            wait.until(ExpectedConditions.textToBe(selector, text));
            WebElement departmentFilter = driver.findElement(selector);
            return departmentFilter.getText();
        } catch (Exception e){
            Log.error("Can not match text wiith the " + selector + " text!");
            throw e;
        }
    }

    public String getElementAttribute(By selector, String attribute) {
        try {
            Log.info("getElementAttribute method called: getting attribute " + selector);
            return findElement(selector).getAttribute(attribute);
        } catch (Exception e) {
            Log.error("Can not get attribute from " + selector + " !");
            throw e;
        }
    }

    public boolean isElementDisplayed(By selector) {
        try {
            Log.info("isElementDisplayed method called: " + selector);
            return findElement(selector).isDisplayed();
        } catch (Exception e) {
            Log.error("Can not displayed " + selector + " !");
            return false;
        }
    }

    public boolean isPageTitleContains(String expected) {
        try {
            Log.info("Page Title: " + driver.getTitle());
            return driver.getTitle().contains(expected);
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollIntoView(By selector) {
        try {
            WebElement element = driver.findElement(selector);
            Log.info("scrollIntoView method called: " + selector);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            Log.error("Can not scroll into the " + selector + " element!");
            throw e;
        }
    }

    public Boolean selectFromDropdownWithScroll(By dropdownSelector, By optionSelector) {
        Log.info("selectFromDropdownWithScroll method called: " + dropdownSelector);
        clickElement(dropdownSelector);

        boolean found = false;
        for (int i = 0; i < 5; i++) {
            try {
                WebElement option = driver.findElement(optionSelector);
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block: 'center'});", option
                );
                wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                found = true;
                break;
            } catch (Exception ex) {
                Log.error(optionSelector + " element can not find!");
                throw ex;
            }
        }
        return found;
    }

    public String switchToNewTab() {
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        Set<String> allWindows = driver.getWindowHandles();
        String originalWindow = driver.getWindowHandle();
        allWindows.remove(originalWindow);
        String newWindow = allWindows.iterator().next();
        driver.switchTo().window(newWindow);
        return newWindow;
    }

    public void waitSeconds(Integer seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
