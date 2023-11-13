package gov.gsa.sst.option;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import util.ActionByXpath;

public class OptionModPage extends Page{
	
	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(OptionModPage.class);
	private ExecutionContext executionContext;
	//TODO to be replaced with header tomorrow
	String text = "//p[contains(.,'You have initiated an OPTION modification')]";
	
	public OptionModPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return ActionByXpath.isDisplayed(driver, text, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Option and extension modpage");
			LeftNavigationMenu.navigateTo(driver, "Option or Extension");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Option or Extension");
		}
		try {
			executionContext.testPageFor508("Option mod");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("Could not load Option or Extension page for Options mod", isLoaded());
	}
	
	public void populateForm(JsonObject jsonObj){
		String isTdr = jsonObj.get("isTDR").getAsString().substring(0, 1);
		CommonUtilPage.selectRadioOption(driver, "tdrOptInResponse", isTdr);
		
		Octo_SeleniumLibrary.clickElement(driver, By.id( "id_tdrResponseSave"));
	}

}
