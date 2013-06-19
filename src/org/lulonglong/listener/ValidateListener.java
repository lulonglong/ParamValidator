package org.lulonglong.listener;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.lulonglong.base.validator.AbstractParamValidator;
import org.lulonglong.util.ServletValidatorsMap;
import org.lulonglong.util.StringUtil;

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

		if (StringUtil.isNullOrWhiteSpace(configLocation)) {
			System.err.println("没找到validateConfigLocation配置文件");
			return;
		}

		String configPath = servletContextEvent.getServletContext()
				.getRealPath(configLocation);

		SAXReader reader = new SAXReader();

		try {
			Document document = reader.read(new File(configPath));
			List<Element> servletValidators = document
					.selectNodes("/validators/servletValidator");

			// 取到所有的servlet
			for (Element servletValidator : servletValidators) {

				String servletUrl = servletValidator.valueOf("@servletUrl");
				List<AbstractParamValidator> paramValidators = new ArrayList<AbstractParamValidator>();

				List<Element> validators = servletValidator
						.selectNodes("validator");

				// 取到每个servlet对应的所有validator
				for (Element validator : validators) {

					paramValidators.clear();
					String validatorClassName = validator
							.valueOf("@validatorClass");

					Class<?> validatorClass = Class.forName(validatorClassName);
					Object validatorInstance = validatorClass.newInstance();

					// 调用每个validator上所对应的设置方法
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

					// 设置validator需要验证的所有参数
					List<Element> params = validator
							.selectNodes("validateParam");
					for (Element element : params) {

						String nameString = element.valueOf("@name");
						String errorCode = element.valueOf("errorCode");
						
						Method addParamMethod = validatorClass.getMethod(
								"addParam", String.class, String.class);

						addParamMethod.invoke(validatorInstance, nameString,
								errorCode);
					}
					paramValidators
							.add((AbstractParamValidator) validatorInstance);

				}
				ServletValidatorsMap.servletValidators.put(servletUrl,
						paramValidators);

			}
			System.out.println(ServletValidatorsMap.servletValidators.size());
			servletContextEvent.getServletContext().addFilter("ValidateFilter", "org.lulonglong.filter.ValidateFilter");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

}
