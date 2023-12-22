package CarAccessiores; 
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;

public class Main // to delete
{
	static Scanner scan = new Scanner(System.in);
	static Scanner str = new Scanner(System.in);
	static Scanner strin= new Scanner(System.in);
	static final Logger logger = Logger.getLogger(Main.class.getName());
	private static final String invalidCho="Invalid Choice !, Pleas try again";

	public static void main(String[] args) throws SQLException
	{
		Logger logger = Logger.getLogger("");
		for (java.util.logging.Handler handler : logger.getHandlers()) {
			logger.removeHandler(handler);
		}
		ConsoleHandler consoleHandler = new ConsoleHandler();
		CustomLogFormatter customFormatter = new CustomLogFormatter();
		consoleHandler.setFormatter(customFormatter);
		logger.addHandler(consoleHandler);
		logger.info("---------------------------------------------------------");
		logger.info("-         Welcome to our Car Accessories Company        -");
		logger.info("-                                                       -");
		logger.info("-   Login                                 SignUp        -");
		logger.info("-                                    * New Account *    -");
		logger.info("-    (1)                                    (0)         -");
		logger.info("-                                                       -");
		logger.info("---------------------------------------------------------");
		
		while(true) {
			
			Scanner scanner = new Scanner(System.in);
			if(scanner.nextInt()==0)
			{
				logger.info("Enter user Email :");
				String newUserEmail = scanner.next();
				logger.info("Enter user password : ");
				String newUserPassword = scanner.next();
				String customer = "customer";
				registeration reg = new registeration();
				reg.setData(newUserEmail, newUserPassword , customer);
			}
			else 
			{	
			logger.info("Enter user Email : ");
			String user_email = scanner.next();
			logger.info("Enter user Password : ");
			String user_password = scanner.next();

			UserLoginPage loginP = new UserLoginPage(user_email , user_password);
			ProductCat catalog = new ProductCat();

			loginP.is_valid_credentials(user_email, user_password);

			if(loginP.is_Admin_logged())
			{
				int choice=0;
				String email;
				String password;
				String temp1;
				String temp2;
				int temp3=0;
				admin Admin=new admin(user_email,user_password);
				logger.info("The Admin entered the site");
				boolean adminLogFlag = true;
				logger.info("==============================================");
				logger.info("            WELCOME TO OUR MENU");
				logger.info("==============================================");
				logger.info("Option 1 - Show Products Catalog .");
				logger.info("Option 2 - Search and filter options to easily find products.");
				logger.info("Option 3 - Admin role management menu.");
				logger.info("Option 4 - Show admin dashboard.");
				logger.info("Option 5 - Admin Logout the site.");
				logger.info("==============================================");
				while(adminLogFlag)
				{

					int selectedListValue = scan.nextInt();

					switch(selectedListValue)
					{
						case 1: logger.info("You select Option 1 :");
							catalog.show_products_catalog_toUser(loginP);
							logger.info("You can also choose from the menu : ");
							break;

						case 2: logger.info("You select Option 2 :");
							logger.info("Pleas The Product that you want to search about it : ");
							String selectProduct = str.nextLine();
							logger.info("Please The Filterd price for an selected product  : ");
							String selectPrice = str.nextLine();
							catalog.search_and_show_filtered_result(selectProduct,selectPrice);
							logger.info("You can also choose from the menu : ");
							break;
						case 3:
							while(choice!=4){
								//logger.info("1-add new user \n2-change password for existing user \n3-delete a user \n4-back to main menu \n please enter a number\n ");
								logger.info("=== Option 3 Menu ===");
								logger.info("Sub-option 1 - Add new user to the system");
								logger.info("Sub-option 2 - Change password for existing user");
								logger.info("Sub-option 3 - Delete a user ");
								logger.info("Sub-option 4 - Back to main menu ");
								choice = scanner.nextInt();
								switch (choice) {
									case 1:
										logger.info("please enter an email for the user you want to add: ");
										email = scanner.next();
										logger.info("please enter the password for the user you want to add: ");
										password = scanner.next();
										try {
											Admin.addCustomer(email, password);
										}
										catch (SQLException e) {
											logger.warning("\nsome thing went wrong please try again later");
											break;
										}
										break;
									case 2:
										logger.info("please enter an email for the user you want to edit: ");
										email = scanner.next();
										logger.info("please enter the new password for the user : ");
										password = scanner.next();
										try {

											Admin.UpdatePass(email, password);
										}
										catch (SQLException e) {
											logger.warning("some thing went wrong please try again later");
											break;
										}
										logger.info("the user password updated successfully : ");
										break;
									case 3:
										logger.info("please enter the email of the user you want to delete: ");
										email = scanner.next();
										try {

											Admin.DeleteUser(email);
										}
										catch (SQLException e) {
											logger.warning("some thing went wrong please try again later");
											break;
										}
										logger.info("the user deleted successfully : ");
										break;
									case 4:
										logger.info("you will be redirected soon");
										break;
									default:
										logger.warning("invalid choice please try again");
										break;
								}

							}
							break;
						case 4:
							choice =0;
							while(choice !=4) {
								logger.info("=== Admin dashboard ===");
								logger.info(" 1 - Manage product Product listing.");
								logger.info(" 2 - View and manage customer accounts.");
								logger.info(" 3 - Schedule and manage installation appointments. ");
								logger.info(" 4 - Back to main menu. ");
								choice = scanner.nextInt();
								switch (choice) {
									case 1:
										Admin.showProductCatalog();
										logger.info("=== Product management options ===");
										logger.info(" 1 - Add new product");
										logger.info(" 2 - Edit current product");
										logger.info(" 3 - Delete a product");
										logger.info(" 4 - Back to the dashboard");
										choice= scanner.nextInt();
										switch(choice){
											case 1:
												logger.info("please enter the product name: ");
												String Temp1= str.nextLine();
												logger.info("please enter the product type: ");
												temp2= str.nextLine();
												logger.info("please enter the product price: ");
												temp3= scanner.nextInt();
												Admin.addProduct(Temp1,temp2,temp3);
												break;
											case 2 :
												logger.info("=== Product edit options ===");
												logger.info(" 1 -Rename a product");
												logger.info(" 2- Change product type");
												logger.info(" 3- Change product price");
												logger.info(" 4- Make product unavailable");
												choice = scanner.nextInt();
												logger.info("please enter the product id you want to edit: ");
												temp3 = scanner.nextInt();
												switch(choice){
													case 1:
														logger.info("please enter the new name for the product: ");
														temp2=str.nextLine();
														Admin.changeProductName(temp3,temp2);
														break;
													case 2:
														logger.info("please enter the new type for the product: ");
														temp2=str.nextLine();
														Admin.changeProductType(temp3,temp2);
														break;
													case 3:
														logger.info("please enter the new price for the product: ");
														int price= str.nextInt();
														Admin.changeProductPrice(temp3,price);
														break;
													case 4:
														Admin.isUnavailable(temp3);
														choice=0;
														break;
												}
												break;
											case 3:
												logger.info("Please enter the product id you want to delete:");
												temp3= scanner.nextInt();
												Admin.deleteProduct(temp3);
												break;
											case 4:
												choice=0;
												break;
											default :
												logger.info("unavailable choice");
												break;


										}
										break;
									case 2:
										Admin.showCustomer();
										logger.info("please enter the mail of the user you want to edit: ");
										String cemail= strin.nextLine();
										logger.info("If you want to change the password for the customer press 1");
										logger.info("If you want to change the email for the customer press 2");
										temp3=scanner.nextInt();
										if(temp3==1){
											logger.info("please enter the new password: ");
											temp1=str.nextLine();
											Admin.UpdatePass(cemail,temp1);
										}else if(temp3==2){
											logger.info("please enter the new email: ");
											temp1=scan.nextLine();
											Admin.updateEmail(cemail,temp1);
										}else{
											logger.info("invalid option");
										}
										break;
									case 3:
										Admin.showScheduledAppointments();
										logger.info("=== Appointment management options ===");
										logger.info(" 1 - Reschedule an appointment");
										logger.info(" 2 - Mark as completed");
										logger.info(" 3 - Mark as canceled");
										logger.info(" 4 - Back to the dashboard");
										temp3= scanner.nextInt();
										switch(temp3){
											case 1:
												Scanner fathi = new Scanner(System.in);
												Scanner samer = new Scanner(System.in);
												Scanner apointTime = new Scanner(System.in);
												logger.info("please enter the request ID you want to reschedule: ");
												temp3= fathi.nextInt();
												logger.info("please enter the email of the installer you want to assign the request to: ");
												email= samer.nextLine();
												logger.info("enter a time for the appointment in the following format HH:mm:ss");
												temp1= apointTime.nextLine();
												Admin.scheduleNewAppointment(temp3,email,temp1);
												break;
											case 2:
												logger.info("please enter the request ID you want to mark as completed: ");
												temp3= scanner.nextInt();
												Admin.setStatusToCompleted(temp3);
												break;
											case 3:
												logger.info("please enter the request ID you want to mark as canceled: ");
												temp3= scanner.nextInt();
												Admin.setStatusToCanceled(temp3);
												break;
											case 4:

												break;
											default:
												logger.info(invalidCho);
										}
										break;
									case 4:

										break;
									default:
										logger.info(invalidCho);

								}
							}
							break;
						case 5: theAdminLogout(loginP);
							adminLogFlag=false;
							break;

						default:logger.info(invalidCho);
					}//end switch
				}// end while(adminLogFlag)


			}// end if(loginP.is_Admin_logged())



			else if(loginP.is_Customer_logged())
			{
				String temp1;
				customer Customer=new customer(user_email,user_password);
				boolean customerLogFlag=true;
				int choice=0;
				logger.info("The Customer entered the site");
				logger.info("==============================================");
				logger.info("             WELCOME TO HALA CAR");
				logger.info("==============================================");
				logger.info("Option 1 - Show Products Catalog .");
				logger.info("Option 2 - View shopping cart.");
				logger.info("Option 3 - Customer Profile access.");
				logger.info("Option 4 - Installation request menu.");
				logger.info("Option 5 - Change email.");
				logger.info("Option 6 - Show personal information.");
				logger.info("Option 7 - Customer Logout the site.");
				logger.info("==============================================");
				while(customerLogFlag) {

					choice= scanner.nextInt();
					switch(choice){
						case 1:
							if(Customer.calatogAvailable()){
								Customer.ShowCatalogToCustomer();
								logger.info("enter 0 if you want to go back");
								logger.info("please enter the id of the product you want to add to cart :");
								int id= scanner.nextInt();
								if(id!=0){
									logger.info("how many pieces you want to add to the cart?");
									int quantity= scanner.nextInt();
									Customer.AddToCart(id,quantity);

								}

							}else{
								logger.info("The catalog is currently not available please try again later");

							}
							break;
						case 2:
							try {
								Customer.viewCart();
								logger.info("if you want to check out please press 1 ");
								int check = scanner.nextInt();
								if(check==1){
									Customer.checkOut();
									break;
								}

							} catch (SQLException e) {
								logger.info("something went wrong please try again later");
								logger.info(String.format("%s",e));
								break;
							}
						case 3:
							int access=0;
							while(access!=3) {
								access=Customer.showProfile();
								switch (access) {
									case 1:
										Customer.showHistory();
										break;
									case 2:
										Customer.showCompleted();
										break;
								}
							}
							break;
						case 4:
							Scanner req =new Scanner(System.in);
							int reqChoice=0;
							logger.info("=== Option 4 Menu ===");
							logger.info("Sub-option 1 - creat new installation request");
							logger.info("Sub-option 2 - show scheduled requests");
							logger.info("Sub-option 3 - show completed requests");
							logger.info("Sub-option 4 - show canceled requests");
							logger.info("Sub-option 5 - back to the main menu ");
							while(reqChoice!=5) {

								reqChoice= req.nextInt();
								switch(reqChoice){

									case 1:
										logger.info("please enter the wanted product id :");
										int pid=scanner.nextInt();
										logger.info("please enter your car description (example: skoda octavia 2019):");
										String carModel= str.nextLine();
										logger.info("please enter the preferred date in the following format (dd-MM-yyyy)");
										String date=str.nextLine();
										try {
											Customer.installationRequest(pid,carModel,date);
										} catch (ParseException e) {
											logger.warning("the date format is wrong try again please");
										}
										break;
									case 2:
										Customer.showScheduled();
										break;
									case 3:
										Customer.showCompleted();
										break;
									case 4:
										Customer.showCanceled();
										break;
									case 5:
										break;
									default:
										logger.warning("invalid action please re enter the number");
										break;

								}

							}
							break;
						case 5:
							Scanner mahmoud = new Scanner(System.in);
							logger.info("please enter your new email");
							temp1=mahmoud.nextLine();
							Customer.changeEmail(temp1);
							break;
						case 6:
							Customer.ShowPersonalInfo();
							break;
						case 7:
							logger.info("The Customer has left the site.");
							customerLogFlag=false;
							break;
						default:
							logger.info("invalid action please re enter the number");
							break;
					}

				}
			}

			else if(loginP.Installer_is_login)
			{
				logger.info("The Installer entered the site");
				installer Installer=new installer(user_email,user_password);
				boolean installerLogFlag=true;
				int choice=0;
				logger.info("==============================================");
				logger.info("             WELCOME TO HALA CAR");
				logger.info("==============================================");
				logger.info("Option 1 - Show pending requests.");
				logger.info("Option 2 - Show assigned requests.");
				logger.info("Option 3 - status check.");
				logger.info("Option 4 - Installer Logout the site.");
				logger.info("==============================================");
				while(installerLogFlag){
					int rid=0;
					choice= scanner.nextInt();
					switch(choice){
						case 1:
							Installer.showPending();
							logger.info("enter 0 to go back to the main menu \nenter the request number to assign it to your self:");
							rid=scanner.nextInt();
							if(rid!=0){
								logger.info("enter a time for the appointment in the following format HH:mm:ss");
								String time= str.nextLine();
								Installer.schedule(rid,time);
							}
							break;
						case 2:
							Installer.showAssigned();
							logger.info("=== Option 2 Menu ===");
							logger.info("Sub-option 1 if you want to mark a request as completed");
							logger.info("Sub-option 2 if you want to cancel a request");
							logger.info("Sub-option 3 to continue");
							int temp= scanner.nextInt();
							if(temp==1){
								logger.info("please enter the request number you want to mark as completed");
								rid= scanner.nextInt();
								Installer.setCompleted(rid);
							} else if (temp==2) {
								logger.info("please enter the request number you want to cancel");
								rid= scanner.nextInt();
								Installer.setCanceled(rid);
							}
							break;
						case 3:
							logger.info("please enter the request number you want to check");
							rid= scanner.nextInt();
							String status= Installer.getStatus(rid);
							logger.info("the status for the mentioned request number is: "+status);
							break;
						case 4:
							logger.info("The Installer has left the site.");
							installerLogFlag=false;
							break;
						default:
							logger.info("invalid action please re enter the number");
							break;

					}

				}

			}

			else
			{
				logger.info("User email and.or password in Correct , Pleas try agine");
			}


		}
		}
		
	} // end first while loop
	
	public static void theAdminLogout(UserLoginPage login) throws SQLException {

		login.Admin_logout();
		logger.info("The Admin has left the site.");
	}
}
