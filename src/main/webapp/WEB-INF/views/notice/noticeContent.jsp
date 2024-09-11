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
			<p class="notice-title">공지사항</p>
		</div>
		<div class="divider"></div>
		<div class="notice-content">
			<div class="notices-title">
				<div class="title">${notice.noticeTitle}</div>
			</div>
			<div class="notices-date">
				<div class="date">작성일</div>
				<div class="notices-content-divider"></div>
       			<div class="registrationDate"><fmt:formatDate value="${notice.noticeRegDate}" pattern="yyyy-MM-dd"/></div>
				<div class="count">조회</div>
				<div class="notices-content-divider"></div>
				<div class="count-num">${notice.noticeHitcount}</div>
			</div>
			<div class="notices-divider">
				<div class="divider-bottom"></div>
			</div>
			<div class="notices-item">
				<div class="content">
					${fn:replace(notice.noticeContent, lf, "<br/>")}
				</div>
			</div>
		</div>
	</div>

	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	
</body>
</html>