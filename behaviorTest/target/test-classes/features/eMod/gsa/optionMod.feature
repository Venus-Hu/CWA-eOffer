Feature: GSA Initiated Option Mod
  
  As a vendor
  I want to be able to update contract type from TDR to non-TDR and vice versa
  So that my contract can be TDR or non TDR

## Option mod cannot be setup on GSA test env
	@skip
  Scenario: GSA initiated Option Mod - validate TDR option
    Given I am on the Modifications List page
    When I select GSA mod type: "GSA Initiated Option"
    And I "accept" TDR participation for the contract
    And I complete all Pricing data updates for FPT contract
    Then on mod completion, the mod response document displays contract is TDR
