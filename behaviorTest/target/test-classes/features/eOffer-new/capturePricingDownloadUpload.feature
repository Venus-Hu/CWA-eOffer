#@fpt
#Feature: Capture Pricing - File Upload feature
  #
  #As a vendor who has wants to create an offer for FPT schedule
  #I need to capture pricing information for my product(s) using the product template
  #So that I can provide all of the details for my offer
#
  #@int
  #@all_env @skip
  #Scenario: FPT Capture Pricing - Complete Download/Upload Template successful with all Wizard Mgmt Options
    #Given I am on the Capture Pricing Download/Upload Template page for an FPT Schedule
    #And I download the product template
    #When I complete the template with pricing details for ALL SINs on my offer
    #And I upload and validate the completed product template
    #Then "Capture Pricing" menu is complete
#
  ## Need to remove this scenario as we are moving away from saving errors in data file
  #Data comes from data.json file and errors are listed there too
  #@negative @skip
  #Scenario: FPT Capture Pricing - Complete Download/Upload Template with invalid data
    #Given I am on the Capture Pricing Download/Upload Template page for an FPT Schedule
    #And I download the product template
    #When I complete the template with pricing details for ALL SINs on my offer
    #And I try to upload the completed product template with invalid data
    #Then "Capture Pricing" menu is incomplete
#
  ## The data for this sceanrio comes from InvalidPricingData.xls file
  #@negative @skip
  #Scenario: FPT Capture Pricing - Complete Pricing sheet with invalid data
    #Given I am on the Capture Pricing Download/Upload Template page for an FPT Schedule
    #And I download the product template
    #When I update the template with invalid pricing details for "Pricing" sheet
    #And I try to upload the completed product template with invalid data
    #Then template document upload process fails and data errors are verified
#
  #Same scenario as above  different data
  ## The data for this sceanrio comes from InvalidPricingData.xls file
  #@negative @skip
  #Scenario: FPT Capture Pricing - Complete ALL sheets with invalid data
    #Given I am on the Capture Pricing Download/Upload Template page for an FPT Schedule
    #And I download the product template
    #When I update the template with invalid pricing details for "Pricing,Non Pricing,Photos" sheet
    #And I try to upload the completed product template with invalid data
    #Then template document upload process fails and data errors are verified
#
#	 # Adding TDR SIN and reponding with Yes, so following 4 fields are MISSING in template
    #*Commercial List Price,	*(Name)Best Priced Customer,	*Price Offered to Best Priced Customer,	*Discount Offered to Best Priced Customer
  #Scenario: FPT Capture Pricing - Add TDR SIN and respond with Yes
    #Given I am on the Capture Pricing Download/Upload Template page for an FPT Schedule
    #And I download the product template
    #When I complete the template with pricing details for ALL SINs on my offer
    #And I upload and validate the completed product template
    #Then "Capture Pricing" menu is complete
#		And Commercial Sales Practice details are not required in Solicitation Clauses
#		
    # Following 4 fields are present in template
    #*Commercial List Price,	*(Name)Best Priced Customer,	*Price Offered to Best Priced Customer,	*Discount Offered to Best Priced Customer
  #Scenario: FPT Capture Pricing - Add TDR SIN and respond with No
    #Given I am on the Capture Pricing Download/Upload Template page for an FPT Schedule
    #And I download the product template
    #When I complete the template with pricing details for ALL SINs on my offer
    #And I upload and validate the completed product template
    #Then "Capture Pricing" menu is complete
    #And Commercial Sales Practice details are required in Solicitation Clauses
    