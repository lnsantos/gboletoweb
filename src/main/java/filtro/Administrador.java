package filtro;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.coyote.Request;

import mbean.LoginMBean;

@WebFilter("/restrito/adm/*")
public class Administrador implements Filter{
	
	LoginMBean loginMbean; 
	Request request;
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
		System.out.println(loginMbean.getUsuarioLogado().getUsuario() + "Encontrado no sistema");
		loginMbean = (LoginMBean) ((HttpServletRequest) request).getSession().getAttribute("LoginMBean");
		if(loginMbean == null || loginMbean.getResultado().getUser().getNome().equals("")) {
			String diretorio = ((HttpServletRequest)request).getContextPath();
			((HttpServletRequest)request).getSession().setAttribute("msg", "Acesso Negado");
			((HttpServletResponse)response).sendRedirect(diretorio + "");
		}else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		// Sessão finalizada
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy z");
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));
		System.out.println(loginMbean.getUsuarioLogado().getUsuario() + " Finalizou a Sessão [ " + formataData.format(c));
	}

}
