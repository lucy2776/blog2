package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인 됨
	
//	@ColumnDefault("0") // 숫자 : '' x
	private int count; // 조회수

	@ManyToOne(fetch = FetchType.EAGER) 
	// Board = Many, User = One -> 하나의 유저에 여러 개의 테이블
	// ManyToOne의 기본 전략 : EAGER -> 무조건 호출(Board의 테이블에 생성되는 FK)
	@JoinColumn(name="userId")
	private User user; // = one 
	// DB는 오브젝트 저장x. FK, 자바는 오브젝트 저장o
	
	@OneToMany(mappedBy="board", fetch = FetchType.LAZY)
	// 하나의 게시글에 여러 개의 답변을 달 수 있음
	// mappedBy : 연관관계의 주인 x (FK x) -> DB 칼럼 만들지 x
	// OneToMany의 기본 전략 : LAZY -> 필요할 때만 호출해서 쓸 수 있음(Board의 테이블에 생성되는 FK x)
//	@JoinColumn(name="replyId")
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
