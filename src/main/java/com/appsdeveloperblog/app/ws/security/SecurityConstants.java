package com.appsdeveloperblog.app.ws.security;

import com.appsdeveloperblog.app.ws.SpringApplicationContext;

public class SecurityConstants {
	
	
	public static final long EXPIRATION_TIME = 864000000; //10 days

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL= "/users";
	public static final String VERIFICATION_EMAIL_URL= "/users/email-verification";
	
	
	// AMAZON SES ACCSESS AND SECRET KEY
	public static final String ACCESS_KEY = "AKIA2EO26A42FS25DGXD";
	public static final String SECRET_KEY= "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";
	
	public static String getTokenSecret() {
		
		AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
		return appProperties.getTokenSecret();
	}

}
