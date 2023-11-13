package gov.gsa.sst.additions;



import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.mod.SelectSins;
import util.ActionByXpath;

public class AddLaborCategory extends SelectSins{
	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(AddProductModPage.class);
	private String headerText = "//h1[contains(text(), 'Add Labor Category and/or Service Offerings')]";
	
	public AddLaborCategory(ExecutionContext executionContext) {
		super(executionContext);
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return ActionByXpath.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Add Labor Category page");
			LeftNavigationMenu.navigateTo(driver, "Additions");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Additions");
		}
		Assert.assertTrue("Could not load Additions page for Add Labor Category  mod", isLoaded());
	}

}
