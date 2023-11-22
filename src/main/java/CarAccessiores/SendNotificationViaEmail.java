package CarAccessiores;

import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendNotificationViaEmail 
{
	static final Logger logger = Logger.getLogger(SendNotificationViaEmail.class.getName());
	public void sendNotificationToCustomer(String customerEmail, String messageContent)throws SQLException
	{
        String fromEmail = "caraccessioescompany@gmail.com";
        String password = "eujm kiyn xfjv kjfq";  
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerEmail));
            message.setSubject("Test Email");
            message.setText(messageContent);
            Transport.send(message);
            System.out.println("Verification email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
	public void sendNotificationToInstaller(String installerEmail, String InstallermessageContent)throws SQLException 
	{
		String fromEmail = "caraccessioescompany@gmail.com";
        String password = "eujm kiyn xfjv kjfq";  
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(InstallermessageContent));
            message.setSubject("Test Email");
            message.setText(InstallermessageContent);
            Transport.send(message);
            System.out.println("Verification email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}
   
}
