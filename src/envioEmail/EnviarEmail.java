package envioEmail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class EnviarEmail {
	
	public static void main(String[] args) throws EmailException {
		
	
	EmailAttachment attachment = new EmailAttachment();
	attachment.setPath("icons/add.png"); //caminho da imagem
	attachment.setDisposition(EmailAttachment.ATTACHMENT);
	attachment.setDescription("Picture of John");
	attachment.setName("John");

	// Cria a mensagem de e-mail.
	MultiPartEmail email = new MultiPartEmail();
	
	email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
    email.setSmtpPort(465);
	email.setAuthenticator(new DefaultAuthenticator("vidracariacg2021@gmail.com", "Compel@99"));
	email.setSSLOnConnect(true);
	email.addTo("wandeilsongomes@gmail.com", "John Doe"); //destinatario
	email.setFrom("vidracariacg2021@gmail.com", "Me"); //remetente
	email.setSubject("Mensagem de teste com anexo"); //Assunto
	email.setMsg("Aqui está a Imagem anexada ao e-mail"); //conteudo do e-mail
	email.attach(attachment); // adiciona o anexo à mensagem
	email.send();// envia o e-mail
	
	}
}
