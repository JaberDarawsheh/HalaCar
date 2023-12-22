package car.accessories;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import car.accessories.ProductCat;
import car.accessories.UserLoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class ProductCatalog {
    
	private static final Logger logger = Logger.getLogger(ProductCatalog.class.getName());
	UserLoginPage userLogin;
	ProductCat proCat;
	
	public ProductCatalog(ProductCat proCat)
	{
		userLogin = new UserLoginPage("sam@najah.edu","123541");
		this.proCat= proCat;
	}
	
	@Given("the user is login to the system")
	public void the_user_is_login_to_the_system() {
	    try {
	    	userLogin.is_valid_credentials("sam@najah.edu", "123541");
	    }
	    catch(SQLException exp)
	    {
	    	exp.printStackTrace();
	    }
	}

	@When("they request the product categories")
	public void they_request_the_product_categories() {
	   try {
		   proCat.system_is_found_catalog();
	   }
	   catch(SQLException expe)
	   {
		   expe.printStackTrace();
	   }
	}

	@Then("they should see detailed product information including description, images, price, and availability")
	public void they_should_see_detailed_product_information_including_description_images_price_and_availability() {
	    try
	    {
	    	proCat.show_products_catalog_toUser(userLogin);
	    }
	    catch(SQLException e)
	    {
	    	e.printStackTrace();
	    }
	}

	@When("the user search for a product \\(e.g., {string} and set filters for {string} to {string} and {string} to {string}")
	public void the_user_search_for_a_product_e_g_and_set_filters_for_to_and_to(String someProdct, String price, String amountOfMoney) {
	       try
	       {
	    	    proCat.search_and_show_filtered_result(someProdct,price);
	       }
	       catch(SQLException excption)
	       {
	    	   excption.printStackTrace();
	       }
	}

	@When("the user search for product \\(ex: {strangeProduct}) does not found in out company")
	public void the_user_search_for_product_ex_does_not_found_in_out_company(String strangeProduct) {
	   try
	   {
		   proCat.search_about_some_product(strangeProduct);
	   }
	   catch(SQLException ee)
	   {
		   ee.printStackTrace();
	   }
	   
	}

	@Then("the user should have error message")
	public void the_user_should_have_error_message()
	{
	 logger.log(Level.INFO,"You are search about product does not fount in Car Accessories Compamy");
	}

	@Given("the user with out login")
	public void the_user_with_out_login() {
	   try
	   {
		   userLogin.is_valid_credentials("smm@najah.edu", "123451");
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	}

	@When("the user want to see product catalog")
	public void the_user_want_to_see_product_catalog() {
	    try
	    {
	    	proCat.show_products_catalog_toUser(userLogin);
	    }
	    catch(SQLException expc)
	    {
	    	expc.printStackTrace();
	    }
	}

	@Then("the user should have waning message")
	public void the_user_should_have_waning_message() {
	    logger.log(Level.INFO,"You are not login to the system !");
	}

	
}
