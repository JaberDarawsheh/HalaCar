package car.accessories;

import java.sql.SQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserLoginPage {
    
	protected boolean adminIsLogin; 
	protected boolean customerIsLogin; 
	protected boolean installerIsLogin;
	
	protected boolean loginFlag;
	
	protected String typeAdmin="admin";
	protected String typeCustomer="customer";
	protected String typeInstaller="installer";
	
	protected String userEmail;
	protected String userPassword;
	
	public UserLoginPage() 
	{
		adminIsLogin=false;
		customerIsLogin=false;
		installerIsLogin=false;
		
		loginFlag=false;
		
		userEmail=null;
		userPassword=null;
	}
	
	public UserLoginPage(String userEmail, String userPassword) { 
	
		this.userEmail=userEmail;
		this.userPassword=userPassword;
	}

	public void isValidCredentials(String userEmail, String userPassword) throws SQLException {

		 ConnectDB conDB = new ConnectDB();
		 conDB.testConn();
		 String sql = "SELECT * FROM systemusers WHERE user_email = ? AND user_password = ?";
		try ( 
                PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)) 
		{
	        stmt.setString(1, userEmail);
	        stmt.setString(2, userPassword);
	        ResultSet resultSet = stmt.executeQuery();
			if(resultSet.next())
			{
				String typeOfUser =resultSet.getString("user_type");
				
				this.adminIsLogin=false;
				this.customerIsLogin=false;
				this.installerIsLogin=false;
				this.loginFlag=false;
				
				if(typeOfUser.equals(typeAdmin))
				{
					this.adminIsLogin=true;
					this.loginFlag=true;
				}
				else if(typeOfUser.equals(typeCustomer)) 
				{
					this.customerIsLogin=true;
					this.loginFlag=true;
				}
				else 
				{
					this.installerIsLogin=true;
					this.loginFlag=true;
				}
			}
		}
		catch(SQLException e)
		{
		    e.printStackTrace();
		}
	}

	public boolean isUserLogged() 
	{
		return this.loginFlag;
	}
	
	public boolean LogOut() 
	{
		this.loginFlag=false;
		return this.loginFlag;
	}
	
	public boolean isAdminLogged()
	{
	  return this.adminIsLogin;	
	}
	
	public boolean isCustomerLogged()
	{
		return this.customerIsLogin;
	}
	
	public boolean isInstallerLogged()
	{
		return this.installerIsLogin;
	}
	
	public boolean adminLogout() throws SQLException
	{
		this.adminIsLogin=false;
		return this.adminIsLogin;
	}
	
	public boolean customerLogout()
	{
		this.adminIsLogin=false;
		return this.adminIsLogin;
	}
	
	public boolean installerLogout()
	{
		this.installerIsLogin=false;
		return this.installerIsLogin;
	}

    public String getUserEmail() {
        return this.userEmail;
    }
}
