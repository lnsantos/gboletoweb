package validation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import database.ConDB;
import entidade.Boleto;
import entidade.Mail;
import entidade.Usuario;

public class BoletoValidation {
	Connection con;

	public BoletoValidation() {
		con = ConDB.getConnection();
	}
	/*
	  Return All Users
	  	- CODIGO
	  	- EMAIL
	  Return All boletos
	  	- ID_USUARIO
	  	- NOME
	  	- VALOR
	  
	 */
	
	public Set<Mail> listSendEmail() throws SQLException{
		Set<Mail> send = new HashSet<Mail>();
		String html = "";
		
		System.out.println(" waiting validate all mail...");
		if(allTicketWaitingValidation() != null) {
			System.out.println("Validation completed !");
			Set<Usuario> users = authenticateEmail(returnAllUsers());
			
			for(Iterator<Usuario> u = users.iterator(); u.hasNext();) {
				System.out.println("Processing...");
				Mail sendUser = new Mail();
				Usuario user = u.next();
				sendUser.setEmail(user.getEmail());
				for(Iterator<Boleto> b = user.getBoletos().iterator(); b.hasNext();) {
					Boleto ticket = new Boleto();
					html = "<li>"+ticket.getItem()+"</li>";
					sendUser.setMsg(sendUser.getMsg()+html); 
				}
				send.add(sendUser);
				System.out.println("Processing completed !");
			}
			return send;
		}
		System.out.println("Validation failed !");
		return null;
	}
	
	public Set<Usuario> authenticateEmail(Set<Usuario> usuarios) {
		Set<Usuario> userReturn = new HashSet<Usuario>();
		for(Iterator<Usuario> usuario = usuarios.iterator(); usuario.hasNext();) {
			Usuario userActual = new Usuario();
			Boleto ticket = new Boleto();
			Set<Boleto> boletos = returnAllTicket();
			Set<Boleto> ticketUser = new HashSet<Boleto>();
			userActual = usuario.next();
			for(Iterator<Boleto> b = boletos.iterator(); b.hasNext();) {
				ticket = b.next();
				if(ticket.getId_usuario() == userActual.getCodigo()) {
					ticketUser.add(ticket);
				}
			}
			userReturn.add(userActual);
		}
		return userReturn;
	}
	
	public List<Boleto> verificaVencimentoAntes(List<Boleto> boletos, int diasAntes) {
		int diaAntes = 86400000 * diasAntes;
		List<Boleto> boletosAntes = new ArrayList<Boleto>();
		for (Boleto b : boletos) {
			Calendar diaAtual = Calendar.getInstance();
			long total = b.getVencimento().getTime() - diaAtual.getTimeInMillis();
			if (total < diaAntes) {
				boletosAntes.add(b);
			}
		}
		return boletosAntes;
	}

	public Set<Usuario> allTicketWaitingValidation() throws SQLException {
		if (con != null) {
			String SQL = "SELECT * FROM usuario"
					+ "     JOIN boleto on boleto.id_usuario = usuario.codigo"
					+ "	  WHERE boleto.statu <> 4 AND boleto.verificado <> 1 ORDER BY boleto.vencimento";

			Set<Usuario> usuarios = new HashSet<>();
			
			ResultSet rs = selectDatabase(SQL);
			try {
				while (rs.next()) {
					Usuario u = new Usuario();
					u.setCodigo(rs.getInt("usuario.codigo"));
					u.setEmail(rs.getString("usuario.email"));
					
					
					Boleto b = new Boleto();

					b.setCodigo(rs.getInt("boleto.codigo"));
					b.setEmissao(new Date(rs.getLong("boleto.emissao")));
					b.setId_usuario(rs.getInt("boleto.id_usuario"));
					b.setItem(rs.getString("boleto.nome_item"));
					b.setPdf_caminho(rs.getString("boleto.caminho"));
					b.setStatus(rs.getInt("boleto.statu"));
					b.setValor(rs.getDouble("boleto.valor"));
					b.setVencimento(new Date(rs.getLong("boleto.vencimento")));
					
					u.getBoletos().add(b);
					usuarios.add(u);
					int novoStatu = verificaVencimento(b.getVencimento());
					if(novoStatu != b.getStatus()) {
						b.setStatus(novoStatu);
						if(mudaStatuVerificado(b)) {
							System.out.println(b.getCodigo() + "Novo statu inserido!");
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return usuarios;
			
		}
		return null;
	}
	
	
	public int verificaVencimento(Date dataVencimento) {
		Calendar dataAtual = Calendar.getInstance();
		
		long diferencaEntreData = dataVencimento.getTime() - dataAtual.getTime().getTime();
		System.out.println("DIA HOJE : " + dataAtual.getTime().getTime() + " ||| VENCIMENTO : "+ dataVencimento.getTime()+ " ||| DIFERENCA : " + diferencaEntreData);
		
		if(diferencaEntreData < 0){
			System.out.println("Atrasado");
			return 3;
		}else if(diferencaEntreData > 86400000) {
			System.out.println("Boleto Pendente no sistema");
			return 1;
		}else if((diferencaEntreData) < 86400000) {
			System.out.println("Falta 1 Dia");
			return 2;
		}else {
			System.out.println("Atrasado");
			return 3;
		}
	
	}
	
	private boolean mudaStatuVerificado(Boleto b) {
		if (con != null) {
			String SQL = "UPDATE boleto SET statu = "+ b.getStatus()+ " WHERE codigo = " + b.getCodigo();
			if(b.getStatus() > 2) {
				SQL = "UPDATE boleto SET statu = "+ b.getStatus()+ ", verificado = 1 WHERE codigo = " + b.getCodigo();
			}
			try {
				PreparedStatement ps = con.prepareStatement(SQL);
				if (ps.executeUpdate() > 0) {
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			return false;
		}
		return false;
	}
	
	private Set<Usuario> returnAllUsers() {
		String SQL = "SELECT usuario.codigo, usuario.email FROM usuario JOIN boleto on boleto.id_usuario = usuario.codigo" + 
				" WHERE boleto.statu <> 4 GROUP BY usuario.codigo";
		Set<Usuario> users = new HashSet<Usuario>();
		System.out.println("Loading all users that have ticket in the system...");
			try {
				ResultSet rs = selectDatabase(SQL);
				while(rs.next()) {
					Usuario user = new Usuario();
					user.setCodigo(rs.getInt("usuario.codigo"));
					user.setEmail(rs.getString("usuario.email"));
					users.add(user);
				}
				System.out.println("loading users completed !");
				return users;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("loading users failed !");
				return null;
			}
	}
	
	private Set<Boleto> returnAllTicket(){
		String SQL = "SELECT boleto.valor, boleto.id_usuario, boleto.nome_item FROM boleto " + 
				"WHERE boleto.statu = 3 AND boleto.verificado = 0 GROUP BY boleto.codigo;";
		Set<Boleto> tickets = new HashSet<Boleto>();
		System.out.println("Loading all ticket not verified in the system...");
			try {
				ResultSet rs = selectDatabase(SQL);
				while(rs.next()) {
					Boleto ticket = new Boleto();
					ticket.setId_usuario(rs.getInt("boleto.id_usuario"));
					ticket.setValor(rs.getDouble("boleto.valor"));
					ticket.setItem(rs.getString("boleto.nome_item"));
					tickets.add(ticket);
				}
				return tickets;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Loading ticket failed !");
			}
		System.out.println("Loading ticket completed !");
		return null;
	}
	
	
	private ResultSet selectDatabase(String S) throws SQLException {
			try {
				PreparedStatement ps = con.prepareStatement(S);
				ResultSet rs = ps.executeQuery();
				return rs;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return null;
	}
	
}
