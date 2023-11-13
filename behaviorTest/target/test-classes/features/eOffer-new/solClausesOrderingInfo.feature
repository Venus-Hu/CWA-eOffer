Feature: Solicitation Clauses:  Ordering Information
  
  As a vendor
  I need to provide Addresses for Ordering Information as part of Solicitation Clauses
  In order to provide information on how to receive orders

  @all_env
  Scenario: Sol Clauses: Add Ordering Information entry successful
    Given I am on the Ordering Information Page
    When I add an Ordering Information entry with all details
    Then the Ordering Information is added and displayed with correct description in the Ordering Information List

  @int @all_env
  Scenario: Sol Clauses: Complete Ordering Information template
    Given I am on the Ordering Information Page
    When I add at least one Ordering Receipt Information entry
    And I add at least one Remittance Address entry
    Then the Ordering Information is added and displayed with correct description in the Ordering Information List
    And Ordering Information template is complete

  @all_env
  Scenario: Sol Clauses:  Ordering Information template incomplete - no Remittance Address added
    Given I am on the Ordering Information Page
    When I add at least one Ordering Receipt Information entry
    Then the Ordering Information is added and displayed with correct description in the Ordering Information List
    And Ordering Information template is Incomplete

  @all_env
  Scenario: Add Ordering Information fails - No Fax provided for Order Receipt Information
    Given I am on the Ordering Information Page
    When I add an Ordering Receipt Information entry with Fax transmission but DO NOT provide Fax Number
    Then Error message is displayed: "Since you have selected Facsimile Transmission, Please enter Fax Number"

  @all_env
  Scenario: Add Ordering Information fails - Invalid Zip for US Address for Remittance Address
    Given I am on the Ordering Information Page
    When I add an Remittance Address entry but provide an Invalid Zip Code for US Address
    Then Error message is displayed: "Since you have selected country as USA, Please enter the valid zip format for USA"

  @all_env
  Scenario: Edit Ordering Information successful
    Given I am on the Ordering Information Page
    When I edit an Ordering Information entry in the Ordering Information List
    Then the Ordering Information is updated and displayed in the Ordering Information List

  @all_env
  Scenario: Edit Ordering Information fails - No email provided
    Given I am on the Ordering Information Page
    And I add at least one Ordering Receipt Information entry
    When I update an Ordering Receipt Information with EDI transmission but DO NOT provide Email
    Then Error message is displayed: "Please enter a value in Email field"

  @all_env
  Scenario: Delete Ordering Information successful
    Given I am on the Ordering Information Page
    And I add at least one Ordering Receipt Information entry
    When I delete an Ordering Information entry from the Ordering Information List
    Then the Ordering Information entry is removed from the Ordering Information List

  #@wip
  #Scenario: Sol Clauses:  Edit Ordering Information template
    #Given I am on the Solicitation Clauses Page
    # Not sure if you want to reuse step above
    #And the Ordering Information template is complete
    #When I "Edit" the Ordering Information template
    #Then the Ordering Information List will be displayed for Edit/Delete
    
  #@wip
  #Scenario: Sol Clauses:  Delete Ordering Information template
    #Given I am on the Solicitation Clauses Page
    # Not sure if you want to reuse step above
    #And I Point of Contacts Information template is complete
    #When I "Delete" the Ordering Information template
    #Then the Ordering Information List will be displayed for Edit/Delete
