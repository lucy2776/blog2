package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reply {
	
	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 프로젝트에서 연결된 DB(MySQL)의 넘버링 전략 따라감
	// MySQL : auto_increment(, oracle : 시퀀스)
	private int id; 
	
	@Column(nullable=false, length=200)
	private String content;
	
	@ManyToOne // 하나의 게시글에 여러 개의 답변을 달 수 있음
	@JoinColumn(name="boardId")
	private Board board; // = one
	
	@ManyToOne // 하나의 유저가 여러 개의 답변을 달 수 있음
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
