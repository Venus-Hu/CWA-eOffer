#Feature: American Recovery and Reinvestment Act or ARRA Mod
  #
  #As a vendor
  #I want to identify if I accept all of the clauses within the American Recovery and Reinvestment Act as of 2009
  #So that contract can be amended correctly to utilize these funds 
#
  #@wip
  #Scenario: American Recovery and Reinvestment Act or ARRA Mod
    #Given I am on the Modifications List page
    #When I create an "American Recovery and Reinvestment Act(ARRA)" mod
    #Then mod is created successfully and "Terms & Conditions" menu is displayed
#
#
#There is an SCR to remove ARRA from the list of mod types available
#	@all_env @skip
  #Scenario: Submit American Recovery and Reinvestment Act or ARRA Mod: Review eMod successful
    #Given I am on the Terms and Conditions eMod "American Recovery and Reinvestment Act" page
    #When I complete ARRA details successfully
    #And all menu items are complete
    #Then I can perform Final Review of my eMod
#
  #@gsaTest @wip
  #Scenario: Submit American Recovery and Reinvestment Act or ARRA Mod: Submit eMod successful - GSA
    #Given I am on the Final Review page in eMod for ARRA mod
    #When I submit my eMod
    #Then eMod is submitted and message is displayed:  "Your eMod was successfully submitted"
    #And Modification Id is displayed in my Submitted eMods list  
    