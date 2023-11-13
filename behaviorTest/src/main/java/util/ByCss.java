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
public class ByCss {

    public static void waitForElementToBeVisible(WebDriver driver, String cssSelector, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
    }

    public static void waitForElementToBePresent(WebDriver driver, String cssSelector, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String cssSelector, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
    }

    public static boolean isDisplayed(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
            return driver.findElement(By.cssSelector(cssSelector)).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
            return driver.findElement(By.cssSelector(cssSelector)).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
            return driver.findElement(By.cssSelector(cssSelector)).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }
    public static void clear(WebDriver driver, String cssSelector, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        driver.findElement(By.cssSelector(cssSelector)).clear();
    }

    public static void clear(WebDriver driver, String cssSelector,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        driver.findElements(By.cssSelector(cssSelector)).get(index).clear();
    }

    public static void type(WebDriver driver,String cssSelector,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        driver.findElement(By.cssSelector(cssSelector)).sendKeys(input);
    }

    public static void type(WebDriver driver,String cssSelector,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        driver.findElements(By.cssSelector(cssSelector)).get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String cssSelector,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        driver.findElement(By.cssSelector(cssSelector)).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String cssSelector,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        driver.findElements(By.cssSelector(cssSelector)).get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,String cssSelector,String text,int timeoutInSecs) {
        if (isSelected(driver, cssSelector, timeoutInSecs)) {
            new Select(driver.findElement(By.cssSelector(cssSelector))).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, cssSelector, timeoutInSecs)) {
            new Select(driver.findElements(By.cssSelector(cssSelector)).get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,String cssSelector,String value,int timeoutInSecs) {
        if (isSelected(driver, cssSelector, timeoutInSecs)) {
            new Select(driver.findElement(By.cssSelector(cssSelector))).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,String cssSelector,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, cssSelector, timeoutInSecs)) {
            new Select(driver.findElements(By.cssSelector(cssSelector)).get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,String cssSelector,int index,int timeoutInSecs) {
        if (isSelected(driver, cssSelector, timeoutInSecs)) {
            new Select(driver.findElement(By.cssSelector(cssSelector))).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,String cssSelector,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, cssSelector, timeoutInSecs)) {
            new Select(driver.findElements(By.cssSelector(cssSelector)).get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,cssSelector,timeoutInSecs);
        driver.findElement(By.cssSelector(cssSelector)).click();
    }

    public static void click(WebDriver driver,String cssSelector,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,cssSelector,timeoutInSecs);
        driver.findElements(By.cssSelector(cssSelector)).get(index).click();
    }

    public static String getText(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return driver.findElement(By.cssSelector(cssSelector)).getText();
    }

    public static String getText(WebDriver driver,String cssSelector,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return driver.findElements(By.cssSelector(cssSelector)).get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return driver.findElement(By.cssSelector(cssSelector)).getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return driver.findElements(By.cssSelector(cssSelector)).get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return driver.findElements(By.cssSelector(cssSelector)).get(index).getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return driver.findElement(By.cssSelector(cssSelector)).getText().equals(text);
    }

    public static WebElement getElement(WebDriver driver,String cssSelector,int timeoutInSecs){
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return driver.findElement(By.cssSelector(cssSelector));
    }

    public static WebElement getElement(WebDriver driver,String cssSelector,int index,int timeoutInSecs){
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return driver.findElements(By.cssSelector(cssSelector)).get(index);
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String cssSelector,int timeoutInSecs){
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return element.findElement(By.cssSelector(cssSelector));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String cssSelector,int timeoutInSecs){
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return elements.get(index).findElement(By.cssSelector(cssSelector));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String cssSelector,int timeoutInSecs){
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        return element.findElements(By.cssSelector(cssSelector));
    }

    public static List<WebElement> getElements(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.cssSelector(cssSelector));
        return list;
    }

    public static void multiSelectOptions(WebDriver driver, String cssSelector, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,cssSelector,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.cssSelector(cssSelector));
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

    public static void clickOption(WebDriver driver, String cssSelector, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,cssSelector,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.cssSelector(cssSelector));
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,cssSelector,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,String cssSelector,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,cssSelector,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String cssSelector,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,cssSelector,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String cssSelector,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,cssSelector,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,cssSelector,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,cssSelector,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,cssSelector,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,cssSelector,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,cssSelector,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,cssSelector,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String cssSelector,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,cssSelector,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String cssSelector,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,cssSelector,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String cssSelector,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,cssSelector,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String cssSelector,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,cssSelector,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String cssSelector,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,cssSelector,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String cssSelector,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,cssSelector,index,timeoutInSecs));
    }

}
