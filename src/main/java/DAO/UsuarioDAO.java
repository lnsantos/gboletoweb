package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import database.ConDB;
import entidade.Usuario;

public class UsuarioDAO {
	private Connection con;
	
	public UsuarioDAO() {
		con = ConDB.getConnection();
	}
	
	
	
	public boolean cadastrarUsuario(Usuario u) throws SQLException {
		PreparedStatement ps;
					String SQL_GERA_PERMISSAO = "INSERT INTO permissao(codigo) VALUE codigo = ";
					String SQL_BUSCA_ID = "SELECT codigo as c FROM usuario WHERE "
							+ "usuario = " + u.getUsuario().toString() + " AND "
							+ "email = " + u.getEmail().toString()+";"; 
					
					System.out.println(SQL_GERA_PERMISSAO);
					System.out.println(SQL_BUSCA_ID);
					String SQL = "INSERT INTO usuario(usuario,nome,sobrenome,email,senha) VALUE("
							+ "'" + u.getUsuario() + "'"+ ","
							+ "'" + u.getNome() + "'" + ","
							+ "'" + u.getSobrenome() + "'" + ","
							+ "'" + u.getEmail() + "'" + ","
							+ "'" + u.getSenha() + "'" +")";
					System.out.println(SQL);
					 ps = con.prepareStatement(SQL);
					if(ps.executeUpdate() > 0) {
						 PreparedStatement ps1 = con.prepareStatement(SQL_BUSCA_ID);
						 ResultSet rs = ps1.executeQuery();
						 String indentificadorEncontrado = rs.getString("c");
						 ps = con.prepareStatement(SQL_GERA_PERMISSAO + "'" +indentificadorEncontrado + "'");
						 return ps.executeUpdate() > 0;
						}else {
							System.out.println("Email/usuario já existe no sistema!");
							return false;
						}
			} // Fecha metodo

		
}
