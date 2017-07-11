package com.lunamaan.account.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
	
	@Lob
	@Column(name="social_Id",unique = true )
	private String mSocialId;
	@Column(name="member_type",unique = true )
	private int mType; 
	private int status;
	private LocalDateTime regDate;
	private LocalDateTime updateDate;
	
}
