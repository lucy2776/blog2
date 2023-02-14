package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDTO;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController{
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
//	@Autowired
//	private HttpSession session;

	@PostMapping("/auth/joinProc")
	public ResponseDTO<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController : save 호출");
		// 실제로 DB에서 insert하고 return
//		user.setRole(RoleType.USER);
		
		UserService.회원가입(user);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환해서 반환
	}
	
//	@PostMapping("api/user/login")
//	public ResponseDTO<Integer> login(@RequestBody User user){
//		System.out.println("UserApiController : login 호출");
//		User principal = UserService.로그인(user); // principal : 접근주체
//		
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		
//		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
//	}
	
	
	
}
