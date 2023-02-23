package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDTO;

@ControllerAdvice //Exception이 발생하면 이 클래스로 옴
@RestController
public class GlobalExceptionHandler {

//	@ExceptionHandler(value=IllegalArgumentException.class) 
//	// IllegalArgumentException이 발생하면 그에 대한 에러가 전달되는 함수
//	public String handleArgumentException(IllegalArgumentException e) {
//		
//		return "<h1>"+e.getMessage()+"</h1>";
//	}
	@ExceptionHandler(value=Exception.class) 
	public ResponseDTO<String> handleArgumentException(Exception e) {
		
		return new ResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()); // 500
	}
	
}
