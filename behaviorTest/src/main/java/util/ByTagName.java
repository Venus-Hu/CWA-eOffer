package util;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by skumar on 1/25/2017.
 */
public class ByTagName {
    public static void waitForElementToBeVisible(WebDriver driver, String tagName, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.tagName(tagName)));
    }

    public static void waitForElementToBePresent(WebDriver driver, String tagName, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.tagName(tagName)));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String tagName, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.tagName(tagName)));
    }

    public static boolean isDisplayed(WebDriver driver,String tagName,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,tagName,timeoutInSecs);
            return driver.findElement(By.tagName(tagName)).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,String tagName,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,tagName,timeoutInSecs);
            return driver.findElement(By.tagName(tagName)).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,String tagName,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,tagName,timeoutInSecs);
            return driver.findElement(By.tagName(tagName)).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,String tagName,int timeoutInSecs)
    {
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.tagName(tagName)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,String tagName,int timeoutInSecs)
    {
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.tagName(tagName)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }
    public static void clear(WebDriver driver, String tagName, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        driver.findElement(By.tagName(tagName)).clear();
    }

    public static void clear(WebDriver driver, String tagName,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        driver.findElements(By.tagName(tagName)).get(index).clear();
    }

    public static void type(WebDriver driver,String tagName,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        driver.findElement(By.tagName(tagName)).sendKeys(input);
    }

    public static void type(WebDriver driver,String tagName,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        driver.findElements(By.tagName(tagName)).get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String tagName,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        driver.findElement(By.tagName(tagName)).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String tagName,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        driver.findElements(By.tagName(tagName)).get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,String tagName,String text,int timeoutInSecs) {
        if (isSelected(driver, tagName, timeoutInSecs)) {
            new Select(driver.findElement(By.tagName(tagName))).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,String tagName,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, tagName, timeoutInSecs)) {
            new Select(driver.findElements(By.tagName(tagName)).get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,String tagName,String value,int timeoutInSecs) {
        if (isSelected(driver, tagName, timeoutInSecs)) {
            new Select(driver.findElement(By.tagName(tagName))).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,String tagName,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, tagName, timeoutInSecs)) {
            new Select(driver.findElements(By.tagName(tagName)).get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,String tagName,int index,int timeoutInSecs) {
        if (isSelected(driver, tagName, timeoutInSecs)) {
            new Select(driver.findElement(By.tagName(tagName))).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,String tagName,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, tagName, timeoutInSecs)) {
            new Select(driver.findElements(By.tagName(tagName)).get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,String tagName,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,tagName,timeoutInSecs);
        driver.findElement(By.tagName(tagName)).click();
    }

    public static void click(WebDriver driver,String tagName,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,tagName,timeoutInSecs);
        driver.findElements(By.tagName(tagName)).get(index).click();
    }

    public static String getText(WebDriver driver,String tagName,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return driver.findElement(By.tagName(tagName)).getText();
    }

    public static String getText(WebDriver driver,String tagName,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return driver.findElements(By.tagName(tagName)).get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String tagName,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return driver.findElement(By.tagName(tagName)).getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String tagName,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return driver.findElements(By.tagName(tagName)).get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String tagName,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return driver.findElements(By.tagName(tagName)).get(index).getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String tagName,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return driver.findElement(By.tagName(tagName)).getText().equals(text);
    }

    public static WebElement getElement(WebDriver driver, String tagName, int timeoutInSecs){
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return driver.findElement(By.tagName(tagName));
    }

    public static WebElement getElement(WebDriver driver,String tagName,int index,int timeoutInSecs){
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return driver.findElements(By.tagName(tagName)).get(index);
    }

    public static List<WebElement> getElements(WebDriver driver, String tagName, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.tagName(tagName));
        return list;
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String tagName,int timeoutInSecs){
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return element.findElement(By.tagName(tagName));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String tagName,int timeoutInSecs){
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return elements.get(index).findElement(By.tagName(tagName));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String tagName,int timeoutInSecs){
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        return element.findElements(By.tagName(tagName));
    }

    public static void multiSelectOptions(WebDriver driver, String tagName, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,tagName,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.tagName(tagName));
        for(int i=0;i<list.size();i++)
        {
            for(int j=0;j<text.size();j++)
            {
                if(text.get(j).equalsIgnoreCase(list.get(i).getText()))
                {
                    list.get(i).click();
                }
            }
        }
    }

    public static void clickOption(WebDriver driver, String tagName, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,tagName,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.tagName(tagName));
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,String tagName,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,tagName,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,String tagName,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,tagName,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String tagName,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,tagName,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String tagName,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,tagName,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String tagName,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,tagName,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String tagName,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,tagName,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String tagName,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,tagName,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String tagName,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,tagName,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String tagName,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,tagName,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String tagName,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,tagName,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String tagName,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,tagName,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String tagName,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,tagName,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String tagName,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,tagName,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String tagName,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,tagName,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String tagName,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,tagName,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String tagName,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,tagName,index,timeoutInSecs));
    }
}
