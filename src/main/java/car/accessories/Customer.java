package car.accessories;

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

public class Customer extends User {
    private UserLoginPage user;
    private ProductCat cat;
    private static final Logger logger = Logger.getLogger(Customer.class.getName());
    
    public Customer(String userEmail,String userPassword) {
        super(userEmail,userPassword);
        user=new UserLoginPage(userEmail,userPassword);
        cat=new ProductCat();
    }

    public void showHistory() throws SQLException {
        String historySQL = "SELECT `productName`,`productType`,`unitPrice`,`orderDate`" +
                " FROM history WHERE email=? ";
        ConnectDB dataBase = new ConnectDB();
        dataBase.testConn();
        Logger historyLogger = Logger.getLogger("ShowHistory");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try (PreparedStatement stmnt = dataBase.getConnection().prepareStatement(historySQL)) {
            stmnt.setString(1, user.getUserEmail());
            ResultSet rSet = stmnt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String format = "| %-20s | %-15s | %-10s | %-12s |%n";
            logger.log(Level.INFO, String.format(format, "Product Name", "Product Type", "Unit Price", "Order Date"));

            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();

                for (int i = 1; i <= numberOfColumns; i++) {
                    rowData.append(rSet.getString(i));
                    if (i < numberOfColumns) {
                        rowData.append(" ");
                    }
                }

                Date date = rSet.getDate("orderDate");
                String formattedDate = dateFormat.format(date);
                rowData.append(formattedDate);
                logger.log(Level.INFO, String.format(format, rowData.toString()));
            }
        }
    }


    public boolean calatogAvailable(){
        boolean returnValue=false;
        try {
            returnValue= cat.systemFoundCatalog();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public void showCatalogToCustomer() throws SQLException{
        cat.showProductsCatalogToUser();
    }

    public void AddToCart(int id,int Quantity) throws SQLException
    {
        String carTsql = "INSERT INTO cart (pid,productName,productType,unitPrice,quantity,email)"+
                     "SELECT `id`,`productName`,`productType`,`productPrice`,?,?"+
                     "FROM ProductCatalog WHERE id =?";
        ConnectDB connDataBase = new ConnectDB();
        connDataBase.testConn();
        try(PreparedStatement stmt = connDataBase.getConnection().prepareStatement(carTsql)){
            stmt.setInt(1, Quantity);
            stmt.setString(2, user.getUserEmail());
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
        String showCart = "SELECT `pid`,`productName`,`productType`,`unitPrice`,`quantity`" +
                " FROM cart WHERE email =?";
        ConnectDB connDataBase = new ConnectDB();
        connDataBase.testConn();
        Logger viewLogger = Logger.getLogger("ViewCart");

        try (PreparedStatement stmnt = connDataBase.getConnection().prepareStatement(showCart)) {
            stmnt.setString(1, user.getUserEmail());
            ResultSet rSet = stmnt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String format = "| %-5s | %-30s | %-15s | %-10s | %-10s |%n";
            logger.log(Level.INFO, String.format(format, "ID", "Product Name", "Product Type", "Unit Price", "Quantity"));

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
    public void checkOut() throws SQLException
    {
       
        String checkOutSql= "INSERT INTO history(id,productName,productType,unitPrice,quantity,email,orderDate)"+
                            "SELECT `pid`,`productName`,`productType`,`unitPrice`,`quantity`,`email`,?"+
                            "FROM cart WHERE email =?";
        String delteOrderFromCart= "DELETE FROM cart" +
                                   "WHERE email =?;";
       ConnectDB conDB= new ConnectDB();
        conDB.testConn();
        Timestamp currentTimestamp = new Timestamp(new Date().getTime());
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(checkOutSql)){
            stmt.setTimestamp(1,currentTimestamp);
            stmt.setString(2,user.getUserEmail());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The order is processing.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }
        }
        try (PreparedStatement stmt = conDB.getConnection().prepareStatement(delteOrderFromCart)){
            stmt.setString(1,user.getUserEmail());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The order is placed successfully.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }
        }



    }

    public int showProfile() {
        
        logger.info("=== Option  Menu ===");
        logger.info("Sub-option 1 View order history");
        logger.info("Sub-option 2 View installation requests history");
        logger.info("Sub-option 3 Go back to the main menu");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();

    }

    public void installationRequest(int id,String carModel,String installationDate) throws SQLException,ParseException  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dated = dateFormat.parse(installationDate);


        String creatRequest="INSERT INTO install_request (pid,productName,productType,email,carModel,preferredDate,status)"+
                            "SELECT `id`,`productName`,`productType`,`email`,?,?,?"+
                            "FROM ProductCatalog WHERE email=? AND id=?";
        String customer_email="SELECT `email` FROM install_request";
        String orderName ="SELECT `productName` FROM install_request";
        String installerEmail="SELECT `assigned` FROM install_request";
        String orderData="SELECT `preferredData` FROM install_request";
        Timestamp date = new Timestamp(dated.getTime());
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(creatRequest)){
            stmt.setString(1,carModel);
            stmt.setTimestamp(2,date);
            stmt.setString(3,"pending");
            stmt.setString(4, user.getUserEmail());
            stmt.setInt(5,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request placed successfully.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }
        }
      SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
      String emailMessageTOCustomer = "Dear Customer,\n\n"
              + "We hope this email finds you well. We would like to request information about your order:\n\n"
              + "Order Name: " + orderName + "\n"
              + "Date: " + orderData + "\n"
              + "Installer Email: " + installerEmail + "\n\n"
              + "Please provide the necessary details at your earliest convenience.\n\n"
              + "Thank you,\nCar Accessories Company";
      String emailMessageToInstaller = "Dear Installer,\n\n"
              + "We hope this email finds you well. We would like to request information about your installation :\n\n"
              + "Order Name: " + orderName + "\n"
              + "Date: " + orderData + "\n"
              + "Customer Email: " + customer_email + "\n\n"
              + "Please provide the necessary details at your earliest convenience.\n\n"
              + "Thank you,\nCar Accessories Company";
      toCustomerEmail.sendNotificationToCustomer(customer_email, emailMessageTOCustomer);
      toCustomerEmail.sendNotificationToInstaller( emailMessageToInstaller);
    }

    public void showScheduled() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`carModel`,`assigned`,`preferredDate`,`status`" +
                " FROM install_request WHERE status =? AND email =?";
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        Logger showScheduleogger = Logger.getLogger("ShowScheduled");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, "scheduled");
            stmt.setString(2, user.getUserEmail());
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

    public void showCompleted() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`carModel`,`assigned`,`preferredDate`,`status`" +
                " FROM install_request WHERE status =? AND email =?";
       ConnectDB connection = new ConnectDB();
        connection.testConn();
        Logger shoeCompleteLogger = Logger.getLogger("ShowCompleted");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, "completed");
            stmt.setString(2, user.getUserEmail());
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
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        Logger showCanceldLogger = Logger.getLogger("ShowCanceled");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, "canceled");
            stmt.setString(2, user.getUserEmail());
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
}
