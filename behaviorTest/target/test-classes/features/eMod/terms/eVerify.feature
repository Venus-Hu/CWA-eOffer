Feature: E-Verify Mod
  
  As a vendor
  I want to identify if I will be using the U.S. Citizenship and Immigration Services’ E-Verify system 
  In order to comply with FAR, Clause 52.222-54, Employment Eligibility Verification Jan 2009

  @all_env
  Scenario: Submit E-Verify Mod: Review eMod successful
    Given I am on the Terms and Conditions eMod "E-Verify" page
    When I complete E-Verify details successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Submit E-Verify Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for E-Verify mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list

  @wip
  Scenario: E-Verify Mod
    Given I am on the Modifications List page
    When I create an "E-Verify" mod
    Then mod is created successfully and "Terms & Conditions" menu is displayed
