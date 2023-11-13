#@fpt
#Feature: Product Catalog Update mod
  #
  #As a vendor
  #I want to update existing products for my contract
  #So that I can update products information of my existing contract
#
  #Scenario: Product Catalog Update Mod: Create eMod successful
    #Given I am on the Modifications List page
    #When I create an "Product Catalog Update" mod
    #Then Product Catalog Update mod is created successfully
#
  #@skip
  #Scenario: Product Catalog Update Mod: Select multiple mods to create
    #Given I am on the Modifications List page
    #When I select mod types "Product Catalog Update" and "Add SIN"
    #Then Error message with quotes is displayed: /The mod type "Product Catalog Update" can not be combined with any other mod type/
#
  #Scenario: Product Catalog Update Mod: Complete Product Catalog Update page
    #Given I am on the Product Catalog Update eMod "Product Catalog Update" page
    #When I validate contract begin date and sin list associated with the contract
    #And I add detailed description of the modification
    #And I complete Product Catalog Update page successfully
    #Then "Product Catalog" menu is complete
#
  #Scenario: Product Catalog Update Mod: Select Components fails when no components selected
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I DO NOT select any components and try to save the page
    #Then Error message with quotes is displayed: "No Change" /has been selected for each Component/
#
  #@skip
  #Scenario: Product Catalog Update Mod: Select Components fails when Delivery level is not updated
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select Delivery component and try to save the page
    #Then Error message with quotes is displayed: "Level" /for Delivery is required when "Change Delivery Level" is selected/
#
  #Scenario: Product Catalog Update Mod: Validate Select Components page content
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When Instructions and page elements are displayed on the Select Components Page
    #And I validate non-pricing Changes icon text
    #And I validate Bi-lateral Signature icon text
    #And I verify information icon text for each component
#
  #@fileupload @skip 
  #Scenario: Product Catalog Update Mod: Complete without Signature Components with Add option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Add" option for Product Catalog Updates without Signature for contract without these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@fileupload @skip 
  #Scenario: Product Catalog Update Mod: Complete without Signature Components with Update option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Update" option for Product Catalog Updates without Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@skip
  #Scenario: Product Catalog Update Mod: Complete without Signature Components with Remove option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Remove" option for Product Catalog Updates CW, Photos without Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@fileUpload @skip @all_env 
  #Scenario: Product Catalog Update Mod: Complete Bi-lateral Signature components with Add option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Add" option for Product Catalog Updates with Bi-lateral Signature for contract without these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@fileUpload @skip @all_env
  #Scenario: Product Catalog Update Mod: Complete Bi-lateral Signature components with Update option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Update" option for Product Catalog Updates with Bi-lateral Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@skip
  #Scenario: Product Catalog Update Mod: Complete Bi-lateral Signature components with Remove option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Remove" option for Product Catalog Updates SF, Del, Op, SC, OPI with Bi-lateral Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@fileUpload @all_env
  #Scenario: Product Catalog Update Mod: Complete with and without Signature Components with Add option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Add" option for Product Catalog Updates with and without Signature for contract without these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@all_env @fileUpload
  #Scenario: Product Catalog Update Mod: Complete with and without Signature Components with Update option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Update" option for Product Catalog Updates with and without Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@all_env
  #Scenario: Product Catalog Update Mod: Complete with and without Signature Components with Remove option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Remove" option for Product Catalog Updates CW, Ph, SF, Op, SC with and without Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@all_env @edi 
  #Scenario: Product Catalog Update Mod: Complete one of each Pricing and Non-pricing Component with Update option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Update" option for Product Catalog Updates "Other Product Information" with and "Accessories" without Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@edi @skip
  #Scenario: Product Catalog Update Mod: Complete one of each Pricing and Non-pricing Components with Add option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Add" option for Product Catalog Updates "Delivery" with and "Contractor Warranty" without Signature for contract without these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@edi 
  #Scenario: Product Catalog Update Mod: Complete one of each Pricing and Non-pricing Components with Remove option
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Remove" option for Product Catalog Updates "Options, Special Charges" with and "Photos" without Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #Scenario: Product Catalog Update Mod: Complete mod along with Zonal Pricing page
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #And I select "Add" option for Product Catalog Updates Contractor Warranty for FPT contract
    #When I complete all Product data updates for FPT contract
    #And all menu items are complete
#
  #@negative @wip
  #Scenario: Product Catalog Update Mod: Capture Product Data fails when SC, SF, Op, Acc, Prod Info data is not provided
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Special Charges" component and DO NOT provide data in pricing document template
    #And I 'Validate Product Data' OR select 'Continue' option
    #Then Validate "Capture Product Data" page has errors
#
  #@wip @negative
  #Scenario: Product Catalog Update Mod: Capture Product Data fails when invalid data is provided and succeeds when the data is corrected
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #And I select Delivery component and try to save the page
    ##Reuse the method DownloadUploadTemplatePage.uploadAndValidateError()
    #When I provide an invalid pricing document template and status fails
    #Then I provide the valid pricing document template and Capture Product Data completes successfully
#
  ######################### Other/ Final Pricing Scenario's  ########################
  #@skip
  #Scenario: Product Catalog Update Mod: Access EDI for processing a Product Catalog Update Mod
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I have completed the product catalog updates and selected Pricing and Non-pricing components
    #Then Instructions and page elements are displayed on the EDI Page
#
  #@skip
  #Scenario: Product Catalog Update Mod: Validate for Remove All on the Select Components page
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Remove All" option for components CW, SF, SC, Ph and items do not exist for that component
    #Then Validate "Select Components" page has errors
#
  #@wip
  #Scenario: Product Catalog Update Mod: Save question and prompts
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select Options for the Bilatral and Non Pricing Items
    #Then the System shows information and saves on Confirm Selection page
#
  #Scenario: Product Catalog Update Mod: Retain selection on the Select Component Page
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select components for Product Catalog Mod and click 'No' on the Confirmation page
    #Then the system navigates to Select Components page and last selection for Options displays
#
  #@skip
  #Scenario: Product Catalog Update Mod: Validate Instructions on the Wizard Management Page
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I have completed the product catalog updates and selected Pricing and Non-pricing components
    #Then Instructions and page elements are displayed on the Wizard Management Page
#
  #@skip
  #Scenario: Product Catalog Update Mod: Pricing Document incomplete if fail to Generate Final Pricing Document
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I have completed the product catalog updates and selected Pricing and Non-pricing components
    #And Instructions and page elements are displayed on the Final Pricing Document Page
    #And I DO NOT Generate Final Pricing Document but select Continue option
    #Then Error message with quotes is displayed: /The "Final Pricing Document" has not been completed/
#
  #@skip
  #Scenario: Product Catalog Update Mod: Validate Capture Product Data and Download/Upload Product Catalog for Excel Pages
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I have completed the product catalog updates and selected Pricing and Non-pricing components
    #Then Instructions and page elements are displayed on the Capture Product Data Page
    #And Instructions and page elements are displayed on the Download/Upload Product Catalog for Excel Page
#
  #@wip
  #Scenario: Product Catalog Update Mod: Validations for the Delivery at SIN-Contract Level
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I change Delivery to "Contract" level on the Select Components page
    #And the System shows message and updates data for the "Contract" level delivery
    #And I change Delivery to "SIN" level on the Select Components page
    #Then the System shows message and updates data for the "SIN" level delivery
#
  #@wip
  #Scenario: Product Catalog Update Mod: Validations for the Delivery at Line Item-Contract Level
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I change Delivery to "Contract" level on the Select Components page
    #And the System shows message and updates data for the "Contract" level delivery
    #And I change Delivery to "Line Item" level on the Select Components page
    #Then the System shows message and updates data for the "Line Item" level delivery
#
  #@wip
  #Scenario: Product Catalog Update Mod: Validations for the Delivery at SIN-Line Item Level
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I change Delivery to "Line Item" level on the Select Components page
    #And the System shows message and updates data for the "Line Item" level delivery
    #And I change Delivery to "SIN" level on the Select Components page
    #Then the System shows message and updates data for the "SIN" level delivery
#
  #Scenario: Product Catalog Update Mod: Delivery Level Change from Contract to Line Item
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I change Delivery level to "Line Item Level" from "Contract" on the Select Components page
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #Scenario: Product Catalog Update Mod: Delivery Level Change from Contract to SIN
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I change Delivery level to "SIN" from "Contract" on the Select Components page
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #Scenario: Product Catalog Update Mod: Delivery Level Change from Line Item to SIN
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I change Delivery level to "SIN" from "Line Item Level" on the Select Components page
    #And I complete all Product data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  ######################Photos #######################
  #@skip
  #Scenario: Product Catalog Update Mod: There are No Photos on the Contract and vendor chooses to Add/Update Photos but no product to photo association is made
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Update" option for Product Catalog Updates without Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #Then Error message with quotes is displayed: /"Add/Update" for Photos has been selected on "Select Components" page, therefore, at least one Product-Photo association is required within the product data./
#
  #@skip
  #Scenario: Product Catalog Update Mod: There are Photos on the Contract but no product to photo association is made
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Update" option for Product Catalog Updates without Signature for contract with these components
    #And I complete all Product data updates for FPT contract
    #Then Warning with quotes is displayed: /At least one Product-Photo association is required within the product data OR at least one photo must be uploaded on the "Upload Photos" page, if "Add/Update" has been selected for Photos on the "Select Components" page/
#
  #@skip
  #Scenario: Product Catalog Update Mod: Add association for new photo but no photo uploaded
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #And I select "Update" option for Product Catalog Updates without Signature for contract with these components
    #When I complete all Product data updates for FPT contract
    #And I try to validate photos without uploading any photos
    #Then Error message is displayed:
      #"""
      #The following photos are missing and must be uploaded
      #Flower00.jpg
      #"""
#
  #Scenario: Product Catalog Update Mod: Zip File Validation-where none of the photos had product to photo association from Contract
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #And I select "Update" option for Product Catalog Updates without Signature for contract with these components
    #When I complete all Product data updates for FPT contract
    #Then Error message with quotes is displayed: /"File Name" for photo must match one of the "Additional Photo" names you previously provided./
#
  #Scenario: Product Catalog Update Mod: Photo Validation during Generating Pricing Document -when user only updates the photo image and performs no other change
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #And I select "Update" option for Product Catalog Updates without Signature for contract with these components
    #When I complete all Product data updates for FPT contract
    #And I try to generate the Final Pricing Document
    #Then Error message with quotes is displayed: /"Add/Update" for Photos has been selected on "Select Components" page, therefore, at least one Product-Photo association is required within the product data OR at least one photo must be uploaded on the "Upload Photos" page/
#
  #Scenario: Product Catalog Update Mod: Delete uploaded replacement photo
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #And I have completed the product catalog updates and selected Pricing and Non-pricing components
    #And I complete all Product data updates for FPT contract
    #And the vendor has uploaded a photo with the same name as an already existing photo for a contract
    #When the vendor deletes the uploaded photo file
    #Then the user receives message: "This delete photo action only deleted the photo that was loaded in this Product Catalog Mod, and left the photo that was previously loaded with the same filename."
#
  #Scenario: Product Catalog Update Mod: Delete uploaded non-replacement photo
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #And I have completed the product catalog updates and selected Pricing and Non-pricing components
    #And I complete all Product data updates for FPT contract
    #And the vendor has uploaded a photo with a unique name
    #When the vendor deletes the uploaded photo file
    #Then the photo is deleted from the mod
    #
    #@negative
    #Scenario: Product Catalog Update Mod: Invalid data submission
    #Given I am on the Product Catalog Update eMod "Select Components" page
    #When I select "Add" option for Product Catalog Updates with Bi-lateral Signature for contract without these components
    #And I complete all Wizard Mgmt sub pages
    #And I download the product template
    #When I update the template with invalid pricing details for "Pricing,Non Pricing,Photos" sheet
    #And I try to upload the completed product template with invalid data
    #Then template document upload process fails and data errors are verified
