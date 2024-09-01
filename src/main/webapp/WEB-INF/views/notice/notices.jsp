<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>공지사항 페이지</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/notices.css">
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
</head>

<body>
	<div id="header">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
	</div>

	<div class="notice-container">
		<div class="notice-inner">
			<p class="notice-title">공지사항</p>
			<p class="notice-num">총 4건</p>
		</div>
		<div class="divider"></div>

		<div class="contents">
			<div class="content-title">
				<div class="num">번호</div>
				<div class="main-title">제목</div>
				<div class="date">작성일</div>
			</div>
			<div class="divider"></div>
		</div>
		<div class="contents">
			<div class="content">
				<div class="noticeId"></div>
				<div class="title"></div>
				<div class="registrationDate"></div>
				<!-- json으로 불러오던 데이터를 임시로 코드 추가 -->
				<div class="notices-item">
					<div class="noticeId">1</div>
					<div class="title">신제품 출시 안내</div>
					<div class="registrationDate">2024.05.01</div>
				</div>
				<div class="divider"></div>
			</div>
		</div>

		<div class="pageNum">
			1
			<div class="divider"
				style="width: 20px; border: 1.5px solid #069369; margin-top: 7px;"></div>
		</div>

	</div>
	
	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	
	<script src="${pageContext.request.contextPath}/resources/js/notices.js"></script>
</body>

</html>