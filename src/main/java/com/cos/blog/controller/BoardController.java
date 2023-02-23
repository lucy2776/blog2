package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	// 컨트롤러에서 세션 찾기
	// @AuthenticationPrincipal PrincipalDetail principal
//	System.out.println("로그인 사용자 아이디 : "+principal.getUsername());
	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size=4, sort="id", direction=Sort.Direction.DESC) Pageable pageable) { 
		model.addAttribute("boards", boardService.글목록(pageable));
		
		return "index";
		// Controller에서 return할 때 viewResolver 작동
		// index를 return할 때 model의 정보를 가지고 감
		// jsp에서 model(data, collection)은 request정보
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		
		return "board/updateForm";
	}
	
	// USER 권한 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		
		return "board/saveForm";
	}
}
