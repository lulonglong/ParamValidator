package org.paramvalidate.filter;

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

import org.paramvalidate.base.validator.AbstractParamValidator;
import org.paramvalidate.util.ValidateUtil;
import org.paramvalidate.vo.AbstractErrorResultVO;

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
		// get request
		HttpServletRequest req = (HttpServletRequest) request;

		// get servlet validators
		String servletUrlString = req.getServletPath();
		List<AbstractParamValidator> validators = ValidateUtil.servletValidators
				.get(servletUrlString);

		if (validators != null) {
			List<String> errorCodes = new ArrayList<String>();
			// validate every
			for (AbstractParamValidator validator : validators) {
				try {
					errorCodes.addAll(validator.validate(req));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (!errorCodes.isEmpty()) {
				AbstractErrorResultVO vo = null;
				try {
					vo = (AbstractErrorResultVO) Class.forName(
							ValidateUtil.errorResultClass).newInstance();
					vo.setErrorCodes(errorCodes);

				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
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
