package com.lunamaan.account.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;

import com.lunamaan.account.model.Naver;

@Configuration
public class SocialConfig {
	@Value("${google.app.id}")
	private String googleId;
	@Value("${google.app.secret}")
	private String googleSecret;
	@Value("${google.callback.url}")
	private String googleCallback;
	@Value("${google.scope.url}")
	private String scopeUrl;
	@Value("${naver.app.id}")
	private String naverId;
	@Value("${naver.app.secret}")
	private String naverSecret;
	@Value("${naver.oauth.url}")
	private String oauthUrl;
	@Value("${naver.callback.url}")
	private String callbackUrl;
	@Value("${naver.token.url}")
	private String tokenUrl;
	@Value("${naver.profile.url}")
	private String profileUrl;
			
	@Bean("getGoogleConnectionFactory")
	public GoogleConnectionFactory  getGoogleConnectionFactory(){
		return new GoogleConnectionFactory(googleId, googleSecret);
	}
	
	@Bean("googleOAuth2Parameters")
	public OAuth2Parameters googleOAuth2Parameters(){
		OAuth2Parameters oAuth = new OAuth2Parameters();
		oAuth.setRedirectUri(googleCallback);
		oAuth.setScope(scopeUrl);

		return oAuth;
	}
		
	@Bean("naverConnectionFactory")
	public Naver getNaverFactory(){
		return new Naver(naverId, naverSecret, oauthUrl, callbackUrl, tokenUrl, profileUrl);
	}
	
}
