Feature: Re-representation of Business Size Mod
  
  As a vendor
  I need to notify GSA when there is a change to my business size
  So that I can determine if the change will affect contract terms

  @all_env @int
  Scenario: Submit Re-representation of Business Size Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Re-representation of Business Size" page
    When I complete Re-representation of Business Size details successfully
    And I have uploaded ALL REQUIRED Re-Representation documents successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Submit Re-representation of Business Size Mod: Submit eMod successful (GSA)
    Given I am on the Final Review page in eMod for Re-representation of Business Size mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list

  @wip
  Scenario: Re-representation of Business Size Mod
    Given I am on the Modifications List page
    When I create an "Re-representation of Business Size" mod
    Then mod is created successfully and "Terms & Conditions" menu is displayed
