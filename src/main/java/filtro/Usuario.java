package filtro;

import java.io.IOException;

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

import entidade.Retorno;
import mbean.LoginMBean;

@WebFilter("/restrito/usuario/*")
public class Usuario implements Filter {

	Boolean logado;
	Request request;
	HttpSession session;
	Retorno user;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		logado = (Boolean) ((HttpServletRequest) request).getSession().getAttribute("usuarioNormalLogado");
		user = (Retorno) ((HttpServletRequest) request).getSession().getAttribute("usuarioLogado");
		
			if (logado == false || logado == null) {
				String diretorio = ((HttpServletRequest) request).getContextPath();
				System.out.println(diretorio);
				((HttpServletResponse) response).sendRedirect(diretorio + "/index.xhtml");
			}
			chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
