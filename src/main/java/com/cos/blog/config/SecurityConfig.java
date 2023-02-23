package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

// 시큐리티 3종  세트
@Configuration // bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는것 (IoC 관리)
@EnableWebSecurity // 시큐리티 필터 등록 = 해당 파일에서 설정 // 컨트롤러 함수 실행하기 전에 검사
@EnableGlobalMethodSecurity(prePostEnabled=true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManager();
	}

	@Bean // IoC (스프링이 관리, 필요할 때마다 가져와서 쓰면 됨)
	public BCryptPasswordEncoder encodePWD() {
		
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			// 방법
			// 1. Referrer 검증 : 같은 도메인 상에서 요청
			// 2. csrf 토큰 : 세션에 토큰 저장하여 요청할 때마다 토큰 전송하여 정상적이 사용자 확인 (토큰x -> 외부 공격 차단)
			.csrf().disable() // csrf 토큰 비활성화 (테스트 시 .disable())
			.authorizeRequests() // 어떤 요청이 들어오면
//				.antMatchers("/auth/loginForm", "/auth/joinForm")
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") // 이 경로는 인증없이 들어올 수 있음
				.permitAll()
				.anyRequest() // 다른 모든 요청은
				.authenticated() // 인증이 돼야 됨
			.and()
				.formLogin() // 인증이 필요한 경로 요청은
				.loginPage("/auth/loginForm") // 로그인폼 연결
				.loginProcessingUrl("/auth/loginProc") 
				// 스프링 시큐리티가 해당 주소로  요청오는 로그인 가로채서 대신 로그인
				// PrincipalDetailService -> loadUserByUsername 함수 
				.defaultSuccessUrl("/"); // 인증 성공하면 이동할 url
//				.failureUrl() // 실패하면 이동할 url
	}
	
}

// 시큐리티 기본 설정 : 해쉬 암호화 해야 로그인 진행 (고정길이의 문자열로 변경)
// 		안녕 -> ABC365FC
// 		안녕 -> ABC365FC
// 		안녕! -> 3671AF28 (완전 다른 암호 // 고정길이 난수 해쉬)
// 		엄청 두꺼운 책(300p) -> 896F3A1B ( // 고정길이 난수 해쉬) (원본)
//			=> 내용 변경(250p) -> 375FBA19 (원본과 비교해서 위조 확인 가능)