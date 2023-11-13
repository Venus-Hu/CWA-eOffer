package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

/**
 * Created by skumar on 8/19/2016.
 * For Tradition Application Only where a lot of asynchronous request is not going
 * This class is to perform action using css locator
 * input to method will be mainly your webdriver instance,css identifier for HTML element and time out in secs which will check for different pageload strategies (explicit wait and page load where document.readystate is complete)
 */
public class ActionByCss {

	/*********Update for framework upgrade by Nur and Venus**************/
	
    public static void action(WebDriver driver, String cssSelector, String action, String value, int index,
			int timeoutInSecs) {
		ActionBy_TempFix.action(driver, By.cssSelector(cssSelector), action, value, index);
	}

	public static void waitForElementToBeVisible(WebDriver driver, String cssSelector, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeVisible(driver, By.cssSelector(cssSelector));
	}

	public static void waitForElementToBePresent(WebDriver driver, String cssSelector, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBePresent(driver, By.cssSelector(cssSelector));
	}

	public static void waitForElementToBeClickable(WebDriver driver, String cssSelector, int timeoutInSecs) {
		ActionBy_TempFix.waitForElementToBeClickable(driver, By.cssSelector(cssSelector));
	}

	public static boolean isDisplayed(WebDriver driver, String cssSelector, int timeoutInSecs) {
		return ActionBy_TempFix.isDisplayed(driver, By.cssSelector(cssSelector));
	}

	public static boolean isEnabled(WebDriver driver, String cssSelector, int timeoutInSecs) {
		return ActionBy_TempFix.isEnabled(driver, By.cssSelector(cssSelector));
	}

	public static boolean isSelected(WebDriver driver, String cssSelector, int timeoutInSecs) {
		return ActionBy_TempFix.isSelected(driver, By.cssSelector(cssSelector));
	}

	public static boolean isVisible(WebDriver driver, String cssSelector, int timeoutInSecs) {
		return ActionBy_TempFix.isVisible(driver, By.cssSelector(cssSelector));
	}

	public static boolean isClickable(WebDriver driver, String cssSelector, int timeoutInSecs) {
		return ActionBy_TempFix.isClickable(driver, By.cssSelector(cssSelector));
	}

	public static void clear(WebDriver driver, String cssSelector, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.cssSelector(cssSelector));
	}

	public static void clear(WebDriver driver, String cssSelector, int index, int timeoutInSecs) {
		ActionBy_TempFix.clear(driver, By.cssSelector(cssSelector), index);
	}

	public static void type(WebDriver driver, String cssSelector, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.cssSelector(cssSelector), input);
	}

	public static void type(WebDriver driver, String cssSelector, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.type(driver, By.cssSelector(cssSelector), index, input);
	}

	public static void sendKeys(WebDriver driver, String cssSelector, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.cssSelector(cssSelector), input);
	}

	public static void sendKeys(WebDriver driver, String cssSelector, int index, String input, int timeoutInSecs) {
		ActionBy_TempFix.sendKeys(driver, By.cssSelector(cssSelector), index, input);
	}

	public static void selectByText(WebDriver driver, String cssSelector, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.cssSelector(cssSelector), text);
	}

	public static void selectByText(WebDriver driver, String cssSelector, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.selectByText(driver, By.cssSelector(cssSelector), index, text);
	}

	public static void selectByValue(WebDriver driver, String cssSelector, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.cssSelector(cssSelector), value);
	}

	public static void selectByValue(WebDriver driver, String cssSelector, int index, String value, int timeoutInSecs) {
		ActionBy_TempFix.selectByValue(driver, By.cssSelector(cssSelector), index, value);
	}

	public static void selectByIndex(WebDriver driver, String cssSelector, int index, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.cssSelector(cssSelector), index);
	}

	public static void selectByIndex(WebDriver driver, String cssSelector, int index, int indexNo, int timeoutInSecs) {
		ActionBy_TempFix.selectByIndex(driver, By.cssSelector(cssSelector), index, indexNo);
	}

	public static void click(WebDriver driver, String cssSelector, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.cssSelector(cssSelector));
	}

	public static void click(WebDriver driver, String cssSelector, int index, int timeoutInSecs) {
		ActionBy_TempFix.click(driver, By.cssSelector(cssSelector), index);
	}

	public static String getText(WebDriver driver, String cssSelector, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.cssSelector(cssSelector));
	}

	public static String getText(WebDriver driver, String cssSelector, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getText(driver, By.cssSelector(cssSelector), index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String cssSelector, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.cssSelector(cssSelector), text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String cssSelector, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextContainsToVerify(driver, By.cssSelector(cssSelector), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String cssSelector, int index, String text,
			int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.cssSelector(cssSelector), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String cssSelector, String text, int timeoutInSecs) {
		return ActionBy_TempFix.getTextEqualToVerify(driver, By.cssSelector(cssSelector), text);
	}

	public static WebElement getElement(WebDriver driver, String cssSelector, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.cssSelector(cssSelector));
	}

	public static WebElement getElement(WebDriver driver, String cssSelector, int index, int timeoutInSecs) {
		return ActionBy_TempFix.getElement(driver, By.cssSelector(cssSelector), index);
	}

	public static List<WebElement> getElements(WebDriver driver, String cssSelector, int timeoutInSecs) {
		return ActionBy_TempFix.getElements(driver, By.cssSelector(cssSelector));
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, String cssSelector,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElement(driver, element, By.cssSelector(cssSelector));
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index,
			String cssSelector, int timeoutInSecs) {
		return ActionBy_TempFix.getElementInElements(driver, elements, index, By.cssSelector(cssSelector));
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, String cssSelector,
			int timeoutInSecs) {
		return ActionBy_TempFix.getElementsInElement(driver, element, By.cssSelector(cssSelector));
	}

	public static void multiSelectOptions(WebDriver driver, String cssSelector, List<String> text, int timeoutInSecs) {
		ActionBy_TempFix.multiSelectOptions(driver, By.cssSelector(cssSelector), text);
	}

	public static void clickOption(WebDriver driver, String cssSelector, String text, int timeoutInSecs) {
		ActionBy_TempFix.clickOption(driver, By.cssSelector(cssSelector), text);
	}

	public static void assertVisible(WebDriver driver, String cssSelector, int timeoutInSecs) {
		ActionBy_TempFix.assertVisible(driver, By.cssSelector(cssSelector));
	}

	public static void assertVisible(WebDriver driver, String cssSelector, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.cssSelector(cssSelector), msg);
	}

	public static void assertNotVisible(WebDriver driver, String cssSelector, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.cssSelector(cssSelector));
	}

	public static void assertNotVisible(WebDriver driver, String cssSelector, String msg, int timeoutInSecs) {
		ActionBy_TempFix.assertNotVisible(driver, By.cssSelector(cssSelector), msg);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String cssSelector, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.cssSelector(cssSelector), text);	
	}

	public static void assertTextEqualsTrue(WebDriver driver, String cssSelector, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.cssSelector(cssSelector), index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String cssSelector, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.cssSelector(cssSelector), text);	
	}

	public static void assertTextContainsTrue(WebDriver driver, String cssSelector, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertTextContainsTrue(driver, By.cssSelector(cssSelector), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String cssSelector, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.cssSelector(cssSelector), text);	
	}

	public static void assertEqualsText(WebDriver driver, String cssSelector, int index, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.cssSelector(cssSelector), index, text);	
	}

	public static void assertEqualsText(WebDriver driver, String cssSelector, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.cssSelector(cssSelector), text, msg);
	}

	public static void assertEqualsText(WebDriver driver, String cssSelector, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertEqualsText(driver, By.cssSelector(cssSelector), index, text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String cssSelector, String text, int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.cssSelector(cssSelector), text);	
	}

	public static void assertNotEqualsText(WebDriver driver, String cssSelector, int index, String text,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.cssSelector(cssSelector), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String cssSelector, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.cssSelector(cssSelector), text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String cssSelector, int index, String text, String msg,
			int timeoutInSecs) {
		ActionBy_TempFix.assertNotEqualsText(driver, By.cssSelector(cssSelector), index, text, msg);
	}
}
