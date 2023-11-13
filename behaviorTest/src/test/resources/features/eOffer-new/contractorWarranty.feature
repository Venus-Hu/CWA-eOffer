#@fpt
#Feature: Contractor Warranty for FPT Schedule
  #
  #As a vendor who wants to create an offer on a FPT schedule
  #	 I want to select options for Contractor Warranty at SIN level
  #	 So that I can customize my offer and warranty details
#
 #@int @all_env @skip
#	 Scenario: FPT Contractor Warranty - Complete Contractor Warranty successful
    #Given I am on the Contractor Warranty page for a FPT Schedule
    #When I select the appropriate warranty details
    #Then Contractor warranty details are displayed
    #And "Contractor Warranty" menu is complete
#
  #@wip @skip
  #Scenario: FPT Contractor Warranty - Contractor Warranty fail - Number of Warranty Periods NOT provided
    #Given I am on the Contractor Warranty page for a FPT Schedule
    #When I add Contractor Warranty details BUT DO NOT provide "Number of Warranty Periods"
#		Then Error message is displayed: /"Number of Warranty Periods" is required/
#
  #@wip @skip
  #Scenario: FPT Contractor Warranty - Contractor Warranty fail - Invalid Number of Warranty Periods provided
    #Given I am on the Contractor Warranty page for a FPT Schedule
    #When I add Contractor Warranty details BUT provide an INVALID "Number of Warranty Periods"
# Number of Warranty Periods - ABC
#		Then Error message is displayed: /"Number of Warranty Periods" must be numeric/
#
  #@wip @skip
  #Scenario: FPT Contractor Warranty - Edit Contractor Warranty successful
    #Given I am on the Contractor Warranty page for a FPT Schedule
    #When I select the appropriate warranty details
    #And I edit a Contractor Warranty from the list
    #Then the Contract Warranty is updated and displayed in the Contractor Warranties list
