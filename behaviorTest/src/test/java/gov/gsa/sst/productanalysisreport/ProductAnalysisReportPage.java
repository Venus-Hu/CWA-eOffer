package gov.gsa.sst.productanalysisreport;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.capturepricing.dataentry.FormEntryUtil;
import gov.gsa.sst.capturepricing.downloaduploadtemplate.DownloadUploadTemplateUtil;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.DownloadUtil;
import gov.gsa.sst.util.HttpDownloadUtility;
import gov.gsa.sst.util.LoadProperties;
import gov.gsa.sst.util.WebDriverUtil;
import gov.gsa.sst.util.exceptions.TestExecutionException;
import util.ActionByCss;
import util.ActionById;
import util.ActionByLocator;
import util.ActionByXpath;
import util.TableElementUtils;

public class ProductAnalysisReportPage extends Page {
	private static Logger log = LoggerFactory.getLogger(ProductAnalysisReportPage.class);
	private static final long PAR_GENERATION_TIMEOUT_SECONDS = 13 * 60;
	private static final long PAR_GENERATION_POLLING_PERIOD_SECONDS = 60;
	private static final long POLLING_PERIOD_SECONDS = 30;
	private static final long TIMEOUT_FOR_UPLOAD_DOCUMENT = 4 * 60;
	private By refreshBtn = By.xpath("//input[(@name='CheckStatus') and not(@disabled)]");
	private static By CHECK_DOWNLOAD_STATUS = By.name("CheckDownloadStatus");
	private static By FLAGGED_ITEMS_DOWNLOAD_TEMPLATE = By.xpath("//a[starts-with(.,'View')]");
	private ExecutionContext executionContext;
	private By headerText = By.xpath("//h1[text()='PRODUCT ANALYSIS REPORT (PAR)']");
	private String regenetePAR = "//button[contains(text(), 'Regenerate PAR')]";
	private String saveChanges = "//button[contains(text(), 'Save Changes')]";
	private String proceedToDownloadUpload = "btnToDownloadTemplate";
	private String downloadBtn = "downloadButton";
	private String goToPARBtn = "viewOfferAnalysisReport";
	private static final int TIMEOUT_IN_SECS = 10;
	private String templateName = "";
	private static String docName = "";

	public ProductAnalysisReportPage(WebDriver driver) {
		super(driver);
	}

	public ProductAnalysisReportPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading 'Product Analysis Report' page");
		try {
			LeftNavigationMenu.navigateTo(driver, "Product Analysis Report", TIMEOUT_IN_SECS);
			log.info("'Product Analysis Report' page is loaded");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Product Analysis Report", TIMEOUT_IN_SECS);
		}
		try {
			executionContext.testPageFor508("PAR");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("'Product Analysis Report' page is not loaded", isLoaded());
	}

	/**
	 * Click the "Validate Product Data" button
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public void validateProductData() throws MalformedURLException, IOException, InterruptedException {
		if (executionContext.getCurrentScenarioObj().has("productAnalysisReport")) {
			// red PAR
			if (executionContext.getCurrentScenarioObj().getAsJsonObject("productAnalysisReport")
					.has("parOnscreenConfirm")) {
				completeRedPAROnScreen();
			} else if (executionContext.getCurrentScenarioObj().getAsJsonObject("productAnalysisReport")
					.has("parFileUploadConfirm"))
				completeRedPARFileUpload();
		}
		log.info("Validating PAR Product data");
		Octo_SeleniumLibrary.clickElement(driver, By.id("btnValidateProductData"));
	}

	/**
	 * Click the 'Generate Offer Analysis Report' button
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void generatePAR() throws IOException {
		Octo_SeleniumLibrary.clickElement(driver, By.id("generateOfferAnalysisReport"));
		// DO NOT DELETE -Added mock xsb calls for now
		/**
		 * Add this data to file for mock xsb call to work "manufacturePartNumber":
		 * "234566", "manufactureName": "DELL COMP",
		 */
		// XSBUtil.executeGraph(executionContext,
		// CommonUtilPage.isOffer(executionContext),
		// "MockXsbResponseWithNoPricingChange.xml");
	}

	/**
	 * Monitors PAR generation completion (e.g. 'Check Status') until finished.
	 * 
	 * @return true if 'Validate Product Data' button displays
	 */
	public boolean waitForPARCompletion() {
		// Wait for 10 seconds and check for status
		// return
		// XSBUtil.checkOfferAnalysisReportGenerationCompletionStatus(executionContext,
		// 10000);

		if (LoadProperties.getProperty("environment").equalsIgnoreCase("cloud"))
			return WebDriverUtil.wait(driver, By.id("btnToCheckOfferAnalysisReport"), By.id("btnToDownloadTemplate"),
					PAR_GENERATION_TIMEOUT_SECONDS, PAR_GENERATION_POLLING_PERIOD_SECONDS, TimeUnit.SECONDS);
		else { // increase time for document to upload to 15min in GSA test env
			return WebDriverUtil.wait(driver, By.id("btnToCheckOfferAnalysisReport"), By.id("btnToDownloadTemplate"),
					900, PAR_GENERATION_POLLING_PERIOD_SECONDS, TimeUnit.SECONDS);
		}

	}

	public String getAppMessage() {
		return ActionByXpath.getText(driver, "//ul[@class='appMessage']/li[1]", TIMEOUT_IN_SECS);
	}

	/**
	 * For the flagged products, we need to correct the standardization flags and
	 * regenerate PAR to complete the PAR process
	 * 
	 */
	public void completeRedPAROnScreen() {
		JsonArray sheetDataArray = executionContext.getCurrentScenarioObj().getAsJsonObject("productAnalysisReport")
				.getAsJsonObject("parOnscreenConfirm").getAsJsonObject("worksheetData").getAsJsonArray("default"); // first
																													// time
																													// red
																													// PAR
		correctStandardizationFlagsOnFormEntry(sheetDataArray);
		regeneratePAR();
		sheetDataArray = executionContext.getCurrentScenarioObj().getAsJsonObject("productAnalysisReport")
				.getAsJsonObject("parOnscreenConfirmAgain").getAsJsonObject("worksheetData").getAsJsonArray("default"); // second
																														// time
																														// red
																														// PAR
		correctStandardizationFlagsOnFormEntry(sheetDataArray);
		regeneratePAR();
	}

	/**
	 * Once the red PAR buttons comes up, we need correct the flagged product column
	 * values on form entry and save the changes
	 * 
	 * @param sheetDataArray
	 */
	public void correctStandardizationFlagsOnFormEntry(JsonArray sheetDataArray) {
		Octo_SeleniumLibrary.clickElement(driver, By.id("btnToEnterOnScreen"));

		FormEntryUtil.enterFormData(driver, "xsbStandardizationFlagHot", sheetDataArray, true);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Octo_SeleniumLibrary.clickElement(driver, By.xpath(saveChanges));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ActionByCss.clickOption(driver, ".ui-button.ui-widget", "OK", TIMEOUT_IN_SECS);
	}

	/**
	 * This method will regenerate the PAR after correcting the standardized flags &
	 * waits for it to complete
	 */
	public void regeneratePAR() {
		Octo_SeleniumLibrary.clickElement(driver, By.xpath(regenetePAR));
		waitForPARCompletion();
	}

	/**
	 * This method completes the flagged items process with a red PAR and corrects
	 * flags to get a blue PAR using file upload method
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void completeRedPARFileUpload() throws MalformedURLException, IOException, InterruptedException {
		File downloadDir = DownloadUtil.setUpDownloadLocation();
		JsonObject pageDataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("productAnalysisReport");
		JsonObject dutObj = executionContext.getCurrentScenarioObj().getAsJsonObject("productAnalysisReport");

		// first red PAR
		if (!downloadFlaggedItemsTemplate(downloadDir, dutObj)) {
			throw new TestExecutionException("Flagged items template could not be downloaded.");
		}
		JsonObject sheetDataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("productAnalysisReport")
				.getAsJsonObject("parFileUploadConfirm");
		downloadUpdateAndUpload(pageDataObj, sheetDataObj);

		// second red PAR
		if (executionContext.getCurrentScenarioObj().getAsJsonObject("productAnalysisReport")
				.has("parFileUploadConfirmAgain")) {
			if (!downloadFlaggedItemsTemplate(downloadDir, dutObj)) {
				throw new TestExecutionException("Flagged items template could not be downloaded.");
			}
			sheetDataObj = executionContext.getCurrentScenarioObj().getAsJsonObject("productAnalysisReport")
					.getAsJsonObject("parFileUploadConfirmAgain");
			downloadUpdateAndUpload(pageDataObj, sheetDataObj);
		} else
			Octo_SeleniumLibrary.clickElement(driver, By.id(goToPARBtn));
	}

	/**
	 * This method downloads the flagged items document template, update them with
	 * corrected standardized flags, upload the document and regenerate the PAR
	 * 
	 * @param pageDataObj
	 * @param sheetDataObj
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	public void downloadUpdateAndUpload(JsonObject pageDataObj, JsonObject sheetDataObj)
			throws IOException, MalformedURLException, InterruptedException {
		DownloadUploadTemplateUtil.updatePricingDocument(executionContext, sheetDataObj, pageDataObj);
		uploadDocAndWait();
		if (ActionById.isDisplayed(driver, "regenPAR", TIMEOUT_IN_SECS)) {
			Octo_SeleniumLibrary.clickElement(driver, By.id("regenPAR"));
			waitForPARCompletion();
		}
	}

	/**
	 * This method downloads the flagged items report download template
	 * 
	 * @param docPricingFileName
	 * @param downloadDir
	 * @param dutObj
	 * @param docName
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public boolean downloadFlaggedItemsTemplate(File downloadDir, JsonObject dutObj)
			throws IOException, MalformedURLException {

		this.templateName = dutObj.get("pricingDocumentTemplateName").getAsString();

		DownloadUtil.deleteFiles(templateName, downloadDir);
		if (templateName.contains("zip")) {
			docName = dutObj.get("pricingDocumentFileName").getAsString();
			DownloadUtil.deleteFiles(FilenameUtils.getBaseName(docName), downloadDir);
			DownloadUtil.deleteFiles(FilenameUtils.getBaseName(templateName), downloadDir);
		}

		Octo_SeleniumLibrary.clickElement(driver, By.id(proceedToDownloadUpload));
		Octo_SeleniumLibrary.clickElement(driver, By.name(downloadBtn));
		if (ActionByLocator.isDisplayed(driver, CHECK_DOWNLOAD_STATUS, TIMEOUT_IN_SECS)) {
			WebDriverUtil.wait(driver, CHECK_DOWNLOAD_STATUS, FLAGGED_ITEMS_DOWNLOAD_TEMPLATE, 30, 3, TimeUnit.SECONDS);
		}

		if (ActionByLocator.isDisplayed(driver, FLAGGED_ITEMS_DOWNLOAD_TEMPLATE, 20)) {
			if (LoadProperties.getProperty("driver.location").toLowerCase().trim().contains("remote")) {
				log.info("Downloading '" + templateName + "' through HTTP download util");
				HttpDownloadUtility.downloadFile(driver,
						"uploadTemplateAction.do?method=retrieveDownloadedProductCatalogTemplate", downloadDir);
			} else
				Octo_SeleniumLibrary.clickElement(driver, FLAGGED_ITEMS_DOWNLOAD_TEMPLATE);
		}

		boolean isFileDownloaded = DownloadUtil.waitForDownload(templateName, downloadDir, 25);

		if (isFileDownloaded && templateName.contains("zip")) {
			log.info("Unzipping the flagged items template file");
			DownloadUtil.unZipFile(templateName, downloadDir);
			log.info("document name for updating the flagged items info is " + docName);
		}
		return isFileDownloaded;
	}

	/**
	 * Upload the updated pricing document and wait for process to complete the data
	 * 
	 * @throws MalformedURLException
	 * @throws InterruptedException
	 */
	public void uploadDocAndWait() throws MalformedURLException, InterruptedException {
		DownloadUploadTemplateUtil.uploadPricingDocument(driver, docName);
		if (waitForDocumentToUpload(driver)) {
			if (ActionByXpath.isDisplayed(driver, "//span[@class='btn btn-danger']", TIMEOUT_IN_SECS)) {
				log.error("Corrected flagged items document upload failed. Please refer to DataErrors document.");
				return;
			}
			log.info("Upload corrected flagged items document document successful");
		} else
			log.error("Corrected flagged items document upload failed.");
	}

	/**
	 * Wait for pricing document to upload completely
	 * 
	 * @return
	 */

	public boolean waitForDocumentToUpload(WebDriver driver) throws InterruptedException {
		WebElement reportTable = ActionById.getElement(driver, "FileDetails", TIMEOUT_IN_SECS);
		int rowCount = TableElementUtils.getTableRowCount(reportTable);
		return WebDriverUtil.wait(driver, refreshBtn,
				By.xpath("//tr[" + (rowCount + 1) + "]//span[contains(@class,'btn btn-success')]"),
				TIMEOUT_FOR_UPLOAD_DOCUMENT, POLLING_PERIOD_SECONDS, TimeUnit.SECONDS);
	}
}
