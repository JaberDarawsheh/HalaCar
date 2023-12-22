package car.accessories;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstallationRequest {
    private static final Logger logger = Logger.getLogger(ProductCat.class.getName());
    Customer customer;
    car.accessories.Installer installer;
    boolean Customer_logged;
    boolean installer_logged;
    UserLoginPage loginObj;
    String user_email , user_password;
    int id;
    String carModel;
    String date;
    String time;
    String status;
    String installer_email="fathi@gmail.com";
    String installer_password="1234567";
    @Given("a customer is logged in")
    public void a_customer_is_logged_in() {
        try {
            loginObj.isValidCredentials(this.user_email,this.user_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Customer_logged =loginObj.isCustomerLogged();
        if(Customer_logged) {
            customer =new Customer(this.user_email,this.user_password);
        }else {
            fail();

        }

    }
    @Given("there are available installers")
    public void there_are_available_installers() {
        try {
            loginObj.isValidCredentials(this.installer_email,this.installer_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        installer_logged =loginObj.isInstallerLogged();
        if(installer_logged) {
            installer =new Installer(this.user_email,this.user_password);
        }else {
            fail("there is no available installer");

        }


    }
    @When("the customer requests installation for a specific product")
    public void the_customer_requests_installation_for_a_specific_product() {
        id=1;

    }
    @When("provides installation details:")
    public void provides_installation_details() {
        date="22-11-2023";
        carModel="skoda octavia 2019";

    }
    @Then("the installation request is submitted successfully")
    public void the_installation_request_is_submitted_successfully() {
        try {
            customer.installationRequest(id,carModel,date);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        } catch (ParseException e) {
            logger.log(Level.WARNING,"Invalid date format. Please enter a date in the format dd-MM-yyyy.");
            fail();
        }

    }
    @Given("an installation request is pending")
    public void an_installation_request_is_pending() {
        try {
            installer.showPending();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }

    }
    @When("an installer schedules an appointment for the request")
    public void an_installer_schedules_an_appointment_for_the_request() {
        id =1;
        time="12:31:00";
        try {
            installer.schedule(id,time);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }


    }

    @Then("the appointment is scheduled successfully")
    public void the_appointment_is_scheduled_successfully() {
        try {
            status=installer.getStatus(id);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Then("the request status is Scheduled")
    public void the_request_status_is_Scheduled() {
    	assertEquals("scheduled", status);


    }

    @Then("the request status is displayed as Scheduled")
    public void the_request_status_is_displayed_as_Scheduled() {
   
        try {
            customer.showScheduled();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Given("an appointment is Scheduled")
    public void an_appointment_is_Scheduled() {


    }
    @When("the installer completes the installation")
    public void the_installer_completes_the_installation() {
        try {
            installer.setCompleted(id);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }

    }
    @Then("the installation request status is updated to completed")
    public void the_installation_request_status_is_updated_to_completed() {
        try {
            status=installer.getStatus(id);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals("completed",status);
    }

    @Then("the request status is displayed as completed")
    public void the_request_status_is_displayed_as_completed() {
        try {
            customer.showCompleted();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Given("an appointment is scheduled")
    public void an_appointment_is_scheduled() {
        status="scheduled";

    }
    @When("the installer cancels the installation")
    public void the_installer_cancels_the_installation() {
        try {
            installer.setCanceled(id);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Then("the installation request status is updated to canceled")
    public void the_installation_request_status_is_updated_to_canceled() {
        try {
            status=installer.getStatus(id);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals("canceled",status);

    }

    @Then("the request status is displayed as canceled")
    public void the_request_status_is_displayed_as() {
        try {
            customer.showCanceled();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
}
