package com.lunamaan.account.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lunamaan.common.util.CommonUtil;

@Component
public class NaverApiComponent{
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
	private String profile;
	
	public String makeLoginUrl(String state){
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(oauthUrl);
		urlBuilder.append("?response_type=code");
		urlBuilder.append("&");
		urlBuilder.append("client_id=");
		urlBuilder.append(naverId);
		urlBuilder.append("&");
		urlBuilder.append("redirect_uri=");
		urlBuilder.append(CommonUtil.encodeURIComponent(callbackUrl));
		urlBuilder.append("&");
		urlBuilder.append("state=");
		urlBuilder.append(state);

		return urlBuilder.toString();
	}
	
	public String makeTokenUrl(String state, String code){
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(tokenUrl);
		urlBuilder.append("?grant_type=authorization_code");
		urlBuilder.append("&");
		urlBuilder.append("client_id=");
		urlBuilder.append(naverId);
		urlBuilder.append("&");
		urlBuilder.append("client_secret=");
		urlBuilder.append(naverSecret);
		urlBuilder.append("&");
		urlBuilder.append("redirect_uri=");
		urlBuilder.append(CommonUtil.encodeURIComponent(callbackUrl));
		urlBuilder.append("&");
		urlBuilder.append("code=");
		urlBuilder.append(code);
		urlBuilder.append("&");
		urlBuilder.append("state=");
		urlBuilder.append(state);

		return urlBuilder.toString();
	}
	
	public String makeProfileUrl(){
		return profile;
	}
}
