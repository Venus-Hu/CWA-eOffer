Feature: GSA Initiated Assign Contract Mod
  
  As a vendor
  I want to be able to view the assign contract mod 
  

## Assign contract mod cannot be setup on GSA test env
	@skip
	Scenario: GSA initiated Assign Contract Mod
    Given I am on the Modifications List page
    When I view GSA mod type: "GSA Initiated Assign Contract"
    Then "ModResponse.pdf" document displays specific text
