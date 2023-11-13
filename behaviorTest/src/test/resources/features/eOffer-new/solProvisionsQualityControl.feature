Feature: Solicitation Provisions:  Quality Control
  
  As a vendor
  I need to provide Quality Control practices as part of the Technical Proposal
  In order to comply with the Solicitation Provisions

  @all_env
  Scenario: Sol Provisions: Quality Control provision fails - Internal Review Procedures NOT provided
    Given I am on the Quality Control page
    When I respond to Quality Control questions but DO NOT answer "Internal Review Procedures" question
    Then Error message is displayed: "Please describe the internal review procedures that facilitate high-quality standards"

  @all_env @int
  Scenario: Sol Provisions: Quality Control successful
    Given I am on the Quality Control page
    When I complete ALL Quality Control questions with valid responses
    Then Quality Control provision is complete

  @all_env
  Scenario: Sol Provisions: Edit Quality Control successful
    Given I am on the Solicitation Provisions page
    And I add ALL Quality Control questions with valid responses
    When I edit the Quality Control
    Then the Quality Control provision is updated and message is displayed:  "Saved successfully"

  @all_env
  Scenario: Sol Provisions: Edit Quality Control fails - Individuals Responsible for Quality Control NOT provided
    Given I am on the Solicitation Provisions page
    And I add ALL Quality Control questions with valid responses
    When I edit the Quality Control but DO NOT answer "Individuals Responsible for Quality Control" question
    Then Error message is displayed: "Please identify the individuals responsible for ensuring quality control"
