package com.cos.blog.service;

import java.util.EventListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
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
	private BCryptPasswordEncoder encoder; // 해쉬화
	
	@Transactional
	public User 회원찾기(String username) {
		User user = userRepository.findByUsername(username).orElseGet(()->{
			
			return new User(); // null x -> 빈 객체 리턴
		});
		
		return user; // null이 될 수도 있고 아닐 수도 있음. 
	}
	
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // 1234 원문
		String encPassword = encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword);
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
	} // 성공하면 commit 실패하면 rollback
	
	@Transactional
	public void 회원수정(User user) {
		// 수정 시 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문 날림
		User persistance = userRepository.findById(user.getId())
				.orElseThrow(()->{
					
					return new IllegalArgumentException("회원 찾기 실패");
				});
		
		// Validate 체크 -> oauth 필드에 값이 없으면 수정 가능
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		// 회원 수정 함수(Service, transaction) 종료 = commit(자동)
		// 영속화된 persistance 객체의 변화가 감지되면 더티체킹되어 update문 날림
	}

//	@Transactional(readOnly = true)
//	// Select 할 때 트랜젝션 시작
//	// 서비스 종료 시에 트랜젝션 종료 (=> 정합성 유지)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
	@Transactional
	public void 회원탈퇴(int id) {
		userRepository.deleteById(id);
	}
	
}
