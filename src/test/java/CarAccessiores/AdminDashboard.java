package CarAccessiores;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AdminDashboard {
    String user_email , user_password;
    int pid,rid;
    admin admin;
    //jaberDar@najah.edu
    //123455
    @Given("the admin is logged in and on the dashboard")
    public void the_admin_is_logged_in_and_on_the_dashboard() {
        user_email="jaberDar@najah.edu";
        user_password="123455";
        admin=new admin(user_email,user_password);
    }

    @When("the admin selects the option to manage product listing")
    public void the_admin_selects_the_option_to_manage_product_listing() {

    }

    @Then("the admin should see a list of current products")
    public void the_admin_should_see_a_list_of_current_products() {
        int flag=1;
        try {
            admin.showProductCatalog();
        } catch (SQLException e) {
            fail("some thing wrong with showProductCatalog method ");
            flag=0;
        }
        assertTrue(flag==1);
    }

    @Then("should be able to add a new product")
    public void should_be_able_to_add_a_new_product() {
        int flag=1;
        try {
            admin.addProduct("product name ","product type", 1500);
        } catch (SQLException e) {
            fail("some thing wrong with addProduct method ");
            flag=0;
        }
        assertTrue(flag==1);

    }

    @Then("should be able to edit details of existing products")
    public void should_be_able_to_edit_details_of_existing_products() {
        pid =5;
        int flag=1;
        try {
            admin.isUnavailable(pid);
        } catch (SQLException e) {
            fail("some thing wrong with isUnavailable method"+ e.getMessage());
            flag=0;
        }
        try {
            admin.changeProductName(pid,"newName");
        } catch (SQLException e) {
            fail("some thing wrong with changeProductName method"+ e.getMessage());
            flag=0;
        }
        try {
            admin.changeProductType(pid,"newType");
        } catch (SQLException e) {
            fail("some thing wrong with changeProductType method"+ e.getMessage());
            flag=0;
        }
        try {
            admin.changeProductPrice(pid,1000);
        } catch (SQLException e) {
            fail("changeProductPrice"+ e.getMessage());
            flag=0;
        }
        assertTrue(flag==1);
    }

    @Then("should be able to delete a product")
    public void should_be_able_to_delete_a_product() {
        pid=4;
        int flag=1;
        try {
            admin.deleteProduct(pid);
        } catch (SQLException e) {
            fail("some thing wrong with deleteProduct method");
            flag=0;
        }
        assertTrue(flag==1);
    }

    @When("the admin selects the option to manage customer accounts")
    public void the_admin_selects_the_option_to_manage_customer_accounts() {

    }

    @Then("the admin should see a list of registered customers with detailes")
    public void the_admin_should_see_a_list_of_registered_customers() {
        try {
            admin.showCustomer();
        } catch (SQLException e) {
            fail("some thing wrong with showCustomer method");
        }
    }

    @Then("should be able to edit customer information")
    public void should_be_able_to_edit_customer_information() {
        try {
            admin.UpdatePass("khalidsameer@gmail.com","00000");
        } catch (SQLException e) {
            fail("some thing wrong with UpdatePass method");
        }
        try {
            admin.updateEmail("khalidsameer@gmail.com","newEmail@gmail.com");
        } catch (SQLException e) {
            fail("some thing wrong with updateEmail method");
        }
    }

    @Then("should be able to delete a customer account")
    public void should_be_able_to_delete_a_customer_account() {
        try {
            admin.DeleteUser("khalidfathi@yahoo.com");
        } catch (SQLException e) {
            fail("some thing wrong with DeleteUser method");
        }
    }

    @When("the admin accesses the installation management section")
    public void the_admin_accesses_the_installation_management_section() {

    }

    @Then("the admin should see a list of scheduled installation appointments")
    public void the_admin_should_see_a_list_of_scheduled_installation_appointments() {
        try {
            admin.showScheduledAppointments();
        } catch (SQLException e) {
            fail("some thing wrong with showScheduledAppointments method");
        }
    }

    @Then("should be able to reschedule appointments")
    public void should_be_able_to_reschedule_appointments() {
        try {
            admin.scheduleNewAppointment(rid,"installer@gmail.com","9:11:30");
        } catch (SQLException e) {
            fail("some thing wrong with scheduleNewAppointment method");
        }
    }

    @Then("should be able to mark appointments as completed or canceled")
    public void should_be_able_to_mark_appointments_as_completed_or_canceled() {
        try {
            admin.setStatusToCompleted(rid);
        } catch (SQLException e) {
            fail("some thing wrong with setStatusToCompleted method");
        }
        try {
            admin.setStatusToCanceled(rid);
        } catch (SQLException e) {
            fail("some thing wrong with setStatusToCanceled method");
        }
    }
}
