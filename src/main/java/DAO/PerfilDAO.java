package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.ConDB;
import entidade.Usuario;

public class PerfilDAO {
	
	Connection con;
	
	public PerfilDAO() {
		con = ConDB.getConnection();
	}
	
	public Boolean novaSenhaUsuario(Integer codigo,String novaSenha) {
		if(preparaSql(updateUsuarioSQL("senha = "+novaSenha ,codigo))) {
			return true;
		}
		return false;
	}
	
	public Boolean novoNomeCompleto(Usuario u) {
		if(preparaSql(updateUsuarioSQL("nome = "+"'"+u.getNome()+"'"+" , sobrenome = "+"'"+ u.getSobrenome()+"'" ,u.getCodigo()))) {
			return true;
		}
		return false;
	}
	
	public Boolean novoEmail() {
		return false;
	}

	private String updateUsuarioSQL(String query,Integer where) {
		String SQL = "UPDATE usuario SET " + query +" WHERE codigo = "+ where;
		System.out.println(SQL);
		return SQL;
	}
	
	private boolean preparaSql(String SQL) {
		try {
			PreparedStatement ps = con.prepareStatement(SQL);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
