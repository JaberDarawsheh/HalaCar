
Feature: Product Catalog

  Scenario: View Product Categories
  Given the user is login to the system 
  When they request the product categories
  Then they should see detailed product information including description, images, price, and availability
  
  Scenario: Product Catalog Search and Filters
  Given the user is login to the system
  When the user serrch for a product (e.g., "Matur" and set filters for "Price" to "5000" 
  Then should see all product  that in price equal to 5000 and category to electronics
  
  Scenario: Product Catalog Search and Filters
  Given the user is login to the system
  When the user serrch for product (ex: "banana") does not found in out company
  Then the user should have error message
  
  Scenario: User does not login to the system
  Given the user with out login 
  When the user want to see product catalog
  Then the user should have waning message
  

