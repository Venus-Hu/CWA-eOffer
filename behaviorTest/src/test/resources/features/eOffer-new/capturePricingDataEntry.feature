#Feature: Capture Pricing - On-screen data entry feature
  #
  #As a vendor who has wants to create an offer for FPT schedule
  #I need to capture pricing information for my product(s) using the form entry process
  #So that I can provide all of the details for my offer
#
#@formEntry @skip
  #Scenario: FPT Capture Pricing - using data entry
    #Given I am on the Capture Pricing Data entry page for an FPT Schedule
    #When I complete the data entry with pricing details for ALL SINs on my offer
    #And I validate the completed product data
    #Then "Capture Pricing" menu is complete
#
#No validations occur on Accessories and Special Charges so skipping them
#Validations for Pricing, Delivery, Dimensions & Shipping, Options and Zonal pricing
#@negative @formEntry @skip
#Scenario: FPT Capture Pricing - using invalid data for Form entry
    #Given I am on the Capture Pricing Data entry page for an FPT Schedule
    #When I complete the data entry with invalid data for ALL SINs on my offer
    #Then I validate the errors in completed product data on Form entry page
    #
#
#@negative @formEntry @skip
  #Scenario: FPT Capture Pricing - using data entry and template upload
   #Given I am on the Capture Pricing Download/Upload Template page for an FPT Schedule
    #And I download the product template
    #And I complete the template with pricing details for ALL SINs on my offer
     #And I upload and validate the completed product template
    #When I remove the photo details for ALL SINs on my offer using data entry
    #And I validate the completed product data
    #Then Error message is displayed: "At least one Product-to-Photo association is required for this Offer"
#
#@positive @wip @formEntry @skip
   #Scenario: FPT Capture Pricing - Enter data using template upload and validate it in data entry
     #Given I am on the Capture Pricing Download/Upload Template page for an FPT Schedule
    #And I download the product template
    #And I complete the template with pricing details for ALL SINs on my offer
     #When I upload and validate the completed product template
     #Then I verify the data on Form entry page