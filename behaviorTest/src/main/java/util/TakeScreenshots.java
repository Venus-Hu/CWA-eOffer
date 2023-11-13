package util;
import cucumber.api.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.io.IOException;


public class TakeScreenshots {
/**
 * get the scenario using @Before and then pass the driver and scenario and it will put the screenshot for that step
 * @param driver
 * @param scenario
 * @throws IOException
 */
	public static void takeScreenshot(WebDriver driver, Scenario scenario) throws IOException
	{
            try {
				if(driver instanceof TakesScreenshot) {
	                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES); 
	                scenario.embed(screenshot, "image/png");
            	}
            } catch (WebDriverException wde) {  
                System.err.println(wde.getMessage());  
            } catch (ClassCastException cce) {  
                cce.printStackTrace();  
            }
	}
}
