package gov.gsa.sst.capturepricing.edi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import comment.ExecutionContext;
import comment.Page;
import customUtility.Octo_SeleniumLibrary;
import gov.gsa.sst.capturepricing.CaptureDataPage;
import gov.gsa.sst.commonpage.CommonUtilPage;
import gov.gsa.sst.corporateinfo.CorporateInformationPage;
import gov.gsa.sst.util.HttpURLConnectionSoap;
import gov.gsa.sst.util.LoadProperties;
import gov.gsa.sst.util.WebDriverUtil;
import gov.gsa.sst.util.exceptions.TestDataException;
import util.ActionByLocator;
import util.TableElementUtils;

public class EdiOptionPage extends Page {
	private final int TIMEOUT_IN_SECS = 10;	
	private By headerText = By.xpath("//h1[contains(text(), 'EDI')]");
	private ExecutionContext executionContext;
	private static Logger log = LoggerFactory.getLogger(EdiOptionPage.class);
	
    private By refreshBtn = By.xpath("//a[@title='Refresh Transaction Status']");
    private static final long TIMEOUT_FOR_KEY = 4*60;
	private static final long POLLING_PERIOD_SECONDS = 30;
	
	private static final By MODIFICATION_KEY_CONTRACT_COL = By.xpath("//table[@id='modificationKey']/tbody/tr/td[1]");
	private static final By MODIFICATION_KEY_MOD_COL = By.xpath("//table[@id='modificationKey']/tbody/tr/td[2]");	
	private static final By SOLICITATION_NUMBER_COL = By.xpath("//table[@id='offerKey']/tbody/tr/td[1]");
	private static final By REFRESH_NUMBER_COL = By.xpath("//table[@id='offerKey']/tbody/tr/td[2]");
	private static final By DUNS_NUMBER_COL = By.xpath("//table[@id='offerKey']/tbody/tr/td[3]");
	private static final By REFRESH_TRANSACTION_STATUS_LINK = By.xpath("//td/div/a[@title='Refresh Transaction Status']");
	
	
	private static Map<String, String> modTypeMap;
	static {
		// TODO Initialize from external file
		modTypeMap = new HashMap<>();
		modTypeMap.put("Product Catalog Update", "Product Catalog Update");
		modTypeMap.put("Add SIN", "Add Sin");
		modTypeMap.put("Add Product(s)", "Add Products");
		modTypeMap.put("Capture Formatted Pricing", "Capture Formatted Pricing");
		modTypeMap.put("Temporary Price Reduction", "Temporary Price Reduction");
		modTypeMap.put("Permanent Price Reduction (Industry Partner requested)", "Permanent Price Reduction (Based on Industry Partner Requested)");
		modTypeMap.put("Permanent Price Reduction (Based on Most Favored Customer)", "Permanent Price Reduction (Based on MFC)");
		modTypeMap.put("Economic Price Adjustments (EPA) with Commercial Price List (increase)", "Economic Price Adjustments (EPA) with Commercial Price List (increase)");
		modTypeMap.put("Economic Price Adjustments (EPA) without Commercial Price List (increase)", "Economic Price Adjustments (EPA) without Commercial Price List (increase)");
		modTypeMap.put("Manage Discounts", "Create/Manage Discounts");
	}
	
	public EdiOptionPage(WebDriver driver) {
		super(driver);
	}
	
	public EdiOptionPage(ExecutionContext executionContext) {
		super(executionContext.getDriver());
		this.executionContext = executionContext;
	}

	@Override
	protected boolean isLoaded() {		
		return ActionByLocator.isDisplayed(driver, headerText, TIMEOUT_IN_SECS);
	}

	@Override
	protected void load() {
		log.info("Loading EDI option page");
		try {
			if(CommonUtilPage.isOffer(executionContext)){
				boolean flag = CommonUtilPage.isSolicitationApt(executionContext);		
				if(!flag)
					throw new Exception("The solicitation number specified in data does not match the one on UI");
			}
			CaptureDataPage cpPage = new CaptureDataPage(executionContext);
			cpPage.get();
			cpPage.selectEdiOption();
		} catch (Exception e) {
			CorporateInformationPage corpInfoPage = new CorporateInformationPage(executionContext);
			corpInfoPage.get();
			
			CaptureDataPage cpPage = new CaptureDataPage(executionContext);
			cpPage.get();
			cpPage.selectEdiOption();
		}
		try {
			executionContext.testPageFor508("Capture pricing -EDI");
		}catch (Exception e) {
			log.warn("Section 508 exception: " +e.getMessage());
		}
		Assert.assertTrue("Capture Pricing EDI page is not loaded", isLoaded());
	}
	
	public void populateForm() throws InterruptedException, MalformedURLException, FileNotFoundException, IOException
	{
		boolean postToEDIsuccess = true;
		boolean transactionSuccess = true;
		
		generateKey();
		
		try {
			postToEDI();
		} catch (Exception e) {
			log.info(e.getMessage());
			postToEDIsuccess = false;
		}
		
		
		try {
			waitForEdiTransactionToComplete();
		} catch (RuntimeException e) {
			log.info(e.getMessage());
			transactionSuccess = false;
		}
		
		if (!postToEDIsuccess) {
			log.info("EDI message was not sent successfully");
		}
		
		if (!transactionSuccess) {
			log.info("EDI transaction not successful");
			getErrorFileFromTransaction();
		}
	}
	
	private String getErrorFileFromTransaction() throws MalformedURLException, FileNotFoundException, IOException {
		String errorResponse = "";
		log.info("Building transaction error message file");
		Map<String, String> numberMap = retrieveModificationKey();
		String transactionId =  getGeneratedTransactionId();
		String contractId = numberMap.get("contractNumber");
		String modId = numberMap.get("modNumber");
		StringBuilder soapRequest = new StringBuilder();
        soapRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://fas.gsa.gov/services/Transaction/v1.0\">");
        soapRequest.append("<soapenv:Header/>");
        soapRequest.append("<soapenv:Body>");
        soapRequest.append("<v1:getTransactionErrorFileRequest oid=\"1\">");
        soapRequest.append("<transactionId>" + transactionId + "</transactionId>"
	              	+ "<contractModKey>"
	              	+ "<contractNumber>" + contractId + "</contractNumber>"
	              	+ "<modificationNumber>" + modId + "</modificationNumber>"
	              	+ "</contractModKey>");
        soapRequest.append("</v1:getTransactionErrorFileRequest>");
        soapRequest.append("</soapenv:Body>");
        soapRequest.append("</soapenv:Envelope>");
        
        HttpURLConnectionSoap httpSoapConnection = new HttpURLConnectionSoap();
        String response = httpSoapConnection.createHttpURLConnectionAndMakeRequest(soapRequest.toString(),
				LoadProperties.getProperty("webservice.host"),
				LoadProperties.getProperty("transaction.webservice.endpoint"), false);
        
        if (!response.isEmpty()) {
        	String docString = response.substring(response.indexOf("<doc>"), response.indexOf("</doc>"));
            docString = docString.replaceFirst("<doc>", "");
            byte[] decodedData = Base64.decodeBase64(docString);
            errorResponse = new String(decodedData, "UTF-8");
        } else {
        	log.info("Server response is empty");
        }
        log.info("Error response: " + errorResponse);
        
        return errorResponse;
	}

	/**
	 * Generates the transaction key
	 */
	public void generateKey(){
		Octo_SeleniumLibrary.clickElement(driver, By.name( "generateTranKey"));
		String id = getGeneratedTransactionId();
		if (id != null)
			log.info("Generated transaction id: " + id);
		else
			log.error("Could not generate Transaction Id");
	}
	
	/**
	 * Gets the generated edi transaction key from the UI
	 * 
	 * @return
	 */
	public String getGeneratedTransactionId() {
		WebElement table = driver.findElement(By.id("TransactionsList"));
		int noOfRows = TableElementUtils.getTableRowCount(table);		
		WebElement lastRow = TableElementUtils.getNthTableRow(table, noOfRows);
		String transactionId = "";		
		if (lastRow != null) {
			transactionId = driver.findElement(By.xpath("//table[@id='TransactionsList']/tbody/tr[" + noOfRows +"]/td[1]")).getText();
		} 
		return transactionId;
	}
	
	/**
	 * Wait for key to be generated
	 * @return
	 */
    public boolean waitForEdiKeyGeneration() throws InterruptedException{
        return WebDriverUtil.wait(driver, refreshBtn, By.xpath("//span[@class='btn btn-success special']"), TIMEOUT_FOR_KEY, POLLING_PERIOD_SECONDS, TimeUnit.SECONDS);
    }
    
    
	/**
	 * Returns a map of contract number and modification number values for mods
	 * or return map of solicitation, refresh and control id
	 * @return
	 */
	public HashMap<String, String> retrieveModificationKey(){
		HashMap<String, String> numberMap = new HashMap<>();
		if(CommonUtilPage.isOffer(executionContext)){
			numberMap.put("duns", ActionByLocator.getText(driver, DUNS_NUMBER_COL, TIMEOUT_IN_SECS));
			numberMap.put("solNumber", ActionByLocator.getText(driver, SOLICITATION_NUMBER_COL, TIMEOUT_IN_SECS));
			numberMap.put("refreshNumber", ActionByLocator.getText(driver, REFRESH_NUMBER_COL, TIMEOUT_IN_SECS));
		}else {
			numberMap.put("contractNumber", ActionByLocator.getText(driver, MODIFICATION_KEY_CONTRACT_COL, TIMEOUT_IN_SECS));
			numberMap.put("modNumber", ActionByLocator.getText(driver, MODIFICATION_KEY_MOD_COL, TIMEOUT_IN_SECS));
		}
			
		return numberMap;
	}

	
	/**
	 * soap request is built and edi transaction is triggered
	 * 
	 * @throws Exception
	 */
	public void postToEDI() throws Exception {
		
		String filePath = null;
		String transactionId = getGeneratedTransactionId();
		Map<String, String> numberMap = new HashMap<>();
		String fileName = executionContext.getCurrentScenarioObj().getAsJsonObject("ediOption").get("edifileName").getAsString();
		filePath = "/data/ediXml/" + fileName;
	    String fileContent = readFromFile(filePath);
		String encodedData = Base64.encodeBase64String(fileContent.getBytes());
		log.info("Preparing the soap request");
		
		numberMap = retrieveModificationKey();	
		log.info("Going to post file " + filePath);
		
		StringBuilder soapRequest = new StringBuilder();
	        soapRequest.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:v1=\"http://fas.gsa.gov/services/Attachment/v1.0\">");
	        soapRequest.append("<soapenv:Header/>");
	        soapRequest.append("<soapenv:Body>");
	        soapRequest.append("<v1:acquisitionData>");
	        soapRequest.append("<transactionId>" + transactionId + "</transactionId>");
	        if(CommonUtilPage.isOffer(executionContext)){
	    		String solicitationNumber = numberMap.get("solNumber");
	    		String refreshNumber = numberMap.get("refreshNumber");
	    		String dunsNo = numberMap.get("duns");
	        	soapRequest.append("<offerKey>"
			              	+ "<solicitationKey>"
			              	+ "<solicitationNumber>" + solicitationNumber + "</solicitationNumber>"
			              	+ "<solicitationRefreshNumber>" + refreshNumber + "</solicitationRefreshNumber>"
			              	+ "</solicitationKey>"
			              	+ "<vendorKey>"
			              	+ "<duns>" + dunsNo + "</duns>"
			              	+ "</vendorKey>"		              	
			              	+"</offerKey>");
	        }else{  
	        	String contractId = numberMap.get("contractNumber");
	    		String modId = numberMap.get("modNumber");
	    		
	    		// TODO Refactor to support more than mod type. Currently, only one mod type 
	    		// is expected in 'modType' scenario data object 
	    		String modType = getModTypeForEDIMessage(executionContext.getCurrentScenarioObj().get("modTypes").getAsJsonArray().get(0).getAsString());
	        	
	    		soapRequest.append( "<contractModActionKey>"
		              	+ "<contractModKey>"
		              	+ "<contractNumber>" + contractId + "</contractNumber>"
		              	+ "<modificationNumber>" + modId + "</modificationNumber>"
		              	+ "</contractModKey>"
		              	+"<modType>"+ modType + "</modType>"
		              	+"</contractModActionKey>");
	        }
	        	soapRequest.append("<name>EDI</name>"
              	+ "<type>"+ "pricing/xml"+"</type>"
            	+ "<data>" + encodedData + "</data>"
              	+ "<dunsNo>"+ executionContext.getCurrentScenarioObj().get("DUNS").getAsString() +"</dunsNo>");
	        soapRequest.append("</v1:acquisitionData>");
	        soapRequest.append("</soapenv:Body>");
	        soapRequest.append("</soapenv:Envelope>");
	        log.info("Executing the edi transaction soap request");
	    
	        log.info("webservice host - " + LoadProperties.getProperty("webservice.host"));
	        log.info("edi webservice endpoint - " + LoadProperties.getProperty("edi.webservice.endpoint"));
		HttpURLConnectionSoap httpSoapConnection = new HttpURLConnectionSoap();
		httpSoapConnection.createHttpURLConnectionAndMakeRequest(soapRequest.toString(), LoadProperties.getProperty("webservice.host"), LoadProperties.getProperty("edi.webservice.endpoint"), false);
	}
	
	/**
	 * Maps modType object from scenario data to the 
	 * XML modType for EDI message
	 * 
	 * @param modType
	 * @return
	 */
	private String getModTypeForEDIMessage(String modType) {
		if (modTypeMap.containsKey(modType)) {
			return modTypeMap.get(modType);
		} else {
			throw new TestDataException("Undefined EDI mod type: " + modType);
		}
	}

	/**
	 * Wait for edi transaction to complete
	 * 
	 * @return
	 */
    public boolean waitForEdiTransactionToComplete() throws InterruptedException{
        return WebDriverUtil.wait(driver, REFRESH_TRANSACTION_STATUS_LINK, By.xpath("//span[@class='btn btn-success special']"), TIMEOUT_FOR_KEY, POLLING_PERIOD_SECONDS, TimeUnit.SECONDS);
    }
    
    /**
     * Reads the xml file contents for edi transaction
     * 
     * @param ediFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
	private String readFromFile(String ediFile)throws FileNotFoundException, IOException {
		if ( ediFile != null && ediFile.length() > 0 ) {
			URL dataFileURL = this.getClass().getResource(ediFile);
			File file = new File(dataFileURL.getFile());
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuffer fileContents = new StringBuffer();
			String line = br.readLine();
			while (line != null) {
				fileContents.append(line);
				line = br.readLine();
			}
			br.close();
			return fileContents.toString();
		}
		return "";
	}
}
