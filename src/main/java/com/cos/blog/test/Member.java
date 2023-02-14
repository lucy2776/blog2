package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
//@RequiredArgsConstructor // final
@Data
//@AllArgsConstructor // all 생성자
@NoArgsConstructor // bean 생성자
public class Member {
	private int id;
	private String name;
	private String pw;
	private String email;
	
	@Builder
	public Member(int id, String name, String pw, String email) {
		this.id = id;
		this.name = name;
		this.pw = pw;
		this.email = email;
	}
	
}
