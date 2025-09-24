package web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import web.util.BasePageUtil;
import web.util.Log;

import java.util.List;
import java.util.Set;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

public class CareersPage extends BasePageUtil {

    private String qualityAssuranceText = "quality assurance";

    private By demoButton = By.linkText("Get a Demo");
    private By insiderLogo = By.cssSelector("img[alt='insider_logo']");
    private By teamsBlock = By.cssSelector("h3.category-title-media");
    private By teamCustomerSuccess = By.cssSelector("a[href$=\"/customer-success/\"]");
    private By seeAllTeamsButton = By.cssSelector("a.loadmore");
    private By locationsBlock = By.cssSelector("h3.category-title-media.ml-0");
    private By lifeAtInsiderBlock = By.xpath("//h2[contains(@class,'elementor-size-default') and contains(text(),'Life at Insider')]");
    private By seeAllQAJobsButton = By.cssSelector("a[href*=\"qualityassurance\"]");
    private By locationFilter = By.id("select2-filter-by-location-container");
    private By departmentFilter = By.id("select2-filter-by-department-container");
    private By clearFilterButton = By.cssSelector("span.select2-selection__clear");
    private By istanbulOption = By.xpath("//li[@class='select2-results__option' and text()='Istanbul, Turkiye']");

    private By jobCards = By.cssSelector("div.position-list-item");
    private By position = By.cssSelector(".position-title");
    private By department = By.cssSelector(".position-department");
    private By location = By.cssSelector(".position-location");
    private By viewRoleButton = By.xpath(".//a[contains(text(),'View Role')]");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public void checkPageTitle(String expected) {
        Assert.assertTrue(isPageTitleContains(expected), "Page title is not contains expected value!");
    }

    public void checkCareersPageOpened() {
        Assert.assertTrue(isElementDisplayed(insiderLogo),"Insider logo is not present!");
        Assert.assertTrue(isElementDisplayed(demoButton),"Demo button is not present!");
        scrollIntoView(teamsBlock);
        Assert.assertTrue(isElementDisplayed(teamsBlock),"Find your calling block is not present!");
        Assert.assertTrue(getText(teamsBlock).contains("Find your calling"),"Text is not match!");
        scrollIntoView(teamCustomerSuccess);
        Assert.assertTrue(isElementDisplayed(teamCustomerSuccess),"Customer Sucsess team is not present!");
        scrollIntoView(seeAllTeamsButton);
        Assert.assertTrue(isElementDisplayed(seeAllTeamsButton),"See all teams button is not present!");
        scrollIntoView(locationsBlock);
        Assert.assertTrue(isElementDisplayed(locationsBlock),"Our locations block is not present!");
        Assert.assertTrue(getText(locationsBlock).contains("Our Locations"),"Text is not match!");
        scrollIntoView(lifeAtInsiderBlock);
        Assert.assertTrue(isElementDisplayed(lifeAtInsiderBlock),"Life at Insider block is not present!");
    }

    public void openQualityAssurancePage() {
        driver.get("https://useinsider.com/careers/quality-assurance");
        Assert.assertTrue(isPageTitleContains(qualityAssuranceText));
        Assert.assertTrue(isElementDisplayed(insiderLogo),"Insider logo is not present!");
        Assert.assertTrue(isElementDisplayed(demoButton),"Demo button is not present!");
        Assert.assertTrue(isElementDisplayed(seeAllQAJobsButton),"See all QA jobs button is not present!");
    }


    public void openQAJobsInIstanbul() {
        waitUntilElementVisible(seeAllQAJobsButton);
        clickElement(seeAllQAJobsButton);
        scrollIntoView(locationFilter);
        Assert.assertTrue(isElementDisplayed(locationFilter),"Location filter is not present!" );
        Assert.assertTrue(isElementDisplayed(departmentFilter),"Department filter is not present!" );
        Assert.assertEquals(waitForText(departmentFilter, "×\n" + "Quality Assurance"), "×\n" + "Quality Assurance",
                "Department filter selected value is not correct!");
        selectFromDropdownWithScroll(locationFilter, istanbulOption);
        Assert.assertEquals(waitForText(locationFilter, "×\n" + "Istanbul, Turkiye"), "×\n" + "Istanbul, Turkiye",
                "Location filter selected value is not correct!");
    }

    public void checkListedJobsMatchWithFilters() {
        waitSeconds(1);
        isElementDisplayed(jobCards);
        List<WebElement> cards = findElements(jobCards);
        Integer cardSize = cards.size();
        for (int i = 0; i < cardSize ; i++) {
            Assert.assertTrue(getTextWithIndex(position, i).contains("Quality Assurance"));
            Assert.assertTrue(getTextWithIndex(department, i).contains("Quality Assurance"));
            Assert.assertTrue(getTextWithIndex(location, i).contains("Istanbul, Turkiye"));
        }
    }

    public void viewRoleAndCheckRedirectToLeverApp() {
        hoverOverElement(viewRoleButton);
        waitUntilElementVisible(viewRoleButton);
        clickElement(viewRoleButton);
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        Set<String> allWindows = driver.getWindowHandles();
        String originalWindow = driver.getWindowHandle();
        allWindows.remove(originalWindow);
        String newWindow = allWindows.iterator().next();
        driver.switchTo().window(newWindow);

        Log.info("New Tab URL: " + driver.getCurrentUrl());
        Assert.assertTrue(driver.getCurrentUrl().contains("jobs.lever.co/useinsider"),"Lever app is not present!");
    }

}
