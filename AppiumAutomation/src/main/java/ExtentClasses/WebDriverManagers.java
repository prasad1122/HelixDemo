package ExtentClasses;

import java.io.File;
import java.io.IOException;	
import java.nio.file.Files;
import java.nio.file.Paths;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.android.AndroidDriver;




public class WebDriverManagers {
	
	public static void quitDriver(WebDriver driver) {
		// Do all clean up logic
		driver.quit();
	}

	public static void getScreenshot(AndroidDriver driver, String filePath) throws IOException {
		File imgFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(imgFile.toPath(), Paths.get(filePath));
	}

	public static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
