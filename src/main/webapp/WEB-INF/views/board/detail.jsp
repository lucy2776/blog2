<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
	<div class="card">

		<div class="card-header">
			<span style="font-size: 25px">${board.title }</span>
			<div class="pt-1 d-flex justify-content-end">
				<span style="font-size: 13px">작성자 : <i>${board.user.username } </i></span>
			</div>
		</div>

		<div class="m-3">
			<div class="list-group-item">${board.content }</div>
			<br />
			<div class="d-flex justify-content-between">
				<div>
					<p class="m-0" style="font-size: 12px">글 번호 : <i id="id">${board.id } </i></p>
					<p class="m-0" style="font-size: 13px">${board.createDate }</p>
				</div>
				<div>
					<button class="btn btn-secondary" onclick="history.back()">목록</button>
					&nbsp;
					<c:if test="${board.user.id==principal.user.id }">
						<a href="/board/${board.id }/updateForm" class="btn btn-warning" style="color:white">수정</a>
						&nbsp;
						<button id="btn-delete" class="btn btn-danger">삭제</button>
					</c:if>
				</div>
			</div>
		</div>

	</div>

	<hr />

	<div class="card">
		<div class="card-header">댓글</div>

		<ul class="list-group" id="reply-box">
			<c:forEach var="reply" items="${board.replys }">
				<li class="list-group-item" id="reply-${reply.id }">
					<div style="font-size: 12px">
						작성자 : <i>${reply.user.username }</i>&nbsp;
					</div>
					<div class="pt-1">${reply.content }</div>
					<c:if test="${reply.user.id eq principal.user.id }">
						<div class="d-flex justify-content-end">
							<button class="btn btn-danger badge" onClick="index.replyDelete(${board.id}, ${reply.id })">삭제</button>
						</div>
					</c:if>
				</li>
			</c:forEach>
		</ul>

	</div>

	<hr />

	<div class="card">
		<form action="">
			<input type="hidden" id="userId" value="${principal.user.id }" /> 
			<input type="hidden" id="boardId" value="${board.id }" />
			<div class="card-body d-flex justify-content-between">
				<textarea class="form-control" id="reply-content" rows="1" style="width: 580px; height: 37.6px;"></textarea>
				<button class="btn btn-primary" id="btn-reply-save">등록</button>
			</div>
		</form>
	</div>

</div>

<script src="	/js/board.js"></script>

<%@include file="../layout/footer.jsp"%>