package com.lunamaan.account.model;

import com.lunamaan.common.util.CommonUtil;

public class Naver {
	private String naverId;
	private String naverSecret;
	private String oauthUrl;
	private String callbackUrl;
	private String tokenUrl;
	private String profileUrl;
	
	
	
	public Naver(String naverId, String naverSecret, String oauthUrl, String callbackUrl, String tokenUrl,
			String profileUrl) {
		super();
		this.naverId = naverId;
		this.naverSecret = naverSecret;
		this.oauthUrl = oauthUrl;
		this.callbackUrl = callbackUrl;
		this.tokenUrl = tokenUrl;
		this.profileUrl = profileUrl;
	}

	public String getLoginUrl(String state){
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
	
	public String getTokenUrl(String state, String code){
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
	
	public String getProfileUrl(){
		return profileUrl;
	}
}
