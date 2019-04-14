package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.ConDB;
import entidade.Retorno;
import entidade.Upload;


public class UploadDAO {
	
	Connection con;
	
	public UploadDAO() {
		con = ConDB.getConnection();
	}
	

	
}
