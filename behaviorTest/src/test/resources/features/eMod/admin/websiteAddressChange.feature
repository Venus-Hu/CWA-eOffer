Feature: Website Address Change Mod
  
  As a vendor
  I want to be able to update my website address
  So that contract can be updated with my new website address

  @all_env
  Scenario: Submit Website Address Change Mod: Review eMod successful
    Given I am on the Administrative eMod "Website Address Change" page
    When I update my website URL successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Website Address Change Mod
    Given I am on the Modifications List page
    When I create an "Website Address Change" mod
    Then mod is created successfully and "Administrative" menu is displayed

  @wip
  Scenario: Submit Website Address Change Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Website Address Change mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
