package util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by skumar on 9/19/2016.
 * This is class where methods have been overloaded and various javascript methods are called when you are not able to perform the required result with the basic action class
 */
public class ActionByJavaScript {

    /**
     * This one is deprecated and another method has been added where driver is the first parameter and element is the 2nd parameter
     * @param element
     * @param driver
     */
    @Deprecated
    public static void scrollIntoView(WebElement element, WebDriver driver)
    {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
    }


    @Deprecated
    public static void scrollIntoViewAndClick(WebElement element, WebDriver driver)
    {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].click();",element);
    }

    public static void scrollIntoView(WebDriver driver,WebElement element)
    {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
    }


    public static void scrollIntoViewAndClick(WebDriver driver,WebElement element)
    {
        scrollIntoView(driver,element);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].click();",element);
    }

    public static void click(WebDriver driver,WebElement element)
    {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].click();",element);
    }

    public static void scrollIntoView(WebDriver driver, List<WebElement> elements, int index)
    {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",elements.get(index));
    }


    public static void scrollIntoViewAndClick(WebDriver driver,List<WebElement> elements, int index)
    {
        scrollIntoView(driver,elements,index);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].click();",elements.get(index));
    }

    public static void click(WebDriver driver,List<WebElement> elements, int index)
    {
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].click();",elements.get(index));
    }


    public static void scrollIntoView(WebDriver driver,By locator)
    {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].scrollIntoView(true);",element);
    }


    public static void scrollIntoViewAndClick(WebDriver driver, By locator)
    {
        scrollIntoView(driver,locator);
        WebElement element = driver.findElement(locator);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].click();",element);
    }

    public static void click(WebDriver driver, By locator)
    {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript("arguments[0].click();",element);
    }

    public static void highlightElement(WebDriver driver,List<WebElement> elements,int index)
    {
        WebElement element = elements.get(index);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px dotted blue'", element);
    }
    public static void highlightElement(WebDriver driver,WebElement element)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px dotted blue'", element);
    }
    public static void highlightElement(WebDriver driver,By locator)
    {
        WebElement element = driver.findElement(locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px dotted blue'", element);
    }

    public static void hideElementByName(WebDriver driver,String name,int index){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByName('"+name+"')["+index+"].style.display='none'");
    }

    public static void showElementByName(WebDriver driver,String name,int index){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByName('"+name+"')["+index+"].style.display='block'");
    }

    public static void hideElementByTagName(WebDriver driver,String tagName,int index){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByTagName('"+tagName+"')["+index+"].style.display='none'");
    }

    public static void showElementByTagName(WebDriver driver,String tagName,int index){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByTagName('"+tagName+"')["+index+"].style.display='block'");
    }

    public static void hideElementByClassName(WebDriver driver,String className,int index){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByClassName('"+className+"')["+index+"].style.display='none'");
    }

    public static void showElementByClassName(WebDriver driver,String className,int index){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByClassName('"+className+"')["+index+"].style.display='block'");
    }

    public static void showElementById(WebDriver driver,String id){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('"+id+"').style.display='block'");
    }

    public static void hideElementById(WebDriver driver,String id){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('"+id+"').style.display='none'");
    }

}
