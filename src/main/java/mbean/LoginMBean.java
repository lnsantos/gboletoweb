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

@ManagedBean(eager=true)
@SessionScoped
public class LoginMBean {
	
	private String usuario;
	private String senha;
	private String mensagem;
	
	Usuario usuarioLogado; 
	UsuarioDAO uDao;
	Retorno resultado; // Usuario esta aqui dentro
	Permissao per;
	
	private boolean logado = false;
	
	public LoginMBean() {
		usuarioLogado = new Usuario();
		uDao = new UsuarioDAO();
		resultado = new Retorno(); // Usuario esta aqui dentro
		per = new Permissao();
	}
	
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
			System.out.println( "[ "+usuarioLogado.getCodigo() + " ] " + usuarioLogado.getNome() + "Logado no sistema!");
			return "/restrito/administrador/index?faces-redirect=true";
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

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public Retorno getResultado() {
		return resultado;
	}

	public void setResultado(Retorno resultado) {
		this.resultado = resultado;
	}
	
}
