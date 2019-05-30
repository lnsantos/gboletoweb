package mbean;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import DAO.UsuarioDAO;
import entidade.Retorno;
import entidade.Usuario;

@ManagedBean
public class CadastroMB {
	
	Usuario usuario; // Usuário que vai ser populado
	String senhaSecundaria = "";
	
	UsuarioDAO uDAO;
	
	public CadastroMB() {
		usuario = new Usuario();
		uDAO = new UsuarioDAO();
	}
	
	public String cadastrarUsuario() {
		if(verificaIntegridadeSenha()) {
			if(verificaIgualdadeSenha()) {
				if(verificaEmailVerdadeiro()) {
					
					if(uDAO.cadastrarUsuario(usuario).getRetorno()) 
					return "index?facesredirect=true";
					else erroCadastrarUsuario();
				}else erroEmailVerdadeiro();
			}else erroIgualdadeSenha();
		}else erroIntegridadeSenha();
		return "";
	}
	
	private boolean verificaEmailVerdadeiro() {
		String email = usuario.getEmail();
		
		Boolean encontrado = false;
		
		for(int i=0;i < email.length();i++){
			char c = email.charAt(i);
			if(c == '@') encontrado = true;
			else return encontrado;
		}
		
		String DOMAIN = email.substring(email.indexOf("@"));	
		Set<String> domains = new HashSet<String>();
		
		domains.add("@faj.br");
		/* domains.add("@gmail.com");
		domains.add("@gmail.com.br");
		domains.add("@hotmail.com");
		domains.add("@hotmail.com.br"); */
		
		for(Iterator<String> domain = domains.iterator(); domain.hasNext();) {
			String value = domain.next();
			if(DOMAIN.equals(value)) {
				return true;
			}
		}
		// erroEmailVerdadeiro();
		return false;
	}
	
	private boolean verificaIgualdadeSenha() {
		if(usuario.getSenha().equals(senhaSecundaria)) return true;
		else return false;
	}
	
	private boolean verificaIntegridadeSenha() {
		if(usuario.getSenha().length() > 6) return true;
		else return false;
	}
	
	private void erroCadastrarUsuario() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Usuario ou Email já existe"));
		usuario.setSenha("");
		usuario.setUsuario("");
		usuario.setEmail("");
		senhaSecundaria = "";

	}
	private void erroEmailVerdadeiro() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Porfavor Insira um Email, caso tenha inserido um email verifique o dominio registrado!"));
		senhaSecundaria = "";
		usuario.setEmail("");
	}
	private void erroIgualdadeSenha() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Senhas precisam ser iguais"));
		senhaSecundaria = "";
		usuario.setSenha("");
	}
	private void erroIntegridadeSenha() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Senha precisa ter mais que 8 Caracter"));
		senhaSecundaria = "";
		usuario.setSenha("");
	}
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenhaSecundaria() {
		return senhaSecundaria;
	}

	public void setSenhaSecundaria(String senhaSecundaria) {
		this.senhaSecundaria = senhaSecundaria;
	}
	
}
