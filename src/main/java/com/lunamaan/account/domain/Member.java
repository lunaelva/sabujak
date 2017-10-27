package com.lunamaan.account.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Data 
@Getter
@Setter
@ToString
@Table(name="member")
public class Member {
	@Id
	@SequenceGenerator(name="seq", sequenceName="member_no_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	private long mId;
	@Column(name="member_social_Id")
	private String mSocialId;
	@Column(name="member_social_type")
	private int mSocialType;
	@Column(name="member_nickname",unique = true)
	private String nickname;
	@Column(name="grade")
	private int grade; //0-일반, 1-관리자
	@Column(name="reg_date")
	private LocalDateTime regDate;
	@Column(name="update_date")
	private LocalDateTime updateDate;
	
}
