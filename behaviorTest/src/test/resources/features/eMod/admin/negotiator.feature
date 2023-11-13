Feature: Authorized Negotiator Mod
  
  As a vendor
  I want to be able to update the Authorized Negotiators on the contract
  So that negotiators can be added, updated or deleted as needed

  @all_env @negative @int
  Scenario: Submit Authorized Negotiator Mod - International phone and US Phone
    Given I am on the Administrative eMod "Authorized Negotiator" page
    When I add a Negotiator with all details
    Then Error message is displayed: "901027 - Please enter Phone (US or International)."

  Scenario: Submit Authorized Negotiator Mod - International phone eMod successful
    Given I am on the Administrative eMod "Authorized Negotiator" page
    When I add a Negotiator with all details
    And I have uploaded Agent Authorization Letter documents successfully
    Then all menu items are complete

  @all_env
  Scenario: Submit Authorized Negotiator Mod - International Phone: eMod successful GSA
    Given I am on the Administrative eMod "Authorized Negotiator" page
    When I complete Negotiator details successfully
    And I have uploaded Agent Authorization Letter documents successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Authorized Negotiator Mod
    Given I am on the Modifications List page
    When I create an "Authorized Negotiator" mod
    Then mod is created successfully and "Administrative" menu is displayed

  @wip
  Scenario: Authorized Negotiator Mod - Add Negotiator with Signature
    Given I am on the Administrative eMod "Authorized Negotiator" page
    When I add a Negotiator who is authorized to Sign
    Then the Negotiator is added and displayed with correct role in the Negotiator List

  @wip
  Scenario: Authorized Negotiator Mod - Add Negotiator not authorized to sign
    Given I am on the Administrative eMod "Authorized Negotiator" page
    When I add a Negotiator who is NOT authorized to Sign
    Then the Negotiator is added and displayed with correct role in the Negotiator List

  @wip
  Scenario: Authorized Negotiator Mod - Edit Negotiator successful
    Given I am on the Administrative eMod "Authorized Negotiator" page
    And I add a Negotiator with all details
    When I edit a Negotiator in the Negotiator list
    Then the Negotiator is updated and displayed in the Negotiator List

  @wip
  Scenario: Authorized Negotiator Mod - Delete Negotiator successful
    Given I am on the Administrative eMod "Authorized Negotiator" page
    And I add a Negotiator with all details
    When I delete a Negotiator in the Negotiator list
    Then the Negotiator is removed from the Negotiator List

  @wip
  Scenario: Submit Authorized Negotiator Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Authorized Negotiator mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
