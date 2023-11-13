package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 8/19/2016. For Tradition Application Only where a lot of
 * asynchronous request is not going This class is to perform action using
 * classname locator input to method will be mainly your webdriver
 * instance,classname identifier for HTML element and time out in secs which
 * will check for different pageload strategies (explicit wait and page load
 * where document.readystate is complete)
 */
public class ActionByClassName {

	/*********Update for framework upgrade by Nur and Venus**************/

	public static void action(WebDriver driver, String className, String action, String value, int index,
			int timeoutInSecs) {
		ActionBy_TempFix.action(driver, By.className(className), action, value, index);
	}

	public static void waitForElementToBeVisible(WebDriver driver, String className, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeVisible(driver, By.className(className));
	}

	public static void waitForElementToBePresent(WebDriver driver, String className, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBePresent(driver, By.className(className));
	}

	public static void waitForElementToBeClickable(WebDriver driver, String className, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeClickable(driver, By.className(className));
	}

	public static boolean isDisplayed(WebDriver driver, String className, int timeoutInSecs) {
		return ActionBy_TempFix.isDisplayed(driver, By.className(className));
	}

	public static boolean isEnabled(WebDriver driver, String className, int timeoutInSecs) {
		return ActionBy_TempFix.isEnabled(driver, By.className(className));
	}

	public static boolean isSelected(WebDriver driver, String className, int timeoutInSecs) {
		return ActionBy_TempFix.isSelected(driver, By.className(className));
	}

	public static boolean isVisible(WebDriver driver, String className, int timeoutInSecs) {
		return ActionBy_TempFix.isVisible(driver, By.className(className));
	}

	public static boolean isClickable(WebDriver driver, String className, int timeoutInSecs) {
		return ActionBy_TempFix.isClickable(driver, By.className(className));
	}

	public static void clear(WebDriver driver, String className, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.className(className));
	}

	public static void clear(WebDriver driver, String className, int index, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.className(className), index);
	}

	public static void type(WebDriver driver, String className, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.className(className), input);
	}

	public static void type(WebDriver driver, String className, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.className(className), index, input);
	}

	public static void sendKeys(WebDriver driver, String className, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.className(className), input);
	}

	public static void sendKeys(WebDriver driver, String className, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.className(className), index, input);
	}

	public static void selectByText(WebDriver driver, String className, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.className(className), text);
	}

	public static void selectByText(WebDriver driver, String className, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.className(className), index, text);
	}

	public static void selectByValue(WebDriver driver, String className, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.className(className), value);
	}

	public static void selectByValue(WebDriver driver, String className, int index, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.className(className), index, value);
	}

	public static void selectByIndex(WebDriver driver, String className, int index, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.className(className), index);
	}

	public static void selectByIndex(WebDriver driver, String className, int index, int indexNo, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.className(className), index, indexNo);
	}

	public static void click(WebDriver driver, String className, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.className(className));
	}

	public static void click(WebDriver driver, String className, int index, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.className(className), index);
	}

	public static String getText(WebDriver driver, String className, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.className(className));
	}

	public static String getText(WebDriver driver, String className, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.className(className), index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String className, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.className(className), text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String className, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.className(className), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String className, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.className(className), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String className, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.className(className), text);
	}

	public static WebElement getElement(WebDriver driver, String className, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.className(className));
	}

	public static WebElement getElement(WebDriver driver, String className, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.className(className), index);
	}

	public static List<WebElement> getElements(WebDriver driver, String className, int timeoutInSecs) {
		return ActionBy_TempFix.getElements(driver, By.className(className));
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, String className,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElement(driver, element, By.className(className));
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index,
			String className, int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElements(driver, elements, index, By.className(className));
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, String className,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementsInElement(driver, element, By.className(className));
	}

	public static void multiSelectOptions(WebDriver driver, String className, List<String> text, int timeoutInSecs) {
		ActionBy_TempFix.multiSelectOptions(driver, By.className(className), text);
	}

	public static void clickOption(WebDriver driver, String className, String text, int timeoutInSecs) {
		ActionBy_TempFix.clickOption(driver, By.className(className), text);
	}

	public static void assertVisible(WebDriver driver, String className, int timeoutInSecs) {
		ActionBy_TempFix.assertVisible(driver, By.className(className));
	}

	public static void assertVisible(WebDriver driver, String className, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.className(className), msg);
	}

	public static void assertNotVisible(WebDriver driver, String className, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.className(className));
	}

	public static void assertNotVisible(WebDriver driver, String className, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.className(className), msg);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String className, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.className(className), text);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String className, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.className(className), index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String className, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.className(className), text);	
	}

	public static void assertTextContainsTrue(WebDriver driver, String className, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.className(className), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String className, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.className(className), text);	
	}

	public static void assertEqualsText(WebDriver driver, String className, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.className(className), index, text);	
	}

	public static void assertEqualsText(WebDriver driver, String className, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.className(className), text, msg);
	}

	public static void assertEqualsText(WebDriver driver, String className, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.className(className), index, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String className, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.className(className), text);	
	}

	public static void assertNotEqualsText(WebDriver driver, String className, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.className(className), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String className, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.className(className), text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String className, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.className(className), index, text, msg);
	}
}
