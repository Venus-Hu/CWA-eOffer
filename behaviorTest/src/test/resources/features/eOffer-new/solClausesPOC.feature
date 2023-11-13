Feature: Solicitation Clauses:  Point of Contacts Information
  
  As a vendor
  I need to provide Point of Contacts Information as part of Solicitation Clauses
  In order to provide various contacts' details associated with the offer

  @int @all_env
  Scenario: Sol Clauses: Add POC successful
    Given I am on the Point of Contact Information Page
    When I add a Point of Contact with all details
    Then the POC is added and displayed with correct description in the POC List
    And verify POC does not have any special characters

  @positive @all_env
  Scenario: Sol Clauses: Edit POC successful
    Given I am on the Point of Contact Information Page
    And I add a Point of Contact with all details
    When I edit a POC in the POC list
    #  Update name so can verify updates in the POC list
    #  Do we want to specifically identify fields to update in this step? And I update POC Name - No that is not needed.
    ## We are checking functionality and as long as we validate that, we are covered.
    Then the POC is updated and displayed in the POC List

  @positive @all_env
  Scenario: Sol Clauses: Delete POC successful
    Given I am on the Point of Contact Information Page
    And I add a Point of Contact with all details
    When I delete a POC in the POC list
    Then the POC is removed from the POC List

  Scenario: Sol Clauses: Edit POC template
    Given I am on the Point of Contact Information Page
    When I add a Point of Contact with all details
    And the "Point of Contacts Information" Solicitation Clause template is complete
    When I "Edit" the Point of Contacts Information template
    Then the POCs will be displayed in the POC List for Edit/Delete

  # This scenario has same behavior has Edit POC template even though you select Delete
  Scenario: Sol Clauses: Delete POC template
    Given I am on the Point of Contact Information Page
    When I add a Point of Contact with all details
    And the "Point of Contacts Information" Solicitation Clause template is complete
    When I "Delete" the Point of Contacts Information template
    Then the POCs will be displayed in the POC List for Edit/Delete

  #This scenario is covered in the above one. We need to just change step below to say "I add a POC for all mandatory roles"
  @wip
  Scenario: Sol Clauses: Complete POC template
    # Varies by solicitation if Order POC is required, not sure how we should handle
    Given I am on the Point of Contact Information Page
    When I add at least one Contract Admin POC
    And I add at least one IFF POC
    And I add at least one Order POC
    Then the POC is added and displayed with correct description in the POC List
    And Point of Contacts Information template is complete

  Scenario: Sol Clauses: POC template incomplete - no IFF POC added
    Given I am on the Point of Contact Information Page
    When I add at least one Contract Admin POC
    Then the POC is added and displayed with correct description in the POC List
    And Point of Contacts Information template is Incomplete

  Scenario: Sol Clauses: Add POC fails - No Address1 provided
    Given I am on the Point of Contact Information Page
    When I add a POC but DO NOT provide Address1
    Then Error message is displayed: "Please enter a value in Address1 field"

  @wip
  Scenario: Sol Clauses: Add POC fails - Provide International State for US Address
    Given I am on the Point of Contact Information Page
    When I add a POC but provide an International State for US Address
    Then Error message is displayed: "Since you have selected Country as USA, Please do not enter International State"

  @wip
  Scenario: Sol Clauses: Edit POC fails - No title provided
    Given I am on the Point of Contact Information Page
    When I edit a POC in the POC list
    And I update Point of Contact details but DO NOT provide a title
    Then Error message is displayed: "Please enter a value in Title field"
