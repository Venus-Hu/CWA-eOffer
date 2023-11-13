Feature: Point of Contacts for Manufacturers, Dealers, Resellers, Agents Mod
  
  As a vendor
  I want to be able to update my Point of Contacts for Manufacturers, Dealers, Resellers, Agents
  So that contract can be updated

  @all_env @int
  Scenario: Submit Point of Contacts for Manufacturers, Dealers, Resellers, Agents Mod: Review eMod successful
    Given I am on the Administrative eMod "Point of Contacts for Manufacturers, Dealers, Resellers, Agents" page
    When I complete Point of Contacts for Manufacturers, Dealers, Resellers, Agents details successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Point of Contacts for Manufacturers, Dealers, Resellers, Agents Mod
    Given I am on the Modifications List page
    When I create an "Point of Contacts for Manufacturers, Dealers, Resellers, Agents" mod
    Then mod is created successfully and "Administrative" menu is displayed

  @wip
  Scenario: Submit Point of Contacts for Manufacturers, Dealers, Resellers, Agents Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Point of Contacts for Manufacturers, Dealers, Resellers, Agents mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
