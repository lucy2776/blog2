package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	
	// @AuthenticationPrincipal PrincipalDetail principal
//	System.out.println("로그인 사용자 아이디 : "+principal.getUsername());
	@GetMapping({"", "/"})
	public String index() { // 컨트롤러에서 세션 찾기
		
		return "index";
	}
	
	// USER 권한 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		
		return "board/saveForm";
	}
}
