Feature: Select DUNS
  
  As a vendor
  I want to select my DUNS Number
  So that I can create an offer for my business

  @positive @all_env @int
  Scenario: Select valid UEI successful
    Given I am on the UEI page
    When I provide a valid UEI Number
    Then My Offers page displays my saved and submitted offers

  @negative @all_env
  Scenario: Select UEI fails - No UEI provided
    Given I am on the UEI page
    When I provide a empty UEI Number
    Then Error message "201099 - Please verify that the submission contains exactly 12 alphanumeric characters for UEI. Please do not enter any spaces, or special characters."  is displayed on UEI page

  @negative @all_env
  Scenario: Select UEI fails - Invalid UEI provided
    Given I am on the UEI page
    When I provide an invalid UEI Number
    Then Error message "201099 - Please verify that the submission contains exactly 12 alphanumeric characters for UEI. Please do not enter any spaces, or special characters."  is displayed on UEI page
  #Need to find new data for this scenario
  @negative @all_env
  Scenario: Select UEI fails - valid UEI provided with SAM UEI missing
    Given I am on the UEI page
    When I provide an valid UEI Number
    Then Error message "201099 - Please verify that the submission contains exactly 12 alphanumeric characters for UEI. Please do not enter any spaces, or special characters."  is displayed on UEI page
