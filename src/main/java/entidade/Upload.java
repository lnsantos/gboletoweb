package entidade;

public class Upload {
	
	private Integer codigo;
	private Integer id_boleto;
	private Integer id_usuario;
	private String caminho;
	
	public Upload(Integer id_boleto, Integer id_usuario, String caminho) {
		super();
		this.id_boleto = id_boleto;
		this.id_usuario = id_usuario;
		this.caminho = caminho;
	}
	
	public Upload() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Integer getId_boleto() {
		return id_boleto;
	}
	public void setId_boleto(Integer id_boleto) {
		this.id_boleto = id_boleto;
	}
	public Integer getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Upload other = (Upload) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
}
