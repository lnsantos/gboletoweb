package mbean;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;

import DAO.UsuarioDAO;
import entidade.Usuario;

@ManagedBean
public class CadastroMB {
	
	Usuario usuario; // Usu�rio que vai ser populado
	UsuarioDAO uDAO;
	
	public CadastroMB() {
		usuario = new Usuario();
		uDAO = new UsuarioDAO();
	}
	
	public String cadastrarUsuario() {
		try {
			if(uDAO.cadastrarUsuario(usuario)) {
				return "index?facesredirect=true";
			}else {
				System.out.println("Usuario j� existe no sistema");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Algum problema que n�o sei qual �");
		}
		return "";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
