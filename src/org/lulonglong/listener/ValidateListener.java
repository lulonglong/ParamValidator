package org.lulonglong.listener;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.lulonglong.base.validator.AbstractParamValidator;
import org.lulonglong.util.StringUtil;
import org.lulonglong.util.ValidateUtil;

/**
 * Application Lifecycle Listener implementation class ValidateListener
 * 
 */
public class ValidateListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public ValidateListener() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		String configLocation = servletContextEvent.getServletContext()
				.getInitParameter("validateConfigLocation");

		String returnType = servletContextEvent.getServletContext()
				.getInitParameter("returnType");
		ValidateUtil.returnType = StringUtil.isNullOrWhiteSpace(returnType) ? ValidateUtil.returnType
				: returnType;

		if (StringUtil.isNullOrWhiteSpace(configLocation)) {
			System.err.println("validateConfigLocation配置项不存在");
			return;
		}

		String configPath = servletContextEvent.getServletContext()
				.getRealPath(configLocation);

		SAXReader reader = new SAXReader();
		Map<String,String> map = new HashMap<String,String>(); 
        map.put("xmlns","http://www.example.net/test");
		reader.getDocumentFactory().setXPathNamespaceURIs(map);
		try {
			Document document = reader.read(new File(configPath));
			List<Element> servletValidators = document
					.selectNodes("/xmlns:validators/xmlns:servletValidator");

			// 读取每个servletValidator
			for (Element servletValidator : servletValidators) {

				String servletUrl = servletValidator.valueOf("@servletUrl");
				List<AbstractParamValidator> paramValidators = new ArrayList<AbstractParamValidator>();

				List<Element> validators = servletValidator
						.selectNodes("xmlns:validator");

				// 读取每个validator
				for (Element validator : validators) {

					String validatorClassName = validator
							.valueOf("@validatorClass");

					Class<?> validatorClass = Class.forName(validatorClassName);
					Object validatorInstance = validatorClass.newInstance();

					// 读取validator的所有参数
					List<Attribute> attributes = validator.attributes();
					for (Attribute attribute : attributes) {

						String attributeName = attribute.getName();
						if (attributeName.equals("validatorClass"))
							continue;

						String attributeValue = attribute.getValue();

						Method method = validatorClass.getMethod(
								StringUtil.getSetMethodString(attributeName),
								String.class);

						method.invoke(validatorInstance, attributeValue);

					}

					// 读取validator需要验证的参数
					List<Element> params = validator
							.selectNodes("xmlns:validateParam");
					for (Element element : params) {

						String nameString = element.valueOf("@name");
						String errorCode = element.valueOf("@errorCode");

						Method addParamMethod = validatorClass.getMethod(
								"addParam", String.class, String.class);

						addParamMethod.invoke(validatorInstance, nameString,
								errorCode);
					}
					paramValidators
							.add((AbstractParamValidator) validatorInstance);

				}
				ValidateUtil.servletValidators.put(servletUrl, paramValidators);

			}

			FilterRegistration filterRegistration = servletContextEvent
					.getServletContext().addFilter("ValidateFilter",
							"org.lulonglong.filter.ValidateFilter");
			filterRegistration.addMappingForUrlPatterns(null, true, "/*");

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
	}

}
