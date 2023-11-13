Feature: Delete Labor Category and/or Service Offerings mod
  
  As a vendor
  I want to delete labor categories or service offerings to my contract
  So that I can update my existing contract

  @all_env
  Scenario: Submit Delete Labor Category/Service Offerings Mod: Review eMod successful for Non-FPT contract
    Given I am on the Deletions eMod "Delete Labor Category and/or Service Offerings" page
    When I complete Delete Labor Category and/or Service Offerings details successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Delete Labor Category and/or Service Offerings Mod
    Given I am on the Modifications List page
    When I create an "Delete Labor Category and/or Service Offerings" mod
    Then mod is created successfully and "Deletions" menu is displayed

  @wip
  Scenario: Submit Delete Labor Category and/or Service Offerings Mod: Submit eMod successful for Non-FPT contract (GSA)
    Given I am on the Final Review page in eMod for Delete Labor Category and/or Service Offerings mod for Non-FPT contract
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
    #@fpt
  #Scenario: Submit Delete Labor Category/Service Offerings Mod: Review eMod successful for FPT contract
    #Given I am on the Deletions eMod "Delete Labor Category and/or Service Offerings" page
    #When I complete Delete Labor Category and/or Service Offerings details successfully
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
