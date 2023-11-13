Feature: Permanent Price Reduction -Industry Partner requested mod
  
  As a vendor
  I want to provide permanent price reduction to my existing contract
  So that I can make price reductions upon request by Industry Partner and existing contract terms can stay in effect

  #Respond with No to revise CSP
  @all_env @int
  Scenario: Submit Permanent Price Reduction IPR Mod: Review eMod successful for Non-FPT Non-TDR contract
    Given I am on the Pricing eMod "Permanent Price Reduction (IPR)" page
    And I have the option to revise CSP for "Permanent Price Reduction (IPR)" mod
    When I complete Permanent Price Reduction-IPR details successfully
    And I complete all data updates for non-FPT contract
    And all menu items are complete
    Then I can perform Final Review of my eMod

  @wip
  Scenario: Permanent Price Reduction IPR Mod
    Given I am on the Modifications List page
    When I create an "Permanent Price Reduction (IPR)" mod
    Then mod is created successfully and "Pricing" menu is displayed

  #@fileUpload @fpt
  #Scenario: Submit Permanent Price Reduction IPR Mod: Review eMod successful for FPT contract
  #Given I am on the Pricing eMod "Permanent Price Reduction (IPR)" page
  #When I complete Permanent Price Reduction-IPR details successfully
  #And I complete all Pricing data updates for FPT contract
  #And all menu items are complete
  #Then I can perform Final Review of my eMod
  #
  #@edi @fpt
  #Scenario: Submit Permanent Price Reduction IPR Mod: Review eMod successful using EDI process
  #Given I am on the Pricing eMod "Permanent Price Reduction (IPR)" page
  #When I complete Permanent Price Reduction-IPR details successfully
  #And I complete all Pricing data updates for FPT contract
  #And all menu items are complete
  #Then I can perform Final Review of my eMod
  #
  #@gsaTest @wip @fpt
  #Scenario: Submit Permanent Price Reduction IPR Mod: Submit eMod successful for FPT contract
  #Given I am on the Final Review page in eMod for Permanent Price Reduction (IPR) mod for FPT contract
  #When I submit my eMod
  #Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
  #And Modification Id is displayed in my Submitted eMods list
  @wip
  Scenario: Submit Permanent Price Reduction (IPR) Mod: Submit eMod successful for Non-FPT contract (GSA)
    Given I am on the Final Review page in eMod for Permanent Price Reduction (IPR) mod for Non-FPT contract
    When I submit my eMod
    Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    And Modification Id is displayed in my Submitted eMods list
