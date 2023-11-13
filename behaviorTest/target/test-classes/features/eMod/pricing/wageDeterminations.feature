@a1
Feature: Wage Determinations mod
  
  As a vendor
  I want to provide pricing adjustments to my existing contract
  So that I can modify pricing related to wage determinations

  @all_env
  Scenario: Submit Wage Determinations Mod: Review eMod successful for Non-FPT contract
    Given I am on the Pricing eMod "Wage Determinations" page
    # For other pricing mods are you handling CSP as part of this statement?
    When I complete Wage Determinations details successfully
    And I have uploaded ALL REQUIRED Wage Determination documents successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @negative @all_env
  Scenario: Submit Wage Determinations Mod: Verify not responding to Revise CSP gives error
    Given I am on the Pricing eMod "Wage Determinations" page
    And I have the option to revise CSP for "Wage Determinations" mod
    When I complete Wage Determinations details successfully
    Then "Pricing" menu is incomplete
    And Error message is displayed: "Please select Yes or No for Commercial Sales Practice."

  @wip
  Scenario: Wage Determinations Mod
    Given I am on the Modifications List page
    When I create an "Wage Determinations" mod
    Then mod is created successfully and "Pricing" menu is displayed

  @wip
  Scenario: Submit Wage Determinations Mod: Submit eMod successful for Non-FPT contract (GSA)
    Given I am on the Final Review page in eMod for Wage Determinations mod for Non-FPT contract
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
