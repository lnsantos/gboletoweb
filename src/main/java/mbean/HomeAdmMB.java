package mbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import DAO.BoletoDAO;
import entidade.Boleto;

@ManagedBean(name="home")
@RequestScoped
public class HomeAdmMB {
	BoletoDAO bDao;
	Boleto boleto_inserir;
	FacesContext context;
    LoginMBean informa;
   
    Integer codigoUsuarioFRONT;
	public HomeAdmMB() {
		bDao = new BoletoDAO();
		boleto_inserir = new Boleto();
		context = FacesContext.getCurrentInstance();
		System.out.println("Construtor : " + boleto_inserir.getId_usuario());
		codigoUsuarioFRONT = 0;
	}
	public void x() {
		if(boleto_inserir != null) {
			System.out.println("LOGADO NO SISTEMA : " + codigoUsuarioFRONT);
			if(bDao.inserirBoleto(boleto_inserir,codigoUsuarioFRONT)) {
				context.addMessage(null, new 
						FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso!",boleto_inserir.getItem() + " Inserido!"));
			}else {
				context.addMessage(null, new 
						FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!",boleto_inserir.getItem() + " Não foi inserido!"));
			}
		}else {
			context.addMessage(null, new 
					FacesMessage(FacesMessage.SEVERITY_ERROR,"Informações do boleto estão nulas",boleto_inserir.getItem() + " Não foi inserido!"));
		}
	}

	public Boleto getBoleto_inserir() {
		return boleto_inserir;
	}

	public void setBoleto_inserir(Boleto boleto_inserir) {
		this.boleto_inserir = boleto_inserir;
	}

	public Integer getCodigoUsuarioFRONT() {
		return codigoUsuarioFRONT;
	}

	public void setCodigoUsuarioFRONT(Integer codigoUsuarioFRONT) {
		this.codigoUsuarioFRONT = codigoUsuarioFRONT;
	}
	
}
