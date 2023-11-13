Feature: SubContracting Plan - Select Plan Type
  
  As a vendor who has subcontracting opportunities
  I want to select a SubContracting Plan Type
  So that I can provide correct SubContracting Plan details

  @all_env
  Scenario: SubK: Select New Individual Plan successful
    Given I am on the SubContracting Plan Selection page
    When I select a "New Individual Plan" for subcontracting
    Then I am required to complete all ten sections of SubContracting Plan information
    And all menu items are complete

  @int @all_env
  Scenario: SubK: Select New Commercial Plan successful
    Given I am on the SubContracting Plan Selection page
    When I select a "New Commercial Plan" for subcontracting
    Then I am required to complete all ten sections of SubContracting Plan information
    And all menu items are complete

  @all_env
  Scenario: SubK: Select Existing Master Plan successful
    Given I am on the SubContracting Plan Selection page
    When I select an "Existing Master Plan" that has been approved
    Then I am only required to provide Goals and size standards information for the SubContracting Plan
    And I am required to upload "Pre-Approved Master Plan" document in the Uploads section
    And all menu items are complete

  @all_env
  Scenario: SubK: Select Existing Commercial Plan successful
    Given I am on the SubContracting Plan Selection page
    When I select an "Existing Commercial Plan" that has been approved
    Then I am only required to provide Goals and size standards information for the SubContracting Plan
    And I am required to upload "Pre-Approved Commercial Plan" document in the Uploads section

  @all_env
  Scenario: SubK:  Select New SubK Plan fails - No SubK Plan Type provided
    Given I am on the SubContracting Plan Selection page
    When I select a "new SubContracting Plan" but do not provide a plan type
    Then Error message is displayed: "Please select a plan type"
