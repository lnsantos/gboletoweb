package mbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import DAO.UsuarioDAO;
import entidade.Permissao;

import entidade.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioSistemaMB {
	UsuarioDAO uDao;

	List<Usuario> usuariosLista;
	List<Usuario> filtroUsuarios;

	Usuario usuarioSelecionado;

	@ManagedProperty(value = "#{LoginMBean.usuarioLogado}")
	Usuario usuarioLogadoSistema;

	@ManagedProperty(value = "#{LoginMBean.per}")
	Permissao usuarioLogadoPermissao;

	@PostConstruct
	private void init() {
		uDao = new UsuarioDAO();
		usuariosLista = uDao.listaUsuarios();

	}

	public UsuarioSistemaMB() {
		usuarioSelecionado = new Usuario();
	}

	public void mudaStatuUsuario() {
			if (uDao.mudaStatus(usuarioSelecionado)) {
				usuariosLista = uDao.listaUsuarios();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Alteração realizada com sucesso!"));
				if(usuarioLogadoSistema != null) {
					System.out.println(usuarioLogadoSistema.getUsuario());
				}else {
					System.out.println("Usuário null");
				}
			}
	}

// GET and SET
	public List<Usuario> getUsuariosLista() {
		return usuariosLista;
	}

	public Usuario getUsuarioLogadoSistema() {
		return usuarioLogadoSistema;
	}

	public void setUsuarioLogadoSistema(Usuario usuarioLogadoSistema) {
		this.usuarioLogadoSistema = usuarioLogadoSistema;
	}

	public Permissao getUsuarioLogadoPermissao() {
		return usuarioLogadoPermissao;
	}

	public void setUsuarioLogadoPermissao(Permissao usuarioLogadoPermissao) {
		this.usuarioLogadoPermissao = usuarioLogadoPermissao;
	}

	public void setUsuariosLista(List<Usuario> usuariosLista) {
		this.usuariosLista = usuariosLista;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public Usuario getLogadoSistema() {
		return usuarioLogadoSistema;
	}

	public void setLogadoSistema(Usuario logadoSistema) {
		this.usuarioLogadoSistema = logadoSistema;
	}

	public Permissao getLogadoPermissao() {
		return usuarioLogadoPermissao;
	}

	public void setLogadoPermissao(Permissao logadoPermissao) {
		this.usuarioLogadoPermissao = logadoPermissao;
	}

	public List<Usuario> getFiltroUsuarios() {
		return filtroUsuarios;
	}

	public void setFiltroUsuarios(List<Usuario> filtroUsuarios) {
		this.filtroUsuarios = filtroUsuarios;
	}
}
