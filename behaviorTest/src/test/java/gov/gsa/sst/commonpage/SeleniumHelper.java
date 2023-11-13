package gov.gsa.sst.commonpage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import customUtility.Octo_SeleniumLibrary;

public class SeleniumHelper {

	public static void clickById_tempFix(WebDriver driver, String id, int TIMEOUT_IN_SECS) {
		clickElement_tempFix(driver, By.id(id));
	}

	public static void clickByXpath_tempFix(WebDriver driver, String xpath, int TIMEOUT_IN_SECS) {
		clickElement_tempFix(driver, By.xpath(xpath));
	}

	public static void clickByName_tempFix(WebDriver driver, String name, int TIMEOUT_IN_SECS) {
		clickElement_tempFix(driver, By.name(name));
	}

	public static void clickByLocator_tempFix(WebDriver driver, By locator, int TIMEOUT_IN_SECS) {
		clickElement_tempFix(driver, locator);
	}

	public static void clickAndContinueByLocator_tempFix(WebDriver driver, By locator) {
		try {
			findElem_tempFix(driver, locator).click();
		} catch (Exception e) {
			System.out.println("********* Click & Continue - unable to click on " + locator.toString());
		}
	}

	public static void clickNestElement_tempFix(WebDriver driver, WebElement elem) {
		scrollToElement_tempFix(driver, elem);
		JS_click(driver, elem);
	}

	public static void clickNestElement_tempFix(WebDriver driver, WebElement elem, By locator) {
		WebElement target = elem.findElement(locator);
		scrollToElement_tempFix(driver, target);
		JS_click(driver, elem);
	}

	static void clickElement_tempFix(WebDriver driver, By locator) {
		WebElement elem = findElem_tempFix(driver, locator);
		scrollToElement_tempFix(driver, elem);
//		elem.click();	
		JS_click(driver, elem);
	}

	public static void sendKey_tempFix(WebDriver driver, By locator, String input) {

		WebElement elem = findElem_tempFix(driver, locator);
		elem.sendKeys(input);
		WebElement logo = findElem_tempFix(driver, By.id("logo"));
		actionClick(driver, logo);

	}

	public static void sendKeyById_tempFix(WebDriver driver, String id, String input) {

		sendKey_tempFix(driver, By.id(id), input);
	}
	
	public static void clear_tempFix(WebDriver driver, By locator) {

		WebElement elem = findElem_tempFix(driver, locator);
		scrollToElement_tempFix(driver, elem);
		elem.clear();
		
	}
	
	public static void clear_tempFix(WebDriver driver,WebElement elem) {
		
		scrollToElement_tempFix(driver, elem);
		elem.clear();
		
	}
	
	public static String getTextByLocator(WebDriver driver, By locator) {
		return findElem_tempFix(driver, locator).getText();
	}

	public static void actionClick(WebDriver driver, WebElement elem) {
		Actions ac = new Actions(driver);
		ac.moveToElement(elem).click(elem).build().perform();
	}

	public static void clickHiddenElement(WebDriver driver, String locatorValue, String locatorType) {
		By locator = null;
		switch (locatorType.toLowerCase()) {
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "id":
			locator = By.id(locatorValue);
			break;
		}
		WebElement elem = findElem_tempFix(driver, locator);
		JS_click(driver, elem);
	}

	public static void scrollDown(WebDriver driver, int high) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + high + ")", "");
		customWait(1);
	}

	static void JS_click(WebDriver driver, WebElement elem) {
		System.out.println("************** try to JSclick " + elem.toString());
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].click();", elem);
	}

	static void scrollToElement_tempFix(WebDriver driver, WebElement elem) {
		try {
			Actions ac = new Actions(driver);
			ac.moveToElement(elem);
		} catch (Exception e) {
			System.out.println("********unable to move to element+ " + elem.toString());
		}
	}

	public static WebElement findElem_tempFix_public(WebDriver driver, By locator) {
		return findElem_tempFix(driver, locator);
	}

	public static WebElement findElem_tempFix(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElement(locator);
	}

	static List<WebElement> findElems_tempFix(WebDriver driver, By locator) {
//		WebDriverWait wait = new WebDriverWait(driver, 10);
//		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElements(locator);
	}

	public static void customWait(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static String getTagName_tempFix(WebDriver driver, String elementName) {
		return findElem_tempFix(driver, By.name(elementName)).getTagName();
	}

	static String getAttribute_tempFix(WebDriver driver, By locator, String attributeType) {
		return findElem_tempFix(driver, locator).getAttribute(attributeType);
	}

	static void waitElelement_tempFix(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	static void selectByLocator_tempFix(WebDriver driver, By locator, String value, String type) {
		WebElement elem = findElem_tempFix(driver, locator);
		Select mySelect = new Select(elem);
		System.out.println("************ Select by ****************" + value + type);
		if (type.equalsIgnoreCase("text")) {
			mySelect.selectByVisibleText(value);
		} else if (type.equalsIgnoreCase("value")) {
			mySelect.selectByValue(value);
		} else {
			mySelect.selectByIndex(Integer.getInteger(value));
		}
	}

	/** for performance class */
	public static void performActionByLocatorName(WebDriver driver, String elementName, String value) {

		By locator = By.name(elementName);
		String tagName = getTagName_tempFix(driver, elementName);
		waitElelement_tempFix(driver, locator);
		switch (tagName.toLowerCase()) {
		case "select": {
			try {
				selectByLocator_tempFix(driver, locator, value, "text");
			} catch (Exception e) {
				selectByLocator_tempFix(driver, locator, value, "value");
			}
			break;
		}
		case "input": {
			String type = getAttribute_tempFix(driver, locator, "type");

			if (type.equalsIgnoreCase("text")) {
				driver.findElement(By.name((String) elementName)).clear();
				sendKey_tempFix(driver, locator, value);
				break;
			}
			if (type.equalsIgnoreCase("radio")) {
				if (value.equalsIgnoreCase("Yes")) {
					clickElement_tempFix(driver, locator);
				}
				break;
			}
			if (type.equalsIgnoreCase("checkbox")) {
				if (value.equalsIgnoreCase("Yes")) {
					if (driver.findElement(locator).isSelected())
						break;
					clickElement_tempFix(driver, locator);
					break;
				}
				if (!value.equalsIgnoreCase("No") || !driver.findElement(locator).isSelected())
					break;
				clickElement_tempFix(driver, locator);
				break;
			}
			if (type.equalsIgnoreCase("button")) {
				clickElement_tempFix(driver, locator);
				break;
			}
			if (!type.equalsIgnoreCase("a"))
				break;
			break;
		}
		case "textarea": {
			driver.findElement(By.name((String) elementName)).clear();
			sendKey_tempFix(driver, locator, value);
		}
		}
	}

	public static void clickCheckBoxByName_tempFix(WebDriver driver, String elementName, String value) {
		WebElement checkbox_Button = findElem_tempFix(driver, By.name(elementName));
		try {
			if (value.equalsIgnoreCase("yes") && !checkbox_Button.isSelected()) {
				checkbox_Button.click();
			} else if (checkbox_Button.isSelected()) {
				checkbox_Button.click();
			}
		} catch (Exception e) {
			scrollDown(driver, 400);
			System.out.println("********** Check box scroll down*******");
			checkbox_Button.click();
		}
	}
	
	public static void clickCheckBoxById_tempFix(WebDriver driver, String elementName, String value) {
		WebElement checkbox_Button = findElem_tempFix(driver, By.id(elementName));
		try {
			if (value.equalsIgnoreCase("yes") && !checkbox_Button.isSelected()) {
				checkbox_Button.click();
			} else if (checkbox_Button.isSelected()) {
				checkbox_Button.click();
			}
		} catch (Exception e) {
			scrollDown(driver, 400);
			System.out.println("********** Check box scroll down*******");
			checkbox_Button.click();
		}
	}

	public static Boolean verifyElementExist(WebDriver driver, By locator) {

		return findElems_tempFix(driver, locator).size() > 0;

	}

	public static void validateCheckBoxisSelected(WebDriver driver, String elementName, String value) {

		WebElement checkBoxElement = driver.findElement(By.id(elementName));
		if (checkBoxElement.getAttribute("checked") != null) // if Checked
			checkBoxElement.click();
		else {
			boolean isSelected = checkBoxElement.isSelected();
			if (isSelected == false) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "pathwayToSuccessCertAction_pathwayAcknowledged"));
			}
		}

	}
	
	/***
	 * This method highlights web-elements using javascript executon
	 * 
	 * @param by
	 */

	public static void highLightElement(WebDriver driver, By by) {
		
			try {
				WebElement temp = driver.findElement(by);
				
				JavascriptExecutor js = (JavascriptExecutor) driver;

				for (int i = 0; i < 3; i++) {
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", temp,
							"color: red; border: 2px solid yellow;");
					customWait(1);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", temp, "");
					customWait(1);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	
	/***
	 * This method highlights web-elements using javascript executon
	 * 
	 * @param by
	 */

	public static void highLightElement(WebDriver driver, WebElement elem) {
		
			try {
				
				JavascriptExecutor js = (JavascriptExecutor) driver;

				for (int i = 0; i < 3; i++) {
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", elem,
							"color: red; border: 2px solid yellow;");
					customWait(1);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", elem, "");
					customWait(1);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

}
