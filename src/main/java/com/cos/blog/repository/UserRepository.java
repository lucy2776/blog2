package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

//@Repository // 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
	// JpaRepository = User table이 관리하는 repository, PK = int(숫자)
	// 모든 함수를 가질 수 있음
	// 자동으로 bean 등록
	
	// SELECT * FROM user WHERE username = ?;
	Optional<User> findByUsername(String username);
	
}

	// JPA Naming 쿼리 전략
//	User findByUsernameAndPassword(String username, String password);
	// JPA 함수 x
	// return : User 타입
	// SELECT * FROM user WHERE username = ? AND password = ?; 쿼리 실행
	
	// 다른 방법
//	@Query(value="SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery=true)
//	User login(String username, String password);
