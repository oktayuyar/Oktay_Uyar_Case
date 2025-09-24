package web.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.testng.Assert;
import web.util.BasePageUtil;

public class HomePage extends BasePageUtil {

    private By demoButton = By.linkText("Get a Demo");
    private By insiderLogo = By.cssSelector("img[alt='insider_logo']");
    private By companyMenu = By.xpath("//nav//a[contains(normalize-space(.), 'Company') or contains(., 'Company')]");
    private By careersLink = By.linkText("Careers");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Check page title contains '{expected}' ")
    public void checkPageTitle(String expected) {
        Assert.assertTrue(isPageTitleContains(expected));
    }

    @Step("Check home page opened")
    public void checkHomePageOpened() {
        Assert.assertTrue(isElementDisplayed(insiderLogo),"Insider logo is not present");
        Assert.assertTrue(isElementDisplayed(demoButton),"Demo button is not present");
        Assert.assertTrue(isElementDisplayed(companyMenu),"Company menu item is not present");
    }

    @Step("Open careers page")
    public void openCareersPage() {
        waitUntilElementVisible(companyMenu);
        clickElement(companyMenu);
        waitUntilElementVisible(careersLink);
        clickElement(careersLink);
    }

}
