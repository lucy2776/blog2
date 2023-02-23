package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입할 수 있는 경로 /auth/** 허용
// 주소 : / -> index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {
		// Post 방식으로 key=value 데이터를 요청(카카오로)
		// HttpsURLConnection
		// 라이브러리
		// Retrofit2 (안드로이드)
		// OkHttp
		// RestTemplate
		
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "02aea87274170b72fa8601a1eccc2965");
		params.add("redirect_uri", "http://localhost:8091/auth/kakao/callback");
		params.add("code", code);
		// 데이터는 변수에 담아서 사용
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);
		
		// Http 요청하기 - Post 방식으로 - response 변수의 응답 받음
		ResponseEntity<String> response = rt.exchange(
					"https://kauth.kakao.com/oauth/token",
					HttpMethod.POST,
					kakaoTokenRequest,
					String.class
				);
		
		// Gson, Json, Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : "+ oauthToken.getAccess_token());
		
		//
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - Post 방식으로 - response 변수의 응답 받음
		ResponseEntity<String> response2 = rt2.exchange(
					"https://kapi.kakao.com/v2/user/me",
					HttpMethod.POST,
					kakaoProfileRequest2,
					String.class
				);
		
		// Gson, Json, Simple, ObjectMapper
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// User 오브젝트 : username, password, email
		System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
		System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 username : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그서버 email : "+kakaoProfile.getKakao_account().getEmail());
//		UUID garbagePassword = UUID.randomUUID();
		// UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘(로그인할 때마다 바뀜)
//		System.out.println("블로그서버 password : "+garbagePassword); // 유효 아이디 값
		System.out.println("블로그서버 password : "+cosKey);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		// 가입자 혹은 비가입자 체크해서 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("신규 회원입니다.");
			System.out.println("자동 회원가입합니다.");
			
			userService.회원가입(kakaoUser);
		}
		
		System.out.println("자동 로그인 됐습니다.");
		
		// 로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
//		return "카카오 인증 완료 - 코드값 : "+code; 
		// code값을 이용해 카카오 리소스서버에 등록된 개인정보에 접근할 수 있는 엑세스 토큰 부여받음
//		return response2.getBody(); // 토큰 벗기기 
		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
		
		return "user/updateForm";
	}
	
}

/*

카카오 api 클라이언트 키
	02aea87274170b72fa8601a1eccc2965

웹 서버 주소 : http://localhost:8091
	login : http://localhost:8091/auth/kakao/callback
	logout : http://localhost:8091/auth/kakao/logout
 
카카오 동의 구성
	User 오브젝트 : id(번호), username, password, email
	카카오로부터 받을 정보 : profile정보(필수), email(선택)
 
로그인 요청 주소 (해당 주소로 클라이언트 id는 = , 해당 uri로 (타입은 코드) 코드 발급)(Get)
https://kauth.kakao.com/oauth/authorize?
         client_id=02aea87274170b72fa8601a1eccc2965&
         redirect_uri=http://localhost:8091/auth/kakao/callback&
         response_type=code

응답받은 코드
http://localhost:8091/auth/kakao/callback?code= ~

토큰 발급 요청 주소(Post) - http body에 데이터 전달(5가지 데이터를 담음)
주소 : https://kauth.kakao.com/oauth/token
		헤더 값
	         Content-type(MIME) = application/x-www-form-urlencoded;charset=utf-8 (key=value)
         바디 데이터 : 
	         grant_type = authorization_code
	         client_id = 02aea87274170b72fa8601a1eccc2965
	         redirect_uri = http://localhost:8091/auth/kakao/callback
	         code = {동적임}
         
토큰을 통한 사용자 정보 조회(Post)
요청 주소 : https://kapi.kakao.com/v2/user/me
	헤더 값
		Authorization : Bearer {access_token}
		Content-type : application/x-www-form-urlencoded;charset=utf-8

 */
