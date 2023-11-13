package gov.gsa.sst.selectcontract;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.common.GetUEIandContract;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.dunspage.DunsPage;
import gov.gsa.sst.locators.EmodLocators;
import util.ActionByLocator;
import util.ActionByPartialLinkText;

public class ListOfContractsPage extends Page{
	
	private static Logger log = LoggerFactory.getLogger(ListOfContractsPage.class);
	private final int TIMEOUT_IN_SECS = 3;
	private By headerText = EmodLocators.selectContract_headerText;
    private ExecutionContext executionContext;

	
	public ListOfContractsPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		try {
			log.info("Loading 'List of Contracts Available' page");
			ActionByPartialLinkText.click(driver, "My eMods", TIMEOUT_IN_SECS);
		} catch (Exception ex) {
			//Ensure that DUNS is not overwritten for Mods during parallel execution
			DunsPage dunsPage = new DunsPage(executionContext);
			dunsPage.get();
			dunsPage.enterUEINumber(executionContext.getCurrentScenarioObj().get("UEI").getAsString());
			dunsPage.submitForm();
		}
		try {
			executionContext.testPageFor508("Contract Landing List");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("'List of Contracts' page did not load", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}
	
	public void populateForm()
	{
		
	//	ActionByName.selectByText(driver, "contractNumber", executionContext.getCurrentScenarioObj().get("contractNumber").getAsString(), TIMEOUT_IN_SECS);
		SeleniumHelper.performActionByLocatorName(driver, "contractNumber", GetUEIandContract.getContractNumberByFilter(executionContext.getCurrentScenarioObj().get("contractNumber").getAsString()));
		Octo_SeleniumLibrary.clickElement(driver, EmodLocators.selectContract_selectContractBtn);
	}

}
