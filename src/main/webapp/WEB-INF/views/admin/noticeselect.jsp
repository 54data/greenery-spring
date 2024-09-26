<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>공지사항 등록 및 수정</title>
	<link href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
	<link href="${pageContext.request.contextPath}/resources/css/noticeselect.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<%@ include file="mainadmin.jsp" %>
	<div class="admin-content">
		<div class="top-text-margin">
			공지사항 > <span class="text-success"><b>공지사항 수정 / 삭제</b></span>
		</div>
		<h2 class="top-text-margin">
			<b>공지사항 수정 / 삭제</b>
		</h2>
		<div class="list-header">
			<div class="item1">등록일자</div>
			<div class="item2">제목</div>
			<div class="item3">수정 / 삭제</div>
		</div>
		<c:forEach items="${noticeList}" var="notice">
			<div class="list-container">
				<div class="list1"><fmt:formatDate value="${notice.noticeRegDate}" pattern="yyyy-MM-dd"/></div>
				<div class="list2">${notice.noticeTitle}</div>
				<div class="list3">
					<button class="btn1" onclick="location.href='noticeUpdateForm?pageUsage=수정&noticeId=${notice.noticeId}'">수정</button>
					<button class="btn2" data-nid="${notice.noticeId}">삭제</button>
				</div>
			</div>
		</c:forEach>
		<div class="pageNum">
			<c:if test="${noticeList != null}">
				<c:if test="${pager.groupNo > 1}">
					<button
						onclick="location.href='noticeselect?pageNo=${pager.startPageNo-1}'" 
						class="btn btn-light">이전</button>
				</c:if>
				<c:forEach begin="${pager.startPageNo}" end="${pager.endPageNo}" step="1" var="i">
	            	<c:if test="${pager.pageNo == i}">
	                  <a href="noticeselect?pageNo=${i}" class="btn btn-outline-dark">${i}</a>
	               </c:if>
	               <c:if test="${pager.pageNo != i}">
	                  <a href="noticeselect?pageNo=${i}" class="btn btn-light">${i}</a>
	               </c:if>
				</c:forEach>
				<c:if test="${pager.groupNo < pager.totalGroupNo}">
					<button
						onclick="location.href='noticeselect?pageNo=${pager.endPageNo+1}'"
						class="btn btn-light">다음</button>
				</c:if>
			</c:if>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/resources/js/noticeselect.js"></script>
</body>
</html>
