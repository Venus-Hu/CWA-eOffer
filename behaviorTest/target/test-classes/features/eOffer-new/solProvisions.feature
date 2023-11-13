Feature: Solicitation Provisions
  
  As a vendor
  I need to respond to all of the terms and conditions as part of Solicitation Provisions
  In order to complete Solicitation Provisions

  @mcc @all_env @debug
  Scenario: Complete Solicitation Provisions
    Given I am on the Solicitation Provisions page
    When I have completed all Solicitation Provisions
    Then all Solicitation Provisions are complete
    And "Solicitation Provisions" menu is complete
