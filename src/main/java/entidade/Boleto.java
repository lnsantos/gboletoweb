package entidade;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Boleto {

	private Integer codigo;
	private String item;
	private double valor;
	private Date vencimento;
	private Date emissao;
	private String pdf_caminho = "";
	private int id_usuario;
	private int status = 0;
	private String vencimentoString;
	private String emissaoString;
	
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
		this.status = status;
	}
	@Override
	public String toString() {
		return "Boleto [codigo=" + codigo + ", item=" + item + "] POR USUARIO : " + id_usuario;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
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
