package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener{

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String reportName;
	
	public void onStart(ITestContext testCOntext)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time stamp
		reportName = "Test-Report-" + timeStamp + ".html";
		
		// Initialize ExtentSparkReporter
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + reportName);	//location of Report
        sparkReporter.config().setDocumentTitle("RestAssuredAutomationTestReport");	//Report Title
        sparkReporter.config().setReportName("Pet Store Users API");
        sparkReporter.config().setTheme(Theme.DARK);
        
     // Initialize ExtentReports and attach the reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system information to the report
        extent.setSystemInfo("Application", "Pets Store User API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("User", "Sudhanshu Shekhar");
        extent.setSystemInfo("Environment", "QA");
	}
	
	// This method is invoked when a test starts
//    public void onTestStart(ITestResult result) {
//        // Create a new test in the report for the current test method
//        test = extent.createTest(result.getMethod().getMethodName());
//    }

    // This method is invoked when a test passes
    public void onTestSuccess(ITestResult result) {
    	test = extent.createTest(result.getName());
    	test.assignCategory(result.getMethod().getGroups());
    	test.createNode(result.getName());
        test.log(Status.PASS, "Test Passed");
    }

    // This method is invoked when a test fails
    public void onTestFailure(ITestResult result) {
    	test = extent.createTest(result.getName());
    	test.createNode(result.getName());
    	test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, "Reason: " + result.getThrowable().getMessage());

        // Add a screenshot if necessary (ensure screenshot logic is implemented)
        // String screenshotPath = "path_to_screenshot";
        // test.addScreenCaptureFromPath(screenshotPath);
    }

    // This method is invoked when a test is skipped
    public void onTestSkipped(ITestResult result) {
    	test = extent.createTest(result.getName());
    	test.createNode(result.getName());
    	test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, "Reason: " + result.getThrowable().getMessage());
    }

    // This method is invoked when a test fails but is within a success percentage
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        test.log(Status.WARNING, "Test Partially Passed");
    }

    // This method is invoked when a test context ends
    public void onFinish(ITestContext testContext) {
        // Flush the extent report to write the results
        extent.flush();
    }

	 
}
