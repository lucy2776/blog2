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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">

			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">Home</a>
			</div>

			<div class="collapse navbar-collapse" id="myNavbar">
				<c:choose>
					<c:when test="${empty principal }">
						<ul class="nav navbar-nav navbar-right">
							<li><a href="/auth/loginForm"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
							<li><a href="/auth/joinForm"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
						</ul>
					</c:when>
					<c:otherwise>
						<ul class="nav navbar-nav navbar-right">
							<li><a href="/board/saveForm"><span class="glyphicon glyphicon-user"></span> 글 작성</a></li>
							<li><a href="/user/updateForm"><span class="glyphicon glyphicon-user"></span> 회원정보</a></li>
							<li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
						</ul>
					</c:otherwise>
				</c:choose>
			</div>

		</div>
	</nav>

	<br />
	<br />