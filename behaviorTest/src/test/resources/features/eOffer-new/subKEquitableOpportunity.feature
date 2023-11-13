Feature: SubContracting Plan - Equitable Opportunity
  
  As a vendor who will provide subcontracting opportunities
  I want to provide description of our efforts to address small business concerns
  In order to provide an equitable opportunity to compete for subcontracts

  @all_env
  Scenario: SubK:  Equitable Opportunity Outreach Efforts - Select All
    Given I am on the SubContracting Plan "Equitable Opportunity" page for a "New Individual Plan"
    When I choose the "Select All" option to select ALL Outreach Efforts options
    Then all of the Outreach Efforts options will be "selected"

  @all_env
  Scenario: SubK:  Equitable Opportunity Outreach Efforts - Select/De-select All
    Given I am on the SubContracting Plan "Equitable Opportunity" page for a "New Individual Plan"
    When I choose the "Select All" option to select ALL Outreach Efforts options
    And I choose the "Select All" option to de-select ALL Outreach Efforts options
    Then all of the Outreach Efforts options will be "de-selected"

  @all_env
  Scenario: SubK:  Equitable Opportunity Outreach Efforts - Clear All
    Given I am on the SubContracting Plan "Equitable Opportunity" page for a "New Individual Plan"
    When I choose the "Select All" option to select ALL Outreach Efforts options
    When I choose the "Clear All" option to de-select ALL Outreach Efforts options
    Then all of the Outreach Efforts options will be "de-selected"

  @all_env
  Scenario: SubK:  Equitable Opportunity Internal Efforts - Select All
    Given I am on the SubContracting Plan "Equitable Opportunity" page for a "New Individual Plan"
    When I choose the "Select All" option to select ALL Internal Efforts options
    Then all of the Internal Efforts options will be "selected"

  @all_env
  Scenario: SubK:  Equitable Opportunity Internal Efforts - Select/De-select All
    Given I am on the SubContracting Plan "Equitable Opportunity" page for a "New Individual Plan"
    When I choose the "Select All" option to select ALL Internal Efforts options
    And I choose the "Select All" option to de-select ALL Internal Efforts options
    Then all of the Internal Efforts options will be "de-selected"

  @all_env
  Scenario: SubK:  Equitable Opportunity Internal Efforts - Clear All
    Given I am on the SubContracting Plan "Equitable Opportunity" page for a "New Individual Plan"
    When I choose the "Select All" option to select ALL Internal Efforts options
    When I choose the "Clear All" option to de-select ALL Internal Efforts options
    Then all of the Internal Efforts options will be "de-selected"
