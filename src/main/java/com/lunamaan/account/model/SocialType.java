package com.lunamaan.account.model;

public enum SocialType {
	GOOGLE(0),FACEBOOK(1), NAVER(2);
	
	private int value;

	public int getValue() {
		return value;
	}
	
	private SocialType(int value){
		this.value = value;
	}
	
	public static SocialType valueOf(int key) {
		SocialType result = null;
		for (SocialType socialType : values()) {
			if (socialType.getValue() == key) {
				result = socialType;
			}
		}
		return result;
	}

	public static SocialType getSocialType(String socialDomain){
		if(socialDomain.equals("google")){
			return SocialType.GOOGLE;
		}else if(socialDomain.equals("facebook")){
			return SocialType.FACEBOOK;
		}else if(socialDomain.equals("naver")){
			return SocialType.NAVER;
		}else{
			return null;
		}
	}
}
