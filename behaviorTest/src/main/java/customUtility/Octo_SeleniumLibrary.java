package customUtility;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Octo_SeleniumLibrary {
	
	WebDriver driver_willUseLater;
	
	public Octo_SeleniumLibrary(WebDriver driver) {
		driver_willUseLater = driver;
	}

	
	



	public static void clickElement(WebDriver driver, By locator) {
		WebElement elem = findElem(driver, locator);
		scrollToElement(driver, elem);
//		elem.click();	
		JS_click(driver, elem);
	}
	
	public static void clickElementByIndex(WebDriver driver, By locator, int index) {
		WebElement elem = findElems(driver, locator).get(index);
		scrollToElement(driver, elem);
//		elem.click();	
		JS_click(driver, elem);
	}

	public static void clickAndContinue(WebDriver driver, By locator) {
		try {
			findElem(driver, locator).click();
		} catch (Exception e) {
			System.out.println("********* Click & Continue - unable to click on " + locator.toString());
		}
	}


	public static void clickNestElement(WebDriver driver, WebElement elem, By locator) {
		WebElement target = elem.findElement(locator);
		scrollToElement(driver, target);
		JS_click(driver, elem);
	}



	public static void sendKey(WebDriver driver, By locator, String input) {

		WebElement elem = findElem(driver, locator);
		elem.sendKeys(input);
//		WebElement logo = findElem(driver, By.id("logo"));
//		actionClick(driver, logo);

	}
	
	public static void sendKey(WebDriver driver, WebElement elem, String input) {

		elem.sendKeys(input);
//		WebElement logo = findElem(driver, By.id("logo"));
//		actionClick(driver, logo);

	}

	
	public static void clear(WebDriver driver, By locator) {

		WebElement elem = findElem(driver, locator);
		scrollToElement(driver, elem);
		elem.clear();
		
	}
	
	public static void clear(WebDriver driver,WebElement elem) {
		
		scrollToElement(driver, elem);
		elem.clear();
		
	}
	
	public static String getText(WebDriver driver, By locator) {
		return findElem(driver, locator).getText();
	}

	public static void actionClick(WebDriver driver, WebElement elem) {
		Actions ac = new Actions(driver);
		ac.moveToElement(elem).click(elem).build().perform();
	}
	public static void actionClick(WebDriver driver, By locator) {
		WebElement elem = findElem(driver, locator);
		Actions ac = new Actions(driver);
		ac.moveToElement(elem).click(elem).build().perform();
		
	}

	public static void clickHiddenElement(WebDriver driver, By locator) {
		WebElement elem = findElem(driver, locator);
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

	static void scrollToElement(WebDriver driver, WebElement elem) {
		try {
			Actions ac = new Actions(driver);
			ac.moveToElement(elem);
		} catch (Exception e) {
			System.out.println("********unable to move to element+ " + elem.toString());
		}
	}

	public static WebElement findElem(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return driver.findElement(locator);
	}

	static List<WebElement> findElems(WebDriver driver, By locator) {
		waitElelement(driver,locator);
		return driver.findElements(locator);
	}

	public static void customWait(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	static String getTagName(WebDriver driver, By locator) {
		return findElem(driver, locator).getTagName();
	}

	static String getAttribute(WebDriver driver, By locator, String attributeType) {
		return findElem(driver, locator).getAttribute(attributeType);
	}

	static void waitElelement(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	static void selectByLocator(WebDriver driver, By locator, String value, String type) {
		WebElement elem = findElem(driver, locator);
		Select mySelect = new Select(elem);
		System.out.println("************ Select by ****************" + value +"  with "+ type);
		if (type.equalsIgnoreCase("text")) {
			selectByVisibleText(driver, locator, value);
		} else if (type.equalsIgnoreCase("value")) {
			selectByValue(driver, locator, value);
		} else {
			selectByIndex(driver, locator, Integer.getInteger(value));
		}
	}
	
	static void selectByIndex(WebDriver driver, By locator, int index) {
		WebElement elem = findElem(driver, locator);
		Select mySelect = new Select(elem);
		System.out.println("************ Select by Index****************" + index );
		mySelect.selectByIndex(index);
	}
	
	static void selectByValue(WebDriver driver, By locator, String value) {
		WebElement elem = findElem(driver, locator);
		Select mySelect = new Select(elem);
		System.out.println("************ Select by Value**************** " + value );
		mySelect.selectByValue(value);
	}
	
	static void selectByVisibleText(WebDriver driver, By locator, String text) {
		WebElement elem = findElem(driver, locator);
		Select mySelect = new Select(elem);
		System.out.println("************ Select by Visible Text**************** locator: "+locator.toString()+"  text: " + text );
		mySelect.selectByVisibleText(text);
	}

	/** for performance class */
	public static void performActionByLocatorName(WebDriver driver, By locator, String value) {

		String tagName = getTagName(driver, locator);
		waitElelement(driver, locator);
		switch (tagName.toLowerCase()) {
		case "select": {
			try {
				selectByLocator(driver, locator, value, "text");
			} catch (Exception e) {
				selectByLocator(driver, locator, value, "value");
			}
			break;
		}
		case "input": {
			String type = getAttribute(driver, locator, "type");

			if (type.equalsIgnoreCase("text")) {
				driver.findElement(locator).clear();
				sendKey(driver, locator, value);
				break;
			}
			if (type.equalsIgnoreCase("radio")) {
				if (value.equalsIgnoreCase("Yes")) {
					clickElement(driver, locator);
				}
				break;
			}
			if (type.equalsIgnoreCase("checkbox")) {
				if (value.equalsIgnoreCase("Yes")) {
					if (driver.findElement(locator).isSelected())
						break;
					clickElement(driver, locator);
					break;
				}
				if (!value.equalsIgnoreCase("No") || !driver.findElement(locator).isSelected())
					break;
				clickElement(driver, locator);
				break;
			}
			if (type.equalsIgnoreCase("button")) {
				clickElement(driver, locator);
				break;
			}
			if (!type.equalsIgnoreCase("a"))
				break;
			break;
		}
		case "textarea": {
			driver.findElement(locator).clear();
			sendKey(driver, locator, value);
		}
		}
	}

	public static void clickCheckBoxByName(WebDriver driver, By locator, String value) {
		WebElement checkbox_Button = findElem(driver, locator);
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

		return findElems(driver, locator).size() > 0;

	}

	public static boolean verifyCheckBoxisSelected(WebDriver driver, By locator) {

		WebElement checkBoxElement = findElem(driver, locator);
		return checkBoxElement.isSelected();

	}
	
	public static boolean verifyElementDisplay(WebDriver driver, By locator) {
		WebElement elem = findElem(driver, locator);
		return elem.isDisplayed();
	}
	
	/***
	 * This method highlights web-elements using javascript executon
	 * 
	 * @param by
	 */

	public static void highLightElement(WebDriver driver, By by) {
		
				WebElement temp = driver.findElement(by);
				highLightElement(driver, temp);
				
//				JavascriptExecutor js = (JavascriptExecutor) driver;
//
//				for (int i = 0; i < 3; i++) {
//					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", temp,
//							"color: red; border: 2px solid yellow;");
//					customWait(1);
//					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", temp, "");
//					customWait(1);
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		
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
