package entidade;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Boleto {

	private Integer codigo;
	private String item;
	private double valor = 10.00;
	private Date vencimento;
	private Date emissao;
	private String pdf_caminho = "";
	private int id_usuario;
	private int status = 1;
	private String vencimentoString;
	private String emissaoString;
	private String mensagemEmail;
	private int verificado = 0;
	
	
	public int getVerificado() {
		return verificado;
	}
	public void setVerificado(int verificado) {
		this.verificado = verificado;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public Date getVencimento() {
		return vencimento;
	}
	
	public void setVencimento(Date vencimento) {
		setVencimentoString(vencimento);
		this.vencimento = vencimento;
	}
	public Date getEmissao() {
		return emissao;
	}
	public void setEmissao(Date emissao) {
		setEmissaoString(emissao);
		this.emissao = emissao;
	}
	public String getPdf_caminho() {
		return pdf_caminho;
	}
	public void setPdf_caminho(String pdf_caminho) {
		this.pdf_caminho = pdf_caminho;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		
		if(getVencimentoString() == "" ) setVencimentoString(getVencimento());
		
		if(status == 1) {
			setMensagemEmail("Esta pentende ainda, data de vencimento : " + getVencimento());
		}else if(status == 2) {
			setMensagemEmail("Amanh� � o �ltimo dia para paga esse boleto, fique atento  ");
		}else if(status == 3) {
			setMensagemEmail("Infelizmente esse boleto venceu dia "+ getVencimento() + "Para n�o receber mais esse boleto nas verifica��es, confirme o pagamento dele no sistema!");
		}else {
			setMensagemEmail("Sem informa��es...");
		}
		this.status = status;
	}
	@Override
	public String toString() {
		return "Boleto [codigo=" + codigo + ", item=" + item + "] POR USUARIO : " + id_usuario;
	}
	
	public void setVencimentoString(Date v) {
		SimpleDateFormat formato = new SimpleDateFormat("dd / MMM / yyyy");
		this.vencimentoString = formato.format(v);
	}
	public String getVencimentoString() {
		return vencimentoString;
	}
	
	public String getEmissaoString() {
		return emissaoString;
	}
	public void setEmissaoString(Date emissaoString) {
		SimpleDateFormat formato = new SimpleDateFormat("dd / MMM / yyyy");
		this.emissaoString = formato.format(emissaoString);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	public String getMensagemEmail() {
		return mensagemEmail;
	}
	public void setMensagemEmail(String mensagemEmail) {
		this.mensagemEmail = mensagemEmail;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Boleto other = (Boleto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
}
