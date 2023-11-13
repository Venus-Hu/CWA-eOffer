Feature: Cancel Contract Mod
  
  As a vendor
  I want to be able to cancel or terminate my contract
  So that my contract can be terminated

  Scenario: Cancel Contract Mod
    Given I am on the Modifications List page
    When I select mod types "Cancel Contract"
    Then mod is created successfully and "Cancellation or Termination" menu is displayed

   @all_env @int
  Scenario: Submit Cancel Contract Mod: Review eMod successful
    Given I am on the Cancellations Or Terminations eMod "Cancel Contract" page
    When I complete Cancel Contract details successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Submit Cancel Contract Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Cancel Contract mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list  
    