package com.justdial.testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.justdial.base.BaseClass;

public class loginTest extends BaseClass {
	 //public WebDriver driver; 
	
	@BeforeClass
	public void launch()
	{
		launchBrowser("chrome");
		   driver.navigate().to("https://jsplrjtest.prowessbeat.net");
		
	}
  
  
  

 @Test(priority=1,dataProvider = "invalidloginData")
  public void loginByInvalidCredentials(String username, String password) {
	 
	  SoftAssert softAssert=new SoftAssert();
	  WaitVisibliltyOfLocated(By.name("email"));
      driver.findElement(By.name("email")).sendKeys(username);
      WaitVisibliltyOfLocated(By.name("password"));
      driver.findElement(By.name("password")).sendKeys(password);
      WaitVisibliltyOfLocated(By.xpath("//button[@type='submit']"));
      driver.findElement(By.xpath("//button[@type='submit']")).click();
    
      WaitVisibliltyOfLocated(By.xpath("//div[contains(text(), 'Username and password are invalid')]"));
      WebElement alertElement=driver.findElement(By.xpath("//div[contains(text(), 'Username and password are invalid')]"));
     
      softAssert.assertTrue(alertElement.isDisplayed());
      softAssert.assertEquals(alertElement.getText(),"Username and password are invalid. Please enter correct username and password");
      driver.navigate().refresh();
      softAssert.assertAll();
      
  }
  
  @Test(priority=2,dataProvider = "blankloginData")
  public void loginByBlankCredentials(String username, String password) {
	 SoftAssert softAssert=new SoftAssert();
	  WaitVisibliltyOfLocated(By.name("email"));
      driver.findElement(By.name("email")).sendKeys(username);
      WaitVisibliltyOfLocated(By.name("password"));
      driver.findElement(By.name("password")).sendKeys(password);
      WaitVisibliltyOfLocated(By.xpath("//button[@type='submit']"));
      driver.findElement(By.xpath("//button[@type='submit']")).click();
      
      WaitVisibliltyOfLocated(By.xpath("//div[contains(text(), \"Please Enter Your\")]"));
      WebElement alertElement=driver.findElement(By.xpath("//div[contains(text(), 'Please Enter Your')]"));
     
    softAssert.assertTrue(alertElement.isDisplayed());
    driver.navigate().refresh();
    softAssert.assertAll();
    
  }
  
  @Test(priority=3,dataProvider = "validloginData")
  public void loginByValidCredentials(String username, String password) {
	 SoftAssert softAssert=new SoftAssert();
	  WaitVisibliltyOfLocated(By.name("email"));
      driver.findElement(By.name("email")).sendKeys(username);
      WaitVisibliltyOfLocated(By.name("password"));
      driver.findElement(By.name("password")).sendKeys(password);
      WaitVisibliltyOfLocated(By.xpath("//button[@type='submit']"));
      driver.findElement(By.xpath("//button[@type='submit']")).click();
      
      WaitVisibliltyOfLocated(By.xpath("//*[@id=\"page-header-user-dropdown\"]/span"));
      WebElement alertElement=driver.findElement(By.xpath("//*[@id=\"page-header-user-dropdown\"]/span"));
     
    softAssert.assertTrue(alertElement.isDisplayed());
    softAssert.assertAll();
    
  }
  
  @DataProvider(name = "invalidloginData")
 public Object[][] invalidData() {
	return new Object[][]{
	  {"6001" ,"admin123"},
	  {"admin", "tELE@6001"},
	  {"admin", "admin123"}
	  
	 
  
  
  };
  }

  @DataProvider(name = "blankloginData")
  public Object[][] blankData() {
 	return new Object[][]{
  
	  {"","tELE@6001"},
	  {"6001",""},
	  {"",""},
	 
  };
  
    }
  
  @DataProvider(name = "validloginData")
  public Object[][] validData() {
 	return new Object[][]{
	  {"6001","tELE@600111"}
  };
  }
  
  @AfterClass
  public void closed()
  {
	driver.quit();
  }
  
}
