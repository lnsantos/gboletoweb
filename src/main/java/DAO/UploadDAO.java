package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;

import database.ConDB;
import entidade.Retorno;
import entidade.Upload;


public class UploadDAO {
	
	Connection con;
	
	public UploadDAO() {
		con = ConDB.getConnection();
	}
	
	public Retorno uploadArquivo(Upload u, String caminho) {
		String SQL = "INSERT INTO upload_boleto VALUE(0,?,?,?)";
		
		/* String SQL_BUSCA_ID_UPLOAD = "SELECT codigo FROM upload_boleto WHERE caminho = " + "'" + u.getCaminho()+"'" +
				" AND id_boleto = " + u.getId_boleto() +
				" AND id_usuario = " + u.getId_usuario();
		*/
		// String SQL_
		
		Retorno resultado = new Retorno();
		resultado.setRetorno(false);
		try {
			PreparedStatement ps = con.prepareStatement(SQL);
			
			ps.setInt(1, u.getId_usuario());
			ps.setInt(2, u.getId_boleto());
			ps.setString(3, caminho);
			
			if(ps.executeUpdate() > 0) {
				resultado.setRetorno(true);
				resultado.setCaminho(u.getCaminho());
				return resultado;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultado;
	}
	
}
