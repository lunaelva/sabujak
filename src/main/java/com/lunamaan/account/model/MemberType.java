package com.lunamaan.account.model;

public enum MemberType {
	GOOGLE(0),FACEBOOK(1), NAVER(2);
	
	private int value;

	public int getValue() {
		return value;
	}
	
	private MemberType(int value){
		this.value = value;
	}
	
	public static MemberType valueOf(int key) {
		MemberType result = null;
		for (MemberType memberType : values()) {
			if (memberType.getValue() == key) {
				result = memberType;
			}
		}
		return result;
	}

	public static MemberType getMemberType(String socialDomain){
		if(socialDomain.equals("google")){
			return MemberType.GOOGLE;
		}else if(socialDomain.equals("facebook")){
			return MemberType.FACEBOOK;
		}else if(socialDomain.equals("naver")){
			return MemberType.NAVER;
		}else{
			return null;
		}
	}
}
