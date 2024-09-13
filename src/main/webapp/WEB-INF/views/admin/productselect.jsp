<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>

<link href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/productselect.css" rel="stylesheet"
	type="text/css" />
</head>



<body>
	<div class="section1">
		<a href="${pageContext.request.contextPath}">
			<h2 class="header-logo">greenery</h2>
		</a>
	</div>
	<!-- 관리자 페이지 목록 -->
	<div class="admin-page">
		<%@ include file="mainadmin.jsp" %>
		<div class="admin-content">
			<div class="top-text-margin">
				상품관리 > <span class="text-success"><b>상품조회</b></span>
			</div>
			<h2 class="top-text-margin">
				<b>상품조회</b>
			</h2>
	
			<div class="list-header">
				<div class="item1">등록일자</div>
				<div class="item2">상품 이미지</div>
				<div class="item3">상품명</div>
				<div class="item4">상품 수량</div>
				<div class="item5">판매가</div>
				<div class="item6">수정 / 삭제</div>
			</div>
			<c:forEach items="${productList}" var="product">
				<div class="list-container">
					<div class="list1">왜안나와</div>
					<div class="list2">
						<img src="#">
					</div>
					<div class="list3">${product.productName}</div>
					<div class="list4">${product.productStock}</div>
					<div class="list5"><fmt:formatNumber>${product.productPrice}</fmt:formatNumber>원</div>
					<div class="list6">
						<a href="${pageContext.request.contextPath}/admin/updateProduct?productId=${product.productId}" class=" btn btn1">수정</a>
						<a href="${pageContext.request.contextPath}/admin/deleteProduct?productId=${product.productId}" class=" btn btn2">삭제</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/productselect.js"></script>
</body>
</html>
