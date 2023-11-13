Feature: Solicitation Provisions:  Past Performance
  
  As a vendor
  I need to provide Past Performance information as part of the Technical Proposal
  In order to comply with the Solicitation Provisions

  @all_env
  Scenario: Sol Provisions: Past Performance successful - Documents not uploaded
    Given I am on the Past Performance page
    When I complete ALL Past Performance questions with valid responses
    And I submit the Past Performance form
    Then Past Performance provision is complete

  @all_env @int
  Scenario: Sol Provisions: Past Performance successful - Documents uploaded
    Given I am on the Past Performance page
    When I upload "Past Performance & Experience" document on Past Performance page
    And I upload "Order Form for Past Performance Eval" document on Past Performance page
    And I complete ALL Past Performance questions with valid responses
    And I submit the Past Performance form
    Then Past Performance provision is complete

  @all_env
  Scenario: Sol Provisions: Edit Past Performance successful
    Given I am on the Past Performance page
    And I complete ALL Past Performance questions with valid responses
    And I submit the Past Performance form
    When I edit the Past Performance
    Then the Past Performance provision is updated and message is displayed: "Saved successfully"

  @wip
  Scenario: Sol Provisions:  Edit Past Performance fails - Intended use of Subcontractors NOT provided
    Given I am on the Solicitation Provisions page
    And I complete ALL Past Performance questions with valid responses
    And I submit the Past Performance form
    When I edit the Past Performance but DO NOT answer "Past Performance Evaluation Month" question
    Then Error message is displayed: "Date Cannot be 'DD'. Please select a valid date"

  @wip
  Scenario: Sol Provisions: Past Performance provision fails - Past Performance Evaluation Month NOT provided
    Given I am on the Past Performance page
    When I respond to Past Performance questions but DO NOT answer "Past Performance Evaluation Month" question
    Then Error message is displayed: "Month Cannot be 'MM'. Please select a valid month"

  @wip
  Scenario: Sol Provisions:  Past Performance Upload document successful
    Given I am on the Past Performance page
    When I upload "Past Performance & Experience" supporting document on Past Performance page
    Then the document is uploaded and displayed with document type and name on the Past Performance page
    And the document(s) is displayed with document type and name in the Documents List on the Upload Documents page
