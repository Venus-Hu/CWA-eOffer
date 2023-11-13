package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 8/19/2016.
 * For Tradition Application Only where a lot of asynchronous request is not going
 * This class is to perform action using locator (E.g By)
 * input to method will be mainly your webdriver instance,locator for HTML element and time out in secs which will check for different pageload strategies (explicit wait and page load where document.readystate is complete)
 */
public class ActionByLocator {


	/*********Update for framework upgrade by Nur and Venus**************/
	
    public static void action(WebDriver driver, By locator, String action, String value, int index,
			int timeoutInSecs) {
		ActionBy_TempFix.action(driver, locator, action, value, index);
	}

	public static void waitForElementToBeVisible(WebDriver driver, By locator, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeVisible(driver, locator);
	}

	public static void waitForElementToBePresent(WebDriver driver, By locator, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBePresent(driver, locator);
	}

	public static void waitForElementToBeClickable(WebDriver driver, By locator, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeClickable(driver, locator);
	}

	public static boolean isDisplayed(WebDriver driver, By locator, int timeoutInSecs) {
		return ActionBy_TempFix.isDisplayed(driver, locator);
	}

	public static boolean isEnabled(WebDriver driver, By locator, int timeoutInSecs) {
		return ActionBy_TempFix.isEnabled(driver, locator);
	}

	public static boolean isSelected(WebDriver driver, By locator, int timeoutInSecs) {
		return ActionBy_TempFix.isSelected(driver, locator);
	}

	public static boolean isVisible(WebDriver driver, By locator, int timeoutInSecs) {
		return ActionBy_TempFix.isVisible(driver, locator);
	}

	public static boolean isClickable(WebDriver driver, By locator, int timeoutInSecs) {
		return ActionBy_TempFix.isClickable(driver, locator);
	}

	public static void clear(WebDriver driver, By locator, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, locator);
	}

	public static void clear(WebDriver driver, By locator, int index, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, locator, index);
	}

	public static void type(WebDriver driver, By locator, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, locator, input);
	}

	public static void type(WebDriver driver, By locator, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, locator, index, input);
	}

	public static void sendKeys(WebDriver driver, By locator, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, locator, input);
	}

	public static void sendKeys(WebDriver driver, By locator, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, locator, index, input);
	}

	public static void selectByText(WebDriver driver, By locator, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, locator, text);
	}

	public static void selectByText(WebDriver driver, By locator, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, locator, index, text);
	}

	public static void selectByValue(WebDriver driver, By locator, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, locator, value);
	}

	public static void selectByValue(WebDriver driver, By locator, int index, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, locator, index, value);
	}

	public static void selectByIndex(WebDriver driver, By locator, int index, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, locator, index);
	}

	public static void selectByIndex(WebDriver driver, By locator, int index, int indexNo, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, locator, index, indexNo);
	}

	public static void click(WebDriver driver, By locator, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, locator);
	}

	public static void click(WebDriver driver, By locator, int index, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, locator, index);
	}

	public static String getText(WebDriver driver, By locator, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, locator);
	}

	public static String getText(WebDriver driver, By locator, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, locator, index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, By locator, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, locator, text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, By locator, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, locator, index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, By locator, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, locator, index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, By locator, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, locator, text);
	}

	public static WebElement getElement(WebDriver driver, By locator, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, locator);
	}

	public static WebElement getElement(WebDriver driver, By locator, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, locator, index);
	}

	public static List<WebElement> getElements(WebDriver driver, By locator, int timeoutInSecs) {
		return ActionBy_TempFix.getElements(driver, locator);
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, By locator,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElement(driver, element, locator);
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index,
			By locator, int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElements(driver, elements, index, locator);
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, By locator,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementsInElement(driver, element, locator);
	}

	public static void multiSelectOptions(WebDriver driver, By locator, List<String> text, int timeoutInSecs) {
		ActionBy_TempFix.multiSelectOptions(driver, locator, text);
	}

	public static void clickOption(WebDriver driver, By locator, String text, int timeoutInSecs) {
		ActionBy_TempFix.clickOption(driver, locator, text);
	}

	public static void assertVisible(WebDriver driver, By locator, int timeoutInSecs) {
		ActionBy_TempFix.assertVisible(driver, locator);
	}

	public static void assertVisible(WebDriver driver, By locator, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, locator, msg);
	}

	public static void assertNotVisible(WebDriver driver, By locator, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, locator);
	}

	public static void assertNotVisible(WebDriver driver, By locator, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, locator, msg);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, By locator, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, locator, text);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, By locator, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, locator, index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, By locator, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, locator, text);	
	}

	public static void assertTextContainsTrue(WebDriver driver, By locator, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, locator, index, text);
	}

	public static void assertEqualsText(WebDriver driver, By locator, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, locator, text);	
	}

	public static void assertEqualsText(WebDriver driver, By locator, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, locator, index, text);	
	}

	public static void assertEqualsText(WebDriver driver, By locator, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, locator, text, msg);
	}

	public static void assertEqualsText(WebDriver driver, By locator, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, locator, index, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, By locator, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, locator, text);	
	}

	public static void assertNotEqualsText(WebDriver driver, By locator, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, locator, index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, By locator, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, locator, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, By locator, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, locator, index, text, msg);
	}
}



