Feature: Add Negotiator to Offer
  
  As a vendor
  I want to add a negotiator to the offer
  So that I can work the offer

  @all_env @smokeTest @int
  Scenario: Add Negotiator successful
    Given I am on the Add Negotiator Page
    When I add a Negotiator with all details
    #  All required fields in valid format
    Then the Negotiator is added and displayed with correct role in the Negotiator List

  @all_env
  Scenario: Complete Negotiator - Add Negotiator with Signature
    Given I am on the Add Negotiator Page
    When I add a Negotiator who is authorized to Sign
    Then the Negotiator is added and displayed with correct role in the Negotiator List
    And "Negotiators" menu is complete

  @all_env
  Scenario: Negotiator Incomplete - Add Negotiator not authorized to sign
    Given I am on the Add Negotiator Page
    When I add a Negotiator who is NOT authorized to Sign
    Then the Negotiator is added and displayed with correct role in the Negotiator List
    And "Negotiators" menu is incomplete

  @all_env
  Scenario: Add Negotiator fails - No username provided
    Given I am on the Add Negotiator Page
    When I add a Negotiator but DO NOT provide Name
    Then Error message is displayed: "Please Enter Name"

  @all_env
  Scenario: Add Negotiator fails - Invalid email address format
    Given I am on the Add Negotiator Page
    When I add a Negotiator but provide an INVALID email address
    Then Error message is displayed: "Please enter a valid Email"

  @all_env
  Scenario: Edit Negotiator successful
    Given I am on the Add Negotiator Page
    # Assumes 1 or more negotiator has been added but prefer to keep scenario simple
    And I add a Negotiator with all details
    When I edit a Negotiator in the Negotiator list
    #  Update name and/or role so can verify updates in the Negotiator list
    # Do we want to specifically identify fields to update in this step? And I update Negotiator Name
    Then the Negotiator is updated and displayed in the Negotiator List

  @all_env
  Scenario: Edit Negotiator fails - Invalid phone number format
    Given I am on the Add Negotiator Page
    And I add a Negotiator with all details
    When I update Negotiator details but provide an INVALID phone number format
    Then Error message is displayed: "Please enter a valid format for Phone"

  @all_env
  Scenario: Delete Negotiator successful
    Given I am on the Add Negotiator Page
    ##Adding 2 negotiators for this scenario
    And I add a Negotiator with all details
    When I delete a Negotiator in the Negotiator list
    Then the Negotiator is removed from the Negotiator List
#  If only 1 negotiator, it doesn't return to the Negotiator List page and goes to Add negotior
