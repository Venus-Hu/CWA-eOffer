package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 8/19/2016.
 * For Tradition Application Only where a lot of asynchronous request is not going
 * This class is to perform action using name
 * input to method will be mainly your webdriver instance,name identifier for HTML element and time out in secs which will check for different pageload strategies (explicit wait and page load where document.readystate is complete)
 */

public class ActionByName {


	/*********Update for framework upgrade by Nur and Venus**************/
	
    public static void action(WebDriver driver, String name, String action, String value, int index,
			int timeoutInSecs) {
		ActionBy_TempFix.action(driver, By.name(name), action, value, index);
	}

	public static void waitForElementToBeVisible(WebDriver driver, String name, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeVisible(driver, By.name(name));
	}

	public static void waitForElementToBePresent(WebDriver driver, String name, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBePresent(driver, By.name(name));
	}

	public static void waitForElementToBeClickable(WebDriver driver, String name, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeClickable(driver, By.name(name));
	}

	public static boolean isDisplayed(WebDriver driver, String name, int timeoutInSecs) {
		return ActionBy_TempFix.isDisplayed(driver, By.name(name));
	}

	public static boolean isEnabled(WebDriver driver, String name, int timeoutInSecs) {
		return ActionBy_TempFix.isEnabled(driver, By.name(name));
	}

	public static boolean isSelected(WebDriver driver, String name, int timeoutInSecs) {
		return ActionBy_TempFix.isSelected(driver, By.name(name));
	}

	public static boolean isVisible(WebDriver driver, String name, int timeoutInSecs) {
		return ActionBy_TempFix.isVisible(driver, By.name(name));
	}

	public static boolean isClickable(WebDriver driver, String name, int timeoutInSecs) {
		return ActionBy_TempFix.isClickable(driver, By.name(name));
	}

	public static void clear(WebDriver driver, String name, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.name(name));
	}

	public static void clear(WebDriver driver, String name, int index, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.name(name), index);
	}

	public static void type(WebDriver driver, String name, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.name(name), input);
	}

	public static void type(WebDriver driver, String name, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.name(name), index, input);
	}

	public static void sendKeys(WebDriver driver, String name, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.name(name), input);
	}

	public static void sendKeys(WebDriver driver, String name, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.name(name), index, input);
	}

	public static void selectByText(WebDriver driver, String name, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.name(name), text);
	}

	public static void selectByText(WebDriver driver, String name, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.name(name), index, text);
	}

	public static void selectByValue(WebDriver driver, String name, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.name(name), value);
	}

	public static void selectByValue(WebDriver driver, String name, int index, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.name(name), index, value);
	}

	public static void selectByIndex(WebDriver driver, String name, int index, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.name(name), index);
	}

	public static void selectByIndex(WebDriver driver, String name, int index, int indexNo, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.name(name), index, indexNo);
	}

	public static void click(WebDriver driver, String name, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.name(name));
	}

	public static void click(WebDriver driver, String name, int index, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.name(name), index);
	}

	public static String getText(WebDriver driver, String name, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.name(name));
	}

	public static String getText(WebDriver driver, String name, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.name(name), index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String name, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.name(name), text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String name, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.name(name), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String name, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.name(name), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String name, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.name(name), text);
	}

	public static WebElement getElement(WebDriver driver, String name, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.name(name));
	}

	public static WebElement getElement(WebDriver driver, String name, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.name(name), index);
	}

	public static List<WebElement> getElements(WebDriver driver, String name, int timeoutInSecs) {
		return ActionBy_TempFix.getElements(driver, By.name(name));
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, String name,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElement(driver, element, By.name(name));
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index,
			String name, int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElements(driver, elements, index, By.name(name));
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, String name,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementsInElement(driver, element, By.name(name));
	}

	public static void multiSelectOptions(WebDriver driver, String name, List<String> text, int timeoutInSecs) {
		ActionBy_TempFix.multiSelectOptions(driver, By.name(name), text);
	}

	public static void clickOption(WebDriver driver, String name, String text, int timeoutInSecs) {
		ActionBy_TempFix.clickOption(driver, By.name(name), text);
	}

	public static void assertVisible(WebDriver driver, String name, int timeoutInSecs) {
		ActionBy_TempFix.assertVisible(driver, By.name(name));
	}

	public static void assertVisible(WebDriver driver, String name, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.name(name), msg);
	}

	public static void assertNotVisible(WebDriver driver, String name, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.name(name));
	}

	public static void assertNotVisible(WebDriver driver, String name, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.name(name), msg);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String name, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.name(name), text);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String name, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.name(name), index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String name, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.name(name), text);	
	}

	public static void assertTextContainsTrue(WebDriver driver, String name, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.name(name), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String name, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.name(name), text);	
	}

	public static void assertEqualsText(WebDriver driver, String name, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.name(name), index, text);	
	}

	public static void assertEqualsText(WebDriver driver, String name, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.name(name), text, msg);
	}

	public static void assertEqualsText(WebDriver driver, String name, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.name(name), index, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String name, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.name(name), text);	
	}

	public static void assertNotEqualsText(WebDriver driver, String name, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.name(name), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String name, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.name(name), text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String name, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.name(name), index, text, msg);
	}
}
