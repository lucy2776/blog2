<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal" />
</sec:authorize>
<!-- isAuthenticated() : 로그인 정보 확인 -->

<!DOCTYPE html>
<html lang="en">
<head>
<title>JE Blog</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>

</head>
<body>

	<nav class="navbar navbar-expand-sm bg-dark navbar-dark d-flex justify-content-between">
		<a class="navbar-brand" href="/">Home</a>
		<c:choose>
			<c:when test="${empty principal }">
				<ul class="navbar-nav">
					<li><a href="/auth/loginForm" class="nav-link">Login</a></li>
					<li><a href="/auth/joinForm" class="nav-link">Sign Up</a></li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul class="navbar-nav">
					<li><a href="/board/saveForm" class="nav-link">✏글 작성</a></li>
					<li><a href="/user/updateForm" class="nav-link">⚙회원정보</a></li>
					<li><a href="/logout" class="nav-link">Logout</a></li>
				</ul>
			</c:otherwise>
		</c:choose>
	</nav>

	<br />
	<br />