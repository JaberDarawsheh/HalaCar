package car.accessories;

import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserLoginPage {
    
	protected boolean Admin_is_login; // declaring the flag to chekck id the admin id login to the system or not
	protected boolean Customer_is_login; 
	protected boolean Installer_is_login;
	
	protected boolean login_flag;
	
	protected String type_admin="admin";
	protected String type_customer="customer";
	protected String type_installer="installer";
	
	protected String user_email;
	protected String user_password;
	
	public UserLoginPage() // initilazation for this calss
	{
		Admin_is_login=false;
		Customer_is_login=false;
		Installer_is_login=false;
		
		login_flag=false;
		
		user_email=null;
		user_password=null;
	}
	
	public UserLoginPage(String user_email, String user_password) { 
		// TODO Auto-generated constructor stub
		this.user_email=user_email;
		this.user_password=user_password;
	}

	public void is_valid_credentials(String user_email, String user_password) throws SQLException {
		// TODO Auto-generated method stub
		 connectDB conDB = new connectDB();
		 conDB.testConn();
		 String sql = "SELECT * FROM systemusers WHERE user_email = ? AND user_password = ?";
		try ( 
                PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)) 
		{
	        stmt.setString(1, user_email);
	        stmt.setString(2, user_password);
	        ResultSet resultSet = stmt.executeQuery();
			if(resultSet.next())
			{
				String type_of_user =resultSet.getString("user_type");
				
				this.Admin_is_login=false;
				this.Customer_is_login=false;
				this.Installer_is_login=false;
				this.login_flag=false;
				
				if(type_of_user.equals(type_admin))
				{
					this.Admin_is_login=true;
					this.login_flag=true;
				}
				else if(type_of_user.equals(type_customer)) 
				{
					this.Customer_is_login=true;
					this.login_flag=true;
				}
				else 
				{
					this.Installer_is_login=true;
					this.login_flag=true;
				}
			}
		}
		catch(SQLException e)
		{
			System.out.print(e);
		}
	}

	public boolean is_user_logged() 
	{
		return this.login_flag;
	}
	
	public boolean Logout() 
	{
		this.login_flag=false;
		return this.login_flag;
	}
	
	public boolean is_Admin_logged()
	{
	  return this.Admin_is_login;	
	}
	
	public boolean is_Customer_logged()
	{
		return this.Customer_is_login;
	}
	
	public boolean is_Installer_logged()
	{
		return this.Installer_is_login;
	}
	
	public boolean Admin_logout()
	{
		this.Admin_is_login=false;
		return this.Admin_is_login;
	}
	
	public boolean Customer_logout()
	{
		this.Customer_is_login=false;
		return this.Customer_is_login;
	}
	
	public boolean Installer_logout()
	{
		this.Installer_is_login=false;
		return this.Installer_is_login;
	}

    public String getUser_email() {
        return this.user_email;
    }
}
