package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 1/25/2017.
 * For Tradition Application Only where a lot of asynchronous request is not going
 * This class is to perform action using tag name
 * input to method will be mainly your webdriver instance,name identifier for HTML element and time out in secs which will check for different pageload strategies (explicit wait and page load where document.readystate is complete)
 */
public class ActionByTagName {


/*********Update for framework upgrade by Nur and Venus**************/
	
    public static void action(WebDriver driver, String tagName, String action, String value, int index,
			int timeoutInSecs) {
		ActionBy_TempFix.action(driver, By.tagName(tagName), action, value, index);
	}

	public static void waitForElementToBeVisible(WebDriver driver, String tagName, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeVisible(driver, By.tagName(tagName));
	}

	public static void waitForElementToBePresent(WebDriver driver, String tagName, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBePresent(driver, By.tagName(tagName));
	}

	public static void waitForElementToBeClickable(WebDriver driver, String tagName, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeClickable(driver, By.tagName(tagName));
	}

	public static boolean isDisplayed(WebDriver driver, String tagName, int timeoutInSecs) {
		return ActionBy_TempFix.isDisplayed(driver, By.tagName(tagName));
	}

	public static boolean isEnabled(WebDriver driver, String tagName, int timeoutInSecs) {
		return ActionBy_TempFix.isEnabled(driver, By.tagName(tagName));
	}

	public static boolean isSelected(WebDriver driver, String tagName, int timeoutInSecs) {
		return ActionBy_TempFix.isSelected(driver, By.tagName(tagName));
	}

	public static boolean isVisible(WebDriver driver, String tagName, int timeoutInSecs) {
		return ActionBy_TempFix.isVisible(driver, By.tagName(tagName));
	}

	public static boolean isClickable(WebDriver driver, String tagName, int timeoutInSecs) {
		return ActionBy_TempFix.isClickable(driver, By.tagName(tagName));
	}

	public static void clear(WebDriver driver, String tagName, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.tagName(tagName));
	}

	public static void clear(WebDriver driver, String tagName, int index, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.tagName(tagName), index);
	}

	public static void type(WebDriver driver, String tagName, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.tagName(tagName), input);
	}

	public static void type(WebDriver driver, String tagName, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.tagName(tagName), index, input);
	}

	public static void sendKeys(WebDriver driver, String tagName, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.tagName(tagName), input);
	}

	public static void sendKeys(WebDriver driver, String tagName, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.tagName(tagName), index, input);
	}

	public static void selectByText(WebDriver driver, String tagName, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.tagName(tagName), text);
	}

	public static void selectByText(WebDriver driver, String tagName, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.tagName(tagName), index, text);
	}

	public static void selectByValue(WebDriver driver, String tagName, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.tagName(tagName), value);
	}

	public static void selectByValue(WebDriver driver, String tagName, int index, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.tagName(tagName), index, value);
	}

	public static void selectByIndex(WebDriver driver, String tagName, int index, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.tagName(tagName), index);
	}

	public static void selectByIndex(WebDriver driver, String tagName, int index, int indexNo, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.tagName(tagName), index, indexNo);
	}

	public static void click(WebDriver driver, String tagName, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.tagName(tagName));
	}

	public static void click(WebDriver driver, String tagName, int index, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.tagName(tagName), index);
	}

	public static String getText(WebDriver driver, String tagName, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.tagName(tagName));
	}

	public static String getText(WebDriver driver, String tagName, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.tagName(tagName), index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String tagName, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.tagName(tagName), text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String tagName, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.tagName(tagName), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String tagName, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.tagName(tagName), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String tagName, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.tagName(tagName), text);
	}

	public static WebElement getElement(WebDriver driver, String tagName, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.tagName(tagName));
	}

	public static WebElement getElement(WebDriver driver, String tagName, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.tagName(tagName), index);
	}

	public static List<WebElement> getElements(WebDriver driver, String tagName, int timeoutInSecs) {
		return ActionBy_TempFix.getElements(driver, By.tagName(tagName));
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, String tagName,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElement(driver, element, By.tagName(tagName));
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index,
			String tagName, int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElements(driver, elements, index, By.tagName(tagName));
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, String tagName,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementsInElement(driver, element, By.tagName(tagName));
	}

	public static void multiSelectOptions(WebDriver driver, String tagName, List<String> text, int timeoutInSecs) {
		ActionBy_TempFix.multiSelectOptions(driver, By.tagName(tagName), text);
	}

	public static void clickOption(WebDriver driver, String tagName, String text, int timeoutInSecs) {
		ActionBy_TempFix.clickOption(driver, By.tagName(tagName), text);
	}

	public static void assertVisible(WebDriver driver, String tagName, int timeoutInSecs) {
		ActionBy_TempFix.assertVisible(driver, By.tagName(tagName));
	}

	public static void assertVisible(WebDriver driver, String tagName, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.tagName(tagName), msg);
	}

	public static void assertNotVisible(WebDriver driver, String tagName, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.tagName(tagName));
	}

	public static void assertNotVisible(WebDriver driver, String tagName, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.tagName(tagName), msg);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String tagName, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.tagName(tagName), text);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String tagName, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.tagName(tagName), index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String tagName, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.tagName(tagName), text);	
	}

	public static void assertTextContainsTrue(WebDriver driver, String tagName, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.tagName(tagName), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String tagName, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.tagName(tagName), text);	
	}

	public static void assertEqualsText(WebDriver driver, String tagName, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.tagName(tagName), index, text);	
	}

	public static void assertEqualsText(WebDriver driver, String tagName, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.tagName(tagName), text, msg);
	}

	public static void assertEqualsText(WebDriver driver, String tagName, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.tagName(tagName), index, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String tagName, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.tagName(tagName), text);	
	}

	public static void assertNotEqualsText(WebDriver driver, String tagName, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.tagName(tagName), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String tagName, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.tagName(tagName), text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String tagName, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.tagName(tagName), index, text, msg);
	}
}

