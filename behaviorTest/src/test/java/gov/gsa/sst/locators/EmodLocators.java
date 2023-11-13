package gov.gsa.sst.locators;

import org.openqa.selenium.By;

public class EmodLocators {
	
	/*
	 * Pattern:
	 * public static final By locatorName = value
	 * locatorName -> pageName_purposer of locator
	 * 
	 * like  modSelectionPage_termAndCondationCheckBox
	 * 		 modSubmitPage_submitBtn
	 * */
	
	
	
	
	/**********Select Contract****************/
	public static final By selectContract_headerText = By.xpath("//h1[contains(text(), 'List of Contracts Available')]");
	public static final By selectContract_selectContractBtn = By.xpath("//input[@value='Select Contract']");
}
