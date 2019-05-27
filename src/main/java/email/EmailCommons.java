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

import entidade.Boleto;
import entidade.Mail;
import entidade.Usuario;

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
	public void enviaEmail(Mail u) throws EmailException{
		Email email = conectaEmail();
		email.setSubject("BOLETOS A SEREM PAGOS!");
		String boletos = u.getMsg();
		
		Double valorTotal = (double) 0;
		
		email.setContent("<table cellpadding='0' cellspacing='0' border='0' width='100%' align='center'>" + 
				"    <tr style='background-color: black; height: 50px;'>" + 
				"        <td align='center' valign='top' style='font-weight: bold;font-style: normal;font-size: 30px;color:red;'>ALERTA AUTOMÁTICO!</td>" + 
				"    </tr>" + 
				"    <tr style='background-color:darkgray'>" + 
				"            <td align='center' valign='top' style='font-weight: bold;font-style: normal;font-size: 15px;color:black;'>" + 
				"                Alguns boletos estão atrasados!" + 
				"                <ul>" + 
									boletos + 
				"                </ul>" + 
				"            </td>" + 
				"    </tr>" + 
				"    <tr style='background-color: black; height: 50px;'>" + 
				"            <td align='center' valign='top' style='font-weight: bold;font-style: normal;font-size: 30px;color:red;'>TOTAL "+ valorTotal.toString() +"</td>" + 
				"        </tr>" + 
				"</table>","text/html");
		email.addTo(u.getEmail()); // alterar para u.getEmail();
		email.send();
		}
	public void enviaEmail(Usuario u) throws EmailException{
		Email email = conectaEmail();
		email.setSubject("BOLETOS A SEREM PAGOS!");
		StringBuilder boletos = new StringBuilder();
		
		Double valorTotal = (double) 0;
		
		for (Boleto b : u.getBoletos()) {
			boletos.append("<li>");
			boletos.append(b.getItem());
			boletos.append("</li>");
			
			// valorTotal =+ b.getValor();
		}
		
		email.setContent("<table cellpadding='0' cellspacing='0' border='0' width='100%' align='center'>" + 
				"    <tr style='background-color: black; height: 50px;'>" + 
				"        <td align='center' valign='top' style='font-weight: bold;font-style: normal;font-size: 30px;color:red;'>ALERTA AUTOMÁTICO!</td>" + 
				"    </tr>" + 
				"    <tr style='background-color:darkgray'>" + 
				"            <td align='center' valign='top' style='font-weight: bold;font-style: normal;font-size: 15px;color:black;'>" + 
				"                Alguns boletos estão atrasados!" + 
				"                <ul>" + 
									boletos.toString() + 
				"                </ul>" + 
				"            </td>" + 
				"    </tr>" + 
				"    <tr style='background-color: black; height: 50px;'>" + 
				"            <td align='center' valign='top' style='font-weight: bold;font-style: normal;font-size: 30px;color:red;'>TOTAL "+ valorTotal.toString() +"</td>" + 
				"        </tr>" + 
				"</table>","text/html");
		email.addTo(u.getEmail()); // alterar para u.getEmail();
		email.send();
		
		System.out.println("Enviou Email para : " + u.getNome() + " " + u.getSobrenome());
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
		email.setAuthentication("spam.nepo2018@gmail.com","senai123");
		email.setSmtpPort(465);
		//email.setAuthenticator(new DefaultAuthenticator("hashepo@gmail.com","01616221"));
		email.setSSLOnConnect(true);
		// envia email
		email.send();
		
		}
}
