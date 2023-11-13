package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 8/19/2016.
 * For Tradition Application Only where a lot of asynchronous request is not going
 * This class is to perform action using id locator
 * input to method will be mainly your webdriver instance,id identifier for HTML element and time out in secs which will check for different pageload strategies (explicit wait and page load where document.readystate is complete)
 */
public class ActionById {


	/*********Update for framework upgrade by Nur and Venus**************/
	
    public static void action(WebDriver driver, String id, String action, String value, int index,
			int timeoutInSecs) {
		ActionBy_TempFix.action(driver, By.id(id), action, value, index);
	}

	public static void waitForElementToBeVisible(WebDriver driver, String id, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeVisible(driver, By.id(id));
	}

	public static void waitForElementToBePresent(WebDriver driver, String id, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBePresent(driver, By.id(id));
	}

	public static void waitForElementToBeClickable(WebDriver driver, String id, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeClickable(driver, By.id(id));
	}

	public static boolean isDisplayed(WebDriver driver, String id, int timeoutInSecs) {
		return ActionBy_TempFix.isDisplayed(driver, By.id(id));
	}

	public static boolean isEnabled(WebDriver driver, String id, int timeoutInSecs) {
		return ActionBy_TempFix.isEnabled(driver, By.id(id));
	}

	public static boolean isSelected(WebDriver driver, String id, int timeoutInSecs) {
		return ActionBy_TempFix.isSelected(driver, By.id(id));
	}

	public static boolean isVisible(WebDriver driver, String id, int timeoutInSecs) {
		return ActionBy_TempFix.isVisible(driver, By.id(id));
	}

	public static boolean isClickable(WebDriver driver, String id, int timeoutInSecs) {
		return ActionBy_TempFix.isClickable(driver, By.id(id));
	}

	public static void clear(WebDriver driver, String id, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.id(id));
	}

	public static void clear(WebDriver driver, String id, int index, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.id(id), index);
	}

	public static void type(WebDriver driver, String id, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.id(id), input);
	}

	public static void type(WebDriver driver, String id, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.id(id), index, input);
	}

	public static void sendKeys(WebDriver driver, String id, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.id(id), input);
	}

	public static void sendKeys(WebDriver driver, String id, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.id(id), index, input);
	}

	public static void selectByText(WebDriver driver, String id, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.id(id), text);
	}

	public static void selectByText(WebDriver driver, String id, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.id(id), index, text);
	}

	public static void selectByValue(WebDriver driver, String id, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.id(id), value);
	}

	public static void selectByValue(WebDriver driver, String id, int index, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.id(id), index, value);
	}

	public static void selectByIndex(WebDriver driver, String id, int index, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.id(id), index);
	}

	public static void selectByIndex(WebDriver driver, String id, int index, int indexNo, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.id(id), index, indexNo);
	}

	public static void click(WebDriver driver, String id, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.id(id));
	}

	public static void click(WebDriver driver, String id, int index, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.id(id), index);
	}

	public static String getText(WebDriver driver, String id, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.id(id));
	}

	public static String getText(WebDriver driver, String id, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.id(id), index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String id, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.id(id), text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String id, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.id(id), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String id, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.id(id), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String id, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.id(id), text);
	}

	public static WebElement getElement(WebDriver driver, String id, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.id(id));
	}

	public static WebElement getElement(WebDriver driver, String id, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.id(id), index);
	}

	public static List<WebElement> getElements(WebDriver driver, String id, int timeoutInSecs) {
		return ActionBy_TempFix.getElements(driver, By.id(id));
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, String id,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElement(driver, element, By.id(id));
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index,
			String id, int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElements(driver, elements, index, By.id(id));
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, String id,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementsInElement(driver, element, By.id(id));
	}

	public static void multiSelectOptions(WebDriver driver, String id, List<String> text, int timeoutInSecs) {
		ActionBy_TempFix.multiSelectOptions(driver, By.id(id), text);
	}

	public static void clickOption(WebDriver driver, String id, String text, int timeoutInSecs) {
		ActionBy_TempFix.clickOption(driver, By.id(id), text);
	}

	public static void assertVisible(WebDriver driver, String id, int timeoutInSecs) {
		ActionBy_TempFix.assertVisible(driver, By.id(id));
	}

	public static void assertVisible(WebDriver driver, String id, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.id(id), msg);
	}

	public static void assertNotVisible(WebDriver driver, String id, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.id(id));
	}

	public static void assertNotVisible(WebDriver driver, String id, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.id(id), msg);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String id, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.id(id), text);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String id, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.id(id), index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String id, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.id(id), text);	
	}

	public static void assertTextContainsTrue(WebDriver driver, String id, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.id(id), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String id, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.id(id), text);	
	}

	public static void assertEqualsText(WebDriver driver, String id, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.id(id), index, text);	
	}

	public static void assertEqualsText(WebDriver driver, String id, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.id(id), text, msg);
	}

	public static void assertEqualsText(WebDriver driver, String id, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.id(id), index, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String id, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.id(id), text);	
	}

	public static void assertNotEqualsText(WebDriver driver, String id, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.id(id), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String id, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.id(id), text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String id, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.id(id), index, text, msg);
	}
}

