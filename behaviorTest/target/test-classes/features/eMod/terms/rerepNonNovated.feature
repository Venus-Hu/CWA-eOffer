Feature: Re-representation of Non-Novated Merger/Acquisition Mod
  
  As a vendor
  I need to notify GSA when there is a merger or acquisition that may affect my existing contract
  So that I can determine if the merger/acquisition will affect contract terms

  @all_env @int
  Scenario: Submit Re-representation of Non-Novated Merger/Acquisition Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Re-representation of Non-Novated Merger/Acquisition" page
    When I complete Re-representation of Non-Novated Merger/Acquisition details successfully
    And I have uploaded ALL REQUIRED Re-Representation documents successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Submit Re-representation of Non-Novated Merger/Acquisition Mod: Submit eMod successful (GSA)
    Given I am on the Final Review page in eMod for Re-representation of Non-Novated Merger/Acquisition mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list

  @wip
  Scenario: Re-representation of Non-Novated Merger/Acquisition Mod
    Given I am on the Modifications List page
    When I create an "Re-representation of Non-Novated Merger/Acquisition" mod
    Then mod is created successfully and "Terms & Conditions" menu is displayed
