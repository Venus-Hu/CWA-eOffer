Feature: Solicitation Clauses:  Basic Information
  
  As a vendor
  I need to respond to Basic Information as part of Solicitation Clauses
  In order to respond to all Terms and Conditions

  @all_env
  Scenario: Sol Clauses: Complete Basic Information template
    #Why sol clause page and not basic info page?
    #  Given I am on the Solicitation Clauses page
    Given I am on the Basic Information Solicitation Clauses page
    When I have responded to ALL Basic Info Solicitation Clauses successfully
    Then Basic Information template is Complete

  @wip
  Scenario: Sol Clauses:  Basic Information - Edit Clause successful
    Given I am on the Basic Information Solicitation Clauses page
    When I edit a Solicitation Clause in the Clause List that has been completed
    And I update the Terms and Conditions
    Then the Solicitation Clause is updated and message is displayed: "Saved successfully"

  @wip
  Scenario: Sol Clauses:  Basic Information - Delete Clause successful
    Given I am on the Basic Information Solicitation Clauses page
    And I have responded to ALL Basic Info Solicitation Clauses successfully
    When I delete a Solicitation Clause in the Clause List
    Then the Clause status is Incomplete

  @wip
  Scenario: Sol Clauses:  Basic Information - Respond to Clause successful
    Given I am on the Basic Information Solicitation Clauses page
    When I respond to a Solicitation Clause and provide valid responses for ALL questions
    Then the Clause status is Complete
