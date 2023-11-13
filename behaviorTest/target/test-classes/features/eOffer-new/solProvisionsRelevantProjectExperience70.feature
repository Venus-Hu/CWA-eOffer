Feature: Solicitation Provisions:  Relevant Project Experience
  
  As a vendor
  I need to provide Relevant Project Experience as part of the Technical Proposal
  In order to comply with the Solicitation Provisions

  Background: 
    Given I am on the Available Offerings page
    When I add at least one SIN and complete Available Offerings details

  @all_env @int
  Scenario: Sol Provisions: Relevant Project Experience successful
    Given I am on the Relevant Project Experience page
    When I add at least three relevant projects for each SIN
    Then Relevant Project Experience provision is complete

  @all_env
  Scenario: Sol Provisions: Relevant Project Experience - Edit Project successful
    Given I am on the Relevant Project Experience page
    And I have already added a project for a SIN
    When I edit a project from the Relevant Project Experience list for that SIN
    Then the project is updated and message is displayed: "Saved successfully"

  @all_env
  Scenario: Sol Provisions: Relevant Project Experience - Delete Project successful
    Given I am on the Relevant Project Experience page
    And I have already added a project for a SIN
    When I delete a project from the Relevant Project Experience list for that SIN
    Then the project is removed from the Relevant Project Experience list for that SIN

  @wip
  Scenario: Sol Provisions:  Relevant Project Experience - Edit Provision successful
    Given I am on the Relevant Project Experience page
    When I add at least three relevant projects for each SIN
    # Not sure if you want to reuse this step or new step?
    #    When I have added at least three relevant projects for each SIN
    And I edit the Relevant Project Experience Provision
    Then the Projects will be displayed by SIN in Relevant Project Experience list for Edit/Delete

  @wip
  Scenario: Sol Provisions: Relevant Project Experience Project List
    Given I am on the Solicitation Provisions page
    And I add at least one SIN and complete Goods and Services details
    When I respond to the Relevant Project Experience
    Then the SIN(s) will be displayed in Relevant Project Experience list

  @wip
  Scenario: Sol Provisions:  Relevant Project Experience - Add Project fails - Methodology NOT provided
    Given I am on the Relevant Project Experience page
    When I add a project for a SIN
    And I respond to Relevant Project Experience questions but DO NOT answer "Methodology" question
    Then Error message is displayed: "Please provide a description for the methodology, tools, and/or processes utilized in performing the work"
