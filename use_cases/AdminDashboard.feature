Feature: Admin Dashboard Management

  Scenario: Admin Adds and Updates Product listing
    Given the admin is logged in and on the dashboard
    When the admin selects the option to manage product listing
    Then the admin should see a list of current products
    And should be able to add a new product
    And should be able to edit details of existing products
    And should be able to delete a product

  Scenario: Admin Manages Customer Accounts
    Given the admin is logged in and on the dashboard
    When the admin selects the option to manage customer accounts
    Then the admin should see a list of registered customers with detailes
    And should be able to edit customer information
    And should be able to delete a customer account

  Scenario: Admin Manages Installation Appointments
    Given the admin is logged in and on the dashboard
    When the admin accesses the installation management section
    Then the admin should see a list of scheduled installation appointments
    And should be able to reschedule appointments
    And should be able to mark appointments as completed or canceled