#@fpt
#Feature: Zonal Pricing for FPT Schedule
#
  #As a vendor who has wants to create an offer on a FPT schedule
  #I need to identify Zones for each state
  #In order to provide Zonal Pricing details
#
#@int
#	@all_env
  #Scenario:  FPT Zonal Pricing - Complete Zonal Pricing successful    
    #Given I am on the Zonal Pricing page for an FPT Schedule
    #When I assign a Zone Number to ALL states/territories
    #Then the Zone details will be displayed in the Zones list
    #And "Zonal Pricing" menu is complete 
#
  #@wip
  #Scenario:  FPT Zonal Pricing - Add CONUS Zone successful    
    #Given I am on the Zonal Pricing page for an FPT Schedule
    #When I select the CONUS Zonal Area for a Zone Number
    #Then the states are selected in the CONUS list
    #And the states are assigned to the selected zone and displayed in the Zones list
#
  #@wip
  #Scenario:  FPT Zonal Pricing - Add NON-CONUS Zone successful    
    #Given I am on the Zonal Pricing page for an FPT Schedule
    #When I select the NON-CONUS Zonal Area for a Zone Number
    #Then the states are selected in the NON-CONUS list
    #And the states are assigned to the selected zone and displayed in the Zones list
    #
  #@wip
  #Scenario:  FPT Zonal Pricing - Zonal Pricing fails - No States are selected   
    #Given I am on the Zonal Pricing page for an FPT Schedule
    #When I add Zonal Area BUT DO NOT select any states
#		Then Error message is displayed: /Please select a "Zone Number" and select states from either CONUS or NON CONUS/
#
  #@wip
  #Scenario:  FPT Zonal Pricing - Add States to a Zone successful    
    #Given I am on the Zonal Pricing page for an FPT Schedule
    #When I select a list of States and assign to a Zone Number
    #Then the states are assigned to the selected zone and displayed in the Zones list
#
  #@wip
  #Scenario: FPT Zonal Pricing - Edit Zonal Pricing successful
    #Given I am on the Zonal Pricing page for an FPT Schedule
    #When I select a list of States and assign to a Zone Number
    #And I edit a Zone from the list
    #Then the Zone is updated and displayed in the Zones list
       