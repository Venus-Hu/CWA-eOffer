Feature: Solicitation Provisions:  Corporate Experience
  
  As a vendor
  I need to provide Corporate Experience information as part of the Technical Proposal
  In order to comply with the Solicitation Provisions

  @all_env
  Scenario: Sol Provisions: Corporate Experience provision fails - Number of years of Experience NOT provided
    Given I am on the Corporate Experience Page
    When I respond to Corporate Experience questions but DO NOT answer "Number of Years of Corporate Experience" question
    Then Error message is displayed: "Please enter the number of years of corporate experience"

  @int @all_env
  Scenario: Sol Provisions: Corporate Experience successful
    Given I am on the Corporate Experience Page
    When I complete ALL Corporate Experience questions with valid responses
    Then Corporate Experience provision is complete

  @all_env
  Scenario: Sol Provisions: Edit Corporate Experience successful
    Given I am on the Solicitation Provisions page
    And I add ALL Corporate Experience questions with valid responses
    When I edit the Corporate Experience
    Then the Corporate Experience provision is updated and message is displayed:  "Saved successfully"

  @all_env
  Scenario: Sol Provisions: Edit Corporate Experience fails - Intended use of Subcontractors NOT provided
    Given I am on the Solicitation Provisions page
    And I add ALL Corporate Experience questions with valid responses
    When I edit the Corporate Experience but DO NOT answer "Intended use of Subcontractors" question
    Then Error message is displayed: "Please discuss the intended use of subcontractors"
