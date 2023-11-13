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
 * Created by skumar on 8/19/2016.
 */
public class ActionjQueryByXpath {

    public static void waitForElementToBeVisible(WebDriver driver, String xpath, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public static void waitForElementToBePresent(WebDriver driver, String xpath, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String xpath, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static boolean isDisplayed(WebDriver driver,String xpath,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,xpath,timeoutInSecs);
            return driver.findElement(By.xpath(xpath)).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,String xpath,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,xpath,timeoutInSecs);
            return driver.findElement(By.xpath(xpath)).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,String xpath,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,xpath,timeoutInSecs);
            return driver.findElement(By.xpath(xpath)).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,String xpath,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,String xpath,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver,timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver,timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }
    public static void clear(WebDriver driver, String xpath, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        driver.findElement(By.xpath(xpath)).clear();
    }

    public static void clear(WebDriver driver, String xpath,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        driver.findElements(By.xpath(xpath)).get(index).clear();
    }

    public static void type(WebDriver driver,String xpath,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        driver.findElement(By.xpath(xpath)).sendKeys(input);
    }

    public static void type(WebDriver driver,String xpath,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        driver.findElements(By.xpath(xpath)).get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String xpath,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        driver.findElement(By.xpath(xpath)).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String xpath,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        driver.findElements(By.xpath(xpath)).get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,String xpath,String text,int timeoutInSecs) {
        if (isSelected(driver, xpath, timeoutInSecs)) {
            new Select(driver.findElement(By.xpath(xpath))).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,String xpath,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, xpath, timeoutInSecs)) {
            new Select(driver.findElements(By.xpath(xpath)).get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,String xpath,String value,int timeoutInSecs) {
        if (isSelected(driver, xpath, timeoutInSecs)) {
            new Select(driver.findElement(By.xpath(xpath))).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,String xpath,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, xpath, timeoutInSecs)) {
            new Select(driver.findElements(By.xpath(xpath)).get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,String xpath,int index,int timeoutInSecs) {
        if (isSelected(driver, xpath, timeoutInSecs)) {
            new Select(driver.findElement(By.xpath(xpath))).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,String xpath,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, xpath, timeoutInSecs)) {
            new Select(driver.findElements(By.xpath(xpath)).get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,String xpath,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,xpath,timeoutInSecs);
        driver.findElement(By.xpath(xpath)).click();
    }

    public static void click(WebDriver driver,String xpath,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,xpath,timeoutInSecs);
        driver.findElements(By.xpath(xpath)).get(index).click();
    }

    public static String getText(WebDriver driver,String xpath,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return driver.findElement(By.xpath(xpath)).getText();
    }

    public static String getText(WebDriver driver,String xpath,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return driver.findElements(By.xpath(xpath)).get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String xpath,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return driver.findElement(By.xpath(xpath)).getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String xpath,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return driver.findElements(By.xpath(xpath)).get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String xpath,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return driver.findElements(By.xpath(xpath)).get(index).getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String xpath,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return driver.findElement(By.xpath(xpath)).getText().equals(text);
    }

    public static WebElement getElement(WebDriver driver,String xpath,int timeoutInSecs){
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return driver.findElement(By.xpath(xpath));
    }

    public static WebElement getElement(WebDriver driver,String xpath,int index,int timeoutInSecs){
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return driver.findElements(By.xpath(xpath)).get(index);
    }

    public static List<WebElement> getElements(WebDriver driver,String xpath,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.xpath(xpath));
        return list;
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String xpath,int timeoutInSecs){
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return element.findElement(By.xpath(xpath));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String xpath,int timeoutInSecs){
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return elements.get(index).findElement(By.xpath(xpath));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String xpath,int timeoutInSecs){
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        return element.findElements(By.xpath(xpath));
    }

    public static void multiSelectOptions(WebDriver driver, String xpath, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,xpath,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.xpath(xpath));
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

    public static void clickOption(WebDriver driver, String xpath, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,xpath,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.xpath(xpath));
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,String xpath,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,xpath,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,String xpath,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,xpath,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String xpath,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,xpath,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String xpath,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,xpath,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String xpath,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,xpath,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String xpath,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,xpath,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String xpath,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,xpath,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String xpath,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,xpath,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String xpath,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,xpath,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String xpath,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,xpath,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String xpath,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,xpath,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String xpath,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,xpath,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String xpath,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,xpath,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String xpath,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,xpath,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String xpath,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,xpath,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String xpath,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,xpath,index,timeoutInSecs));
    }
}
