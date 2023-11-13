package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 8/19/2016.
 */
public class ActionByXpath {


/*********Update for framework upgrade by Nur and Venus**************/
	
    public static void action(WebDriver driver, String xpath, String action, String value, int index,
			int timeoutInSecs) {
		ActionBy_TempFix.action(driver, By.xpath(xpath), action, value, index);
	}

	public static void waitForElementToBeVisible(WebDriver driver, String xpath, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeVisible(driver, By.xpath(xpath));
	}

	public static void waitForElementToBePresent(WebDriver driver, String xpath, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBePresent(driver, By.xpath(xpath));
	}

	public static void waitForElementToBeClickable(WebDriver driver, String xpath, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeClickable(driver, By.xpath(xpath));
	}

	public static boolean isDisplayed(WebDriver driver, String xpath, int timeoutInSecs) {
		return ActionBy_TempFix.isDisplayed(driver, By.xpath(xpath));
	}

	public static boolean isEnabled(WebDriver driver, String xpath, int timeoutInSecs) {
		return ActionBy_TempFix.isEnabled(driver, By.xpath(xpath));
	}

	public static boolean isSelected(WebDriver driver, String xpath, int timeoutInSecs) {
		return ActionBy_TempFix.isSelected(driver, By.xpath(xpath));
	}

	public static boolean isVisible(WebDriver driver, String xpath, int timeoutInSecs) {
		return ActionBy_TempFix.isVisible(driver, By.xpath(xpath));
	}

	public static boolean isClickable(WebDriver driver, String xpath, int timeoutInSecs) {
		return ActionBy_TempFix.isClickable(driver, By.xpath(xpath));
	}

	public static void clear(WebDriver driver, String xpath, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.xpath(xpath));
	}

	public static void clear(WebDriver driver, String xpath, int index, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.xpath(xpath), index);
	}

	public static void type(WebDriver driver, String xpath, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.xpath(xpath), input);
	}

	public static void type(WebDriver driver, String xpath, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.xpath(xpath), index, input);
	}

	public static void sendKeys(WebDriver driver, String xpath, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.xpath(xpath), input);
	}

	public static void sendKeys(WebDriver driver, String xpath, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.xpath(xpath), index, input);
	}

	public static void selectByText(WebDriver driver, String xpath, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.xpath(xpath), text);
	}

	public static void selectByText(WebDriver driver, String xpath, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.xpath(xpath), index, text);
	}

	public static void selectByValue(WebDriver driver, String xpath, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.xpath(xpath), value);
	}

	public static void selectByValue(WebDriver driver, String xpath, int index, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.xpath(xpath), index, value);
	}

	public static void selectByIndex(WebDriver driver, String xpath, int index, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.xpath(xpath), index);
	}

	public static void selectByIndex(WebDriver driver, String xpath, int index, int indexNo, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.xpath(xpath), index, indexNo);
	}

	public static void click(WebDriver driver, String xpath, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.xpath(xpath));
	}

	public static void click(WebDriver driver, String xpath, int index, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.xpath(xpath), index);
	}

	public static String getText(WebDriver driver, String xpath, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.xpath(xpath));
	}

	public static String getText(WebDriver driver, String xpath, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.xpath(xpath), index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String xpath, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.xpath(xpath), text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String xpath, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.xpath(xpath), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String xpath, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.xpath(xpath), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String xpath, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.xpath(xpath), text);
	}

	public static WebElement getElement(WebDriver driver, String xpath, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.xpath(xpath));
	}

	public static WebElement getElement(WebDriver driver, String xpath, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.xpath(xpath), index);
	}

	public static List<WebElement> getElements(WebDriver driver, String xpath, int timeoutInSecs) {
		return ActionBy_TempFix.getElements(driver, By.xpath(xpath));
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, String xpath,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElement(driver, element, By.xpath(xpath));
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index,
			String xpath, int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElements(driver, elements, index, By.xpath(xpath));
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, String xpath,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementsInElement(driver, element, By.xpath(xpath));
	}

	public static void multiSelectOptions(WebDriver driver, String xpath, List<String> text, int timeoutInSecs) {
		ActionBy_TempFix.multiSelectOptions(driver, By.xpath(xpath), text);
	}

	public static void clickOption(WebDriver driver, String xpath, String text, int timeoutInSecs) {
		ActionBy_TempFix.clickOption(driver, By.xpath(xpath), text);
	}

	public static void assertVisible(WebDriver driver, String xpath, int timeoutInSecs) {
		ActionBy_TempFix.assertVisible(driver, By.xpath(xpath));
	}

	public static void assertVisible(WebDriver driver, String xpath, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.xpath(xpath), msg);
	}

	public static void assertNotVisible(WebDriver driver, String xpath, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.xpath(xpath));
	}

	public static void assertNotVisible(WebDriver driver, String xpath, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.xpath(xpath), msg);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String xpath, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.xpath(xpath), text);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String xpath, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.xpath(xpath), index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String xpath, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.xpath(xpath), text);	
	}

	public static void assertTextContainsTrue(WebDriver driver, String xpath, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.xpath(xpath), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String xpath, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.xpath(xpath), text);	
	}

	public static void assertEqualsText(WebDriver driver, String xpath, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.xpath(xpath), index, text);	
	}

	public static void assertEqualsText(WebDriver driver, String xpath, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.xpath(xpath), text, msg);
	}

	public static void assertEqualsText(WebDriver driver, String xpath, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.xpath(xpath), index, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String xpath, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.xpath(xpath), text);	
	}

	public static void assertNotEqualsText(WebDriver driver, String xpath, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.xpath(xpath), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String xpath, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.xpath(xpath), text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String xpath, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.xpath(xpath), index, text, msg);
	}
}

