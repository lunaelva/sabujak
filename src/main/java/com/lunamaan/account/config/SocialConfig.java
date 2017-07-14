package com.lunamaan.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;

@Configuration
public class GoogleSocialConfig {
	@Value("${google.app.id}")
	private String googleId;
	@Value("${google.app.secret}")
	private String googleSecret;
	@Value("${google.callback.url}")
	private String callbackUrl;
	@Value("${google.scope.url}")
	private String scopeUrl;
			
	@Bean("getGoogleConnectionFactory")
	public GoogleConnectionFactory  getGoogleConnectionFactory(){
		return new GoogleConnectionFactory(googleId, googleSecret);
	}
	
	@Bean("googleOAuth2Parameters")
	public OAuth2Parameters googleOAuth2Parameters(){
		OAuth2Parameters oAuth = new OAuth2Parameters();
		oAuth.setRedirectUri(callbackUrl);
		oAuth.setScope(scopeUrl);

		return oAuth;
	}
		
}
