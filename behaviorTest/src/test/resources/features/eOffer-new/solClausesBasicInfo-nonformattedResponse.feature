Feature: Solicitation Clauses:  Basic Information -non formatted vendor response
  
  As a vendor
  I need to respond to Basic Information as part of Solicitation Clauses
  In order to respond to all Terms and Conditions

  #@int @all_env
  Scenario: Sol Clauses: Basic Information template - Respond to Clause successful
    Given I am on the Basic Information page for non formatted vendor response
    When I respond to a Basic information Clause and provide valid responses for ALL questions
    Then Basic Information template is Complete

  Scenario: Sol Clauses:  Edit Basic Information template
    Given I am on the Solicitation Clauses page
    And I respond to a Basic information Clause and provide valid responses for ALL questions
    When I "Edit" the Basic Information template
    Then the Solicitation Clause List will be displayed for Edit/Delete

  Scenario: Sol Clauses:  Delete Basic Information template
    Given I am on the Solicitation Clauses page
    And I respond to a Basic information Clause and provide valid responses for ALL questions
    When I "Delete" the Basic Information template
    Then the Basic Information template will be deleted and the page will prompt me to Respond
