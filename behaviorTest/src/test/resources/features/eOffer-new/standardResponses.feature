Feature: Complete Standard Responses for the Offer
  
  As a vendor
  I need to respond to Standard Responses section
  In order to comply with terms of the solicitation

  @all_env @int
  Scenario: Complete Standard Responses
    Given I am on the Standard Responses page
    When I complete ALL questions with valid responses
    Then Standard Responses menu is complete

  @all_env
  Scenario: Standard Responses Incomplete - No Disaster Recovery Purchasing Program
    Given I am on the Standard Responses page
    When I respond to questions but DO NOT answer "Disaster Recovery Purchasing Program" question
    Then Error message is displayed: "Please select value for Disaster Purchasing"

  @all_env
  Scenario: Standard Responses Incomplete - No Exceptions to Minimum Order Limit
    Given I am on the Standard Responses page
    When I respond to questions but DO NOT answer "Minimum Order Limit" question
    Then Error message is displayed: "Please select value for Minimum Order Limit"

  @all_env
  Scenario: Standard Responses - SubK Plan Required - For profit, domestic, not small bus, SubK work
    Given I am on the Standard Responses page
    When we are for profit, domestic, not a small business and there ARE opportunities for subcontracting work
    Then SubContracting Plan is required and SubK menu is displayed

  @all_env
  Scenario: Standard Responses - No SubK Plan Required - For profit, domestic, not small bus, no SubK work
    Given I am on the Standard Responses page
    When we are for profit, domestic, not a small business and there are NO opportunities for subcontracting work
    Then SubContracting Plan is NOT required and SubK menu is NOT displayed

  @all_env
  Scenario: Standard Responses - SubK Plan Required - For profit, foreign, locs in US, op in US, not small bus, SubK work
    Given I am on the Standard Responses page
    When we are for profit, foreign, have locations, and operations in US, not a small business and there ARE opportunities for subcontracting work
    Then SubContracting Plan is required and SubK menu is displayed

  @all_env
  Scenario: Standard Responses - No SubK Plan Required - Not-for-profit, no work in US
    Given I am on the Standard Responses page
    When we are not-for-profit, and no work on the contract will be performed in the US
    Then SubContracting Plan is NOT required and SubK menu is NOT displayed

  @all_env
  Scenario: Standard Responses - No Exceptions Required
    Given I am on the Standard Responses page
    When I DO NOT take exception to any of the Terms and Conditions
    Then Exceptions are NOT required and Exceptions menu is NOT displayed

  @all_env
  Scenario: Standard Responses - Exceptions Required
    Given I am on the Standard Responses page
    When I take exception to any of the Terms and Conditions
    Then Exceptions are required and Exceptions menu is displayed

  @fastLane @all_env
  Scenario: Standard Reponses - Add SIN for IT Category Only fastLane Required
    Given I am on the Available Offerings page
    When I add at least one SIN and complete Available Offerings details
    Then I am on the Standard Responses page
    Then I should have the option to select fastlane on Standard Responses page

  @fastLane @negative @all_env
  Scenario: Standard Responses - Add SIN for IT and Non IT Category fastLane Not Required
    Given I am on the Available Offerings page
    And I "add" SIN details on the Available Offerings page
    Then I am on the Standard Responses page
    Then I should not have the option to select fastlane on Standard Responses page

  @fastLane @all_env
  Scenario: Standard Responses - Add SIN for IT and ANCILLARY Only fastLane Required
    Given I am on the Available Offerings page
    And I "add" SIN details on the Available Offerings page
    Then I am on the Standard Responses page
    Then I should have the option to select fastlane on Standard Responses page

  @fastLane @negative @all_env
  Scenario: Standard Responses - Add SIN for IT and ANCILLARY and Non IT Category fastLane Not Required
    Given I am on the Available Offerings page
    And I "add" SIN details on the Available Offerings page
    Then I am on the Standard Responses page
    Then I should not have the option to select fastlane on Standard Responses page
