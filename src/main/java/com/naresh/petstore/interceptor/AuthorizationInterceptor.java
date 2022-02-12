package com.naresh.petstore.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.naresh.petstore.model.Users;
import com.naresh.petstore.repository.UserRepository;
import com.naresh.petstore.security.CredentialsContext;
import com.naresh.petstore.security.CredentialsContextHolder;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

	private final UserRepository userRepository;	
	private final Environment env;
	
	
	public AuthorizationInterceptor(UserRepository userRepository, Environment env) {
		this.userRepository = userRepository;
		this.env = env;
	}
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		CredentialsContext credentialsContext = CredentialsContextHolder.createEmptyContext();
		CredentialsContextHolder.setContext(credentialsContext);
				
		logger.info("Deployment Profile is :" + env.getProperty("petstore.deployment.profile"));
		logger.info("Environment Profile is :" + env.getProperty("petstore.deployment.environment"));
		
		// Need to find the user, if the user is not there then we need to call the user service
		
		String userRoles = request.getHeader("UserGroups");
		String[] userRolesArray = StringUtils.split(userRoles, "^");
		List<String> rolesList = (List) Arrays.asList(userRolesArray);
		credentialsContext.setRoles(rolesList);
		
		String userId = request.getHeader("SM_USER");
		if(StringUtils.isBlank(userId)){
			throw new UsernameNotFoundException("Following User not found : " + userId);
		}
		
		Users userObj = userRepository.findByUserId(userId).orElse(null);
		if(null== userObj){
			Users newUser = new Users();
			newUser.setUserId(userId);
			newUser.setFirstName("Naresh");
			newUser.setLastName("Kodumoori");
			newUser.setEmail("NareshChinni.k@gmail.com");
			userObj = userRepository.addUser(newUser);
		} 
		
		credentialsContext.setUser(userObj);
		credentialsContext.setUserDetails(userObj);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}