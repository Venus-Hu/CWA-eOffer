package gov.gsa.sst.productcatalog;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import comment.Steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.gsa.sst.capturepricing.downloaduploadtemplate.DownloadUploadTemplatePage;
import gov.gsa.sst.common.LeftNavigationMenu;
import gov.gsa.sst.commonpage.CommonUtilPage;
import customUtility.Octo_SeleniumLibrary;import gov.gsa.sst.delivery.DeliveryPage;

@Component
public class ProductCatalogUpdateMod extends Steps{

	private static Logger log = LoggerFactory.getLogger(ProductCatalogUpdateMod.class);
	protected ProductCatalogUpdateModPage productCatalogUpdateModPage;
	protected SelectComponentsModPage selectComponentsModPage;
	
	public void initProductCatalog()
	{
		if(productCatalogUpdateModPage == null)
			productCatalogUpdateModPage = new ProductCatalogUpdateModPage(executionContext);
		productCatalogUpdateModPage.get();
	}
	
	public void initSelectComponents()
	{
		if(selectComponentsModPage == null)
			selectComponentsModPage = new SelectComponentsModPage(executionContext);
		selectComponentsModPage.get();
	}
	
	@When("^I create an \"([^\"]*)\" mod$")
	public void I_create_an_mod(String arg1) {
		initProductCatalog();
	}
	
	@Then("^Product Catalog Update mod is created successfully$")
	public void product_Catalog_Update_mod_is_created_successfully() throws Throwable {
		LeftNavigationMenu.isMenuItemPresent(executionContext.getDriver(), "Product Catalog");
	}
	
	@Given("^I am on the Product Catalog Update eMod \"([^\"]*)\" page$")
	public void i_am_on_the_Product_Catalog_Update_eMod_page(String arg1) throws Throwable {
		initProductCatalog();
		productCatalogUpdateModPage.populateForm();
	}

	@When("^I validate contract begin date and sin list associated with the contract$")
	public void i_validate_contract_begin_date_and_sin_list_associated_with_the_contract() throws Throwable {
		log.info("Validating the contract begin date and sin list");
		LeftNavigationMenu.navigateTo(executionContext.getDriver(), "Product Catalog");
		productCatalogUpdateModPage.validateContractBeginDate();
		productCatalogUpdateModPage.validateSins();
	}

	@When("^I add detailed description of the modification$")
	public void i_add_detailed_description_of_the_modification() throws Throwable {
		productCatalogUpdateModPage.setDetailedDescription();
	}

	@When("^I complete Product Catalog Update page successfully$")
	public void i_complete_Product_Catalog_Update_page_successfully() throws Throwable {
		productCatalogUpdateModPage.clickSaveAndContinue();
	}

	@When("^I validate non-pricing Changes icon text$")
	public void validateLightiningBoltIconText() throws Throwable {
		initSelectComponents();
		selectComponentsModPage.validateLightningBoltText();
	}
	
	@When("^I validate Bi-lateral Signature icon text$")
	public void validateHourglassIconText() throws Throwable {
		initSelectComponents();
		selectComponentsModPage.validateHourGlassText();
	}
	
	@When("^I verify information icon text for each component$")
	public void i_verify_information_icon_text_for_each_component() throws Throwable {
		initSelectComponents();
		selectComponentsModPage.validateInformationIconText();
	}
	
	@When("^I select components without Signature on Select Components page with Add option$")
	public void selectComponents() throws Throwable {
		initSelectComponents();
		selectComponentsModPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				.getAsJsonObject("selectComponents"));		
	}
	
	@When("^I select \"([^\"]*)\" option for Product Catalog Updates without Signature for contract without these components$")
	public void i_select_option_for_Product_Catalog_Updates_without_Signature_for_contract_without_these_components(String arg1) throws Throwable {
		log.info("Selecting non-pricing components with " + arg1 + " option");
		selectComponents();
	}

	@When("^I select \"([^\"]*)\" option for Product Catalog Updates without Signature for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_without_Signature_for_contract_with_these_components(String arg1) throws Throwable {
		log.info("Selecting components without Signature and with " + arg1 + " option");
		selectComponents();
	}
	
	@When("^I select \"([^\"]*)\" option for Product Catalog Updates CW, Photos without Signature for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_CW_Photos_without_Signature_for_contract_with_these_components(String arg1) throws Throwable {
		log.info("Selecting Contractor Warranty and Photos components with '" + arg1 + " All' option");
		selectComponents();
	}	
	
	@When("^I select \"([^\"]*)\" option for Product Catalog Updates with Bi-lateral Signature for contract without these components$")
	public void i_select_option_for_Product_Catalog_Updates_with_Bi_lateral_Signature_for_contract_without_these_components(String arg1) throws Throwable {
		log.info("Selecting all Bi-lateral signature components with " + arg1 + " option");
		selectComponents();
	}

	@When("^I select \"([^\"]*)\" option for Product Catalog Updates with Bi-lateral Signature for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_with_Bi_lateral_Signature_for_contract_with_these_components(String arg1) throws Throwable {
		log.info("Selecting all Bi-lateral signature components with " + arg1 + " option");
		selectComponents();
	}

	@When("^I select \"([^\"]*)\" option for Product Catalog Updates SF, Del, Op, SC, OPI with Bi-lateral Signature for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_SF_Del_Op_SC_OPI_with_Bi_lateral_Signature_for_contract_with_these_components(String arg1) throws Throwable {
		log.info("Selecting SF, Del, Op, SC, OPI Bi-lateral signature components with " + arg1 + " option");
		selectComponents();
	}
	
	@When("^I select \"([^\"]*)\" option for Product Catalog Updates SF, SC with Bi-lateral Signature for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_SF_SC_with_Bi_lateral_Signature_for_contract_with_these_components(String arg1) throws Throwable {
		log.info("Selecting Special Charges and Special Features Bi-lateral signature components with " + arg1 + " option");
		selectComponents();
	}
	
	
	@When("^I select \"([^\"]*)\" option for Product Catalog Updates CW, SF, SC, Ph, Op components for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_CW_SF_SC_Ph_Op_components_for_contract_with_these_components(String arg1) throws Throwable {
		log.info("Selecting Photos Bi-lateral signature components with " + arg1 + " option");
		selectComponents();
	}

	
	@When("^I select \"([^\"]*)\" option for Product Catalog Updates with and without Signature for contract without these components$")
	public void i_select_option_for_Product_Catalog_Updates_with_and_without_Signature_for_contract_without_these_components(String arg1) throws Throwable {
		log.info("Selecting with and without Signature components components with " + arg1 + " option");
		selectComponents();
	}

	@When("^I select \"([^\"]*)\" option for Product Catalog Updates with and without Signature for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_with_and_without_Signature_for_contract_with_these_components(String arg1) throws Throwable {
		log.info("Selecting with and without Signature components components with " + arg1 + " option");
		selectComponents();
	}

	@When("^I select \"([^\"]*)\" option for Product Catalog Updates CW, Ph, SF, Op, SC with and without Signature for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_CW_Ph_SF_Op_SC_with_and_without_Signature_for_contract_with_these_components(String arg1) throws Throwable {
		log.info("Selecting CW, Ph, SF, Op, SC components components with " + arg1 + " option");
		selectComponents();
	}
	
	@When("^I select \"([^\"]*)\" option for Product Catalog Updates \"([^\"]*)\" with and \"([^\"]*)\" without Signature for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_with_and_without_Signature_for_contract_with_these_components(String arg1, String arg2, String arg3) throws Throwable {
		log.info("Selecting " + arg1 + " option for Product Catalog Updates " + arg2 + " with and" + arg3 +  " without Signature for contract with these components");
		selectComponents();	 
	}
	
	@When("^I select \"([^\"]*)\" option for Product Catalog Updates \"([^\"]*)\" with and \"([^\"]*)\" without Signature for contract without these components$")
	public void i_select_option_for_Product_Catalog_Updates_with_and_without_Signature_for_contract_without_these_components(String arg1, String arg2, String arg3) throws Throwable {
		log.info("Selecting " + arg1 + " option for Product Catalog Updates " + arg2 + " with and" + arg3 +  " without Signature for contract without these components");
		selectComponents();	 
	}

	@When("^I DO NOT select any components and try to save the page$")
	public void I_DO_NOT_select_any_components_and_try_to_save_the_page() throws Throwable {
		initSelectComponents();
		selectComponentsModPage.clickContinueBtn();
	}
	
	@Given("^I have completed the product catalog updates and selected Pricing and Non-pricing components$")
	public void i_have_completed_the_product_catalog_updates_and_selected_Pricing_and_Non_pricing_components() throws Throwable {
		    initSelectComponents();
		    selectComponents();		
	}
	
	@When("^I select \"([^\"]*)\" option for \"([^\"]*)\" Product Catalog Updates for contract without these components$")
	public void i_select_option_for_Product_Catalog_Updates_for_contract_without_these_components(String arg1, String arg2) throws Throwable {
		log.info("Selecting with and without Signature components components with " + arg1 + " option");
		selectComponents();
	}
	
	@When("^I select \"([^\"]*)\" option for \"([^\"]*)\" Product Catalog Updates for contract with these components$")
	public void i_select_option_for_Product_Catalog_Updates_for_contract_with_these_components(String arg1, String arg2) throws Throwable {
		log.info("Selecting with and without Signature components components with " + arg1 + " option");
	    selectComponents();
	}
	
	@Given("^I select \"([^\"]*)\" option for Product Catalog Updates Contractor Warranty for FPT contract$")
	public void i_select_option_for_Product_Catalog_Updates_Contractor_Warranty_for_FPT_contract(String arg1) throws Throwable {
		log.info("Selecting Contractor Warranty component with " + arg1 + " option");
		selectComponents();
	}
	
	@When("^I select Delivery component and try to save the page$")
	public void i_select_Delivery_component_and_try_to_save_the_page() throws Throwable {
		initSelectComponents();
		selectComponents();
	}
	
	@When("^I select \"([^\"]*)\" component and DO NOT provide data in pricing document template$")
	public void i_select_component_and_DO_NOT_provide_data_in_pricing_document_template(String arg1) throws Throwable {
		log.info("Selecting " + arg1 + " component in Select Components page for 'Product Catalog Update' mod");
		initSelectComponents();
		selectComponents();
	}
	
	@When("^I select components for Product Catalog Mod and click 'No' on the Confirmation page$")
	public void i_select_components_for_Product_Catalog_Mod_and_click_No_on_the_Confirmation_page() throws Throwable {
		        log.info("Select 'No' on the Confirmation Page");
                initSelectComponents();
            	selectComponentsModPage.populateForm(executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
      				  .getAsJsonObject("selectComponents"));
            	selectComponentsModPage.saveComponents("No");                   
	}
	
	@Then("^the system navigates to Select Components page and last selection for Options displays$")
	public void the_system_navigates_to_Select_Components_page_and_last_selection_for_Options_displays() throws Throwable {
		        initSelectComponents();
	            selectComponentsModPage.validateSelectedOptions();
	}
	
	@When("^I select \"([^\"]*)\" option for Product Catalog Updates with Bi-lateral Signature and Non-Pricing Items$")
	public void i_select_option_for_Product_Catalog_Updates_with_Bi_lateral_Signature_and_Non_Pricing_Items(String arg1) throws Throwable {
		        log.info("Selecting " + arg1 + " component in Select Components page for 'Product Catalog Update' mod");
		        initSelectComponents();
		        selectComponents();     
	} 
	
	@When("^I select Options for the Bilatral and Non Pricing Items$")
	public void i_select_Options_for_the_Bilatral_and_Non_Pricing_Items() throws Throwable {
		        log.info("Select 'Options' for Bilatral and Non Pricing Items");
		        initSelectComponents();
		        selectComponentsModPage.selectComponents(executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
	      				  .getAsJsonObject("selectComponents"));	        
	}

	@Then("^the System shows information and saves on Confirm Selection page$")
	public void the_System_shows_information_and_saves_on_Confirm_Selection_page() throws Throwable {
		        initSelectComponents();
                selectComponentsModPage.validateOnConfirmationTable();	    
	}
		
	@When("^I select \"([^\"]*)\" option for components CW, SF, SC, Ph and items do not exist for that component$")
	public void i_select_option_for_components_CW_SF_SC_Ph_and_items_do_not_exist_for_that_component(String arg1) throws Throwable {
		        log.info("Selecting " + arg1 + " for components CW, SF, SC, Ph");
                initSelectComponents();
                selectComponents();
	}
	
	@Then("^Instructions and page elements are displayed on the Capture Product Data Page$")
	public void instructions_and_page_elements_are_displayed_on_the_Capture_Product_Data_page() {
		// Capture Product Data Page and Capture Pricing used interchangeably in this context 
		LeftNavigationMenu.navigateTo(executionContext.getDriver(), "Capture Pricing");
		CommonUtilPage.verifyPageObjectElements(executionContext.getDriver(), "CaptureProductDataPage");
	}
	
	@Then("^Instructions and page elements are displayed on the Select Components Page$")
	public void instructions_and_page_elements_are_displayed_on_the_Select_Components_page() {
		LeftNavigationMenu.navigateTo(executionContext.getDriver(), "Select Components");
		CommonUtilPage.verifyPageObjectElements(executionContext.getDriver(), "SelectComponentsPage");
	}
	
	@Then("^Instructions and page elements are displayed on the Download/Upload Product Catalog for Excel Page$")
	public void instructions_and_page_elements_are_displayed_on_the_Download_Upload_Product_Catalog_for_Excel_Page() {
		LeftNavigationMenu.navigateTo(executionContext.getDriver(), "Capture Pricing");
		DownloadUploadTemplatePage downloadUploadPage = new DownloadUploadTemplatePage(executionContext);
		downloadUploadPage.get();
		CommonUtilPage.verifyPageObjectElements(executionContext.getDriver(), "DownloadUploadProductCatalogExcelFile");
	}
	
	@Then("^Instructions and page elements are displayed on the EDI Page$")
	public void instructions_and_page_elements_are_displayed_on_the_EDI_page() {
		LeftNavigationMenu.navigateTo(executionContext.getDriver(), "Capture Pricing");
		Octo_SeleniumLibrary.clickElement(executionContext.getDriver(), By.id("btnToEDI"));
		CommonUtilPage.verifyPageObjectElements(executionContext.getDriver(), "EDIPage");
	}
	
	@Then("^Instructions and page elements are displayed on the Wizard Management Page$")
	public void instructions_and_page_elements_are_displayed_on_the_Wizard_Management_page() {
		LeftNavigationMenu.navigateTo(executionContext.getDriver(), "Wizard Management");
		CommonUtilPage.verifyPageObjectElements(executionContext.getDriver(), "WizardManagementPage");
	}
	
	@Then("^Instructions and page elements are displayed on the Final Pricing Document Page$")
	public void instructions_and_page_elements_are_displayed_on_the_Final_Pricing_Document_page() {
		LeftNavigationMenu.navigateTo(executionContext.getDriver(), "Pricing Document");
		CommonUtilPage.verifyPageObjectElements(executionContext.getDriver(), "FinalPricingDocumentPage");
	}
	
	@When("^I change Delivery to \"([^\"]*)\" level on the Select Components page$")
	public void i_change_Delivery_to_level_on_the_Select_Components_page(String deliveryLvl) throws Throwable {
		log.info("Selecting Delivery Level... " +deliveryLvl );
		if(deliveryLvl.equals("Contract")) {
		    initSelectComponents();
		    selectComponentsModPage.selectComponents(executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				  .getAsJsonObject("selectComponents-Contract"));
		} else if(deliveryLvl.equals("SIN")) {
			  initSelectComponents();
			  selectComponentsModPage.selectComponents(executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				  .getAsJsonObject("selectComponents-SIN"));
		} else if(deliveryLvl.equals("Line Item")) {
			  initSelectComponents();
			  selectComponentsModPage.selectComponents(executionContext.getCurrentScenarioObj().getAsJsonObject("productCatalogUpdateMod")
				  .getAsJsonObject("selectComponents-Item"));			   
		} else {
			log.info("Delivery Level not defined ...");
		}
	}
	
	@When("^I change Delivery level to \"([^\"]*)\" from \"([^\"]*)\" on the Select Components page$")
	public void i_change_delivery_level_on_the_select_components_page(String newDeliveryLevel, String previousDeliveryLevel) {
		initSelectComponents();
		Assert.assertEquals("Contract delivery is not " + previousDeliveryLevel, previousDeliveryLevel, selectComponentsModPage.getDeliveryLevel());
		
		selectComponentsModPage.selectComponents(executionContext.getCurrentScenarioObj()
				.getAsJsonObject("productCatalogUpdateMod")
      			.getAsJsonObject("selectComponents"));
		Assert.assertEquals("Contract delivery is not " + newDeliveryLevel, newDeliveryLevel, selectComponentsModPage.getDeliveryLevel());
		
		selectComponentsModPage.saveChanges();
		Assert.assertTrue("'Delivery' message not as expected", selectComponentsModPage.isDeliveryLevelMessage(newDeliveryLevel, previousDeliveryLevel));
		
		selectComponentsModPage.confirmSave();
	}

	@Then("^the System shows message and updates data for the \"([^\"]*)\" level delivery$")
	public void the_System_shows_message_and_updates_data_for_the_level_delivery(String deliveryLvl) throws Throwable {
		log.info("Verifying Information for Delivery Level " + deliveryLvl);
		DeliveryPage deliveryPage = new DeliveryPage(executionContext);
		deliveryPage.get();
		deliveryPage.validateDeliveryLevelUpdate(deliveryLvl);		  
	}
		
}
