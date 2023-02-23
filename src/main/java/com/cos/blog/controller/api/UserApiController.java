package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDTO;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/auth/joinProc")
	public ResponseDTO<Integer> save(@RequestBody User user) { // username, password, email
		// 실제로 DB에서 insert하고 return
//		user.setRole(RoleType.USER);
		
		userService.회원가입(user);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 JSON으로 변환해서 반환
	}
	
	@PutMapping("/user")
	public ResponseDTO<Integer> update(@RequestBody User user, @AuthenticationPrincipal PrincipalDetail principal){ 
		// @RequestBody : key=value(x-www-form-urlencoded) => json data로 받음
		userService.회원수정(user);
		// 트랜젝션 종료 -> DB값 변경 / 세션 값은 변경x

		// 세션 등록(값 변경)
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseDTO<Integer> deleteById(@PathVariable int id){
		userService.회원탈퇴(id);

		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
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
