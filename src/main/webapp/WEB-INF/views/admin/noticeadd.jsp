<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>공지사항 등록 및 수정</title>
	<link href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/noticeadd.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<%@ include file="mainadmin.jsp" %>
	<div class="admin-content">
		<form class="container-form" method="post" 
			<c:if test="${param.pageUsage != '수정'}">action="addNotice"</c:if>
			<c:if test="${param.pageUsage == '수정'}">action="updateNotice"</c:if>
			onsubmit="return checkValid()">
			<c:if test="${param.pageUsage == '수정'}">
				<input type="hidden" name="noticeId" value="${notice.noticeId}">
			</c:if>
			<div class="top-text-margin">
				공지사항 > <span class="text-success"><b>공지사항 등록</b></span>
			</div>
			<h2 class="top-text-margin">
				<b>공지사항 등록</b>
			</h2>
			<div class="form-group">
				<div class="form-title">제목 [30자 이하]</div> 
				<input id="noticeTitle" name="noticeTitle" type="text" placeholder="ex) 그리너리 서버 점검 안내 (8/21)" maxlength="29" value="${notice.noticeTitle}">
				<div id="charCount"><span class="titleNum">0</span> / 30</div>
			</div>
			<div class="form-group">
				<div class="form-title">내용</div>
			</div>
			<div class="container-textarea">
				<textarea id="noticeContent" name="noticeContent" class="textarea" rows="5" placeholder="공지사항에 대한 내용을 입력하세요.">${notice.noticeContent}</textarea>
			</div>
			<div class="btn-register-div">
				<button type="submit" class="btn-register">등록</button>
			</div>
		</form>
	</div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/noticeadd.js"></script>

</body>
</html>
