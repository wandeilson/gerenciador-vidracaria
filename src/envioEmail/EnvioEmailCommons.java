package envioEmail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class EnvioEmailCommons {
	
	public static void main(String[] args) throws EmailException {
		
		 // Create the attachment
		  EmailAttachment attachment = new EmailAttachment();
		  attachment.setPath("icons/add.png");
		  attachment.setDisposition(EmailAttachment.ATTACHMENT);
		  attachment.setDescription("Picture of John");
		  attachment.setName("John");

		  // Create the email message
		  MultiPartEmail email = new MultiPartEmail();
		  email.setHostName("mail.myserver.com");
		  email.setAuthenticator(new DefaultAuthenticator("vidracariacg2021@gmail.com", "Compel@99"));
		  email.addTo("wandeilsongomes@gmail.com", "John Doe");
		  email.setFrom("vidracariacg2021@gmail.com", "Me");
		  email.setSubject("The picture");
		  email.setMsg("Here is the picture you wanted");
		  
		  // add the attachment
		  email.attach(attachment);

		  // send the email
		  email.send();
		
	}
	
}
