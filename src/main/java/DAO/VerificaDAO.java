package DAO;

import java.sql.Connection;

import database.ConDB;
/*
 	Esse DAO foi desenvolvido para fica mais f�cil
 	de agrupar os metodos da verifica��o de boleto
 	que ser� enviado para o usu�rio... 
 */
public class VerificaDAO {
	private Connection con;

	public VerificaDAO() {
		con = ConDB.getConnection();
	}
	
	
	
}
