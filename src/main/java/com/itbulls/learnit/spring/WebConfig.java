package com.itbulls.learnit.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.itbulls.learnit.spring.interceptors.DemoHandlerInterceptor;
import com.itbulls.learnit.spring.security.DefaultAuthenticationFailureHandler;
import com.itbulls.learnit.spring.security.DefaultSuccessLogoutHandler;

@EnableWebMvc
@EnableWebSecurity
@Configuration
@ComponentScan(basePackages = { "com.itbulls.learnit.spring" })
@PropertySources({ @PropertySource("classpath:test.properties"), @PropertySource("classpath:test2.properties") })
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

	@Bean("messageSource")
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("msg");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new DemoHandlerInterceptor()).addPathPatterns("/test/simple-model-demo");

		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**", "/js/**").addResourceLocations("/css/", "/js/");
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

	// ================== SPRING SECURITY DEMO

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("test1")).roles("CUSTOMER")
				.build();
		UserDetails user2 = User.withUsername("user2").password(passwordEncoder().encode("test2")).roles("CUSTOMER")
				.build();
		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("root")).roles("ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user1, user2, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.csrf()
	        .disable()
	        .authorizeHttpRequests()
		        .requestMatchers("/test/admin/**")
		        .hasRole("ADMIN")
		        .requestMatchers("/test/anonymous*")
		        .anonymous()
		        .requestMatchers("/test/login_page*")
		        .permitAll()
//		        .anyRequest()
//		        .authenticated()
	        .and()
		        .formLogin()
//		        .usernameParameter("email")		// in case you want to use different parameter 
//		        .passwordParameter("pass")
		        .loginPage("/test/login_page")
		        .loginProcessingUrl("/test/perform_login")
		        .defaultSuccessUrl("/test/homepage", false)
		        .failureUrl("/test/login_page?error=true")
		        .failureHandler(authenticationFailureHandler())
	        .and()
		        .logout()
		        .logoutUrl("/test/perform_logout")
		        .logoutSuccessUrl("/test/login_page")
		        .deleteCookies("JSESSIONID")
		        .logoutSuccessHandler(logoutSuccessHandler());
    	return http.build();
    }

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new DefaultAuthenticationFailureHandler();
	}
	
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new DefaultSuccessLogoutHandler();
	}

}