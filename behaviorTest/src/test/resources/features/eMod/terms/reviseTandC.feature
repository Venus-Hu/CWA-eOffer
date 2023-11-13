Feature: Revise Terms and Conditions Mod
  
  As a vendor
  I want to revise terms and conditions on my contract
  So that I can modify specific clauses without creating a new contract

  # While CSP is essentially same idea as other clauses, I think it would be good to separate it
  @all_env @int
  Scenario: Submit Revise Terms and Conditions CSP Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Revise Terms and Conditions" page
    When I select and complete Commercial Sales Practice clause
    And I complete all data updates for non-FPT contract
    And all menu items are complete
    Then I can perform Final Review of my eMod

  # Writing scenario to assume that data file will have 1:M Clauses to add (similar to eOffer solClausesBasicInfo
  # but I am open to discussion of other options as well
  #@int
  Scenario: Submit Revise Terms and Conditions Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "Revise Terms and Conditions" page
    When I add and complete multiple Clauses for Terms and Conditions
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Submit Revise Terms and Conditions Mod: Submit eMod successful (GSA)
    Given I am on the Final Review page in eMod for Revise Terms and Conditions mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list

  @wip
  Scenario: Revise Terms and Conditions Mod
    Given I am on the Modifications List page
    When I create an "Revise Terms and Conditions" mod
    Then mod is created successfully and "Terms & Conditions" menu is displayed
