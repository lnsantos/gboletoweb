package util;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.ToggleEvent;

@ManagedBean
public class FieldsetView {
	 public void handleToggle(ToggleEvent event) {
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocultado", "Visivel:" + event.getVisibility());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
}
