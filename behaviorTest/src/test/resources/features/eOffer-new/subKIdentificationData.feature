Feature: SubContracting Plan - Identification Data
  
  As a vendor who has subcontracting opportunities
  I want to provide Identification Data
  So that I can summarize SubContracting Plan data


  @wip
  Scenario: SubK:  Identification Data section fails - Place Of Performance NOT provided
    Given I am on the Identification Data Page
    When I respond to Identification Data questions but DO NOT answer "Place Of Performance" question
  	Then Error message is displayed: "Please enter Place Of Performance"