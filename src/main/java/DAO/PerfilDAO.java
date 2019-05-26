package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.ConDB;

public class PerfilDAO {
	
	Connection con;
	
	public PerfilDAO() {
		con = ConDB.getConnection();
	}
	
	public Boolean novaSenhaUsuario(Integer codigo,String novaSenha) {
		if(preparaSql(updateUsuarioSQL("senha = "+novaSenha ,codigo.toString()))) {
			return true;
		}
		return false;
	}
	
	public Boolean novoNomeCompleto(String nome, String sobrenome, Integer codigo) {
		if(preparaSql(updateUsuarioSQL("nome = "+nome+ " AND sobrenome = "+ sobrenome ,codigo.toString()))) {
			return true;
		}
		return false;
	}
	
	public Boolean novoEmail() {
		return false;
	}
	public Boolean novoNomeSobrenome() {
		return false;
	}
	
	
	private String updateUsuarioSQL(String query,String where) {
		String SQL = "UPDATE usuario SET " + query +" WHERE codigo = "+ where;
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
