#@fpt
#Feature: Delivery for FPT Schedule
  #
  #As a vendor who wants to create an offer on a FPT schedule
  #	 I want to select options for Delivery
  #	 So that I can customize my offer
#
  #@all_env
  #Scenario: FPT Delivery at Offer level - Complete Delivery successful
    #Given I selected Delivery at Offer level and am on the Delivery page
    #When I select delivery types for ALL delivery areas
    #Then the Delivery option is updated and message is displayed: "Delivery Information has been successfully added."
    #And "Delivery" menu is complete
#
  # Not sure if you prefer to make this Offer or SIN specific or leave it generic
  #@wip
  #Scenario: FPT Delivery for Offer/SIN Level - Delivery fails - Delivery Type not provided
    #Given I am on the Delivery page for an FPT Schedule
    #When I add Delivery details BUT DO NOT provide Delivery Type for each area
    #Then Error message is displayed: /A "Delivery Type" must be assigned to each Delivery Area/
#
  #@int @all_env
  #Scenario: FPT Delivery at SIN level - Complete Delivery successful
    #Given I selected Delivery at SIN level and am on the Delivery page
    #When I select delivery types for ALL delivery areas
    #Then the Delivery option is updated and message is displayed: "Delivery Information has been successfully added."
    #And "Delivery" menu is complete
#
  #@wip
  #Scenario: FPT Delivery at SIN level - Edit Delivery successful
    #Given I selected Delivery at SIN level and am on the Delivery page
    #When I select delivery types for ALL delivery areas
    # Not sure if you want to reuse this step, or new one that just adds 1 delivery
    #And I edit a Delivery from the list
    #Then the Delivery is updated and displayed in the Deliveries list
#
  #@all_env
  #Scenario: FPT Delivery at SIN level - Delivery NOT required if SINs removed
    #Given I selected Delivery at SIN level and am on the Delivery page
    #And I select delivery types for ALL delivery areas
    #And the Delivery option is updated and message is displayed: "Delivery Information has been successfully added."
    #When I delete a SIN in the SIN list on Goods and services page
    ##Wizard menu gets reset. This is FAD if no SINs are present in Goods and services
    #Then "Delivery" details are NOT required and corresponding menu is NOT displayed
#
  ################### Modify Delivery Level ###########################################
  #@wip
  #Scenario: FPT Delivery:  Modify Delivery Level from Line Item to Offer Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #And I have existing Delivery Area selected by Line Item
    #When I update Delivery to Offer Level
    #Then "Delivery" details are required and corresponding menu is displayed
#
  #@wip
  #Scenario: FPT Delivery:  Modify Delivery Level from Offer to Line Item Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #And I have existing Delivery Area selected at the Offer Level
    #When I update Delivery Area to Line Item
    #Then "Delivery" details are NOT required and corresponding menu is NOT displayed
#
  #@wip
  #Scenario: FPT Delivery:  Modify Delivery Level from Offer to SIN Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #And I have existing Delivery Area selected at the Offer Level
    #When I update Delivery Area to SIN Level
    #Then existing Delivery details will be copied for all SINs
    #And I add any new Delivery Area details at SIN Level
#
  #@wip
  #Scenario: FPT Delivery:  Modify Delivery Level from SIN to Offer Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #And I have existing Delivery Area selected at the SIN Level
    #When I update Delivery Area to Offer Level
    #Then existing Delivery details will be deleted
    #And I add any new Delivery Area details at Offer Level
## TBD - Delivery by Line Items scenarios that need to be provided in the Product Template  
