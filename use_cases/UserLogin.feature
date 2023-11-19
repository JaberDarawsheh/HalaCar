
Feature: User Login 

  Scenario: User successfully login 
    Given the user_email is "jaberDar@najah.edu"
    And the user_password is "123455"
    When the user enters valid credentials (useremail and password)
    Then user login successfully
    And should see a welcome to the car accessories company
    
 Scenario: User enters invalid user_email  
    Given the user_email is "Mohammed@najah.edu"
    And the user_password is "123455"
    When the user enters an invalid useremail
    Then the user should see an Incorrect userEmail
    And login faild
    
  Scenario: User enters vaild user_email invalid user_password 
    Given the user_email is "jaberDar@najah.edu"
    And the user_password is "000000"
    When the user enters an invalid userpassword
    Then the user should see an Incorrect Password
    And login faild
    
  Scenario: User leaves user_email is empty
    Given the user_email is ""
    And the user_password is "123455"
    When the user enters an invalid userEmail
    Then the user should see an userEmail is empty 
    And login faild
    
  Scenario: User leaves user_password is empty
    Given the user_email is "jaber@najah.edu"
    And the user_password is ""
    When the user enters an invalid user_pawword
    Then the user should see an password is empty
    And login faild    
    
  Scenario: User leaves user_email and user_password is empty
    Given the user_email is ""
    And the user_password is ""
    When the user enters an invalid userEmail and userPassword
    Then the user should see an userEmail and password is empty
    And login faild 










 