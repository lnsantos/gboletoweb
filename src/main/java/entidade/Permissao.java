package entidade;

public class Permissao {
	/*
	 
	-- --------------------------------------
    -- Explicações do CODIGO ----------------
    -- o codigo do usuário deve ser inserido-
    -- após a criação da conta no sistema   -
    -- --------------------------------------
	codigo integer primary key unique ,
    statu int default 0, 
    -- --------------------------------------
    -- Explicações do STATUS ----------------
	-- 0 = Aguardando liberação de acesso   -
    -- 1 = Habilitado a utilizar o sistema  -
    -- 2 = Desabilitado a utilizar o sistema-
    -- --------------------------------------
    -- Explicações das Permissões -----------
    -- 0 = Não habilitado                   -
    -- 1 = Habilitado ao usuario            -
	-- --------------------------------------
    cria_usuario int default 0,
    cria_boleto int default 0,
    deleta_usuario int default 0,
    deleta_boleto int default 0,
    edita_usuario int default 0,
    edita_boleto int default 0
	 
*/
	
	private Integer codigo;
	private Integer statu= 0;
	private Integer cria_usuario= 0;
	private Integer cria_boleto= 0;
	private Integer deleta_boleto= 0;
	private Integer deleta_usuario= 0;
	private Integer edita_usuario= 0;
	private Integer edita_boleto= 0;
	
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Integer getStatu() {
		return statu;
	}
	public void setStatu(Integer statu) {
		this.statu = statu;
	}
	public Integer getCria_usuario() {
		return cria_usuario;
	}
	public void setCria_usuario(Integer cria_usuario) {
		this.cria_usuario = cria_usuario;
	}
	public Integer getCria_boleto() {
		return cria_boleto;
	}
	public void setCria_boleto(Integer cria_boleto) {
		this.cria_boleto = cria_boleto;
	}
	public Integer getDeleta_boleto() {
		return deleta_boleto;
	}
	public void setDeleta_boleto(Integer deleta_boleto) {
		this.deleta_boleto = deleta_boleto;
	}
	public Integer getDeleta_usuario() {
		return deleta_usuario;
	}
	public void setDeleta_usuario(Integer deleta_usuario) {
		this.deleta_usuario = deleta_usuario;
	}
	public Integer getEdita_usuario() {
		return edita_usuario;
	}
	public void setEdita_usuario(Integer edita_usuario) {
		this.edita_usuario = edita_usuario;
	}
	public Integer getEdita_boleto() {
		return edita_boleto;
	}
	public void setEdita_boleto(Integer edita_boleto) {
		this.edita_boleto = edita_boleto;
	}
}
