Feature: Solicitation Provisions:  SCA Matrix
  
  As a vendor
  I need to provide labor category information as part of the Services Contract Act Labor Matrix
  In order to comply with the Solicitation Provisions

  @all_env
  Scenario: Sol Provisions: SCA Matrix - Add Labor Category successful
    Given I am on the SCA Matrix Page
    When I add a SCA Labor Category with all details
    Then the SCA Labor Category is added and displayed with details in the SCA Labor Category Matrix

  @all_env
  Scenario: Sol Provisions: SCA Matrix - Add Labor Category fails - No SCA Equivalent Code Title provided
    Given I am on the SCA Matrix Page
    When I add a SCA Labor Category but DO NOT provide "SCA Equivalent Code Title"
    Then Error message is displayed: "Please provide SCA Equivalent Code Title"

  @all_env @int
  Scenario: Sol Provisions: Complete SCA Matrix successful
    Given I am on the SCA Matrix Page
    When I add at least one SCA Labor Category
    And the SCA Labor Category is added and displayed with details in the SCA Labor Category Matrix
    Then I submit the SCA Labor Category Matrix form
    And SCA Matrix provision is complete

  @all_env
  Scenario: Sol Provisions: Edit SCA Matrix successful
    Given I am on the Solicitation Provisions page
    And I have completed the SCA Matrix provision
    When I edit the SCA Matrix
    Then the SCA Matrix provision is updated

  # No saved message provided by app, may want to mention that to dev team for consistency
  @all_env
  Scenario: Sol Provisions: SCA Matrix - Edit Labor Category successful
    Given I am on the SCA Matrix Page
    And I add a SCA Labor Category with all details
    When I edit a SCA Labor Category in the SCA Labor Category Matrix with all details
    Then the SCA Labor Category is updated and displayed with details in the SCA Labor Category Matrix

  @all_env
  Scenario: Sol Provisions: SCA Matrix - Edit Labor Category fails - No SCA Eligible Contract Labor Category provided
    Given I am on the SCA Matrix Page
    And I add a SCA Labor Category with all details and submit form
    When I edit a SCA Labor Category in the SCA Labor Category Matrix but DO NOT provide "SCA Eligible Contract Labor Category"
    Then Error message is displayed: "Please provide SCA Eligible Contract Labor Category"

  @all_env
  Scenario: Sol Provisions: SCA Matrix - Delete Labor Category successful
    Given I am on the SCA Matrix Page
    And I add a SCA Labor Category with all details
    When I delete a SCA Labor Category in the SCA Labor Category Matrix
    Then the SCA Labor Category is deleted and removed from the SCA Labor Category Matrix
