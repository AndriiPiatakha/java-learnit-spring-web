package com.itbulls.learnit.spring;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.itbulls.learnit.spring.aop.DemoAop;
import com.itbulls.learnit.spring.aop.DemoAspect;
import com.itbulls.learnit.spring.aop.User;
import com.itbulls.learnit.spring.interceptors.DemoHandlerInterceptor;
import com.itbulls.learnit.spring.security.DefaultAuthenticationFailureHandler;
import com.itbulls.learnit.spring.security.DefaultAuthenticationProvider;
import com.itbulls.learnit.spring.security.DefaultSuccessLogoutHandler;
import com.itbulls.learnit.spring.security.DefaultUserDetailsService;

@EnableWebMvc
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
@ComponentScan(basePackages = { "com.itbulls.learnit.spring" }, excludeFilters = 
		  {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = {DemoAop.class, DemoAspect.class, User.class})})
@EnableTransactionManagement
@EnableJpaRepositories("com.itbulls.learnit.spring.persistence.repositories.springdata")
@PropertySource("classpath:database.properties")
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

	// ================== SPRING SECURITY DEMO - first project

//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user1 = User.withUsername("user1").password(passwordEncoder().encode("test1")).roles("CUSTOMER")
//				.build();
//		UserDetails user2 = User.withUsername("user2").password(passwordEncoder().encode("test2")).roles("CUSTOMER")
//				.build();
//		UserDetails admin = User.withUsername("admin").password(passwordEncoder().encode("root")).roles("ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(user1, user2, admin);
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    	http.csrf()
//	        .disable()
//	        .authorizeHttpRequests()
//		        .requestMatchers("/test/admin/**")
//		        .hasRole("ADMIN")
//		        .requestMatchers("/test/anonymous*")
//		        .anonymous()
//		        .requestMatchers("/test/login_page*")
//		        .permitAll()
////		        .anyRequest()
////		        .authenticated()
//	        .and()
//		        .formLogin()
////		        .usernameParameter("email")		// in case you want to use different parameter 
////		        .passwordParameter("pass")
//		        .loginPage("/test/login_page")
//		        .loginProcessingUrl("/test/perform_login")
//		        .defaultSuccessUrl("/test/homepage", false)
//		        .failureUrl("/test/login_page?error=true")
//		        .failureHandler(authenticationFailureHandler())
//	        .and()
//		        .logout()
//		        .logoutUrl("/test/perform_logout")
//		        .logoutSuccessUrl("/test/login_page")
//		        .deleteCookies("JSESSIONID")
//		        .logoutSuccessHandler(logoutSuccessHandler());
//    	return http.build();
//    }

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new DefaultAuthenticationFailureHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new DefaultSuccessLogoutHandler();
	}

	// ============== Spring Security Roles and Privileges demo

//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    	http.csrf()
//	        .disable()
//	        .authorizeHttpRequests()
//		        .requestMatchers("/test/admin/**")
//		        .hasRole("ADMIN")
//		        .requestMatchers("/test/manager/**")
//		        .hasAuthority(SetupDataLoader.WRITE_PRIVILEGE)
////		        .hasRole("MANAGER")
//		        .requestMatchers("/test/anonymous*")
//		        .anonymous()
//		        .requestMatchers("/test/login_page*", "/test/user-registration-form-security-demo", "/test/create-user-security-demo")
//		        .permitAll()
//		        .anyRequest()
//		        .authenticated()
//	        .and()
//		        .formLogin()
//		        .loginPage("/test/login_page")
//		        .loginProcessingUrl("/test/perform_login")
//		        .defaultSuccessUrl("/test/homepage", false)
//		        .failureUrl("/test/login_page?error=true")
//		        .failureHandler(authenticationFailureHandler())
//	        .and()
//		        .logout()
//		        .logoutUrl("/test/perform_logout")
//		        .deleteCookies("JSESSIONID")
//		        .logoutSuccessHandler(logoutSuccessHandler());
//    	return http.build();
//    }

//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new DefaultUserDetailsService();
//	}

	// ===== Remember Me Configuration

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeHttpRequests().requestMatchers("/test/admin/**").hasRole("ADMIN")
//				.requestMatchers("/test/manager/**").hasAuthority(SetupDataLoader.WRITE_PRIVILEGE)
//				.requestMatchers("/test/anonymous*").anonymous()
//				.requestMatchers("/test/login_remember_me*", "/test/user-registration-form-security-demo",
//						"/test/create-user-security-demo")
//				.permitAll().anyRequest().authenticated().and().formLogin().loginPage("/test/login_remember_me")
//				.loginProcessingUrl("/test/perform_login").defaultSuccessUrl("/test/homepage", false)
//				.failureHandler(authenticationFailureHandler()).and().logout().logoutUrl("/test/perform_logout")
//				.deleteCookies("JSESSIONID").logoutSuccessUrl("/test/login_remember_me").and().rememberMe()
//				.key("superSecretKey").rememberMeParameter("remember") // it is name of checkbox at login page
//				.rememberMeCookieName("rememberlogin"); // it is name of the cookie
//		return http.build();
//	}

	// ===== Demo for the Custom Authentication Provider

	@Bean
	public UserDetailsService userDetailsService() {
		return new DefaultUserDetailsService();
	}

	@Bean
	public AuthenticationProvider authProvider() {
		return new DefaultAuthenticationProvider();
	}

	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authProvider());
		return authenticationManagerBuilder.build();
	}

	// ===== Spring Data JPA Configuration

	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_USERNAME = "user";
	private static final String PROPERTY_PASSWORD = "password";
	private static final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_DIALECT = "hibernate.dialect";

	@Autowired
	private Environment environment;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setUrl(environment.getProperty(PROPERTY_URL));
		ds.setUsername(environment.getProperty(PROPERTY_USERNAME));
		ds.setPassword(environment.getProperty(PROPERTY_PASSWORD));
		ds.setDriverClassName(environment.getProperty(PROPERTY_DRIVER));
		return ds;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
		lfb.setDataSource(dataSource());
		lfb.setPackagesToScan("com.itbulls.learnit.spring.persistence.entities");
		lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		lfb.setJpaProperties(hibernateProps());
		return lfb;
	}

	private Properties hibernateProps() {
		Properties properties = new Properties();
		properties.setProperty(PROPERTY_DIALECT, environment.getProperty(PROPERTY_DIALECT));
		properties.setProperty(PROPERTY_SHOW_SQL, environment.getProperty(PROPERTY_SHOW_SQL));
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
	
	
	
	// ===== Spring JDBC Configuration

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}
}