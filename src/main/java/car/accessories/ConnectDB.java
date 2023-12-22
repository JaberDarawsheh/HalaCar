package car.accessories;

import java.sql.*;

public class ConnectDB {

	protected Connection connection; 
	
	public void testConn() throws SQLException {

		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");
		    String url = "jdbc:mysql://localhost:3306/caraccessories";
		    String username = "root";
		    String password = "your_password"; 
		    connection = DriverManager.getConnection(url, username, password);
		    
		} catch (ClassNotFoundException | SQLException e) {
		    e.printStackTrace(); 
		}
    }

	public Connection getConnection() 
	{
		return connection;
	}	
}
