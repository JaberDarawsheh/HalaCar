package car.accessories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Installer extends User{
    private static final Logger logger = Logger.getLogger(ProductCat.class.getName());
    private String orderName;
    private String orderDate;
    private String customer_email;
    private String carModel;
    private String requestID;
    private String customerEmail;
    private String installationTime;
    private UserLoginPage user;
    private static final String tryAgainMessage= "\n some thing went wrong please try again later";
    private static final String updateQuery ="UPDATE install_request SET status = ? WHERE rid =?";
    public Installer(String userEmail, String userPassword) {
        super(userEmail,userPassword);
        user=new UserLoginPage(userEmail,userPassword);


    }


    public void showPending() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`email`,`carModel`,`preferredDate`" +
                " FROM install_request WHERE status =?";
        ConnectDB connection = new ConnectDB();
        connection.testConn();

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, "pending");
            ResultSet rSet = stmt.executeQuery();
            String format = "| %-15s | %-10s | %-15s | %-30s | %-15s | %-20s |%n";
            String toBePrinted=String.format(format, "Request Number", "Product ID", "Product Type", "Requester Email", "Car Model", "Preferred Date");
            logger.log(Level.INFO, toBePrinted);

            while (rSet.next()) {
                int rid = rSet.getInt("rid");
                int pid = rSet.getInt("pid");
                String productName = rSet.getString("productName");
                String productType = rSet.getString("productType");
                String email = rSet.getString("email");
                String carModel = rSet.getString("carModel");
                String preferredDate = rSet.getString("preferredDate");
                String infoToBePrinted=String.format(format, rid, pid, productName, email, carModel, preferredDate);
                logger.log(Level.INFO, infoToBePrinted);
            }
        }
    }

    public void setTime(int id,String time) throws SQLException {
        String updateTimeSQL = "UPDATE install_request SET preferredDate = TIMESTAMP(CONCAT(CAST(DATE(preferredDate) AS DATE), ' ', ?))"+
                               " WHERE rid=?";
        String query= "SELECT * FROM Install_request WHERE rid=?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(updateTimeSQL)){
            stmt.setString(1,time);
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request time scheduled.");

                try(PreparedStatement stmnt=connection.getConnection().prepareStatement(query)){
                    stmnt.setInt(1,id);
                    ResultSet rs= stmnt.executeQuery();
                    if(rs.next()) {
                        orderName = rs.getString("productName");
                        Date date = rs.getDate("preferredDate");
                        orderDate = dateFormat.format(date);
                        customer_email = rs.getString("email");
                        carModel = rs.getString("carModel");
                        requestID = rs.getString("rid");
                        customerEmail = rs.getString("email");
                    }
                }
                SendNotificationViaEmail toInstallerEmail = new SendNotificationViaEmail();
                String emailMessageToInstaller = "Dear Installer,\n\n"
                                + "We hope this email finds you well."
                                + "We would like to inform you that one of your installation requests with request id number: "+requestID+" has been set to installation on a specific time.\n\n"
                                + "Here are the request information :"
                                + "Part to be installed: " + orderName + "\n"
                                + "Car model :"+carModel+"\n"
                                + "Date of installation: " + orderDate + "\n"
                                + "Time of installation: " + installationTime + "\n"
                                + "Customer Email: " + customer_email + "\n\n"
                                + "Thank you,\nCar Accessories Company";
                toInstallerEmail.sendNotificationToInstaller(user.getUserEmail(), emailMessageToInstaller);
                SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
                String emailMessageToCustomer =
                                  "Dear Customer,\n\n"
                                + "We would like to inform you that your installation request with request id number : "+requestID+" has been set to installation on a specific time.\n"
                                + "Request information :\n"
                                + "Part to be installed: "+orderName+"\n"
                                + "Car model :"+carModel+"\n"
                                + "Date of installation :"+ orderDate +"\n"
                                + "Time of installation :"+ installationTime +"\n"
                                + "Installer email :"+user.getUserEmail()+"\n"
                                + "Please feel free to contact your installer on his email\n\n"
                                + "Thank you,\nCar Accessories Company";
                toCustomerEmail.sendNotificationToCustomer(customerEmail,emailMessageToCustomer);

            } else {
                logger.warning(tryAgainMessage);
            }

        }
    }

    public String getStatus(int id) throws SQLException {
        String statusSQL = "SELECT `status` FROM install_request WHERE rid=?";
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(statusSQL)){
            stmt.setInt(1,id);
            ResultSet rSet = stmt.executeQuery();
            if(rSet.next()) {
                String status = rSet.getString("status");
                return status;
            }
            return "request not found";


        }

    }

    public void setScheduled(int id) throws SQLException {
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(updateQuery)){
            stmt.setString(1,"scheduled");
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to scheduled.");

            } else {
                logger.warning(tryAgainMessage);
            }

        }
    }

    public void assign(int id) throws SQLException {
        String assign ="UPDATE install_request SET assigned = ? WHERE rid =?";
        String query= "SELECT * FROM Install_request WHERE rid=?";
        ConnectDB connection = new ConnectDB();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(assign)){
            stmt.setString(1, user.getUserEmail());
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request has been assigned to :"+user.getUserEmail());
                try(PreparedStatement stmnt=connection.getConnection().prepareStatement(query)){
                    stmnt.setInt(1,id);
                    ResultSet rs= stmnt.executeQuery();
                    rs.next();//
                    orderName = rs.getString("productName");
                    Date date = rs.getDate("preferredDate");
                    String formatedDateTime=dateFormat.format(date);
                    if (formatedDateTime.contains(" ")) {
                        String[] dateTimeSplit = formatedDateTime.split("\\s+");
                        installationTime = dateTimeSplit[1];
                        orderDate = dateTimeSplit[0];
                    }else {
                        installationTime ="not determined yet";
                        orderDate =formatedDateTime;

                    }
                    customer_email = rs.getString("email");
                    carModel = rs.getString("carModel");
                    requestID = rs.getString("rid");
                    customerEmail = rs.getString("email");
                }
                SendNotificationViaEmail toInstallerEmail = new SendNotificationViaEmail();
                String emailMessageToInstaller =
                        "Dear Installer,\n\n"
                                + "We hope this email finds you well."
                                + "We would like to inform you that the installation request with request id number: "+requestID+" has been assigned to you.\n\n"
                                + "Here are the request information :"
                                + "Part to be installed: " + orderName + "\n"
                                + "Car model :"+carModel+"\n"
                                + "Date of installation: " + orderDate + "\n"
                                + "Customer Email: " + customer_email + "\n\n"
                                + "Thank you,\nCar Accessories Company";
                toInstallerEmail.sendNotificationToInstaller(user.getUserEmail(), emailMessageToInstaller);
                SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
                String emailMessageToCustomer =
                        "Dear Customer,\n\n"
                                + "We would like to inform you that your installation request with request id number : "+requestID+" has been assigned to one of our installers\n"
                                + "Request information :\n"
                                + "Part to be installed: "+orderName+"\n"
                                + "Car model :"+carModel+"\n"
                                + "Date of installation :"+ orderDate +"\n"
                                + "Installer email :"+user.getUserEmail()+"\n"
                                + "Please feel free to contact your installer on his email\n\n"
                                + "Thank you,\nCar Accessories Company";
                toCustomerEmail.sendNotificationToCustomer(customerEmail,emailMessageToCustomer);

            } else {
                logger.warning(tryAgainMessage);
            }

        }

    }

    public void schedule(int id, String time) throws SQLException {
        int length =time.length();
        if(length==8) {
            setTime(id, time);
            setScheduled(id);
            assign(id);
        }else{
            logger.warning("wrong time format");
            throw new RuntimeException("wrong time format");

        }
    }

    public void setCompleted(int id) throws SQLException {

        String query= "SELECT * FROM Install_request WHERE rid=?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(updateQuery)){
            stmt.setString(1,"completed");
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to completed.");
                try(PreparedStatement stmnt=connection.getConnection().prepareStatement(query)){
                    stmnt.setInt(1,id);
                    ResultSet rs= stmnt.executeQuery();
                    if(rs.next()) {
                        orderName = rs.getString("productName");
                        Date date = rs.getDate("preferredDate");
                        String formatedDateTime = dateFormat.format(date);
                        String[] dateTimeSplit = formatedDateTime.split("\\s+");
                        installationTime = dateTimeSplit[0];
                        orderDate = dateTimeSplit[0];
                        customer_email = rs.getString("email");
                        carModel = rs.getString("carModel");
                        requestID = rs.getString("rid");
                        customerEmail = rs.getString("email");
                    }
                }
                SendNotificationViaEmail toInstallerEmail = new SendNotificationViaEmail();
                String emailMessageToInstaller = "Dear Installer,\n\n"
                                + "We hope this email finds you well."
                                + "We would like to inform you that set the status of the following  installation request with request id number: "+requestID+" to completed.\n\n"
                                + "Here are the request information :"
                                + "Part to be installed: " + orderName + "\n"
                                + "Car model :"+carModel+"\n"
                                + "Date of installation: " + orderDate + "\n"
                                + "Customer Email: " + customer_email + "\n\n"
                                + "Thank you,\nCar Accessories Company";
                toInstallerEmail.sendNotificationToInstaller(user.getUserEmail(), emailMessageToInstaller);
                SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
                String emailMessageToCustomer = "Dear Customer,\n\n"
                                + "We would like to inform you that the status of the following installation request with request id number : "+requestID+" has been updated to completed\n"
                                + "Request information :\n"
                                + "Part to be installed: "+orderName+"\n"
                                + "Car model :"+carModel+"\n"
                                + "Date of installation :"+ orderDate +"\n"
                                + "Installer email :"+user.getUserEmail()+"\n"
                                + "Please feel free to contact us or your installer on his email\n\n"
                                + "Thank you,\nCar Accessories Company";
                toCustomerEmail.sendNotificationToCustomer(customerEmail,emailMessageToCustomer);

            } else {
                logger.warning(tryAgainMessage);
            }

        }
    }

    public void setCanceled(int id) throws SQLException {
        String query= "SELECT * FROM Install_request WHERE rid=?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(updateQuery)){
            stmt.setString(1,"canceled");
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to canceled.");
                try(PreparedStatement stmnt=connection.getConnection().prepareStatement(query)){
                    stmnt.setInt(1,id);
                    ResultSet rs= stmnt.executeQuery();
                    if(rs.next()) {
                        orderName = rs.getString("productName");
                        Date date = rs.getDate("preferredDate");
                        String formatedDateTime = dateFormat.format(date);

                        if (formatedDateTime.contains(" ")) {
                            String[] dateTimeSplit = formatedDateTime.split("\\s+");
                            installationTime = dateTimeSplit[1];
                            orderDate = dateTimeSplit[0];
                        }else {
                            installationTime ="not determined yet";
                            orderDate =formatedDateTime;

                        }
                        customer_email = rs.getString("email");
                        carModel = rs.getString("carModel");
                        requestID = rs.getString("rid");
                        customerEmail = rs.getString("email");
                    }
                }
                SendNotificationViaEmail toInstallerEmail = new SendNotificationViaEmail();
                String emailMessageToInstaller =
                                  "Dear Installer,\n\n"
                                + "We hope this email finds you well."
                                + "We would like to inform you that set the status of the following  installation request with request id number: "+requestID+" to canceled.\n\n"
                                + "Here are the request information :"
                                + "Part to be installed: " + orderName + "\n"
                                + "Car model :"+carModel+"\n"
                                + "Date of installation: " + orderDate + "\n"
                                + "Customer Email: " + customer_email + "\n\n"
                                + "Thank you,\nCar Accessories Company";
                toInstallerEmail.sendNotificationToInstaller(user.getUserEmail(), emailMessageToInstaller);
                SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
                String emailMessageToCustomer =
                                  "Dear Customer,\n\n"
                                + "We would like to inform you that the installation request with request id number : "+requestID+" has been canceled.\n"
                                + "Request information :\n"
                                + "Part to be installed: "+orderName+"\n"
                                + "Car model :"+carModel+"\n"
                                + "Date of installation :"+ orderDate +"\n"
                                + "Installer email :"+user.getUserEmail()+"\n"
                                + "You can always apply for a new installation request.\n\n"
                                + "Thank you,\nCar Accessories Company";
                toCustomerEmail.sendNotificationToCustomer(customerEmail,emailMessageToCustomer);

            } else {
                logger.warning(tryAgainMessage);
            }

        }

    }

    public void showAssigned() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`email`,`carModel`,`preferredDate`" +
                " FROM install_request WHERE assigned =?";
        ConnectDB connection = new ConnectDB();
        connection.testConn();

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, user.getUserEmail());
            ResultSet rSet = stmt.executeQuery();
            String format = "| %-15s | %-10s | %-15s | %-30s | %-15s | %-20s |%n";
            String headOfTable=String.format(format, "Request Number", "Product ID", "Product Type", "Requester Email", "Car Model", "Preferred Date");
            logger.log(Level.INFO,headOfTable);

            while (rSet.next()) {
                int rid = rSet.getInt("rid");
                int pid = rSet.getInt("pid");
                String productName = rSet.getString("productName");
                String productType = rSet.getString("productType");
                String email = rSet.getString("email");
                String carModel = rSet.getString("carModel");
                String preferredDate = rSet.getString("preferredDate");
                String contentOfTable=String.format(format, rid, pid,productType ,productName, email, carModel, preferredDate);
                logger.log(Level.INFO, contentOfTable);
            }
        }
    }
}
