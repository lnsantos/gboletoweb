package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.ConDB;

public class PermissaoDAO {
	Connection con;
	
	public PermissaoDAO() {
		con = ConDB.getConnection();
	}
	
	private String updateUsuarioSQL(String query,Integer where) {
		String SQL = "UPDATE permissao SET " + query +" WHERE codigo = "+ where;
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
