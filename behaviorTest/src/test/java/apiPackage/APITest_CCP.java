package apiPackage;

import java.io.IOException;
import io.restassured.response.Response;


public class APITest_CCP {
	
	
//	{"contractNumber": "NotUsed", "ccpActionId": "ccpActionIdValue", "eModId": "W4226SB4", "submissionFiles": 
	//[ { "s3Path": "s3://cmccp-s3-development/base_product_file/CCP_Product_File.xlsx", "fileName": "file-name-as-uploaded_2.xlsx", "type": "TnCFile" } ] }

//	Authentication Details - rpauser/Rpa@GsaRegion2!
	
	
//	POST API URL (DEV)-  https://gateway-am.test.apiss.mcaas.fcs.gsa.gov/eoffer/dev/offerapi/modservice/modFiles/W4226SB4   ---> (  W4226SB4    is eMod Control Id)

//		POST API URL (TEST)-  https://gateway-am.test.apiss.mcaas.fcs.gsa.gov/eoffer/test/offerapi/modservice/modFiles/W4226SB4  
	
	
	
	public static void main(String[] args){
		
		testCCPAPI("QSJ6BHIA");
		

		
	}
	
	
	public static Response testCCPAPI(String eModId) {
		Response resp=null;
	
		
		ApiRequestUtility apiLib = new ApiRequestUtility();
		try {
		//create request
		apiLib.specifyBaseURIWithBasicAuth("rpauser", "Rpa@GsaRegion2!");
		
		apiLib.setRequestHeader("Content-Type", "application/json");
		
		String fileName = "Price Proposal Template_Automation"+apiLib.generateRandomNumberForCCP()+".xlsx";
		
		//outter body
		apiLib.setRequestBody("contractNumber", "string");
		apiLib.setRequestBody("ccpActionId", "string");
		apiLib.setRequestBody("emodId", eModId);
		
		// inner body
		apiLib.setInnerBody("s3Path", "s3://cmccp-s3-test/base_product_file/CCP_Product_File.xlsx");
		apiLib.setInnerBody("fileName", fileName);
		apiLib.setInnerBody("type", "string");
		apiLib.addNestedNodesToTheBody("submissionFiles");
		
		//Post
		 resp = apiLib.sendAPIRequestWithResponse("post", eModId);
		
		System.out.println("Response Code is: "+resp.statusCode()+"\nFile Name: "+fileName);

		System.out.println("Response Body is: \n"+resp.body().asPrettyString());
		}
		catch(Exception e) {
			System.out.println("API call failed by eMod ID: "+eModId);
			apiLib.showAPIDetails();
			e.printStackTrace();
		}
		return resp;
	}
	
	
	
	
	
}
