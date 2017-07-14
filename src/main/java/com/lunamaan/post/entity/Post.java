package com.lunamaan.post.entity;

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
@Table(name="post")
public class Post {
	@Id
	@SequenceGenerator(name="seq", sequenceName="post_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
	private long pId;	
	private int categoryId;
	private String title;
	@Lob()
	@Column(name="content")
	private String content;
	private String writer;
	private String updater;
	private LocalDateTime occTime;
	private LocalDateTime updTime;
}
