Feature: Add Labor Category and/or Service Offerings mod
  
  As a vendor
  I want to add new labor categories or service offerings to my contract
  So that I can expand opportunities on my existing contract

  # Say yes to revise CSP and complete CSP page
  @all_env @smokeTest
  Scenario: Submit Add Labor Category/Service Offerings Mod: Review eMod successful for Non-FPT contract - Revise CSP
    Given I am on the Additions eMod "Add Labor Category and/or Service Offerings" page
    And I have the option to revise CSP for "Add Labor Category" mod
    When I complete Add Labor Category and/or Service Offerings with revise CSP successfully
    And I have uploaded ALL REQUIRED Labor Category documents successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"

  @all_env
  Scenario: Submit Add Labor Category and/or Service Offerings Mod: Review eMod successful for Non-FPT contract - No CSP
    Given I am on the Additions eMod "Add Labor Category and/or Service Offerings" page
    And I have the option to revise CSP for "Add Labor" mod
    When I complete Add Labor Category and/or Service Offerings details without revise CSP
    Then "Additions" menu is incomplete
    And Error message is displayed: "Please select Yes or No for Commercial Sales Practice."

  @wip
  Scenario: Submit Add Labor Category and/or Service Offerings Mod: Submit eMod successful for Non-FPT contract (GSA)
    Given I am on the Final Review page in eMod for Add Labor Category and/or Service Offerings mod for Non-FPT contract
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list

  @wip
  Scenario: Add Labor Category and/or Service Offerings Mod
    Given I am on the Modifications List page
    When I create an "Add Labor Category and/or Service Offerings" mod
    Then mod is created successfully and "Additions" menu is displayed
    #@fpt
  #Scenario: Submit Add Labor Category/Service Offerings Mod: Review eMod successful for FPT contract
    #Given I am on the Additions eMod "Add Labor Category and/or Service Offerings" page
    #When I complete Add Labor Category and/or Service Offerings with revise CSP successfully
    #And I have uploaded ALL REQUIRED Labor Category documents successfully
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
