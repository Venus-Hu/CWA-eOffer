Feature: Solicitation Clauses:  Commercial Sales Practice
  
  As a vendor
  I need to respond to Commercial Sales Practice as part of Solicitation Clauses
  In order to respond to all Terms and Conditions

  @all_env @int
  Scenario: Sol Clauses: Commercial Sales Practice successful - Offer/Contract level with Discount
    Given I am on the Solicitation Clauses Page
    When I respond to Commercial Sales Practice and provide valid responses for ALL questions
    And I select Offer/Contract Level for Discount/Concession Policy Level and I select Discount Basis Level
    And I add a Discount Policy
    Then Commercial Sales Practice template is Complete

  @all_env
  Scenario: Sol Clauses: Edit Commercial Sales Practice successful
    Given I successfully respond to the Commercial Sales Practice
    When I edit the Commercial Sales Practice template which is complete
    Then the Commercial Sales Practice template is updated and message is displayed:  "Saved successfully"

  @all_env
  Scenario: Sol Clauses: Delete Commercial Sales Practice successful
    Given I successfully respond to the Commercial Sales Practice
    When I delete the Commercial Sales Practice template which is complete
    Then Commercial Sales Practice template is Incomplete

  @all_env @tdr
  Scenario: Sol Clauses:  CSP is not available when TDR response is Yes
    Given I am on the Goods and Services page
    When I "add" SIN details on the Goods and Services page
    Then Commercial Sales Practice details are not required in Solicitation Clauses

  @all_env @tdr
  Scenario: Sol Clauses:  CSP is available when TDR response is No
    Given I am on the Goods and Services page
    When I "add" SIN details on the Goods and Services page
    Then Commercial Sales Practice details are required in Solicitation Clauses

  @all_env @negative
  Scenario: Sol Clauses: Error thrown when sales dollar value is not provided
    Given I am on the Solicitation Clauses Page
    When I respond to Commercial Sales Practice but do not provide Sales Dollar Value
    Then Error message is displayed: "901118 - Please enter dollar value of sales in last 12-month period."

  @all_env @negative
  Scenario: Sol Clauses: Error thrown when sales period is not exactly one year
    Given I am on the Solicitation Clauses Page
    When I respond to Commercial Sales Practice but the sales period is not one year apart
    Then Error message is displayed: "Please check one year date difference for sales period."

  @all_env @negative
  Scenario: Sol Clauses: Error thrown when there is a deviation but explation is not provided
    Given I am on the Solicitation Clauses Page
    When I respond to Commercial Sales Practice but do not explain the reason for the deviation
    Then Error message is displayed: "901122 - Please explain deviations for your written discount policies."

  @all_env @negative
  Scenario: Sol Clauses: Message is thrown when Discount Policy is not provided
    Given I am on the Solicitation Clauses Page
    When I respond to Commercial Sales Practice but do not provide a Discount Policy
    Then Application message with quotes is displayed: 901132 - Commercial Sales Practice sections are saved. Please click "Add Discount Policy" button to add at least one discount/concession policy under Section 4a

  # Alternate scenario for CSP complete (2 of 4)
  @wip
  Scenario: Sol Clauses:  Commercial Sales Practice successful - Offer/Contract level with Markup/Build-out
    Given I am on the Solicitation Clauses Page
    When I respond to Commercial Sales Practice and provide valid responses for ALL questions
    And I select Offer/Contract Level for Discount/Concession Policy Level
    And I select Markup/Build-out Basis Level and add a Markup/Build out Policy
    Then Commercial Sales Practice template is Complete

  # Alternate scenario for CSP complete (3 of 4)
  @wip
  Scenario: Sol Clauses:  Commercial Sales Practice successful - SIN level with Discount
    Given I am on the Solicitation Clauses Page
    And I have added at least one SIN in the Goods and Services section
    When I respond to Commercial Sales Practice and provide valid responses for ALL questions
    And I select SIN for Discount/Concession Policy Level and select Discount Basis Level
    And I add a Discount Policy for each SIN
    Then Commercial Sales Practice template is Complete

  # Alternate scenario for CSP complete (4 of 4)
  @wip @skip
  Scenario: Sol Clauses:  Commercial Sales Practice successful - SIN level with Markup/Build-out
    Given I am on the Solicitation Clauses Page
    # Same step used in Sol Provisions, not sure if you want to reuse or want a new step
    And I have added at least one SIN in the Goods and Services section
    When I respond to Commercial Sales Practice and provide valid responses for ALL questions
    And I select SIN for Discount/Concession Policy Level
    And I select Markup/Build-out Basis Level and add a Markup/Build out Policy for each SIN
    Then Commercial Sales Practice template is Complete
