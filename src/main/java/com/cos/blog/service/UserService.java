package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service // 스프링이 컴포넌트 스캔을 통해서 Bean에 등록 (IoC)
public class UserService {
	
	// Service
	// 1. 트랜젝션 관리
	// 2. 서비스 의미
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 1234 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword);
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
	} // 성공하면 commit 실패하면 rollback

//	@Transactional(readOnly = true)
//	// Select 할 때 트랜젝션 시작
//	// 서비스 종료 시에 트랜젝션 종료 (=> 정합성 유지)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
}
