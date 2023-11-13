package gov.gsa.sst.solicitationprovision;

import java.net.MalformedURLException;
import java.net.URL;
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
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import gov.gsa.sst.commonpage.SeleniumHelper;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.upload.UploadDocumentsPage;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByWebElement;
import util.TableElementUtils;

/**
 * This page is a sub page of Solicitation Provision also called as Technical
 * Proposal
 *
 * @author amulay
 *
 */
public class PastPerformancePage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(PastPerformancePage.class);

	private By headerText = By.xpath("//h1[contains(text(),'Technical Proposal: Past Performance')]");
	private By saveAndContinueButton = By.xpath("//input[contains(@value,'Save and Continue')]");
	private ExecutionContext executionContext;

	public PastPerformancePage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading Past Performance page");
		try {
			// Need to use driver as ActionBy* calls do not throw exceptions
			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
			solPage.selectProvisionAction("Past Performance");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Solicitation Provisions");
			SolicitationProvisionPage solPage = new SolicitationProvisionPage(executionContext);
			solPage.selectProvisionAction("Past Performance");
		}
		try {
			executionContext.testPageFor508("Past Performance");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Past Performance page is not loaded", isLoaded());
	}

	public void setEvaluationDate(String month, String day, String year) {
		ActionByName.selectByValue(driver, "StartDateMonth", month, TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "StartDateDay", day, TIMEOUT_IN_SECS);
		ActionByName.selectByValue(driver, "StartDateYear", year, TIMEOUT_IN_SECS);
	}

	public URL getDataUrl(String fileName) {
		if (fileName == null) {
			throw new RuntimeException("Filename cannot be null");
		}

		URL dataFileURL = this.getClass().getResource("/data/" + fileName);

		if (dataFileURL == null) {
			throw new RuntimeException("The file '" + fileName + "' does not exist!");
		} else
			return dataFileURL;
	}

	public void uploadDocument(String typeName, String fileName, String docName) throws MalformedURLException {
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("pastPerformance");
		//Octo_SeleniumLibrary.clickElement(driver, By.name( "confirmPastPerformaceDelete", TIMEOUT_IN_SECS);
		selectTechProp(jsonObj);
		responseChangesQuestionExit();
		SeleniumHelper.scrollDown(driver, 400);
		WebElement row = TableElementUtils
				.getTableRowByCellValue(ActionById.getElement(driver, "upTable", TIMEOUT_IN_SECS), "Type", typeName);
		WebElement uploadLink = TableElementUtils.getLinkElementFromTableRow(row, "Upload");
		uploadLink.click();
		uploadFile(fileName, docName, typeName);
	}

	public void uploadFile(String fileName, String documentName, String typeName) throws MalformedURLException {
		UploadDocumentsPage page = new UploadDocumentsPage(executionContext);
		page.uploadFile(fileName, documentName, typeName);
	}

	public void setFeedback(String feedback) {
		JsonObject jsonObj = executionContext.getCurrentScenarioObj().getAsJsonObject("pastPerformance");
		selectTechProp(jsonObj);
		if(ActionById.isDisplayed(executionContext.getDriver(), "provisionResponse_confirmPastPerformaceDelete", TIMEOUT_IN_SECS)) {
			SeleniumHelper.clickById_tempFix(driver, "provisionResponse_confirmPastPerformaceDelete", TIMEOUT_IN_SECS);
		}
		if(!CommonUtilPage.identifyEmod(executionContext.getCurrentScenarioObj())) {
		Octo_SeleniumLibrary.clickElement(driver, By.id( "form_input_1_1_1"));
		}
		ActionByName.clear(driver, "scpFSS001PastPerformanceNegativeFeedback", TIMEOUT_IN_SECS);
		ActionByName.sendKeys(driver, "scpFSS001PastPerformanceNegativeFeedback", feedback, TIMEOUT_IN_SECS);
	}

	public void submitForm() {
		Octo_SeleniumLibrary.clickElement(driver, saveAndContinueButton);
	}

	public String getEvaluationDate() {
		String date = "";
		String month = ActionByName.getText(driver, "StartDateMonth", TIMEOUT_IN_SECS);
		String day = ActionByName.getText(driver, "StartDateDay", TIMEOUT_IN_SECS);
		String year = ActionByName.getText(driver, "StartDateYear", TIMEOUT_IN_SECS);
		date = month + "/" + day + "/" + year;
		return date;
	}

	/**
	 * Selecting an uploaded document for view will download it to local system
	 *
	 * @param docName
	 */
	public void viewDocument(String docName) {
		By locator = By
				.xpath("//td[contains(.,'" + docName + "')]/following-sibling::td[position()=1]/a[text()='View']");
		Octo_SeleniumLibrary.clickElement(driver, locator);
	}

	public void deleteDocument(String docName) {
		By locator = By
				.xpath("//td[contains(.,'" + docName + "')]/following-sibling::td[position()=1]/a[text()='Delete']");
		Octo_SeleniumLibrary.clickElement(driver, locator);
		GenericDialogPage deletePage = new GenericDialogPage(driver);
		deletePage.clickConfirmYes();

	}

	public void deleteAllDocuments() {
		try {
			By locator = By.xpath("//a[text()='Delete']");
			List<WebElement> list = ActionByLocator.getElements(driver, locator, TIMEOUT_IN_SECS);
			for (int i = 0; i < list.size(); i++) {
				Octo_SeleniumLibrary.clickElement(driver, locator);
				GenericDialogPage deletePage = new GenericDialogPage(driver);
				deletePage.clickConfirmYes();
			}
		} catch (Exception e) {
			log.info("There are no documents to delete in Past Performance page");
		}
	}

	/**
	 * "pastPerformance": { "startDate": "01/01/2017", "orderDoc":
	 * "C://Users//amulay//Documents//orientation.txt", "expDoc":
	 * "C://Users//amulay//Documents//DP-Saurav.txt", "feedback": "fb" }
	 *
	 * @param jsonObj
	 * @throws MalformedURLException
	 */
	public void populateAllFields(JsonObject jsonObj) throws MalformedURLException {
		selectTechProp(jsonObj);
		boolean changeYes = false;
		changeYes = ActionById.isDisplayed(driver, "pastPerformanceYes", TIMEOUT_IN_SECS);
		if (changeYes) {
			Octo_SeleniumLibrary.clickElement(driver, By.id( "pastPerformanceYes"));
		}
		uploadSupportingDocuments(jsonObj);
		setFeedback(jsonObj.get("feedback").getAsString());

		String startDate = jsonObj.get("startDate").getAsString();
		String[] start = startDate.split("/");
		setEvaluationDate(start[0], start[1], start[2]);
	}

	public void selectTechProp(JsonObject jsonObj) {

		for (Map.Entry<String, JsonElement> element : jsonObj.entrySet()) {
			if(element.getKey().equals("supportingDocument")) {
				continue;
			}
			String elementName = element.getKey();
			String elementValue = element.getValue().getAsString();
			switch (elementName) {
			case "CPARS":
				if(elementValue.equalsIgnoreCase("yes")) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "form_input_1_1_1"));
				}else {
					Octo_SeleniumLibrary.clickElement(driver, By.id( "form_input_1_1_2"));
				}
				break;
			case "ORI":
				if(elementValue.equalsIgnoreCase("yes")) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "form_input_2_2_1"));
				if(ActionById.isDisplayed(driver, "provisionResponse_confirmPastPerformaceDelete", TIMEOUT_IN_SECS)) {
					Octo_SeleniumLibrary.clickElement(driver, By.id( "provisionResponse_confirmPastPerformaceDelete"));
				}
				}else {
					Octo_SeleniumLibrary.clickElement(driver, By.id( "form_input_2_2_2"));
				}
				break;
			case "ISSUEORI":
				if(elementValue.equalsIgnoreCase("no")) {
				Octo_SeleniumLibrary.clickElement(driver, By.id( "form_input_3_3_2"));
				}
			default: return;

			}
		}

	}

	public void uploadSupportingDocuments(JsonObject jsonObj) {

		JsonArray array = jsonObj.getAsJsonArray("supportingDocument");
		array.forEach(doc -> {
			String type = doc.getAsJsonObject().get("type").getAsString();
			String fileName = doc.getAsJsonObject().get("filename").getAsString();
			String docName = doc.getAsJsonObject().get("documentName").getAsString();
			String action = doc.getAsJsonObject().get("action").getAsString();
			switch (action) {
			case "upload":
				try {
					uploadDocument(type, fileName, docName);
				} catch (MalformedURLException e) {
					log.error(e.getMessage());
					e.printStackTrace();
				}
				break;
			case "delete":
				// row.findElement(By.xpath("./td[2]//a[contains(text(),'Delete')]")).click();
				// new GenericDialogPage(driver).clickConfirmYes();
				break;
			case "view":
				break;
			default:
				throw new RuntimeException("Action '" + action + "' not implemented");
			}
		});
	}

	public void populateForm(JsonObject jsonObj) throws MalformedURLException {
		if (jsonObj.has("supportingDocument")) {
			uploadSupportingDocuments(jsonObj);
		} else
			log.info("There are no supporting documents to be added as part of this scenario");

		setFeedback(jsonObj.get("feedback").getAsString());

		if (jsonObj.has("startDate")) {
			String startDate = jsonObj.get("startDate").getAsString();
			String[] start = startDate.split("/");
			setEvaluationDate(start[0], start[1], start[2]);
		}
		submitForm();
	}

	public void setFeedbackAndStartDate(JsonObject jsonObj) {
		setFeedback(jsonObj.get("feedback").getAsString());

		String startDate = jsonObj.get("startDate").getAsString();
		String[] start = startDate.split("/");
		setEvaluationDate(start[0], start[1], start[2]);
	}

	/**
	 * Verify the status of documents uploaded This method verifies
	 *
	 * @param rowName Can be Past Experience or Order form
	 * @param status  Uploaded, Not Uploaded
	 */
	public void verifyStatus(String rowName, String status) {
		By locator = By.xpath("//td[contains(.,'" + rowName + "')]/following-sibling::td[position()=1]");
		WebElement element = ActionByLocator.getElement(driver, locator, TIMEOUT_IN_SECS);
		Assert.assertTrue(ActionByWebElement.getElement(driver, element, TIMEOUT_IN_SECS).getText().contains(status));
	}

	/**
	 * This method verifies the document name and its status
	 *
	 * @param docName
	 * @param status  Uploaded, Not Uploaded
	 */
	public void verifyNameAndStatus(String docName, String status) {
		By locator = By.xpath("//li[contains(.,'" + docName + "')]/following::td[position()=2]");
		WebElement element = ActionByLocator.getElement(driver, locator, TIMEOUT_IN_SECS);
		Assert.assertTrue(ActionByWebElement.getElement(driver, element, TIMEOUT_IN_SECS).getText().contains(status));
	}

	public void responseChangesQuestionExit() {
		if(ActionById.isEnabled(driver, "pastPerformanceYes", TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, By.name( "confirmPastPerformaceDelete"));
		}else {
			log.info("Confirm response change question not show");
		}
	}

}
