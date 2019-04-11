package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.ConDB;
import entidade.Permissao;
import entidade.Retorno;
import entidade.Usuario;

public class UsuarioDAO {
	private Connection con;

	public UsuarioDAO() {
		con = ConDB.getConnection();
	}

	public List<Usuario> listaUsuarios() {
		if (con != null) {

			List<Usuario> usuarios = new ArrayList<Usuario>();

			String SQL = "SELECT u.*, p.* FROM usuario as u, permissao as p WHERE p.codigo = u.codigo";

			try {
				PreparedStatement ps = con.prepareStatement(SQL);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					Usuario u = new Usuario();
					u.setCodigo(rs.getInt("u.codigo"));
					u.setNome(rs.getString("u.nome"));
					u.setSobrenome(rs.getString("u.sobrenome"));
					u.setUsuario(rs.getString("u.usuario"));
					u.setEmail(rs.getString("u.email"));

					Permissao p = new Permissao();
					p.setCodigo(rs.getInt("p.codigo"));
					p.setCria_boleto(rs.getInt("p.cria_boleto"));
					p.setCria_usuario(rs.getInt("p.cria_usuario"));
					p.setDeleta_boleto(rs.getInt("p.deleta_boleto"));
					p.setDeleta_usuario(rs.getInt("p.deleta_usuario"));
					p.setEdita_boleto(rs.getInt("p.edita_boleto"));
					p.setEdita_usuario(rs.getInt("p.edita_usuario"));
					p.setStatu(rs.getInt("p.statu"));
					
					u.setPermissao(p);
					
					usuarios.add(u);
				}
				
				return usuarios;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public boolean mudaStatus(Usuario u) {
		if(con != null) {
			String SQL;
			if(u.getPermissao().getStatu() != 1) {
				SQL = "UPDATE permissao SET statu = 1 WHERE codigo = " + u.getCodigo();
			}else {
				SQL = "UPDATE permissao SET statu = 0 WHERE codigo = " + u.getCodigo();
			}
			
			try {
				PreparedStatement ps = con.prepareStatement(SQL);
				if(ps.executeUpdate() > 0) {
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public Usuario buscaUsuarioID(int codigo) {
		if (con != null) {
			Usuario u = new Usuario();
			String SQL = "SELECT * FROM usuario WHERE codigo = " + codigo;

			try {
				PreparedStatement ps = con.prepareStatement(SQL);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					u.setCodigo(rs.getInt("codigo"));
					u.setEmail(rs.getString("email"));
					u.setNome(rs.getString("nome"));
					u.setSobrenome(rs.getString("sobrenome"));
					u.setUsuario(rs.getString("usuario"));

					return u;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	// *********** buscaUsuarioRetornaID
	// ********************************************
	// *********** Esse método é o tipo retorno, onde retorna o ID do usuario,
	// ******
	// *********** e um true, para simboliza que deu tudo certo
	// *********************
	public Retorno buscaUsuarioRetornaID(Usuario u, String SQL) throws SQLException {
		if (con != null) {
			String SQL_BUSCA_ID = "SELECT codigo FROM usuario WHERE " + "usuario = " + "'" + u.getUsuario().toString()
					+ "'" + " AND " + "email = " + "'" + u.getEmail().toString() + "'" + ";";
			Integer indentificadorEncontrado = 0;

			// Prepara o SQL
			PreparedStatement ps = con.prepareStatement(SQL_BUSCA_ID);

			// Retorna as linhas do resultado do SQL_BUSCA_ID
			ResultSet rs = ps.executeQuery();

			// em quanto tiver tinhas no banco de dados, ele vai executar o while
			while (rs.next()) {
				// Retorna o codigo do usuario
				indentificadorEncontrado = rs.getInt("codigo");
			}
			System.out.println("ID ENCONTRADO : " + indentificadorEncontrado);

			// Insere o ID do usuario no codigo, criando uma linha na tabela permissao
			ps = con.prepareStatement(SQL + indentificadorEncontrado + ")");

			// Verifica se a inserção deu certo
			if (ps.executeUpdate() > 0) {
				System.out.println("Permissões criadas com sucesso!");
				Retorno r = new Retorno("Permissões criadas com sucesso!", true);
				return r;
			} else {
				System.out.println("Permissões não foi criada!");
				Retorno r = new Retorno("Permissões não foi criada!", false);
				return r;
			}
		}

		return null;
	}

	// ******************************************************************************
	// ******************************************************************************
	// *********** cadastrarUsuario
	// *************************************************
	// *********** Esse método é o tipo retorno, onde retorna o mensagem do
	// ocorrido*
	// *********** e um boolean
	// *****************************************************
	// ******************************************************************************
	public Retorno cadastrarUsuario(Usuario u) {
		if (con != null) {
			Retorno resul = new Retorno("", false);
			Retorno r = new Retorno();
			// prepara o SQL
			PreparedStatement ps;

			// ***************************************************************
			// *Codigo SQL a ser executado ***********************************
			// ***************************************************************
			String SQL_GERA_PERMISSAO = "INSERT INTO permissao(codigo) VALUE (";
			System.out.println(SQL_GERA_PERMISSAO);
			String SQL = "INSERT INTO usuario(usuario,nome,sobrenome,email,senha) VALUE(" + "'" + u.getUsuario() + "'"
					+ "," + "'" + u.getNome() + "'" + "," + "'" + u.getSobrenome() + "'" + "," + "'" + u.getEmail()
					+ "'" + "," + "'" + u.getSenha() + "'" + ")";
			System.out.println(SQL);
			try {
				// executa o sql de criação de usuario
				ps = con.prepareStatement(SQL);

				// verifica se a inserção foi bem Sucedida
				if (ps.executeUpdate() > 0) {
					System.out.println("usuario cadastrado com sucesso!");
					try {

						// buscaUsuarioRetornaID, retorna ID do usuario criado
						r = buscaUsuarioRetornaID(u, SQL_GERA_PERMISSAO);
						System.out.println(r.getMensagem());
						resul.setRetorno(r.getRetorno());
						resul.setMensagem("Usuario inserido com sucesso! ");

						// case de problema com o buscaUsuarioRetornaID
					} catch (SQLException e) {
						e.printStackTrace();
						resul.setMensagem("Problema com " + r.getMensagem());
						System.out.println(resul.getMensagem());
						return resul;
					}

					// Retorno do TRY
					// Retorno do TRY
					return resul;

					// Se usuario já existir no banco ou inserção mau sucedida
				} else {
					System.out.println("Email/usuario já existe no sistema!");
					resul.setMensagem("Email/usuario já existe no sistema!");
					return resul;
				}
				// Não conseguiu executar SQL, problema com a conexão ou dados inserido
				// incorretamente!
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println();
				resul.setMensagem("Problema com " + SQL);
				System.out.println(resul.getMensagem());
				return resul;
			}
		}
		return null;
	} // Fecha metodo

	public Retorno buscaUsuarioLiberado(String codigo) {
		if (con != null) {
			String SQL_BUSCA_PERMISSAO = "SELECT * FROM permissao WHERE codigo =" + codigo;
			Retorno resultado = new Retorno();

			// Retorno PADRÃO
			resultado.setRetorno(false);
			resultado.setPer(null);
			resultado.setMensagem("Problema com a conexão");

			try {
				PreparedStatement ps = con.prepareStatement(SQL_BUSCA_PERMISSAO);
				ResultSet rs = ps.executeQuery();

				if (rs.next()) {
					Permissao per = new Permissao();

					per.setCria_boleto(rs.getInt("cria_boleto"));
					per.setCria_usuario(rs.getInt("cria_usuario"));
					per.setDeleta_boleto(rs.getInt("deleta_boleto"));
					per.setDeleta_usuario(rs.getInt("deleta_usuario"));
					per.setEdita_boleto(rs.getInt("edita_boleto"));
					per.setEdita_usuario(rs.getInt("edita_usuario"));
					per.setStatu(rs.getInt("statu"));

					resultado.setPer(per);

					if (resultado.getPer().getStatu() != 0) {
						resultado.setRetorno(true);
						return resultado;
					} else {
						resultado.setRetorno(false);
						return resultado;
					}
				} else {
					resultado.setMensagem("Nem um resultado encontrado!");
					return resultado;
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return resultado;
			}
		}
		return null;
	}

	// ******************************************************************************
	// ******************************************************************************
	// *********** cadastrarUsuario
	// *************************************************
	public Retorno loginUsuario(String usuario, String senha) {
		if (con != null) {
			Retorno resultado = new Retorno("Usuario não encontrado", false);

			// Informações do usuario a ser retornado
			Usuario u = new Usuario();

			// String SQL a ser executada
			String SQL_BUSCA = "SELECT * FROM usuario WHERE usuario = " + "'" + usuario + "'" + "AND" + " senha = "
					+ "'" + senha + "'";

			// Prepara o SQL
			PreparedStatement ps;

			try {
				ps = con.prepareStatement(SQL_BUSCA);

				// Resultado da busca
				ResultSet rs = ps.executeQuery();

				// em quanto tiver tinhas no banco de dados, ele vai executar o while
				while (rs.next()) {
					u.setCodigo(rs.getInt("codigo"));
					u.setNome(rs.getString("nome"));
					u.setSobrenome(rs.getString("sobrenome"));
					u.setUsuario(rs.getString("usuario"));
					u.setEmail(rs.getString("email"));

					resultado = buscaUsuarioLiberado(u.getCodigo().toString());

					if (resultado.getRetorno()) {
						// Retorno final
						resultado.setUser(u);
						resultado.setRetorno(true);
						resultado.setMensagem("Bem vindo " + u.getNome() + " " + u.getSobrenome());

						return resultado;
					} else {
						// Retorno final
						resultado.setUser(null);
						resultado.setRetorno(false);
						resultado.setMensagem("Usuario pentende no sistema");

						return resultado;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return resultado;
		}

		return null;
	}
}
