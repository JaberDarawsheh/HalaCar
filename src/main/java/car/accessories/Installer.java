package car.accessories;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Installer extends User{
    private static final Logger logger = Logger.getLogger(Installer.class.getName());
    private UserLoginPage user;
    public Installer(String userEmail,String userPassword) {
        super(userEmail,userPassword);
        user=new UserLoginPage(userEmail,userPassword);
    }


    public void showPending() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`email`,`carModel`,`preferredDate`" +
                " FROM install_request WHERE status =?";
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        Logger pendingLogger = Logger.getLogger("ShowPending");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, "pending");
            ResultSet rSet = stmt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            String format = "| %-15s | %-10s | %-15s | %-30s | %-15s | %-20s |%n";
            logger.log(Level.INFO, String.format(format, "Request Number", "Product ID", "Product Type", "Requester Email", "Car Model", "Preferred Date"));

            while (rSet.next()) {
                int rid = rSet.getInt("rid");
                int pid = rSet.getInt("pid");
                String productName = rSet.getString("productName");
                String email = rSet.getString("email");
                String resultCarModel = rSet.getString("carModel");
                String preferredDate = rSet.getString("preferredDate");
                logger.log(Level.INFO, String.format(format, rid, pid, productName, email, resultCarModel, preferredDate));
            }
        }
    }

    public void setTime(int id,String time) throws SQLException {
        String updateTimeSQL = "UPDATE install_request SET preferredDate = TIMESTAMP(CONCAT(CAST(DATE(preferredDate) AS DATE), ' ', ?))"+
                               " WHERE rid=?";
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(updateTimeSQL)){
            stmt.setString(1,time);
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request time scheduled.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
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
            return rSet.getString("status");

        }

    }
   private static final String update="UPDATE install_request SET status = ? WHERE rid =?";
    public void setScheduled(int id) throws SQLException {
        String sche=update;
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(sche)){
            stmt.setString(1,"scheduled");
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to scheduled.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }

        }
    }

    public void assign(int id) throws SQLException {
        String assign =update;
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(assign)){
            stmt.setString(1, user.getUserEmail());
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request has been assigned to :"+user.getUserEmail());

            } else {
                logger.warning("\nsome thing went wrong please try again later");
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
            //throw new RuntimeException("wrong time format");
        }
    }

    public void setCompleted(int id) throws SQLException {
        String sche=update;
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(sche)){
            stmt.setString(1,"completed");
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to completed.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }

        }
    }

    public void setCanceled(int id) throws SQLException {
        String sche="UPDATE install_request SET status = ? WHERE rid =?";
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        try(PreparedStatement stmt =connection.getConnection().prepareStatement(sche)){
            stmt.setString(1,"canceled");
            stmt.setInt(2,id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("The request status updated to canceled.");

            } else {
                logger.warning("\nsome thing went wrong please try again later");
            }

        }

    }

    public void showAssigned() throws SQLException {
        String query = "SELECT `rid`,`pid`,`productName`,`productType`,`email`,`carModel`,`preferredDate`" +
                " FROM install_request WHERE assigned =?";
        ConnectDB connection = new ConnectDB();
        connection.testConn();
        Logger assignLogger = Logger.getLogger("ShowAssigned");

        try (PreparedStatement stmt = connection.getConnection().prepareStatement(query)) {
            stmt.setString(1, user.getUserEmail());
            ResultSet rSet = stmt.executeQuery();
            ResultSetMetaData metaData = rSet.getMetaData();
            String format = "| %-15s | %-10s | %-15s | %-30s | %-15s | %-20s |%n";
            logger.log(Level.INFO, String.format(format, "Request Number", "Product ID", "Product Type", "Requester Email", "Car Model", "Preferred Date"));

            while (rSet.next()) {
                int rid = rSet.getInt("rid");
                int pid = rSet.getInt("pid");
                String productName = rSet.getString("productName");
                String productType = rSet.getString("productType");
                String email = rSet.getString("email");
                String carModel = rSet.getString("carModel");
                String preferredDate = rSet.getString("preferredDate");
                logger.log(Level.INFO, String.format(format, rid, pid, productName, email, carModel, preferredDate));
            }
        }
    }
}
