package gov.gsa.sst.common;

import gov.gsa.sst.util.LoadProperties;

public class GetUEIandContract {

	public static String getContractNumberByFilter(String contractNumber) {

	//	String newContractNumber = contractNumber;
		switch (contractNumber) {
		case "eMod_additions_Contract":
			contractNumber = LoadProperties.getProperty("eMod_additions_Contract");
			break;
		case "eMod_additions_Contract_TDR":
			contractNumber = LoadProperties.getProperty("eMod_additions_Contract_TDR");
			break;
		case "eMod_administrative_Contract":
			contractNumber = LoadProperties.getProperty("eMod_administrative_Contract");
			break;
		case "eMod_cancel_Contract":
			contractNumber = LoadProperties.getProperty("eMod_cancel_Contract");
			break;
		case "eMod_deletions_Contract":
			contractNumber = LoadProperties.getProperty("eMod_deletions_Contract");
			break;
		case "eMod_legal_Contract":
			contractNumber = LoadProperties.getProperty("eMod_legal_Contract");
			break;
		case "eMod_pricing_Contract":
			contractNumber = LoadProperties.getProperty("eMod_pricing_Contract");
			break;
		case "eMod_productCatalog_Contract":
			contractNumber = LoadProperties.getProperty("eMod_productCatalog_Contract");
			break;
		case "eMod_technical_Contract":
			contractNumber = LoadProperties.getProperty("eMod_technical_Contract");
			break;
		case "eMod_terms_Contract":
			contractNumber = LoadProperties.getProperty("eMod_terms_Contract");
			break;
		case "eMod_terms_Contract_FCP":
			contractNumber = LoadProperties.getProperty("eMod_terms_Contract_FCP");
			break;
		}

		return contractNumber;
	}

	public static String getUEIByFilter(String ueiNumber) {

		// get specific UEI for eMod only when we have below UEI in JSON file
		switch (ueiNumber) {
		case "eMod_additions_UEI":
			ueiNumber = LoadProperties.getProperty("eMod_additions_UEI");
			break;
		case "eMod_additions_UEI_TDR":
			ueiNumber = LoadProperties.getProperty("eMod_additions_UEI_TDR");
			break;
		case "eMod_administrative_UEI":
			ueiNumber = LoadProperties.getProperty("eMod_administrative_UEI");
			break;
		case "eMod_cancel_UEI":
			ueiNumber = LoadProperties.getProperty("eMod_cancel_UEI");
			break;
		case "eMod_deletions_UEI":
			ueiNumber = LoadProperties.getProperty("eMod_deletions_UEI");
			break;
		case "eMod_legal_UEI":
			ueiNumber = LoadProperties.getProperty("eMod_legal_UEI");
			break;
		case "eMod_pricing_UEI":
			ueiNumber = LoadProperties.getProperty("eMod_pricing_UEI");
			break;
		case "eMod_productCatalog_UEI":
			ueiNumber = LoadProperties.getProperty("eMod_productCatalog_UEI");
			break;
		case "eMod_technical_UEI":
			ueiNumber = LoadProperties.getProperty("eMod_technical_UEI");
			break;
		case "eMod_terms_UEI":
			ueiNumber = LoadProperties.getProperty("eMod_terms_UEI");
			break;
		case "eMod_terms_FCP":
			ueiNumber = LoadProperties.getProperty("eMod_terms_FCP");
			break;
		}
		return ueiNumber;
	}
}
