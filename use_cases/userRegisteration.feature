
Feature: User Registeration via Email and Password
  
  Scenario: Successful User Registration
    Given a user is on the registration page
    When they provide valid registration details, including "jaber@najah.edu" and "123455"
    Then the enterd details should be sored to DB 
    And should be see registration confirm message
    
    
 