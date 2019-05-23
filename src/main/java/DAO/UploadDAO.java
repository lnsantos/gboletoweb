package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.context.FacesContext;

import database.ConDB;
import entidade.Retorno;
import entidade.Upload;


public class UploadDAO {
	
	Connection con;
	
	public UploadDAO() {
		con = ConDB.getConnection();
	}
	
	public boolean incluirArquivo(String caminho) {
			// Path path = Paths.get(caminho);
			File f = new File(caminho);
            //converte o objeto file em array de bytes
			try {
				 InputStream is = new FileInputStream(f.getPath());
				 is.close();
		         f.delete(); //Deleta o arquivo onde foi criado
		         return true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
    }
	
}
