Feature: Add SIN Mod

  Scenario: Add SIN Mod and Email Address change mod
    Given I am on the Additions eMod "Add SIN" page for a Non-FPT Non-TDR contract
    And I have the option to revise CSP for "Add SIN" mod
    When I add at least one SIN as part of the mod
    And I Revise CSP successfully
    And I update Email Address successfully
    Then "Additions" menu is complete
    And "Administrative" menu is complete
