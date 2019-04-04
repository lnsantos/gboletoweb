package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import database.ConDB;
import entidade.Boleto;
import entidade.Retorno;
import entidade.Upload;
import mbean.LoginMBean;

public class BoletoDAO {
	
	Connection con;
	Boleto boleto;
	
	public BoletoDAO() {
		con = ConDB.getConnection();
		boleto = new Boleto();
	}

	// CRUD - INSERIR O BOLETO NO SISTEMA
	public boolean inserirBoleto(Boleto b, int id) {
		String SQL = "INSERT INTO boleto VALUE(0,?,?,?,?,?,?)";
		// LoginMBean informa = null;
		try {
			PreparedStatement ps = con.prepareStatement(SQL);
				ps.setString(1, b.getItem());
				ps.setDouble(2, b.getValor());
				ps.setLong(3, b.getVencimento().getTime());
				ps.setInt(4, b.getStatus());
				ps.setLong(5, b.getEmissao().getTime());
				ps.setInt(6, id);
			if(ps.executeUpdate() > 0) {
				System.out.println("Boleto " + b + " inserido com sucesso!");
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Problema ao inserir boleto " + b);
		}
		return false;
	} 
	
	public Upload busca_Id_Boleto(Boleto b) {
		String SQL = "SELECT codigo FROM boleto WHERE nome_item = " + "'"+ b.getItem() + "'" +
				" AND valor = " + b.getValor() + " AND vencimento = " + b.getVencimento().getTime() +
				" AND emissao = " + b.getEmissao().getTime() + " AND id_usuario = " + b.getId_usuario();
		
		Upload returno = new Upload();
		try {
			PreparedStatement ps = con.prepareStatement(SQL);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				returno.setId_boleto(rs.getInt("codigo"));
				returno.setId_usuario(rs.getInt("id_usuario"));
				return returno;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Boleto> listaBoletos (){
		String SQL = "SELECT * FROM boleto WHERE statu = 0 OR statu = 1 OR statu = 2";
		
		return null;
	}
	
}
