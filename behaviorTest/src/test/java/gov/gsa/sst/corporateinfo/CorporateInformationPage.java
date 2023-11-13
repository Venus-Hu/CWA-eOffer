/**
 * 
 */
package gov.gsa.sst.corporateinfo;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.GetUEIandContract;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.modlist.ModListPage;
import gov.gsa.sst.offerslist.OffersListPage;
import gov.gsa.sst.util.DataUtil;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByXpath;
import util.TableElementUtils;

/**
 * @author amulay
 *
 */
public class CorporateInformationPage extends Page {

	private static Logger log = LoggerFactory.getLogger(CorporateInformationPage.class);
	private final int TIMEOUT_IN_SECS = 3;
	private By headerText = By.xpath("//h1[contains(text(), 'CORPORATE INFORMATION')]");
	
	private ExecutionContext executionContext;


	public CorporateInformationPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		boolean isOffer = CommonUtilPage.isOffer(executionContext);
		try {
			log.info("Loading Corporate Information page");
			if(isOffer){
				boolean flag = CommonUtilPage.isSolicitationApt(executionContext);		
				if(!flag)
					throw new Exception("The solicitation number specified in data does not match the one on UI");
			}	
			LeftNavigationMenu.navigateTo(driver, "Corporate Information");	
		} catch(Exception e) {
			if (isOffer){
				OffersListPage offerListPage = new OffersListPage(executionContext);
				String scheduleNumber = executionContext.getCurrentScenarioObj().get("scheduleNumber").getAsString();
				offerListPage.get();
				
				offerListPage.editOffer(scheduleNumber);
			} else {
				String contractNumber = GetUEIandContract.getContractNumberByFilter(executionContext.getCurrentScenarioObj().get("contractNumber").getAsString());
				ModListPage modListPage = new ModListPage(executionContext);
				modListPage.get();
				modListPage.createNewMod(contractNumber);
				//TODO
			}
		}
		try {
			executionContext.testPageFor508("Corporate information");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Corporate Information page is not loaded for DUNS: " + DataUtil.getUEI(executionContext), isLoaded());			
	}

	public void verifyScheduleNumber(String scheduleNumber){
		Assert.assertTrue("Could not verify Solictionation number is: "+ scheduleNumber,
				ActionByXpath.getText(driver, "//tr/td[contains(.,'Schedule')]/following-sibling::td[1]", TIMEOUT_IN_SECS).contains(scheduleNumber));	
	}

	public boolean isUEIMatch(String ueiNumber) {          
		WebElement table = ActionById.getElement(driver, "corpDUNS", TIMEOUT_IN_SECS);
		WebElement ueiRow = TableElementUtils.getNthTableRow(table, 0);

		String rowText = ueiRow.getText();
		log.info("UEI row data: " + rowText);
		if (rowText.contains(ueiNumber)) {
			log.info("Expected and actual UEI number are : '" + ueiNumber + "'");
			return true;
		} else {
			log.info("Failed to match expected UEI number '" + ueiNumber + "' with actual one '" + rowText + "'");
			return false;
		}
	}
	

	public void verifyUEINumber(String ueiNumber) {
		Assert.assertTrue("Failed to match expected UEI number", isUEIMatch(ueiNumber));
	}

	public void submitForm() {
		// Cannot use name attribute as its common for 2 buttons
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@value='Save and Continue' and @name='saveAndContinue']"));
	}
}
