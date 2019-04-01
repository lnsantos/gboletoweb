package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.ConDB;
import entidade.Boleto;
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
	
}
