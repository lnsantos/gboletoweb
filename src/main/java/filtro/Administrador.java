package filtro;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.faces.bean.ManagedProperty;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.coyote.Request;

import mbean.LoginMBean;

@WebFilter("/restrito/administrador/*")
public class Administrador implements Filter{
	
	LoginMBean loginMbean; 
	Request request;
	HttpSession session;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Sessão iniciou
		// SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy z");
		// Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		// System.out.println(loginMbean.getUsuarioLogado().getUsuario() + " Esta Online [ " + formataData.format(c));
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Processamento { Thregger }
			loginMbean = (LoginMBean) ((HttpServletRequest) request).getSession().getAttribute("loginMBean");
			
			if(loginMbean == null) {
				String diretorio = ((HttpServletRequest)request).getContextPath();
				System.out.println(diretorio);
				((HttpServletResponse)response).sendRedirect(diretorio + "/index.xhtml");
			}else {
				System.out.println(loginMbean.getUsuarioLogado().getUsuario() + " Esta logado no sistema [ " + dataHoraAtual() + "] " + request.getProtocol());
				chain.doFilter(request, response);
			}
		
	}

	@Override
	public void destroy() {
		// Sessão finalizada
		
		// System.out.println(loginMbean.getUsuarioLogado().getUsuario() + " Finalizou a Sessão [ " +dataHoraAtual());
	}
	
	private String dataHoraAtual() {
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy z");
		Calendar c = new GregorianCalendar();
		formataData.setTimeZone(c.getTimeZone());
		return formataData.format(c.getTime());
	}
}
