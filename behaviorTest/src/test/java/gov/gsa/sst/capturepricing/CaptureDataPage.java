package gov.gsa.sst.capturepricing;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;


public class CaptureDataPage extends Page {

	private final int TIMEOUT_IN_SECS = 10;	
	private ExecutionContext executionContext;
	private static Logger log = LoggerFactory.getLogger(CaptureDataPage.class);
	
	public CaptureDataPage(WebDriver driver) {
		super(driver);
	}

	public CaptureDataPage(ExecutionContext executionContext)
	{
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}
	
	@Override
	protected void load() {
		log.info("Loading Capture Pricing page");
		String menuItem = "Capture Pricing";
		try {
			boolean flag = CommonUtilPage.isSolicitationApt(executionContext);		
			if(!flag)
				throw new Exception("The solicitation number specified in data does not match the one on UI");
			LeftNavigationMenu.navigateTo(driver, menuItem);
			
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			LeftNavigationMenu.navigateTo(driver, menuItem);
		}
		try {
			executionContext.testPageFor508("Capture Pricing Data");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("Capture Pricing page is not loaded", isLoaded());

	}

	@Override
	protected boolean isLoaded() {
		return driver.getTitle().contains("Capture Pricing");
	}

	public void selectTemplateOption(){
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnToDownloadTemplate"));
	}
	
	public void selectEdiOption(){
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnToEDI"));
	}

	public void selectFormEntryOption(){
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnToEnterOnScreen"));
	}
	
	public void validateData(){
		Octo_SeleniumLibrary.clickElement(driver, By.id( "btnValidateProductData"));
	}
}
