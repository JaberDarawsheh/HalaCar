package car.accessories;

import java.sql.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class connectDB {

	protected Connection connection; 
	
	public void testConn() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/caraccessories","root","");
            		//System.out.print("connected to DB done successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

	public Connection getConnection() 
	{
		return connection;
	}	
}
