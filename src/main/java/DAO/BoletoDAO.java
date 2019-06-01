package DAO;

import java.awt.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import database.ConDB;
import entidade.Boleto;
import entidade.Upload;
import entidade.Usuario;

public class BoletoDAO {

    Connection con;
    Boleto boleto;

    public BoletoDAO() {
        con = ConDB.getConnection();
        boleto = new Boleto();
    }

    // CRUD - INSERIR O BOLETO NO SISTEMA
    public boolean inserirBoleto(Boleto b) {
        if (con != null) {
            String SQL = "INSERT INTO boleto VALUE(0,?,?,?,?,?,?,?,0)";
            // LoginMBean informa = null;
            try {
                PreparedStatement ps = con.prepareStatement(SQL);
                ps.setString(1, b.getItem());
                ps.setDouble(2, b.getValor());
                ps.setLong(3, b.getVencimento().getTime());

                b.setStatus(verificaVencimento(b.getVencimento()));

                ps.setInt(4, b.getStatus());
                ps.setLong(5, b.getEmissao().getTime());
                ps.setInt(6, b.getId_usuario());
                ps.setString(7, b.getPdf_caminho());

                if (ps.executeUpdate() > 0) {
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

    public boolean deletaBoleto(int codigo) throws SQLException {
        String SQL = "DELETE FROM boleto WHERE codigo = " + codigo;
        if (con != null) {
            PreparedStatement ps = con.prepareStatement(SQL);
            return ps.executeUpdate() > 0;
        }
        return false;
    }

    public Upload busca_Id_Boleto(Boleto b) {
        if (con != null) {
            String SQL = "SELECT * FROM boleto WHERE nome_item = " + "'" + b.getItem() + "'" + " AND valor = "
                    + b.getValor() + " AND vencimento = " + b.getVencimento().getTime() + " AND emissao = "
                    + b.getEmissao().getTime() + " AND id_usuario = " + b.getId_usuario();

            Upload returno = new Upload();
            try {
                PreparedStatement ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
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

    public Set<Usuario> todoBoletosPendenteVerificandoStatu() {
        if (con != null) {
            String SQL = "SELECT * FROM usuario";

            Set<Usuario> usuarios = new HashSet<>();

            ResultSet rs = executeSQL(SQL);
            try {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setCodigo(rs.getInt("codigo"));
                    u.setEmail(rs.getString("email"));

                    String boletoSQL = "SELECT * FROM boleto WHERE boleto.id_usuario = ? AND boleto.statu <> 4 AND boleto.verificado <> 1";

                    PreparedStatement ps = con.prepareStatement(boletoSQL);
                    ps.setInt(1, u.getCodigo());
                    ResultSet boletos = ps.executeQuery();

                    while (boletos.next()) {
                        Boleto b = new Boleto();
                        
                        b.setCodigo(boletos.getInt("codigo"));
                        b.setEmissao(new Date(boletos.getLong("emissao")));
                        b.setId_usuario(boletos.getInt("id_usuario"));
                        b.setItem(boletos.getString("nome_item"));
                        b.setPdf_caminho(boletos.getString("caminho"));
                        b.setStatus(boletos.getInt("statu"));
                        b.setValor(boletos.getDouble("valor"));
                        b.setVencimento(new Date(boletos.getLong("vencimento")));
                        
                        System.out.println("Verificando Status de " + b.getItem());
                        int novoStatu = verificaVencimento(b.getVencimento());
                        System.out.println("Comparando Status de " + b.getItem());
                        if (novoStatu != b.getStatus()) {
                            b.setStatus(novoStatu);
                            if (mudaStatuVerificado(b)) {
                                System.out.println(b.getCodigo() + "Novo statu inserido!");
                            }
                        }
                        u.getBoletos().add(b);
                        usuarios.add(u);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return usuarios;

        }
        return null;
    }

    public int verificaVencimento(Date dataVencimento) {
        Calendar dataAtual = Calendar.getInstance();

        long diferencaEntreData = dataVencimento.getTime() - dataAtual.getTime().getTime();
        System.out.println("DIA HOJE : " + dataAtual.getTime().getTime() + " ||| VENCIMENTO : " + dataVencimento.getTime() + " ||| DIFERENCA : " + diferencaEntreData);

        if (diferencaEntreData < 0) {
            System.out.println("Atrasado");
            return 3;
        } else if (diferencaEntreData > 86400000) {
            System.out.println("Boleto Pendente no sistema");
            return 1;
        } else if ((diferencaEntreData) < 86400000) {
            System.out.println("Falta 1 Dia");
            return 2;
        } else {
            System.out.println("Atrasado");
            return 3;
        }

    }

    public List<Boleto> listaBoletoPorStatuDoUsuario(int codigoBusca, int codigoUsuario) {
        String SQL = "SELECT * FROM boleto WHERE id_usuario = " + codigoUsuario + " AND statu = " + "'" + codigoBusca + "'";
        List<Boleto> boletos = new ArrayList<Boleto>();
        if (con != null) {
            try {
                PreparedStatement ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Boleto b = new Boleto();

                    b.setCodigo(rs.getInt("codigo"));
                    b.setEmissao(new Date(rs.getLong("emissao")));
                    b.setItem(rs.getString("nome_item"));
                    b.setPdf_caminho(rs.getString("caminho"));
                    b.setStatus(rs.getInt("statu"));
                    b.setValor(rs.getDouble("valor"));
                    b.setVencimento(new Date(rs.getLong("vencimento")));

                    boletos.add(b);
                }
                return boletos;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private ResultSet executeSQL(String SQL) {
        if (con != null) {
            PreparedStatement ps;
            try {
                ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();
                return rs;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public List<Boleto> todosBoletos() {
        if (con != null) {
            String SQL = "SELECT * FROM boleto ORDER BY boleto.vencimento";
            List<Boleto> boletos = new ArrayList<Boleto>();
            try {
                PreparedStatement ps = con.prepareStatement(SQL);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Boleto b = new Boleto();

                    b.setCodigo(rs.getInt("codigo"));
                    b.setEmissao(new Date(rs.getLong("emissao")));
                    b.setId_usuario(rs.getInt("id_usuario"));
                    b.setItem(rs.getString("nome_item"));
                    b.setPdf_caminho(rs.getString("ub.caminho"));
                    b.setStatus(rs.getInt("statu"));
                    b.setValor(rs.getDouble("valor"));
                    b.setVencimento(new Date(rs.getLong("vencimento")));

                    boletos.add(b);
                }
                return boletos;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("NEM UM BOLETO FALTA 1 DIA ");
        return null;

    }

    private boolean mudaStatuVerificado(Boleto b) {
        if (con != null) {
            String SQL = "UPDATE boleto SET statu = " + b.getStatus() + " WHERE codigo = " + b.getCodigo();
            if (b.getStatus() > 2) {
                SQL = "UPDATE boleto SET statu = " + b.getStatus() + ", verificado = 1 WHERE codigo = " + b.getCodigo();
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

    public boolean confirmaPagamento(int codigo) {
        if (con != null) {
            String SQL = "UPDATE boleto SET statu = 4 WHERE codigo = " + codigo;
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

    public List<Boleto> listaBoletosUsuarioLogado(int codigoUsuarioLogado) {
        /*
		 * SELECT * FROM boleto as B INNER JOIN upload_boleto as ub ON ub.id_boleto =
		 * b.codigo WHERE b.statu = 1 OR b.statu = 2 OR b.statu = 3 AND b.id_usuario =
		 * 1;
         */
        if (con != null) {
            String SQL = "SELECT  * FROM boleto as b WHERE b.id_usuario = " + "'" + codigoUsuarioLogado
                    + "' AND b.statu <> 4 ORDER BY b.vencimento";

            List<Boleto> boletos = new ArrayList<Boleto>();

            try {
                PreparedStatement ps = con.prepareStatement(SQL);
                System.out.println("CODIGO SQL : " + ps.toString());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Boleto b = new Boleto();

                    b.setCodigo(rs.getInt("b.codigo"));
                    b.setEmissao(new Date(rs.getLong("b.emissao")));
                    b.setId_usuario(rs.getInt("b.id_usuario"));
                    b.setItem(rs.getString("b.nome_item"));
                    b.setPdf_caminho(rs.getString("b.caminho"));
                    b.setStatus(rs.getInt("b.statu"));
                    b.setValor(rs.getDouble("b.valor"));
                    b.setVencimento(new Date(rs.getLong("b.vencimento")));

                    boletos.add(b);
                    System.out.println("BOLETO : " + b.getItem() + "Encontrado no sistema" + " Localizado em : "
                            + b.getPdf_caminho() + " Para o usuario : " + b.getId_usuario());
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
