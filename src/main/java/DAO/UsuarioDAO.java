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
	
	public List<Usuario> user_not_repiter() throws SQLException{
		List<Usuario> usuariosEncontrado = new ArrayList<Usuario>();
		String SQL = "SELECT usuario as u, email as e FROM usuario";
		int numeroRegistros = 0;

			PreparedStatement ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				numeroRegistros++;
				
				Usuario u = new Usuario();
				u.setEmail(rs.getString("e"));
				u.setUsuario(rs.getString("u"));
				usuariosEncontrado.add(u);
			}
			System.out.println(numeroRegistros + " ENCONTRADO! [ user_not_repiter ]");
			return usuariosEncontrado;
	}
	
	public boolean cadastrarUsuario(Usuario u) throws SQLException {
		PreparedStatement ps;
		List<Usuario> usuariosRetornado = user_not_repiter();
		if(usuariosRetornado != null) {
			for(Usuario uCompara : usuariosRetornado) {
				if(uCompara.getEmail().equals(u.getEmail()) && uCompara.getUsuario().equals(u.getUsuario())) {
					System.out.println("Usuario/Email já existe no sistema, verifique!");
					return false;
				}else {
					String SQL_GERA_PERMISSAO = "INSERT INTO permissao(codigo) VALUE codigo = ";
					String SQL_BUSCA_ID = "SELECT codigo as c FROM usuario WHERE "
							+ "usuario = " + u.getUsuario() + " AND "
							+ "nome = " + u.getNome() + " AND "
							+ "sobrenome = " + u.getSobrenome() + " AND "
							+ "email = " + u.getEmail() + " AND "
							+ "senha = " + u.getSenha();
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
						 ps = con.prepareStatement(SQL_BUSCA_ID);
						 ResultSet rs = ps.executeQuery();
						 String indentificadorEncontrado = rs.getString("c");
						 ps = con.prepareStatement(SQL_GERA_PERMISSAO + indentificadorEncontrado);
						 return ps.executeUpdate() > 0;
						}else {
							System.out.println("Problema ao inserir usuario");
						}
				} // FECHA ELSE
			} // Fecha FOR
		}else {
			ps = con.prepareStatement("INSERT INTO usuario(usuario,nome,sobrenome,email,senha) values"
					+ "('Adm','Adm','Sistema','ti.suporte@gmail.com','admadm')");
			return ps.executeUpdate() > 0;
		}
		return false;
	}
}
