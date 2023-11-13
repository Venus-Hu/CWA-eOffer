Feature: Cooperative Purchasing Mod
  
  As a vendor
  I want to identify products and services that may be utilized by multiple agencies
  So that cooperative purchasing agreements can be created

  @all_env @int
  Scenario: Submit Cooperative Purchasing Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Cooperative Purchasing" page
    When I complete Cooperative Purchasing details by SIN successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @all_env @int
  Scenario: Submit Cooperative Purchasing Mod: Edit Cooperative Purchasing Mod successful - GSA
    Given I am on the Terms and Conditions eMod "Cooperative Purchasing" page
    When I complete Cooperative Purchasing details by SIN successfully
    Then I return to eMod Page
    Then I edit the "Cooperative Purchasing"
    And I am on the Terms and Conditions eMod "Cooperative Purchasing" page

  @all_env @int
  Scenario: Submit Cooperative Purchasing Mod: Delete Cooperative Purchasing Mod successful - GSA
    Given I am on the Terms and Conditions eMod "Cooperative Purchasing" page
    When I complete Cooperative Purchasing details by SIN successfully
    Then I return to eMod Page
    Then I delete the "Cooperative Purchasing"

  @wip
  Scenario: Cooperative Purchasing Mod
    Given I am on the Modifications List page
    When I create an "Cooperative Purchasing" mod
    Then mod is created successfully and "Terms & Conditions" menu is displayed

  @wip
  Scenario: Submit Cooperative Purchasing Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Cooperative Purchasing mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
