package com.naresh.petstore.security;

import org.springframework.util.Assert;

/**
 * 
 * @author Naresh Kodumoori
 * 
 * This implements a Threadlocal object to maintain the credentials of the current user making the request 
 */
public class CredentialsContextHolder {
	
	private static final ThreadLocal<CredentialsContext> contextHolder = new ThreadLocal<>();
	
	private CredentialsContextHolder(){
		
	}
	
	public static void clearConext(){
		contextHolder.remove();
	}
	
	public static CredentialsContext getContext(){
		return contextHolder.get();
	}
	
	public static void setContext(CredentialsContext context){
		Assert.notNull(context, "Only non-null CredentialsContext instances are permitted");
	}
	
	public static CredentialsContext createEmptyContext(){
		return new CredentialsContext();
	}
}
