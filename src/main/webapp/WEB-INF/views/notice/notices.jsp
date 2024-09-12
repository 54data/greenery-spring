<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>	
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	
<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>공지사항 페이지</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css">    
	<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>	
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/notices.css">
</head>

<body>
	<div id="header">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
	</div>
	<div class="notice-container">
		<div class="notice-inner">
			<p class="notice-inner-title">공지사항</p>
			<p class="notice-num">총 ${noticeSize}건</p>
		</div>
		<div class="divider"></div>
		<div class="notice-contents">
			<div class="notice-contents-title">
				<div class="notice-id">번호</div>
				<div class="notice-title">제목</div>
				<div class="notice-date">작성일</div>
			</div>
			<div class="divider"></div>
		</div>
		<c:forEach items="${noticeList}" var="notice">
			<div class="notice-contents">
				<div class="notice-contents-list">
					<div class="notice-id">${notice.noticeId - 1999}</div>
					<div class="notice-title"><a href="noticeContentAddHitcount?noticeId=${notice.noticeId}">${notice.noticeTitle}</a></div>
					<div class="notice-date"><fmt:formatDate value="${notice.noticeRegDate}" pattern="yyyy-MM-dd"/></div>
				</div>
				<div class="divider"></div>
			</div>
		</c:forEach>
		<div class="pageNum">
			<c:forEach begin="${pager.startPageNo}" end="${pager.endPageNo}" step="1" var="i">
            	<c:if test="${pager.pageNo == i}">
                  <a href="notices?pageNo=${i}" class="btn btn-outline-dark">${i}</a>
               </c:if>
               <c:if test="${pager.pageNo != i}">
                  <a href="notices?pageNo=${i}" class="btn btn-light">${i}</a>
               </c:if>
			</c:forEach>
		</div>
	</div>
	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/notices.js"></script>
</body>

</html>