
Feature: Notifications

  Scenario: Send Notificatios to customer via email 
    Given The customer request some instllation 
    When the request is complete
    Then send notification to the customer via email
    
    
  Scenario:  Send Notification to installer via email
   Given The installer receives customer instllation
   When  the request is complete
   Then send notification to the installer via email
    