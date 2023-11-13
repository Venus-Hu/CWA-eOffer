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
public class ById {

    public static void waitForElementToBeVisible(WebDriver driver, String id, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    public static void waitForElementToBePresent(WebDriver driver, String id, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }


    public static void waitForElementToBeClickable(WebDriver driver, String id, int timeoutInSecs)
    {
        new WebDriverWait(driver,timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.id(id)));
    }

    public static boolean isDisplayed(WebDriver driver,String id,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,id,timeoutInSecs);
            return driver.findElement(By.id(id)).isDisplayed();
        }catch(Exception ex){
            return false;
        }
    }


    public static boolean isEnabled(WebDriver driver,String id,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,id,timeoutInSecs);
            return driver.findElement(By.id(id)).isEnabled();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isSelected(WebDriver driver,String id,int timeoutInSecs)
    {
        try {
            waitForElementToBeVisible(driver,id,timeoutInSecs);
            return driver.findElement(By.id(id)).isSelected();
        }catch(Exception ex){
            return false;
        }
    }

    public static boolean isVisible(WebDriver driver,String id,int timeoutInSecs)
    {
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }

    public static boolean isClickable(WebDriver driver,String id,int timeoutInSecs)
    {
        try {
            new WebDriverWait(driver, timeoutInSecs).until(ExpectedConditions.elementToBeClickable(By.id(id)));
            return true;
        }catch (Exception ex)
        {
            return false;
        }
    }
    public static void clear(WebDriver driver, String id, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        driver.findElement(By.id(id)).clear();
    }

    public static void clear(WebDriver driver, String id,int index, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        driver.findElements(By.id(id)).get(index).clear();
    }

    public static void type(WebDriver driver,String id,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        driver.findElement(By.id(id)).sendKeys(input);
    }

    public static void type(WebDriver driver,String id,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        driver.findElements(By.id(id)).get(index).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String id,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        driver.findElement(By.id(id)).sendKeys(input);
    }

    public static void sendKeys(WebDriver driver,String id,int index,String input,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        driver.findElements(By.id(id)).get(index).sendKeys(input);
    }

    public static void selectByText(WebDriver driver,String id,String text,int timeoutInSecs) {
        if (isSelected(driver, id, timeoutInSecs)) {
            new Select(driver.findElement(By.id(id))).selectByVisibleText(text);
        }
    }

    public static void selectByText(WebDriver driver,String id,int index,String text,int timeoutInSecs) {
        if (isSelected(driver, id, timeoutInSecs)) {
            new Select(driver.findElements(By.id(id)).get(index)).selectByVisibleText(text);
        }
    }

    public static void selectByValue(WebDriver driver,String id,String value,int timeoutInSecs) {
        if (isSelected(driver, id, timeoutInSecs)) {
            new Select(driver.findElement(By.id(id))).selectByValue(value);
        }
    }

    public static void selectByValue(WebDriver driver,String id,int index,String value,int timeoutInSecs) {
        if (isSelected(driver, id, timeoutInSecs)) {
            new Select(driver.findElements(By.id(id)).get(index)).selectByValue(value);
        }
    }

    public static void selectByIndex(WebDriver driver,String id,int index,int timeoutInSecs) {
        if (isSelected(driver, id, timeoutInSecs)) {
            new Select(driver.findElement(By.id(id))).selectByIndex(index);
        }
    }

    public static void selectByIndex(WebDriver driver,String id,int index,int indexNo,int timeoutInSecs) {
        if (isSelected(driver, id, timeoutInSecs)) {
            new Select(driver.findElements(By.id(id)).get(index)).selectByIndex(indexNo);
        }
    }

    public static void click(WebDriver driver,String id,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,id,timeoutInSecs);
        driver.findElement(By.id(id)).click();
    }

    public static void click(WebDriver driver,String id,int index,int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,id,timeoutInSecs);
        driver.findElements(By.id(id)).get(index).click();
    }

    public static String getText(WebDriver driver,String id,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return driver.findElement(By.id(id)).getText();
    }

    public static String getText(WebDriver driver,String id,int index,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return driver.findElements(By.id(id)).get(index).getText();
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String id,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return driver.findElement(By.id(id)).getText().contains(text);
    }

    public static boolean getTextContainsToVerify(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return driver.findElements(By.id(id)).get(index).getText().contains(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return driver.findElements(By.id(id)).get(index).getText().equals(text);
    }

    public static boolean getTextEqualToVerify(WebDriver driver,String id,String text,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return driver.findElement(By.id(id)).getText().equals(text);
    }

    public static WebElement getElement(WebDriver driver,String id,int timeoutInSecs){
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return driver.findElement(By.id(id));
    }

    public static WebElement getElement(WebDriver driver,String id,int index,int timeoutInSecs){
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return driver.findElements(By.id(id)).get(index);
    }

    public static List<WebElement> getElements(WebDriver driver,String id,int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.id(id));
        return list;
    }

    public static WebElement getElementInElement(WebDriver driver,WebElement element,String id,int timeoutInSecs){
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return element.findElement(By.id(id));
    }

    public static WebElement getElementInElements(WebDriver driver,List<WebElement> elements,int index,String id,int timeoutInSecs){
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return elements.get(index).findElement(By.id(id));
    }


    public static List<WebElement> getElementsInElement(WebDriver driver,WebElement element,String id,int timeoutInSecs){
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        return element.findElements(By.id(id));
    }

    public static void multiSelectOptions(WebDriver driver, String id, List<String> text, int timeoutInSecs)
    {
        waitForElementToBeVisible(driver,id,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.id(id));
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

    public static void clickOption(WebDriver driver, String id, String text, int timeoutInSecs)
    {
        waitForElementToBeClickable(driver,id,timeoutInSecs);
        List<WebElement> list = driver.findElements(By.id(id));
        for(int i=0;i<list.size();i++)
        {
            if(text.equalsIgnoreCase(list.get(i).getText()))
            {
                list.get(i).click();
                break;
            }
        }
    }

    public static void assertVisible(WebDriver driver,String id,int timeoutInSecs)
    {
        Assert.assertTrue(isVisible(driver,id,timeoutInSecs));
    }

    public static void assertVisible(WebDriver driver,String id,String msg,int timeoutInSecs)
    {
        Assert.assertTrue(msg,isVisible(driver,id,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String id,int timeoutInSecs)
    {
        Assert.assertFalse(isVisible(driver,id,timeoutInSecs));
    }

    public static void assertNotVisible(WebDriver driver,String id,String msg,int timeoutInSecs)
    {
        Assert.assertFalse(msg,isVisible(driver,id,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String id,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,id,text,timeoutInSecs));
    }

    public static void assertTextEqualsTrue(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextEqualToVerify(driver,id,index,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String id,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,id,text,timeoutInSecs));
    }

    public static void assertTextContainsTrue(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
        Assert.assertTrue(getTextContainsToVerify(driver,id,index,text,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String id,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,id,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
        Assert.assertEquals(text,getText(driver,id,index,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String id,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,id,timeoutInSecs));
    }

    public static void assertEqualsText(WebDriver driver,String id,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertEquals(msg,text,getText(driver,id,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String id,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,id,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String id,int index,String text,int timeoutInSecs)
    {
        Assert.assertNotEquals(text,getText(driver,id,index,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String id,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,id,timeoutInSecs));
    }

    public static void assertNotEqualsText(WebDriver driver,String id,int index,String text,String msg,int timeoutInSecs)
    {
        Assert.assertNotEquals(msg,text,getText(driver,id,index,timeoutInSecs));
    }
}
