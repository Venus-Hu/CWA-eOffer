package gov.gsa.sst.deletions;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.mod.SelectSins;
import util.ActionByXpath;

public class DeleteProductModPage extends SelectSins {

	//TODO update the header below when FPT and non FPT pages have same text
	//private String headerText = "//h1/text()[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'delete product(s)')]";
	private String header = "//h1";
	private static Logger log = LoggerFactory.getLogger(DeleteProductModPage.class);
	

	public DeleteProductModPage(ExecutionContext executionContext) {
		super(executionContext);
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		try {
			LeftNavigationMenu.navigateTo(driver, "Deletions");
		} catch (Exception ex) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Deletions");
		}
		try {
			executionContext.testPageFor508("Delete Product mod");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("Could not load Deletions page for Delete Product mod", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		//TODO Needed to change logic here as FPT and non FPT pages do not have same header text
		//return ActionByLocator.isDisplayed(driver, By.xpath(headerText), TIMEOUT_IN_SECS);
		try{
			String headerText = ActionByXpath.getText(driver, header, 2).toLowerCase();
			return headerText.contains("delete product(s)");
		}catch (Exception e) {
			log.error("Could not find header - delete product(s)");
			return false;
		}
	}

}
