package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import web.utils.BasePageUtil;

public class HomePage extends BasePageUtil {

    private By demoButton = By.linkText("Get a Demo");
    private By insiderLogo = By.cssSelector("img[alt='insider_logo']");
    private By companyMenu = By.xpath("//a[normalize-space(text())='Company']");
    private By careersLink = By.linkText("Careers");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void checkPageTitle(String expected) {
        Assert.assertTrue(isPageTitleContains(expected), "Page title is not contains expected value!");
    }

    public void checkHomePageOpened() {
        Assert.assertTrue(isElementDisplayed(insiderLogo),"Insider logo is not present!");
        Assert.assertTrue(isElementDisplayed(demoButton),"Demo button is not present!");
        Assert.assertTrue(isElementDisplayed(companyMenu),"Company menu item is not present!");
    }

    public void openCareersPage() {
        waitUntilElementVisible(companyMenu);
        clickElement(companyMenu);
        waitUntilElementVisible(careersLink);
        clickElement(careersLink);
    }

}
