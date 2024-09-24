<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% pageContext.setAttribute("lf", "\n"); %>
	
<!DOCTYPE html>
<html>
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
		<div class="notice-list">
			<div class="notice-before">
				<span>이전글</span>
				<div class="notice-list-title">
					<c:choose>
						<c:when test="${not empty notice.beforeNoticeTitle}">
							<a href="noticeContentAddHitcount?noticeId=${notice.beforeNoticeId}">${notice.beforeNoticeTitle}</a>
						</c:when>
						<c:when test="${empty beforeNoticeTitle}">
							이전글이 없습니다.
						</c:when>
					</c:choose>
				</div>
			</div>
			<div class="notice-after">
				<span>다음글</span>
				<div class="notice-list-title">
					<c:choose>
						<c:when test="${not empty notice.afterNoticeTitle}">
							<a href="noticeContentAddHitcount?noticeId=${notice.afterNoticeId}">${notice.afterNoticeTitle}</a>
						</c:when>
						<c:when test="${empty notice.afterNoticeTitle}">
							다음글이 없습니다.
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
		
		<button class="notice-list-btn" onclick="location.href='notices?pageNo=${pager.pageNo}'">목록 보기</button>
	</div>
	
	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	
</body>
</html>