package email;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import entidade.Usuario;

public class EmailCommons {
	
	private static final String HOSTNAME = "";
	private static final String USERNAME = "alerta.boletoalert@gmail.com";
	private static final String PASSWORD = "polis2019";
	private static final String EMAILORIGEM = "BOLETO ALERTA <>";
	private String dataHoraAtual() {
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy z");
		Calendar c = new GregorianCalendar();
		formataData.setTimeZone(c.getTimeZone());
		return formataData.format(c.getTime());
	}
	public Email conectaEmail() throws EmailException{
		   Email email = new SimpleEmail();
		   email.setHostName("smtp.gmail.com");
		   email.setSmtpPort(587);
		   email.setAuthenticator(new DefaultAuthenticator("alerta.boletoalert@gmail.com", "polis2019"));
		   email.setStartTLSEnabled(true);
		   email.setFrom("alerta.boletoalert@gmail.com", "ALERTA " + dataHoraAtual());		   
		   return email;
	}
	
	public void enviaEmail(Usuario u) throws EmailException{
		Email email = conectaEmail();
		email.setSubject("BOLETOS A SEREM PAGOS!");
		StringBuilder boletos = new StringBuilder();
		Double valorTotal =  0.0;
		
		for (Boleto b : u.getBoletos()) {
			boletos.append("<li style=' font-size: 16px;padding-top: 16px;padding-left:  16px;padding-right:  16px;padding-bottom:  1px;color: #fff;background-color: #333;border-left: 15px solid red;border-radius: 5px;margin-top: 16px;'>");
			boletos.append(b.getItem());
			boletos.append("<hr style='height: 5px;background-color: #fff;'><p style='font-size: 12px;font-family: 'Roboto', sans-serif;fill: black;'>"+ b.getMensagemEmail() +"( VALOR DO ITEM : "+ b.getValor() +")</p></li>");
			
			valorTotal =+ b.getValor();
		}
		
		email.setContent("<body style=\"width: 99%;background-color: #012840;font-family: 'Anton', sans-serif;\">" + 
				"	<ul style=\"margin: 0 auto;background-color: #fff;border-radius: 5px 5px 0px 0px;padding: 16px;\">" + 
				"		<table>" + 
				"			<tr>" + 
				"				<td width=\"25%\" >" + 
				"			<img src=\"https://media.licdn.com/dms/image/C4E0BAQHPz3e4CY-yCg/company-logo_200_200/0?e=2159024400&v=beta&t=xBnEhPA8evzcLGlZl8h9QJ4szsxRiAI6G5OR3j-_h_Y\"" + 
				"		width=\"50px\" height=\"50px\"/>" + 
				"				</td>" + 
				"				<td width=\"75%\" style=\"font-size: 160%\">" + 
				"					Olá "+u.getNome()+" "+u.getSobrenome()+" , nosso sistema detectou algumas informações sua registrada no sistema!" + 
				"				</td> " + 
				"			</tr>" + 
				"		</table>" + 
				"		<h6>GBOLETOWEB AVISO AUTOMÁTICO! Esta mensagem foi enviada para o e-mail "+u.getEmail()+" e é dirigida a "+u.getUsuario()+". "+"</h6>" + 
				"        "+boletos+"" + 
				"	</ul>" + 
				"	<div style=\"border-top: 2px solid black;border-radius: 0px 0px 5px 5px;background-color: #C0C0C0;padding: 16px;\">Este email não recebe respostas. Está a receber esta mensagem de alerta automatico pelo sistema interno no núcleo de informática da UniFaj. Caso desconheça esse sistema mande um email para ti.santos@faj.br</div>" + 
				"</body>","text/html");
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
