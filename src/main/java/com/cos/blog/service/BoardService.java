package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDTO;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
//@RequiredArgsConstructor
public class BoardService {

	// @Autowired 원리
//	private BoardRepository boardRepository;
//	private ReplyRepository replyRepository;
//
//	public BoardService(BoardRepository bRepo, ReplyRepository rRepo) {
//		this.boardRepository = bRepo;
//		this.replyRepository = rRepo;
//	}
	
	// @Autowired 다른 방법
//	private final BoardRepository boardRepository; // final -> 초기화(= null)
//	private final ReplyRepository replyRepository; // @RequiredArgsConstructor -> 생성자 만들때 초기화가 필요한 생성자에 파라미터를 넣어서 초기화
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Transactional
	public void 글작성(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);

		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {

		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {

		return boardRepository.findById(id).orElseThrow(() -> {

			return new IllegalArgumentException("글 상세보기 실패 \n 아이디를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {

			return new IllegalArgumentException("글 찾기 실패 \n 아이디를 찾을 수 없습니다.");
		}); // 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료 시(Service 종료 시) 트랜젝션 종료(=더티체킹)
		// 자동 업데이트, DB flush
	}

	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}

//	@Transactional
//	public void 댓글작성(User user, int boardId, Reply requestReply) {
//		Board board = boardRepository.findById(boardId).orElseThrow(() -> {
//
//			return new IllegalArgumentException("댓글 작성 실패 \n 게시글 아이디를 찾을 수 없습니다.");
//		});
//		
//		requestReply.setUser(user);
//		requestReply.setBoard(board);
//	
//		replyRepository.save(requestReply);

//	@Transactional
//	public void 댓글작성(ReplySaveRequestDTO replySaveRequestDTO) {
	// DTO 예시 1)
//		User user = userRepository.findById(replySaveRequestDTO.getUserId()).orElseThrow(() -> {
//
//			return new IllegalArgumentException("댓글 작성 실패 \n 작성자 아이디를 찾을 수 없습니다.");
//		});
//		Board board = boardRepository.findById(replySaveRequestDTO.getBoardId()).orElseThrow(() -> {
//
//			return new IllegalArgumentException("댓글 작성 실패 \n 게시글 아이디를 찾을 수 없습니다.");
//		});
//		
//		Reply requestReply = Reply.builder()
//				.user(user)
//				.board(board)
//				.content(replySaveRequestDTO.getContent())
//				.build();

	// DTO 예시 2) (Reply)
//		Reply requestReply = new Reply();
//		requestReply.update(user, board, replySaveRequestDTO.getContent());

//		replyRepository.save(requestReply);

	@Transactional
	public void 댓글작성(ReplySaveRequestDTO replySaveRequestDTO) {
		replyRepository.mSave(replySaveRequestDTO.getUserId(), replySaveRequestDTO.getBoardId(), replySaveRequestDTO.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}

}
