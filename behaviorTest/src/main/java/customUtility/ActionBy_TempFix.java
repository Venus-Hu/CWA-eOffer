package customUtility;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.PageLoad;

public class ActionBy_TempFix {

	final static int timeoutInSecs = 15;
	private Octo_SeleniumLibrary lib_willUseLater;

	public static void action(WebDriver driver, By locator, String action, String value, int index) {
		switch (action) {
		case "clear":
			if (index <=0) {
				clear(driver, locator);
			} else {
				clear(driver, locator, index);
			}
			break;
		case "type":
			if (index <=0) {
				type(driver, locator, value);
			} else {
				type(driver, locator, index, value);
			}
			break;
		case "sendKeys":
			if (index <=0) {
				sendKeys(driver, locator, value);
			} else {
				sendKeys(driver, locator, index, value);
			}
			break;
		case "selectByText":
			if (index <=0) {
				selectByText(driver, locator, value);
			} else {
				selectByText(driver, locator, index, value);
			}
			break;
		case "selectByValue":
			if (index <=0) {
				selectByValue(driver, locator, value);
			} else {
				selectByValue(driver, locator, index, value);
			}
			break;
		case "selectByIndex":
			if (index <=0) {
				selectByIndex(driver, locator, Integer.parseInt(value));
			} else {
				selectByIndex(driver, locator, index, Integer.parseInt(value));
			}
			break;
		case "click":
			if (index <=0) {
				click(driver, locator);
			} else {
				click(driver, locator, index);
			}
			break;
		case "assertVisible":
			if (index <=0) {
				assertVisible(driver, locator);
			}
			break;
		case "assertNotVisible":
			if (index <=0) {
				assertNotVisible(driver, locator);
			}
			break;
		case "assertTextEqualsTrue":
			if (index <=0) {
				assertTextEqualsTrue(driver, locator, value);
			} else {
				assertTextEqualsTrue(driver, locator, index, value);
			}
			break;
		case "assertTextContainsTrue":
			if (index <=0) {
				assertTextContainsTrue(driver, locator, value);
			} else {
				assertTextContainsTrue(driver, locator, index, value);
			}
			break;
		case "assertEqualsText":
			if (index  <=0) {
				assertEqualsText(driver, locator, value);
			} else {
				assertEqualsText(driver, locator, index, value);
			}
			break;
		case "assertNotEqualsText":
			if (index <=0) {
				assertNotEqualsText(driver, locator, value);
			} else {
				assertNotEqualsText(driver, locator, index, value);
			}
			break;
		default:
			break;
		}
	}

	public static void waitForElementToBeVisible(WebDriver driver, By locator) {

		new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForElementToBePresent(WebDriver driver, By locator) {

		new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static void waitForElementToBeClickable(WebDriver driver, By locator) {

		new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static boolean isDisplayed(WebDriver driver, By locator) {

		try {
			waitForElementToBeVisible(driver, locator);
			return Octo_SeleniumLibrary.verifyElementDisplay(driver, locator);
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isEnabled(WebDriver driver, By locator) {

		try {
			waitForElementToBeVisible(driver, locator);
			return driver.findElement(locator).isEnabled();
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isSelected(WebDriver driver, By locator) {

		try {
			waitForElementToBeVisible(driver, locator);
			return Octo_SeleniumLibrary.verifyCheckBoxisSelected(driver, locator);
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isVisible(WebDriver driver, By locator) {

		try {
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static boolean isClickable(WebDriver driver, By locator) {

		try {
			new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public static void clear(WebDriver driver, By locator) {
		waitForElementToBeVisible(driver, locator);
		Octo_SeleniumLibrary.clear(driver, locator);
	}

	public static void clear(WebDriver driver, By locator, int index) {
		waitForElementToBeVisible(driver, locator);
		WebElement elem = Octo_SeleniumLibrary.findElems(driver, locator).get(index);
		Octo_SeleniumLibrary.clear(driver, elem);
	}

	public static void type(WebDriver driver, By locator, String input) {
		waitForElementToBeVisible(driver, locator);
		Octo_SeleniumLibrary.sendKey(driver, locator, input);
	}

	public static void type(WebDriver driver, By locator, int index, String input) {
		waitForElementToBeVisible(driver, locator);
		WebElement elem =Octo_SeleniumLibrary.findElems(driver, locator).get(index);
		Octo_SeleniumLibrary.sendKey(driver, elem, input);
	}

	public static void sendKeys(WebDriver driver, By locator, String input) {

		Octo_SeleniumLibrary.sendKey(driver, locator, input);
	}

	public static void sendKeys(WebDriver driver, By locator, int index, String input) {
		WebElement elem = Octo_SeleniumLibrary.findElems(driver, locator).get(index);
		Octo_SeleniumLibrary.sendKey(driver, elem, input);
	}

	public static void selectByText(WebDriver driver, By locator, String text) {
			Octo_SeleniumLibrary.selectByVisibleText(driver, locator, text);
	}

	public static void selectByText(WebDriver driver, By locator, int index, String text) {
			new Select(driver.findElements(locator).get(index)).selectByVisibleText(text);
	}

	public static void selectByValue(WebDriver driver, By locator, String value) {
			Octo_SeleniumLibrary.selectByValue(driver, locator, value);
	}

	public static void selectByValue(WebDriver driver, By locator, int index, String value) {
			new Select(driver.findElements(locator).get(index)).selectByValue(value);
	}

	public static void selectByIndex(WebDriver driver, By locator, int index) {
			Octo_SeleniumLibrary.selectByIndex(driver, locator, index);
	}

	public static void selectByIndex(WebDriver driver, By locator, int index, int indexNo) {
			Octo_SeleniumLibrary.selectByIndex(driver, locator, indexNo);
	}

	public static void click(WebDriver driver, By locator) {
		Octo_SeleniumLibrary.clickElement(driver, locator);
	}

	public static void click(WebDriver driver, By locator, int index) {
		Octo_SeleniumLibrary.clickElementByIndex(driver, locator, index);
	}

	public static String getText(WebDriver driver, By locator) {
		return Octo_SeleniumLibrary.getText(driver, locator);
	}

	public static String getText(WebDriver driver, By locator, int index) {
		return Octo_SeleniumLibrary.findElems(driver, locator).get(index).getText();
	}

	public static boolean getTextContainsToVerify(WebDriver driver, By locator, String text) {
		waitForElementToBeVisible(driver, locator);
		return driver.findElement(locator).getText().contains(text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, By locator, int index, String text) {
		waitForElementToBeVisible(driver, locator);
		return driver.findElements(locator).get(index).getText().contains(text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, By locator, int index, String text) {
		waitForElementToBeVisible(driver, locator);
		return driver.findElements(locator).get(index).getText().equals(text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, By locator, String text) {
		waitForElementToBeVisible(driver, locator);
		return driver.findElement(locator).getText().equals(text);
	}

	public static WebElement getElement(WebDriver driver, By locator) {
		waitForElementToBeVisible(driver, locator);
		return driver.findElement(locator);
	}

	public static WebElement getElement(WebDriver driver, By locator, int index) {
		waitForElementToBeVisible(driver, locator);
		return driver.findElements(locator).get(index);
	}

	public static List<WebElement> getElements(WebDriver driver, By locator) {
		waitForElementToBeVisible(driver, locator);
		List<WebElement> list = driver.findElements(locator);
		return list;
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, By locator) {
		waitForElementToBeVisible(driver, locator);
		return element.findElement(locator);
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index, By locator) {
		waitForElementToBeVisible(driver, locator);
		return elements.get(index).findElement(locator);
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, By locator) {
		waitForElementToBeVisible(driver, locator);
		return element.findElements(locator);
	}

	public static void multiSelectOptions(WebDriver driver, By locator, List<String> text) {
		waitForElementToBeVisible(driver, locator);
		List<WebElement> list = driver.findElements(locator);
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < text.size(); j++) {
				if (text.get(j).equalsIgnoreCase(list.get(i).getText())) {
					list.get(i).click();
				}
			}
		}
	}

	public static void clickOption(WebDriver driver, By locator, String text) {
		waitForElementToBeClickable(driver, locator);
		List<WebElement> list = driver.findElements(locator);
		for (int i = 0; i < list.size(); i++) {
			if (text.equalsIgnoreCase(list.get(i).getText())) {
				list.get(i).click();
				break;
			}
		}
	}

	public static void assertVisible(WebDriver driver, By locator) {
		Assert.assertTrue(isVisible(driver, locator));
	}

	public static void assertVisible(WebDriver driver, By locator, String msg) {
		Assert.assertTrue(msg, isVisible(driver, locator));
	}

	public static void assertNotVisible(WebDriver driver, By locator) {
		Assert.assertFalse(isVisible(driver, locator));
	}

	public static void assertNotVisible(WebDriver driver, By locator, String msg) {
		Assert.assertFalse(msg, isVisible(driver, locator));
	}

	public static void assertTextEqualsTrue(WebDriver driver, By locator, String text) {
		Assert.assertTrue(getTextEqualToVerify(driver, locator, text));
	}

	public static void assertTextEqualsTrue(WebDriver driver, By locator, int index, String text) {
		Assert.assertTrue(getTextEqualToVerify(driver, locator, index, text));
	}

	public static void assertTextContainsTrue(WebDriver driver, By locator, String text) {
		Assert.assertTrue(getTextContainsToVerify(driver, locator, text));
	}

	public static void assertTextContainsTrue(WebDriver driver, By locator, int index, String text) {
		Assert.assertTrue(getTextContainsToVerify(driver, locator, index, text));
	}

	public static void assertEqualsText(WebDriver driver, By locator, String text) {
		Assert.assertEquals(text, getText(driver, locator));
	}

	public static void assertEqualsText(WebDriver driver, By locator, int index, String text) {
		Assert.assertEquals(text, getText(driver, locator, index));
	}

	public static void assertEqualsText(WebDriver driver, By locator, String text, String msg) {
		Assert.assertEquals(msg, text, getText(driver, locator));
	}

	public static void assertEqualsText(WebDriver driver, By locator, int index, String text, String msg) {
		Assert.assertEquals(msg, text, getText(driver, locator, index));
	}

	public static void assertNotEqualsText(WebDriver driver, By locator, String text) {
		Assert.assertNotEquals(text, getText(driver, locator));
	}

	public static void assertNotEqualsText(WebDriver driver, By locator, int index, String text) {
		Assert.assertNotEquals(text, getText(driver, locator, index));
	}

	public static void assertNotEqualsText(WebDriver driver, By locator, String text, String msg) {
		Assert.assertNotEquals(msg, text, getText(driver, locator));
	}

	public static void assertNotEqualsText(WebDriver driver, By locator, int index, String text, String msg) {
		Assert.assertNotEquals(msg, text, getText(driver, locator, index));
	}

}
