<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<input type="hidden" id="id" value="${principal.user.id }">
		<div class="form-group">
			<label for="username">Username</label> <input type="text" value="${principal.user.username }" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>

		<c:if test="${empty principal.user.oauth }">
			<div class="form-group">
				<label for="password">Password</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>

			<div class="form-group">
				<label for="email">Email address</label> <input type="email" value="${principal.user.email }" class="form-control" placeholder="Enter email" id="email">
			</div>

			<div class="d-flex justify-content-end">
				<button id="btn-delete" class="btn btn-danger">탈퇴</button>
				<button id="btn-update" class="btn btn-warning" style="color: white">수정</button>
			</div>
		</c:if>

		<c:if test="${not empty principal.user.oauth }">
			<div class="form-group">
				<label for="email">Email address</label> <input type="email" value="${principal.user.email }" class="form-control" placeholder="Enter email" id="email" readonly>
			</div>
			
			<div class="d-flex justify-content-end">
				<button id="btn-delete" class="btn btn-danger">탈퇴</button>
			</div>
		</c:if>

	</form>

</div>

<script src="/js/user.js"></script>

<%@include file="../layout/footer.jsp"%>