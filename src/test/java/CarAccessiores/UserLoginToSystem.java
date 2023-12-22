package CarAccessiores;



import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class UserLoginToSystem {
	 private static final Logger logger = Logger.getLogger(UserLoginToSystem.class.getName());
     String user_email , user_password;
     UserLoginPage loginObj;
     public UserLoginToSystem()  
        {
    	 loginObj=new UserLoginPage("Darawsheh@najah.edu","123455");
     	}
	@Given("the user_email is {string}")
	public void the_user_email_is(String user_email) {
	   this.user_email=user_email;
	}

	@Given("the user_password is {string}")
	public void the_user_password_is(String user_password) {
	   this.user_password=user_password;
	}

	@When("the user enters valid credentials \\(useremail and password)")
	public void the_user_enters_valid_credentials_useremail_and_password() {
	   
			try {
				loginObj.is_valid_credentials(user_email,user_password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	@Then("user login successfully")
	public void user_login_successfully() {
		 boolean login_flag=loginObj.is_user_logged();
		 assertEquals(true,login_flag);
	}

	private Object is_user_logged() {
	
		return null;
	}
	@Then("should see a welcome to the car accessories company")
	public void should_see_a_welcome_to_the_car_accessories_company() {
	    
		logger.log(Level.INFO,"Welcome to the car accessories company");
	}

	@When("the user enters an invalid useremail")
	public void the_user_enters_an_invalid_useremail() {
		user_email="mohammed@najah.edu";
	    user_password="123455";
       
			try {
				loginObj.is_valid_credentials(user_email, user_password);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
	}

	@Then("the user should see an Incorrect userEmail")
	public void the_user_should_see_an_incorrect_user_email() {
		assertEquals(false,loginObj.is_user_logged());
	    logger.log(Level.INFO,"Incorrect user email!");
	}

	@Then("login faild")
	public void login_faild() {
		 boolean login_flag=loginObj.is_user_logged();
	   assertEquals(false,login_flag);
	}

	@When("the user enters an invalid userpassword")
	public void the_user_enters_an_invalid_userpassword() {
	   user_email="jaber@najah.edu";
	   user_password="000000";
	   try {
		   loginObj.is_valid_credentials(user_email,user_password);
	   }catch(SQLException e) {
		   e.printStackTrace();
	   }
	}

	@Then("the user should see an Incorrect Password")
	public void the_user_should_see_an_incorrect_password() {
	     assertEquals(false,loginObj.is_user_logged());
	     logger.log(Level.INFO,"Incorrect user password!");
	}

	@When("the user enters an invalid userEmail")
	public void the_user_enters_an_invalid_user_email() {
		user_email="";
		user_password="123455";
		try {
			loginObj.is_valid_credentials(user_email, user_password);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Then("the user should see an userEmail is empty")
	public void the_user_should_see_an_user_email_is_empty() {
	    assertEquals(false,loginObj.is_user_logged());
	    logger.log(Level.INFO,"user email is empty!");
	}

	@When("the user enters an invalid user_pawword")
	public void the_user_enters_an_invalid_user_pawword() {
		user_email="jaber@najah.edu";
		user_password="";
		try {
			loginObj.is_valid_credentials(user_email, user_password);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Then("the user should see an password is empty")
	public void the_user_should_see_an_password_is_empty() {
	   assertEquals(false,loginObj.is_user_logged());
	   logger.log(Level.INFO,"user password is empty!");
	}

	@When("the user enters an invalid userEmail and userPassword")
	public void the_user_enters_an_invalid_user_email_and_user_password(){
	    user_email=""; 
	    user_password=""; 
	    try {
	    	loginObj.is_valid_credentials(user_email,user_password);
	    }catch(SQLException e) {
	    	e.printStackTrace();
	    }
	}

	@Then("the user should see an userEmail and password is empty")
	public void the_user_should_see_an_user_email_and_password_is_empty() {
	   assertEquals(false,loginObj.is_user_logged());
	}

}
