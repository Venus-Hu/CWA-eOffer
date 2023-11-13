Feature: Part Number Change Mod
  
  As a vendor
  I want to be able to update the part(s) number
  So that contract can be updated

  @all_env
  Scenario: Submit Part Number Change Mod: Review eMod successful
    Given I am on the Technical eMod "Part(s) Number Change" page
    When I complete Part Number Change details successfully
    And I have uploaded ALL REQUIRED Supporting Documentation successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Part Number Change Mod
    Given I am on the Modifications List page
    When I create an "Part(s) Number Change" mod
    Then mod is created successfully and "Technical" menu is displayed

  @wip
  Scenario: Submit 	Part Number Change Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Part Number Change mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
