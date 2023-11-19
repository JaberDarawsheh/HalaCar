#Given a customer is logged in
#And there are available installers
Feature: installation request




Scenario: Customer submits an installation request
Given a customer is logged in
And there are available installers
When the customer requests installation for a specific product
And provides installation details:
Then the installation request is submitted successfully


Scenario: Installer schedules an appointment
Given an installation request is Pending
When an installer schedules an appointment for the request
Then the appointment is scheduled successfully
And the request status is Scheduled

Scenario: Customer views the installation request status
When the customer checks the status of their installation request
Then the request status is displayed as Scheduled

Scenario: Installer marks the installation as complete
Given an appointment is Scheduled
When the installer completes the installation
Then the installation request status is updated to completed

Scenario: Customer views the completed installation request

Then the request status is displayed as completed

Scenario: Installer marks the installation as canceled
Given an appointment is scheduled
When the installer cancels the installation
Then the installation request status is updated to canceled

Scenario: Customer views the canceled installation request

Then the request status is displayed as canceled