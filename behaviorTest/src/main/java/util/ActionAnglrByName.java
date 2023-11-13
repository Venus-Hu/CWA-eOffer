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
 * For Angular Application Only
 * This class is to perform action for Angular Application using name
 * input to method will be mainly your webdriver instance,name and time out in secs which will check for different pageload strategies (explicit wait,pageload and angular page ready state)
 */
public class ActionAnglrByName {

    public static void waitForElementToBeVisible(WebDriver driver, String name, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
    }

    public static void waitForElementToBePresent(WebDriver driver, String name, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.name(name)));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String name, int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.name(name)));
    }

    public static boolean isDisplayed(WebDriver driver,String name,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,name,timeoutInSecs);
            return driver.findElement(By.name(name)).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,String name,int timeoutInSecs)
    {

        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,name,timeoutInSecs);
            return driver.findElement(By.name(name)).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,String name,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            waitForElementToBeVisible(driver,name,timeoutInSecs);
            return driver.findElement(By.name(name)).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,String name,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver, timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver, timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,String name,int timeoutInSecs)
    {
        PageLoad.checkPageLoad(driver,timeoutInSecs);
        PageLoad.checkPageLoadAngular(driver,timeoutInSecs);
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.name(name)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }
    public static void clear(WebDriver driver, String name, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        driver.findElement(By.name(name)).clear();
    }

    public static void clear(WebDriver driver, String name,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        driver.findElements(By.name(name)).get(index).clear();
    }

    public static void type(WebDriver driver,String name,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        driver.findElement(By.name(name)).sendKeys(input);
    }

    public static void type(WebDriver driver,String name,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        driver.findElements(By.name(name)).get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String name,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        driver.findElement(By.name(name)).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String name,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        driver.findElements(By.name(name)).get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,String name,String text,int timeoutInSecs) {
        if (isSelected(driver, name, timeoutInSecs)) {
            new Select(driver.findElement(By.name(name))).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,String name,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, name, timeoutInSecs)) {
            new Select(driver.findElements(By.name(name)).get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,String name,String value,int timeoutInSecs) {
        if (isSelected(driver, name, timeoutInSecs)) {
            new Select(driver.findElement(By.name(name))).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,String name,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, name, timeoutInSecs)) {
            new Select(driver.findElements(By.name(name)).get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,String name,int index,int timeoutInSecs) {
        if (isSelected(driver, name, timeoutInSecs)) {
            new Select(driver.findElement(By.name(name))).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,String name,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, name, timeoutInSecs)) {
            new Select(driver.findElements(By.name(name)).get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,String name,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,name,timeoutInSecs);
        driver.findElement(By.name(name)).click();
    }

    public static void click(WebDriver driver,String name,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,name,timeoutInSecs);
        driver.findElements(By.name(name)).get(index).click();
    }

    public static String getText(WebDriver driver,String name,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return driver.findElement(By.name(name)).getText();
    }

    public static String getText(WebDriver driver,String name,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return driver.findElements(By.name(name)).get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String name,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return driver.findElement(By.name(name)).getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String name,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return driver.findElements(By.name(name)).get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String name,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return driver.findElements(By.name(name)).get(index).getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String name,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return driver.findElement(By.name(name)).getText().equals(text);
    }

    public static WebElement getElement(WebDriver driver,String name,int timeoutInSecs){
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return driver.findElement(By.name(name));
    }

    public static WebElement getElement(WebDriver driver,String name,int index,int timeoutInSecs){
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return driver.findElements(By.name(name)).get(index);
    }

    public static List<WebElement> getElements(WebDriver driver,String name,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.name(name));
        return list;
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String name,int timeoutInSecs){
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return element.findElement(By.name(name));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String name,int timeoutInSecs){
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return elements.get(index).findElement(By.name(name));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String name,int timeoutInSecs){
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        return element.findElements(By.name(name));
    }

    public static void multiSelectOptions(WebDriver driver, String name, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,name,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.name(name));
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

    public static void clickOption(WebDriver driver, String name, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,name,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.name(name));
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,String name,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,name,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,String name,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,name,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String name,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,name,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String name,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,name,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String name,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,name,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String name,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,name,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String name,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,name,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String name,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,name,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String name,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,name,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String name,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,name,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String name,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,name,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String name,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,name,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String name,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,name,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String name,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,name,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String name,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,name,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String name,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,name,index,timeoutInSecs));
    }
}
