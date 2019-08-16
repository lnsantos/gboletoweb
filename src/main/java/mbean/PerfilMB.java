package mbean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;



import DAO.PerfilDAO;
import entidade.Usuario;

@ManagedBean(name="perfil")
@ViewScoped
public class PerfilMB {
	Usuario usuarioLogado;
	PerfilDAO pDao;
	
	// TROCA SENHA
	String senhaAtual;
	String senhaPrimaria;
	String senhaSecundaria;
	
	Boolean fieldset_email_codigo = false;
	
	// TROCA EMAIL
	String novoEmail;
	String verificaNovoEmail;
	String codigoConfirmaEmailString;
	
	// TROCA NOME COMPLETO
	String nomeUsuario = "";
	String sobrenomeUsuario = "";
	
	// @ManagedProperty(value = "#{loginMBean.usuarioLogado.codigo}")
	
	
	public PerfilMB() {
		pDao = new PerfilDAO();
		//nomeUsuario = usuarioLogado.getNome();
		//sobrenomeUsuario = usuarioLogado.getSobrenome();
	}
	
	@PostConstruct
	public void postPerfilMB() {
		if(usuarioLogado != null) {
			nomeUsuario = usuarioLogado.getNome();
			sobrenomeUsuario = usuarioLogado.getSobrenome();
		}
		//usuarioLogado.setCodigo("#{loginMBean.usuarioLogado.codigo}");
	}
	
	public void efeturarTrocaNomeCompleto() {
		if(verificaInputAlterados()) {
			if(pDao.novoNomeCompleto(usuarioLogado)) sucessoNomeCompleto();
			else erroNomeCompleto();
		}else erroPrenchaCampos();
	}
	
	public void efetuarTrocaEmail() {
		novoEmail = "lc";
		verificaNovoEmail= "lc";
		codigoConfirmaEmailString= "lc";
		System.out.println("clicou");
		fieldset_email_codigo = !fieldset_email_codigo;
	}
	
	public void verificaSenhaParaEdita() {
		if(senhaAtual.equals(usuarioLogado.getSenha())) {
			if(senhaPrimaria.equals(senhaSecundaria)) {
				if(novaSenha()) passEditSucess();
				else problemaSQLPassword();
			}else passwordNotEquals();
		}else passwordInvalid();
	}
	
	private boolean verificaInputAlterados() {
		if(nomeUsuario.equals("") && sobrenomeUsuario.equals("")) return false;
		else {
			if(nomeUsuario.equals("")) {} else usuarioLogado.setNome(nomeUsuario);
			if(sobrenomeUsuario.equals("")) {} else usuarioLogado.setSobrenome(sobrenomeUsuario);
			return true;
		}
	}
	
	private void sucessoNomeCompleto() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Nome Alterado com sucesso!"));
		nomeUsuario = "";
		sobrenomeUsuario = "";
	}
	private void erroNomeCompleto() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Hove um problema ao confirmar alteração do nome, tente mais tarde!"));
	}
	private void erroPrenchaCampos() {
		FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Preencha os campos que deseja alterar"));
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

	public Boolean getFieldset_email_codigo() {
		return fieldset_email_codigo;
	}

	public void setFieldset_email_codigo(Boolean fieldset_email_codigo) {
		this.fieldset_email_codigo = fieldset_email_codigo;
	}

	public String getNovoEmail() {
		return novoEmail;
	}

	public void setNovoEmail(String novoEmail) {
		this.novoEmail = novoEmail;
	}

	public String getVerificaNovoEmail() {
		return verificaNovoEmail;
	}

	public void setVerificaNovoEmail(String verificaNovoEmail) {
		this.verificaNovoEmail = verificaNovoEmail;
	}

	public String getCodigoConfirmaEmailString() {
		return codigoConfirmaEmailString;
	}

	public void setCodigoConfirmaEmailString(String codigoConfirmaEmailString) {
		this.codigoConfirmaEmailString = codigoConfirmaEmailString;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSobrenomeUsuario() {
		return sobrenomeUsuario;
	}

	public void setSobrenomeUsuario(String sobrenomeUsuario) {
		this.sobrenomeUsuario = sobrenomeUsuario;
	}
	
}
