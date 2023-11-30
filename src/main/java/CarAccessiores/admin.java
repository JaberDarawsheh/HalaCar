package CarAccessiores;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class admin extends User {

    private ProductCat cat;
    static final Logger logger = Logger.getLogger(Main.class.getName());
    String sql;
    int rowsAffected;

    public admin(String userEmail,String userPassword) {
        super(userEmail,userPassword);
        cat=new ProductCat();
    }

    public void addCustomer(String email, String Upass) throws SQLException {
        sql = "INSERT INTO systemusers (user_email,user_password,user_type) VALUES (? , ? , ?)";
        connectDB conDB = new connectDB();
        //the new user is added

        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setString(2, Upass);
            stmt.setString(3, "customer");
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("User added successfully.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }
        }
    }

    public void UpdatePass(String email, String Upass) throws SQLException {
        sql = "UPDATE systemusers SET `user_password`= ? WHERE `user_email` = ?";
        connectDB conDB = new connectDB();
        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,Upass);
            stmt.setString(2, email);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("the password updated successfully.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }

        }

    }

    public void updateEmail(String mail, String newEmail) throws SQLException {
        sql = "UPDATE systemusers SET `user_email`= ? WHERE `user_email` = ?";
        connectDB conDB = new connectDB();
        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,newEmail);
            stmt.setString(2, mail);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("the email updated successfully.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }

        }
    }

    public void DeleteUser(String email) throws SQLException {
        sql = "DELETE FROM systemusers WHERE `user_email` = ?";

        connectDB conDB = new connectDB();
        //the new user is added

        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(1,email);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("User deleted successfully.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }


        }




    }

    public void showProductCatalog() throws SQLException {
        cat.show_products_catalog_toAdmin(this);
    }

    public void addProduct(String productName, String productType, int productPrice) throws SQLException {
        sql = "INSERT INTO productcatalog (productName, productType, productPrice, isAvilable) VALUES (?, ?, ?, ?)";
        connectDB connection= new connectDB();
        connection.testConn();
        try(PreparedStatement stmt = connection.getConnection().prepareStatement(sql)){
            stmt.setString(1,productName);
            stmt.setString(2,productType);
            stmt.setInt(3,productPrice);
            stmt.setInt(4,1);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product added successfully.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }
        }
    }

    public void isUnavailable(int pid) throws SQLException {
        sql ="UPDATE productcatalog SET isAvailable = ? WHERE id = ?";
        connectDB conn=new connectDB();
        conn.testConn();
        try(PreparedStatement stmt= conn.getConnection().prepareStatement(sql)){
            stmt.setInt(1,0);
            stmt.setInt(2,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product is now un available.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }
        }
    }

    public void changeProductName(int pid,String newName) throws SQLException {
        sql="UPDATE productcatalog SET productName = ? WHERE id = ?";
        connectDB conn=new connectDB();
        conn.testConn();
        try(PreparedStatement stmt= conn.getConnection().prepareStatement(sql)){
            stmt.setString(1,newName);
            stmt.setInt(2,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product name updated.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }
        }
    }

    public void changeProductType(int pid, String newType) throws SQLException {
        sql="UPDATE productcatalog SET productType = ? WHERE id = ?";
        connectDB conn=new connectDB();
        conn.testConn();
        try(PreparedStatement stmt= conn.getConnection().prepareStatement(sql)){
            stmt.setString(1,newType);
            stmt.setInt(2,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product type updated.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }
        }
    }

    public void changeProductPrice(int pid, int newPrice) throws SQLException {
        sql="UPDATE prosuctcatalog SET productPrice = ? WHERE id =? ";
        connectDB conn=new connectDB();
        conn.testConn();
        try(PreparedStatement stmt= conn.getConnection().prepareStatement(sql)){
            stmt.setInt(1,newPrice);
            stmt.setInt(2,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product price updated.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }
        }
    }

    public void deleteProduct(int pid) throws SQLException {
        sql="DELETE FROM productcatalog WHERE id = ?";
        connectDB conn=new connectDB();
        conn.testConn();
        try(PreparedStatement stmt= conn.getConnection().prepareStatement(sql)){
            stmt.setInt(1,pid);
            rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Product is deleted successfully.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }
        }
    }

    public void showCustomer() throws SQLException {
        logger.log(Level.INFO, "|        id       |   Customer Email   |");
        sql="SELECT * FROM systemusers WHERE user_type = ?";
        connectDB conn=new connectDB();
        conn.testConn();
        try(PreparedStatement stmt= conn.getConnection().prepareStatement(sql)){
            stmt.setString(1,"customer");
            try (ResultSet resultSet = stmt.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    StringBuilder rowData = new StringBuilder();

                    for (int i = 1; i <= 2; i++) {
                        String columnValue = resultSet.getString(i);

                        // Define a fixed width for each column and left-align the text
                        int columnWidth = 18;
                        String formattedValue = String.format("%-" + columnWidth + "s", columnValue);

                        rowData.append(formattedValue);

                        if (i < columnCount) {
                            rowData.append("|"); // Add a vertical bar as a separator between columns
                        }
                    }

                    logger.log(Level.INFO, rowData.toString());
                }
            }

        }
    }

    public void showScheduledAppointments() throws SQLException {
        sql = "SELECT `rid`,`pid`,`productName`,`productType`,`email`,`carModel`,`preferredDate`" +
                " FROM install_request WHERE assigned =?";
        connectDB connection = new connectDB();
        connection.testConn();
        Logger logger = Logger.getLogger("ShowScheduled");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, "scheduled");
            ResultSet rSet = stmt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String format = "| %-15s | %-10s | %-15s | %-20s | %-15s | %-30s | %-10s |%n";
            logger.log(Level.INFO, String.format(format, "Request Number", "Product ID", "Product Type", "Installer Email", "Car Model", "Installation Date", "Status"));

            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();

                for (int i = 1; i <= numberOfColumns; i++) {
                    rowData.append(rSet.getString(i));
                    if (i < numberOfColumns) {
                        rowData.append(" ");
                    }
                }
                logger.log(Level.INFO, String.format(format, rowData.toString()));
            }
        }
    }

    public void scheduleNewAppointment(int rid, String mail,String time) throws SQLException {
        assignTO(rid,mail);
        setTime(rid,time);
        set_scheduled(rid);

    }
    public void set_scheduled(int rid) throws SQLException {
        sql="UPDATE install_request SET status = ? WHERE rid =?";
        connectDB connection = new connectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(sql)){
            stmt.setString(1,"scheduled");
            stmt.setInt(2,rid);
            rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to scheduled.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }

        }

    }

    public void assignTO(int rid,String Email) throws SQLException {
        sql = "UPDATE install_request SET assigned = ? WHERE rid =?";
        connectDB connection = new connectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(sql)){
            stmt.setString(1, Email);
            stmt.setInt(2,rid);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request has been assigned to :"+Email);

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }

        }
    }

    public void setTime(int rid,String time) throws SQLException {
        sql = "UPDATE install_request SET preferredDate = TIMESTAMP(CONCAT(CAST(DATE(preferredDate) AS DATE), ' ', ?))"+
                " WHERE rid=?";
        connectDB connection = new connectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(sql)){
            stmt.setString(1,time);
            stmt.setInt(2,rid);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request time scheduled.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }

        }
    }

    public void setStatusToCompleted(int rid) throws SQLException {
        sql ="UPDATE install_request SET status = ? WHERE rid =?";
        connectDB connection = new connectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(sql)){
            stmt.setString(1,"completed");
            stmt.setInt(2,rid);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to completed.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }

        }
    }

    public void setStatusToCanceled(int rid) throws SQLException {
        sql ="UPDATE install_request SET status = ? WHERE rid =?";
        connectDB connection = new connectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(sql)){
            stmt.setString(1,"canceled");
            stmt.setInt(2,rid);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to canceled.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }

        }
    }
}
