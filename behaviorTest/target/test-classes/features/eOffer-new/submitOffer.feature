Feature: Submit eOffer
  
  As a vendor
  I need to submit my eOffer
  so that my offer can be evaluated

  Scenario: Submit eOffer for non-FPT schedule with TDR SIN in GSA Test env
    Given I have completed all non-FPT Offer sections
    When all menu items are complete
    Then documents can be reviewed

  #This scenario submits offer so PLEASE BE CAREFUL
  #Same data cannot be used again


  @fastLaneN
  Scenario: Submit eOffer successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application.  Your request for submission has been received. You will be notified by email when review and/or eSignature is required. "

  @tdrYes  
  Scenario: Submit eOffer with TDR yes successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application. Your request for submission has been received. You will be notified by email when review and/or eSignature is required."

  #And Offer Id is displayed in my Submitted eOffers list
  @tdrNo  
  Scenario: Submit eOffer with TDR no successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application. Your request for submission has been received. You will be notified by email when review and/or eSignature is required."


  @exception 
  Scenario: Submit eOffer with exception successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application. Your request for submission has been received. You will be notified by email when review and/or eSignature is required."

  @subK 
  Scenario: Submit eOffer with subK successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application. Your request for submission has been received. You will be notified by email when review and/or eSignature is required."

  @fastLaneY
  Scenario: Submit fastLane eOffer successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application. Your request for submission has been received. You will be notified by email when review and/or eSignature is required."

  @fastLaneN 
  Scenario: Submit fastLaneN eOffer successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application. Your request for submission has been received. You will be notified by email when review and/or eSignature is required."
  
  @withoutIFF  @submitOffer
  Scenario: Submit eOffer without iffClause successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application. Your request for submission has been received. You will be notified by email when review and/or eSignature is required."
  
  @smallBusiness @submitOffer
  Scenario: Submit eOffer with small business successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application. Your request for submission has been received. You will be notified by email when review and/or eSignature is required."
  
  @SBSAWithNonSBSA @submitOffer
  Scenario: Submit eOffer with small business and non small business successful
    Given I am on the Final Review page
    When I complete ALL Disclaimer questions with valid responses
    And I submit my eOffer
    Then offer is submitted and message is displayed: "Thank you for using our application. Your request for submission has been received. You will be notified by email when review and/or eSignature is required."
  
  #For FPT offers the documents cannot be reviewed due to application bug:
  #UI shows complete all reqd information before submitting even though all left menu items are complete
  #@fileUpload  @fpt
  #Scenario: FPT Submit eOffer with Products Only: Review eOffer successful
    #Given I have completed all FPT Offer sections
    #When all menu items are complete
  #Then documents can be reviewed
  #
  #
  #@gsaTest @fpt
  #Scenario: FPT Submit eOffer with Products and Services: Review eOffer successful
    #Given I have completed all FPT Offer sections
    #When all menu items are complete
#
  #This scenario has special character data added in form entry
  #@formEntry @skip  @fpt
  #Scenario: FPT Submit eOffer: Review eOffer successful using form entry
    #Given I have completed all FPT Offer sections
    #When all menu items are complete
#
  #
  #@edi @fpt
  #Scenario: FPT Submit eOffer: Review eOffer with special characters is successful
    #Given I have completed all FPT Offer sections
    #When all menu items are complete
#
  #
  #@skip @fpt
  #Scenario: FPT Submit eOffer: Review eOffer successful with flagged products
    #Given I have completed all FPT Offer sections
    #When all menu items are complete
#
  #@skip
  #Scenario: Submit eOffer for non-FPT schedule with TDR SIN
    #Given I have completed all non-FPT Offer sections
    #When all menu items are complete
