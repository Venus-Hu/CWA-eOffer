Feature: Novation Agreement Mod
  
  As a vendor
  I want to be able to provide anovation agreement
  In order to re-assign terms of my contract

  # Depending on answers, different questions or asked and different upload doc requirements
  # In future, I'll add various scenarios
  @all_env @int
  Scenario: Submit Novation Agreement Mod: Review eMod successful
    Given I am on the Legal eMod "Novation Agreement" page
    When I complete Novation Agreement details successfully
    # Do you want to share this step with other Legal mods or separate step
    And I have uploaded ALL REQUIRED Legal documents successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Novation Agreement Mod
    Given I am on the Modifications List page
    When I create an "Novation Agreement" mod
    Then mod is created successfully and "Legal" menu is displayed

  @wip
  Scenario: Novation Agreement Mod: Update DUNS Number
    Given I am on the Legal eMod "Novation Agreement" page
    When I update the DUNS Number
    Then Corporate Information is displayed for the new DUNS

  @wip
  Scenario: Submit Novation Agreement Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Novation Agreement mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
