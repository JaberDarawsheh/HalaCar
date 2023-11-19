package CarAccessiores;




import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.sql.SQLException;
import java.util.logging.Level;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;



public class UserRoles {

    private static final Logger logger = Logger.getLogger(UserRoles.class.getName());


    String user_email , user_password;
    String installer_email;
    String installer_password;
    int rid;
    admin user;
    customer Customer;
    CarAccessiores.installer installer;
    boolean Admin_logged;
    boolean Customer_logged;
    boolean installer_logged;
    UserLoginPage loginObj;
    int choice=0;
    public UserRoles()  // initialization of class
    {
        //assume these credentials used as admin,customer and installer
        this.user_email="jaberDar@najah.edu";
        this.user_password="123455";
        loginObj=new UserLoginPage(this.user_email,this.user_password);
    }
    @Given("an Admin is logged into the system")
    public void an_admin_is_logged_into_the_system() {
        try {
            loginObj.is_valid_credentials(this.user_email,this.user_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Admin_logged =loginObj.is_Admin_logged();
        if(Admin_logged) {
            user=new admin(this.user_email,this.user_password);
        }else {
            fail("this is not admin credentials");
        }


    }

    @When("the Admin navigates to the user role management section")
    public void the_admin_navigates_to_the_user_role_management_section() {
        logger.log(Level.INFO,"The Admin is in the user management screen");

    }

    @Then("the Admin should be able to add a new user")
    public void the_admin_should_be_able_to_add_a_new_user() {
        String user_mail="";
        // Write code here that turns the phrase above into concrete actions
        try {
            user.add("subhi@outlook.com","subhi1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectDB conDB = new connectDB();

        //check if the new user is added successfully
        String sql2="SELECT * FROM systemusers WHERE userEmail = ?";
        try {
            conDB.testConn();
            try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql2)){
                stmt.setString(1, "subhi@outlook.com");
                ResultSet resultSet = stmt.executeQuery();
                if(resultSet.next()) {
                    user_mail = resultSet.getString("userEmail");

                }


            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals("subhi@outlook.com",user_mail);


    }

    @Then("the Admin should be able to edit an existing user")
    public void the_admin_should_be_able_to_edit_an_existing_user() {
        // Write code here that turns the phrase above into concrete actions
        String user_pass="";
        // Write code here that turns the phrase above into concrete actions
        try {
            user.UpdatePass("subhi@outlook.com","subhi123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        connectDB conDB = new connectDB();

        String sql2="SELECT * FROM systemusers WHERE user_email = ?";
        try {
            conDB.testConn();
            try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql2)){
                stmt.setString(1, "subhi@outlook.com");
                ResultSet resultSet = stmt.executeQuery();
                if(resultSet.next()) {
                    user_pass = resultSet.getString("user_password");

                }


            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertEquals("subhi123456",user_pass);
    }

    @Then("the Admin should be able to delete a user")
    public void the_admin_should_be_able_to_delete_a_user() {
        // Write code here that turns the phrase above into concrete actions
        boolean deleted=false;
        try {
            user.DeleteUser("subhi@outlook.com");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectDB conDB = new connectDB();
        String sql2="SELECT * FROM systemusers WHERE user_email = ?";
        try {
            conDB.testConn();
            try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql2)){
                stmt.setString(1, "subhi@outlook.com");
                ResultSet resultSet = stmt.executeQuery();
                if (!resultSet.next())
                    deleted=true;
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertTrue( deleted);
    }

    @Given("a Customer is logged into the system")
    public void a_customer_is_logged_into_the_system() {
        try {
            loginObj.is_valid_credentials(this.user_email,this.user_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Customer_logged =loginObj.is_Customer_logged();
        if(Customer_logged) {
            Customer =new customer(this.user_email,this.user_password);
        }else {
            throw new io.cucumber.java.PendingException();//the test case will not pass if the customer login failed

        }

    }

    @When("the Customer browses the product catalog")
    public void the_customer_browses_the_product_catalog() {
        if(!Customer.calatogAvailable()){
            fail("Catalog isn't available");//if the catalog is not available the test case will fail
        }

    }

    @Then("the Customer should be able to view detailed product information")
    public void the_customer_should_be_able_to_view_detailed_product_information() {
        try {
            Customer.ShowCatalogToCustomer();
            //the test will pass if there is no unhandled exception occurred
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Then("the Customer should be able to add the product to the cart")
    public void the_customer_should_be_able_to_add_the_product_to_the_cart() {
        // the customer will enter the id of the product that want to be added to the cart and the quantity
        //the id & quantity should be entered as integers
        //assume the id of the product is 1 and the quantity the customer wants to add to the cart is 10
        int id=1;
        int quantity=10;
        try {
            Customer.AddToCart(id,quantity);
            //the test will pass if there is no unhandled exception occurred
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }

    }

    @Then("the Customer should be able to proceed to checkout")
    public void the_customer_should_be_able_to_proceed_to_checkout() {
        try {
            Customer.viewCart();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }

    }

    @Then("the Customer should be able to complete the purchase")
    public void the_customer_should_be_able_to_complete_the_purchase() {
        try {
            Customer.checkOut();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }

    }

    @When("the Customer accesses their user profile")
    public void the_customer_accesses_their_user_profile() {
        choice=Customer.showProfile();
        //the method will ask the user to enter a number to choose an action to perform (change Email or password,view history ,etc.....)
        if(choice<=0||choice>5)
            fail("The user entered an in valid number that perform no action");
    }

    @When("views their order history")
    public void views_their_order_history() {
        if(choice!=3)
            fail("The user has entered a number that performs another operation different from viewing the history  ");
    }

    @Then("the Customer should see a list of their past orders")
    public void the_customer_should_see_a_list_of_their_past_orders() {
        try {
            Customer.showHistory();
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }

    }

    @Given("an Installer is logged into the system")
    public void an_installer_is_logged_into_the_system() {
        installer_email="fathi@gmail.com";
        installer_password="1234567";
        try {
            loginObj.is_valid_credentials(this.installer_email,this.installer_password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        installer_logged =loginObj.is_Installer_logged();
        if(installer_logged) {
            installer =new installer(this.user_email,this.user_password);
        }else {
            fail("there is no available installer");//the test case will not pass if the installer login failed

        }
    }

    @When("the Installer navigates to the installation request section")
    public void the_installer_navigates_to_the_installation_request_section() {
        try {
            installer.showPending();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
        rid=1;//assume this is the request id the installer want to accept

    }

    @Then("the Installer should be able to view a list of installation requests")
    public void the_installer_should_be_able_to_view_a_list_of_installation_requests() {
        try {
            installer.showPending();
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Then("the Installer should be able to accept or reject installation requests")
    public void the_installer_should_be_able_to_accept_or_reject_installation_requests() {
        try {
            installer.assign(rid);//this function will assign the request to the installer
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Then("the Installer should be able to schedule appointments for accepted requests")
    public void the_installer_should_be_able_to_schedule_appointments_for_accepted_requests() {
        try {
            installer.schedule(rid,"15:30:00");
        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }

    }
}
