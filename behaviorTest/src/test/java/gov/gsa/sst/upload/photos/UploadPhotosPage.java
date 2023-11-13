package gov.gsa.sst.upload.photos;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
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
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.DownloadUtil;
import gov.gsa.sst.util.LoadProperties;
import gov.gsa.sst.util.exceptions.TestDataException;
import util.ActionByTagName;
import util.ActionByXpath;
import util.TableElementUtils;

public class UploadPhotosPage extends Page {

	private final int TIMEOUT_IN_SECS = 3;
	private By headerText = By.xpath("//h1[contains(text(), 'UPLOAD PHOTOS')]");
	private static Logger log = LoggerFactory.getLogger(UploadPhotosPage.class);
	private ExecutionContext executionContext;


	public UploadPhotosPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {
		return CommonUtilPage.verifyPageHeader(executionContext, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading Upload Photos page");
		try {
			if( CommonUtilPage.isOffer(executionContext) ){
				boolean flag = CommonUtilPage.isSolicitationApt(executionContext);
				if (!flag)
					throw new Exception("The solicitation number specified in data does not match the one on UI");
			}
			// Need to use driver as ActionBy* calls do not throw exceptions
			LeftNavigationMenu.navigateTo(driver, "Upload Photos");
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();

			LeftNavigationMenu.navigateTo(driver, "Upload Photos");
		}
		try {
			executionContext.testPageFor508("Upload Photos");
		}catch (Exception ex) {
			log.warn("Section 508 exception: " +ex.getMessage());
		}
		Assert.assertTrue("Upload Photos page is not loaded", isLoaded());
	}

	/**
	 * Upload photo files
	 * @param array
	 * @throws IOException 
	 */
	public void populateForm(JsonArray array) throws IOException {
		List<String> fileNames = new ArrayList<String>();
		array.forEach((feature) -> {
			JsonObject jsonObj = feature.getAsJsonObject();
			Set<Entry<String, JsonElement>> qcSet = jsonObj.entrySet();
	        Iterator<Entry<String, JsonElement>> iter = qcSet.iterator();
	        while (iter.hasNext()) {
	            Entry<String, JsonElement> basicInfoElement = iter.next();
	            String elementName = basicInfoElement.getKey();
	            JsonElement elementValue = basicInfoElement.getValue();
	            if(elementName.contains("photo") ){
	            	//TODO refactor populateForm check each array item
	            	if(jsonObj.has("zip")){
	            		fileNames.add(elementValue.getAsString());
	            	} else{
		            	try {
		    				uploadFile(elementValue.getAsString());
		    			} catch (MalformedURLException e) {
		    				throw new TestDataException("Exception uploading photos file:  " + e.getMessage());
		    			}    	
	            	}
	            }
	        }	
		});
		//Generate Zip file and upload it 
		if(!fileNames.isEmpty()){
			File downloadDir = DownloadUtil.setUpDownloadLocation();
			File zipFile = DownloadUtil.zipFile(fileNames, downloadDir);
			URL dataFileURL = zipFile.toURI().toURL();
			uploadFile(dataFileURL, zipFile.getName());
			
		}
	}

	/**
	 * Click validate photos button 
	 */
	public void validatePhotos(){
		Octo_SeleniumLibrary.clickElement(driver, By.name( "validatePhotosBtn"));
	}
	
	/**
	 * Specify the file to upload
	 * @param fileName
	 * @throws MalformedURLException
	 */
	public void uploadFile(String fileName) throws MalformedURLException{
		URL dataFileURL = this.getClass().getResource("/data/" + fileName);
		log.info(" Upload file: " + dataFileURL.toString());
		uploadFile(dataFileURL, fileName);
	}

	/**
	 * Specify the file to upload along with the file location
	 * @param dataFileURL
	 * @param fileName
	 * @throws MalformedURLException
	 */
	public void uploadFile(URL dataFileURL, String fileName) throws MalformedURLException{
		String browser = LoadProperties.getProperty("driver.browser");
		String location = LoadProperties.getProperty("driver.location");
		
		File file = new File(dataFileURL.getFile());
		if(browser.equalsIgnoreCase("chrome")) {
			ActionByXpath.sendKeys(driver, "//input[@type='file']", file.getAbsolutePath(), TIMEOUT_IN_SECS);
		} else if (browser.equalsIgnoreCase("firefox")) {
			if (location.equalsIgnoreCase("remote")) {
				ActionByXpath.type(driver, "//input[@type='file']", dataFileURL.getPath(), TIMEOUT_IN_SECS);
			} else if (location.equalsIgnoreCase("local")) {
				ActionByXpath.sendKeys(driver, "//input[@type='file']", file.getAbsolutePath(), TIMEOUT_IN_SECS);
			}
		}
		Octo_SeleniumLibrary.clickElement(driver, By.xpath(
				"//input[@name='uploadTemplate' and @value='Upload File']"));
	}

	
	public void verifyPhotos(JsonArray array) {
		Iterator<JsonElement> iterator = array.iterator();
		while (iterator.hasNext()) {
			JsonObject jsonObj = iterator.next().getAsJsonObject();
			String fileName = jsonObj.get("filename").getAsString();
			Assert.assertNotNull("Could not find file with name: " + fileName, getRowByName(fileName));
		}
	}
	
	/**
	 * Check whether or not an array of photo names is uploaded.
	 * 
	 * @param photoArray photo filenames (e.g. 'photo1.jpg')
	 * @return map with filenames and 'upload' status 
	 */
	public Map<String, Boolean> arePhotosUploaded(JsonArray photoArray) {
		Map<String, Boolean> resultMap = new HashMap<>();
		Iterator<JsonElement> iterator = photoArray.iterator();
		while (iterator.hasNext()) {
			String photoName = iterator.next().getAsString();
			WebElement row = getRowByName(photoName);
			boolean isUploaded = (row != null ? true : false);		
			resultMap.put(photoName, isUploaded);
			log.info("Photo named '" + photoName + "' uploaded? " + isUploaded);		
		}
		return resultMap;
	}

	/**
	 * Return row from 'PHOTOS' table.
	 * 
	 * @param input name of photo
	 * @return a WebElement representing row or null if no row found for given photo name
	 */
	public WebElement getRowByName(String input) {
		WebElement row = null;
		try {
			WebElement div = ActionByXpath.getElement(driver, "//div[@id='table2']", TIMEOUT_IN_SECS);
			WebElement table = ActionByTagName.getElementInElement(driver, div, "table", TIMEOUT_IN_SECS);
			row = TableElementUtils.getTableRowByCellValue(table, "File Name", input);
		} catch (TimeoutException e) {
			log.info("'PHOTOS' table was not displayed on page");
		}
		return row;
	}

	/**
	 * Delete all photos in photo array
	 * 
	 * @param photoArray
	 */
	public void deletePhotos(JsonArray photoArray) {
		photoArray.forEach(photo -> {
			String photoName = photo.getAsString();
			WebElement row = getRowByName(photoName);
			row.findElement(By.xpath(".//a[text()='Delete']")).click();			
		});
	}
}
