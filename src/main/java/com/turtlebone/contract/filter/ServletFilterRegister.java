package com.turtlebone.contract.filter;

import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ServletFilterRegister {

	@Autowired
	private Environment env;

	@Bean
	public FilterRegistrationBean registrationAuthenticationFilter() throws ServletException {
		DemoFilter authenticationFilter = new DemoFilter();
		FilterRegistrationBean registration = new FilterRegistrationBean(authenticationFilter);
		registration.setOrder(1);

		return registration;
	}

}
