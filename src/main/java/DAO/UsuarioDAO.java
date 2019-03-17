package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import database.ConDB;
import entidade.Retorno;
import entidade.Usuario;

public class UsuarioDAO {
	private Connection con;
	
	public UsuarioDAO() {
		con = ConDB.getConnection();
	}
	
	// *********** buscaUsuarioRetornaID ********************************************
	// *********** Esse m�todo � o tipo retorno, onde retorna o ID do usuario, ******
	// *********** e um true, para simboliza que deu tudo certo *********************
	public Retorno buscaUsuarioRetornaID(Usuario u, String SQL) throws SQLException {
		String SQL_BUSCA_ID = "SELECT codigo FROM usuario WHERE "
				+ "usuario = " + "'" + u.getUsuario().toString() +"'"  + " AND "
				+ "email = " + "'" + u.getEmail().toString() + "'"+";"; 
		Integer indentificadorEncontrado = 0;
		
		 // Prepara o SQL
		 PreparedStatement ps = con.prepareStatement(SQL_BUSCA_ID);
		 
		 // Retorna as linhas do resultado do SQL_BUSCA_ID
		 ResultSet rs = ps.executeQuery(); 
		 
		 // em quanto tiver tinhas no banco de dados, ele vai executar o while 
		 while(rs.next()) {
			  // Retorna o codigo do usuario 
			   indentificadorEncontrado = rs.getInt("codigo");
		 }	 
		 System.out.println("ID ENCONTRADO : " + indentificadorEncontrado);
		 
		 // Insere o ID do usuario no codigo, criando uma linha na tabela permissao
		 ps = con.prepareStatement(SQL+indentificadorEncontrado+")");
		 
		 // Verifica se a inser��o deu certo
		 if(ps.executeUpdate() > 0) {
			 System.out.println("Permiss�es criadas com sucesso!");
			 Retorno r = new Retorno("Permiss�es criadas com sucesso!", true);
			 return r;
		 }else {
			 System.out.println("Permiss�es n�o foi criada!");
			 Retorno r = new Retorno("Permiss�es n�o foi criada!", false);
			 return r;
		 }
	}
	
	// ******************************************************************************
	// ******************************************************************************
	// *********** cadastrarUsuario *************************************************
	// *********** Esse m�todo � o tipo retorno, onde retorna o mensagem do ocorrido*
	// *********** e um boolean *****************************************************
	// ******************************************************************************
	public Retorno cadastrarUsuario(Usuario u)  {
		Retorno resul = new Retorno("",false);
		Retorno r = new Retorno();
		// prepara o SQL
		PreparedStatement ps;
					
					// ***************************************************************
					// *Codigo SQL a ser executado ***********************************
					// ***************************************************************
					String SQL_GERA_PERMISSAO = "INSERT INTO permissao(codigo) VALUE (";		
					System.out.println(SQL_GERA_PERMISSAO);
					String SQL = "INSERT INTO usuario(usuario,nome,sobrenome,email,senha) VALUE("
							+ "'" + u.getUsuario() + "'"+ ","
							+ "'" + u.getNome() + "'" + ","
							+ "'" + u.getSobrenome() + "'" + ","
							+ "'" + u.getEmail() + "'" + ","
							+ "'" + u.getSenha() + "'" +")";
					System.out.println(SQL);
					 try {
						 // executa o sql de cria��o de usuario
						ps = con.prepareStatement(SQL);
						
						// verifica se a inser��o foi bem Sucedida 
						if(ps.executeUpdate() > 0) {
								System.out.println("usuario cadastrado com sucesso!");
								try {
									
									// buscaUsuarioRetornaID, retorna ID do usuario criado
									r = buscaUsuarioRetornaID(u,SQL_GERA_PERMISSAO);
									System.out.println(r.getMensagem());
									resul.setRetorno(r.getRetorno());
									resul.setMensagem("Usuario inserido com sucesso! ");
								
								// case de problema com o buscaUsuarioRetornaID
								}catch (SQLException e) {
									e.printStackTrace();
									resul.setMensagem("Problema com " + r.getMensagem());
									System.out.println(resul.getMensagem());
									return resul;
								}
								
								// Retorno do TRY
								// Retorno do TRY
							 	return resul;
							 	
							// Se usuario j� existir no banco ou inser��o mau sucedida 
							}else {
								System.out.println("Email/usuario j� existe no sistema!");
								resul.setMensagem("Email/usuario j� existe no sistema!");
								return resul;
							}
					// N�o conseguiu executar SQL, problema com a conex�o ou dados inserido incorretamente!
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println();
						resul.setMensagem("Problema com " + SQL);
						System.out.println(resul.getMensagem());
						return resul;
					}	
			} // Fecha metodo
	
		// ******************************************************************************
		// ******************************************************************************
		// *********** cadastrarUsuario *************************************************
		public Retorno loginUsuario(String usuario, String senha) {
			Retorno resultado = new Retorno("Usuario n�o encontrado",false);
			
			// Informa��es do usuario a ser retornado
			Usuario u = new Usuario();
			
			// String SQL a ser executada
			String SQL_BUSCA = "SELECT * FROM usuario WHERE usuario = " + "'" + usuario + "'" 
												+ "AND" + " senha = "   + "'" + senha   + "'";
			
			// Prepara o SQL
			PreparedStatement ps;
			
			try {
				ps = con.prepareStatement(SQL_BUSCA);
				
				//Resultado da busca
				ResultSet rs = ps.executeQuery();
				
				// em quanto tiver tinhas no banco de dados, ele vai executar o while 
				while(rs.next()) {
					u.setCodigo(rs.getInt("codigo"));
					u.setNome(rs.getString("nome"));
					u.setSobrenome(rs.getString("sobrenome"));
					u.setUsuario(rs.getString("usuario"));
					u.setEmail(rs.getString("email"));
					
					// Retorno final
					resultado.setUser(u);
					resultado.setRetorno(true);
					resultado.setMensagem("Bem vindo " + u.getNome() + " " + u.getSobrenome());
					
					return resultado;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultado;
		}
}
