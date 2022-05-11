package envioEmail;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

//import Java.io.File;    
//import Java.io.IOException;    
//import Java.util.Properties;   
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailApp {
    public static void main(String[] args)throws IOException {
        final String username = "vidracariacg2021@gmail.com";
        final String password = "Compel@99";

        Properties props = new Properties();
        
//        props.put("mail.smtp.user", username);
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.port", "25");
//		props.put("mail.debug", "true");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.EnableSSL.enable", "true");
//		
//		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		props.setProperty("mail.smtp.socketFactory.fallback", "false");
//		props.setProperty("mail.smtp.port", "465");
//		props.setProperty("mail.smtp.socketFactory.port", "465");
        
        
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.Host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(username));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler," + "\n\n No spam to my email, please!");
            message.setSubject("Testing Subject");
            message.setText("PFA");

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart = new MimeBodyPart();

            String attachmentPath = "C:/Users/disso/Pictures/ppp.jpg";
            String attachmentName = "LogResults.txt";

            File att = new File(new File(attachmentPath), attachmentName);
            messageBodyPart.attachFile(att);

            DataSource source = new FileDataSource(att);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(attachmentName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            System.out.println("Sending");
            Transport.send(message);
            Transport.send(message);
            System.out.println("Done");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
