package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 8/19/2016.
 */
public class ActionByLinkText {


	/*********Update for framework upgrade by Nur and Venus**************/
	
    public static void action(WebDriver driver, String linkText, String action, String value, int index,
			int timeoutInSecs) {
		ActionBy_TempFix.action(driver, By.linkText(linkText), action, value, index);
	}

	public static void waitForElementToBeVisible(WebDriver driver, String linkText, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeVisible(driver, By.linkText(linkText));
	}

	public static void waitForElementToBePresent(WebDriver driver, String linkText, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBePresent(driver, By.linkText(linkText));
	}

	public static void waitForElementToBeClickable(WebDriver driver, String linkText, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeClickable(driver, By.linkText(linkText));
	}

	public static boolean isDisplayed(WebDriver driver, String linkText, int timeoutInSecs) {
		return ActionBy_TempFix.isDisplayed(driver, By.linkText(linkText));
	}

	public static boolean isEnabled(WebDriver driver, String linkText, int timeoutInSecs) {
		return ActionBy_TempFix.isEnabled(driver, By.linkText(linkText));
	}

	public static boolean isSelected(WebDriver driver, String linkText, int timeoutInSecs) {
		return ActionBy_TempFix.isSelected(driver, By.linkText(linkText));
	}

	public static boolean isVisible(WebDriver driver, String linkText, int timeoutInSecs) {
		return ActionBy_TempFix.isVisible(driver, By.linkText(linkText));
	}

	public static boolean isClickable(WebDriver driver, String linkText, int timeoutInSecs) {
		return ActionBy_TempFix.isClickable(driver, By.linkText(linkText));
	}

	public static void clear(WebDriver driver, String linkText, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.linkText(linkText));
	}

	public static void clear(WebDriver driver, String linkText, int index, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.linkText(linkText), index);
	}

	public static void type(WebDriver driver, String linkText, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.linkText(linkText), input);
	}

	public static void type(WebDriver driver, String linkText, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.linkText(linkText), index, input);
	}

	public static void sendKeys(WebDriver driver, String linkText, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.linkText(linkText), input);
	}

	public static void sendKeys(WebDriver driver, String linkText, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.linkText(linkText), index, input);
	}

	public static void selectByText(WebDriver driver, String linkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.linkText(linkText), text);
	}

	public static void selectByText(WebDriver driver, String linkText, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.linkText(linkText), index, text);
	}

	public static void selectByValue(WebDriver driver, String linkText, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.linkText(linkText), value);
	}

	public static void selectByValue(WebDriver driver, String linkText, int index, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.linkText(linkText), index, value);
	}

	public static void selectByIndex(WebDriver driver, String linkText, int index, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.linkText(linkText), index);
	}

	public static void selectByIndex(WebDriver driver, String linkText, int index, int indexNo, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.linkText(linkText), index, indexNo);
	}

	public static void click(WebDriver driver, String linkText, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.linkText(linkText));
	}

	public static void click(WebDriver driver, String linkText, int index, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.linkText(linkText), index);
	}

	public static String getText(WebDriver driver, String linkText, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.linkText(linkText));
	}

	public static String getText(WebDriver driver, String linkText, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.linkText(linkText), index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String linkText, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.linkText(linkText), text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String linkText, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.linkText(linkText), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String linkText, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.linkText(linkText), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String linkText, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.linkText(linkText), text);
	}

	public static WebElement getElement(WebDriver driver, String linkText, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.linkText(linkText));
	}

	public static WebElement getElement(WebDriver driver, String linkText, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.linkText(linkText), index);
	}

	public static List<WebElement> getElements(WebDriver driver, String linkText, int timeoutInSecs) {
		return ActionBy_TempFix.getElements(driver, By.linkText(linkText));
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, String linkText,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElement(driver, element, By.linkText(linkText));
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index,
			String linkText, int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElements(driver, elements, index, By.linkText(linkText));
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, String linkText,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementsInElement(driver, element, By.linkText(linkText));
	}

	public static void multiSelectOptions(WebDriver driver, String linkText, List<String> text, int timeoutInSecs) {
		ActionBy_TempFix.multiSelectOptions(driver, By.linkText(linkText), text);
	}

	public static void clickOption(WebDriver driver, String linkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.clickOption(driver, By.linkText(linkText), text);
	}

	public static void assertVisible(WebDriver driver, String linkText, int timeoutInSecs) {
		ActionBy_TempFix.assertVisible(driver, By.linkText(linkText));
	}

	public static void assertVisible(WebDriver driver, String linkText, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.linkText(linkText), msg);
	}

	public static void assertNotVisible(WebDriver driver, String linkText, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.linkText(linkText));
	}

	public static void assertNotVisible(WebDriver driver, String linkText, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.linkText(linkText), msg);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String linkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.linkText(linkText), text);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String linkText, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.linkText(linkText), index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String linkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.linkText(linkText), text);	
	}

	public static void assertTextContainsTrue(WebDriver driver, String linkText, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.linkText(linkText), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String linkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.linkText(linkText), text);	
	}

	public static void assertEqualsText(WebDriver driver, String linkText, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.linkText(linkText), index, text);	
	}

	public static void assertEqualsText(WebDriver driver, String linkText, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.linkText(linkText), text, msg);
	}

	public static void assertEqualsText(WebDriver driver, String linkText, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.linkText(linkText), index, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String linkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.linkText(linkText), text);	
	}

	public static void assertNotEqualsText(WebDriver driver, String linkText, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.linkText(linkText), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String linkText, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.linkText(linkText), text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String linkText, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.linkText(linkText), index, text, msg);
	}
}


