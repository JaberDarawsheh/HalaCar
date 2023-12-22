package car.accessories;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.SQLException;
import java.util.logging.Logger;

import static org.junit.Assert.fail;

public class UserProfile {

    private static final Logger logger = Logger.getLogger(UserRoles.class.getName());
    String user_email , user_password;
    int rid;
    Customer customer;
    String new_email;

    @Given("the user is logged in")
    public void the_user_is_logged_in() {
        user_email= "fathi@gmail.com";
        user_password="0000";
        customer=new Customer(user_email,user_password);
    }

    @When("the user navigates to their profile")
    public void the_user_navigates_to_their_profile() {


    }

    @Then("they should see their personal information")
    public void they_should_see_their_personal_information() {
        customer.ShowPersonalInfo();


    }

    @Then("they should see their order history")
    public void they_should_see_their_order_history() {
        try {
            customer.showHistory();
        } catch (SQLException e) {
            fail("some thing wrong in the showHistory method 1");
        }
    }

    @Then("they should see their installation requests")
    public void they_should_see_their_installation_requests() {
        try {
            customer.showAllRequests();
        } catch (SQLException e) {
            fail("some thing wrong with showAllRequests method");
        }
    }

    @When("the user selects the edit profile option")
    public void the_user_selects_the_edit_profile_option() {

    }

    @When("updates their contact information")
    public void updates_their_contact_information() {
        new_email="faredzamano@gmail.com";
    }

    @Then("the changes should be saved successfully")
    public void the_changes_should_be_saved_successfully() {
        try {
            customer.changeEmail(new_email);
        } catch (SQLException e) {
            fail("some thing wrong with changeEmail method");
        }
    }

    @Then("the updated information should be visible in the profile")
    public void the_updated_information_should_be_visible_in_the_profile() {
        customer.ShowPersonalInfo();
    }

    @When("the user accesses their profile's order history")
    public void the_user_accesses_their_profile_s_order_history() {

    }

    @Then("they should see a list of their past orders in details")
    public void they_should_see_a_list_of_their_past_orders_in_details() {
        try {
            customer.showHistory();
        } catch (SQLException e) {
            fail("some thing wrong in the showHistory method 2");
        }
    }

    @When("the user checks their profile's installation requests")
    public void the_user_checks_their_profile_s_installation_requests() {

    }

    @Then("they should see a list of their previous installation requests in details")
    public void they_should_see_a_list_of_their_previous_installation_requests() {
        try {
            customer.showCompleted();
        } catch (SQLException e) {
            fail("some thing wrong in the showCompleted method");
        }
    }

}
