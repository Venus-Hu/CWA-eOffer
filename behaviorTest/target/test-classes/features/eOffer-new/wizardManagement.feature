#@fpt
#Feature: Wizard Management for FPT Schedule
  #
  #As a vendor who wants to create an offer on a FPT schedule
  #	 I want to select options using Wizard management 
  #	 So that I can customize my offer and pricing details
#
#@int
#	@all_env
  #Scenario: FPT Wizard Management - Complete Wizard Management successful
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select ALL available Accessories/Options at the Offer Level
    #Then corresponding menu items are displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Discounts Required - Volume/Quantity at Offer Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select Volume/Quantity Discounts at the Offer Level
    #Then "Discount" details are required and corresponding menu is displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Discounts Required - Volume/Quantity at SIN Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select Volume/Quantity Discounts at the SIN Level
    #Then "Discount" details are required and corresponding menu is displayed
    #
  #@wip
  #Scenario: FPT Wizard Management - Discounts Required in Product Template - Line Item Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select Volume/Quantity or Dollar Discounts at the Line Item Level
    #Then "Discounts" details are required and provided in the product template 
#
  #@wip
  #Scenario: FPT Wizard Management - Discounts selected - No Level provided
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select Volume/Quantity or Dollar Discounts BUT DO NOT Select Level
    #Then Error message is displayed: /The "Level" for "Volume/Quantity or Dollar Discounts" is required/
#
  #Scenario: FPT Wizard Management - Discounts NOT Required - No Volume/Quantity or Dollar Discounts
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select NO Volume/Quantity or Dollar Discounts for my offer
    #Then "Discount" details are NOT required and corresponding menu is NOT displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Delivery selected - No Level Provided
    #Given I am on the Wizard Management page for an FPT Schedule
    #When Delivery option is selected BUT I DO NOT Select Level
    #Then Error message is displayed: /The "Level" for "Delivery" is required/
#
  #@wip
  #Scenario: FPT Wizard Management - Delivery Required at Offer Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #When Delivery option is selected and I select Delivery at the Offer Level
    #Then "Delivery" details are required and corresponding menu is displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Delivery Required at SIN Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #When Delivery option is selected and I select Delivery at the SIN Level
    #Then "Delivery" details are required and corresponding menu is displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Delivery Required in Product Template - Line Item Level
    #Given I am on the Wizard Management page for an FPT Schedule
    #When Delivery option is selected and I select Delivery at the Line Item Level
    #Then "Delivery" details are required and provided in the product template 
#
  #@wip
  #Scenario: FPT Wizard Management - Contractor Warranty NOT Required
    #Given I am on the Wizard Management page for an FPT Schedule
#    And I add at least one SIN and complete Goods and Services details
    #When I select NO Contractor Warranty for my offer
    #Then "Contractor Warranty" details are NOT required and corresponding menu is NOT displayed
#
  #@wip
   #Scenario: FPT Wizard Management - Contractor Warranty Required
    #Given I am on the Wizard Management page for an FPT Schedule
#    And I add at least one SIN and complete Goods and Services details
    #When I select Contractor Warranty for my offer
    #Then "Contractor Warranty" details are required and corresponding menu is displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Zonal Pricing NOT Required
    #Given I am on the Wizard Management page for an FPT Schedule
#    And I add at least one SIN and complete Goods and Services details
    #When I select NO Zonal Pricing for my offer
    #Then "Zonal Pricing" details are NOT required and corresponding menu is NOT displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Zonal Pricing Required
    #Given I am on the Wizard Management page for an FPT Schedule
#    And I add at least one SIN and complete Goods and Services details
    #When I select Zonal Pricing for my offer
    #Then "Zonal Pricing" details are required and corresponding menu is displayed
    #And "Zonal Pricing" details are required and provided in the product template 
#
  #@wip
  #Scenario: FPT Wizard Management - Special Charges NOT Required
    #Given I am on the Wizard Management page for an FPT Schedule
#    And I add at least one SIN and complete Goods and Services details
    #When I select NO Special Charges for my offer
    #Then "Special Charges" details are NOT required and corresponding menu is NOT displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Special Charges Required
    #Given I am on the Wizard Management page for an FPT Schedule
#    And I add at least one SIN and complete Goods and Services details
    #When I select Special Charges for my offer
    #Then "Special Charges" details are required and corresponding menu is displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Special Features NOT Required
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select NO Special Features for my offer
    #Then "Special Features" details are NOT required and corresponding menu is NOT displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Special Features Required
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select Special Features for my offer
    #Then "Special Features" details are required and corresponding menu is displayed
#
  #@wip
  #Scenario: FPT Wizard Management - Accessories NOT Required in Product Template
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select NO Accessories for my offer
    #Then "Accessories" are NOT required and not included in the product template 
#
  #@wip
  #Scenario: FPT Wizard Management - Accessories Required in Product Template
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select Special Features for my offer
    #Then "Accesories" details are required and provided in the product template 
#
  #@wip
  #Scenario: FPT Wizard Management - Options NOT Required in Product Template
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select NO Options for my offer
    #Then "Options" are NOT required and not included in the product template 
#
  #@wip
  #Scenario: FPT Wizard Management - Options Required in Product Template
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select Options for my offer
    #Then "Options" details are required and provided in the product template     
    #
  #@wip
  #Scenario: FPT Wizard Management - Photos NOT Required in Product Template
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select NO Photos for my offer
    #Then "Photos" are NOT required and not included in the product template 
#
  #@wip
  #Scenario: FPT Wizard Management - Photos Required in Product Template
    #Given I am on the Wizard Management page for an FPT Schedule
    #When I select Photos for my offer
    #Then "Photos" details are required and provided in the product template         