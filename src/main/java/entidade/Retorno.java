package entidade;

import java.io.File;

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
	
	public Retorno(File file, String caminho, Boolean retorno ) {
		this.file = file;
		this.caminho = caminho;
		this.retorno = retorno;
	}

	public Retorno(Boolean retorno, String caminho) {
		this.retorno = retorno;
		this.caminho = caminho;
	}
	public Retorno(Usuario user, Boleto boleto) {
		this.boleto = boleto;
		this.user = user;
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
	private String caminho;
	private File file;
	private Boleto boleto;
	
	public File getFile() {
		return file;
	}
	
	public File setFile(File file) {
		return this.file = file;
	}
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

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public Boleto getBoleto() {
		return boleto;
	}

	public void setBoleto(Boleto boleto) {
		this.boleto = boleto;
	}
}
