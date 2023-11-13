#@fpt
#Feature: Special Charges for FPT Schedule
#
  #As a vendor who has wants to create an offer on a FPT schedule
  #I need to identify Special Charges that apply to my product(s)
  #In order to clarify pricing details
  #
  #Scenario:  FPT Special Charges - Add Special Charges successful    
    #Given I am on the Special Charges page for an FPT Schedule
    #When I add ALL available Special Charges for a SIN
    #Then the Special Charges are added and displayed in the Special Charges list
  #
  #@int
  #Scenario:  FPT Special Charges - Complete Special Charges successful    
    #Given I am on the Special Charges page for an FPT Schedule
    #When I add at least one Special Charge for each SIN
    #Then the Special Charges are added and displayed in the Special Charges list
    #And "Special Charges" menu is complete   
#
  #@wip
  #Scenario: FPT Special Charges - Special Charges fail - No Special Charges selected
    #Given I am on the Special Charges page for an FPT Schedule
    #When I add Special Charges BUT DO NOT select any Special Charges    
#		Then Error message is displayed: /At least one "Special Charge" or "None" must be selected/   
#
  #@wip
  #Scenario: FPT Special Charges - Edit Special Charges successful
    #Given I am on the Special Charges page for an FPT Schedule
    #When I add Special Charges for a SIN
    #And I edit Special Charges from the SIN list
    #Then the Special Charges are updated and displayed in the Special Charges list
 