package com.testvagrant.utils;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class invokeBrowser {

	public static WebDriver driver;

	public WebDriver invokeChromeBrowser() throws Exception {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\config.properties");
		Properties prop = new Properties();
		prop.load(fis);

		String driverPreference = prop.getProperty("driverPreference");
		

		if(driverPreference.equalsIgnoreCase("chrome")) {

			//Assign Chrome Browser
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");

			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver(options);

		}


		//Launch Cleartrip
		driver.manage().window().maximize();
		String baseUrl = prop.getProperty("baseUrl");
		driver.navigate().to(baseUrl);
		driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);

		return driver;
	}

}
