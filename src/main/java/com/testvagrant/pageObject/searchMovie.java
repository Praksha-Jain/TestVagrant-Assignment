package com.testvagrant.pageObject;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testvagrant.pageObject.PageObject;
import com.testvagrant.utils.ReadInputData;

public class searchMovie 
{
	WebDriver driver;
	PageObject pob;
	String  movie, releaseDateIMDB,releaseDateWiki,countryIMDB,countryWiki;
	//***********Constructor to initialize the driver**************

	public searchMovie(WebDriver driver) throws Exception
	{
		this.driver = driver;
		pob = new PageObject(driver);

	}



	public void getData(String str) throws Exception
	{
		ReadInputData read = new ReadInputData();
		movie = read.readData(str);
		System.out.println("Movie Name: "+movie);
	}

	public void searchMovieDetails(String platform)
	{
		try {
			search(platform);
			getReleaseDate(platform);
			getCountry(platform);

		}
		catch(Exception e)
		{
			System.out.print(e);
		}
	}

	public void navigateToWiki()
	{
		driver.navigate().to("https://en.wikipedia.org/wiki/India");
	}


	public void search(String platform) 
	{
		try {
			if(platform.equalsIgnoreCase("IMDB"))
			{
				PageObject.imdbSearch.click();
				PageObject.imdbSearch.sendKeys(movie);
				Thread.sleep(3000);
				PageObject.selectFirstIMDB.click();
			}
			else if(platform.equalsIgnoreCase("Wikipedia"))
			{
				PageObject.searchWikipedia.click();
				PageObject.searchWikipedia.sendKeys(movie);
				Thread.sleep(3000);
				PageObject.selectFirstWiki.click();
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public void getCountry(String platform) 
	{
		try {
			if(platform.equalsIgnoreCase("IMDB"))
			{

				countryIMDB = PageObject.countryIMDB.getText();
				System.out.println("Country IMDB: "+countryIMDB);
				Thread.sleep(3000);
			}
			else if(platform.equalsIgnoreCase("Wikipedia"))
			{
				countryWiki=PageObject.countryWiki.getText();
				System.out.println("Country Wiki: "+countryWiki);
				Thread.sleep(3000);
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}

	}



	public void getReleaseDate(String platform)
	{
		try {
			JavascriptExecutor j = (JavascriptExecutor) driver;
			if(platform.equalsIgnoreCase("IMDB"))
			{

				j.executeScript("arguments[0].scrollIntoView();",PageObject.releaseDateIMDB);

				releaseDateIMDB = PageObject.releaseDateIMDB.getText();
				System.out.println("Release Date IMDB: "+releaseDateIMDB);
			}
			else if(platform.equalsIgnoreCase("Wikipedia"))
			{
				j.executeScript("arguments[0].scrollIntoView();",PageObject.releaseDateWiki);
				releaseDateWiki=PageObject.releaseDateWiki.getText();
				System.out.println("Release Date Wikipedia: "+releaseDateWiki);
			}
		}catch(Exception e)
		{
			System.out.println(e);
		}

	}
	
	public boolean compareValues()
	{
		System.out.println(releaseDateIMDB);
		String[] dateIMDB=releaseDateIMDB.split(" ");
		String[] dateWiki=releaseDateWiki.split(" ");

		dateIMDB[1]=dateIMDB[1].substring(0, 2);
		//System.out.println(dateIMDB[1]);
		
		
		dateWiki[2]=dateWiki[2].substring(0, 4);
		//System.out.println(dateWiki[2]);
		
		if((countryWiki.equalsIgnoreCase(countryIMDB)) && (dateIMDB[0].equalsIgnoreCase(dateWiki[1]) && dateIMDB[1].equalsIgnoreCase(dateWiki[0]) && dateIMDB[2].equalsIgnoreCase(dateWiki[2])))
			return true;
		else
			return false;
		
		
		
	}
	
	


}
