package HelixSensePages;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract  class HelixsensePageBase {
	
	
	 AndroidDriver driver;

	public HelixsensePageBase(AndroidDriver driver) {
		
		this.driver =  driver;
       
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	public void waitForVisible(WebDriver driver, WebElement element) {
		try {
			Thread.sleep(1500);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void waitForListVisible(WebDriver driver, List<WebElement> element) {
		try {
			 Thread.sleep(2000);
			WebDriverWait wait = new WebDriverWait(driver, 15);
			wait.until(ExpectedConditions.visibilityOfAllElements(element));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String randomTicketName(String ticketname) {

		String randomticketname = ticketname + RandomStringUtils.randomNumeric(8);

		return randomticketname;
	}
	
	@SuppressWarnings("unchecked")
	public String  scrollToExactElement(String str) {
        ((AndroidDriver<MobileElement>) driver).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
                        + str + "\").instance(0))").click();;
        
        return str;
    }

}
