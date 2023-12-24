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
    private String pass;
    private static final Logger logger = Logger.getLogger(Customer.class.getName());
    private static final String P_NAME="Product Name";
    private static final String STATUS="Status";
    private static final String PID="Product ID";
    private static final String CAR_MODEL="Car Model";
    private static final String INSTALLER_MAIL="Installer Email";
    private static final String REQUEST_NUM="Request Number";
    private static final String P_TYPE="Product Type";
    private static final String INSTALL_DATE="Installation Date";
    private static final String SOME_THING="\nsome thing went wrong please try again later";
    private static final String SELECT_QUERY="SELECT `rid`,`pid`,`productName`,`productType`,`carModel`,`assigned`,`preferredDate`,`status` FROM install_request WHERE status =? and email = ? ";
    private static final String FORMATER="| %-15s | %-10s | %-15s | %-20s | %-15s | %-30s | %-10s |%n";
    public Customer(String userEmail, String userPassword) {
        super(userEmail,userPassword);
        pass = userPassword;
        user=new UserLoginPage(userEmail,userPassword);
        cat=new ProductCat();
    }

    public void showPersonalInfo() {
        logger.info("Personal Information ");
        logger.info("Email: " + user.getUserEmail());
    }


    public void showHistory() throws SQLException {
        String historySQL = "SELECT `productName`,`productType`,`unitPrice`,`orderDate`" +
                " FROM history WHERE email=? ";
        ConnectDB dataBase = new ConnectDB();
        dataBase.testConn();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        try (PreparedStatement stmnt = dataBase.getConnection().prepareStatement(historySQL)) {
            stmnt.setString(1, user.getUserEmail());
            ResultSet rSet = stmnt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String format = "| %-20s | %-15s | %-10s | %-12s |%n";
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, String.format(format, P_NAME, P_TYPE, "Unit Price", "Order Date"));
            }
            int[] columnWidths={20,15,10,12};
            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();
                for (int i = 1; i < numberOfColumns; i++) {
                    String columnValue = rSet.getString(i);
                    String formSpes="%-"+columnWidths[i-1]+"s";
                    String formattedColumn = String.format(formSpes, columnValue);
                    rowData.append(formattedColumn);
                    rowData.append(" | "); // Add separator between columns

                }
                Date date = rSet.getDate("orderDate");
                String formattedDateTime = dateFormat.format(date);
                String [] dateTimeSplit=formattedDateTime.split("\\s+");
                String onlydate =dateTimeSplit[0];
                rowData.append(onlydate);
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(String.format("| %-" + "%ds   |%n", 20, rowData));
                }
            }

        }
    }


    public boolean calatogAvailable(){
        boolean returnValue=false;
        try {
            returnValue= cat.systemFoundCatalog();
        } catch (SQLException e) {
            logger.info("the cataloge currently un available please try again later");
        }
        return returnValue;
    }

    public void showCatalogToCustomer() throws SQLException{
        cat.showProductsCatalogToUser();
    }

    public void addToCart(int id, int quantity) throws SQLException {
        String cartSQL = "INSERT INTO cart (pid,productName,productType,unitPrice,quantity,email)"+
                     "SELECT `id`,`productName`,`productType`,`productPrice`,?,?"+
                     "FROM ProductCatalog WHERE id =?";
        ConnectDB connDataBase = new ConnectDB();
        connDataBase.testConn();
        try(PreparedStatement stmt = connDataBase.getConnection().prepareStatement(cartSQL)){
            stmt.setInt(1, quantity);
            stmt.setString(2, user.getUserEmail());
            stmt.setInt(3,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("added to the cart successfully.");

            } else {
                logger.warning(SOME_THING);
            }
        }
    }

    public void viewCart() throws SQLException {
        String showCart = "SELECT `pid`,`productName`,`productType`,`unitPrice`,`quantity` FROM cart WHERE email =?";
        ConnectDB connDataBase = new ConnectDB();
        connDataBase.testConn();


        try (PreparedStatement stmnt = connDataBase.getConnection().prepareStatement(showCart)) {
            stmnt.setString(1, user.getUserEmail());
            ResultSet rSet = stmnt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, String.format("| %-5s | %-30s | %-15s | %-10s | %-10s |%n", "ID", P_NAME, P_TYPE, "Unit Price", "Quantity"));
            }
            int[] columnWidths={5,30,15,10,10};
            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();
                tableFormatPrint(rSet, numberOfColumns, rowData, columnWidths);
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(String.format("| %-5s  |%n", rowData));
                }
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
                logger.warning(SOME_THING);
            }
        }
        try (PreparedStatement stmt = conDB.getConnection().prepareStatement(delteOrderFromCart)){
            stmt.setString(1,user.getUserEmail());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The order is placed successfully.");

            } else {
                logger.warning(SOME_THING);
            }
        }



    }

    public int showProfile(int choice) {

        logger.info("=== Option  Menu ===");
        logger.info("Sub-option 1 View order history");
        logger.info("Sub-option 2 View installation requests history");
        logger.info("Sub-option 3 Go back to the main menu");
        //the user will enter the corresponding number of the action wanted to be performed
        if (choice != 3) {

            Scanner scanner = new Scanner(System.in);

            return scanner.nextInt();
        }else
            return choice;

    }

    public void installationRequest(int id,String carModel,String installationDate) throws SQLException,ParseException  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = dateFormat.parse(installationDate);


        String creatRequest="INSERT INTO install_request (pid,productName,productType,email,carModel,preferredDate,status)"+
                            "SELECT `id`,`productName`,`productType`,?,?,?,?"+
                            "FROM ProductCatalog WHERE id=?";
        String customerEmail=user.getUserEmail();
        String query ="SELECT * FROM ProductCatalog WHERE id=?";
        String orderName;
        Timestamp date = new Timestamp(date1.getTime());
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(creatRequest)){
            stmt.setString(2,carModel);
            stmt.setTimestamp(3,date);
            stmt.setString(4,"pending");
            stmt.setString(1, user.getUserEmail());
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

                toCustomerEmail.sendNotificationToCustomer(customerEmail, emailMessageTOCustomer);

            } else {
                logger.warning(SOME_THING);
            }
        }


    }

    public void showScheduled() throws SQLException {

        ConnectDB connection = new ConnectDB();
        connection.testConn();


        try (PreparedStatement stmt = connection.getConnection().prepareStatement(SELECT_QUERY)) {
            stmt.setString(1, "scheduled");
            stmt.setString(2, user.getUserEmail());
            ResultSet rSet = stmt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, String.format(FORMATER, REQUEST_NUM, PID, P_NAME, P_TYPE, CAR_MODEL, INSTALLER_MAIL, INSTALL_DATE, STATUS));
            }
            int[] columnWidths={15,10,15,20,15,30,10};

            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();


                for (int i = 1; i <= numberOfColumns && i <= columnWidths.length; i++) {
                    String columnValue = rSet.getString(i);
                    String formSpes="%-" + columnWidths[i-1] + "s";
                    String formattedColumn = String.format(formSpes, columnValue);
                    rowData.append(formattedColumn);
                    rowData.append(" | "); // Add separator between columns
                }
                if (logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, String.format("| %-5s ", rowData.toString()));
                }
            }
        }
    }

    public void showCompleted() throws SQLException {

        ConnectDB connection = new ConnectDB();
        connection.testConn();


        try (PreparedStatement stmt = connection.getConnection().prepareStatement(SELECT_QUERY)) {
            stmt.setString(1, "completed");
            sqlGetUserEmail(stmt);
        }
    }

    public void showCanceled() throws SQLException {

        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try (PreparedStatement stmt = connection.getConnection().prepareStatement(SELECT_QUERY)) {
            stmt.setString(1, "canceled");
            sqlGetUserEmail(stmt);
        }
    }

    private void sqlGetUserEmail(PreparedStatement stmt) throws SQLException {
        stmt.setString(2, user.getUserEmail());
        ResultSet rSet = stmt.executeQuery();
        ResultSetMetaData metaData = rSet.getMetaData();
        int numberOfColumns = metaData.getColumnCount();

        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, String.format(FORMATER, REQUEST_NUM, PID, P_TYPE, INSTALLER_MAIL, CAR_MODEL, INSTALL_DATE, STATUS));
        }
        while (rSet.next()) {
            StringBuilder rowData = new StringBuilder();
            int [] columnValue={15,10,15,20,15,30,10};
            for (int i = 1; i <= numberOfColumns&&i <= columnValue.length; i++) {
                tableFormaterV2(rSet, numberOfColumns, rowData, columnValue, i);
            }
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, rowData.toString());
            }
        }
    }

    private void tableFormaterV2(ResultSet rSet, int numberOfColumns, StringBuilder rowData, int[] columnValue, int i) throws SQLException {
        String columnValues = rSet.getString(i);
        String formSpes="%-"+columnValue[i-1]+"s";
        String formattedColumn = String.format(formSpes, columnValues);
        rowData.append(formattedColumn);
        if (i < numberOfColumns) {
            rowData.append(" | ");
        }
    }

    public void showAllRequests() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`carModel`,`assigned`,`preferredDate`,`status`" +
                " FROM install_request WHERE  email =?";
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, user.getUserEmail());
            ResultSet rSet = stmt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            String format = "| %-15s | %-10s | %-15s | %-20s | %-20s | %-15s | %-30s | %-10s |";
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, String.format(format, REQUEST_NUM, PID, P_NAME, P_TYPE, INSTALLER_MAIL, CAR_MODEL, INSTALL_DATE, STATUS));
            }
            while (rSet.next()) {
                StringBuilder rowData = new StringBuilder();

                int [] columnval={15,10,15,20,20,15,30,10};
                tableFormatPrint(rSet, numberOfColumns, rowData, columnval);
                if (logger.isLoggable(Level.INFO)) {
                    logger.log(Level.INFO, rowData.toString());
                }
            }
        }
    }

    private void tableFormatPrint(ResultSet rSet, int numberOfColumns, StringBuilder rowData, int[] columnval) throws SQLException {
        for (int i = 1; i <= numberOfColumns; i++) {
            tableFormaterV2(rSet, numberOfColumns, rowData, columnval, i);
        }
    }

    public void changeEmail(String newEmail) throws SQLException {
        String sql = "UPDATE systemusers SET user_email = ? WHERE user_email = ?";
        ConnectDB conDB = new ConnectDB();
        conDB.testConn();
        try(PreparedStatement stmt = conDB.getConnection().prepareStatement(sql)){
            stmt.setString(2, user.getUserEmail());
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
