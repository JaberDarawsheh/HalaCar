package CarAccessiores;

import java.net.PasswordAuthentication;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;
import com.mysql.cj.Session;
import java.util.*;
import java.net.*;

public class SendNotificationViaEmail 
{
	public void sendNotificationToCustomer(String customerEmail, String message) throws SQLException 
	{
		 
		 // Recipient's email ID needs to be mentioned.
        String to = customerEmail;

        // Sender's email ID needs to be mentioned
        String from = "halacar@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() 
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("halacar@gmail.com", "12345678");

            }

        });
        session.setDebug(true);
        try

        {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject("Your Reques Status!!");
            // Now set the actual message
            message.setText("Your installation request is complete : \n "+message);
            System.out.println("sending email...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch(MessagingException mex)

        {
            mex.printStackTrace();
        }
		
	}

	public void sendNotificationToInstaller(String installerEmail, String message)throws SQLException 
	{
		 // Recipient's email ID needs to be mentioned.
        String to = installerEmail;

        // Sender's email ID needs to be mentioned
        String from = "halacar@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication() 
            {
            	return new PasswordAuthentication("halacar@gmail.com", "12345678");

            }

        });
        session.setDebug(true);
        try

        {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject("Your Request Status!!");
            // Now set the actual message
            message.setText("You have a new installation request : \n "+message);
            System.out.println("sending email...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch(MessagingException mex)

        {
            mex.printStackTrace();
        }
	}
   
}
