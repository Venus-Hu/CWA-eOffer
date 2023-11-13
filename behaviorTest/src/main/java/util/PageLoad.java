package util;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class PageLoad {

	
	/**
	 * checkPage load is only checking the dom state as complete which selenium is also doing that
	 * for jquery related application use checkPageLoadJquery method
	 * @param driver
	 */
	public static void checkPageLoad(WebDriver driver, int timeoutinsecs)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutinsecs);
	    wait.until(new ExpectedCondition<Boolean>() {
	    	@Override
	        public Boolean apply(WebDriver driver) {
	            return ((JavascriptExecutor) driver).executeScript(
	                "return document.readyState"
	            ).equals("complete");
	        }
	    });
	}
	
	public WebElement waitForAsyncElemVisible(WebDriver driver, final By locator, TimeUnit timeunit, int timeout, int poller) {
	    Wait<WebDriver> waitForContent = new FluentWait<WebDriver>(driver)
	    		.withTimeout(timeout, timeunit)
	    		.pollingEvery(poller, timeunit)
	    		.ignoring(NoSuchElementException.class);
	    
	    WebElement foo = waitForContent.until(new Function<WebDriver, WebElement>() {
	    	public WebElement apply(WebDriver driver) {
		    	if(driver.findElement(locator).isDisplayed())
		    		{
		    			return driver.findElement(locator);
		    		};
					return null;
		    	}
		});
	    return foo;
	}

	/**
	 * to check whether jquery is active or not
	 * @param driver
	 * @param timeoutinsecs
	 */
	public static void checkPageLoadJquery(WebDriver driver,int timeoutinsecs)
	{
		WebDriverWait wait = new WebDriverWait(driver, timeoutinsecs);
	    wait.until(new ExpectedCondition<Boolean>() {
	    	@Override
	        public Boolean apply(WebDriver driver) {
	            return ((JavascriptExecutor) driver).executeScript(
	                "return jQuery.active"
	            ).equals("0");
	        }
	    });
	}

	/**
	 * For places where jquery active is 0 and class loader visibility is false
	 * @param driver
	 * @param timeoutinsecs
	 * @param loaderClassName
	 */
	public static void checkPageLoadJqueryLoader(WebDriver driver,int timeoutinsecs,final String loaderClassName)
	{
		WebDriverWait wait = new WebDriverWait(driver,timeoutinsecs);
	    wait.until(new ExpectedCondition<Boolean>() {
	    	@Override
	        public Boolean apply(WebDriver driver) {
	            return (((JavascriptExecutor) driver).executeScript(
	                "return jQuery.active"
	            ).equals("0") && ((JavascriptExecutor) driver).executeScript(
		                "return $('."+loaderClassName+"').is(':visible')"
	    	            ).equals(false));
	        }
	    });
	}
	
	public static void checkPageLoadJqueryLoader(WebDriver driver,int timeoutinsecs,final By locator)
	{
	    WebDriverWait wait = new WebDriverWait(driver, timeoutinsecs);
	    wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (((JavascriptExecutor) driver).executeScript(
		                "return jQuery.active"
		            ).equals("0") && !(driver.findElement(locator).isDisplayed()));
			}
	    });
	}

	/**
	 * Angular Page verification
	 * @param driver
	 * @param timeoutinsecs
	 */
	public static void checkPageLoadAngular(WebDriver driver,int timeoutinsecs)
	{
	    WebDriverWait wait = new WebDriverWait(driver, timeoutinsecs);
	    wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				//window.angular && !(window.angular.version && window.angular.version.major > 1)
				//(window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)
                return Boolean.valueOf(((JavascriptExecutor) driver).executeScript("return (window.angular !== undefined) && (angular.element(document).injector() !== undefined) && (angular.element(document).injector().get('$http').pendingRequests.length === 0)").toString());
			}
	    });
	}
	
}
