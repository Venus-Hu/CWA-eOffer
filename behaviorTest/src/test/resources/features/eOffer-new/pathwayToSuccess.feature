Feature: Verify Pathway to Success
  
  As a vendor
  I want to verify my Pathway to Sucess is selected

  @all_env @smokeTest
  Scenario: Pathway to Success section one is selected
    Given I am on the Pathway to Success Page
    When Vendor complete section one
    And Vendor Save and Continue
    Then Pathway to Success menu is complete

  @all_env
  Scenario: Pathway to Success section two is selected
    Given I am on the Pathway to Success Page
    When Vendor complete section two
    And Vendor Save and Continue
    Then Pathway to Success menu is complete

  @all_env
  Scenario: Pathway to Success section one and two are selected
    Given I am on the Pathway to Success Page
    When Vendor complete section one and two
    And Vendor Save and Continue
    Then Error message is displayed: "201122 - You must make a selection from Section 1 OR Section 2, but not both"

  @all_env
  Scenario: Pathway to Success not complete
    Given I am on the Pathway to Success Page
    When Vendor did not complete section one Or two
    Then Error message is displayed: "201120 - You must make a selection under Section 1 OR Section 2"

  @all_env
  Scenario: Pathway to Success not complete section one
    Given I am on the Pathway to Success Page
    When Vendor did not complete section one
    Then Error message is displayed: "201121 - You must select both options from Section 1"
