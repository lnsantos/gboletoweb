package email;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public class EmailCommons {
	
	private static final String HOSTNAME = "smtp.gmail.com";
	private static final String USERNAME = "spam.nepo2018@gmail.com";
	private static final String PASSWORD = "senai123";
	private static final String EMAILORIGEM = "spam.nepo2018@gmail.com";
	
	public Email conectaEmail() throws EmailException{
		   Email email = new SimpleEmail();
		   email.setHostName(HOSTNAME);
		   email.setSmtpPort(587);
		   email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
		   email.setStartTLSEnabled(true);
		   email.setFrom(EMAILORIGEM);
		   
		   return email;
	}
	
	public void enviaEmail() throws EmailException{
		Email email = conectaEmail();
		email.setSubject("BOLETOS A SEREM PAGOS!");
		email.setContent("<table cellpadding='0' cellspacing='0' border='0' width='100%' align='center'>" + 
				"    <tr style='background-color: black; height: 50px;'>" + 
				"        <td align='center' valign='top' style='font-weight: bold;font-style: normal;font-size: 30px;color:red;'>ALERTA AUTOMÁTICO!</td>" + 
				"    </tr>" + 
				"    <tr style='background-color:darkgray'>" + 
				"            <td align='center' valign='top' style='font-weight: bold;font-style: normal;font-size: 15px;color:black;'>" + 
				"                Alguns boletos estão atrasados!" + 
				"                <ul>" + 
				"                    <li> Panelas de pressão</li>" + 
				"                    <li> Matagaus de ferro</li>" + 
				"                    <li> Pilhas de molho</li>" + 
				"                    <li> Show Recommendations</li>" + 
				"                    <li> Professores</li>" + 
				"                </ul>" + 
				"            </td>" + 
				"    </tr>" + 
				"    <tr style='background-color: black; height: 50px;'>" + 
				"            <td align='center' valign='top' style='font-weight: bold;font-style: normal;font-size: 30px;color:red;'>TOTAL R$1500,00</td>" + 
				"        </tr>" + 
				"</table>","text/html");
		email.addTo("lucas.nepomuceno1999@gmail.com");
		email.send();
		
		System.out.println("Enviou");
	}
	
	public void emailCommons() {

		// conta do gmail a ser utilizada para manda email
		final String USUARIO = "hashepo@gmail.com";
		final String SENHA = "01616221";

		// configurando todo gmail
		Properties p = new Properties();
		p.put("mail.transport.protocol", "smtp");
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.host", "smtp.gmail.com");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.user", USUARIO);
		p.put("mail.debug", "true");
		p.put("mail.smtp.port", 587);
		p.put("mail.smtp.socketFactory.port", 587);
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");

		// autenticando conta gmail
		Session s = Session.getInstance(p, new Authenticator() {

			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USUARIO, SENHA);
			}
		});

		s.setDebug(true);

		Message m = new MimeMessage(s);

		// quem vai manda ?
		try {
			m.setFrom(new InternetAddress(USUARIO, "Administrador"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// quem vai recebe?

		try {
			m.setRecipients(Message.RecipientType.TO, InternetAddress.parse("lucas.nepomuceno1999@gmail.com"));
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// mensagem do email
		MimeBodyPart mBP = new MimeBodyPart();
		try {
			mBP.setText(
					"<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" width=\"100%\" align=\"center\"> <tr><td align=\"center\" valign=\"top\"> Título com call-to-action do email marketing!</td></tr></table>");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// resto do email/partes
		Multipart mp = new MimeMultipart();
		try {
			mp.addBodyPart(mBP);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// assunto do email
		try {
			m.setSubject("ENVIANDO EMAIL");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// conteúdo do email
		try {
			m.setContent(mp);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// confirmar/enviar email
		try {
			Transport.send(m);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void enviaEmailFormatoHtml() throws EmailException, MalformedURLException {
		HtmlEmail email = new HtmlEmail();
		// adiciona uma imagem ao corpo da mensagem e retorna seu id
		URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		String cid = email.embed(url, "Apache logo");	
		// configura a mensagem para o formato HTML
		email.setHtmlMsg(" Logo do Apache - ");
		// configure uma mensagem alternativa caso o servidor não suporte HTML
		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");
		email.setHostName("smtp.googlemail.com"); // o servidor SMTP para envio do e-mail
		email.addTo("lucas.nepomuceno1999@gmail.com" , "destinatario"); //destinatário
		email.setFrom("hashepo@gmail.com", "Remetente"); // remetente
		email.setSubject("Teste de email em HTML"); // assunto do e-mail
		email.setMsg("Teste de Envio de Email no formato HTML, caso tenha recebido este email entrar em contato no tel (xx) xxxx-xxxx"); //conteudo do e-mail
		email.setAuthentication("hashepo@gmail.com","01616221");
		email.setSmtpPort(465);
		//email.setAuthenticator(new DefaultAuthenticator("hashepo@gmail.com","01616221"));
		email.setSSLOnConnect(true);
		// envia email
		email.send();
		
		}


}
