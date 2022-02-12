package com.naresh.petstore.config;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.naresh.petstore.security.CustomUserDetailsService;
import com.naresh.petstore.security.RestAuthenticationEntryPoint;
import com.naresh.petstore.util.AppConstants;


@Configuration
@ComponentScan
@EnableWebSecurity
public class ChannelSecureConfig extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(ChannelSecureConfig.class);
	
	
	private final Environment env;
	private final ServletContext servletContext;
	private final CustomUserDetailsService customUserDetailsService;
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	
	public ChannelSecureConfig(Environment env, ServletContext servletContext,
			CustomUserDetailsService customUserDetailsService, RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
		super();
		this.env = env;
		this.servletContext = servletContext;
		this.customUserDetailsService = customUserDetailsService;
		this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		logger.info("Debug : ChannelSecureConfig.config() method");
		http.headers().contentSecurityPolicy("script-src 'self' https://trustedscritps.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-denpoint/");
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
		
		if(AppConstants.LOCAL_DEPLOYEMENT.equals(env.getProperty("petstore.deployment.profile"))){
			logger.debug("HTTP security enabled");
			http.authorizeRequests().antMatchers("/public").permitAll().anyRequest().authenticated().and().httpBasic()
			.authenticationEntryPoint(restAuthenticationEntryPoint).and().cors().and().csrf().disable();
		} else{
			logger.debug("NO-HTTP security enabled");
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and().authorizeRequests().anyRequest()
			.permitAll().and().cors().and().csrf().disable().headers().frameOptions().deny().xssProtection().block(true);
		}
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@ConditionalOnExpression("'${petstore.deployment.profile}'=='cloud' &&& '${petstore.security.chanelsecure.disable}'!='true'")
	@Bean
	public FilterRegistrationBean<Filter> createFilterRegistraionBean() throws Exception{
		logger.debug("FilterRegistrationBean creating");
		FilterRegistrationBean<Filter> filterRegBean = new FilterRegistrationBean<>();
		
		Map<String, String> initParametersMap = new HashMap<>();
		initParametersMap.put("gatewayUrl", env.getProperty("petstore.security.chanelsecure.gatewayUrl"));
		initParametersMap.put("enabled", "true");
		initParametersMap.put("plugin", "cs-idfcgateway");
		initParametersMap.put("proxyEnabe;", "false");
		initParametersMap.put("ssoZone;", "CSI");
		initParametersMap.put("sendRequestHeaders;", "host");
		
		filterRegBean.setInitParameters(initParametersMap);
		filterRegBean.addUrlPatterns("/*");
		
		try{
			filterRegBean.onStartup(servletContext);
			filterRegBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
		} catch(ServletException exp){
			logger.error("Getting exception while settng the servletContext and Dispatcher"+ exp.getMessage());
			throw exp;
		}

		logger.debug("FilterRegistrationBean created successfully");

		return filterRegBean;
		
		
	}
	
	
	

	



}



