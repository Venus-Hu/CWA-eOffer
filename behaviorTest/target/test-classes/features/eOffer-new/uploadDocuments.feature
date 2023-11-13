Feature: Upload Documents to the Offer
  
  As a vendor
  I need to Upload Documents to the Offer
  In order to provide supplemental information

  @int @all_env
  Scenario: Upload Document successful
    Given I am on the Upload Documents page
    When I upload a document in valid format and provide document details
    Then the document(s) are uploaded and displayed with document type and name in the Documents List
    And "Upload Documents" menu is complete

  @all_env @int
  Scenario: Upload Document fails - No document selected
    Given I am on the Upload Documents page
    When I upload a document but DO NOT select document from computer
    Then Message is displayed: "Please select a file to upload."

  @all_env
  Scenario: Upload Document fails - Invalid file format
    Given I am on the Upload Documents page
    When I upload a document with an INVALID file format
    Then Error message is displayed: "This file type is not allowed for Upload"

  #github file size limitation
  @all_env @skip
  Scenario: Upload Document fails for file size exceeding the limits
    Given I am on the Upload Documents page
    When I upload a document with a file size greater than the required size
    Then Message is displayed: "Selected file to upload is greater than 100.0 MB. Only files of size less than or equal to 100.0 MB can be uploaded."

  @all_env
  Scenario: Complete Upload Documents
    Given I am on the Available Offerings page
    When I add at least one SIN and complete Available Offerings details
    Then the SIN details will be displayed on Available Offerings page
    And I select preponderance of work
    Then I am on the Upload Documents page
    Then I have uploaded ALL REQUIRED documents successfully
    And the document(s) are uploaded and displayed with document type and name in the Documents List
    And "Upload Documents" menu is complete

  @all_env @int
  Scenario: Delete Uploaded Document successful
    Given I am on the Upload Documents page
    And I have uploaded ALL REQUIRED documents successfully
    When I delete a document in the Documents List
    Then the document is removed from the Documents List

  @all_env
  Scenario: View Uploaded Document successful
    Given I am on the Upload Documents page
    And I have uploaded ALL REQUIRED documents successfully
    When I view a document in the Documents List
    Then the document is downloaded successfully

  @all_env
  Scenario: Upload Additional Document successful
    Given I am on the Upload Documents page
    When I upload a document in valid format and provide document details
    And I upload an additional document with same document type
    Then the document(s) are uploaded and displayed with document type and name in the Documents List

  #This is to upload a mass number of documents
  #Update data with the DUNS and schedule number for the offer you need to upload the mass files to and run
  @skip
  Scenario: Upload Mass Documents
    Given I am on the Upload Documents page
    When I upload a document in valid format and provide document details
    Then the document(s) are uploaded and displayed with document type and name in the Documents List
