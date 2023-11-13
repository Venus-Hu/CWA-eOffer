Feature: GSA Initiated Incorporate Sub-Contracting Plan Mod
  
  As a CO
   I want to award a re-rep that changes current business size from "small" to "other than small" 
 	 I want a eMod system to receive the GSA Initiated Incorporate Sub-Contracting Plan Mod
 	 
## Subk mod cannot be setup on GSA test env
	@skip
  Scenario: GSA initiated Subk Mod - validate for new Individual plan
    Given I am on the Modifications List page
    When I select GSA mod type: "Incorporate Sub-Contracting Plan (SubK)"
    And I am on the Terms and Conditions eMod "Sub-Contracting Plan" page
    And I select a "New Individual Plan" for subcontracting
    And I am required to complete all ten sections of SubContracting Plan information
    Then I can perform Final Review of my eMod
    
  @skip
    Scenario: GSA initiated Subk Mod - validate for new Commercial plan
    Given I am on the Modifications List page
    When I select GSA mod type: "Incorporate Sub-Contracting Plan (SubK)"
    And I am on the Terms and Conditions eMod "Sub-Contracting Plan" page
    And I select a "New Commercial Plan" for subcontracting
    And I am required to complete all ten sections of SubContracting Plan information
    Then I can perform Final Review of my eMod
