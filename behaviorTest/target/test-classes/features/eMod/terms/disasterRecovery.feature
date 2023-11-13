Feature: Disaster Recovery Mod
  
  As a vendor
  I want to identify if I will participate in the Recovery Purchasing Program
  So that contract can be amended correctly to utilize these funds

  @all_env
  Scenario: Submit Disaster Recovery Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Disaster Recovery" page
    When I complete Disaster Recovery details successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Submit Disaster Recovery Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Disaster Recovery mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list

  @wip
  Scenario: Disaster Recovery Mod
    Given I am on the Modifications List page
    When I create an "Disaster Recovery" mod
    Then mod is created successfully and "Terms & Conditions" menu is displayed
