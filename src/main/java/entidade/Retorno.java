package entidade;

public class Retorno {
	
	public Retorno(Permissao per,Boolean retorno,String mensagem) {
		this.per = per;
		this.retorno = retorno;
		this.mensagem = mensagem;
	}
	
	public Retorno(String mensagem,Boolean retorno) {
		this.mensagem = mensagem;
		this.retorno = retorno;
	}
	
	public Retorno(Usuario user, Boolean retorno) {
		this.user = user;
		this.retorno = retorno;
	}
	
	public Retorno() {
		
	}
	
	private Usuario user;
	private String mensagem;
	private Boolean retorno;
	private Permissao per;
	
	public Permissao getPer() {
		return per;
	}
	
	public Permissao setPer(Permissao per) {
		this.per = per;
		return per;
	}
	
	public Usuario getUser() {
		return user;
	}
	
	public void setUser(Usuario user) {
		this.user = user;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public Boolean getRetorno() {
		return retorno;
	}
	public void setRetorno(Boolean retorno) {
		this.retorno = retorno;
	}
}
