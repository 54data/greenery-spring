<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
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
		<div class="notice-Content">
			<!-- json으로 불러오던 데이터를 임시로 코드 추가 -->
			<div class="content">
				<div class="notices-title">
					<div class="title">신제품 출시 안내</div>
				</div>
				<div class="notices-date">
					<div class="date">작성일</div>
					<div class="divider"></div>
	       			<div class="registrationDate">2024.05.01</div>
					<div class="count">조회</div>
					<div class="divider"></div>
					<div class="count-num">1</div>
				</div>
				<div class="notices-divider">
					<div class="divider-bottom"></div>
				</div>
				<div class="notices-item">
					<div class="content">
						안녕하세요. 그리너리입니다.<br>
						그리너리를 방문해주신 고객님들께 안내 드립니다.<br>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	
<%-- 	<script src="${pageContext.request.contextPath}/resources/js/noticeContent.js"></script> --%>
</body>
</html>