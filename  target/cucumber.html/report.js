$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("InstallationRequest.feature");
formatter.feature({
  "comments": [
    {
      "line": 1,
      "value": "#Given a customer is logged in"
    },
    {
      "line": 2,
      "value": "#And there are available installers"
    }
  ],
  "line": 3,
  "name": "installation request",
  "description": "",
  "id": "installation-request",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 8,
  "name": "Customer submits an installation request",
  "description": "",
  "id": "installation-request;customer-submits-an-installation-request",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 9,
  "name": "a customer is logged in",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "there are available installers",
  "keyword": "And "
});
formatter.step({
  "line": 11,
  "name": "the customer requests installation for a specific product",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "provides installation details:",
  "keyword": "And "
});
formatter.step({
  "line": 13,
  "name": "the installation request is submitted successfully",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 16,
  "name": "Installer schedules an appointment",
  "description": "",
  "id": "installation-request;installer-schedules-an-appointment",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 17,
  "name": "an installation request is Pending",
  "keyword": "Given "
});
formatter.step({
  "line": 18,
  "name": "an installer schedules an appointment for the request",
  "keyword": "When "
});
formatter.step({
  "line": 19,
  "name": "the appointment is scheduled successfully",
  "keyword": "Then "
});
formatter.step({
  "line": 20,
  "name": "the request status is Scheduled",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 22,
  "name": "Customer views the installation request status",
  "description": "",
  "id": "installation-request;customer-views-the-installation-request-status",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 23,
  "name": "the customer checks the status of their installation request",
  "keyword": "When "
});
formatter.step({
  "line": 24,
  "name": "the request status is displayed as Scheduled",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 26,
  "name": "Installer marks the installation as complete",
  "description": "",
  "id": "installation-request;installer-marks-the-installation-as-complete",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 27,
  "name": "an appointment is Scheduled",
  "keyword": "Given "
});
formatter.step({
  "line": 28,
  "name": "the installer completes the installation",
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "the installation request status is updated to completed",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 31,
  "name": "Customer views the completed installation request",
  "description": "",
  "id": "installation-request;customer-views-the-completed-installation-request",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 33,
  "name": "the request status is displayed as completed",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 35,
  "name": "Installer marks the installation as canceled",
  "description": "",
  "id": "installation-request;installer-marks-the-installation-as-canceled",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 36,
  "name": "an appointment is scheduled",
  "keyword": "Given "
});
formatter.step({
  "line": 37,
  "name": "the installer cancels the installation",
  "keyword": "When "
});
formatter.step({
  "line": 38,
  "name": "the installation request status is updated to canceled",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 40,
  "name": "Customer views the canceled installation request",
  "description": "",
  "id": "installation-request;customer-views-the-canceled-installation-request",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 42,
  "name": "the request status is displayed as canceled",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.uri("Notifications.feature");
formatter.feature({
  "line": 2,
  "name": "Notifications",
  "description": "",
  "id": "notifications",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 4,
  "name": "Send Notificatios to customer via email",
  "description": "",
  "id": "notifications;send-notificatios-to-customer-via-email",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "The customer request some instllation",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "the request is complete",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "send notification to the customer via email",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 10,
  "name": "Send Notification to installer via email",
  "description": "",
  "id": "notifications;send-notification-to-installer-via-email",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 11,
  "name": "The installer receives customer instllation",
  "keyword": "Given "
});
formatter.step({
  "line": 12,
  "name": "the request is complete",
  "keyword": "When "
});
formatter.step({
  "line": 13,
  "name": "send notification to the installer via email",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.uri("ProductCatalog.feature");
formatter.feature({
  "line": 2,
  "name": "Product Catalog",
  "description": "",
  "id": "product-catalog",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 4,
  "name": "View Product Categories",
  "description": "",
  "id": "product-catalog;view-product-categories",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "the user is login to the system",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "they request the product categories",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "they should see detailed product information including description, images, price, and availability",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 9,
  "name": "Product Catalog Search and Filters",
  "description": "",
  "id": "product-catalog;product-catalog-search-and-filters",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 10,
  "name": "the user is login to the system",
  "keyword": "Given "
});
formatter.step({
  "line": 11,
  "name": "the user serrch for a product (e.g., \"Matur\" and set filters for \"Price\" to \"5000\"",
  "keyword": "When "
});
formatter.step({
  "line": 12,
  "name": "should see all product  that in price equal to 5000 and category to electronics",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 14,
  "name": "Product Catalog Search and Filters",
  "description": "",
  "id": "product-catalog;product-catalog-search-and-filters",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 15,
  "name": "the user is login to the system",
  "keyword": "Given "
});
formatter.step({
  "line": 16,
  "name": "the user serrch for product (ex: \"banana\") does not found in out company",
  "keyword": "When "
});
formatter.step({
  "line": 17,
  "name": "the user should have error message",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 19,
  "name": "User does not login to the system",
  "description": "",
  "id": "product-catalog;user-does-not-login-to-the-system",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 20,
  "name": "the user with out login",
  "keyword": "Given "
});
formatter.step({
  "line": 21,
  "name": "the user want to see product catalog",
  "keyword": "When "
});
formatter.step({
  "line": 22,
  "name": "the user should have waning message",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.uri("UserLogin.feature");
formatter.feature({
  "line": 2,
  "name": "User Login",
  "description": "",
  "id": "user-login",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 4,
  "name": "User successfully login",
  "description": "",
  "id": "user-login;user-successfully-login",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "the user_email is \"jaberDar@najah.edu\"",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "the user_password is \"123455\"",
  "keyword": "And "
});
formatter.step({
  "line": 7,
  "name": "the user enters valid credentials (useremail and password)",
  "keyword": "When "
});
formatter.step({
  "line": 8,
  "name": "user login successfully",
  "keyword": "Then "
});
formatter.step({
  "line": 9,
  "name": "should see a welcome to the car accessories company",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 11,
  "name": "User enters invalid user_email",
  "description": "",
  "id": "user-login;user-enters-invalid-user-email",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 12,
  "name": "the user_email is \"Mohammed@najah.edu\"",
  "keyword": "Given "
});
formatter.step({
  "line": 13,
  "name": "the user_password is \"123455\"",
  "keyword": "And "
});
formatter.step({
  "line": 14,
  "name": "the user enters an invalid useremail",
  "keyword": "When "
});
formatter.step({
  "line": 15,
  "name": "the user should see an Incorrect userEmail",
  "keyword": "Then "
});
formatter.step({
  "line": 16,
  "name": "login faild",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 18,
  "name": "User enters vaild user_email invalid user_password",
  "description": "",
  "id": "user-login;user-enters-vaild-user-email-invalid-user-password",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 19,
  "name": "the user_email is \"jaberDar@najah.edu\"",
  "keyword": "Given "
});
formatter.step({
  "line": 20,
  "name": "the user_password is \"000000\"",
  "keyword": "And "
});
formatter.step({
  "line": 21,
  "name": "the user enters an invalid userpassword",
  "keyword": "When "
});
formatter.step({
  "line": 22,
  "name": "the user should see an Incorrect Password",
  "keyword": "Then "
});
formatter.step({
  "line": 23,
  "name": "login faild",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 25,
  "name": "User leaves user_email is empty",
  "description": "",
  "id": "user-login;user-leaves-user-email-is-empty",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 26,
  "name": "the user_email is \"\"",
  "keyword": "Given "
});
formatter.step({
  "line": 27,
  "name": "the user_password is \"123455\"",
  "keyword": "And "
});
formatter.step({
  "line": 28,
  "name": "the user enters an invalid userEmail",
  "keyword": "When "
});
formatter.step({
  "line": 29,
  "name": "the user should see an userEmail is empty",
  "keyword": "Then "
});
formatter.step({
  "line": 30,
  "name": "login faild",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 32,
  "name": "User leaves user_password is empty",
  "description": "",
  "id": "user-login;user-leaves-user-password-is-empty",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 33,
  "name": "the user_email is \"jaber@najah.edu\"",
  "keyword": "Given "
});
formatter.step({
  "line": 34,
  "name": "the user_password is \"\"",
  "keyword": "And "
});
formatter.step({
  "line": 35,
  "name": "the user enters an invalid user_pawword",
  "keyword": "When "
});
formatter.step({
  "line": 36,
  "name": "the user should see an password is empty",
  "keyword": "Then "
});
formatter.step({
  "line": 37,
  "name": "login faild",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 39,
  "name": "User leaves user_email and user_password is empty",
  "description": "",
  "id": "user-login;user-leaves-user-email-and-user-password-is-empty",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 40,
  "name": "the user_email is \"\"",
  "keyword": "Given "
});
formatter.step({
  "line": 41,
  "name": "the user_password is \"\"",
  "keyword": "And "
});
formatter.step({
  "line": 42,
  "name": "the user enters an invalid userEmail and userPassword",
  "keyword": "When "
});
formatter.step({
  "line": 43,
  "name": "the user should see an userEmail and password is empty",
  "keyword": "Then "
});
formatter.step({
  "line": 44,
  "name": "login faild",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.uri("UserRoles.feature");
formatter.feature({
  "line": 1,
  "name": "User Roles",
  "description": "",
  "id": "user-roles",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 3,
  "name": "Admin Manages Users",
  "description": "",
  "id": "user-roles;admin-manages-users",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 4,
  "name": "an Admin is logged into the system",
  "keyword": "Given "
});
formatter.step({
  "line": 5,
  "name": "the Admin navigates to the user role management section",
  "keyword": "When "
});
formatter.step({
  "line": 6,
  "name": "the Admin should be able to add a new user",
  "keyword": "Then "
});
formatter.step({
  "line": 7,
  "name": "the Admin should be able to edit an existing user",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "the Admin should be able to delete a user",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 10,
  "name": "Customer Browses Products and Makes Purchases",
  "description": "",
  "id": "user-roles;customer-browses-products-and-makes-purchases",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 11,
  "name": "a Customer is logged into the system",
  "keyword": "Given "
});
formatter.step({
  "line": 12,
  "name": "the Customer browses the product catalog",
  "keyword": "When "
});
formatter.step({
  "line": 13,
  "name": "the Customer should be able to view detailed product information",
  "keyword": "Then "
});
formatter.step({
  "line": 14,
  "name": "the Customer should be able to add the product to the cart",
  "keyword": "And "
});
formatter.step({
  "line": 15,
  "name": "the Customer should be able to proceed to checkout",
  "keyword": "And "
});
formatter.step({
  "line": 16,
  "name": "the Customer should be able to complete the purchase",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 18,
  "name": "Customer Views Order History",
  "description": "",
  "id": "user-roles;customer-views-order-history",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 19,
  "name": "a Customer is logged into the system",
  "keyword": "Given "
});
formatter.step({
  "line": 20,
  "name": "the Customer accesses their user profile",
  "keyword": "When "
});
formatter.step({
  "line": 21,
  "name": "views their order history",
  "keyword": "And "
});
formatter.step({
  "line": 22,
  "name": "the Customer should see a list of their past orders",
  "keyword": "Then "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.scenario({
  "line": 24,
  "name": "Installer Views Installation Requests and Schedules Appointments",
  "description": "",
  "id": "user-roles;installer-views-installation-requests-and-schedules-appointments",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 25,
  "name": "an Installer is logged into the system",
  "keyword": "Given "
});
formatter.step({
  "line": 26,
  "name": "the Installer navigates to the installation request section",
  "keyword": "When "
});
formatter.step({
  "line": 27,
  "name": "the Installer should be able to view a list of installation requests",
  "keyword": "Then "
});
formatter.step({
  "line": 28,
  "name": "the Installer should be able to accept or reject installation requests",
  "keyword": "And "
});
formatter.step({
  "line": 29,
  "name": "the Installer should be able to schedule appointments for accepted requests",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.uri("userRegisteration.feature");
formatter.feature({
  "line": 2,
  "name": "User Registeration via Email and Password",
  "description": "",
  "id": "user-registeration-via-email-and-password",
  "keyword": "Feature"
});
formatter.scenario({
  "line": 4,
  "name": "Successful User Registration",
  "description": "",
  "id": "user-registeration-via-email-and-password;successful-user-registration",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 5,
  "name": "a user is on the registration page",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "they provide valid registration details, including \"jaber@najah.edu\" and \"123455\"",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "the enterd details should be sored to DB",
  "keyword": "Then "
});
formatter.step({
  "line": 8,
  "name": "should be see registration confirm message",
  "keyword": "And "
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
formatter.match({});
formatter.result({
  "status": "undefined"
});
});