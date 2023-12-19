package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;

import java.io.File;

public class ExtentReportsUtil {
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static String screenshot;
	
	public static void startExtentReport(String path) {
		extent = new ExtentReports(System.getProperty("user.dir")+ "\\ExtentReport\\"+ path, true);
		System.out.println("Creating " + path);
		extent.loadConfig(new File(System.getProperty("user.dir")+ "\\ExtentReport\\Config\\ExtentConfig.xml"));
	}
	
	public static void getExtentResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Method Failed = " + result.getName());
			logger.log(LogStatus.FAIL, "Method Failed = " + result.getThrowable());
			Logger.log("Method Failed = " + result.getName());
		}
		else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Method Skipped = " + result.getName());
			Logger.log("Method Skipped = " + result.getName());
		}
		else {
			logger.log(LogStatus.PASS, "Method Passed = " + result.getName());
			Logger.log("Method Passed = " + result.getName());
		}
		
		extent.endTest(logger);
	}
	
	public static void logstep(String details) {
		logger = extent.startTest(details);
		Logger.log(details);
	}
	
	public static void pass(String details) {
		logger.log(LogStatus.PASS, details);
	}
	
	public static void fail(String details) {
		logger.log(LogStatus.FAIL, details); 
	}
	
	public static void info(String details) {
		logger.log(LogStatus.INFO, details);
	}
	
	public static void flushExtentReport() {
		extent.flush();
	}
	
	public static void closeExtentReport() {
		extent.close();
	}
	
	public static void endTest() {
		extent.endTest(logger);
	}	
}
