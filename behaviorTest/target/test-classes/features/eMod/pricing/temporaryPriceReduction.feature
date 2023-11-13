Feature: Temporary Price Reduction mod
  
  As a vendor
  I want to provide temporary price reduction to my existing contract
  So that I can make price reductions but contract terms can stay in effect

  @int @all_env
  Scenario: Submit Temporary Price Reduction Mod: Review eMod successful for Non-FPT contract
    Given I am on the Pricing eMod "Temporary Price Reduction" page
    When I complete Temporary Price Reduction details successfully
    And I complete all data updates for non-FPT contract
    And all menu items are complete for eMod
    Then I can perform Final Review of my eMod

  #@fileUpload @fpt
  #Scenario: Submit Temporary Price Reduction Mod: Review eMod successful for FPT contract
  #Given I am on the Pricing eMod "Temporary Price Reduction" page
  #When I complete Temporary Price Reduction details successfully
  #And I complete all Pricing data updates for FPT contract
  #And all menu items are complete for eMod
  #Then I can perform Final Review of my eMod
  #
  #@edi @fpt
  #Scenario: Submit Temporary Price Reduction Mod: Review eMod successful using EDI
  #Given I am on the Pricing eMod "Temporary Price Reduction" page
  #When I complete Temporary Price Reduction details successfully
  #And I complete all Pricing data updates for FPT contract
  #And all menu items are complete for eMod
  #Then I can perform Final Review of my eMod
  #
  #@gsaTest @wip @fpt
  #Scenario: Submit Temporary Price Reduction Mod: Submit eMod successful for FPT contract (GSA)
  #Given I am on the Final Review page in eMod for Temporary Price Reduction mod for FPT contract
  #When I submit my eMod
  #Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
  #And Modification Id is displayed in my Submitted eMods list
  @wip
  Scenario: Temporary Price Reduction Mod
    Given I am on the Modifications List page
    When I create an "Temporary Price Reduction" mod
    Then mod is created successfully and "Pricing" menu is displayed

  @wip
  Scenario: Submit Temporary Price Reduction Mod: Submit eMod successful for Non-FPT contract (GSA)
    Given I am on the Final Review page in eMod for Temporary Price Reduction mod for Non-FPT contract
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
