package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 파일을 리턴
public class TempControllerTest {
	
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴 기본경로 : src/main/resources/static
		// 리턴 명 : /home.html
		// 풀 경로 : src/main/resources/static/home.html
		
		// static : 정적 파일 (브라우저가 인식할 수 있는 파일)
		
//		return "home.html";
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		
		return "/03.jpg";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		// prefix : /WEB-INF/views/
		// suffix : .jsp
		// 풀네임 : /WEB-INF/views/test.jsp
		
		return "/test";
	}
	
}
