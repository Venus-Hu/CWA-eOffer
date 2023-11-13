Feature: Change of Name Agreement Mod
  
  As a vendor
  I want to be able to update my company name
  So that my contract can be updated but contractor's rights and obligations remain unaffected

  @all_env @int
  Scenario: Submit Change of Name Agreement Mod: Review eMod successful
    Given I am on the Legal eMod "Change of Name Agreement" page
    When I complete Change of Name Agreement details successfully
    And I have uploaded ALL REQUIRED Legal documents successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Change of Name Agreement Mod
    Given I am on the Modifications List page
    When I create an "Change of Name Agreement" mod
    Then mod is created successfully and "Legal" menu is displayed

  @wip
  Scenario: Change of Name Agreement Mod: Update DUNS Number
    Given I am on the Legal eMod "Change of Name Agreement" page
    When I update the DUNS Number
    Then Corporate Information is displayed for the new DUNS

  @wip
  Scenario: Submit Change of Name Agreement Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Change of Name Agreement mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
