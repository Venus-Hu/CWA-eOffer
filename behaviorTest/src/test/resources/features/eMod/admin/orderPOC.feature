Feature: Order POC Mod
  
  As a vendor
  I want to be able to update the Order POC on the contract
  So that Order POCs can be added, updated or deleted as needed

  @all_env @int @debug
  Scenario: Submit Order POC Mod: Review eMod successful
    Given I am on the Administrative eMod "Order POC" page
    When I complete Order POC details successfully
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Order POC Mod
    Given I am on the Modifications List page
    When I create an "Order POC" mod
    Then mod is created successfully and "Administrative" menu is displayed

  @wip
  Scenario: Order POC Mod - Add Order POC successful
    Given I am on the Administrative eMod "Order POC" page
    # You can either reuse eOffer steps or add steps that could be shared for IFF
    When I add a Point of Contact with all details
    Then the POC is added and displayed with correct description in the POC List

  #    When I add an "Order POC"
  #    Then the "Order POC" is added and displayed with correct description in the POC List
  @wip
  Scenario: Order POC Mod - Edit Order POC successful
    Given I am on the Administrative eMod "Order POC" page
    # You can either reuse eOffer steps or add steps that could be shared for IFF
    And I add a Point of Contact with all details
    When I edit a POC in the POC list
    Then the POC is updated and displayed in the POC List

  #    When I add an "Order POC"
  #    When I edit an "Order POC" in the POC List
  #    Then the "Order POC" is updated and displayed in the POC List
  @wip
  Scenario: Order POC Mod - Delete Order POC successful
    Given I am on the Administrative eMod "Order POC" page
    # You can either reuse eOffer steps or add steps that could be shared for IFF
    And I add a Point of Contact with all details
    When I delete a POC in the POC list
    Then the POC is removed from the POC List

  #    When I add an "Order POC"
  #    When I delete an "Order POC" in the POC List
  #    Then the "Order POC" is removed from the POC List
  @wip
  Scenario: Submit Order POC Mod: Submit eMod successful - GSA
    Given I am on the Final Review page in eMod for Order POC mod
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
