package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class PrincipalDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	// 스프링이 로그인 요청을 가로챌 때, username, password 변수 2개를 가로채는데
	// password 부분 처리는 알아서 함
	// username이 DB에 있는지만 확인
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// User 타입 : 시큐리티의 기본 id:user, password:console창 사용 x
		User principal = userRepository.findByUsername(username).orElseThrow(()->{
					
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. \n입력된 사용자 : "+username);
				});
		
		return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보 저장 (UserDetails 타입)
	}
}
