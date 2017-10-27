package com.lunamaan.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSession {
	private int id;
	private boolean login = false;
	private SocialType socialType;
	private String socialId;
	private int memberGrade;
}
