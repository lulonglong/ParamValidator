package org.lulonglong.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.lulonglong.base.validator.AbstractParamValidator;
import org.lulonglong.util.ValidateUtil;
import org.lulonglong.vo.ErrorResultVO;

/**
 * Servlet Filter implementation class ValidateFilter
 */
public class ValidateFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public ValidateFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		String servletUrlString = req.getServletPath();
		List<AbstractParamValidator> validators = ValidateUtil.servletValidators
				.get(servletUrlString);
		if (validators != null) {
			List<String> errorCodes = new ArrayList<String>();

			for (AbstractParamValidator validator : validators) {
				try {
					errorCodes.addAll(validator.validate(req));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (!errorCodes.isEmpty()) {
				ErrorResultVO vo = new ErrorResultVO();
				vo.setErrorCodes(errorCodes);
				if (ValidateUtil.returnType == "json") {
					response.getWriter().println(vo.toJsonString());
				} else {
					response.getWriter().println(vo.toXmlString());
				}
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
