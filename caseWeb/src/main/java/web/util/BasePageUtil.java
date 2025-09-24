package web.util;

import org.openqa.selenium.*;
import web.base.BaseTest;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class BasePageUtil extends BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePageUtil(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    final private Integer timeout = 10;
    final static Logger logger = Logger.getLogger(BasePageUtil.class);

    public WebElement findElement(By selector) {
        try {
            logger.info("findElement method called:  finding " + selector);
            return wait.until(ExpectedConditions.elementToBeClickable(selector));
        } catch (Exception ex) {
            logger.error(selector + " element can not find!");
            throw ex;
        }
    }

    public List<WebElement> findElements(By selector) {
        try {
            logger.info("findElements method called:  finding" + selector);
            List<WebElement> elements = driver.findElements(selector);
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
            return elements;
        } catch (Exception ex) {
            logger.error(selector + " elements can not find!");
            throw ex;
        }
    }

    public void waitUntilElementVisible(By selector) {
        logger.info("waitUntilElementVisible method called: " + selector);
        wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
    }

    public void clickElement(By selector) {
        try {
            logger.info("clickElement method called:  clicking " + selector);
            wait.until(ExpectedConditions.elementToBeClickable(selector)).click();
        } catch (Exception ex) {
            logger.error(selector + " element can not clicked!");
            throw ex;
        }
    }

    public void sendKeys(By selector, String text) {
        try {
            logger.info("sendKeys method called: sending " + text + " to " + selector + " element");
            findElement(selector).sendKeys(text);
        } catch (Exception ex) {
            logger.error("Can not send keys to " + selector + " element!");
            throw ex;
        }
    }

    public void sendKeyCode(By selector, Keys text) {
        try {
            logger.info("sendKeyCode method called: sending " + text + " to " + selector + " element");
            findElement(selector).sendKeys(text);
        } catch (Exception ex) {
            logger.error("Can not send keycode to " + selector + " element!");
            throw ex;
        }
    }

    public String getText(By selector) {
        try {
            logger.info("getText method called: getting text " + selector);
            return findElement(selector).getText();
        } catch (Exception ex) {
            logger.error("Can not get text from " + selector + " !");
            throw ex;
        }
    }

    public String getElementAttribute(By selector, String attribute) {
        try {
            logger.info("getElementAttribute method called: getting attribute " + selector);
            return findElement(selector).getAttribute(attribute);
        } catch (Exception ex) {
            logger.error("Can not get attribute from " + selector + " !");
            throw ex;
        }
    }

    public boolean isElementDisplayed(By selector) {
        try {
            logger.info("isElementDisplayed method called: " + selector);
            return findElement(selector).isDisplayed();
        } catch (Exception ex) {
            logger.error("Can not displayed " + selector + " !");
            return false;
        }
    }

    public boolean isPageTitleContains(String expected) {
        try {
            Log.info("Page Title: " + driver.getTitle());
            return driver.getTitle().contains(expected);
        } catch (Exception ex) {
            return false;
        }
    }

    public void scrollIntoView(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        sleep(5);
    }

    public void sleep(Integer seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
