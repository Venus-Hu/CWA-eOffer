Feature:  Verify Corporate Information

As a vendor
I want to verify my Corporate Information is correct
So that I can ensure ffer data is valid
@int @all_env
  Scenario:  Corporate Information is correct
    Given I am on the Corporate Information Page
    When verify Corporate Information is correct
    And Save Corporate Information data
    Then Corporate Information menu is complete
	    
