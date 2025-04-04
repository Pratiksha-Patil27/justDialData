package com.justdial.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }
    
    public void waitForVisibilityOfElementLocated(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForInvisibilityOfElementLocated(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
	public void click(By locator)
	{
		try
		{
		    wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
		    driver.findElement(locator).click();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		
	public void sendkeys(By locator, String text)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		driver.findElement(locator).sendKeys(text);
		
	}

}
