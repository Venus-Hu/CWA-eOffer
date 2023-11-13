package gov.gsa.sst.upload;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
import gov.gsa.sst.util.DataUtil;
import gov.gsa.sst.util.DownloadUtil;
import gov.gsa.sst.util.LoadProperties;
import util.ActionByClassName;
import util.ActionByLocator;
import util.ActionByName;
import util.ActionByTagName;
import util.ActionByXpath;

public class UploadDocumentsPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private static Logger log = LoggerFactory.getLogger(UploadDocumentsPage.class);
	private By headerText = By.xpath("//h1[contains(text(), 'UPLOAD DOCUMENTS')]");
	private By tableColumnType = By.xpath("//div[@class='table2']//th[text()='Type']");

	private ExecutionContext executionContext;

	public UploadDocumentsPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected void load() {
		log.info("Loading Upload Documents page");
		try {
			if (CommonUtilPage.isOffer(executionContext)) {
				boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
				if (!flag)
					throw new Exception("The solicitation number specified in data does not match the one on UI");
			}
			LeftNavigationMenu.navigateTo(driver, "Upload Documents");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Upload Documents");
		}
		try {
			executionContext.testPageFor508("Upload Documents");
		} catch (Exception ex) {
			log.warn("Section 508 exception: " + ex.getMessage());
		}
		Assert.assertTrue("Upload Documents page is not loaded", isLoaded());
	}

	@Override
	protected boolean isLoaded() {
		if (CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS)
				&& ActionByLocator.isDisplayed(driver, tableColumnType, TIMEOUT_IN_SECS)) {
			return true;
		} else
			return false;
	}

	/**
	 * Performs action on document described by a JSON array with the following
	 * format:
	 * 
	 * [{ "type": "Order Form for Past Performance Eval", "action": "upload",
	 * "filename": "exampleDir/exampleFile.xls" }, ...]
	 * 
	 * Valid actions are: 'upload', 'view', and 'delete'
	 * 
	 * Filename is relative to data folder. In the example above, there should be an
	 * exampleFile.xls inside exampleDir in the src/test/data directory.
	 * 
	 * @param array
	 * @throws MalformedURLException
	 */
	public void documentAction(JsonArray array) throws MalformedURLException {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonObject jsonObj = iterator.next().getAsJsonObject();

			String typeName = getProperty(jsonObj, "type");
			String fileName = getProperty(jsonObj, "filename");
			String action = getProperty(jsonObj, "action");
			String documentName = getProperty(jsonObj, "documentName");

			WebElement row = getDocumentsTableRow(typeName);

			switch (action) {
			case "upload":
//				row.findElement(By.xpath(".//td/a[contains(.,'Upload')]")).click();
				SeleniumHelper.clickNestElement_tempFix(executionContext.getDriver(),row.findElement(By.xpath(".//td/a[contains(.,'Upload')]")));
				uploadFile(fileName, documentName, typeName);
				break;
			case "delete":
				// Check if there are multiple files with same name
				List<WebElement> listOfRows = getListOfTableRows(typeName);
				for (int i = 0; i < listOfRows.size(); i++) {
					// call the find row again otherwise we get stale element exception
					row = getDocumentsTableRow(typeName);
					row.findElement(By.xpath(".//td/div/table/tbody/tr/td/a[contains(.,'Delete')]")).click();
					new GenericDialogPage(driver).clickConfirmYes();
					log.info("Deleting file " + typeName + " in row: " + i);
				}
				break;
			case "view":
				viewFile(fileName, row);
				break;
			case "download":
				downloadFile(fileName, row);
				break;
			default:
				throw new RuntimeException("Action '" + action + "' not implemented");
			}

		}
	}

	private void viewFile(String filePath, WebElement viewElement) {
		String fileName = DataUtil.getFilename(filePath);
		log.info("Remove from download directory any existing files with name: " + fileName);

		DownloadUtil.deleteFiles(fileName);
		viewElement.findElement(By.xpath(".//td/div/table/tbody/tr/td/a[contains(.,'View')]")).click();
		DownloadUtil.waitForDownload(fileName, TIMEOUT_IN_SECS);
	}

	private void downloadFile(String filePath, WebElement viewElement) {
		String fileName = DataUtil.getFilename(filePath);
		log.info("Remove from download directory any existing files with name: " + fileName);

		DownloadUtil.deleteFiles(fileName);
		viewElement.findElement(By.xpath("./td[1]//a[contains(text(),'Download')]")).click();
		DownloadUtil.waitForDownload(fileName, TIMEOUT_IN_SECS);
	}

	private WebElement getDocumentsTableRow(String typeName) {
		WebElement div = ActionByClassName.getElement(driver, "table2", TIMEOUT_IN_SECS);
		WebElement table = ActionByTagName.getElementInElement(driver, div, "table", TIMEOUT_IN_SECS);
		return getTableRowByTypeName(table, typeName);
	}

	private List<WebElement> getListOfTableRows(String typeName) {
		WebElement div = ActionByClassName.getElement(driver, "table2", TIMEOUT_IN_SECS);
		WebElement table = ActionByTagName.getElementInElement(driver, div, "table", TIMEOUT_IN_SECS);
		return table.findElements(By.xpath(".//tbody/tr/td/div//td[contains(.,'" + typeName + "')]/parent::tr"));
	}

	private WebElement getTableRowByTypeName(WebElement table, String typeName) {
		return table.findElement(By.xpath(".//tbody/tr/td[contains(.,'" + typeName + "')]/parent::tr"));
	}

	/**
	 * Uploads a file found in src/test/resources/data folder. The file can be
	 * inside another directory. In that case, you specify the directory with the
	 * file name. See example below.
	 * 
	 * @param fileName
	 * @param documentName
	 * @throws MalformedURLException
	 */
	public void uploadFile(String fileName, String documentName, String typeName) throws MalformedURLException {

		URL dataFileURL = this.getClass().getResource("/data/" + fileName);
		if (dataFileURL == null) {
			throw new RuntimeException("The resource data/" + fileName + " was not found! Check your test data files.");
		}
		String filePath = dataFileURL.getFile();
		log.info(" Upload file: " + filePath);
		String browser = LoadProperties.getProperty("driver.browser");
		String location = LoadProperties.getProperty("driver.location");

		// Do NOT replace driver.find** with ActionBy as the file element is not
		// visible.
		File file = new File(filePath);
		if (browser.equalsIgnoreCase("chrome")) {
			if (!fileName.isEmpty())
				driver.findElement(By.xpath("//input[@id='uploadDoc']")).sendKeys(file.getAbsolutePath());
		} else if (browser.equalsIgnoreCase("firefox")) {
			if (location.equalsIgnoreCase("remote")) {
				ActionByXpath.type(driver, "//input[@id='uploadDoc']", filePath, TIMEOUT_IN_SECS);
			} else if (location.equalsIgnoreCase("local")) {
				driver.findElement(By.xpath("//input[@id='uploadDoc']")).sendKeys(file.getAbsolutePath());
			}
		}
		if (typeName.equalsIgnoreCase("Other (optional-offeror defined)")) {
			ActionByName.selectByText(driver, "documentCategory", "Other", TIMEOUT_IN_SECS);
			Octo_SeleniumLibrary.clickElement(driver, By.xpath(
					"//input[contains(@name,'addAttachment') and @value='Upload This Document' and not(@type='hidden')]"));
//			ActionByXpath.click(driver,
//					"//input[contains(@name,'addAttachment') and @value='Upload This Document' and @type='submit']",
//					TIMEOUT_IN_SECS);
			log.info("Uploading file " + fileName);
		} else {
			ActionByName.sendKeys(driver, "uploadName", documentName, TIMEOUT_IN_SECS);
//			ActionByXpath.click(driver,
//					"//input[contains(@name,'addAttachment') and @value='Upload This Document' and @type='submit']",
//					TIMEOUT_IN_SECS);
			Octo_SeleniumLibrary.clickElement(driver, By.xpath(
					"//input[contains(@name,'addAttachment') and @value='Upload This Document' and not(@type='hidden')]"));
			log.info("Uploading file " + fileName);
		}
	}

	/**
	 * Returns a map of document names and whether or not they are uploaded for the
	 * given array of document names. The input document array example format is
	 * below:
	 * 
	 * * [{ "type": "Order Form for Past Performance Eval", "filename":
	 * "exampleDir/exampleFile.xls", "documentName": "Order Form for Past
	 * Performance Eval" }, ...]
	 * 
	 * @param documentArray array of document names as explained above
	 * @return
	 */
	public Map<String, Boolean> areDocumentsUploaded(JsonArray documentArray) {
		Map<String, Boolean> resultMap = new HashMap<>();

		Iterator<JsonElement> iterator = documentArray.iterator();
		while (iterator.hasNext()) {
			JsonObject jsonObj = iterator.next().getAsJsonObject();

			String documentType = getProperty(jsonObj, "type");
			String documentName = getProperty(jsonObj, "documentName");

			boolean documentUploaded = isDocumentUploaded(documentName, documentType);
			log.info("Document named '" + documentName + "' uploaded? " + documentUploaded);

			boolean documentStatus = getDocumentStatus(documentType).equals("Uploaded");
			log.info("Document named '" + documentName + "' status == 'Uploaded'? " + documentStatus);

			resultMap.put(documentName, (documentUploaded && documentStatus));
		}

		return resultMap;
	}

	private String getProperty(JsonObject jsonObj, String propertyName) {
		String property;
		try {
			property = jsonObj.get(propertyName).getAsString();
		} catch (Exception e) {
			throw new RuntimeException("The property '" + propertyName + "' is not defined in the test data");
		}
		return property;
	}

	/**
	 * Whether or not a document is listed on Documents table. If multiple
	 * 
	 * @param documentName name of document, e.g. "exampleFile.xls"
	 * @param documentType e.g. "Order Form for Past Performance Eval"
	 * @return
	 */
	public boolean isDocumentUploaded(String documentName, String documentType) {
		boolean documentUploaded = false;
		WebElement row = getDocumentsTableRow(documentType);
		try {

			if (!CommonUtilPage.identifyEmod(executionContext.getCurrentScenarioObj())) {
				WebElement element = row.findElement(
						By.xpath(".//td[3]/div/table/tbody/tr/td[contains(text(),'" + documentName + "')]"));
				if (element != null)
					documentUploaded = true;
			} else {
				WebElement element = row.findElement(
						By.xpath(".//td[2]/div/table/tbody/tr/td[contains(text(),'" + documentName + "')]"));
				if (element != null)
					documentUploaded = true;
			}
		} catch (NoSuchElementException e) {
			log.info("Document name " + documentName + " not found in table");
		}
		return documentUploaded;
	}

	private String getDocumentStatus(String documentType) {
		if (!CommonUtilPage.identifyEmod(executionContext.getCurrentScenarioObj())) {
			WebElement row = getDocumentsTableRow(documentType);
			return row.findElement(By.xpath(".//td[4]")).getText().trim();
		} else {
			WebElement row = getDocumentsTableRow(documentType);
			return row.findElement(By.xpath(".//td[3]")).getText().trim();
		}
	}

	/**
	 * Use this method to attempt upload of a document without specifying the
	 * document location to test expected error messages. Expected object format
	 * should include a "type" field i.e.
	 * 
	 * "uploadDocuments": { "type": "Order Form for Past Performance Eval" }
	 * 
	 * @param documentObject
	 */
	public void uploadWithoutDocument(JsonObject documentObject) {
		String documentType = getProperty(documentObject, "type");
		getDocumentsTableRow(documentType).findElement(By.xpath("./td[4]/a")).click();
		Octo_SeleniumLibrary.clickElement(driver, By.xpath( "//input[@name='addAttachmentAction' and @value='Upload This Document']"));
	}

	/**
	 * Returns a map of document names and whether or not they are downloaded for
	 * the given array of document names. The input document array example format is
	 * below:
	 * 
	 * * [{ "type": "Order Form for Past Performance Eval", "filename":
	 * "exampleDir/exampleFile.xls" }, ...]
	 *
	 * @param documentArray
	 * @return
	 */
	public Map<String, Boolean> areDocumentsDownloaded(JsonArray documentArray) {
		Map<String, Boolean> resultMap = new HashMap<>();

		Iterator<JsonElement> iterator = documentArray.iterator();
		while (iterator.hasNext()) {
			JsonObject jsonObj = iterator.next().getAsJsonObject();
			String fileName = DataUtil.getFilename(getProperty(jsonObj, "filename"));

			boolean documentUploaded = isDocumentDownloaded(fileName);
			log.info("filename '" + fileName + "' downloaded? " + documentUploaded);

			resultMap.put(fileName, documentUploaded);
		}

		return resultMap;
	}

	private boolean isDocumentDownloaded(String fileName) {
		return DownloadUtil.isFileDownloaded(fileName);
	}

	/**
	 * Get documents on the Document table for each type.
	 * 
	 * @param documentTypes the desired document type e.g. "Order Form for Past
	 *                      Performance Eval" or "Past Performance & Experience"
	 * 
	 * @return for each document type, a list of document names
	 */
	public Map<String, List<String>> getUploadedDocuments(JsonArray documentTypes) {
		Map<String, List<String>> uploadedDocuments = new HashMap<>();

		documentTypes.forEach((documentType) -> {
			WebElement typeRow = getDocumentsTableRow(documentType.getAsString());
			List<WebElement> documents = typeRow.findElements(By.xpath(".//tr/td"));

			List<String> documentNames = new ArrayList<>();
			documents.forEach(document -> documentNames.add(document.getText().trim()));

			uploadedDocuments.put(documentType.getAsString(), documentNames);
		});

		return uploadedDocuments;
	}

	public boolean isDocumentMandatory(String documentType) {
		WebElement row = getDocumentsTableRow(documentType);
		if (row != null) {
			String text = row.findElement(By.xpath("./td[1]")).getText().trim();
			log.info("Row contains text: " + text);
			return text.contains("*");
		}
		return false;
	}
}
