Feature: Clause exceptions for an Offer
  
  As a vendor who wants to create an offer on a schedule
  	 I want to add some exceptions
  	 So that I can customize my offer

@all_env 
  Scenario: Clause exceptions - Complete exceptions successful
    Given I am on the exceptions page
    When I select and complete a clause exception for an offer
    Then "Exceptions" menu is complete
