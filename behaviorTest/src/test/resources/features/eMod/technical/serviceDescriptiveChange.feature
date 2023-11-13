Feature: Service Descriptive Change Mod
  
  As a vendor
  I want to be able to update the service description
  So that contract can be updated

  @all_env
  Scenario: Submit Service Descriptive Change Mod: Review eMod successful
    Given I am on the Technical eMod "Service Descriptive" page
    When I complete Service Descriptive Change details successfully
    And I have uploaded ALL REQUIRED Supporting Documentation successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Submit 	Service Descriptive Change Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Service Descriptive Change mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list

  @wip
  Scenario: Service Descriptive Change Mod
    Given I am on the Modifications List page
    When I create an "Service Descriptive" mod
    Then mod is created successfully and "Technical" menu is displayed
