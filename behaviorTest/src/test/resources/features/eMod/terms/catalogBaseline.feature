Feature: Catalog Baseline Mod

As a vendor
I want to create a common catalog baseline mod to my CCP contract

#@smokeTest @all_env @CCP
Scenario: Submit Catalog Baseline - Commercial Off The Shelf Products Mod: Submit eMod successful for CCP contract
  Given I am on the Terms and Conditions eMod "Catalog Baseline - Commercial Off The Shelf (COTS) Products" page
  When I respond to the comfirm partcipation in catalog baseline
  Then all menu items are complete
  And I can perform Final Review of my eMod
  
   @all_env @CCP
Scenario: Catalog Baseline - Commercial Off The Shelf Products Mod - Error when countinuing without participating in catalog baseline
  Given I am on the Terms and Conditions eMod "Catalog Baseline - Commercial Off The Shelf Products" page
  When I do NOT respond to the comfirm partcipation in catalog baseline
  Then Error message is displayed: "You must select Confirm Participation in Catalog Baseline"
  
  