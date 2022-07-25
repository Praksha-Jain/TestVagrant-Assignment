package com.testVagrant.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.testvagrant.pageObject.searchMovie;
import com.testvagrant.utils.ExtentReport;
import com.testvagrant.utils.ScreenShot;
import com.testvagrant.utils.invokeBrowser;



public class TestCases 
{

	WebDriver driver;
	invokeBrowser browser = new invokeBrowser();

	static ExtentReports extent = ExtentReport.getReport();
	searchMovie sf;

	@BeforeClass(alwaysRun=true)
	//Invoke Browser
	public void openBrowser() throws Exception 
	{
		driver = browser.invokeChromeBrowser();
		sf = new searchMovie(driver);
	}

	@Test (priority = 0)
	//homePageTitle Test - Pass if title contains "#1 Site for Booking" else fail
	public void homePageTitleIMDB() throws Exception {

		ExtentTest test = extent.createTest("Homepage Title - IMDB");

		String homePageTitle = driver.getTitle();

		boolean a = false;
		if (homePageTitle.contains("IMDb")) {

			a = true;
			test.log(Status.PASS, "Homepage title contains - IMDb");
			ScreenShot ss = new ScreenShot(driver);
			ss.captureScreen();
			test.pass("Check screenshot for title", MediaEntityBuilder.createScreenCaptureFromPath(ss.screenshotPath).build());

		}
		else {
			test.log(Status.FAIL, "Homepage title doesn't contain - IMDb");
			ScreenShot ss = new ScreenShot(driver);
			ss.captureScreen();
			test.fail("Check screenshot for failure", MediaEntityBuilder.createScreenCaptureFromPath(ss.screenshotPath).build());

		}

		Assert.assertTrue(a);
	}	

	@Test (priority = 1, groups = "smoke", dependsOnMethods = {"homePageTitleIMDB"})
	//Login with credentials - username & password

	public void searchMovieIMDb() throws Exception 
	{

		ExtentTest test = extent.createTest("Search Movie- IMDb");


		sf.getData("Movie");
		sf.searchMovieDetails("IMDB");

		//Assert.assertTrue(a);

	}

	@Test (priority = 2)
	//homePageTitle Test - Pass if title contains "#1 Site for Booking" else fail
	public void homePageTitleWiki() throws Exception {

		ExtentTest test = extent.createTest("Homepage Title - Wiki");


		sf.navigateToWiki();
		String homePageTitle = driver.getTitle();

		boolean a = false;
		if (homePageTitle.contains("Wikipedia")) {

			a = true;
			test.log(Status.PASS, "Homepage title contains - Wikipedia");
			ScreenShot ss = new ScreenShot(driver);
			ss.captureScreen();
			test.pass("Check screenshot for title", MediaEntityBuilder.createScreenCaptureFromPath(ss.screenshotPath).build());

		}
		else {
			test.log(Status.FAIL, "Homepage title doesn't contain - Wikipedia");
			ScreenShot ss = new ScreenShot(driver);
			ss.captureScreen();
			test.fail("Check screenshot for failure", MediaEntityBuilder.createScreenCaptureFromPath(ss.screenshotPath).build());

		}

		Assert.assertTrue(a);
	}	

	@Test (priority = 3, groups = "smoke", dependsOnMethods = {"homePageTitleWiki"})
	public void searchMovieWiki() throws Exception 
	{

		ExtentTest test = extent.createTest("Search Movie- Wiki");


		sf.getData("Movie");
		sf.searchMovieDetails("Wikipedia");

		//Assert.assertTrue(a);

	}

	@Test (priority = 4, groups = "smoke", dependsOnMethods = {"searchMovieWiki","searchMovieIMDb"})
	public void compareMovieDetails() throws Exception 
	{

		ExtentTest test = extent.createTest("Search Movie- Wiki");

		boolean a = sf.compareValues();

		if (a) 
		{
			test.log(Status.PASS, "Release Date and Country are same");
		}
		else 
		{
			test.log(Status.FAIL, "Release Date and Country are not same");
			ScreenShot ss = new ScreenShot(driver);
			ss.captureScreen();
			test.fail("Check screenshot for failure", MediaEntityBuilder.createScreenCaptureFromPath(ss.screenshotPath).build());

		}

		Assert.assertTrue(a);

	}
	@AfterMethod
	//To write and generate each test case in extent report
	public void extentWrite() 
	{
		extent.flush();
	}
	
	@AfterClass(alwaysRun=true)
	//Close Browser
	
	public void closeBrowser() 
	{
		
		driver.quit();
	}
}
