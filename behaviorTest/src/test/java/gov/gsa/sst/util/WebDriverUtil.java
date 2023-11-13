package gov.gsa.sst.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverUtil {
	private static Logger log = LoggerFactory.getLogger(WebDriverUtil.class);

	/**
	 * Select from options in drop-down located
	 * by 'name' attribute
	 * 
	 * @param driver
	 * @param name value of 'name' attribute
	 * @param option value to select
	 * @param timeOutInSeconds
	 */
	public static void selectByValue(WebDriver driver, String name, String option, int timeOutInSeconds) {
		log.info("Select by value: name='"+name+"', option='"+option+"'");
		WebElement selector = new WebDriverWait(driver, timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
		Select select = new Select(selector);
		select.selectByValue(option);
	}
	
	/**
	 * Clicks one element periodically and exits when
	 * 1) a second element is displayed or 2) if the second
	 * element is not displayed after the desired 
	 * max. waiting time.
	 * 
	 * @param driver
	 * @param elementToClick
	 * @param elementToWaitFor
	 * @param maxTime
	 * @param pollingTime
	 * @param timeUnit
	 * @return true if the element is displayed within the waiting time window
	 */
	public static boolean wait(WebDriver driver, By elementToClick, By elementToWaitFor, long maxTime, long pollingTime, TimeUnit timeUnit) {
		boolean elementFound = false;
		long maxTimeMillisecs = TimeUnit.MILLISECONDS.convert(maxTime, timeUnit);
		long startTimeMillisecs = System.currentTimeMillis();
		long pollingTimeMillisecs = TimeUnit.MILLISECONDS.convert(pollingTime, timeUnit);

		log.info("Entering wait-polling for "+elementToWaitFor.toString()+" element");
		do{
			log.info("Clicking element '"+elementToClick.toString()+"' ...");
			try {
				driver.findElement(elementToClick).click();
			} catch (NoSuchElementException e) {
				throw new RuntimeException("Element '" + elementToClick.toString() + "' could not be found");
			}
            
            log.info("Try to find '"+elementToWaitFor.toString()+"' element...");
            try {
            	driver.findElement(elementToWaitFor).isDisplayed();
            	elementFound = true;
            	break;
            } catch (NoSuchElementException e) {
            	log.info("Element '" + elementToWaitFor.toString() + "' not yet visible");
            }
            
            try {
            	
            	log.info("Waited for secs: "+ (System.currentTimeMillis() - startTimeMillisecs)/1000 +"\nSleeping for " + pollingTimeMillisecs/1000);
				Thread.sleep(pollingTimeMillisecs);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		} while ((System.currentTimeMillis() - startTimeMillisecs) < maxTimeMillisecs);
		log.info("Exit wait-polling for "+elementToWaitFor.toString()+" element - was found? " + elementFound);
		return elementFound;
	}
	
	public static boolean isSelected(WebDriver driver, By by, int timeOutInSeconds) {
		return new WebDriverWait(driver, timeOutInSeconds)
		.until(ExpectedConditions.elementToBeClickable(by)).isSelected();
	}

}
