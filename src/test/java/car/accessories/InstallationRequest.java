package car.accessories;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InstallationRequest {
    private static final Logger logger = Logger.getLogger(ProductCat.class.getName());
    Customer customer;
    Installer installer;
    boolean Customer_logged;
    boolean installer_logged;
    UserLoginPage loginObj;
    String user_email , user_password;
    int id;
    String carModel;
    String date;
    String time;
    String status;
    static String installer_email="iim7md28@gmail.com";
    static String installer_password="12345";
    @Given("a customer is logged in")
    public void a_customer_is_logged_in() {
        user_email="khalidsultan@gmail.com";
        user_password="54321";
        loginObj=new UserLoginPage(user_email,user_password);
        try {
            loginObj.isValidCredentials(user_email,user_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Customer_logged =loginObj.isCustomerLogged();
        if(Customer_logged) {
            customer =new Customer(user_email,user_password);
        }else {
            fail();//the test case will not pass if the customer login failed

        }

    }
    @Given("there are available installers")
    public void there_are_available_installers() {
        try {
            loginObj.isValidCredentials(installer_email,installer_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        installer_logged =loginObj.isInstallerLogged();
        if(installer_logged) {
            installer =new Installer(installer_email,installer_password);
        }else {
            fail("there is no available installer");//the test case will not pass if the installer login failed

        }


    }
    @When("the customer requests installation for a specific product")
    public void the_customer_requests_installation_for_a_specific_product() {
        id=1;//assume the product the Customer want to install has the id 1

    }
    @When("provides installation details:")
    public void provides_installation_details() {
        date="22-11-2023";//the date format the user should enter the date in
        carModel="skoda octavia 2019";//the user should enter the car model as a string with no specific format

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
    @Given("an installation request is Pending")
    public void an_installation_request_is_pending() {
        installer=new Installer(installer_email,installer_password);
        try {
            installer.showPending();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }

    }
    @When("an installer schedules an appointment for the request")
    public void an_installer_schedules_an_appointment_for_the_request() {
        //assume the request number is 1 and the time the installer want performe his task is 12:31:00
        id =9;
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
        assertTrue(status.equals("scheduled"));

    }

    @When("the customer checks the status of their installation request")
    public void the_customer_checks_the_status_of_their_installation_request() {

    }

    @Then("the request status is displayed as Scheduled")
    public void the_request_status_is_displayed_as_Scheduled() {
        // Write code here that turns the phrase above into concrete actions
        try {

            customer=new Customer("s12028647@stu.najah.edu","1234567");
            customer.showScheduled();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
    @Given("an appointment is Scheduled")
    public void an_appointment_is_Scheduled() {
        installer=new Installer(installer_email,installer_password);


    }
    @When("the installer completes the installation")
    public void the_installer_completes_the_installation() {
        installer=new Installer(installer_email,installer_password);
        id =9;
        try {
            installer.setCompleted(id);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("m3lm ana hon ");
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
        assertTrue(status.equals("completed"));
    }

    @Then("the request status is displayed as completed")
    public void the_request_status_is_displayed_as_completed() {
        customer=new Customer("s12028647@stu.najah.edu","1234567");
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
        installer=new Installer(installer_email,installer_password);
        try {
            installer.setCanceled(id);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("i am here");
        }
    }
    @Then("the installation request status is updated to canceled")
    public void the_installation_request_status_is_updated_to_canceled() {
        installer=new Installer(installer_email,installer_password);
        id=6;
        try {
            status=installer.getStatus(id);
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(status.equals("canceled"));

    }

    @Then("the request status is displayed as canceled")
    public void the_request_status_is_displayed_as() {
        customer=new Customer("s12028647@stu.najah.edu","1234567");
        try {
            customer.showCanceled();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }
}
