package HelixSenseTests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import HelixSensePages.HelixSenseLoginPage;
import HelixSensePages.HelpDeskByQRCodePage;

public class HelpDeskByQRCodeTest extends HelixSenseTestBase{
	
	HelpDeskByQRCodePage helpDeskQRCode;

	
	@Test(priority=1)
	public void verifyHelpDeskRaiseTicketByUsingQRCode() throws InterruptedException
	{
		helpDeskQRCode=new HelpDeskByQRCodePage(driver);
		helpDeskQRCode.RaiseaTicketByQR();
		String Actual=helpDeskQRCode.getActual();
		String Excepted=helpDeskQRCode.getExcepted();
		Assert.assertEquals(Actual, Excepted,"ERROR!HelpDesk Raise a Ticket Using QR Code failed");
		testReport.info("Validate HelpDesk Raise a Ticket Using QRCode");	
	}
	@Test(priority=2)
	 public void HelixSenseLogIn() throws InterruptedException, IOException {
		loginpage=new HelixSenseLoginPage(driver);
		boolean status=loginpage.getStatus();
		Assert.assertTrue(false,"ERROR!Log in failed");
		testReport.info("User Login Successful");
		testReport.info("Validate HelixsenseApplogin ");
}
	
}
