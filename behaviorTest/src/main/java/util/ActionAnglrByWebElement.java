package util;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.PageLoad;

import java.util.List;

/**
 * Created by skumar on 8/19/2016.
 * For Angular Application Only
 * This class is to perform action for Angular Application using web element
 * input to method will be mainly your webdriver instance, webelement and time out in secs which will check for different pageload strategies (explicit wait,pageload and angular page ready state)
 */
public class ActionAnglrByWebElement {

    public static void waitForElementToBeVisible(WebDriver driver, WebElement element, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementToBeClickable(WebDriver driver, WebElement element, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean isDisplayed(WebDriver driver,WebElement element,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,element,timeoutInSecs);
            return element.isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,WebElement element,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,element,timeoutInSecs);
            return element.isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,WebElement element,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,element,timeoutInSecs);
            return element.isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,WebElement element,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOf(element));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,WebElement element,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver,timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver,timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static void waitForElementToBeVisible(WebDriver driver, List<WebElement> element, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfAllElements(element));
    }

    public static void waitForElementToBeClickable(WebDriver driver, List<WebElement> element, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element.get(0)));
    }

    public static boolean isDisplayed(WebDriver driver,List<WebElement> element,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,element,timeoutInSecs);
            return element.get(0).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,List<WebElement> element,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,element,timeoutInSecs);
            return element.get(0).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,List<WebElement> element,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,element,timeoutInSecs);
            return element.get(0).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,List<WebElement> element,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfAllElements(element));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,List<WebElement> element,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver,timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver,timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(element.get(0)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static void clear(WebDriver driver, WebElement element, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        element.clear();
    }

    public static void clear(WebDriver driver, List<WebElement> element,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        element.get(index).clear();
    }

    public static void type(WebDriver driver,WebElement element,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        element.sendKeys(input);
    }

    public static void type(WebDriver driver,List<WebElement> element,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        element.get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,WebElement element,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        element.sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,List<WebElement> element,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        element.get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,WebElement element,String text,int timeoutInSecs) {
        if (isSelected(driver, element, timeoutInSecs)) {
            new Select(element).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,List<WebElement> element,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, element, timeoutInSecs)) {
            new Select(element.get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,WebElement element,String value,int timeoutInSecs) {
        if (isSelected(driver, element, timeoutInSecs)) {
            new Select(element).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,List<WebElement> element,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, element, timeoutInSecs)) {
            new Select(element.get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,WebElement element,int index,int timeoutInSecs) {
        if (isSelected(driver, element, timeoutInSecs)) {
            new Select(element).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,List<WebElement> element,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, element, timeoutInSecs)) {
            new Select(element.get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,WebElement element,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,element,timeoutInSecs);
        element.click();
    }

    public static void click(WebDriver driver,List<WebElement> element,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,element,timeoutInSecs);
        element.get(index).click();
    }

    public static String getText(WebDriver driver,WebElement element,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        return element.getText();
    }

    public static String getText(WebDriver driver,List<WebElement> element,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        return element.get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,WebElement element,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        return element.getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,List<WebElement> element,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        return element.get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,WebElement element,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        return element.getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,List<WebElement> element,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        return element.get(index).getText().equals(text);
    }

    public static WebElement getElement(WebDriver driver,WebElement element,int timeoutInSecs){
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        return element;
    }

    public static void multiSelectOptions(WebDriver driver, List<WebElement> element, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,element,timeoutInSecs);
        List<WebElement> list = element;
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

    public static void clickOption(WebDriver driver, List<WebElement> element, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,element,timeoutInSecs);
        List<WebElement> list = element;
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,WebElement element,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,element,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,WebElement element,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,element,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,WebElement element,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,element,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,WebElement element,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,element,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,WebElement element,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,element,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,List<WebElement> element,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,element,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,WebElement element,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,element,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,List<WebElement> element,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,element,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,WebElement element,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,element,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,List<WebElement> element,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,element,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,WebElement element,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,element,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,List<WebElement> element,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,element,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,WebElement element,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,element,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,List<WebElement> element,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,element,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,WebElement element,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,element,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,List<WebElement> element,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,element,index,timeoutInSecs));
    }
}
