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
public class ByClassName {

    public static void waitForElementToBeVisible(WebDriver driver, String className, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
    }

    public static void waitForElementToBePresent(WebDriver driver, String className, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String className, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.className(className)));
    }

    public static boolean isDisplayed(WebDriver driver,String className,int timeoutInSecs)
    {

        try {
            waitForElementToBeVisible(driver,className,timeoutInSecs);
            return driver.findElement(By.className(className)).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,String className,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,className,timeoutInSecs);
            return driver.findElement(By.className(className)).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,String className,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,className,timeoutInSecs);
            return driver.findElement(By.className(className)).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,String className,int timeoutInSecs)
    {
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,String className,int timeoutInSecs)
    {
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.className(className)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }
    public static void clear(WebDriver driver, String className, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        driver.findElement(By.className(className)).clear();
    }

    public static void clear(WebDriver driver, String className,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        driver.findElements(By.className(className)).get(index).clear();
    }

    public static void type(WebDriver driver,String className,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        driver.findElement(By.className(className)).sendKeys(input);
    }

    public static void type(WebDriver driver,String className,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        driver.findElements(By.className(className)).get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String className,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        driver.findElement(By.className(className)).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String className,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        driver.findElements(By.className(className)).get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,String className,String text,int timeoutInSecs) {
        if (isSelected(driver, className, timeoutInSecs)) {
            new Select(driver.findElement(By.className(className))).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,String className,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, className, timeoutInSecs)) {
            new Select(driver.findElements(By.className(className)).get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,String className,String value,int timeoutInSecs) {
        if (isSelected(driver, className, timeoutInSecs)) {
            new Select(driver.findElement(By.className(className))).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,String className,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, className, timeoutInSecs)) {
            new Select(driver.findElements(By.className(className)).get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,String className,int index,int timeoutInSecs) {
        if (isSelected(driver, className, timeoutInSecs)) {
            new Select(driver.findElement(By.className(className))).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,String className,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, className, timeoutInSecs)) {
            new Select(driver.findElements(By.className(className)).get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,String className,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,className,timeoutInSecs);
        driver.findElement(By.className(className)).click();
    }

    public static void click(WebDriver driver,String className,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,className,timeoutInSecs);
        driver.findElements(By.className(className)).get(index).click();
    }

    public static String getText(WebDriver driver,String className,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return driver.findElement(By.className(className)).getText();
    }

    public static String getText(WebDriver driver,String className,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return driver.findElements(By.className(className)).get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String className,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return driver.findElement(By.className(className)).getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return driver.findElements(By.className(className)).get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return driver.findElements(By.className(className)).get(index).getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String className,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return driver.findElement(By.className(className)).getText().equals(text);
    }

    public static WebElement getElement(WebDriver driver,String className,int timeoutInSecs){
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return driver.findElement(By.className(className));
    }

    public static WebElement getElement(WebDriver driver,String className,int index,int timeoutInSecs){
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return driver.findElements(By.className(className)).get(index);
    }

    public static List<WebElement> getElements(WebDriver driver,String className,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.className(className));
        return list;
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String className,int timeoutInSecs){
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return element.findElement(By.className(className));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String className,int timeoutInSecs){
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return elements.get(index).findElement(By.className(className));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String className,int timeoutInSecs){
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        return element.findElements(By.className(className));
    }

    public static void multiSelectOptions(WebDriver driver, String className, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,className,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.className(className));
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

    public static void clickOption(WebDriver driver, String className, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,className,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.className(className));
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,String className,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,className,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,String className,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,className,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String className,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,className,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String className,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,className,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String className,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,className,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,className,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String className,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,className,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,className,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String className,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,className,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,className,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String className,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,className,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String className,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,className,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String className,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,className,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String className,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,className,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String className,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,className,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String className,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,className,index,timeoutInSecs));
    }
}
