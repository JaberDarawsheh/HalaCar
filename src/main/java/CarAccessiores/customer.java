package CarAccessiores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class customer extends User {
    private UserLoginPage user;
    private ProductCat cat;
    private String pass;
    private static final Logger logger = Logger.getLogger(ProductCat.class.getName());
    public customer(String userEmail,String userPassword) {
        super(userEmail,userPassword);
        pass = userPassword;
        user=new UserLoginPage(userEmail,userPassword);
        cat=new ProductCat();
    }

    public void ShowPersonalInfo() {
        logger.info("Personal Information ");
        logger.info("Email: " + user.getUser_email());
    }


    public void showHistory() throws SQLException {
        String historySQL = "SELECT `productName`,`productType`,`unitPrice`,`orderDate`" +
                " FROM history WHERE email=? ";
        connectDB DataBase = new connectDB();
        DataBase.testConn();
        Logger logger = Logger.getLogger("ShowHistory");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try (PreparedStatement stmnt = DataBase.getConnection().prepareStatement(historySQL)) {
            stmnt.setString(1, user.getUser_email());
            ResultSet rSet = stmnt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String format = "| %-20s | %-15s | %-10s | %-12s |%n";
            logger.log(Level.INFO, String.format(format, "Product Name", "Product Type", "Unit Price", "Order Date"));
            int[] columnWidths={20,15,10,12};
            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();
                for (int i = 1; i < numberOfColumns; i++) {
                    String columnValue = rSet.getString(i);
                    String formattedColumn = String.format("%-" + columnWidths[i-1] + "s", columnValue);
                    rowData.append(formattedColumn);
                    rowData.append(" | "); // Add separator between columns

                }
                Date date = rSet.getDate("orderDate");
                String formattedDateTime = dateFormat.format(date);
                String [] dateTimeSplit=formattedDateTime.split("\\s+");
                String onlydate =dateTimeSplit[0];
                rowData.append(onlydate);

                logger.info(String.format("| %-20s   |%n", rowData));
            }

        }
    }


    public boolean calatogAvailable(){
        boolean returnValue=false;
        try {
            returnValue= cat.system_is_found_catalog();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public void ShowCatalogToCustomer() throws SQLException{
        cat.show_products_catalog_toUser(user);
    }

    public void AddToCart(int id,int Quantity) throws SQLException {
        String CARTsql = "INSERT INTO cart (pid,productName,productType,unitPrice,quantity,email)"+
                     "SELECT `id`,`productName`,`productType`,`productPrice`,?,?"+
                     "FROM ProductCatalog WHERE id =?";
        connectDB connDataBase = new connectDB();
        connDataBase.testConn();
        try(PreparedStatement stmt = connDataBase.getConnection().prepareStatement(CARTsql)){
            stmt.setInt(1, Quantity);
            stmt.setString(2, user.getUser_email());
            stmt.setInt(3,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("added to the cart successfully.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }
        }
    }

    public void viewCart() throws SQLException {
        String showCart = "SELECT `pid`,`productName`,`productType`,`unitPrice`,`quantity` FROM cart WHERE email =?";
        connectDB connDataBase = new connectDB();
        connDataBase.testConn();
        Logger logger = Logger.getLogger("ViewCart");

        try (PreparedStatement stmnt = connDataBase.getConnection().prepareStatement(showCart)) {
            stmnt.setString(1, user.getUser_email());
            ResultSet rSet = stmnt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String format = "| %-15s | %-30s | %-15s | %-10s | %-10s |%n";
            logger.log(Level.INFO, String.format("| %-5s | %-30s | %-15s | %-10s | %-10s |%n", "ID", "Product Name", "Product Type", "Unit Price", "Quantity"));
            int[] columnWidths={5,30,15,10,10};
            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();
                for (int i = 1; i <= numberOfColumns; i++) {
                    String columnValue = rSet.getString(i);
                    String formattedColumn = String.format("%-" + columnWidths[i-1] + "s", columnValue);
                    rowData.append(formattedColumn);
                    if (i < numberOfColumns) {
                        rowData.append(" | "); // Add separator between columns
                    }
                }

                logger.info(String.format("| %-5s  |%n", rowData));
            }
        }
    }

    public void checkOut() throws SQLException {
        //this function should move the orders from the cart to the history with date and time of the order purchase
        String checkOutSql= "INSERT INTO history(pid,productName,productType,unitPrice,quantity,email,orderDate)"+
                            "SELECT `pid`,`productName`,`productType`,`unitPrice`,`quantity`,`email`,?"+
                            "FROM cart WHERE email =?";
        String delteOrderFromCart= "DELETE FROM cart " +
                                   "WHERE email =?;";
        connectDB conDB= new connectDB();
        conDB.testConn();
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(checkOutSql)){
            stmt.setTimestamp(1,currentTimestamp);
            stmt.setString(2,user.getUser_email());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The order is processing.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }
        }
        try (PreparedStatement stmt = conDB.getConnection().prepareStatement(delteOrderFromCart)){
            stmt.setString(1,user.getUser_email());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The order is placed successfully.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }
        }



    }

    public int showProfile() {
        //the full functionality will be added soon
        //logger.log(Level.INFO,"| 1-Change Email |\n|2-Change password|\n|3-View order history|\n|4-View installation requests|\n|5-Go back to the main menu|");
        // logger.log(Level.INFO,"| 1-View order history|\n|2-View installation requests history|\n|3-Go back to the main menu|");
        logger.info("=== Option  Menu ===");
        logger.info("Sub-option 1 View order history");
        logger.info("Sub-option 2 View installation requests history");
        logger.info("Sub-option 3 Go back to the main menu");
        //the user will enter the corresponding number of the action wanted to be performed
        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();
        return userChoice;

    }

    public void installationRequest(int id,String carModel,String installationDate) throws SQLException,ParseException  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date Date = dateFormat.parse(installationDate);


        String creatRequest="INSERT INTO install_request (pid,productName,productType,email,carModel,preferredDate,status)"+
                            "SELECT `id`,`productName`,`productType`,?,?,?,?"+
                            "FROM ProductCatalog WHERE id=?";
        String customer_email=user.getUser_email();
        String query ="SELECT * FROM ProductCatalog WHERE id=?";
        String orderName;
        Timestamp date = new Timestamp(Date.getTime());
        connectDB connection = new connectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(creatRequest)){
            stmt.setString(2,carModel);
            stmt.setTimestamp(3,date);
            stmt.setString(4,"pending");
            stmt.setString(1, user.getUser_email());
            stmt.setInt(5,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request placed successfully.");
                try(PreparedStatement stmnt =connection.getConnection().prepareStatement(query)){
                    stmnt.setInt(1,id);
                    ResultSet rs= stmnt.executeQuery();
                    orderName="";
                    while(rs.next()) {
                        orderName = rs.getString("productName");
                    }

                }
                SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
                String emailMessageTOCustomer = "Dear Customer,\n\n"
                        + "We hope this email finds you well. We would like inform you that your installation request is under process  \n\n"
                        + "Here are your request information"
                        + "Part to be installed: " + orderName + "\n"
                        + "Preferred Date: " + installationDate + "\n"
                        + "Our team will contact you shortly\n\n"
                        + "Thank you,\nCar Accessories Company";

                toCustomerEmail.sendNotificationToCustomer(customer_email, emailMessageTOCustomer);

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }
        }


    }

    public void showScheduled() throws SQLException {
        String query =  "SELECT `rid`,`pid`,`productName`,`productType`,`carModel`,`assigned`,`preferredDate`,`status`" +
                        " FROM install_request WHERE status =? and email = ? ";
        connectDB connection = new connectDB();
        connection.testConn();
        Logger logger = Logger.getLogger("ShowScheduled");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, "scheduled");
            stmt.setString(2, user.getUser_email());
            ResultSet rSet = stmt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String format = "| %-15s | %-10s | %-15s | %-20s | %-15s | %-30s | %-10s |%n";
            logger.log(Level.INFO, String.format(format, "Request Number", "Product ID", "Product Type", "Installer Email", "Car Model", "Installation Date", "Status"));
            //logger.info(" "+numberOfColumns+"");
            int[] columnWidths={15,10,15,20,15,30,10};

            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();


                for (int i = 1; i <= numberOfColumns; i++) {
                    String columnValue = rSet.getString(i);
                    String formattedColumn = String.format("%-" + columnWidths[i-1] + "s", columnValue);
                    rowData.append(formattedColumn);
                    rowData.append(" | "); // Add separator between columns
                }
                logger.log(Level.INFO, String.format("| %-5s |", rowData.toString()));
            }
        }
    }

    public void showCompleted() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`carModel`,`assigned`,`preferredDate`,`status`" +
                " FROM install_request WHERE status =? AND email =?";
        connectDB connection = new connectDB();
        connection.testConn();
        Logger logger = Logger.getLogger("ShowCompleted");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, "completed");
            stmt.setString(2, user.getUser_email());
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

    public void showCanceled() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`carModel`,`assigned`,`preferredDate`,`status`" +
                " FROM install_request WHERE status =? AND email =?";
        connectDB connection = new connectDB();
        connection.testConn();
        Logger logger = Logger.getLogger("ShowCanceled");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, "canceled");
            stmt.setString(2, user.getUser_email());
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

    public void showAllRequests() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`carModel`,`assigned`,`preferredDate`,`status`" +
                " FROM install_request WHERE  email =?";
        connectDB connection = new connectDB();
        connection.testConn();
        Logger logger = Logger.getLogger("ShowCanceled");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, user.getUser_email());
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

    public void changeEmail(String newEmail) throws SQLException {
        String sql = "UPDATE systemusers SET user_email = ? WHERE user_email = ?";
        connectDB conDB = new connectDB();
        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(2, user.getUser_email());
            stmt.setString(1, newEmail);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("the email updated successfully.");

            } else {
                logger.warning("\n some thing went wrong please try again later");
            }

        }
        user=null;
        user=new UserLoginPage(newEmail,pass);

    }
}
