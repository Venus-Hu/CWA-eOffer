Feature: Economic Price Adjustments with Commercial Price List increase mod
  
  As a vendor
  I want to provide pricing adjustments to my existing contract
  So that I can modify pricing related to changes in the economy and also provide a Commercial Price List (increase)

  @all_env
  Scenario: Submit EPA with CPL Mod: Review eMod successful for Non-FPT contract
    Given I am on the Pricing eMod "EPA with CPL" page
    When I complete EPA with CPL details successfully
    And I complete all data updates for non-FPT contract
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: EPA with CPL Mod
    Given I am on the Modifications List page
    When I create an "EPA with CPL" mod
    Then mod is created successfully and "Pricing" menu is displayed
  #@all_env @fileUpload @fpt
  #Scenario: Submit EPA with CPL Mod: Review eMod successful for FPT contract
    #Given I am on the Pricing eMod "EPA with CPL" page
    #When I complete EPA with CPL details successfully
    #And I complete all Pricing data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@edi @fpt
  #Scenario: Submit EPA with CPL Mod: Review eMod successful for FPT contract using EDI
    #Given I am on the Pricing eMod "EPA with CPL" page
    #When I complete EPA with CPL details successfully
    #And I complete all Pricing data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #FPT-8432
  #@fileUpload @fpt
  #Scenario: Submit EPA with CPL Mod: Review eMod successful for FPT contract with invalid Characters
    #Given I am on the Pricing eMod "EPA with CPL" page
    #When I complete EPA with CPL details successfully
    #And I complete all Pricing data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@gsaTest @wip @fpt
  #Scenario: Submit EPA with CPL Mod: Submit eMod successful for FPT contract (GSA)
    #Given I am on the Final Review page in eMod for EPA with CPL mod for FPT contract
    #When I submit my eMod
    #Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    #And Modification Id is displayed in my Submitted eMods list
#
  #@gsaTest @wip @nonFPT
  #Scenario: Submit EPA with CPL Mod: Submit eMod successful for Non-FPT contract (GSA)
    #Given I am on the Final Review page in eMod for EPA with CPL mod for Non-FPT contract
    #When I submit my eMod
    #Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    #And Modification Id is displayed in my Submitted eMods list
