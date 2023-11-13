package gov.gsa.sst.util;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import comment.ExecutionContext;

import util.PageUtil;


public class XSBUtil {
    private static final By PROCEED_TO_DOWNLOAD_BUTTON = By.id("btnToDownloadTemplate");
    private static final int MAX_NO_OF_ATTEMPTS_FOR_STATUS_CHECK = 10;
	private static final By MOD_NUMBER = By.xpath("//div[@id='prepHead']/table/tbody/tr[3]/td");    
    private static final By OAR_GENERATION_TRANSACTION_ID = By.id("offerAnalysisGenerationTransactionId");
    private static final By OAR_GENERATION_GPC_ID = By.id("gpcId");

	public static boolean checkOfferAnalysisReportGenerationCompletionStatus(ExecutionContext executionContext, int waitTime) {
//		WebDriver driver = executionContext.getDriver();
//		int noOfAttempts = 0;
		boolean isComplete = false;
//		do{
//			PageUtil.clickSubmitButtonByVal(driver, "Check Status");
//            
//            //Wait for 5 seconds and retry
//            try {
//				Thread.sleep(waitTime);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//            
//            if (driver.findElements(PROCEED_TO_DOWNLOAD_BUTTON).size() > 0) {
//            	isComplete = true;
//            	break;
//            }
//            noOfAttempts++;
//		} while (noOfAttempts < MAX_NO_OF_ATTEMPTS_FOR_STATUS_CHECK);
		return isComplete;	
	}
	
	
	public static void executeGraph(ExecutionContext executionContext, boolean isOffer, String mockResponseFileName) throws IOException  {
//		WebDriver driver = executionContext.getDriver();
//        String transactionId = driver.findElement(OAR_GENERATION_TRANSACTION_ID).getAttribute("value");
//        System.out.println("Offer Analysis Report transaction id = " + transactionId);
//        String gpcId = driver.findElement(OAR_GENERATION_GPC_ID).getAttribute("value");
//        System.out.println("Offer Analysis Report Generation gpc id = " + gpcId);
//		
//		
//		String urlStr  = LoadProperties.getProperty("clover.url") +  "/request_processor/graph_run?sandbox=CPT";
//		String parametersToPass = "&graphID=graph/Xsb/MockExtractResponseXML.grf&param_GPC_ID=" + gpcId + "&param_transactionId=" + transactionId ;
//		if (isOffer) {
//			String solicitationNumber = executionContext.getCurrentScenarioObj().get("solicitationNumber").getAsString();
//			String refreshNumber = executionContext.getCurrentScenarioObj().get("solicitationRefreshNumber").getAsString();
//			String dunsNo = DataUtil.getUEI(executionContext);
//			parametersToPass += "&param_objectType=Offer&param_solicitationNumber=" + solicitationNumber + "&param_refreshNumber=" + refreshNumber + "&param_dunsNo=" + dunsNo +"&param_inputFileName=" + mockResponseFileName;
//		} else {
//			String contractNumber = executionContext.getCurrentScenarioObj().get("contractNumber").getAsString();
//			String modNumber = driver.findElement(MOD_NUMBER).getText();
//			System.out.println("Mod number is ");
//			String dunsNo = DataUtil.getUEI(executionContext);
//			parametersToPass += "&param_objectType=Mod&param_contractNumber=" + contractNumber + "&param_modificationNumber=" + modNumber + "&param_dunsNo=" + dunsNo +"&param_inputFileName=" + mockResponseFileName;
//		}
//		
//		byte[] postDataBytes = parametersToPass.getBytes("UTF-8");
//		//The Http Call should return a runId
//		 URL url = new URL(urlStr);
//		 HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//		 urlConnection.setDoOutput(true);
//		 urlConnection.setRequestMethod("POST");
//		 urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//		 urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
//		 urlConnection.setDoOutput(true);
//		 String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(LoadProperties.getProperty("clover.password").getBytes());
//		 urlConnection.setRequestProperty ("Authorization", basicAuth);
//
//		 urlConnection.getOutputStream().write(postDataBytes);
//
//		 System.out.println(" Response Code from URL Connection " + urlConnection.getResponseCode());
//		 System.out.println(" Response Message from URL Connection " + urlConnection.getResponseMessage());
//
//		 BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//		 String inputLine;
//
//		 while ((inputLine = in.readLine()) != null) {
//		    System.out.println(inputLine);
//		 }
//		 in.close();
	}

}