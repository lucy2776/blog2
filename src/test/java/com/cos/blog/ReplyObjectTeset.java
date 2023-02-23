package com.cos.blog;


import org.junit.Test;

import com.cos.blog.model.Reply;

public class ReplyObjectTeset {
	@Test
	public void 투스트링테스트() {
		Reply reply = Reply.builder()
				.id(1)
				.user(null)
				.board(null)
				.content("Hi")
				.build();
		
		System.out.println(reply); // 오브젝트 출력 시 -> toString() 자동 호출
	}
}
