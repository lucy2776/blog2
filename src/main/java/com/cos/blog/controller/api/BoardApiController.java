package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ReplySaveRequestDTO;
import com.cos.blog.dto.ResponseDTO;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.service.BoardService;

@RestController
public class BoardApiController{
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping("/api/board")
	public ResponseDTO<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.글작성(board, principal.getUser());
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); // 200, 1 => 정상
	}
	
	@PutMapping("/api/board/{id}")
	public ResponseDTO<Integer> update(@PathVariable int id, @RequestBody Board board){
		boardService.글수정하기(id, board);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/api/board/{id}")
	public ResponseDTO<Integer> deleteById(@PathVariable int id){
		boardService.글삭제하기(id);

		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}

	// 데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDTO<Integer> replySave(@RequestBody ReplySaveRequestDTO replySaveRequestDTO) {
//															@PathVariable int boardId,
//															@RequestBody Reply reply,
//															@AuthenticationPrincipal PrincipalDetail principal) {
//		boardService.댓글작성(principal.getUser(), boardId, reply);
		boardService.댓글작성(replySaveRequestDTO);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1); // 200, 1 => 정상
	}
	
	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDTO<Integer> replyDelete(@PathVariable int replyId){
		boardService.댓글삭제(replyId);
		
		return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
	}
	
}
