package gov.gsa.sst.submit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import comment.ExecutionContext;
import comment.Page;

import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.commonpage.GenericDialogPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.DataUtil;
import gov.gsa.sst.util.DownloadUtil;
import util.ActionByLocator;
import util.ActionByTagName;
import util.ActionByXpath;
import util.TableElementUtils;

public class SubmitOfferPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(SubmitOfferPage.class);
	private By offerHeaderText = By.xpath("//h1[text()='FINAL REVIEW OF eOFFER']");
	private By modHeaderText = By.xpath("//h1[text()='FINAL REVIEW OF eMOD']");
	private ExecutionContext executionContext;

	public SubmitOfferPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		try {
			if (CommonUtilPage.isOffer(executionContext)) {
				log.info("Loading 'Submit eOffer' page");
				LeftNavigationMenu.navigateTo(driver, "Submit eOffer");
			} else {
				log.info("Loading 'Submit Request' page");
				LeftNavigationMenu.navigateTo(driver, "Submit Modification");
			}
			GenericDialogPage page = new GenericDialogPage(driver);
			page.clickConfirmYes();
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			if (CommonUtilPage.isOffer(executionContext)) {
				LeftNavigationMenu.navigateTo(driver, "Submit eOffer");
			} else {
				LeftNavigationMenu.navigateTo(driver, "Submit Modification");
			}
			GenericDialogPage page = new GenericDialogPage(driver);
			page.clickConfirmYes();
		}
		try {
			executionContext.testPageFor508("Submit Offer");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		//Submit eOffer
		Assert.assertTrue("'Confirm Submit' page is not loaded", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		if (ActionByLocator.isDisplayed(driver, offerHeaderText, TIMEOUT_IN_SECS))
			return CommonUtilPage.isSolicitationApt(executionContext);
		else if (ActionByLocator.isDisplayed(driver, modHeaderText, TIMEOUT_IN_SECS))
			return true;
		else
			return false;
	}

	/**
	 * Checks whether a document-type and document-name entry exists in the
	 * 'Final Review of eOffer' table.
	 *
	 * The expected format of the input JSON object is, by example:
	 *
	 * { "Offer Response": "Offer Response", "Form 1449": "Form 1449", ... }
	 *
	 * @param expectedUploadedDocs
	 *            JSON object described above
	 * @return map with document types and whether document name is displayed or
	 *         not
	 */
	public Map<String, Boolean> areDocumentsUploaded(JsonArray expectedUploadedDocs) {
		Map<String, Boolean> actualUploadedDocs = new HashMap<>();

		expectedUploadedDocs.forEach((uploadedDocumentObject) -> {
			JsonObject uploadedDocument = uploadedDocumentObject.getAsJsonObject();
			String documentType = uploadedDocument.get("documentType").getAsString();
			String documentName = uploadedDocument.get("documentName").getAsString();

			actualUploadedDocs.put(documentType, isDocumentUploaded(documentType, documentName));
		});

		return actualUploadedDocs;
	}

	/**
	 * Checks whether a document-type and document-name entry exists in the
	 * 'Final Review of eOffer' table.
	 *
	 * @param documentType
	 *            the 'Type' of document, e.g. 'Form 1449'
	 * @param documentName
	 *            the 'Name' of the document, e.g. 'exceptions.txt'
	 * @return true if the entry exists on table, false otherwise
	 */
	public boolean isDocumentUploaded(String documentType, String documentName) {
		boolean documentUploaded = false;
		WebElement row = getDocumentsTableRow(documentType);
		log.info("Looking for uploaded document: " + documentName);
		try {
			WebElement ele = row.findElement(By.xpath(".//td[contains(text(),'" + documentName + "')]"));
			if(ele != null)
				documentUploaded = true;
		} catch (NoSuchElementException e) {
			log.error("Document name " + documentName + " not found in table");
			WebElement ele = row.findElement(By.xpath(".//td/span[contains(text(),'" + documentName + "')]"));
			if(ele != null) {
				log.info("Document name " + documentName + " found in table with span link");
				documentUploaded = true;
			}
		}
		return documentUploaded;
	}

	private WebElement getDocumentsTableRow(String rowName) {
		WebElement div = ActionByLocator.getElement(driver, By.className("table2"), TIMEOUT_IN_SECS);
		WebElement table = ActionByTagName.getElementInElement(driver, div, "table", TIMEOUT_IN_SECS);
		return TableElementUtils.getTableRowByCellValue(table, "Type", rowName);
	}

	/**
	 * Checks whether a download link for the document-type exists in the 'Final
	 * Review of eOffer' table.
	 *
	 * The expected format of the input JSON object array is, by example:
	 *
	 * [{ "documentType": "Reps and Certs Response", "documentName": "Reps and
	 * Certs Response", "elementTitle": "Review orca response" }, ...]
	 *
	 * @param expectedUploadedDocs
	 *            JSON object array described above
	 * @return map with document types and download-link visibility
	 */
	public Map<String, Boolean> areDocumentsDownloadable(JsonArray expectedUploadedDocs) {
		Map<String, Boolean> downloadableDocs = new HashMap<>();

		expectedUploadedDocs.forEach((uploadedDocumentObject) -> {
			JsonObject uploadedDocument = uploadedDocumentObject.getAsJsonObject();
			String documentType = uploadedDocument.get("documentType").getAsString();
			String documentName = uploadedDocument.get("documentName").getAsString();
			String elementTitle = uploadedDocument.get("elementTitle").getAsString();

			boolean isLinkDisplayed = false;
			try {
				isLinkDisplayed = ActionByXpath.isDisplayed(driver, "//a[contains(@title,'" + elementTitle + "')]", TIMEOUT_IN_SECS);
			} catch (NoSuchElementException | ElementNotVisibleException e) {
				log.info("The download link for document name '" + documentName + "' was not found or isn't visible.");
			}
			downloadableDocs.put(documentType, isLinkDisplayed);
		});
		return downloadableDocs;
	}

	/**
	 * Download the documents in the 'Final Review of eOffer' table.
	 *
	 * The expected format of the input JSON object array is, by example:
	 *
	 * [{ "documentType": "Reps and Certs Response", "documentName": "Reps and
	 * Certs Response", "elementTitle": "Review orca response", "filename":
	 * "Orca.pdf" }, ...]
	 *
	 * @param expectedUploadedDocs
	 *            JSON object array described above
	 * @return map with document types and download-link visibility
	 */
	public Map<String, Boolean> downloadDocuments(JsonArray documentsToDownload) {
		Map<String, Boolean> downloadedDocs = new HashMap<>();
		for (JsonElement documentToDownload : documentsToDownload) {
			JsonObject document = documentToDownload.getAsJsonObject();
			String documentType = document.get("documentType").getAsString();
			String documentName = document.get("documentName").getAsString();
			String elementTitle = document.get("elementTitle").getAsString();
			String fileName = DataUtil.getFilename(document.get("filename").getAsString());

			if (fileName.isEmpty()) {
				log.info("Skipping file download for document type '" + documentType + "' - filename is zero-length.");
				continue;
			}

			log.info("Delete existing files with name: " + fileName);
			DownloadUtil.deleteFiles(fileName);
			Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//a[contains(@title,'" + elementTitle + "')]"));
			downloadedDocs.put(documentName, DownloadUtil.waitForDownload(fileName, TIMEOUT_IN_SECS));
		}
		return downloadedDocs;
	}

	/**
	 * JSON array of objects containing the 'name' attribute of an element and
	 * the expected value of the element 'value' attribute. The questions are
	 * expected to be radio-button type elements
	 *
	 * [{ "elementName": "orcaAcceptance", "elementValue": "Yes" }, {
	 * "elementName": "acceptance", "elementValue": "Yes" }]
	 *
	 * @param disclaimerQuestions
	 *            JSON array of objects as described above
	 */
	public void answerDisclaimerQuestions(JsonArray disclaimerQuestions) {
		if (disclaimerQuestions == null) {
			throw new RuntimeException("Data object for 'disclaimer questions' is undefined. Check your test data.");
		}
		disclaimerQuestions.forEach((disclaimerQuestion) -> {
			String elementName = disclaimerQuestion.getAsJsonObject().get("elementName").getAsString();
			String elementValue = disclaimerQuestion.getAsJsonObject().get("elementValue").getAsString();
			log.info("Radio button selection for name:'" + elementName + "' is '" + elementValue + "'.");
			CommonUtilPage.selectRadioOption(driver, elementName, elementValue);
		});
	}

	/**
	 * Clicks "Continue" button to submit offer
	 */
	public void clickContinue() {
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@name='acceptDisclaimerOffer' and @value = 'Continue']"));
	}
	
	/*
	 * Clicks "Submit" button to final submit offer
	 */
	
	public void clickFinalSubmit() {
		Octo_SeleniumLibrary.clickElement(driver, By.xpath("//input[@name='confirmSubmitOffer' and @value='Submit Offer']"));
	}
}
