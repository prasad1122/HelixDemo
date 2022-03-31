package HelixSensePages;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HelixSenseLoginPage extends HelixsensePageBase  {
	
	@AndroidFindBy (xpath = "//android.widget.Button[@text='Allow']")
    WebElement clickAllow;

	@AndroidFindBy (xpath = "//android.widget.Button[@text='While using the app']")
    WebElement clickWhileUsingTheApp;
	
	
	@AndroidFindBy (id = "com.hsensefm.live:id/edt_account_id")
    WebElement enterAccountId;
	
	@AndroidFindBy (xpath = "//android.widget.Button[@text='Next']")
    WebElement clickOnNext;
	
	@AndroidFindBy (className = "android.widget.EditText")
   List< WebElement> Edittext;
	
	@AndroidFindBy (xpath = "//android.widget.Button[@text='Login']")
    WebElement clickOnLogIn;

	@AndroidFindBy (id = "com.app.hsense.compass:id/img_home")
    WebElement Statuslogin;

	public HelixSenseLoginPage(AndroidDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public void login(String username, String password) throws InterruptedException {
	//clickAllow.click();
		clickWhileUsingTheApp.click();
		clickWhileUsingTheApp.click();
		clickAllow.click();
		Thread.sleep(2000);
		waitForVisible(driver,enterAccountId);
		enterAccountId.sendKeys("QA11");
		clickOnNext.click();
		Thread.sleep(2000);
		Edittext.get(0).sendKeys(username);
		Edittext.get(1).sendKeys(password);
		clickOnLogIn.click();
		
	}
	public boolean getStatus() {
		
		return Statuslogin.isDisplayed();
	}
}
