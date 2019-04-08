package DAO;

import java.awt.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import database.ConDB;
import entidade.Boleto;
import entidade.Upload;

public class BoletoDAO {
	
	Connection con;
	Boleto boleto;
	
	public BoletoDAO() {
		con = ConDB.getConnection();
		boleto = new Boleto();
	}

	// CRUD - INSERIR O BOLETO NO SISTEMA
	public boolean inserirBoleto(Boleto b, int id) {
		if (con != null) {
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
		}
		return false;
	} 
	
	public Upload busca_Id_Boleto(Boleto b) {
		if (con != null) {
			String SQL = "SELECT * FROM boleto WHERE nome_item = " + "'"+ b.getItem() + "'" +
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
		}
		return null;
	}
	
	public List<Boleto> listaBoletosUsuarioLogado (int codigoUsuarioLogado){
		if (con != null) {
			String SQL = "SELECT  b.* , ub.caminho FROM boleto as b" + 
					"	LEFT join upload_boleto as ub ON ub.id_boleto = b.codigo and ub.id_usuario = "+"'"+codigoUsuarioLogado+"'" + 
					"   WHERE b.statu = 0 OR b.statu = 1 OR b.statu = 2"
					+ " ORDER BY b.vencimento" ;
			List<Boleto> boletos = new ArrayList<Boleto>();
			
			
			
			try {
				PreparedStatement ps = con.prepareStatement(SQL);
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					Boleto b = new Boleto();
					
					b.setCodigo(rs.getInt("b.codigo"));
					b.setEmissao(new Date(rs.getLong("b.emissao")));
					b.setId_usuario(rs.getInt("b.id_usuario"));
					b.setItem(rs.getString("nome_item"));
					b.setPdf_caminho(rs.getString("ub.caminho"));
					b.setStatus(rs.getInt("b.statu"));
					b.setValor(rs.getDouble("b.valor"));
					b.setVencimento(new Date(rs.getLong("b.vencimento")));
					
					boletos.add(b);
					System.out.println("BOLETO : " + b.getItem() + "Encontrado no sistema" + " Localizado em : " + b.getPdf_caminho() + " Para o usuario : " + b.getId_usuario());
				}
			
				return boletos;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("listaBoletosUsuarioLogado :: Problema ao buscar boletos!");
			}	
		}
		return null;
	}
	
}
