package common;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ExtentReportsUtil;
import utils.Logger;

import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class BaseClass {
	public AndroidDriver driver;
	public static ITestResult result;
    	
	public static int failTC;

	public final String apkPath = "C:\\Users\\COLLABERA.DIGITAL\\Documents\\Dharien_Files\\androidTestingCompleted\\testng-appium-app-browserstack-sdk_Completed\\android\\testng-examples\\mobilebanking.apk";
	

	MutableCapabilities options = new UiAutomator2Options();
	//..setApp(apkPath);
	HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
	String userName ="dharienarrojo_tWSZJP"; // System.getenv
	String accessKey = "9ghHpDgBYJMuGxiX6hxD";  // System.getenv
	String buildName = "Mobile_Banking_App";
	//String app = System.getenv("BROWSERSTACK_APP_ID"); --can be used 
	String app = "bs://8449cec2c3645a1a7c41ecd19973ba6b6abac462";
	String URL2 = "http://" +  userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";
	    
	
    @BeforeSuite
	public void setupReport() {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		ExtentReportsUtil.startExtentReport("\\extendreports\\Test"+timeStamp+".html");
	}  
    
    @BeforeTest(alwaysRun=true)
    public void setUpRunEnv() throws Exception {
    	
    	browserstackOptions.put("appiumVersion", "2.0.1");
       options.setCapability("bstack:options", browserstackOptions);
        options.setCapability("deviceName", "Google Pixel 6");
        options.setCapability("os_Version", "12.0");
        options.setCapability("Project", "MobileBanking");
        options.setCapability("build", buildName);
        options.setCapability("app", app);        
        driver = new AndroidDriver(new URL(URL2),options);
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    
    @BeforeMethod
    public void beforeMethod(Method m) throws Exception{

    }

    @AfterMethod(alwaysRun=true)
    public void afterMethod(ITestResult result) throws Exception {		    	
    	ExtentReportsUtil.getExtentResult(result);
		Logger.log("Results Retrieved");
    }
    
    @AfterTest
    public void tearDown() {  
    	System.out.println(failTC);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
    	try {
			if (failTC == 0) {
				System.out.println("Setting session status to Passed");
				jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
			}
			else {
				System.out.println("Setting session status to Failed");
				jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\"}}");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	driver.quit();
    	Logger.log("End Test");
    }
    
    @AfterSuite
	public void endTest() {
		ExtentReportsUtil.flushExtentReport();
		ExtentReportsUtil.closeExtentReport();
		Logger.log("End Report");
	}
    
}
