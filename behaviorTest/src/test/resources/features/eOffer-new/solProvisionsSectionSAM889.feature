Feature: Solicitation Provisions: Section SAM889
  
  As a vendor
  I need to provide Section SAM889 selections as part of the Technical Proposal
  In order to comply with the Solicitation Provisions

  @all_env @sam889Test @int
  Scenario: Sol Provisions: Section SAM889 part A successful
    Given I am on the Section SAM part A page
    When I complete All Section SAM part A questions with valid responses
    Then Section SAM part A is complete

  @all_env @sam889Test @int
  Scenario: Sol Provisions: Section SAM889 part B successful
    Given I am on the Section SAM part B page
    When I complete All Section SAM part B questions with valid responses
    Then Section SAM part B is complete

  @all_env @sam889Test @wip
  Scenario: Sol Provisions: Edit Section SAM889 part A successful
    Given I am on the Solicitation Provisions page
    And I complete ALL Section SAM889 part A questions with valid responses
    When I edit the Section SAM889 part A
    Then the Section SAM889 part A is updated and message is displayed:  "Saved successfully"

  @all_env @sam889Test @wip
  Scenario: Sol Provisions: Edit Section SAM889 part B successful
    Given I am on the Solicitation Provisions page
    And I complete ALL Section SAM889 part B questions with valid responses
    When I edit the Section SAM889 part B
    Then the Section SAM889 part B is updated and message is displayed:  "Saved successfully"
  @all_env @sam889Test @wip
  Scenario: Sol Provisions: Edit Section SAM889 part A fails - Individuals Responsible for Section SAM889 part A NOT provided
    Given I am on the Solicitation Provisions page
    And I complete ALL Section SAM889 part A questions with valid responses
    When I edit the Section SAM889 part A but DO NOT answer "Individuals Responsible for Section SAM889 part A" question
    Then Error message is displayed as: "901111.2 - Since you selected that the Offeror 'will provide' covered telecom, you must disclose the entity that produced the equipment (enter 'N/A' if you are only providing covered services)."

  @all_env @sam889Test @wip
  Scenario: Sol Provisions: Edit Section SAM889 part B fails - Individuals Responsible for Section SAM889 part B NOT provided
    Given I am on the Solicitation Provisions page
    And I complete ALL Section SAM889 part B questions with valid responses
    When I edit the Section SAM889 part B but DO NOT answer "Individuals Responsible for Section SAM889 part B" question
    Then Error message is displayed as: "901112.2 - Since you selected that the Offeror 'does use' covered telecom, you must disclose the entity that produced the equipment (enter 'N/A' if you are only using covered services)."
