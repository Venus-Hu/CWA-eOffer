package gov.gsa.sst.deletions;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.mod.SelectSins;
import util.ActionByXpath;

public class DeleteLaborCategoryPage extends SelectSins {
	private static Logger log = LoggerFactory.getLogger(DeleteLaborCategoryPage.class);
	private String header = "//h1";
	
	public DeleteLaborCategoryPage(ExecutionContext executionContext) {
		super(executionContext);
		this.executionContext = executionContext;
	}
	
	@Override
	protected boolean isLoaded() {
		try{
			String headerText = ActionByXpath.getText(driver, header, 2).toLowerCase();
			return headerText.contains("delete labor category");
		}catch (Exception e) {
			log.error("Could not find header - delete labor category");
			return false;
		}
	}

	@Override
	protected void load() {
		try {
			LeftNavigationMenu.navigateTo(driver, "Deletions");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Deletions");
		}
		try {
			executionContext.testPageFor508("Delete Labor category mod");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("Could not load Deletions page for Delete Labor category mod", isLoaded());
	}
}
