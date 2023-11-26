Feature: User Profile Editing

  Scenario: View User Profile Information
    Given the user is logged in
    When the user navigates to their profile
    Then they should see their personal information
    And they should see their order history
    And they should see their installation requests

  Scenario: Edit User Profile Information
    Given the user is logged in
    When the user selects the edit profile option
    And updates their contact information
    Then the changes should be saved successfully
    And the updated information should be visible in the profile

  Scenario: View Order History
    Given the user is logged in
    When the user accesses their profile's order history
    Then they should see a list of their past orders in details

  Scenario: View Installation Requests
    Given the user is logged in
    When the user checks their profile's installation requests
    Then they should see a list of their previous installation requests in details