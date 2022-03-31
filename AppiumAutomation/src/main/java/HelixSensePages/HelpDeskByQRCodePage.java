package HelixSensePages;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HelpDeskByQRCodePage extends HelixsensePageBase {
	
	String description1="TestChairTicket";

	@AndroidFindBy (xpath = "//android.widget.TextView[@text='Helpdesk']")
    WebElement clickOnHelpdesk;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text='Scan by QR Code']")
    WebElement clickOnSearchqrcode;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text='Furniture & Fitting']")
    WebElement clickOnFitting;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text='Broken Chair']")
    WebElement clickOnBrokeChair;
	
	@AndroidFindBy (xpath = "//android.widget.Button[@text='Select']")
    WebElement clickOnSelect;
	
	@AndroidFindBy (id = "com.app.hsense.compass:id/edt_desc")
    WebElement description;
	

	@AndroidFindBy (xpath = "//android.widget.Button[@text='Submit']")
    WebElement clickOnSubmit;

	@AndroidFindBy (xpath = "//android.widget.Button[@text='Ok']")
    WebElement clickOnOk;
	
	@AndroidFindBy (xpath = "//android.widget.TextView[@text='Go to New Tickets']")
    WebElement clickOnNewTickets;
	
	@AndroidFindBy (id = "com.app.hsense.compass:id/filter_search")
    WebElement search;
	 
	@AndroidFindBy (xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.RelativeLayout/androidx.appcompat.widget.LinearLayoutCompat/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.AutoCompleteTextView")
    WebElement searchFiledbox;
	
	@AndroidFindBy (xpath = "(//android.widget.ImageView[@content-desc=\"HSense\"])[5]")
    WebElement clickOnbutton;
	
	@AndroidFindBy (className = "android.widget.TextView")
    List<WebElement> testChairTicket;
	

	
	public HelpDeskByQRCodePage(AndroidDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}



	public void RaiseaTicketByQR() throws InterruptedException {
		clickOnHelpdesk.click();
		clickOnSearchqrcode.click();
		Thread.sleep(5000);
		clickOnFitting.click();
		clickOnBrokeChair.click();
		clickOnSelect.click();
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Enter description *\").instance(0))");
		
		description1=randomTicketName(description1);
		description.sendKeys(description1);
		clickOnSubmit.click();
		clickOnOk.click();
		clickOnNewTickets.click();
		search.click();
		waitForVisible(driver,searchFiledbox);
	
		searchFiledbox.sendKeys(description1);
		clickOnbutton.click();
	}

	public String getActual() throws InterruptedException {
				
		Thread.sleep(4000);
		return testChairTicket.get(2).getText();
	}

	public String getExcepted() {
		
		return description1;
	}
	


}
