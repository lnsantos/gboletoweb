package filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class ControllerAcesso implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		Object sessionAtual = ((HttpServletRequest) request).getAttribute("loginMBean");
		String URL_BROWSE = ((HttpServletRequest) request).getRequestURL().toString();
		String URL_PATH = URL_BROWSE.substring(URL_BROWSE.lastIndexOf("/gboletoweb/"));
		System.out.println("URL_PATH : " + URL_PATH);
		
		if(sessionAtual != null && ( URL_PATH.equals("/gboletoweb/index.xhtml")
							   || URL_PATH.equals("/gboletoweb/RES_NOT_FOUND" )
							   || URL_PATH.equals("/gboletoweb"))) 
		{
			((HttpServletResponse)response).sendRedirect("/gboletoweb/restrito/administrador/index.xhtml");
		}
		
		chain.doFilter(request, response);
	}

}
