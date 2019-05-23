package DAO;

import java.sql.Connection;

import database.ConDB;
/*
 	Esse DAO foi desenvolvido para fica mais fácil
 	de agrupar os metodos da verificação de boleto
 	que será enviado para o usuário... 
 */
public class VerificaDAO {
	private Connection con;

	public VerificaDAO() {
		con = ConDB.getConnection();
	}
	
	
	
}
