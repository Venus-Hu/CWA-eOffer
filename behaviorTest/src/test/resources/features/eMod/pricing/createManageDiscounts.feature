#@fpt
#Feature: Create/Manage Discounts mod
  #
  #As a vendor
  #I want to create new discounts and update existing discounts on my existing contract
  #So that I can modify discounts but contract terms can stay in effect
#
  #@wip
  #Scenario: Create/Manage Discounts Mod
    #Given I am on the Modifications List page
    #When I create an "Create/Manage Discounts" mod
    #Then mod is created successfully and "Pricing" menu is displayed
#
  #Scenario: Submit Discounts Mod at Contract Level: Review eMod successful for FPT contract
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #When I complete Manage Discounts details successfully
    #And I update the Pricing data for any new Vol/Qty Discount details at Contract Level
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@int @all_env
  #Scenario: Submit Discounts Mod at SIN Level: Review eMod successful for FPT contract
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #When I complete Manage Discounts details successfully
    #And I update the Pricing data for any new Vol/Qty Discount details at SIN Level
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@fileUpload
  #Scenario: Submit Discounts Mod at Line Item Level - Dollar Discount, no Zonal Pricing: Review eMod successful for FPT contract
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #When I complete Manage Discounts details successfully
    #And I provide Dollar discount details by Line Item in the Capture Pricing Data template
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@fileUpload
  #Scenario: Submit Discounts Mod at Line Item Level - Vol/Qty Discount, no Zonal Pricing: Review eMod successful for FPT contract
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #When I complete Manage Discounts details successfully
    #And I provide Vol/Qty discount details by Line Item in the Capture Pricing Data template
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@edi
  #Scenario: Submit Discounts Mod at Line Item Level - Vol/Qty Discount, no Zonal Pricing: Review eMod successful for FPT contract using EDI
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #When I complete Manage Discounts details successfully
    #And I provide Vol/Qty discount details by Line Item in the Capture Pricing Data template
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@wip
  #Scenario: Submit Discounts Mod at Line Item Level with Zonal Pricing: Review eMod successful for FPT contract
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #When I complete Manage Discounts details successfully
    #And I update the Pricing data for Line Item Discount in the Capture Pricing Data template
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@wip
  #Scenario: Submit Discounts Mod - Remove Discounts: Review eMod successful for FPT contract
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #When I complete Manage Discounts details successfully
    #And I remove Discounts option
    #And I complete all Pricing data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  # Not sure if you want separate Submit scenarios for all of the combinations since conditions would be the same
  #@gsaTest @wip
  #Scenario: Submit Create/Manage Discounts Mod: Submit eMod successful for FPT contract (GSA)
    #Given I am on the Final Review page in eMod for Create/Manage Discounts mod for FPT contract
    #When I submit my eMod
    #Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    #And Modification Id is displayed in my Submitted eMods list
#
  #@wip
  #Scenario: Discounts Mod:  Modify Discount Level from Line Item to Contract Level
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #And I have existing Discounts by Line Item
    #When I update Discounts to Contract Level
    #Then "Discount" details are required and corresponding menu is displayed
    #And "Capture Pricing" details are NOT required and corresponding menu is NOT displayed
#
  #@wip
  #Scenario: Discounts Mod:  Modify Discount Level from Contract to Line Item Level
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #And I have existing Discounts at the Contract Level
    #When I update Discounts to Line Item
    #Then "Capture Pricing" details are required and corresponding menu is NOT displayed
    #And "Discount" details are NOT required and corresponding menu is displayed
#
  #@wip @fpt
  #Scenario: Discounts Mod:  Modify Discount Level from Contract to SIN Level
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #And I have existing Discounts at the Contract Level
    #When I update Discounts to SIN Level
    #Then existing Discount details will be copied for all SINs
    #And I add any new Vol/Qty Discount details at SIN Level
#
  # Not sure if we want to reuse this step, as I believe it's slightly different validation
  #@wip
  #Scenario: Discounts Mod:  Modify Discount Level from SIN to Contract Level
    #Given I am on the Pricing eMod "Create/Manage Discounts" page
    #And I have existing Discounts at the SIN Level
    #When I update Discounts to Contract Level
    #Then existing Discount details will be deleted
    #And I add any new Vol/Qty Discount details at Contract Level
