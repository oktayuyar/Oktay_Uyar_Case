package web.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import web.util.BasePageUtil;

public class CareersPage extends BasePageUtil {

    private By demoButton = By.linkText("Get a Demo");
    private By insiderLogo = By.cssSelector("img[alt='insider_logo']");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    @Step("Check page title contains '{expected}' ")
    public void checkPageTitle(String expected) {
        Assert.assertTrue(isPageTitleContains(expected));
    }

    @Step("Check careers page opened")
    public void checkCareersPageOpened() {
        Assert.assertTrue(isElementDisplayed(insiderLogo),"Insider logo is not present");
        Assert.assertTrue(isElementDisplayed(demoButton),"Demo button is not present");
    }
}
