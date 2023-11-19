package CarAccessiores;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class admin extends User {
    static final Logger logger = Logger.getLogger(Main.class.getName());

    public admin(String userEmail,String userPassword) {
        super(userEmail,userPassword);
    }

    public void add(String email, String Upass) throws SQLException
    {
        String sql = "INSERT INTO systemusers (user_email,user_password,user_type) VALUES (? , ? , ?)";
        connectDB conDB = new connectDB();
        //the new user is added

        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setString(2, Upass);
            stmt.setString(3, "customer");
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("User added successfully.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }

        }




    }

    public void UpdatePass(String email, String Upass) throws SQLException {
        String sql = "UPDATE systemusers SET `user_password`= ? WHERE `user_email` = ?";
        connectDB conDB = new connectDB();
        //the new user is added
        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,Upass);
            stmt.setString(2, email);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("the password updated successfully.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }

        }




    }

    public void DeleteUser(String email) throws SQLException
    {
        String sql = "DELETE FROM systemusers WHERE `user_email` = ?";

        connectDB conDB = new connectDB();
        //the new user is added

        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,email);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("User deleted successfully.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }


        }




    }
}
