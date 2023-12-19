package common;

import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.TapOptions;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ExtentReportsUtil;


import io.appium.java_client.TouchAction;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class BaseMethods {
	protected AndroidDriver driver;
	protected WebDriverWait wait;
	
	public BaseMethods(AndroidDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}
	
	public String getScreenshot() {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
        //String for saving the screenshot to desired path
		String destination = System.getProperty("user.dir") + "\\ExtentReport\\FailedTestsScreenshots\\Error - "+dateName+".png";
		//String for the output for extent report
		String screenshotLoc = "../FailedTestsScreenshots/Error - "+dateName+".png";
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotLoc;
	}

	protected String getSaltString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	public String getScreenshotPass() {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String getRandom = getSaltString();
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		//String for saving the screenshot to desired path
		String destination = System.getProperty("user.dir") + "\\ExtentReport\\PassedScreenshots\\Passed - "+dateName+"-"+getRandom+".png";
		//String for the output for extent report
		String screenshotLoc = "../PassedScreenshots/Passed - "+dateName+"-"+getRandom+".png";
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotLoc;
	}
	
	public void navigateBack() {
		driver.navigate().back();
		System.out.println("Navigated to Previous page");
		ExtentReportsUtil.pass("Navigated to Previous page");
	}
	
	public void pressBackKey() {
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		System.out.println("Pressed Back Key");
		ExtentReportsUtil.pass("Pressed Back Key");
	}
	
	public void clickOnText(String value) {
		WebElement ele = driver.findElement(AppiumBy.xpath("//*[@text = '"+ value +"']"));
		boolean clicked = false;
		try {
			ele.click();
			clicked = true;
		} catch (StaleElementReferenceException e) {
			ele = driver.findElement(AppiumBy.xpath("//*[text() = '"+ value +"']"));
			ele.click();
			clicked = true;
		}
		
		if (clicked == true) {
			System.out.println("\"" +value + "\" text was Clicked");
			ExtentReportsUtil.pass("\"" +value + "\" text was Clicked");
		}
		else {
			System.out.println("\"" +value + "\" text was NOT Clicked");
			ExtentReportsUtil.fail("\"" +value + "\" text was NOT Clicked");
			BaseClass.failTC++;
			
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	
	
	public void clickElement(String[] element) {
		String locatorBy = element[0];
		boolean clicked = false;
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
			try {
				elm.click();
				clicked = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
				elm.click();
				clicked = true;
			}	
		}
		else if (locatorBy == "id") {
			//WebElement elm = driver.findElement(AppiumBy.id(element[2]));
			//WebElement populatePassword = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(AppiumBy.id("com.androiddevelopment.mobile_banking:id/passwordEditText")));
			WebElement elm = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(AppiumBy.id(element[2])));
			try {
				elm.click();
				clicked = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element[2]));
				elm.click();
				clicked = true;
			}

			
		}
		else if (locatorBy == "xpath") {
			WebElement elm = driver.findElement(AppiumBy.xpath(element[2]));
			try {
				elm.click();
				clicked = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element[2]));
				elm.click();
				clicked = true;
			}	
		}
		
		if (clicked == true) {
			System.out.println(element[1] + " was Clicked");
			ExtentReportsUtil.pass(element[1] + " was Clicked");
		}
		else {
			System.out.println(element[1] + " was NOT Clicked");
			ExtentReportsUtil.fail(element[1] + " was NOT Clicked");
			BaseClass.failTC++;
			
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
		
	}
	
	public void sendTextToElement(String[] element, String value) {
		String locatorBy = element[0];
		boolean enteredText = false;
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
			try {
				elm.clear();
				elm.sendKeys(value);
				enteredText = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			}
		}
		else if (locatorBy == "xpath") {
			WebElement elm = driver.findElement(AppiumBy.xpath(element[2]));
			try {
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element[2]));
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			}

		}
		else if (locatorBy == "id") {
			WebElement elm = driver.findElement(AppiumBy.id(element[2]));
			try {
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element[2]));
				elm.clear();
				elm.sendKeys(value);			
				enteredText = true;
			}
		}
		
		if (enteredText == true) {
			System.out.println("Send text value to element: " + element[1]);	
			ExtentReportsUtil.pass("Send text value to element: " + element[1]);
		}
		else {
			System.out.println("Unable to send text value to element: " + element[1]);	
			ExtentReportsUtil.fail("Unable to send text value to element: " + element[1]);
			BaseClass.failTC++;
			
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	
	public void validateIfCorrectText(String[] element, String expectedValue) {
		String locatorBy = element[0];
		String actualValue = null;
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
			try {
				actualValue = elm.getText();
				ExtentReportsUtil.logger.log(LogStatus.PASS,
						ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
				actualValue = elm.getText();
			}
		}
		else if (locatorBy == "xpath") {
			//WebElement elm = driver.findElement(AppiumBy.xpath(element[2]));
			WebElement elm  = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath((element[2]))));
			try {
				actualValue = elm.getText();
				ExtentReportsUtil.logger.log(LogStatus.PASS,
						ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));

			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element[2]));
				actualValue = elm.getText();
			}
		}
		else if (locatorBy == "id") {
			WebElement elm  = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id((element[2]))));
			try {
				actualValue = elm.getText();
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element[2]));
				actualValue = elm.getText();
			}
		}

		if (actualValue.contentEquals(expectedValue)) {
			System.out.println(element[1] + " value is equal to expected value");
			ExtentReportsUtil.pass(element[1] + " value is equal to expected value");
		}
		else {
			System.out.println(element[1] + " value is NOT equal to expected value");
			ExtentReportsUtil.fail(element[1] + " value is NOT equal to expected value");
			BaseClass.failTC++;
			
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}


	}

	public void validateAddedValue(double actualBalance,double expectedBalance,String addedBalance){
		double Result = 0;
		double addedBalanceInt = Double.parseDouble(addedBalance);

		Result = actualBalance + addedBalanceInt;

		if (Result == expectedBalance) {
			System.out.println("Deposit amount "+addedBalanceInt+" is added to the savings amount");
			System.out.println(" Balance: "+expectedBalance);
			ExtentReportsUtil.pass("Deposit amount "+addedBalanceInt+" is added to the savings amount");
			ExtentReportsUtil.pass(" Balance: "+expectedBalance);
			ExtentReportsUtil.logger.log(LogStatus.PASS,
					ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println(" value is NOT equal to expected value+"+expectedBalance+"Actual:"+Result);
			ExtentReportsUtil.fail(" value is NOT equal to expected value+"+expectedBalance+"Actual:"+Result);
			BaseClass.failTC++;

			ExtentReportsUtil.logger.log(LogStatus.FAIL,
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}

	public void validateDeductValue(double actualBalance,double expectedBalance,String addedBalance){
		double Result = 0;
		double addedBalanceInt = Double.parseDouble(addedBalance);
		Result = actualBalance - addedBalanceInt;

		if (Result == expectedBalance) {
			System.out.println("Withdrawal amount "+addedBalanceInt+" is deducted to the savings amount");
			System.out.println(" Balance: "+expectedBalance);
			ExtentReportsUtil.pass("Withdrawal amount"+addedBalanceInt+"is deducted to the savings amount");
			ExtentReportsUtil.pass(" Balance: "+expectedBalance);
			ExtentReportsUtil.logger.log(LogStatus.PASS,
					ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println(" value is NOT deducted to expected value+"+expectedBalance+"Actual:"+Result);
			ExtentReportsUtil.fail(" value is NOT deducted to expected value+"+expectedBalance+"Actual:"+Result);
			BaseClass.failTC++;

			ExtentReportsUtil.logger.log(LogStatus.FAIL,
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}

	public void validateTransferSavingsToChecking(double getCheckBalance,double getSavingsBalance,double getCheckBalanceExpected,double getSavingsBalanceExpected,String addedBalance){
		double ResultSavings = 0,ResultChecking = 0;
		double addedBalanceInt = Double.parseDouble(addedBalance);
		ResultSavings = getSavingsBalance - addedBalanceInt;
		ResultChecking = getCheckBalance + addedBalanceInt;
		if (ResultSavings == getSavingsBalanceExpected && ResultChecking == getCheckBalanceExpected) {
			System.out.println("Savings to Check amount "+addedBalanceInt+" is deducted from Savings to the Check amount");
			System.out.println(" Check Balance: "+getCheckBalanceExpected);
			System.out.println(" Savings Balance: "+getSavingsBalanceExpected);
			ExtentReportsUtil.pass("Savings to Check amount "+addedBalanceInt+" is deducted from Savings and added to Check amount");
			ExtentReportsUtil.pass(" Check Balance: "+getCheckBalanceExpected);
			ExtentReportsUtil.pass(" Savings Balance: "+getSavingsBalanceExpected);
			ExtentReportsUtil.logger.log(LogStatus.PASS,
					ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));

		}
		else {
			System.out.println(" value has no change to expected value+"+getCheckBalanceExpected+"Actual:"+ResultChecking);
			ExtentReportsUtil.fail("value has no change to expected value+"+getCheckBalanceExpected+"Actual:"+ResultChecking);
			ExtentReportsUtil.fail("Savings to Check amount "+addedBalanceInt+" is deducted from Savings and added to Check amount");
			System.out.println(" Check Balance: "+getCheckBalanceExpected);
			System.out.println(" Savings Balance: "+getSavingsBalanceExpected);
			ExtentReportsUtil.fail(" Check Balance: "+getCheckBalanceExpected);
			ExtentReportsUtil.fail(" Savings Balance: "+getSavingsBalanceExpected);
			BaseClass.failTC++;

			ExtentReportsUtil.logger.log(LogStatus.FAIL,
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}

	public void validateTransferCheckingToSavings(double getCheckBalance,double getSavingsBalance,double getCheckBalanceExpected,double getSavingsBalanceExpected,String addedBalance){
		double ResultSavings = 0,ResultChecking = 0;
		double addedBalanceInt = Double.parseDouble(addedBalance);
		ResultSavings = getSavingsBalance + addedBalanceInt;
		ResultChecking = getCheckBalance - addedBalanceInt;
		if (ResultSavings == getSavingsBalanceExpected && ResultChecking == getCheckBalanceExpected) {
			System.out.println("Check to Savings amount "+addedBalanceInt+" is deducted from Check to the Savings amount");
			System.out.println(" Check Balance: "+getCheckBalanceExpected);
			System.out.println(" Savings Balance: "+getSavingsBalanceExpected);
			ExtentReportsUtil.pass("Check to Savings amount "+addedBalanceInt+" is deducted from Savings and added to Check amount");
			ExtentReportsUtil.pass(" Check Balance: "+getCheckBalanceExpected);
			ExtentReportsUtil.pass(" Savings Balance: "+getSavingsBalanceExpected);
			ExtentReportsUtil.logger.log(LogStatus.PASS,
					ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));

		}
		else {
			System.out.println(" value has no change to expected value+"+getCheckBalanceExpected+"Actual:"+ResultChecking);
			ExtentReportsUtil.fail("value has no change to expected value+"+getCheckBalanceExpected+"Actual:"+ResultChecking);
			ExtentReportsUtil.fail("Savings to Check amount "+addedBalanceInt+" is deducted from Check and added to Savings amount");
			System.out.println(" Check Balance: "+getCheckBalanceExpected);
			System.out.println(" Savings Balance: "+getSavingsBalanceExpected);
			ExtentReportsUtil.fail(" Check Balance: "+getCheckBalanceExpected);
			ExtentReportsUtil.fail(" Savings Balance: "+getSavingsBalanceExpected);
			BaseClass.failTC++;

			ExtentReportsUtil.logger.log(LogStatus.FAIL,
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}


	public double getBalanceValue(String[] element,String report) {
		String locatorBy = element[0];
		String expectedValue,actualValue = null;
		double actualValueInt = 0,expectedValueInt = 0;
		String[] withDecimalValue;
		int decimalGet;
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
			try {
				actualValue = elm.getText();
				actualValue = actualValue.replace("$","");
				actualValue = actualValue.replace(",","");
				actualValueInt = Double.parseDouble(actualValue);
				System.out.println(report+" is "+actualValueInt);
				ExtentReportsUtil.pass(report+" is "+actualValueInt);

			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
				actualValue = elm.getText();
			}
		}
		else if (locatorBy == "xpath") {
			//WebElement elm = driver.findElement(AppiumBy.xpath(element[2]));
			WebElement elm  = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath((element[2]))));
			try {
				actualValue = elm.getText();
				actualValue = actualValue.replace("$","");
				actualValue = actualValue.replace(",","");
				actualValueInt = Integer.parseInt(actualValue);
				System.out.println(report+" is "+actualValueInt);
				ExtentReportsUtil.pass(report+" is "+actualValueInt);
				ExtentReportsUtil.logger.log(LogStatus.PASS,
						ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));

			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element[2]));

				actualValue = elm.getText();
			}
		}
		else if (locatorBy == "id") {
			WebElement elm  = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id((element[2]))));
			try {
				actualValue = elm.getText();
				actualValue = actualValue.replace("$","");
				actualValue = actualValue.replace(",","");
				actualValueInt = Double.parseDouble(actualValue);
				System.out.println(report+" is "+actualValueInt);
				ExtentReportsUtil.pass(report+" is "+actualValueInt);
				ExtentReportsUtil.logger.log(LogStatus.PASS,
						ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element[2]));
				
				actualValue = elm.getText();
			}
		}
	return actualValueInt;
	}


	public void longPressElement(String[] element) {
		String locatorBy = element[0];
		JavascriptExecutor js = (JavascriptExecutor) driver;
		boolean longPressed = false;
		
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
			try {
				Map<String, Object> params = new HashMap<>();
				params.put("elementId", ((RemoteWebElement) elm).getId());
				params.put("duration", 2000);
				js.executeScript("mobile: longClickGesture", params);
				longPressed = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
				Map<String, Object> params = new HashMap<>();
				params.put("elementId", ((RemoteWebElement) elm).getId());
				params.put("duration", 2000);
				js.executeScript("mobile: longClickGesture", params);
				longPressed = true;
			}
		}
		else if (locatorBy == "xpath") {
			WebElement elm = driver.findElement(AppiumBy.xpath(element[2]));
			try {
				Map<String, Object> params = new HashMap<>();
				params.put("elementId", ((RemoteWebElement) elm).getId());
				params.put("duration", 2000);
				js.executeScript("mobile: longClickGesture", params);
				longPressed = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element[2]));
				Map<String, Object> params = new HashMap<>();
				params.put("elementId", ((RemoteWebElement) elm).getId());
				params.put("duration", 2000);
				js.executeScript("mobile: longClickGesture", params);
				longPressed = true;
			}
		}
		else if (locatorBy == "id") {
			WebElement elm = driver.findElement(AppiumBy.id(element[2]));
			try {
				Map<String, Object> params = new HashMap<>();
				params.put("elementId", ((RemoteWebElement) elm).getId());
				params.put("duration", 2000);
				js.executeScript("mobile: longClickGesture", params);
				longPressed = true;
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element[2]));
				Map<String, Object> params = new HashMap<>();
				params.put("elementId", ((RemoteWebElement) elm).getId());
				params.put("duration", 2000);
				js.executeScript("mobile: longClickGesture", params);
				longPressed = true;
			}
		}
		
		if (longPressed == true) {
			System.out.println(element[1] + " was long pressed");
			ExtentReportsUtil.pass(element[1] + " was long pressed");
		}
		else {
			System.out.println(element[1] + " was NOT long pressed");
			ExtentReportsUtil.fail(element[1] + " was NOT long pressed");
			BaseClass.failTC++;
			
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
		
	}
	
	public void assertElementDisplayed(String[] element) {
		WebElement elm;
		boolean display = false;
		
		if (element[0] == "accessibilityId") {
			try {
				elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
				display = elm.isDisplayed();
				ExtentReportsUtil.pass("Passed");
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
				display = elm.isDisplayed();
			}
			
		}
		else if (element[0] == "id") {
			try {
				//elm = driver.findElement(AppiumBy.id(element[2]));
				 elm = (WebElement) new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.elementToBeClickable(AppiumBy.id(element[2])));
				 display = elm.isDisplayed();
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.id(element[2]));
				display = elm.isDisplayed();
			}
		}
		
		else if (element[0] == "xpath") {
			try {
				elm = driver.findElement(AppiumBy.xpath(element[2]));
				display = elm.isDisplayed();
				ExtentReportsUtil.pass("Passed");
			} catch (StaleElementReferenceException e) {
				elm = driver.findElement(AppiumBy.xpath(element[2]));
				display = elm.isDisplayed();
			}
		}
		
		if (display == true) {
			System.out.println(element[1] + " is displayed");
			ExtentReportsUtil.pass(element[1] + " is displayed");
			
			
			ExtentReportsUtil.logger.log(LogStatus.PASS,
					ExtentReportsUtil.logger.addScreenCapture(getScreenshotPass()));
		}
		else {
			System.out.println(element[1] + " is NOT displayed");
			ExtentReportsUtil.fail(element[1] + " is NOT displayed");
			BaseClass.failTC++;
			
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	
	public void assertElementNotDisplayed(String[] element) {
		boolean displayed = true;
		displayed = isDisplayed(element);
		if (displayed == false) {
			System.out.println(element[1] + " is not displayed");
			ExtentReportsUtil.pass(element[1] + " is not displayed");
		}
		else {
			System.out.println(element[1] + " is displayed");
			ExtentReportsUtil.fail(element[1] + " is displayed");
			BaseClass.failTC++;
			
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
	}
	public boolean isDisplayed(String[] element) {
		try {
			if (element[0] == "accessibilityId") {
				driver.findElement(AppiumBy.accessibilityId(element[2]));
			}
			else if (element[0] == "id") {
				driver.findElement(AppiumBy.id(element[2]));

			}
			else if (element[0] == "xpath") {
				driver.findElement(AppiumBy.xpath(element[2]));

			}
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean scrollDownGesture() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Map<String, Object> params = new HashMap<>();
		params.put("left", 100);
		params.put("top", 300);
		params.put("width", 1300);
		params.put("height", 2900);
		params.put("direction", "down");
		params.put("percent", 0.5);
		
		boolean canScrollMore = (Boolean) (js.executeScript("mobile: scrollGesture", params));
		
		return canScrollMore;
	}
	
	public void scrollToElementByUIAutomator(String[] element) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView("+element[2]+")"));
		System.out.println("Scrolled to: " + element[1]);
		ExtentReportsUtil.pass("Scrolled to: " + element[1]);
	}
	
	public void scrollToText(String text) {
		driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"))"));
		System.out.println("Scrolled to Text: " + text);
		ExtentReportsUtil.pass("Scrolled to Text: " + text);
	}
	
	public boolean flingGesture(String[] element) {
		String locatorBy = element[0];
		boolean canScrollMore = true;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
			Map<String, Object> params = new HashMap<>();
			params.put("elementId", ((RemoteWebElement) elm).getId());
			params.put("direction", "down");
			params.put("speed", 5000);
			canScrollMore = (Boolean) (js.executeScript("mobile: flingGesture", params));
		}
		else if (locatorBy == "id") {
			WebElement elm = driver.findElement(AppiumBy.id(element[2]));
			Map<String, Object> params = new HashMap<>();
			params.put("elementId", ((RemoteWebElement) elm).getId());
			params.put("direction", "down");
			params.put("speed", 5000);
			canScrollMore = (Boolean) (js.executeScript("mobile: flingGesture", params));
		}		
		else if (locatorBy == "xpath") {
			WebElement elm = driver.findElement(AppiumBy.xpath(element[2]));
			Map<String, Object> params = new HashMap<>();
			params.put("elementId", ((RemoteWebElement) elm).getId());
			params.put("direction", "down");
			params.put("speed", 5000);
			canScrollMore = (Boolean) (js.executeScript("mobile: flingGesture", params));
		}
		
		return canScrollMore;
	}
	
	public void dragGesture(String[] element, int coordinateX, int coordinateY) {
		String locatorBy = element[0];
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (locatorBy == "accessibilityId") {
			WebElement elm = driver.findElement(AppiumBy.accessibilityId(element[2]));
			Map<String, Object> params = new HashMap<>();
			params.put("elementId", ((RemoteWebElement) elm).getId());
			params.put("endX", coordinateX);
			params.put("endY", coordinateY);
			js.executeScript("mobile: dragGesture", params);
		}
		else if (locatorBy == "id") {
			WebElement elm = driver.findElement(AppiumBy.id(element[2]));
			Map<String, Object> params = new HashMap<>();
			params.put("elementId", ((RemoteWebElement) elm).getId());
			params.put("endX", coordinateX);
			params.put("endY", coordinateY);
			js.executeScript("mobile: dragGesture", params);
		}
		else if (locatorBy == "xpath") {
			WebElement elm = driver.findElement(AppiumBy.xpath(element[2]));
			Map<String, Object> params = new HashMap<>();
			params.put("elementId", ((RemoteWebElement) elm).getId());
			params.put("endX", coordinateX);
			params.put("endY", coordinateY);
			js.executeScript("mobile: dragGesture", params);
		}
	}
	
	public void getElementsTextByXpath(String locatorByXpath) {
		List<WebElement> elements = driver.findElements(By.xpath(locatorByXpath));
        
		for(WebElement element: elements){
		    System.out.println(element.getText());
		    ExtentReportsUtil.info(element.getText());
		}
	}
	
	public String getElementText(String element[]) {
		WebElement elm;
		String elmtext = null;
		try {
			elm = driver.findElement(AppiumBy.xpath(element[2]));
			elmtext = elm.getText();
		} catch (Exception e) {
			elm = driver.findElement(AppiumBy.xpath(element[2]));
			elmtext = elm.getText();
		}
		return elmtext;
	}
	
	public void moveToElement(String[] ele) {
		
		String locatorBy = ele[0];
		boolean movedElement = false;
		
		if (locatorBy == "accessibilityId") {
			WebElement element = driver.findElement(AppiumBy.accessibilityId(ele[2]));
			try {
				Actions action = new Actions(driver);
				action.moveToElement(element);
				action.perform();	
				movedElement = true;				
			} catch (StaleElementReferenceException e) {
				element = driver.findElement(AppiumBy.accessibilityId(ele[2]));
				Actions action = new Actions(driver);
				action.moveToElement(element);
				action.perform();	
				movedElement = true;
			}
		}
		else if (locatorBy == "id") {
			WebElement element = driver.findElement(AppiumBy.id(ele[2]));
			try {
				Actions action = new Actions(driver);
				action.moveToElement(element);
				action.perform();	
				movedElement = true;
			} catch (StaleElementReferenceException e) {
				element = driver.findElement(AppiumBy.id(ele[2]));
				Actions action = new Actions(driver);
				action.moveToElement(element);
				action.perform();	
				movedElement = true;
			}
		}
		else if (locatorBy == "xpath") {
			WebElement element = driver.findElement(AppiumBy.xpath(ele[2]));
			try {
				Actions action = new Actions(driver);
				action.moveToElement(element);
				action.perform();	
				movedElement = true;
			} catch (StaleElementReferenceException e) {
				element = driver.findElement(AppiumBy.xpath(ele[2]));
				Actions action = new Actions(driver);
				action.moveToElement(element);
				action.perform();	
				movedElement = true;
			}
			
		}
		
		if (movedElement == true) {
			System.out.println("Moved to element: " + ele[1]);
			ExtentReportsUtil.pass("Moved to element: " + ele[1]);
		}
		else {
			System.out.println("Unable to move to element: " + ele[1]);
			ExtentReportsUtil.fail("Unable to move to element: " + ele[1]);
			BaseClass.failTC++;
			
			ExtentReportsUtil.logger.log(LogStatus.FAIL, 
					ExtentReportsUtil.logger.addScreenCapture(getScreenshot()));
		}
				
	}


}
