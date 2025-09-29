package web.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import web.utils.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BaseTest {

    public WebDriver driver;
    private By acceptAllCookiesButton = By.id("wt-cli-accept-all-btn");

    @Parameters({"browser"})
    @BeforeSuite(alwaysRun = true)
    public void setup(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized");
            driver = new FirefoxDriver(options);

        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://useinsider.com");
        closeCookiesPopup();
    }

    private void closeCookiesPopup() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement acceptButton = wait.until(
                    ExpectedConditions.elementToBeClickable(acceptAllCookiesButton)
            );
            acceptButton.click();
            Log.info("Cookies popup closed.");
        } catch (Exception e) {
            Log.info("Cookies popup is not present, continue to test.");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE)
            attachScreenshot(driver);
    }

    @AfterSuite(alwaysRun = true)
    @Parameters({"generateReport"})
    public void tearDown(ITestContext iTestContext, @Optional String generateReport) {
        int passedTests, skippedTests, failedTests;
        passedTests = iTestContext.getPassedTests().size();
        skippedTests = iTestContext.getSkippedTests().size();
        failedTests = iTestContext.getFailedTests().size();

        Log.info("After Web Test Suite: ");
        Log.info("===============================================");
        Log.info("  passed tests:  " + passedTests);
        Log.info("  skipped tests: " + skippedTests);
        Log.info("  failed tests:  " + failedTests);
        Log.info("===============================================\n");
        driver.quit();
    }


    @Attachment(value = "Screenshot", type = "image/png")
    public void attachScreenshot(WebDriver driver) throws IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        Date date = new Date();
        String fileName = sdf.format(date);
        File des = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        FileUtils.copyFile(des, new File(System.getProperty("user.dir") + "//Screenshot//" + fileName + ".png"));

        Log.info("Screenshot is captured");

        try (InputStream is = Files.newInputStream(Paths.get(System.getProperty("user.dir") + "//Screenshot//" + fileName + ".png"))) {
            Allure.attachment(fileName + ".png", is);
        }

    }

    public WebDriver getDriver() {
        return driver;
    }
}