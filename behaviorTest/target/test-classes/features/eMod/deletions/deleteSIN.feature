Feature: Delete SIN Mod
  
  As a vendor
  I want to delete existing SINs on my contract
  So that I can remove products and services that I do not offer anymore

  
  @all_env @int
  Scenario: Submit Delete SIN Mod: Review eMod successful for Non-FPT contract
    Given I am on the Deletions eMod "Delete SIN" page
    When I complete Delete SIN details successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod
    
  @wip
  Scenario: Delete SIN Mod
    Given I am on the Modifications List page
    When I create an "Delete SIN" mod
    Then mod is created successfully and "Deletions" menu is displayed
    

  @wip
  Scenario: Submit Delete SIN Mod: Submit eMod successful for Non-FPT contract (GSA)
    Given I am on the Final Review page in eMod for Delete SIN mod for Non-FPT contract
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list  
    
    
   #@fpt
  #Scenario: Submit Delete SIN Mod: Review eMod successful for FPT contract
    #Given I am on the Deletions eMod "Delete SIN" page
    #When I complete Delete SIN details successfully
    #And I complete all Pricing data updates for FPT contract
    #And all menu items are complete
    #Then I can perform Final Review of my eMod

###  IM NOT SURE IF FPT IS REALLY DIFFERENT FOR DELETE SIN
  #@gsaTest @wip @fpt
  #Scenario: Submit Delete SIN Mod: Submit eMod successful for FPT contract (GSA)
    #Given I am on the Final Review page in eMod for Delete SIN mod for FPT contract
    #When I submit my eMod
    #Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    #And Modification Id is displayed in my Submitted eMods list  
   