package com.justdial.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.justdial.base.BaseClass;
import com.justdial.pageobjects.GooglePage;
import com.justdial.utilities.ExcelDataUtil;
import com.justdial.utilities.MyXLSReader;
import com.justdial.utilities.Utils;

public class JustDialDataChrome extends BaseClass {
	GooglePage googlepage;
	MyXLSReader excelReader;
	Utils utils = new Utils();
	List<String[]> allData = new ArrayList<>();

	private boolean isHeaderWritten = false; // Flag to check if headers are written

	@BeforeSuite
	public void launch() throws InterruptedException {

		// launchBrowser("chrome");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-blink-features=AutomationControlled");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();

	}

	@BeforeTest
	
	public void searchOnGoogle() throws InterruptedException, IOException {

		driver.navigate().to(getProperty("googleURL"));
		googlepage = new GooglePage(driver);

	}

	
	@Test(dataProvider = "datasupplier")
	public void verifyResultsOnGooglePage(HashMap<String, String> hashMap) throws InterruptedException {
		int totalResultsCount=0;
		try { 

			googlepage = new GooglePage(driver);
			Random rand = new Random();
            int delay = rand.nextInt(5000) + 3000; // Random delay between 3-8 seconds
            Thread.sleep(delay);
			// To verify In excel This TestCase Data is runable or not
			if (!ExcelDataUtil.isRunnable(excelReader, "SearchTest", "TestCases") || hashMap.get("Runmode").equals("N")) {
				throw new SkipException("Run mode is set to N, heance not excuted");
			

			}
			 Thread.sleep(3000);
			googlepage.searchDataBySearchEngine(
					"site:Justdial.com Wood and Timber Contractor in " + hashMap.get("DISTRICT NAME"));

            
			List<String[]> data = googlepage.getResultsBySearchData(hashMap.get("STATE NAME"),hashMap.get("DISTRICT NAME"));

			// Write the header row once
			if (!isHeaderWritten) {
				allData.add(new String[] { "State Name","District Name", "URL", "URL Title" });
				isHeaderWritten = true;
			}
			Thread.sleep(delay);
			// Append data for the current district
			allData.addAll(data);
			
			 totalResultsCount = googlepage.totalResultsOfJustdial();
			
			System.out.println("Total Count of JustDial site on Frist Page for District : "
					+ hashMap.get("DISTRICT NAME") + " : " + totalResultsCount);
			 Thread.sleep(3000);
			
		} catch (Exception e) {

			System.err.println("Exception during test execution: " + e.getMessage());
		}
		
		Thread.sleep(5000);
		googlepage.clearSearchDataByClickonCrossIcon();
		
		
	}

	@AfterClass
	public void tearDown() throws InterruptedException, IOException {
		String filePath = System.getProperty("user.dir") + getProperty("justDialExcelDataFileChrome");
		System.out.println(filePath);
		String sheetName =getProperty("justDialSheetName");
		System.out.println(sheetName);
		ExcelDataUtil.writeDataInExcel(allData, filePath, sheetName);
		driver.quit();
	}

	@DataProvider
	public Object[][] datasupplier() {

		Object[][] data = null;
		try {

			excelReader = new MyXLSReader(
					System.getProperty("user.dir") + "\\src\\test\\resource\\excelDataPassChrome.xlsx");

			data = ExcelDataUtil.getTestData(excelReader, "SearchTest", "DistrictName");

		} catch (Throwable e) {

			e.printStackTrace();
		}
		return data;
	}
}
