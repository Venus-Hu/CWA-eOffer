Feature: Add Goods/Services to the Offer
  
  As a vendor
  I want to add SINs to the offer
  So that I can associate Goods/Services to the offer


  @all_env @smokeTest1
  Scenario: Available Offerings: Complete Available Offerings
    Given I am on the Available Offerings page
    When I add at least one SIN and complete Available Offerings details
    Then the SIN details will be displayed on Available Offerings page
    And I select preponderance of work
    And "Available Offerings" menu is complete

  @all_env
  Scenario: Available Offerings: Edit SIN successful on Available Offerings
    Given I am on the Available Offerings page
    And I "add" SIN details on the Available Offerings page
    When I "edit" SIN details on the Available Offerings page
    Then the SIN details will be displayed on Available Offerings page

  @all_env
  Scenario: Available Offerings: Delete SIN successful on Available Offerings
    Given I am on the Available Offerings page
    And I "add" SIN details on the Available Offerings page
    When I delete a SIN in the SIN list on Available Offerings page
    Then the SIN is removed from the SIN List

  @all_env
  Scenario: Available Offerings: Add SIN fails - Invalid Estimated Sales format
    Given I am on the Available Offerings page
    When I add a SIN but provide an INVALID Estimated Sales
    Then Error message is displayed: "Please enter a valid numeric value for Estimated Sales"

  @all_env
  Scenario: Available Offerings: Edit SIN fails - No Estimated Sales provided
    Given I am on the Available Offerings page
    And I "add" SIN details on the Available Offerings page
    When I "edit" SIN details on the Available Offerings page but DO NOT provide an Estimated Sales
    Then Error message is displayed: "Please enter Estimated Sales"

  @all_env
  Scenario: Available Offerings: SIN Incomplete - No Preponderance of Work
    Given I am on the Available Offerings page
    When I "add" SIN details on the Available Offerings page
    Then the SIN details will be displayed on Available Offerings page
    And "Available Offerings" menu is incomplete

  @all_env @tdr @negative
  Scenario: Available Offerings: Add SIN fails - TDR response not captured
    Given I am on the Goods and Services page
    When I add a TDR SIN but do not respond to the prompt
    Then Error message with quotes is displayed: 201103 - Please select "Yes" or "No" for the TDR Participation Opt-In .

  @mcc @all_env @int
  Scenario: Available Offerings: Add Complementary SIN successful - Added base SIN before adding complementary SIN
    Given I am on the Available Offerings page
    When I add one base SIN and Complementary SIN and complete Available Offerings details
    Then the SIN details will be displayed on Available Offerings page
    And I select preponderance of work
    And "Available Offerings" menu is complete

  @mcc @negative @all_env
  Scenario: Avaiable Offerings: Add Complementary SIN fails - No base SIN added
    Given I am on the Available Offerings page
    When I add at least one SIN and complete Available Offerings details
    Then Error message is displayed: "In order to add ANCILLARY you must include a SIN from an eligible MAS Subcategory in your offer. Eligible Subcategories can be found in the SIN instructions in the Miscellaneous Category attachment that is part of the MAS Solicitation."

  @mcc @negative @all_env
  Scenario: Available Offerings: SIN Incomplete - Cannot add Complementary SIN as Preponderance of Work
    Given I am on the Available Offerings page
    When I add at least one SIN and complete Available Offerings details
    Then the SIN details will be displayed on Available Offerings page
    And I select preponderance of work
    Then Error message is displayed: "ANCILLARY cannot be the Preponderance Of Work on the Offer."
