Feature: Incorporate Sub-Contracting Plan or SubK Mod
  
  As a vendor
  I want to identify Sub-Contracting Plan information
  So that contract can be amended with correct subcontracting details

  @all_env
  Scenario: Submit Sub-Contracting Plan - Existing Master Plan Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Sub-Contracting Plan" page
    When I select an "Existing Master Plan" that has been approved for the contract
    And I am only required to provide Goals and size standards information for the SubContracting Plan
    And I am required to upload "Pre-Approved Master Plan" document in the Uploads section
    And I have uploaded ALL REQUIRED documents successfully
    Then I can perform Final Review of my eMod

  @all_env
  Scenario: Submit Sub-Contracting Plan - New Individual Plan Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Sub-Contracting Plan" page
    When I select a "New Individual Plan" for subcontracting
    And I am required to complete all ten sections of SubContracting Plan information
    Then I can perform Final Review of my eMod

  @all_env
  Scenario: Submit Sub-Contracting Plan - Existing Commercial Plan Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Sub-Contracting Plan" page
    When I select a "Existing Commercial Plan" for subcontracting
    And I am only required to provide Goals and size standards information for the SubContracting Plan
    And I am required to upload "Pre-Approved Commercial Plan" document in the Uploads section
    And I have uploaded ALL REQUIRED documents successfully
    Then I can perform Final Review of my eMod

  @all_env
  Scenario: Submit Sub-Contracting Plan - New Commercial Plan Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Sub-Contracting Plan" page
    When I select a "New Commercial Plan" for subcontracting
    And I am required to complete all ten sections of SubContracting Plan information
    Then I can perform Final Review of my eMod

  @negative @all_env
  Scenario: Sub-Contracting Mod with Master Plan: Missing Identification data
    Given I am on the Terms and Conditions eMod "Sub-Contracting Plan" page
    When I select an "Existing Master Plan" that has been approved for the contract
    And I do NOT add required data for "Identification" in subcontracting plan
    Then Error message is displayed: "Start Date is a required field. You must enter the date on which the subcontracting plan will take effect before you may continue (Error Code 201104)."

  @negative @all_env @skip
  Scenario: Sub-Contracting Mod with Master Plan: Missing tab data
    Given I am on the Terms and Conditions eMod "Sub-Contracting Plan" page
    When I select an "Existing Master Plan" that has been approved for the contract
    And I do NOT add required data for "Assignment of Size Standards to Subcontracts" in subcontracting plan
    Then Error message is displayed: "901110 - Must agree to the Terms."

  @negative @all_env
  Scenario: Sub-Contracting Mod- New Commercial: missing data
    Given I am on the Terms and Conditions eMod "Sub-Contracting Plan" page
    When I select an "New Commercial Plan" that has been approved for the contract
    And I do NOT add required data for "Assurances" in subcontracting plan
    Then Error message is displayed: "901110 - Must agree to all Terms to move forward."

  @wip
  Scenario: Sub-Contracting Plan or SubK Mod
    Given I am on the Modifications List page
    When I create an "Incorporate Sub-Contracting Plan (SubK)" mod
    Then mod is created successfully and "Terms & Conditions" menu is displayed

  # Not sure if you need/want all SubK types here
  @wip
  Scenario: Submit Sub-Contracting Plan or SubK Mod: Submit eMod successful
    Given I am on the Final Review page in eMod for Sub-Contracting Plan mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
