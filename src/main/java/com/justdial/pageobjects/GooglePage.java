package com.justdial.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GooglePage extends BasePage{
WebDriver driver;
int justDialResultCount = 0; 

@FindBy(id="APjFqb")
private WebElement searchEnginField;

@FindBy(xpath="//a[@jsname='UWckNb']")
private List<WebElement> urlResults;

@FindBy(xpath="//a[@jsname='UWckNb']/h3")
private List<WebElement> urlTextResults;


@FindBy(xpath="//span[@class='ExCKkf z1asCe rzyADb']")
private WebElement searchFieldCrossIcon;


public GooglePage(WebDriver driver)
{
	super(driver);
}


public void searchDataBySearchEngine(String searchDataText)
{
	searchEnginField.sendKeys(searchDataText);
	searchEnginField.submit();
}

public List<String[]> getResultsBySearchData(String StateName,String District ) {
	 List<String[]> data = new ArrayList<>();
	waitForVisibilityOfElementLocated(By.xpath("//a[@jsname='UWckNb']"));
	

    
    
	for (int i = 0; i < urlResults.size(); i++) {
		WebElement urlResult = urlResults.get(i);
		String urlText = urlResult.getAttribute("href");

        
		if (urlText.contains("justdial.com")) {
			justDialResultCount++;
			System.out.println(urlText);
			waitForVisibilityOfElementLocated(By.xpath("//a[@jsname='UWckNb']/h3"));

			if (i < urlTextResults.size()) {
				WebElement urlTextResult = urlTextResults.get(i);
				String textResult=urlTextResult.getText();
				
				data.add(new String[]{StateName,District,urlText, textResult});
			}
		}
	}	
	return data;
}

public void clearSearchDataByClickonCrossIcon()
{
	waitForVisibilityOfElementLocated(By.xpath("//span[@class='ExCKkf z1asCe rzyADb']"));
	searchFieldCrossIcon.click();
	
}

public int totalResultsOfJustdial()
{
	return justDialResultCount;
}


}
