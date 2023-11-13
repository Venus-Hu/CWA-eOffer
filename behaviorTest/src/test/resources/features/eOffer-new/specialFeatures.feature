#@fpt
#Feature: Special Features for FPT Schedule
  #
  #As a vendor who has wants to create an offer on a FPT schedule
  #I need to identify Special Features that apply to my product(s)
  #In order to clarify product details
#
  #@wip @fpt
  #Scenario: FPT Special Features - Add Special Features successful
    #Given I am on the Special Features page for an FPT Schedule
    #When I add Environmental and Special Features for a SIN
    # Don't include NONE option
    #Then the Environmental and Special Features are added and displayed in the Special Features list
#
#@int
  #Scenario: FPT Special Features - Complete Special Features successful
    #Given I am on the Special Features page for an FPT Schedule
    #When I identify if there are any Environmental and Special Features for each SIN
    # Reworded to say that you can complete this section by selecting None or other options
    #Then "Special Features" menu is complete
    #
  #@wip
  #Scenario: FPT Special Features - Special Features fail - No Special Features selected
    #Given I am on the Special Features page for an FPT Schedule
    #When I add Special Features BUT DO NOT select any Special Features    
#		Then Error message is displayed: /At least one "Special Feature" or "None" must be selected/
    #
  #@wip
  #Scenario: FPT Special Features - Special Features fail - No Environmental Features selected
    #Given I am on the Special Features page for an FPT Schedule
    #When I add Special Features BUT DO NOT select any Environmental Features    
#		Then Error message is displayed: /At least one "Environmental Feature" or "None" must be selected/
#
  #@wip
  #Scenario: FPT Special Features - Edit Special Features successful
    #Given I am on the Special Features page for an FPT Schedule
    #When I add Environmental and Special Features for a SIN
    #And I edit Special Features from the SIN list
    #Then the Environmental and Special Features are updated and displayed in the Special Features list
    #
  #@skip 
  #Scenario: FPT Special Features - EPA Recommended Features as links successful
    #Given I am on the Special Features page for an FPT Schedule 
#	Then EPA features direct vendor to correct external page 
    