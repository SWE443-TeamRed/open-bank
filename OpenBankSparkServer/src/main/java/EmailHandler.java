/**
 * Created by kimberly_93pc on 5/6/17.
 */
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailHandler {
    public EmailHandler(){}
    //For authentication of Open Bank, email and password.
    private class SMTPAuthenticator extends Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication("openbankservice@gmail.com", "SWE443OpenBank");
        }
    }

    //This creates and sends the email, this use javaMail with gmail server.
    //Parameters: Text (the message to be sent), subject, recipient & receiver.
    public String createEmail(String text,String subject,String from,String to) throws MessagingException{

        Properties properties = System.getProperties();
        properties = new Properties();
        properties.put("mail.smtp.user", "openbankservice@gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.socketFactory.port", "25");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        SMTPAuthenticator auth = new SMTPAuthenticator();
        Session session = Session.getInstance(properties, auth);
        session.setDebug(false);

        MimeMessage message = new MimeMessage(session);
        message.setText(text);
        message.setSubject(subject);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        Transport transport = session.getTransport("smtps");
        transport.connect("smtp.gmail.com", 465, "openbankservice", "SWE443OpenBank");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        return "Email has been sent";
    }
}
