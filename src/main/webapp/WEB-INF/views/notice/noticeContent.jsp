<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("lf", "\n"); %>
	
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>공지 내용 페이지</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/noticeContent.css">
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
</head>

<body>
	<div id="header">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
	</div>

	<div class="notice-container">
		<div class="notice-inner">
			<p class="notice-inner-text">공지사항</p>
		</div>
		<div class="divider"></div>
		<div class="notice-content">
			<div class="notice-title">${notice.noticeTitle}</div>
			<div class="notice-info">
				<div class="notice-date-text">작성일</div>
				<div class="notice-content-divider"></div>
       			<div class="notice-date"><fmt:formatDate value="${notice.noticeRegDate}" pattern="yyyy-MM-dd"/></div>
				<div class="notice-hitcount-text">조회</div>
				<div class="notice-content-divider"></div>
				<div class="notice-hitcount">${notice.noticeHitcount}</div>
			</div>
			<div class="notice-divider"></div>
			<div class="notice-item">
				<div class="content">${fn:replace(notice.noticeContent, lf, "<br/>")}</div>
			</div>
		</div>
		<div class="notice-divider"></div>
		<button class="notice-list-btn">목록 보기</button>
	</div>
	
	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	
</body>
</html>