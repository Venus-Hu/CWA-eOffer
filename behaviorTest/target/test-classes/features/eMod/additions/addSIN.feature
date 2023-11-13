Feature: Add SIN Mod
  
  As a vendor
  I want to add new SINs to my contract
  So that I can provide more products and services on my existing contract

  @all_env @mcc @int
  Scenario: Submit Add SIN Mod: Review eMod successful for Non-FPT contract
    Given I am on the Additions eMod "Add SIN" page
    When I complete Add SIN details successfully
    And I complete all data updates for non-FPT contract
    And all menu items are complete
    Then I can perform Final Review of my eMod

  ## Contract is non FPT non TDR. SIN does not participate in TDR
  ## Schedule SINs have sinTdrFlag=Y. So popup should appear
  @all_env @tdr @mcc @smokeTest
  Scenario: Add SIN Mod: Complete Additions for Non-FPT Non-TDR contract
    Given I am on the Additions eMod "Add SIN" page
    And I have the option to revise CSP for "Add SIN" mod
    When I add at least one SIN as part of the mod
    And I Revise CSP successfully
    Then the SIN details will be displayed on eMod Add SIN page
    And "Additions" menu is complete

  @negative @mcc @all_env
  Scenario: Add SIN Mod: Verify not responding to Revise CSP gives error
    Given I am on the Additions eMod "Add SIN" page
    And I have the option to revise CSP for "Add SIN" mod
    When I add at least one SIN as part of the mod but do NOT respond to revise CSP
    Then "Additions" menu is incomplete
    And Error message is displayed: "Please revise Commercial Sales Practice."

  @fastLane @smokeTest
  Scenario: Add SIN Mod: FastLane offer as No and awarded in reporting office Z
    Given I am on the Additions eMod "Add SIN" page
    And I have the option to revise CSP for "Add SIN" mod
    When I complete Add SIN details successfully
    Then I should have the option to select fastlane on Additions page
    And "Additions" menu is complete

  @fastLane @smokeTest
  Scenario: Add SIN Mod: FastLane offer as Yes and awarded in reporting office Z
    Given I am on the Additions eMod "Add SIN" page
    And I have the option to revise CSP for "Add SIN" mod
    When I complete Add SIN details successfully
    Then I should not have the option to select fastlane on Additions page
    And "Additions" menu is complete

  @fastLane @smokeTest
  Scenario: Add SIN Mod: None-FastLane offer and awarded in reporting office Z
    Given I am on the Additions eMod "Add SIN" page
    When I complete Add SIN details successfully
    Then I should have the option to select fastlane on Additions page
    And "Additions" menu is complete

  @wip
  Scenario: Add SIN Mod
    Given I am on the Modifications List page
    When I create an "Add SIN" mod
    Then mod is created successfully and "Additions" menu is displayed

  @wip
  Scenario: Submit Add SIN Mod: Submit eMod successful for Non-FPT contract
    Given I am on the Final Review page in eMod for Add SIN mod for Non-FPT contract
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
