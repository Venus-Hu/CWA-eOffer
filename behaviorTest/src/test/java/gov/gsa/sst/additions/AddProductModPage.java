package gov.gsa.sst.additions;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.mod.SelectSins;
import util.ActionByXpath;

public class AddProductModPage extends SelectSins{

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(AddProductModPage.class);
	
	public AddProductModPage(ExecutionContext executionContext) {
		super(executionContext);
		this.executionContext = executionContext;
	}
	
	@Override
	protected boolean isLoaded() {
		WebElement header = ActionByXpath.getElement(driver, "//h1", TIMEOUT_IN_SECS);
		return header.getText().equalsIgnoreCase("ADD PRODUCT(S)");
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Add Product page");
			LeftNavigationMenu.navigateTo(driver, "Additions");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Additions");
		}
		try {
			System.out.println("executionContent From Page");
			System.out.println("executionContent From Page: "+executionContext.toString());
			executionContext.testPageFor508("Add Product mod");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("Could not load Additions page for Add Product mod", isLoaded());
	}

		
}
