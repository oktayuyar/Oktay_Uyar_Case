package web.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import web.util.BasePageUtil;

public class CareersPage extends BasePageUtil {

    private By demoButton = By.linkText("Get a Demo");
    private By insiderLogo = By.cssSelector("img[alt='insider_logo']");
    private By teamsBlock = By.cssSelector("h3.category-title-media");
    private By teamCustomerSuccess = By.cssSelector("a[href$=\"/customer-success/\"]");
    private By seeAllTeamsButton = By.cssSelector("a.loadmore");
    private By locationsBlock = By.cssSelector("h3.category-title-media.ml-0");
    private By lifeAtInsiderBlock = By.xpath("//h2[contains(@class,'elementor-size-default') and contains(text(),'Life at Insider')]");
    private String qualityAssuranceText = "quality assurance";
    private By seeAllQAJobsButton = By.cssSelector("a[href*=\"qualityassurance\"]");

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
        scrollIntoView(teamsBlock);
        Assert.assertTrue(isElementDisplayed(teamsBlock),"Find your calling block is not present");
        Assert.assertTrue(getText(teamsBlock).contains("Find your calling"),"Text is not match");
        scrollIntoView(teamCustomerSuccess);
        Assert.assertTrue(isElementDisplayed(teamCustomerSuccess),"Customer Sucsess team is not present");
        scrollIntoView(seeAllTeamsButton);
        Assert.assertTrue(isElementDisplayed(seeAllTeamsButton),"See all teams button is not present");
        scrollIntoView(locationsBlock);
        Assert.assertTrue(isElementDisplayed(locationsBlock),"Our locations block is not present");
        Assert.assertTrue(getText(locationsBlock).contains("Our Locations"),"Text is not match");
        scrollIntoView(lifeAtInsiderBlock);
        Assert.assertTrue(isElementDisplayed(lifeAtInsiderBlock),"Life at Insider block is not present");
    }

    @Step("Check quality assurance careers page opened")
    public void openQualityAssurancePage() {
        driver.get("https://useinsider.com/careers/quality-assurance");
        Assert.assertTrue(isPageTitleContains(qualityAssuranceText));
        Assert.assertTrue(isElementDisplayed(insiderLogo),"Insider logo is not present");
        Assert.assertTrue(isElementDisplayed(demoButton),"Demo button is not present");
        Assert.assertTrue(isElementDisplayed(seeAllQAJobsButton),"See all QA jobs button is not present");
    }

    public void openQAJobsInIstanbul() {
        clickElement(seeAllQAJobsButton);
        sleep(5);
    }
}
