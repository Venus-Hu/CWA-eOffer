package gov.gsa.sst.selectmodtypes;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.modlist.ModListPage;
import util.ActionByPartialLinkText;

public class SelectModTypesPage extends Page{

	private static Logger log = LoggerFactory.getLogger(SelectModTypesPage.class);
	private final int TIMEOUT_IN_SECS = 3;
	private ExecutionContext executionContext;
	

	public SelectModTypesPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}
	
	@Override
	protected void load() {
		try {
			log.info("Loading Select mod sub type page");
			ActionByPartialLinkText.click(driver, "My eMods", TIMEOUT_IN_SECS);
		} catch (Exception ex) {
			ModListPage modListPage = new ModListPage(executionContext);
			modListPage.get();
		}
		try {
			executionContext.testPageFor508("Select mod type");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("'Select Modification Types' page did not load", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		return driver.getTitle().contains("Select Modification Types");
	}
	
	public void selectModSubTypes(JsonArray subTypes) {
		subTypes.forEach((subType) -> {
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//label[contains(text(),'"+ subType.getAsString()+"')]"));
		});
	}
	
	public void populateForm(JsonArray modListArray)
	{
		  log.info("Selecting modification types & sub types");
		  selectModSubTypes(modListArray);
		  Octo_SeleniumLibrary.clickElement(driver, By.id( "submitOnline"));
	}

}
