package mbean;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import DAO.UsuarioDAO;
import entidade.Permissao;
import entidade.Retorno;
import entidade.Usuario;
import util.StringMD5;

@ManagedBean(eager=false)
@SessionScoped
public class LoginMBean {
	
	private String usuario;
	private String senha;
	private String mensagem;
	
	private StringMD5 smd5;
	
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
		smd5 = null ;
	}
	
	public void errorLogin(){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new 
				FacesMessage(FacesMessage.SEVERITY_ERROR,"Voc� n�o est� logado!",""));
	}
	
	
	public String md5(String password) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(password.getBytes(),0,password.length());
			return new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String login(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().
				 getSession(true);

		resultado = uDao.loginUsuario(usuario,md5(senha) );
		usuarioLogado = resultado.getUser();
		per = resultado.getPer();
		
		if(resultado.getRetorno() && per.getCria_usuario() > 0){
			logado = true;
			
			session.setAttribute("administradorLogado", true);
			session.setAttribute("usuarioLogado", resultado);
			
			return "/restrito/administrador/index?faces-redirect=true";
		}else if(resultado.getRetorno() && per.getCria_usuario() < 1) {
			logado = true;
			
			session.setAttribute("usuarioNormalLogado", true);
			session.setAttribute("usuarioLogado", resultado);
			
			return "/restrito/usuario/index?faces-redirect=true";
		}else {
			FacesContext context = FacesContext.getCurrentInstance();
			logado = false;
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
