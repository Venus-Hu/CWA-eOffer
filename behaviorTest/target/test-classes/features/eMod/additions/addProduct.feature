Feature: Add Product mod
  
  As a vendor
  I want to add new products to my contract
  So that I can provide more products and services on my existing contract

  #Has CSP data to revise
  @all_env @smokeTest
  Scenario: Submit Add Product Mod: Review eMod successful for Non-FPT non-TDR contract
    Given I am on the Additions eMod "Add Product" page
    And I have the option to revise CSP for "Add Product" mod
    When I complete Add Product details successfully
    And I complete all data updates for non-FPT contract
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @negative @all_env @smokeTest
  Scenario: Add Product Mod: Verify not responding to Revise CSP gives error
    Given I am on the Additions eMod "Add Product" page
    And I have the option to revise CSP for "Add Product" mod
    When I complete Add Product details but do NOT Respond to revise CSP
    Then "Additions" menu is incomplete
    And Error message is displayed: "Please select Yes or No for Commercial Sales Practice."

  @wip
  Scenario: Add Product Mod
    Given I am on the Modifications List page
    When I create an "Add Product" mod
    Then mod is created successfully and "Additions" menu is displayed

  @wip
  Scenario: Submit Add Product Mod: Submit eMod successful for Non-FPT contract (GSA)
    Given I am on the Final Review page in eMod for Add Product mod for Non-FPT contract
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
