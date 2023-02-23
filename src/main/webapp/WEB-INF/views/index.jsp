<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="layout/header.jsp"%>

<%@include file="layout/banner.jsp"%>

<div class="container">
	<c:forEach var="board" items="${boards.content }">
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title }</h4>
				<div class="pt-1 d-flex justify-content-between">
					<div>
						<p class="m-0" style="font-size: 13px">
							작성자 : <i>${board.user.username } </i>
						</p>
						<p class="m-0" style="font-size: 13px">${board.createDate }</p>
					</div>
					<a href="/board/${board.id }" class="btn btn-primary">상세보기</a>
				</div>
			</div>
		</div>
		<br />
	</c:forEach>

	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${boards.first }">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1 }">◀</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number-1 }">◀</a></li>
			</c:otherwise>
		</c:choose>
		<div class="mt-2">
			<span class="page-item">
				&nbsp;&nbsp;&nbsp;
					<span>${boards.number +1 }</span> 
					/ 
					${boards.size -1 }
				&nbsp;&nbsp;&nbsp;
			</span>
		</div>
		<c:choose>
			<c:when test="${boards.last }">
				<li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1 }">▶</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number+1 }">▶</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	<!-- ⏩⏪ -->
</div>


<script src="	/js/board.js"></script>

<%@include file="layout/footer.jsp"%>