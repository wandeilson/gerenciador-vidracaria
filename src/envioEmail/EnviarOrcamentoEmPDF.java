package envioEmail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import cadastro.Cliente;
import cadastro.ClienteDAO;
import cadastro.Orcamento;
import cadastro.OrcamentoDAO;

public class EnviarOrcamentoEmPDF {

	public static void enviarOrcamentoAoCliente(Cliente destinatario, String msg, Orcamento orcamento) {
		Properties props = new Properties();
		String remetente = "vidracariacg2021@gmail.com";
		String senha = "Compel@99";

//		String filename = orcamento.getNomeDoOrcamento() + ".pdf";
		String filename = "C:/Users/disso/Pictures/ppp.jpg";
		props.put("mail.smtp.user", remetente);
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");
		props.put("mail.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.EnableSSL.enable", "true");
		
		props.setProperty("mail.pop3s.ssl.protocols", "​TLSv1.2");
		props.setProperty("mail.smtp.ssl.protocols", "​TLSv1.2​");

		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(remetente, senha);
			}
		});

		session.setDebug(true);
		Message message = null;
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remetente));

			Address[] toUser = InternetAddress.parse(destinatario.getEmail());

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Email de Vidraçaria CG");

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("Segue seu orçamento...");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

		} catch (MessagingException e) {
			System.out.println("Erro ao enviar e-mail.");
		}

		try {
			Transport tr = session.getTransport("smtps");
			tr.connect("smtp.gmail.com", remetente, senha);
			tr.sendMessage(message, message.getAllRecipients());
			System.out.println("Mail Sent Successfully");
			tr.close();

		} catch (Exception sfe) {
			System.out.println(sfe);
		}
		}

	public static void main(String[] args) {
		OrcamentoDAO o = new OrcamentoDAO();
		Orcamento or = null;
		try {
			or = o.recuperarTodosOsOrcamentos().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ClienteDAO c = new ClienteDAO();
		Cliente cliente = null;
		try {
			cliente = c.recuperarTodosOsClientes().get(0);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		EnviarOrcamentoEmPDF.enviarOrcamentoAoCliente(cliente, "s", or);

	}

}
