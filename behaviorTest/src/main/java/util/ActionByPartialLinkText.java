package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 8/19/2016.
 * For Tradition Application Only where a lot of asynchronous request is not going
 * This class is to perform action using partial link text
 * input to method will be mainly your webdriver instance,name identifier for HTML element and time out in secs which will check for different pageload strategies (explicit wait and page load where document.readystate is complete)
 */
public class ActionByPartialLinkText {


/*********Update for framework upgrade by Nur and Venus**************/
	
    public static void action(WebDriver driver, String partialLinkText, String action, String value, int index,
			int timeoutInSecs) {
		ActionBy_TempFix.action(driver, By.partialLinkText(partialLinkText), action, value, index);
	}

	public static void waitForElementToBeVisible(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeVisible(driver, By.partialLinkText(partialLinkText));
	}

	public static void waitForElementToBePresent(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBePresent(driver, By.partialLinkText(partialLinkText));
	}

	public static void waitForElementToBeClickable(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeClickable(driver, By.partialLinkText(partialLinkText));
	}

	public static boolean isDisplayed(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		return ActionBy_TempFix.isDisplayed(driver, By.partialLinkText(partialLinkText));
	}

	public static boolean isEnabled(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		return ActionBy_TempFix.isEnabled(driver, By.partialLinkText(partialLinkText));
	}

	public static boolean isSelected(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		return ActionBy_TempFix.isSelected(driver, By.partialLinkText(partialLinkText));
	}

	public static boolean isVisible(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		return ActionBy_TempFix.isVisible(driver, By.partialLinkText(partialLinkText));
	}

	public static boolean isClickable(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		return ActionBy_TempFix.isClickable(driver, By.partialLinkText(partialLinkText));
	}

	public static void clear(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.partialLinkText(partialLinkText));
	}

	public static void clear(WebDriver driver, String partialLinkText, int index, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.partialLinkText(partialLinkText), index);
	}

	public static void type(WebDriver driver, String partialLinkText, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.partialLinkText(partialLinkText), input);
	}

	public static void type(WebDriver driver, String partialLinkText, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.partialLinkText(partialLinkText), index, input);
	}

	public static void sendKeys(WebDriver driver, String partialLinkText, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.partialLinkText(partialLinkText), input);
	}

	public static void sendKeys(WebDriver driver, String partialLinkText, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.partialLinkText(partialLinkText), index, input);
	}

	public static void selectByText(WebDriver driver, String partialLinkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.partialLinkText(partialLinkText), text);
	}

	public static void selectByText(WebDriver driver, String partialLinkText, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.partialLinkText(partialLinkText), index, text);
	}

	public static void selectByValue(WebDriver driver, String partialLinkText, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.partialLinkText(partialLinkText), value);
	}

	public static void selectByValue(WebDriver driver, String partialLinkText, int index, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.partialLinkText(partialLinkText), index, value);
	}

	public static void selectByIndex(WebDriver driver, String partialLinkText, int index, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.partialLinkText(partialLinkText), index);
	}

	public static void selectByIndex(WebDriver driver, String partialLinkText, int index, int indexNo, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.partialLinkText(partialLinkText), index, indexNo);
	}

	public static void click(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.partialLinkText(partialLinkText));
	}

	public static void click(WebDriver driver, String partialLinkText, int index, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.partialLinkText(partialLinkText), index);
	}

	public static String getText(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.partialLinkText(partialLinkText));
	}

	public static String getText(WebDriver driver, String partialLinkText, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.partialLinkText(partialLinkText), index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String partialLinkText, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.partialLinkText(partialLinkText), text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String partialLinkText, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.partialLinkText(partialLinkText), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String partialLinkText, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.partialLinkText(partialLinkText), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String partialLinkText, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.partialLinkText(partialLinkText), text);
	}

	public static WebElement getElement(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.partialLinkText(partialLinkText));
	}

	public static WebElement getElement(WebDriver driver, String partialLinkText, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.partialLinkText(partialLinkText), index);
	}

	public static List<WebElement> getElements(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		return ActionBy_TempFix.getElements(driver, By.partialLinkText(partialLinkText));
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, String partialLinkText,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElement(driver, element, By.partialLinkText(partialLinkText));
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index,
			String partialLinkText, int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElements(driver, elements, index, By.partialLinkText(partialLinkText));
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, String partialLinkText,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementsInElement(driver, element, By.partialLinkText(partialLinkText));
	}

	public static void multiSelectOptions(WebDriver driver, String partialLinkText, List<String> text, int timeoutInSecs) {
		ActionBy_TempFix.multiSelectOptions(driver, By.partialLinkText(partialLinkText), text);
	}

	public static void clickOption(WebDriver driver, String partialLinkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.clickOption(driver, By.partialLinkText(partialLinkText), text);
	}

	public static void assertVisible(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		ActionBy_TempFix.assertVisible(driver, By.partialLinkText(partialLinkText));
	}

	public static void assertVisible(WebDriver driver, String partialLinkText, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.partialLinkText(partialLinkText), msg);
	}

	public static void assertNotVisible(WebDriver driver, String partialLinkText, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.partialLinkText(partialLinkText));
	}

	public static void assertNotVisible(WebDriver driver, String partialLinkText, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.partialLinkText(partialLinkText), msg);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String partialLinkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.partialLinkText(partialLinkText), text);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String partialLinkText, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.partialLinkText(partialLinkText), index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String partialLinkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.partialLinkText(partialLinkText), text);	
	}

	public static void assertTextContainsTrue(WebDriver driver, String partialLinkText, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.partialLinkText(partialLinkText), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String partialLinkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.partialLinkText(partialLinkText), text);	
	}

	public static void assertEqualsText(WebDriver driver, String partialLinkText, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.partialLinkText(partialLinkText), index, text);	
	}

	public static void assertEqualsText(WebDriver driver, String partialLinkText, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.partialLinkText(partialLinkText), text, msg);
	}

	public static void assertEqualsText(WebDriver driver, String partialLinkText, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.partialLinkText(partialLinkText), index, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String partialLinkText, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.partialLinkText(partialLinkText), text);	
	}

	public static void assertNotEqualsText(WebDriver driver, String partialLinkText, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.partialLinkText(partialLinkText), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String partialLinkText, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.partialLinkText(partialLinkText), text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String partialLinkText, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.partialLinkText(partialLinkText), index, text, msg);
	}
}
