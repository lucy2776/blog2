package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController // return data(html x)
public class DummyControllerTest {
	
	@Autowired // 의존성 주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) { // Exception(부모) -> EmptyResultDataAccessException
			return "삭제에 실패하였습니다. 해당 id는 존재하지않습니다.";
		}
		
		return "삭제되었습니다. \n삭제된 ID : "+id;
	}
	
	// password, email 수정
	@Transactional // 더티 체킹 // 함수 종료 시 자동 commit
	@PutMapping("/dummy/user/{id}") // 요청이 다르기 때문에 주소가 같아도 알아서 구분
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { 
		// @RequestBody : json 데이터(key=value) 요청 받아오기(MessageConverter의 Jackson라이브러리가 자바 오브젝트로 변환해서 받아옴)
		// json 데이터를 받아오려면 form태그가 있어야 함
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
		
			return new IllegalArgumentException("수정에 실패하였습니다.");
		}); // 영속화
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
//		userRepository.save(user);
		// save 함수
		// 1. id를 전달하지 않을 때 : insert
		// 2. id를 전달할 때 - 해당 id에 대한 데이터가 있으면 : update
		// 3. id를 전달할 때 - 해당 id에 대한 데이터가 없으면 : insert
		
		return user;
	} // 변경 감지하고 commit -> 수정 : 더티 체킹
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		
		return userRepository.findAll();
	}
	
	// 한 페이지 당 2건의 데이터 리턴
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		// direction : 최신순
		Page<User> pagingUser = userRepository.findAll(pageable);
		
//		if(pagingUser.isFirst()) {
//			
//		}
//		if(pagingUser.isLast()) {
//			
//		}
		List<User> users = pagingUser.getContent();
		
		return pagingUser;
	}
	
	
	@GetMapping("/dummy/user/{id}") // {id} 주소로 파라미터를 전달받을 수 있음
	public User detail(@PathVariable int id) {
		// DB에서 id를 찾지 못하면 user = null => return null
		// Optional로 User객체 감싸서 가져와서 null인지 아닌지 판단해서 return
		
		// 1. .get() 
//		User user = userRepository.findById(id).get() // null x -> error
		// 2. .orElseGet()
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() { // return null
//			@Override
//			public User get() {
//				
//				return new User();
//			}
//		});
		//3. .orElseThrow
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {

				return new IllegalArgumentException("해당 유저는 존재하지 않습니다.\n검색한 id : " + id);
			}
		});
		// 람다식 표현
//		User user = userRepository.findById(id).orElseThrow(()->{
//		
//			return new IllegalArgumentException("해당 유저는 존재하지 않습니다.");
//		});
		
		// 요청 : 웹브라우저
		// user 객체 = 자바 오브젝트
		// 변환 (웹브라우저가 이해할 수 있는 데이터 -> json)
		
		// 스프링부트 = MessageConverter 응답 시에 자동 작동 : Jackson 라이브러리 호출 -> user 오브젝트를 json으로 변환해서 브라우저에 던져줌
		return user;
	}
	
	
//	@PostMapping("/dummy/join")
//	public String join(String username, String password, String email) { // key=value(약속된 규칙)
//		System.out.println("username : "+username);
//		System.out.println("password : "+password);
//		System.out.println("email : "+email);
//		
//		return "회원가입이 완료되었습니다.";
//	}
	@PostMapping("/dummy/join")
	public String join(User user) { // 폼 태그로 받아온 데이터
		System.out.println("id : "+user.getId()); // auto_incement -> 자동 추가
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole()); // null
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		
		return "회원가입이 완료되었습니다.";
	}
	
}
