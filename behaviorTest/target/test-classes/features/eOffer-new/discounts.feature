#@fpt
#Feature: Discounts for FPT Schedule
  #
  #As a vendor who has wants to create an offer on a FPT schedule
  #I need to identify Discounts that apply to my product(s)
  #In order to clarify product details
#
  #@all_env
  #Scenario: FPT Discounts for Offer level - Complete Discounts successful
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details for an Offer
    #Then the Discounts are added and displayed in the Volume/Quantity Discounts list
    #Then "Discounts" menu is complete
  #
  #@all_env
  #Scenario: FPT Discounts for Offer level - Edit Discount successful
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details for an Offer
    #And I edit a Discount from the list
    #Then the Discount is updated and displayed in the Volume/Quantity Discounts list
  #
  #@all_env
  #Scenario: FPT Discounts for Offer level - Delete Discount successful
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details for an Offer
    #And I delete a Discount from the list
    #Then the Discount is deleted and removed from the Volume/Quantity Discounts list
#
  #@negative @all_env
  #Scenario: FPT Discounts for Offer/SIN Level - Discounts fail - No Start Range provided
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details BUT DO NOT provide "Start Range"
#		Then Error message with quotes is displayed: /"Start Range" must be numeric and contain a maximum of 8 digits. "Start Range" is required/
#
  #@negative @all_env
  #Scenario: FPT Discounts for Offer/SIN Level - Discounts fail - No End Range provided
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details BUT DO NOT provide "End Range"
#		Then Error message with quotes is displayed: /"End Range" must be numeric and contain a maximum of 8 digits. "End Range" is required/
#
  #@negative @all_env
  #Scenario: FPT Discounts for Offer/SIN Level - Discounts fail - No Discount Percent provided
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details BUT DO NOT provide "Discount Percent"
#		Then Error message with quotes is displayed: /"Discount Percent" must be a decimal number with a maximum of 2 digits prior to the decimal and 4 digits after the decimal. "Discount Percent" is required/
#
  #@negative @all_env 
  #Scenario: FPT Discounts for Offer/SIN Level - Discounts fail - Larger Start Range provided
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details BUT provide a larger Start Range
#		Then Error message with quotes is displayed: /"End Range" value must be greater than the "Start Range" value/
#
  #@negative @all_env
  #Scenario: FPT Discounts for Offer/SIN Level - Discounts fail - Invalid Start Range provided
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details BUT provide an INVALID "Start Range"
# Start 1, End 10, Discount % 10
#		Then Error message with quotes is displayed: /"Start Range" value must be greater than 1/
#
  #@negative @all_env
  #Scenario: FPT Discounts for Offer/SIN Level - Discounts fail - Invalid End Range provided
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details BUT provide an INVALID "End Range"
# Start 10, End ABC, Discount % 10
#		Then Error message with quotes is displayed: /"End Range" must be numeric and contain a maximum of 8 digits/
#
  #@negative @all_env
  #Scenario: FPT Discounts for Offer/SIN Level - Discounts fail - Invalid Discount Percent provided
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details BUT provide an INVALID "Discount Percent"
# Start 5, End 10, Discount % 5000
#		Then Error message with quotes is displayed: /"Discount %" must be a decimal number with a maximum of 2 digits prior to the decimal and 4 digits after the decimal/
#
  #@negative @all_env
  #Scenario: FPT Discounts for Offer level - Discounts fail - Range provided overlaps existing range
    #Given I am on the Discounts page for an FPT Schedule
    #When I add overlapping discounts details for an Offer
# Start 3, End 6, Discount % 10
# Start 5, End 10, Discount % 15
#		Then Error message with quotes is displayed: /"Start Range" and "End Range" values must sequential and distinct from previously entered discount ranges/
#
 #@int
  #Scenario: FPT Discounts for SIN level - Complete Discounts successful
    #Given I am on the Discounts page for an FPT Schedule
    #When I add Discounts details for available SINs
    #Then the Discounts are added and displayed in the Volume/Quantity Discounts list
    #Then "Discounts" menu is complete
#
  #@wip
  #Scenario: FPT Discounts for SIN level - Edit Discount successful
    #Given I am on the Discounts page for an FPT Schedule
# Not sure if you want to reuse this step, or new one that just adds 1 discount
    #When I add Discounts details for available SINs
    #And I edit a Discount from the list
    #Then the Discount is updated and displayed in the Volume/Quantity Discounts list
#
  #@wip
  #Scenario: FPT Discounts for SIN level - Delete Discount successful
    #Given I am on the Discounts page for an FPT Schedule
# Not sure if you want to reuse this step, or new one that just adds 1 discount
    #When I add Discounts details for available SINs
    #And I delete a Discount from the list
    #Then the Discount is deleted and removed from the Volume/Quantity Discounts list
  #
  #@wip  
  #Scenario: FPT Discounts at SIN level - Discounts NOT required if SINs removed
    #Given I am on the Discounts page for an FPT Schedule
    #And I add Discounts details for available SINs
    #When I delete all SINs on the Goods and services page
    #Then "Discount" details are NOT required and corresponding menu is NOT displayed
#
################### Modify Discount Level ###########################################
#
  #@wip
  #Scenario: FPT Discounts:  Modify Discount Level from Line Item to Offer Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #And I have existing Discounts by Line Item
    #When I update Discounts to Offer Level
    #Then "Discount" details are required and corresponding menu is displayed
#
  #@wip
  #Scenario: FPT Discounts:  Modify Discount Level from Offer to Line Item Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #And I have existing Discounts at the Offer Level
    #When I update Discounts to Line Item
    #Then "Discount" details are NOT required and corresponding menu is NOT displayed
#
  #@wip
  #Scenario: FPT Discounts:  Modify Discount Level from Offer to SIN Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #And I have existing Discounts at the Offer Level
    #When I update Discounts to SIN Level
    #Then existing Discount details will be copied for all SINs
    #And I add any new Vol/Qty Discount details at SIN Level
    # Not sure if we want to reuse this step, as I believe it's slightly different validation
#
  #@wip
  #Scenario: FPT Discounts:  Modify Discount Level from SIN to Offer Level
   #Given I am on the Wizard Management page for an FPT Schedule
   #And I have existing Discounts at the SIN Level
   #When I update Discounts to Offer Level
   #Then existing Discount details will be deleted
   #And I add any new Vol/Qty Discount details at Offer Level    
#
## TBD - Discounts by Line Items scenarios that need to be provided in the Product Template