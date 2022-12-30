package com.itbulls.learnit.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.itbulls.learnit.spring" })
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();

		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/view/");
		bean.setSuffix(".jsp");

		return bean;
	}

	@Bean
	public HandlerExceptionResolver errorHandler() {
		return new HandlerExceptionResolver() {
			@Override
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
					Object handler, Exception ex) {
				ModelAndView model = new ModelAndView("error-page");
				model.addObject("exception", ex);
				model.addObject("handler", handler);
				return model;
			}
		};
	}

	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**", "/js/**")
				.addResourceLocations("/css/", "/js/");
	}

	// Mapping of resources from the file system
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	    registry
//	      .addResourceHandler("/images/**")
//	      .addResourceLocations("file:C:\\images\\");
//	 }
	
	
	// Mapping multiple locations
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//	    registry
//	      .addResourceHandler("/resources/**")
//	      .addResourceLocations("/resources/","classpath:/other-resources/");
//	}
	
	// PathResourceResolver Example 
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/resources/**")
//				.addResourceLocations("/resources/", "/other-resources/")
//				.setCachePeriod(3600)
//				.resourceChain(true)
//				.addResolver(new PathResourceResolver());
//	}
	
	// EncodedResourceResolver Example 
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/other-files/**")
//				.addResourceLocations("file:C:\\other-files\\")
//				.setCachePeriod(3600)
//				.resourceChain(true)
//				.addResolver(new EncodedResourceResolver());
//	}
	
	
	// Chaining Resources Example
//	@Override public void addResourceHandlers(ResourceHandlerRegistry registry) { 
//		registry.addResourceHandler("/js/**")
//				.addResourceLocations("/js/")
//				.setCachePeriod(3600)
//				.resourceChain(true)
//				.addResolver(new GzipResourceResolver())
//				.addResolver(new PathResourceResolver()); 
//	}
	
}