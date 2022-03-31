package HelixSenseTests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.helixsense.testsuites.ThreadLocalDriver;

import ExtentClasses.DataDrivenManager;
import ExtentClasses.ExtentManager;
import ExtentClasses.WebDriverManagers;
import HelixSensePages.HelixSenseLoginPage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;


public class HelixSenseTestBase {
	
	 
    private static final ThreadLocalDriver  threadLocalDriver= new ThreadLocalDriver();
	static Properties testConfig;
	Logger logger;
	public AndroidDriver <MobileElement> driver;
	HelixSenseLoginPage loginpage;
	protected static ExtentReports extent;
	protected static ThreadLocal parentTestThread = new ThreadLocal();
	protected static ThreadLocal testThread = new ThreadLocal();
	protected ExtentTest erParentTest; // for test class
	protected ExtentTest testReport; // for test method
	
	String testFailureScreenshotPath;

	@BeforeSuite
	public void beforeSuite() throws FileNotFoundException, IOException {
		PropertyConfigurator.configure("log4j.properties");
		// Get Test Config
		testConfig = new Properties();
		testConfig.load(new FileInputStream("testconfig.properties"));

		String extentReportFilePath = "HelixSenseAndroidReport.html";
		extent = ExtentManager.createInstance(extentReportFilePath);
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(extentReportFilePath);
		extent.attachReporter(htmlReporter);
	}
	@BeforeMethod
   // @Parameters({ "udid", "platformVersion"})
	public void testSetup(Method method ) throws InterruptedException, MalformedURLException {
		logger = Logger.getLogger(this.getClass().getSimpleName());
		logger.info("################# Starting " + method.getName() + " Test Case #################");
		//startAppiumService(port);
		DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554"); 
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,"12");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.NO_RESET,true);
        capabilities.setCapability("appPackage", "com.app.hsense.compass");
        capabilities.setCapability(MobileCapabilityType.APP, "C:\\Users\\Durga\\Downloads\\hsense_sfx_dev_build_1.4.0_070322_13_19.apk");  
	    //threadLocalDriver.setTLDriver(new AndroidDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), capabilities));
	   //driver = threadLocalDriver.getTLDriver();
	     URL remoteUrl = new URL("http://localhost:4723/wd/hub");
	     driver = new AndroidDriver<MobileElement>(remoteUrl, capabilities); 
	     driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS) ;
		    loginpage=new HelixSenseLoginPage(driver);
	        //loginpage.login(testConfig.getProperty("username"), testConfig.getProperty("password"));
			logger.info("Verifying the login for account: " + testConfig.getProperty("username") + ","
					+ testConfig.getProperty("password"));
	}
	@DataProvider
	public Object[][] dataProvider(Method method) {
		DataDrivenManager ddm = new DataDrivenManager(testConfig.getProperty("testdatafile"));
		Object[][] TestData = ddm.getTestCaseDataSets(testConfig.getProperty("testdatasheet"), method.getName());

		return TestData;
	}
	public void startAppiumService(String port) {
		AppiumDriverLocalService service;
		AppiumServiceBuilder builder=new AppiumServiceBuilder();
		builder.withIPAddress("127.0.0.1");
		builder.usingPort(Integer.parseInt(port));
		service=AppiumDriverLocalService.buildService(builder);
		service.start();
		System.out.println("service has been started with"+port);
	}
	@AfterMethod
	public void testCleanUp(ITestResult result) throws InterruptedException, IOException {
		// Capture screenshot when test fails
		captureTestFailureScreenshot(result);

		//threadLocalDriver.getTLDriver().quit();
		
		WebDriverManagers.quitDriver(driver);
	
		logger.info("################# Ending " + result.getName() + " Test Case #################");
	
	}

	public void captureTestFailureScreenshot(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			// Gives path like
			// TestFailureScreenshots\com.teledentistry.tests.AdminAddPatientPageTest.verifyAddFormTitle.png
			testFailureScreenshotPath = "TestFailureScreenshots/" + getClass().getName() // full class name -
																							// com.teledentistry.tests.AdminAddPatientPageTest
					+ "." + result.getName() // test method name - testPageTitle
					+ ".png";

			// Files, Paths classes are provided by java.nio.file package
			// Create the directory if doesn't exist
			if (Files.notExists(Paths.get("TestFailureScreenshots"))) {
				Files.createDirectory(Paths.get("TestFailureScreenshots"));
			}

			// Delete the old file if exists
			Files.deleteIfExists(Paths.get(testFailureScreenshotPath));

			// Create new test failure screenshot file
			WebDriverManagers.getScreenshot(driver, testFailureScreenshotPath);
		}
	}

	@BeforeClass
	public synchronized void extentReportBeforeClass() {
		// Creating extent reports test class for every TestNG test class
		erParentTest = extent.createTest(getClass().getSimpleName());
		parentTestThread.set(erParentTest);
	}

	@BeforeMethod
	public synchronized void extentReportBeforeMethod(Method method) {
		// In ER test class, create test node based TestNG test method
		testReport = erParentTest.createNode(method.getName()+" "+"with correct credentials"+" "+testConfig.getProperty("username") + ","
				+ testConfig.getProperty("password"));
		testThread.set(testReport);
	}

	@AfterMethod(dependsOnMethods = "testCleanUp")
	public synchronized void extentReportAfterMethod(ITestResult result,Method method) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE)
			// Fail the testReport when TestNG test is failed
			testReport.fail(result.getThrowable(),
					MediaEntityBuilder.createScreenCaptureFromPath(testFailureScreenshotPath).build());
		else if (result.getStatus() == ITestResult.SKIP)
			// Skip the testReport when TestNG test is skipped
			testReport.skip(result.getThrowable(),
					MediaEntityBuilder.createScreenCaptureFromPath(testFailureScreenshotPath).build());
		else
			// Pass the testReport when TestNG test is passed
			testReport.pass("Test passed"+"-"+method.getName());

		extent.flush();
	}
	
}
