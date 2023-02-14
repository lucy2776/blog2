package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답(HTML파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpControllerTest : ";

	@GetMapping("/http/lombok")
	public String lombokTest() {
//		Member m = new Member("ssar", "1234", "email");
		// 생성자를 통해서 넣을 때 -> 순서 o
		Member m = Member.builder().name("ssar").pw("1234").email("ssar@nate.com").build();
		// builder 패턴 : 순서 x
		
		System.out.println(TAG+"getter : "+m.getName());
		m.setName("ssar2");
		System.out.println(TAG+"setter : "+m.getName());
		
		return "lombok test 완료";
	}
	
	// 인터넷 브라우저 요청은 무조건 get요청 밖에 할 수 없다.
//	@GetMapping("/http/get") // select
//	public String getTest(@RequestParam int id,
//										@RequestParam String name
//				
//		return "get 요청 : " + id + ", " + name;
//	}
	@GetMapping("/http/get")
	public String getTest(Member m) {
		
		return "get 요청 : " + m.getId()+", " + m.getName() + ", " + m.getPw() + ", " + m.getEmail();
	}
	
//	@PostMapping("/http/post") // insert // text/plain, application/json
//	public String postTest(Member m) {
//		
//		return "post 요청 : " + m.getId()+", " + m.getName() + ", " + m.getPw() + ", " + m.getEmail();
//	}
//	@PostMapping("/http/post")
//	public String postTest(@RequestBody text) {
//		
//		return "post 요청 : " + text;
//	}
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { 
		// MessageConverter(스프링부트) : object 그대로 맵핑해서 받음
		
		return "post 요청 : " + m.getId()+", " + m.getName() + ", " + m.getPw() + ", " + m.getEmail();
	}
	
	@PutMapping("/http/put") // update
	public String putTest(@RequestBody Member m) {
		
		return "put 요청 : " + m.getId()+", " + m.getName() + ", " + m.getPw() + ", " + m.getEmail();
	}
	
	@DeleteMapping("/http/delete") // delete
	public String deleteTest() {
		
		return "delete 요청";
	}
}
