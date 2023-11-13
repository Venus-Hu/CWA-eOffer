package util;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.List;


public class PageUtil {

	private final static Logger logger = LoggerFactory.getLogger(PageUtil.class);
	
//	private WebDriver driver;

	public enum PageIdentifier {
		title, text_in_page
	}

	/*
	public PageUtil(WebDriver driver) {
		this.driver = driver;
	}
	*/

	public static boolean isPage(WebDriver driver, PageIdentifier pi, final String value) {
		boolean result = false;
		switch (pi) {
		case title:
			(new WebDriverWait(driver, 30))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return d.getTitle().toLowerCase().startsWith(value.toLowerCase());
						}
					});
			result = driver.getTitle().toLowerCase().startsWith(value.toLowerCase());
		case text_in_page:
			(new WebDriverWait(driver, 30))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							return d.getPageSource().toLowerCase().contains(value.toLowerCase());
						}
					});
			result = driver.getPageSource().toLowerCase().contains(value.toLowerCase());
		default:
		}

		return result;
	}

	public static void clickSubmitButtonByNameAndVal(WebDriver driver, String buttonName, String buttonValue)
	{
		int count = 0;
		boolean clicked = false;
		while ( count < 5 )
		{
			try
			{
				List<WebElement> buttons = driver.findElements(By.name(buttonName));
				for (WebElement buttonElement : buttons)
				{
					String buttonString = buttonElement.getAttribute("value");
					boolean isDisplayed = buttonElement.isDisplayed();

					if ( isDisplayed == true && buttonString != null && buttonString.trim().equalsIgnoreCase(buttonValue))
					{
						buttonElement.click();
						clicked = true;
						count = 5;
					}
				}
			}
			catch(org.openqa.selenium.StaleElementReferenceException ex)
			{
				//ex.printStackTrace();
				// do nothing;
			}
			count++;
		}
		if(!clicked)
			throw new NoSuchElementException("Submit button with value: " + buttonValue + " not found.");
	}
	

	public static void clickSubmitButtonByVal(WebDriver driver, String buttonValue)
	{
		int count = 0;
		boolean clicked = false;
		while ( count < 5 )
		{
			try
			{
			List<WebElement> buttons = driver.findElements(By.cssSelector("input[type='submit']"));
			for (WebElement buttonElement : buttons)
			{
				String buttonString = buttonElement.getAttribute("value");
				if (buttonString != null && buttonString.trim().equalsIgnoreCase(buttonValue))
				{
					buttonElement.click();
					clicked = true;
					count = 5;
				}
			}
			}
			catch(org.openqa.selenium.StaleElementReferenceException ex)
			{
				//ex.printStackTrace();
				// do nothing;
			}
			count++;
		}
		if(!clicked)
			throw new NoSuchElementException("Submit button with value: " + buttonValue + " not found.");
	}
	
	public static void clickYesOrNoRadioButton(WebDriver driver, String elementName, String elementValue) {
	    List<WebElement> radio_Buttons = driver.findElements(By.name(elementName));
	    if(elementValue.equalsIgnoreCase("yes")) {
	    	radio_Buttons.get(0).click();
	    } else if (elementValue.equalsIgnoreCase("no")) {
	    	radio_Buttons.get(1).click();
	    } else {
	    	//System.out.println("Click RadioButton - elementName = |" + elementName + "| elementValue = |" + elementValue + "|");
	    }
	}

	public static boolean containsText(WebDriver driver, String lookupStr) {
		return driver.getPageSource().contains(lookupStr);
	}

	public static void pageDump(WebDriver driver) throws InterruptedException {
		logger.info("PageDump"	+ driver.getPageSource());
	}

	public static void clickCheckBox(WebDriver driver, String elementName, String elementValue) {
	    WebElement checkbox_Button = driver.findElement(By.name(elementName));
	    // First clear the checkbox
	    if ( checkbox_Button.isSelected() )
	    	checkbox_Button.click();

	    if ( elementValue.equalsIgnoreCase("yes"))
	    	checkbox_Button.click();
	}
	
	
	/**
	 * checkPage load is only checking the dom state as complete which selenium is also doing that
	 * for jquery related application use checkPageLoadJquery method
	 * @param driver
	 */
	public static void checkPageLoad(WebDriver driver)
	{
		WebDriverWait wait = new WebDriverWait(driver, 30);
	    wait.until(new ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {
	            return ((JavascriptExecutor) driver).executeScript(
	                "return document.readyState"
	            ).equals("complete");
	        }
	    });
	}

	public boolean getTitleTextEqualsForAngular(WebDriver driver,String text,int timeOutInSecs)
	{
		PageLoad.checkPageLoad(driver,timeOutInSecs);
		PageLoad.checkPageLoadAngular(driver,timeOutInSecs);
		return driver.getTitle().equalsIgnoreCase(text);
	}

	public boolean getTitleTextContainsForAngular(WebDriver driver,String text,int timeOutInSecs)
	{
		PageLoad.checkPageLoad(driver,timeOutInSecs);
		PageLoad.checkPageLoadAngular(driver,timeOutInSecs);
		return driver.getTitle().contains(text);
	}

	public boolean getTitleTextEquals(WebDriver driver,String text,int timeOutInSecs)
	{
		PageLoad.checkPageLoad(driver,timeOutInSecs);
		return driver.getTitle().equalsIgnoreCase(text);
	}
	
	public static boolean getTitleTextEquals_tempFix(WebDriver driver,String text,int timeOutInSecs)
	{
		PageLoad.checkPageLoad(driver,timeOutInSecs);
		return driver.getTitle().equalsIgnoreCase(text);
	}

	public boolean getTitleTextContains(WebDriver driver,String text,int timeOutInSecs)
	{
		PageLoad.checkPageLoad(driver,timeOutInSecs);
		return driver.getTitle().contains(text);
	}

	public static void verifyTitleIs(WebDriver driver,String title,int timeOutInSecs){
		new WebDriverWait(driver,timeOutInSecs).until(ExpectedConditions.titleIs(title));
		Assert.assertTrue(driver.getTitle().equals(title));
	}

	public static void verifyTitleContains(WebDriver driver,String title,int timeOutInSecs){
		new WebDriverWait(driver,timeOutInSecs).until(ExpectedConditions.titleIs(title));
		Assert.assertTrue(driver.getTitle().contains(title));
	}

	public void uploadFile(WebDriver driver,URL dataFileURL, String browser, String location, String input,By locator){
		if (browser.equalsIgnoreCase("chrome")) {
			File file = new File(dataFileURL.getFile());
			ActionByLocator.type(driver, locator, file.getAbsolutePath(), 60);
		} else if (browser.equalsIgnoreCase("firefox")){
			{
				if (location.equalsIgnoreCase("remote") )
				{
					ActionByLocator.type(driver, locator, dataFileURL.getPath(), 60);
				}
				else
				if (location.equalsIgnoreCase("local") )
				{
					ActionByLocator.type(driver, locator, dataFileURL.toString(), 60);
				}
			}
		}

	}

	public static void clearRadioElement(WebDriver driver, String string, String name) {
		// This is place holder - Nur
		
	}
}
