package DAO;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.ConDB;
import entidade.Usuario;
import util.StringMD5;

public class PerfilDAO {
	
	Connection con;
	private StringMD5 md5;
	public PerfilDAO() {
		con = ConDB.getConnection();
	}
	
	public Boolean novaSenhaUsuario(Integer codigo,String novaSenha) {
		if(preparaSql(updateUsuarioSQL("senha = '"+md5(novaSenha)+"'" ,codigo))) {
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
	
}
