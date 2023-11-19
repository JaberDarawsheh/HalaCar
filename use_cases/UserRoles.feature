Feature: User Roles

  Scenario: Admin Manages Users
    Given an Admin is logged into the system
    When the Admin navigates to the user role management section
    Then the Admin should be able to add a new user
    And the Admin should be able to edit an existing user
    And the Admin should be able to delete a user

  Scenario: Customer Browses Products and Makes Purchases
    Given a Customer is logged into the system
    When the Customer browses the product catalog
    Then the Customer should be able to view detailed product information
    And the Customer should be able to add the product to the cart
    And the Customer should be able to proceed to checkout
    And the Customer should be able to complete the purchase

  Scenario: Customer Views Order History
    Given a Customer is logged into the system
    When the Customer accesses their user profile
    And views their order history
    Then the Customer should see a list of their past orders

  Scenario: Installer Views Installation Requests and Schedules Appointments
    Given an Installer is logged into the system
    When the Installer navigates to the installation request section
    Then the Installer should be able to view a list of installation requests
    And the Installer should be able to accept or reject installation requests
    And the Installer should be able to schedule appointments for accepted requests
