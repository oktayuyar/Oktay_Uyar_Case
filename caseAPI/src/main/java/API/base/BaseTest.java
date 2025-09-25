package API.base;

import API.utils.Log;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @AfterSuite(alwaysRun = true)
    @Parameters({"generateReport"})
    public void tearDown(ITestContext iTestContext, @Optional String generateReport) {
        int passedTests, skippedTests, failedTests;
        passedTests = iTestContext.getPassedTests().size();
        skippedTests = iTestContext.getSkippedTests().size();
        failedTests = iTestContext.getFailedTests().size();

        Log.info("After API Test Suite: ");
        Log.info("===============================================");
        Log.info("  passed tests:  " + passedTests);
        Log.info("  skipped tests: " + skippedTests);
        Log.info("  failed tests:  " + failedTests);
        Log.info("===============================================\n");
    }

}
