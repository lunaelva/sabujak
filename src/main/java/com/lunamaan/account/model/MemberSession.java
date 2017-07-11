package com.lunamaan.account.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSession {
	private int id;
	private boolean login = false;
	private MemberType memberType;
	
}
