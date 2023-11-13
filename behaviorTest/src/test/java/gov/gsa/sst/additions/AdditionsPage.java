package gov.gsa.sst.additions;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.solicitationclause.CommercialSalesPracticePage;
import gov.gsa.sst.util.LoadProperties;
import util.ActionById;
import util.ActionByName;

public class AdditionsPage extends Page {

	private String reviseBtnName = "change";
	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(AdditionsPage.class);

	private ExecutionContext executionContext;

	public AdditionsPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void load() {
		// TODO Auto-generated method stub

	}

	public void clickReviseBtn() {
		Octo_SeleniumLibrary.clickElement(driver, By.name(reviseBtnName));
	}

	public boolean verifyReviseCspExists() {
		return ActionByName.isDisplayed(driver, reviseBtnName, TIMEOUT_IN_SECS);
	}

	public void reviseCspForAddSinMod() {
		JsonObject scenarioObj = executionContext.getCurrentScenarioObj();
		if (ActionByName.isDisplayed(driver, "change", TIMEOUT_IN_SECS)) {
			if (scenarioObj.has("commercialSalesPractice")) {
				clickReviseBtn();
				JsonObject cspObj = scenarioObj.getAsJsonObject("commercialSalesPractice");
				CommercialSalesPracticePage cspPage = new CommercialSalesPracticePage(executionContext);
			
				// The below condition is to check for dummy FPT contracts used in cloud
				// Once we start using actual contracts, this conditional code can be removed
				String scenarioName = scenarioObj.get("scenarioName").getAsString();
				if (LoadProperties.getProperty("environment").trim().equalsIgnoreCase("cloud")
						&& scenarioName.contains("FPT") && !scenarioName.contains("on-FPT")) {
					log.info("Running the Offer CSP page populate method for a mod as it is using dummy contracts");
					cspPage.populateForm(cspObj);
				} else
					cspPage.populateFormForMod(cspObj);

			} else
				log.error("'Commercial Sales Practice' data is not provided.");
		}
	}

	/**
	 * This method is needed to handle fastlane mods
	 * @param modDetailsObj
	 * 
	 * 
	 */
	public void updateFastlane(JsonObject modDetailsObj) {
		String fastlane;
		if (!modDetailsObj.get("fastlane").getAsString().isEmpty()) {
			fastlane = modDetailsObj.get("fastlane").getAsString();
			CommonUtilPage.selectRadioOption(driver, "fastlaneRequired", fastlane);
			Octo_SeleniumLibrary.clickElement(driver, By.xpath("//*[@value='Save']"));
		}
	}

	
	public void update2Git(JsonObject modDetailsObj) {
		String twoGit;
		if(!modDetailsObj.get("2Git").getAsString().isEmpty()) {
			twoGit = modDetailsObj.get("2Git").getAsString();
			CommonUtilPage.selectRadioOption(driver, "responseTotwoGit", twoGit);
			Octo_SeleniumLibrary.clickElement(driver, By.xpath("//*[@value='Save' and @name='change']"));
		}
	}
	
	/**
	 * Returns true if Fastlane question is displayed else false
	 * @return
	 */
	public boolean isFastlaneDisplayed() {     
		return ActionById.isDisplayed(driver, "fastlaneQuest", TIMEOUT_IN_SECS);
	}
	
	public boolean is2GitDisplayed() {
		return ActionById.isDisplayed(driver, "twoGitQuest", TIMEOUT_IN_SECS);
	}
}
