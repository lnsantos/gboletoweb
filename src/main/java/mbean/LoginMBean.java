package mbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.mysql.cj.PerConnectionLRUFactory;

import DAO.UsuarioDAO;
import entidade.Permissao;
import entidade.Retorno;
import entidade.Usuario;

@ManagedBean
@SessionScoped
public class LoginMBean {
	
	private String usuario;
	private String senha;
	private String mensagem;
	
	Usuario usuarioLogado = new Usuario(); 
	UsuarioDAO uDao = new UsuarioDAO();
	Retorno resultado = new Retorno();
	Permissao per = new Permissao();
	
	private boolean logado = false;
	
	public void errorLogin(){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_ERROR,"Você não está logado!",""));
	}
	
	public String login(){
		resultado = uDao.loginUsuario(usuario, senha);
		usuarioLogado = resultado.getUser();
		per = resultado.getPer();
		
		if(resultado.getRetorno()){
			logado = true;
			System.out.println(usuarioLogado.getNome() + "Logado no sistema!");
			return "home?faces-redirect=true";
		}else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR,resultado.getMensagem(),""));
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
