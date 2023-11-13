Feature: Solicitation Clauses
  
  As a vendor
  I need to respond to all of the terms and conditions as part of Solicitation Clauses
  In order to complete Solicitation Clauses

  @all_env @int
  Scenario: Complete Solicitation Clauses
    Given I am on the Solicitation Clauses Page
    When I have completed all Solicitation Clause templates
    #Then all Solicitation Clause templates are complete
  	And "Solicitation Clauses" menu is complete