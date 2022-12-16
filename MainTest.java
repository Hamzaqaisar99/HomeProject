package homeproject;


import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MainTest {

	WebDriver driver = new ChromeDriver();
	POM obj;
	
	
	@BeforeTest
	public void verificationOfHomepage() throws InterruptedException
	{
		
		driver.manage().window().maximize();		
		PropertyConfigurator.configure("log4j.properties");
		driver.get("https://www.galaxy.pk/"); // getting path to open the site				
	}
	
	
	  @AfterTest public void closingWindow() throws InterruptedException {
	  Thread.sleep(1000); driver.close(); }
	 
	
	@Test
	public void Task2() throws InterruptedException, IOException, AWTException
	{
		obj = new POM(driver);
	    obj.hoverAndCLick();
	    obj.scrapingPage();
	    obj.imageDisplay();
	    
}
	
	@AfterTest 
	public void AfterTest() throws InterruptedException 
		{
			Thread.sleep(3000); 
		    driver.quit();
		}
}