package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴 
// orm -> Java(다른 언어) Object -> 테이블로 매핑해주는 기술
@Entity // table 화 : User 클래스가 MySQL 테이블 생성
//@DynamicInsert // insert할 때 null인 필드 제외
public class User {
	
	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 프로젝트에서 연결된 DB(MySQL)의 넘버링 전략 따라감
	// MySQL : auto_increment(, oracle : 시퀀스)
	private int id; 
	
	@Column(nullable=false, length=100, unique=true)
	private String username; // 아이디
	
	@Column(nullable=false, length=100) // 1234 -> 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
//	private String role; // Enum을 쓰는게 좋음 // admin, user, manager -> 도메인(범위)를 정해줄 수 있음
//	@ColumnDefault("'user'") // 문자
	// DB에는 RoleType이라는 게 없다.
	@Enumerated(EnumType.STRING) // 해당 enum = string 타입이라고 표시
	private RoleType role;
	
	private String oauth; // kakao, googles
	
	@CreationTimestamp // 시간 자동 입력
	private Timestamp createDate;
	
}
