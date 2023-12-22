package car.accessories;

import java.sql.SQLException;

import car.accessories.SendNotificationViaEmail;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class sendEmail 
{
	SendNotificationViaEmail  email = new SendNotificationViaEmail();
	@Given("The customer request some instllation")
	public void the_customer_request_some_instllation()
	{
		
	}

	@When("the request is complete")
	public void the_request_is_complete() 
	{
	   
	}

	@Then("send notification to the customer via email")
	public void send_notification_to_the_customer_via_email() 
	{
	  
		  String CustomerEmail="ahmad@gmail.com";
		  String Message ="Your installation request is complete. Thank you for choosing us.";
		  email.sendNotificationToCustomer(CustomerEmail, Message);
	}

	@Given("The installer receives customer instllation")
	public void the_installer_receives_customer_instllation() 
	{
	    
	}

	@Then("send notification to the installer via email")
	public void send_notification_to_the_installer_via_email() 
	{	   
		   String InstallerEmail="mohammed@gmail.com";
		   String Message="You have a new installation request.";
		   email.sendNotificationToInstaller( Message);  
	}

}
