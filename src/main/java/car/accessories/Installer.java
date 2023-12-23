package car.accessories;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Installer extends User{
    private static final Logger logger = Logger.getLogger(Installer.class.getName());
    private String orderName;
    private String orderDate;
    private String customerMail;
    private String carModel;
    private String requestID;
    private String customerEmail;
    private String installationTime;
    private UserLoginPage user;
    private static final String TRY_AGAIN_MESSAGE = "\n some thing went wrong please try again later";
    private static final String UPDATE_QUERY ="UPDATE install_request SET status = ? WHERE rid =?";
    private static final String P_NAME="productName";
    private static final String MAIL ="email";
    private static final String CAR_MODEL="carModel";
    private static final String P_DATE="preferredDate";
    private static final String REQUEST_TABLE_QUERY="SELECT * FROM Install_request WHERE rid=?";
    private static final String DATE_FORMAT="dd-MM-yyyy";
    private static final String INSTALLER_GREETING="Dear Installer,\n\nWe hope this email finds you well.";
    private static final String CUSTOMER_GREETING="Dear Customer,\n\n";
    private static final String REQ_INFO="Here are the request information :";
    private static final String REQUEST_INFO="Request information :\n";
    private static final String PART_TO_INSTALL="Part to be installed: ";
    private static final String CAR_MOD="Car model :";
    private static final String DATE_OF="Date of installation: ";
    private static final String C_MAIL="Customer Email: ";
    private static final String I_MAIL="Installer email :";
    private static final String MAIL_SIGNATURE="Thank you,\nCar Accessories Company";
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
                String productName = rSet.getString(P_NAME);
                String productType = rSet.getString("productType");
                String email = rSet.getString(MAIL);
                String carsModel = rSet.getString(CAR_MODEL);
                String preferredDate = rSet.getString(P_DATE);
                String infoToBePrinted=String.format(format, rid, pid,productType ,productName, email, carsModel, preferredDate);
                logger.log(Level.INFO, infoToBePrinted);
            }
        }
    }

    public void setTime(int id,String time) throws SQLException {
        String updateTimeSQL = "UPDATE install_request SET preferredDate = TIMESTAMP(CONCAT(CAST(DATE(preferredDate) AS DATE), ' ', ?))"+
                               " WHERE rid=?";
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(updateTimeSQL)){
            stmt.setString(1,time);
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request time scheduled.");

                try(PreparedStatement stmnt=connection.getConnection().prepareStatement(REQUEST_TABLE_QUERY)){
                    stmnt.setInt(1,id);
                    ResultSet rs= stmnt.executeQuery();
                    if(rs.next()) {
                        orderName = rs.getString(P_NAME);
                        Date date = rs.getDate(P_DATE);
                        orderDate = dateFormat.format(date);
                        customerMail = rs.getString(MAIL);
                        carModel = rs.getString(CAR_MODEL);
                        requestID = rs.getString("rid");
                        customerEmail = rs.getString(MAIL);
                    }
                }
                SendNotificationViaEmail toInstallerEmail = new SendNotificationViaEmail();
                String emailMessageToInstaller = INSTALLER_GREETING

                                + "We would like to inform you that one of your installation requests with request id number: "+requestID+" has been set to installation on a specific time.\n\n"
                                + REQ_INFO
                                + PART_TO_INSTALL + orderName + "\n"
                                + CAR_MOD+carModel+"\n"
                                + DATE_OF + orderDate + "\n"
                                + "Time of installation: " + installationTime + "\n"
                                + C_MAIL + customerMail + "\n\n"
                                + MAIL_SIGNATURE;
                toInstallerEmail.sendNotificationToInstaller(user.getUserEmail(), emailMessageToInstaller);
                SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
                String emailMessageToCustomer =
                        CUSTOMER_GREETING
                                + "We would like to inform you that your installation request with request id number : "+requestID+" has been set to installation on a specific time.\n"
                                + REQUEST_INFO
                                + PART_TO_INSTALL+orderName+"\n"
                                + CAR_MOD+carModel+"\n"
                                + DATE_OF+ orderDate +"\n"
                                + "Time of installation :"+ installationTime +"\n"
                                + I_MAIL+user.getUserEmail()+"\n"
                                + "Please feel free to contact your installer on his email\n\n"
                                + MAIL_SIGNATURE;
                toCustomerEmail.sendNotificationToCustomer(customerEmail,emailMessageToCustomer);

            } else {
                logger.warning(TRY_AGAIN_MESSAGE);
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

                return rSet.getString("status");
            }
            return "request not found";


        }

    }

    public void setScheduled(int id) throws SQLException {
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(UPDATE_QUERY)){
            stmt.setString(1,"scheduled");
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to scheduled.");

            } else {
                logger.warning(TRY_AGAIN_MESSAGE);
            }

        }
    }

    public void assign(int id) throws SQLException {
        String assign ="UPDATE install_request SET assigned = ? WHERE rid =?";

        ConnectDB connection = new ConnectDB();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(assign)){
            stmt.setString(1, user.getUserEmail());
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request has been assigned to :"+user.getUserEmail());
                try(PreparedStatement stmnt=connection.getConnection().prepareStatement(REQUEST_TABLE_QUERY)){
                    stmnt.setInt(1,id);
                    ResultSet rs= stmnt.executeQuery();
                    rs.next();//
                    orderName = rs.getString(P_NAME);
                    Date date = rs.getDate(P_DATE);
                    String formatedDateTime=dateFormat.format(date);
                    if (formatedDateTime.contains(" ")) {
                        String[] dateTimeSplit = formatedDateTime.split("\\s+");
                        installationTime = dateTimeSplit[1];
                        orderDate = dateTimeSplit[0];
                    }else {
                        installationTime ="not determined yet";
                        orderDate =formatedDateTime;

                    }
                    customerMail = rs.getString(MAIL);
                    carModel = rs.getString(CAR_MODEL);
                    requestID = rs.getString("rid");
                    customerEmail = rs.getString(MAIL);
                }
                SendNotificationViaEmail toInstallerEmail = new SendNotificationViaEmail();
                String emailMessageToInstaller =
                                    INSTALLER_GREETING

                                + "We would like to inform you that the installation request with request id number: "+requestID+" has been assigned to you.\n\n"
                                + REQ_INFO
                                + PART_TO_INSTALL + orderName + "\n"
                                + CAR_MOD+carModel+"\n"
                                + DATE_OF + orderDate + "\n"
                                + C_MAIL + customerMail + "\n\n"
                                + MAIL_SIGNATURE;
                toInstallerEmail.sendNotificationToInstaller(user.getUserEmail(), emailMessageToInstaller);
                SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
                String emailMessageToCustomer =
                        CUSTOMER_GREETING
                                + "We would like to inform you that your installation request with request id number : "+requestID+" has been assigned to one of our installers\n"
                                + REQUEST_INFO
                                + PART_TO_INSTALL+orderName+"\n"
                                + CAR_MOD+carModel+"\n"
                                + DATE_OF+ orderDate +"\n"
                                + I_MAIL+user.getUserEmail()+"\n"
                                + "Please feel free to contact your installer on his email\n\n"
                                + MAIL_SIGNATURE;
                toCustomerEmail.sendNotificationToCustomer(customerEmail,emailMessageToCustomer);

            } else {
                logger.warning(TRY_AGAIN_MESSAGE);
            }

        }

    }

    public void schedule(int id, String time) throws SQLException,IOException {
        int length =time.length();
        if(length==8) {
            setTime(id, time);
            setScheduled(id);
            assign(id);
        }else{
            logger.warning("wrong time format");
            throw new IOException("wrong time format");

        }
    }

    public void setCompleted(int id) throws SQLException {


        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(UPDATE_QUERY)){
            stmt.setString(1,"completed");
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to completed.");
                try(PreparedStatement stmnt=connection.getConnection().prepareStatement(REQUEST_TABLE_QUERY)){
                    stmnt.setInt(1,id);
                    ResultSet rs= stmnt.executeQuery();
                    if(rs.next()) {
                        orderName = rs.getString(P_NAME);
                        Date date = rs.getDate(P_DATE);
                        String formatedDateTime = dateFormat.format(date);
                        String[] dateTimeSplit = formatedDateTime.split("\\s+");
                        installationTime = dateTimeSplit[0];
                        orderDate = dateTimeSplit[0];
                        customerMail = rs.getString(MAIL);
                        carModel = rs.getString(CAR_MODEL);
                        requestID = rs.getString("rid");
                        customerEmail = rs.getString(MAIL);
                    }
                }
                SendNotificationViaEmail toInstallerEmail = new SendNotificationViaEmail();
                String emailMessageToInstaller = INSTALLER_GREETING

                                + "We would like to inform you that set the status of the following  installation request with request id number: "+requestID+" to completed.\n\n"
                                + REQ_INFO
                                + PART_TO_INSTALL + orderName + "\n"
                                + CAR_MOD+carModel+"\n"
                                + DATE_OF + orderDate + "\n"
                                + C_MAIL + customerMail + "\n\n"
                                + MAIL_SIGNATURE;
                toInstallerEmail.sendNotificationToInstaller(user.getUserEmail(), emailMessageToInstaller);
                SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
                String emailMessageToCustomer = CUSTOMER_GREETING
                                + "We would like to inform you that the status of the following installation request with request id number : "+requestID+" has been updated to completed\n"
                                + REQUEST_INFO
                                + PART_TO_INSTALL+orderName+"\n"
                                + CAR_MOD+carModel+"\n"
                                + DATE_OF+ orderDate +"\n"
                                + I_MAIL+user.getUserEmail()+"\n"
                                + "Please feel free to contact us or your installer on his email\n\n"
                                + MAIL_SIGNATURE;
                toCustomerEmail.sendNotificationToCustomer(customerEmail,emailMessageToCustomer);

            } else {
                logger.warning(TRY_AGAIN_MESSAGE);
            }

        }
    }

    public void setCanceled(int id) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(UPDATE_QUERY)){
            stmt.setString(1,"canceled");
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to canceled.");
                try(PreparedStatement stmnt=connection.getConnection().prepareStatement(REQUEST_TABLE_QUERY)){
                    stmnt.setInt(1,id);
                    ResultSet rs= stmnt.executeQuery();
                    if(rs.next()) {
                        orderName = rs.getString(P_NAME);
                        Date date = rs.getDate(P_DATE);
                        String formatedDateTime = dateFormat.format(date);

                        if (formatedDateTime.contains(" ")) {
                            String[] dateTimeSplit = formatedDateTime.split("\\s+");
                            installationTime = dateTimeSplit[1];
                            orderDate = dateTimeSplit[0];
                        }else {
                            installationTime ="not determined yet";
                            orderDate =formatedDateTime;

                        }
                        customerMail = rs.getString(MAIL);
                        carModel = rs.getString(CAR_MODEL);
                        requestID = rs.getString("rid");
                        customerEmail = rs.getString(MAIL);
                    }
                }
                SendNotificationViaEmail toInstallerEmail = new SendNotificationViaEmail();
                String emailMessageToInstaller =
                                    INSTALLER_GREETING

                                + "We would like to inform you that set the status of the following  installation request with request id number: "+requestID+" to canceled.\n\n"
                                + REQ_INFO
                                + PART_TO_INSTALL + orderName + "\n"
                                + CAR_MOD+carModel+"\n"
                                + DATE_OF + orderDate + "\n"
                                + C_MAIL + customerMail + "\n\n"
                                + MAIL_SIGNATURE;
                toInstallerEmail.sendNotificationToInstaller(user.getUserEmail(), emailMessageToInstaller);
                SendNotificationViaEmail toCustomerEmail = new SendNotificationViaEmail();
                String emailMessageToCustomer =
                        CUSTOMER_GREETING
                                + "We would like to inform you that the installation request with request id number : "+requestID+" has been canceled.\n"
                                + REQUEST_INFO
                                + PART_TO_INSTALL+orderName+"\n"
                                + CAR_MOD+carModel+"\n"
                                + DATE_OF+ orderDate +"\n"
                                + I_MAIL+user.getUserEmail()+"\n"
                                + "You can always apply for a new installation request.\n\n"
                                + MAIL_SIGNATURE;
                toCustomerEmail.sendNotificationToCustomer(customerEmail,emailMessageToCustomer);

            } else {
                logger.warning(TRY_AGAIN_MESSAGE);
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
                String productName = rSet.getString(P_NAME);
                String productType = rSet.getString("productType");
                String email = rSet.getString(MAIL);
                String carModels = rSet.getString(CAR_MODEL);
                String preferredDate = rSet.getString(P_DATE);
                String contentOfTable=String.format(format, rid, pid,productType ,productName, email, carModels, preferredDate);
                logger.log(Level.INFO, contentOfTable);
            }
        }
    }
}
