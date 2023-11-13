Feature: Telephone Change Mod
  
  As a vendor
  I want to be able to update my Telephone
  So that contract can be updated with my new Telephone number

  @all_env
  Scenario: Submit Telephone Change Mod: Review eMod successful
    Given I am on the Administrative eMod "Telephone Change" page
    When I update Telephone number successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Telephone Change Mod
    Given I am on the Modifications List page
    When I create an "Telephone Change" mod
    Then mod is created successfully and "Administrative" menu is displayed

  @wip
  Scenario: Submit Telephone Change Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Telephone Change mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
