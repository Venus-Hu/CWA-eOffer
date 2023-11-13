@obsolete
Feature: Address Change Mod
  
  As a vendor
  I want to be able to update my address or DUNS information
  So that contract can be updated with my new company information

  @all_env @smokeTest
  Scenario: Address Change Mod: Update UEI Number
    Given I am on the Administrative eMod "Address Change" page
    When I update the UEI Number

  #The Steps to verify Corporate Information is displayed for the new UEI flow should be the second step when update the UEI number.
  #Then Corporate Information is displayed for the new UEI
  @all_env @smokeTest
  Scenario: Submit Address Change Mod: Review eMod successful
    Given I am on the Administrative eMod "Address Change" page
    When I complete Address Change details successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Address Change Mod
    Given I am on the Modifications List page
    When I create an "Address Change" mod
    Then mod is created successfully and "Administrative" menu is displayed

  @wip
  Scenario: Submit Address Change Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Address Change mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
