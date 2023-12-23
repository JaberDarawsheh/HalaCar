package car.accessories;


import java.util.Properties;
import java.util.logging.Level;
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
	private static final Logger logger = Logger.getLogger(SendNotificationViaEmail.class.getName());

    private static final String SECURE_PASS="eujm kiyn xfjv kjfq";

    public void sendNotificationToCustomer(String customerEmail, String messageContent) {
        String subject = "Test Email";
        sendNotification(customerEmail, subject, messageContent);
    }

    public void sendNotificationToInstaller(String installerEmail, String installerMessageContent) {
        String subject = "Test Email";
        sendNotification(installerEmail, subject, installerMessageContent);
    }

    private void sendNotification(String toEmail, String subject, String messageContent) {
        String fromEmail = "caraccessioescompany@gmail.com";
        final String password = getPasswordFromSecureStorage();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(messageContent);
            Transport.send(message);
            logger.info("Verification email sent successfully!");
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "An error occurred", e);
        }
    }

    private String getPasswordFromSecureStorage() {
        return SECURE_PASS;
    }
   
}
