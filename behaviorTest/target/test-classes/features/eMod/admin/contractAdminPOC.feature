Feature: Contract Administrator/Point of Contact or POC Mod
  
  As a vendor
  I want to be able to update my address or DUNS information
  So that contract can be updated with my new company information

  @all_env @int
  Scenario: Submit Contract Administrator/Point of Contact or POC Mod: Review eMod successful
    Given I am on the Administrative eMod "Contract Administrator/Point of Contact (POC)" page
    When I update Point of Contact Name successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Contract Administrator/Point of Contact or POC Mod
    Given I am on the Modifications List page
    When I create an "Contract Administrator/Point of Contact (POC)" mod
    Then mod is created successfully and "Administrative" menu is displayed

  @wip
  Scenario: Submit Contract Administrator/Point of Contact or POC Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Contract Administrator/Point of Contact mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
    
  #@fpt
  #Scenario: Submit Contract Administrator/Point of Contact or POC Mod for FPT contract: Review eMod successful
    #Given I am on the Administrative eMod "Contract Administrator/Point of Contact (POC)" page
    #When I complete Contract Admin POC details successfully
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
