package CarAccessiores;


import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserRegisteration {
	private static final Logger logger = Logger.getLogger(UserRegisteration.class.getName());
	String user_email, user_password , user_type;
	registeration regObj;
	public UserRegisteration() // initilazation this class
	{
		regObj=new registeration();
	}
	
	@Given("a user is on the registration page")
	public void a_user_is_on_the_registration_page() 
	{
		
	}

	@When("they provide valid registration details, including {string} and {string}")
	public void they_provide_valid_registration_details_including_and(String user_email, String user_password) {
	   this.user_email=user_email;
	   this.user_password=user_password;
	   this.user_type = user_type;
	}
	@Then("the enterd details should be sored to DB")
	public void the_enterd_details_should_be_sored_to_db() {
	    try {
			regObj.setData(user_email , user_password , user_type);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Then("should be see registration confirm message")
	public void should_be_see_registration_confirm_message()
	{
	    logger.log(Level.INFO,"Sign up Done Successfully");
	}

}
