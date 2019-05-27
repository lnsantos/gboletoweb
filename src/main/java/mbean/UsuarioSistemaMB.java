package mbean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import DAO.BoletoDAO;
import DAO.UsuarioDAO;
import entidade.Boleto;
import entidade.Permissao;

import entidade.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioSistemaMB {
	UsuarioDAO uDao;
	BoletoDAO dDao;
	
	List<Usuario> usuariosLista;
	List<Usuario> filtroUsuarios;
	
	List<Boleto> boletosUsuarioSelecionado; 
	List<Boleto> filtroBoletosUsuarioSelecionado; 
	
	Usuario usuarioSelecionado;
	Boleto boletoUsuarioSelecionado;
	
	@ManagedProperty(value = "#{LoginMBean.usuarioLogado}")
	Usuario usuarioLogadoSistema;

	@ManagedProperty(value = "#{LoginMBean.per}")
	Permissao usuarioLogadoPermissao;

	@PostConstruct
	private void init() {
		uDao = new UsuarioDAO();
		dDao = new BoletoDAO();
		usuariosLista = uDao.listaUsuarios();

	}

	public UsuarioSistemaMB() {
		usuarioSelecionado = new Usuario();
		boletoUsuarioSelecionado = new Boleto();
		boletosUsuarioSelecionado = new ArrayList<Boleto>();
	}

	public void mudaStatuUsuario() {
		if (uDao.mudaStatus(usuarioSelecionado)) {
			usuariosLista = uDao.listaUsuarios();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Alteração realizada com sucesso!"));
			if (usuarioLogadoSistema != null) {
				System.out.println(usuarioLogadoSistema.getUsuario());
			} else {
				System.out.println("Usuário null");
			}
		}
	}
	
	public void removeUsuario() {
		if(uDao.excluirUsuario(usuarioSelecionado.getCodigo())) {
			FacesContext.getCurrentInstance().addMessage(null,  
					new FacesMessage(FacesMessage.SEVERITY_INFO,"Usuário " + usuarioSelecionado.getNome() + "Foi excluido com sucesso!",""));
			usuariosLista = uDao.listaUsuarios();
			System.out.println("Usuário " + usuarioSelecionado.getUsuario()+ " Excluido com sucesso!");
		}
	}
	
	public void boletosUsuarioSelecionadoDialog() {
		boletosUsuarioSelecionado = dDao.listaBoletosUsuarioLogado(usuarioSelecionado.getCodigo());
	}
	
	public void resetaSenha() {
		if (usuarioSelecionado != null) {
			if (uDao.novaSenha(usuarioSelecionado)) {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_INFO,"Senha resetada para o padrão",""));
			}else {
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR,"Problema ao resetar a senha",""));
			}
		}else {
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Selecione um usuario para resetar a senha",""));
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

	public List<Boleto> getBoletosUsuarioSelecionado() {
		return boletosUsuarioSelecionado;
	}

	public void setBoletosUsuarioSelecionado(List<Boleto> boletosUsuarioSelecionado) {
		this.boletosUsuarioSelecionado = boletosUsuarioSelecionado;
	}

	public Boleto getBoletoUsuarioSelecionado() {
		return boletoUsuarioSelecionado;
	}

	public void setBoletoUsuarioSelecionado(Boleto boletoUsuarioSelecionado) {
		this.boletoUsuarioSelecionado = boletoUsuarioSelecionado;
	}

	public List<Boleto> getFiltroBoletosUsuarioSelecionado() {
		return filtroBoletosUsuarioSelecionado;
	}

	public void setFiltroBoletosUsuarioSelecionado(List<Boleto> filtroBoletosUsuarioSelecionado) {
		this.filtroBoletosUsuarioSelecionado = filtroBoletosUsuarioSelecionado;
	}
	
	
}
