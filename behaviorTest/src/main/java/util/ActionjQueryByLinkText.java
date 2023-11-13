package util;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.PageLoad;

import java.util.List;

/**
 * Created by skumar on 8/19/2016.
 */
public class ActionjQueryByLinkText {

    public static void waitForElementToBeVisible(WebDriver driver, String linkText, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
    }

    public static void waitForElementToBePresent(WebDriver driver, String linkText, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String linkText, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
    }

    public static boolean isDisplayed(WebDriver driver,String linkText,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,linkText,timeoutInSecs);
            return driver.findElement(By.linkText(linkText)).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,String linkText,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,linkText,timeoutInSecs);
            return driver.findElement(By.linkText(linkText)).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,String linkText,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,linkText,timeoutInSecs);
            return driver.findElement(By.linkText(linkText)).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,String linkText,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,String linkText,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver,timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver,timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }
    public static void clear(WebDriver driver, String linkText, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        driver.findElement(By.linkText(linkText)).clear();
    }

    public static void clear(WebDriver driver, String linkText,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        driver.findElements(By.linkText(linkText)).get(index).clear();
    }

    public static void type(WebDriver driver,String linkText,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        driver.findElement(By.linkText(linkText)).sendKeys(input);
    }

    public static void type(WebDriver driver,String linkText,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        driver.findElements(By.linkText(linkText)).get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String linkText,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        driver.findElement(By.linkText(linkText)).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String linkText,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        driver.findElements(By.linkText(linkText)).get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,String linkText,String text,int timeoutInSecs) {
        if (isSelected(driver, linkText, timeoutInSecs)) {
            new Select(driver.findElement(By.linkText(linkText))).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,String linkText,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, linkText, timeoutInSecs)) {
            new Select(driver.findElements(By.linkText(linkText)).get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,String linkText,String value,int timeoutInSecs) {
        if (isSelected(driver, linkText, timeoutInSecs)) {
            new Select(driver.findElement(By.linkText(linkText))).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,String linkText,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, linkText, timeoutInSecs)) {
            new Select(driver.findElements(By.linkText(linkText)).get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,String linkText,int index,int timeoutInSecs) {
        if (isSelected(driver, linkText, timeoutInSecs)) {
            new Select(driver.findElement(By.linkText(linkText))).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,String linkText,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, linkText, timeoutInSecs)) {
            new Select(driver.findElements(By.linkText(linkText)).get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,String linkText,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,linkText,timeoutInSecs);
        driver.findElement(By.linkText(linkText)).click();
    }

    public static void click(WebDriver driver,String linkText,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,linkText,timeoutInSecs);
        driver.findElements(By.linkText(linkText)).get(index).click();
    }

    public static String getText(WebDriver driver,String linkText,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return driver.findElement(By.linkText(linkText)).getText();
    }

    public static String getText(WebDriver driver,String linkText,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return driver.findElements(By.linkText(linkText)).get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String linkText,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return driver.findElement(By.linkText(linkText)).getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String linkText,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return driver.findElements(By.linkText(linkText)).get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String linkText,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return driver.findElements(By.linkText(linkText)).get(index).getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String linkText,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return driver.findElement(By.linkText(linkText)).getText().equals(text);
    }

    public static WebElement getElement(WebDriver driver,String linkText,int timeoutInSecs){
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return driver.findElement(By.linkText(linkText));
    }

    public static WebElement getElement(WebDriver driver,String linkText,int index,int timeoutInSecs){
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return driver.findElements(By.linkText(linkText)).get(index);
    }

    public static List<WebElement> getElements(WebDriver driver,String linkText,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.linkText(linkText));
        return list;
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String linkText,int timeoutInSecs){
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return element.findElement(By.linkText(linkText));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String linkText,int timeoutInSecs){
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return elements.get(index).findElement(By.linkText(linkText));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String linkText,int timeoutInSecs){
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        return element.findElements(By.linkText(linkText));
    }

    public static void multiSelectOptions(WebDriver driver, String linkText, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,linkText,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.linkText(linkText));
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

    public static void clickOption(WebDriver driver, String linkText, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,linkText,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.linkText(linkText));
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,String linkText,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,linkText,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,String linkText,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,linkText,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String linkText,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,linkText,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String linkText,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,linkText,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String linkText,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,linkText,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String linkText,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,linkText,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String linkText,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,linkText,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String linkText,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,linkText,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String linkText,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,linkText,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String linkText,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,linkText,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String linkText,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,linkText,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String linkText,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,linkText,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String linkText,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,linkText,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String linkText,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,linkText,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String linkText,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,linkText,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String linkText,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,linkText,index,timeoutInSecs));
    }
}
