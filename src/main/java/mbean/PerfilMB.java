package mbean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.jws.soap.InitParam;


import DAO.PerfilDAO;
import entidade.Usuario;

@ManagedBean(name="perfil")
@ViewScoped
public class PerfilMB {
	
	PerfilDAO pDao;
	
	String senhaAtual;
	String senhaPrimaria;
	String senhaSecundaria;
	
	
	// @ManagedProperty(value = "#{loginMBean.usuarioLogado.codigo}")
	Usuario usuarioLogado;
	
	public PerfilMB() {
		pDao = new PerfilDAO();
	}
	
	@PostConstruct
	public void postPerfilMB() {
		// 
		//usuarioLogado.setCodigo("#{loginMBean.usuarioLogado.codigo}");
	}
	
	public void verificaSenhaParaEdita() {
		if(senhaAtual.equals(usuarioLogado.getSenha())) {
			if(senhaPrimaria.equals(senhaSecundaria)) {
				if(novaSenha()) passEditSucess();
				else problemaSQLPassword();
			}else passwordNotEquals();
		}else passwordInvalid();
	}
	
	private void passEditSucess() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Senha Alterada com sucesso"));
		usuarioLogado.setSenha(senhaPrimaria);
		// LoginMBean dx = LoginMBean.this;
		limpaTodosCampos();
	}
	
	private void passwordNotEquals() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Senhas não são iguais!"));
		senhaPrimaria = "";
		senhaSecundaria = "";
	}
	private void passwordInvalid() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Senha Atual inválida!"));
		limpaTodosCampos();
	}
	private void problemaSQLPassword() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Houve um problema ao modifica a senha, tente mais tarde"));
		senhaPrimaria = "";
		senhaSecundaria = "";
	}
	private void limpaTodosCampos() {
		senhaAtual = "";
		senhaPrimaria = "";
		senhaSecundaria = "";
	}
	private boolean novaSenha() {
		if(pDao.novaSenhaUsuario(usuarioLogado.getCodigo(), senhaPrimaria)) return true;
		else return false;
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getSenhaPrimaria() {
		return senhaPrimaria;
	}

	public void setSenhaPrimaria(String senhaPrimaria) {
		this.senhaPrimaria = senhaPrimaria;
	}

	public String getSenhaSecundaria() {
		return senhaSecundaria;
	}

	public void setSenhaSecundaria(String senhaSecundaria) {
		this.senhaSecundaria = senhaSecundaria;
	}

	public Usuario getCodigoUsuarioLogado() {
		return usuarioLogado;
	}

	public void setCodigoUsuarioLogado(Usuario codigoUsuarioLogado) {
		this.usuarioLogado = codigoUsuarioLogado;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}


	
}
