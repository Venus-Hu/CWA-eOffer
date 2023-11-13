Feature: Change in Geographic Coverage or Scope Mod
  
  As a vendor
  I want to be able to update the products geographic coverage or scope
  So that contract can be updated

  @all_env
  Scenario: Change in Geographic Coverage or Scope Mod: Delete SIN successful
    Given I am on the Technical eMod "Change in Geographic Coverage (Scope)" page
    When I "select" SIN to update scope on the Update SIN page
    And I delete a SIN in the SIN list on Technical eMod "Change in Geographic Coverage (Scope)" page
    Then the SIN is removed from the SIN List on Technical eMod "Change in Geographic Coverage (Scope)" page

  @all_env @int
  Scenario: Submit Change in Geographic Coverage or Scope Mod: Review eMod successful
    Given I am on the Technical eMod "Change in Geographic Coverage (Scope)" page
    When I complete Geographic Coverage details by SIN successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Submit Change in Geographic Coverage or Scope Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Change in Geographic Coverage or Scope mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list

  @wip
  Scenario: Change in Geographic Coverage or Scope Mod
    Given I am on the Modifications List page
    When I create an "Change in Geographic Coverage (Scope)" mod
    Then mod is created successfully and "Technical" menu is displayed

  # It's like an Add, but not I don't want to refer to it that way
  @wip
  Scenario: Change in Geographic Coverage or Scope Mod: Select and Update SIN successful
    Given I am on the Technical eMod "Change in Geographic Coverage (Scope)" page
    When I "select" SIN to update scope on the Update SIN page
    Then the SIN details will be displayed on Technical eMod "Change in Geographic Coverage (Scope)" page

  @wip
  Scenario: Change in Geographic Coverage or Scope Mod: Edit SIN successful
    Given I am on the Technical eMod "Change in Geographic Coverage (Scope)" page
    When I "select" SIN to update scope on the Update SIN page
    And I "edit" SIN to update scope on the Update SIN page
    Then the SIN details will be displayed on Technical eMod "Change in Geographic Coverage (Scope)" page
