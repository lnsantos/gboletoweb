package mbean;

import javax.faces.bean.ManagedBean;

import DAO.UsuarioDAO;
import entidade.Usuario;

@ManagedBean
public class CadastroMB {
	
	Usuario usuario; // Usuário que vai ser populado
	UsuarioDAO uDAO;
	
	public CadastroMB() {
		usuario = new Usuario();
		uDAO = new UsuarioDAO();
	}
	
	public String cadastrarUsuario() {
			if(uDAO.cadastrarUsuario(usuario).getRetorno()) {
				return "index?facesredirect=true";
			}else {
				System.out.println("Usuario já existe no sistema");
				return "";
			}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
