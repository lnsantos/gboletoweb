package mbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class LoginMBean {
	
	private String usuario;
	private String senha;
	private String mensagem;
	private boolean logado = false;
	
	public void errorLogin(){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_ERROR,"Você não está logado!",""));
	}
	
	public String login(){
		if(usuario.equals("lnsatos") && senha.equals("polis2019")){
			logado = true;
			System.out.println();
			return "home?faces-redirect=true";
		}else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR,"Usuário e/ou senha inválidos",""));
			return null;
		}
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}
	
}
