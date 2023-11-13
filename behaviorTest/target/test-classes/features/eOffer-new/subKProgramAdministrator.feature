Feature: SubContracting Plan - Program Administrator
  
  As a vendor who will provide subcontracting opportunities
  I want to provide description of our efforts to address small business concerns
  In order to provide an Program Administrator to compete for subcontracts

  @all_env
  Scenario: SubK:  Program Administrator Duties - Select All
    Given I am on the SubContracting Plan "Program Administrator" page for a "New Individual Plan"
    When I choose the "Select All" option to select ALL Program Administrator Duties
    Then all of the Duties options will be "selected"

  @all_env
  Scenario: SubK:  Program Administrator Duties - Select/De-select All
    Given I am on the SubContracting Plan "Program Administrator" page for a "New Individual Plan"
    When I choose the "Select All" option to select ALL Program Administrator Duties
    And I de-select the "Select All" option to de-select ALL Program Administrator Duties
    Then all of the Duties options will be "de-selected"

  @all_env
  Scenario: SubK:  Program Administrator Duties - Clear All
    Given I am on the SubContracting Plan "Program Administrator" page for a "New Individual Plan"
    When I choose the "Select All" option to select ALL Program Administrator Duties
    And I de-select the "Clear All" option to de-select ALL Program Administrator Duties
    Then all of the Duties options will be "de-selected"
