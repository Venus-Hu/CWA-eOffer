#@fpt
#Feature: Capture Formatted Pricing -FPT Baseline mod
  #
  #As a vendor
  #I want to provide pricing adjustments to my existing contract
  #So that I can modify pricing related to changes in the economy
#
  #@wip
  #Scenario: Capture Formatted Pricing (FPT Baseline) Mod
    #Given I am on the Modifications List page
    #When I create an "Capture Formatted Pricing" mod
    #Then mod is created successfully and "Pricing" and "Administrative" menus are displayed
#
  #@int 
  #@fileUpload @all_env
  #Scenario: Submit Capture Formatted Pricing Mod: Review eMod successful for FPT contract
    #Given I am on the Pricing eMod "Baseline Pricing" page
    #When I complete Baseline Pricing details successfully
    #And I add Admin POC details
    #And I complete all Pricing data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
#
  #@gsaTest @wip
  #Scenario: Submit Capture Formatted Pricing Mod: Submit eMod successful for FPT contract (GSA)
    #Given I am on the Final Review page in eMod for Capture Formatted Pricing mod for FPT contract
    #When I submit my eMod
    #Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    #And Modification Id is displayed in my Submitted eMods list
#
  #@fileUpload @all_env
  #Scenario: Submit Capture Formatted Pricing Mod: Review eMod successful for FPT contract with Special Characters
    #Given I am on the Pricing eMod "Baseline Pricing" page
    #When I complete Baseline Pricing details successfully
    #And I add Admin POC details
    #And I complete all Pricing data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@formEntry @skip @all_env
  #Scenario: Submit Capture Formatted Pricing Mod: Review eMod successful for FPT contract with Special Characters using form entry
    #Given I am on the Pricing eMod "Baseline Pricing" page
    #When I complete Baseline Pricing details successfully
    #And I add Admin POC details
    #And I complete all Pricing data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
    #@edi
  #Scenario: Submit Capture Formatted Pricing Mod: Review eMod successful for EDI Upload
    #Given I am on the Pricing eMod "Baseline Pricing" page
    #When I complete Baseline Pricing details successfully
    #And I add Admin POC details
    #And I complete all Pricing data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod