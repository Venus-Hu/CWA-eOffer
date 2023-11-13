package util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import customUtility.ActionBy_TempFix;

public class ActionAnglrByXpath {

	/**
	 *
	 * @param driver        - Pass the webdriver instance
	 * @param xpath         - Pass the xpath identifier
	 * @param timeoutInSecs - Pass the timeout to check page load, page load for
	 *                      angular and explicit wait for element to be visible
	 */
	
	/*********Update for framework upgrade by Nur and Venus**************/

	public static void waitForElementToBeVisible(WebDriver driver, String xpath, int timeoutInSecs) {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

		ActionBy_TempFix.waitForElementToBeVisible(driver, By.xpath(xpath));
	}

	public static void waitForElementToBePresent(WebDriver driver, String xpath, int timeoutInSecs) {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

		ActionBy_TempFix.waitForElementToBePresent(driver, By.xpath(xpath));
	}

	public static void waitForElementToBeClickable(WebDriver driver, String xpath, int timeoutInSecs) {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

		ActionBy_TempFix.waitForElementToBeClickable(driver, By.xpath(xpath));
	}

	public static boolean isDisplayed(WebDriver driver, String xpath, int timeoutInSecs) {

//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//            return driver.findElement(By.xpath(xpath)).isDisplayed();
//        }catch(Exception ex){
//            return false;
//        }

		return ActionBy_TempFix.isDisplayed(driver, By.xpath(xpath));
	}

	public static boolean isEnabled(WebDriver driver, String xpath, int timeoutInSecs) {

//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//            return driver.findElement(By.xpath(xpath)).isEnabled();
//        }catch(Exception ex){
//            return false;
//        }

		return ActionBy_TempFix.isEnabled(driver, By.xpath(xpath));
	}

	public static boolean isSelected(WebDriver driver, String xpath, int timeoutInSecs) {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//            return driver.findElement(By.xpath(xpath)).isSelected();
//        }catch(Exception ex){
//            return false;
//        }

		return ActionBy_TempFix.isSelected(driver, By.xpath(xpath));
	}

	public static boolean isVisible(WebDriver driver, String xpath, int timeoutInSecs) {
//        PageLoad.checkPageLoad(driver, timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
//        try {
//            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
//            return true;
//        }catch (Exception ex)
//        {
//            return false;
//        }

		return ActionBy_TempFix.isVisible(driver, By.xpath(xpath));
	}

	public static boolean isClickable(WebDriver driver, String xpath, int timeoutInSecs) {
//        PageLoad.checkPageLoad(driver,timeoutInSecs);
//        PageLoad.checkPageLoadAngular(driver,timeoutInSecs);
//        try {
//            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
//            return true;
//        }catch (Exception ex)
//        {
//            return false;
//        }

		return ActionBy_TempFix.isClickable(driver, By.xpath(xpath));
	}

	public static void clear(WebDriver driver, String xpath, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        driver.findElement(By.xpath(xpath)).clear();

		ActionBy_TempFix.clear(driver, By.xpath(xpath));
	}

	public static void clear(WebDriver driver, String xpath, int index, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        driver.findElements(By.xpath(xpath)).get(index).clear();

		ActionBy_TempFix.clear(driver, By.xpath(xpath), index);
	}

	public static void type(WebDriver driver, String xpath, String input, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        driver.findElement(By.xpath(xpath)).sendKeys(input);

		ActionBy_TempFix.type(driver, By.xpath(xpath), input);
	}

	public static void type(WebDriver driver, String xpath, int index, String input, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        driver.findElements(By.xpath(xpath)).get(index).sendKeys(input);

		ActionBy_TempFix.type(driver, By.xpath(xpath), index, input);
	}

	public static void sendKeys(WebDriver driver, String xpath, String input, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        driver.findElement(By.xpath(xpath)).sendKeys(input);

		ActionBy_TempFix.sendKeys(driver, By.xpath(xpath), input);
	}

	public static void sendKeys(WebDriver driver, String xpath, int index, String input, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        driver.findElements(By.xpath(xpath)).get(index).sendKeys(input);

		ActionBy_TempFix.sendKeys(driver, By.xpath(xpath), index, input);
	}

	public static void selectByText(WebDriver driver, String xpath, String text, int timeoutInSecs) {
//        if(isSelected(driver,xpath,timeoutInSecs)) {
//            new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(text);
//        }

		ActionBy_TempFix.selectByText(driver, By.xpath(xpath), text);
	}

	public static void selectByText(WebDriver driver, String xpath, int index, String text, int timeoutInSecs) {
//        if (isSelected(driver, xpath, timeoutInSecs)) {
//            new Select(driver.findElements(By.xpath(xpath)).get(index)).selectByVisibleText(text);
//        }

		ActionBy_TempFix.selectByText(driver, By.xpath(xpath), index, text);
	}

	public static void selectByValue(WebDriver driver, String xpath, String value, int timeoutInSecs) {
//        if (isSelected(driver, xpath, timeoutInSecs)) {
//            new Select(driver.findElement(By.xpath(xpath))).selectByValue(value);
//        }

		ActionBy_TempFix.selectByValue(driver, By.xpath(xpath), value);
	}

	public static void selectByValue(WebDriver driver, String xpath, int index, String value, int timeoutInSecs) {
//        if (isSelected(driver, xpath, timeoutInSecs)) {
//            new Select(driver.findElements(By.xpath(xpath)).get(index)).selectByValue(value);
//        }

		ActionBy_TempFix.selectByValue(driver, By.xpath(xpath), index, value);
	}

	public static void selectByIndex(WebDriver driver, String xpath, int index, int timeoutInSecs) {
//        if (isSelected(driver, xpath, timeoutInSecs)) {
//            new Select(driver.findElement(By.xpath(xpath))).selectByIndex(index);
//        }

		ActionBy_TempFix.selectByIndex(driver, By.xpath(xpath), index);
	}

	public static void selectByIndex(WebDriver driver, String xpath, int index, int indexNo, int timeoutInSecs) {
//        if (isSelected(driver, xpath, timeoutInSecs)) {
//            new Select(driver.findElements(By.xpath(xpath)).get(index)).selectByIndex(indexNo);
//        }

		ActionBy_TempFix.selectByIndex(driver, By.xpath(xpath), index, indexNo);
	}

	public static void click(WebDriver driver, String xpath, int timeoutInSecs) {
//        waitForElementToBeClickable(driver,xpath,timeoutInSecs);
//        driver.findElement(By.xpath(xpath)).click();

		ActionBy_TempFix.click(driver, By.xpath(xpath));
	}

	public static void click(WebDriver driver, String xpath, int index, int timeoutInSecs) {
//        waitForElementToBeClickable(driver,xpath,timeoutInSecs);
//        driver.findElements(By.xpath(xpath)).get(index).click();

		ActionBy_TempFix.click(driver, By.xpath(xpath), index);
	}

	public static String getText(WebDriver driver, String xpath, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return driver.findElement(By.xpath(xpath)).getText();

		return ActionBy_TempFix.getText(driver, By.xpath(xpath));
	}

	public static String getText(WebDriver driver, String xpath, int index, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return driver.findElements(By.xpath(xpath)).get(index).getText();

		return ActionBy_TempFix.getText(driver, By.xpath(xpath), index);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String xpath, String text, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return driver.findElement(By.xpath(xpath)).getText().contains(text);

		return ActionBy_TempFix.getTextContainsToVerify(driver, By.xpath(xpath), text);
	}

	public static boolean getTextContainsToVerify(WebDriver driver, String xpath, int index, String text,
			int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return driver.findElements(By.xpath(xpath)).get(index).getText().contains(text);

		return ActionBy_TempFix.getTextContainsToVerify(driver, By.xpath(xpath), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String xpath, int index, String text,
			int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return driver.findElements(By.xpath(xpath)).get(index).getText().equals(text);

		return ActionBy_TempFix.getTextContainsToVerify(driver, By.xpath(xpath), index, text);
	}

	public static boolean getTextEqualToVerify(WebDriver driver, String xpath, String text, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return driver.findElement(By.xpath(xpath)).getText().equals(text);

		return ActionBy_TempFix.getTextEqualToVerify(driver, By.xpath(xpath), text);
	}

	public static WebElement getElement(WebDriver driver, String xpath, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return driver.findElement(By.xpath(xpath));

		return ActionBy_TempFix.getElement(driver, By.xpath(xpath));
	}

	public static WebElement getElement(WebDriver driver, String xpath, int index, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return driver.findElements(By.xpath(xpath)).get(index);

		return ActionBy_TempFix.getElement(driver, By.xpath(xpath), index);
	}

	public static WebElement getElementInElement(WebDriver driver, WebElement element, String xpath,
			int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return element.findElement(By.xpath(xpath));

		return ActionBy_TempFix.getElementInElement(driver, element, By.xpath(xpath));
	}

	public static WebElement getElementInElements(WebDriver driver, List<WebElement> elements, int index, String xpath,
			int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return elements.get(index).findElement(By.xpath(xpath));

		return ActionBy_TempFix.getElementInElements(driver, elements, index, By.xpath(xpath));
	}

	public static List<WebElement> getElementsInElement(WebDriver driver, WebElement element, String xpath,
			int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        return element.findElements(By.xpath(xpath));

		return ActionBy_TempFix.getElementsInElement(driver, element, By.xpath(xpath));
	}

	public static List<WebElement> getElements(WebDriver driver, String xpath, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.xpath(xpath));
//        return list;

		return ActionBy_TempFix.getElements(driver, By.xpath(xpath));
	}

	public static void multiSelectOptions(WebDriver driver, String xpath, List<String> text, int timeoutInSecs) {
//        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.xpath(xpath));
//        for(int i=0;i<list.size();i++)
//        {
//            for(int j=0;j<text.size();j++)
//            {
//                if(text.get(j).equalsIgnoreCase(list.get(i).getText()))
//                {
//                    list.get(i).click();
//                }
//            }
//        }
		ActionBy_TempFix.multiSelectOptions(driver, By.xpath(xpath), text);

	}

	public static void clickOption(WebDriver driver, String xpath, String text, int timeoutInSecs) {
//        waitForElementToBeClickable(driver,xpath,timeoutInSecs);
//        List<WebElement> list = driver.findElements(By.xpath(xpath));
//        for(int i=0;i<list.size();i++)
//        {
//            if(text.equalsIgnoreCase(list.get(i).getText()))
//            {
//                list.get(i).click();
//                break;
//            }
//        }
		ActionBy_TempFix.clickOption(driver, By.xpath(xpath), text);
	}

	public static void assertVisible(WebDriver driver, String xpath, int timeoutInSecs) {
//        Assert.assertTrue(isVisible(driver,xpath,timeoutInSecs));
		ActionBy_TempFix.assertVisible(driver, By.xpath(xpath));
	}

	public static void assertVisible(WebDriver driver, String xpath, String msg, int timeoutInSecs) {
//        Assert.assertTrue(msg,isVisible(driver,xpath,timeoutInSecs));
		ActionBy_TempFix.assertVisible(driver, By.xpath(xpath), msg);
	}

	public static void assertNotVisible(WebDriver driver, String xpath, int timeoutInSecs) {
//        Assert.assertFalse(isVisible(driver,xpath,timeoutInSecs));
		ActionBy_TempFix.assertNotVisible(driver, By.xpath(xpath));
	}

	public static void assertNotVisible(WebDriver driver, String xpath, String msg, int timeoutInSecs) {
//        Assert.assertFalse(msg,isVisible(driver,xpath,timeoutInSecs));
		ActionBy_TempFix.assertNotVisible(driver, By.xpath(xpath), msg);
	}

	public static void assertTextEqualsTrue(WebDriver driver, String xpath, String text, int timeoutInSecs) {
//        Assert.assertTrue(getTextEqualToVerify(driver,xpath,text,timeoutInSecs));
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.xpath(xpath), text);
	}

	public static void assertTextEqualsTrue(WebDriver driver, String xpath, int index, String text, int timeoutInSecs) {
//        Assert.assertTrue(getTextEqualToVerify(driver,xpath,index,text,timeoutInSecs));
		ActionBy_TempFix.assertTextEqualsTrue(driver, By.xpath(xpath), index, text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String xpath, String text, int timeoutInSecs) {
//        Assert.assertTrue(getTextContainsToVerify(driver,xpath,text,timeoutInSecs));
		ActionBy_TempFix.assertTextContainsTrue(driver, By.xpath(xpath), text);
	}

	public static void assertTextContainsTrue(WebDriver driver, String xpath, int index, String text,
			int timeoutInSecs) {
//        Assert.assertTrue(getTextContainsToVerify(driver,xpath,index,text,timeoutInSecs));
		ActionBy_TempFix.assertTextContainsTrue(driver, By.xpath(xpath), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String xpath, String text, int timeoutInSecs) {
//        Assert.assertEquals(text,getText(driver,xpath,timeoutInSecs));
		ActionBy_TempFix.assertEqualsText(driver, By.xpath(xpath), text);
	}

	public static void assertEqualsText(WebDriver driver, String xpath, int index, String text, int timeoutInSecs) {
//        Assert.assertEquals(text,getText(driver,xpath,index,timeoutInSecs));
		ActionBy_TempFix.assertEqualsText(driver, By.xpath(xpath), index, text);
	}

	public static void assertEqualsText(WebDriver driver, String xpath, String text, String msg, int timeoutInSecs) {
//        Assert.assertEquals(msg,text,getText(driver,xpath,timeoutInSecs));
		ActionBy_TempFix.assertEqualsText(driver, By.xpath(xpath), text);
	}

	public static void assertEqualsText(WebDriver driver, String xpath, int index, String text, String msg,
			int timeoutInSecs) {
//        Assert.assertEquals(msg,text,getText(driver,xpath,index,timeoutInSecs));
		ActionBy_TempFix.assertEqualsText(driver, By.xpath(xpath), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String xpath, String text, int timeoutInSecs) {
//        Assert.assertNotEquals(text,getText(driver,xpath,timeoutInSecs));
		ActionBy_TempFix.assertNotEqualsText(driver, By.xpath(xpath), text);
	}

	public static void assertNotEqualsText(WebDriver driver, String xpath, int index, String text, int timeoutInSecs) {
//        Assert.assertNotEquals(text,getText(driver,xpath,index,timeoutInSecs));
		ActionBy_TempFix.assertNotEqualsText(driver, By.xpath(xpath), index, text);
	}

	public static void assertNotEqualsText(WebDriver driver, String xpath, String text, String msg, int timeoutInSecs) {
//        Assert.assertNotEquals(msg,text,getText(driver,xpath,timeoutInSecs));
		ActionBy_TempFix.assertNotEqualsText(driver, By.xpath(xpath), text, msg);
	}

	public static void assertNotEqualsText(WebDriver driver, String xpath, int index, String text, String msg,
			int timeoutInSecs) {
//        Assert.assertNotEquals(msg,text,getText(driver,xpath,index,timeoutInSecs));
		ActionBy_TempFix.assertNotEqualsText(driver, By.xpath(xpath), index, text, msg);
	}

}
