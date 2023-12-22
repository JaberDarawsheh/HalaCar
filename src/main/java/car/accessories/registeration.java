package car.accessories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class registeration {
    
	protected String user_email;
	protected String user_password;
	protected String user_type;
	static final Logger logger = Logger.getLogger(registeration.class.getName());
	public registeration()
	{
		user_email=null;
		user_password=null;
	}
	
	
	public void setData(String user_email, String user_password, String type) throws SQLException {
	    connectDB conn = new connectDB();
	    conn.testConn();
	    
	    // Check if type is null; set it to a default value if needed
	    if (type == null) {
	        type = "default_type";  // Replace "default_type" with an appropriate default value
	    }

	    String sqll = "INSERT INTO systemusers (user_email, user_password, user_type) VALUES (?, ?, ?)";
	    try (PreparedStatement stmt = conn.getConnection().prepareStatement(sqll)) {
	        stmt.setString(1, user_email);
	        stmt.setString(2, user_password);
	        stmt.setString(3, type);
	        int rowsAffected = stmt.executeUpdate();

	        if (rowsAffected > 0) {
	            logger.info("Congratulations! Your account has been successfully created.");
	        } else {
	            logger.warning("Account creation failed. No rows were affected.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


}
