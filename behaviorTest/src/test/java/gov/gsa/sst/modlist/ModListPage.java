package gov.gsa.sst.modlist;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.common.GetUEIandContract;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.dunspage.DunsPage;
import gov.gsa.sst.selectcontract.ListOfContractsPage;
import gov.gsa.sst.selectmodtypes.SelectModTypesPage;
import gov.gsa.sst.util.DataUtil;
import gov.gsa.sst.util.DownloadUtil;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByPartialLinkText;
import util.ActionByXpath;
import util.TableElementUtils;

public class ModListPage extends Page {

	private static Logger log = LoggerFactory.getLogger(ModListPage.class);
	private final int TIMEOUT_IN_SECS = 3;
	private ExecutionContext executionContext;
	private String nextLink = "//div/h2[contains(., 'Saved Modifications')]/following-sibling::div//a[text()='Next']";

	public ModListPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		try {
			log.info("Loading Mod List page");
			if (CommonUtilPage.isUEICorrect(executionContext)) {
				if (CommonUtilPage.isUserCorrect(executionContext)) {
					if (ActionByLocator.isDisplayed(driver,
							By.xpath("//h1[contains(text(), 'List of Contracts Available')]"), 1)) {
						ListOfContractsPage contractsPage = new ListOfContractsPage(executionContext);
						contractsPage.populateForm();
					} else
						ActionByPartialLinkText.click(driver, "My eMods", 2);
				} else
					throw new Exception("The user specified in data does not match the one on UI");
			} else
				throw new Exception("The DUNS number in data does not match the one on UI");
		} catch (Exception ex) {
			DunsPage dunsPage = new DunsPage(executionContext);
			dunsPage.get();
			dunsPage.enterUEINumber(DataUtil.getUEI(executionContext));
			dunsPage.submitForm();

			ListOfContractsPage contractsPage = new ListOfContractsPage(executionContext);
			contractsPage.get();
			contractsPage.populateForm();
		}
		try {
			executionContext.testPageFor508("Contract List");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("'Contract List' page did not load", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		return driver.getTitle().equalsIgnoreCase("My Modifications");
	}

	public void editOrCreateMod(String contractNumber, String modType) {
		/*
		 * WebElement element = getRowByContractAndModType(contractNumber, modType,
		 * "Saved Modifications"); if (element != null){ editSavedMod(contractNumber);
		 * }else
		 */
		createNewMod(contractNumber);
	}

	public void createNewMod(String contract) {
		log.info("Create mod for contract number " + contract);
		ActionByName.selectByText(driver, "contractNumber", contract, TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.name( "submitOnline"));

		SelectModTypesPage modTypePage = new SelectModTypesPage(executionContext);
		modTypePage.get();
		modTypePage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonArray("modTypes"));
	}

	public void editSavedMod(String contractNumber, String modType) {
		log.info("Editing saved emod of type: " + modType);
		JsonObject dataObj = executionContext.getCurrentScenarioObj();
		if (dataObj.has("gsaMod") && dataObj.get("gsaMod").getAsString().equalsIgnoreCase("Y")) {
			if (ActionByXpath.isDisplayed(driver, "//h2[@id='gsamods']/span[contains(@class,'glyphicon-plus-sign')]",
					1))
				Octo_SeleniumLibrary.clickElement(driver, By.xpath(
						"//h2[@id='gsamods']/span[contains(@class,'glyphicon-plus-sign')]"));
			WebElement element = getRowByContractAndModType(contractNumber, modType, "gsamodstable");
			if (element != null) {
				ActionByLocator
						.getElementInElement(driver, element, By.xpath(".//input[@value='Edit Mod']"), TIMEOUT_IN_SECS)
						.click();
			}
		} else {
			WebElement element = getRowByContractAndModType(contractNumber, modType, "savedmodstable");
			if (element != null) {
				ActionByLocator
						.getElementInElement(driver, element, By.xpath(".//input[@value='Edit Mod']"), TIMEOUT_IN_SECS)
						.click();
			}
		}
	}

	public void viewMod(String contractNumber, String modType) {
		log.info("Viewing emod of type: " + modType);
		JsonObject dataObj = executionContext.getCurrentScenarioObj();
		if (dataObj.has("gsaMod") && dataObj.get("gsaMod").getAsString().equalsIgnoreCase("Y")) {
			if (ActionByXpath.isDisplayed(driver, "//h2[@id='gsamods']/span[contains(@class,'glyphicon-plus-sign')]",
					1))
				Octo_SeleniumLibrary.clickElement(driver, By.xpath(
						"//h2[@id='gsamods']/span[contains(@class,'glyphicon-plus-sign')]"));
			WebElement element = getRowByContractAndModType(contractNumber, modType, "gsamodstable");
			if (element != null) {
				ActionByLocator
						.getElementInElement(driver, element, By.xpath(".//input[@value='View Mod']"), TIMEOUT_IN_SECS)
						.click();
			}
		} else {
			WebElement element = getRowByContractAndModType(contractNumber, modType, "savedmodstable");
			if (element != null) {
				ActionByLocator
						.getElementInElement(driver, element, By.xpath(".//input[@value='View Mod']"), TIMEOUT_IN_SECS)
						.click();
			}
		}
	}

	public Map<String, Boolean> viewDocument(JsonArray array) throws Exception {

		for (JsonElement documentToVerify : array) {
			JsonObject document = documentToVerify.getAsJsonObject();
			String fileName = document.get("filename").getAsString();
			String docType = document.get("documentType").getAsString();

			// delete all similar named files before downloading new ones
			DownloadUtil.deleteFiles(fileName);

			WebElement table = ActionByXpath.getElement(driver, "//div[@id='table2']/table", TIMEOUT_IN_SECS);
			WebElement row = TableElementUtils.getTableRowByCellValue(table, "Type", docType);
			row.findElement(By.xpath(".//a[text()='Review']")).click();

			DownloadUtil.waitForDownload(fileName, TIMEOUT_IN_SECS);
		}
		return CommonUtilPage.verifyPDFDocuments(array);
	}

	public void deleteMod(String contractNumber, String modType) {
		log.info("Checking if mod exists for deletion");
		// //*[@aria-expanded='false']/*[@id='savedmods']
//		WebElement element = getRowByContractAndModType(contractNumber, modType, "savedmodstable");
//		if (element != null) {
//			log.info("Deleting saved emod");
////			ActionByLocator
////					.getElementInElement(driver, element, By.xpath(".//input[@value='Delete Mod']"), TIMEOUT_IN_SECS)
////					.click();
//			SeleniumHelper.clickNestElement_tempFix(driver,  element, By.xpath(".//input[@value='Delete Mod']"));
//			GenericDialogPage page = new GenericDialogPage(executionContext.getDriver());
//			page.clickConfirmYes();
//		}

		Boolean isDelete = true;
		System.out.println("******ModType : "+modType);
		System.out.println("******Contract : "+contractNumber);
		while (isDelete) {
			SeleniumHelper.clickAndContinueByLocator_tempFix(driver, By.xpath("//*[@id='savedmods']"));
			isDelete = SeleniumHelper.verifyElementExist(driver, By.xpath("//input[@value='Delete Mod']"));
			if (isDelete) {
				Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@value='Delete Mod']"));
				Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@value='Yes']"));
			}
		}

	}

	public WebElement getRowByContractAndModType(String contractNumber, String modType, String tableId) {
		WebElement modRow = null;
		// WebElement modsTable = ActionById.getElement(driver, tableId,
		// TIMEOUT_IN_SECS);
		WebElement modsTable = SeleniumHelper.findElem_tempFix_public(driver, By.id(tableId));
		modRow = TableElementUtils.getTableRowByCellValues(modsTable, "Mod Actions\nClick to sort column", modType,
				"Contract\nClick to sort column", contractNumber);
		if (modRow == null) {
			while (ActionByXpath.isDisplayed(driver, nextLink, TIMEOUT_IN_SECS)) { // search in other pages
				Octo_SeleniumLibrary.clickElement(driver, By.xpath( nextLink));
//				modsTable = ActionById.getElement(driver, tableId, TIMEOUT_IN_SECS);
				modsTable = SeleniumHelper.findElem_tempFix_public(driver, By.id(tableId));
				modRow = TableElementUtils.getTableRowByCellValues(modsTable, "Mod Actions\nClick to sort column",
						modType, "Contract\nClick to sort column", contractNumber);
				if (modRow != null)
					break;
			}
		}
		return modRow;
	}

	public void navigateToModTypePage() {
//		String contract = executionContext.getCurrentScenarioObj().get("contractNumber").getAsString();
		String contract = GetUEIandContract.getContractNumberByFilter(executionContext.getCurrentScenarioObj().get("contractNumber").getAsString());
		log.info("Create mod for contract number " + contract);
		ActionByName.selectByText(driver, "contractNumber", contract, TIMEOUT_IN_SECS);
		Octo_SeleniumLibrary.clickElement(driver, By.name( "submitOnline"));
		SelectModTypesPage modTypePage = new SelectModTypesPage(executionContext);
		modTypePage.get();
	}

	public void verifyModIsNotPresent(String modType) {
		List<WebElement> tableRows = driver
				.findElements(By.xpath("//table[@class='table table-striped table-bordered modlist']/tbody/tr"));
		for (int i = 2; i <= tableRows.size(); i++) {
			List<WebElement> mods = driver.findElements(By.xpath(
					"//table[@class='table table-striped table-bordered modlist']/tbody/tr[" + i + "]/td[2]/div"));
			for (int j = 1; j <= mods.size(); j++) {
				String modName = driver
						.findElement(By.xpath("//table[@class='table table-striped table-bordered modlist']/tbody/tr["
								+ i + "]/td[2]/div[" + j + "]"))
						.getText().toString();
				Assert.assertFalse(modName.equalsIgnoreCase(modType));
				// log.info(modName + ": is not: " + modType);
			}
		}

	}
}
