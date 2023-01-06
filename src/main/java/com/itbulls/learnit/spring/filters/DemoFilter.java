package com.itbulls.learnit.spring.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


@WebFilter("/test/*")
public class DemoFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println(filterConfig);
		// Filter Config can be used to get Servlet Context or init parameters of the Filter
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Do Filter");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("Destroy method invocation from Web Filter");
	}


}
