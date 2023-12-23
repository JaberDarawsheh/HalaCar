package car.accessories;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Registeration {
    
	protected String userEmail;
	protected String userPassword;
	protected String userType;
	static final Logger logger = Logger.getLogger(Registeration.class.getName());
	public Registeration()
	{
		userEmail=null;
		userPassword=null;
	}
	
	
	public void setData(String userEmail, String userPassword, String type) throws SQLException {
	    ConnectDB conn = new ConnectDB();
	    conn.testConn();
	    if (type == null) {
	        type = "defaultType";  
	    }

	    String sqll = "INSERT INTO systemusers (user_email, user_password, user_type) VALUES (?, ?, ?)";
	    try (PreparedStatement stmt = conn.getConnection().prepareStatement(sqll)) {
	        stmt.setString(1, userEmail);
	        stmt.setString(2, userPassword);
	        stmt.setString(3, type);
	        int rowsAffected = stmt.executeUpdate();

	        if (rowsAffected > 0) {
	            logger.info("Congratulations! Your account has been successfully created.");
	        } else {
	            logger.warning("Account creation failed. No rows were affected.");
	        }
	    } catch (SQLException e) {
	        logger.log(Level.SEVERE, "An error occurred", e);
	    }
	}


}
