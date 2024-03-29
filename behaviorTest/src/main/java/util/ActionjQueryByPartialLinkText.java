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
public class ActionjQueryByPartialLinkText {

    public static void waitForElementToBeVisible(WebDriver driver, String partialLinkText, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(partialLinkText)));
    }

    public static void waitForElementToBePresent(WebDriver driver, String partialLinkText, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(partialLinkText)));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String partialLinkText, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.partialLinkText(partialLinkText)));
    }

    public static boolean isDisplayed(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
            return driver.findElement(By.partialLinkText(partialLinkText)).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
            return driver.findElement(By.partialLinkText(partialLinkText)).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
            return driver.findElement(By.partialLinkText(partialLinkText)).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver, timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(partialLinkText)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver,timeoutInSecs);
        PageLoad.checkPageLoadJquery(driver,timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.partialLinkText(partialLinkText)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }
    public static void clear(WebDriver driver, String partialLinkText, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        driver.findElement(By.partialLinkText(partialLinkText)).clear();
    }

    public static void clear(WebDriver driver, String partialLinkText,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        driver.findElements(By.partialLinkText(partialLinkText)).get(index).clear();
    }

    public static void type(WebDriver driver,String partialLinkText,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        driver.findElement(By.partialLinkText(partialLinkText)).sendKeys(input);
    }

    public static void type(WebDriver driver,String partialLinkText,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        driver.findElements(By.partialLinkText(partialLinkText)).get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String partialLinkText,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        driver.findElement(By.partialLinkText(partialLinkText)).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String partialLinkText,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        driver.findElements(By.partialLinkText(partialLinkText)).get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,String partialLinkText,String text,int timeoutInSecs) {
        if (isSelected(driver, partialLinkText, timeoutInSecs)) {
            new Select(driver.findElement(By.partialLinkText(partialLinkText))).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,String partialLinkText,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, partialLinkText, timeoutInSecs)) {
            new Select(driver.findElements(By.partialLinkText(partialLinkText)).get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,String partialLinkText,String value,int timeoutInSecs) {
        if (isSelected(driver, partialLinkText, timeoutInSecs)) {
            new Select(driver.findElement(By.partialLinkText(partialLinkText))).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,String partialLinkText,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, partialLinkText, timeoutInSecs)) {
            new Select(driver.findElements(By.partialLinkText(partialLinkText)).get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,String partialLinkText,int index,int timeoutInSecs) {
        if (isSelected(driver, partialLinkText, timeoutInSecs)) {
            new Select(driver.findElement(By.partialLinkText(partialLinkText))).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,String partialLinkText,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, partialLinkText, timeoutInSecs)) {
            new Select(driver.findElements(By.partialLinkText(partialLinkText)).get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,partialLinkText,timeoutInSecs);
        driver.findElement(By.partialLinkText(partialLinkText)).click();
    }

    public static void click(WebDriver driver,String partialLinkText,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,partialLinkText,timeoutInSecs);
        driver.findElements(By.partialLinkText(partialLinkText)).get(index).click();
    }

    public static String getText(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return driver.findElement(By.partialLinkText(partialLinkText)).getText();
    }

    public static String getText(WebDriver driver,String partialLinkText,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return driver.findElements(By.partialLinkText(partialLinkText)).get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String partialLinkText,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return driver.findElement(By.partialLinkText(partialLinkText)).getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String partialLinkText,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return driver.findElements(By.partialLinkText(partialLinkText)).get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String partialLinkText,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return driver.findElement(By.partialLinkText(partialLinkText)).getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String partialLinkText,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return driver.findElements(By.partialLinkText(partialLinkText)).get(index).getText().equals(text);
    }

    public static WebElement getElement(WebDriver driver,String partialLinkText,int timeoutInSecs){
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return driver.findElement(By.partialLinkText(partialLinkText));
    }

    public static WebElement getElement(WebDriver driver,String partialLinkText,int index,int timeoutInSecs){
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return driver.findElements(By.partialLinkText(partialLinkText)).get(index);
    }

    public static List<WebElement> getElements(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.partialLinkText(partialLinkText));
        return list;
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String partialLinkText,int timeoutInSecs){
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return element.findElement(By.partialLinkText(partialLinkText));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String partialLinkText,int timeoutInSecs){
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return elements.get(index).findElement(By.partialLinkText(partialLinkText));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String partialLinkText,int timeoutInSecs){
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        return element.findElements(By.partialLinkText(partialLinkText));
    }

    public static void multiSelectOptions(WebDriver driver, String partialLinkText, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,partialLinkText,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.partialLinkText(partialLinkText));
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

    public static void clickOption(WebDriver driver, String partialLinkText, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,partialLinkText,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.partialLinkText(partialLinkText));
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,partialLinkText,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,String partialLinkText,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,partialLinkText,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String partialLinkText,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,partialLinkText,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String partialLinkText,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,partialLinkText,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String partialLinkText,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,partialLinkText,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String partialLinkText,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,partialLinkText,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String partialLinkText,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,partialLinkText,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String partialLinkText,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,partialLinkText,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String partialLinkText,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,partialLinkText,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String partialLinkText,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,partialLinkText,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String partialLinkText,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,partialLinkText,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String partialLinkText,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,partialLinkText,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String partialLinkText,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,partialLinkText,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String partialLinkText,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,partialLinkText,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String partialLinkText,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,partialLinkText,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String partialLinkText,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,partialLinkText,index,timeoutInSecs));
    }
}
