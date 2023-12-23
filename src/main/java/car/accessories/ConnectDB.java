package car.accessories;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ConnectDB {

	protected Connection connection;
	private static String P="";
	
	public void testConn() throws SQLException {

		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    String url = "jdbc:mysql://localhost:3306/caraccessories";
		    String username = "root";

		    connection = DriverManager.getConnection(url, username, P);
		    
		} catch (ClassNotFoundException | SQLException e) {
			Logger logger = Logger.getLogger(ConnectDB.class.getName());
		    logger.log(Level.SEVERE, "An error occurred", e); 
		}
    }

	public Connection getConnection() 
	{
		return connection;
	}	
}
