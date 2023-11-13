Feature: Participate in TDR Mod
  
  As a vendor
  When I have a non-TDR contract on a TDR schedule
  I can incite this mod to have my contract participate in TDR

  @all_env
  Scenario: Create Participate in TDR Mod
    Given I am on the Modifications List page
    When I select mod types "Participate in TDR"
    Then mod is created successfully and "Corporate Information" menu is displayed

  @int @all_env
  Scenario: Submit Participate in TDR Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Participate in TDR" page
    When I respond to the prompt to participate in TDR
    And I accept verification to participate in TDR
    Then all menu items are complete
    And I can perform Final Review of my eMod

	#the last steps will not be implemented as it removes the reusability of the data set. 
	#In the case, submission is required, run scenario and manually submit offer/mod
  @all_env
  Scenario: Submit Participate in TDR Mod: Review eMod successful (GSA)
    Given I am on the Terms and Conditions eMod "Participate in TDR" page
    When I respond to the prompt to participate in TDR
    And I accept verification to participate in TDR
    Then all menu items are complete
    And I can perform Final Review of my eMod
    #When I submit my eMod
    #Then eMod is submitted and message is displayed:  "SUCCESS"
    #And Modification Id is displayed in my Submitted eMods list
    
  @negative @all_env
  Scenario: Participate in TDR Mod - Error when continuing without participating in TDR
    Given I am on the Terms and Conditions eMod "Participate in TDR" page
    When I do NOT respond to the prompt to participate in TDR
    Then Error message is displayed: "Please complete the Participate in TDR"

  @negative @all_env
  Scenario: Participate in TDR Mod is unavailable - Contract already TDR
    Given I am on the Modifications List page
    When I navigate to Mod Types page 
    Then "Participate in TDR" mod is not displayed

  @negative @all_env
  Scenario: Participate in TDR Mod is unavailable - Schedule not TDR
    Given I am on the Modifications List page
    When I navigate to Mod Types page 
    Then "Participate in TDR" mod is not displayed

  @negative @all_env
  Scenario: Participate in TDR Mod: Left nav incomplete
    Given I am on the Terms and Conditions eMod "Participate in TDR" page
    When I respond to the prompt to participate in TDR but do NOT save the response
    And I submit the Participate in TDR mod without accepting verification
    Then the submission page throws the following error "Please complete all required information indicated in the left eMod menu before submitting this modification."
