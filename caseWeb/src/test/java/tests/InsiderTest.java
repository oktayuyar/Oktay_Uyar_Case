package tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import web.base.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import web.listeners.TestListener;
import web.pages.CareersPage;
import web.pages.HomePage;


@Listeners({ TestListener.class })
@Epic("Insider Web Automation")
@Feature("Check Insider Home Page")
public class InsiderTest extends BaseTest {

    HomePage homePage;
    CareersPage careersPage;

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        homePage = new HomePage(driver);
        careersPage = new CareersPage(driver);
    }

    @Test(priority = 1, description = "Home page logo, title, demo button and company overview tests")
    @Story("The user must be able to access the home page and operate the links.")
    public void testHomePageAndNavigation() {

        Allure.step("Check the home page title include 'Insider' ?", () -> {
            homePage.checkPageTitle("Insider");
        });

        Allure.step("Check home page opened", () -> {
            homePage.checkHomePageOpened();
        });

    }

    @Test(priority = 2, description = "Careers page title, locations, teams, and life tests")
    @Story("The user must be able to access the careers page.")
    public void testCareersPage() {

        Allure.step("Open careers page", () -> {
            homePage.openCareersPage();
        });

        Allure.step("Check the careers page title include 'Insider Careers' ?", () -> {
            careersPage.checkPageTitle("Insider Careers");
        });

        Allure.step("Check careers page opened", () -> {
            careersPage.checkCareersPageOpened();
        });
    }

    @Test(priority = 3, description = "Quality Assurance careers page, QA job tests")
    @Story("The user must be able to access the careers page.")
    public void testCareersQAJobsPage() {

        Allure.step("Open quality assurance careers page", () -> {
            careersPage.openQualityAssurancePage();
        });

        Allure.step("Open quality assurance jobs in Istanbul", () -> {
            careersPage.openQAJobsInIstanbul();
        });

        Allure.step("Check all jobs position contains “Quality Assurance” and all location contains Istanbul", () -> {
            careersPage.checkListedJobsMatchWithFilters();
        });

        Allure.step("View the role and check redirect to the Lever Application", () -> {
            careersPage.viewRoleAndCheckRedirectToLeverApp();
        });

    }
}
