package util;
import javax.faces.bean.ManagedBean;


@ManagedBean
public class Navigation {
	// Nessa Class teremos todos os acessos as p�ginas, link redirecinados 
	
	public String cadastro() {
		return "cadastro?faces-redirect=true";
	}
	public String login() {
		return "index?faces-redirect=true";
	}
}
