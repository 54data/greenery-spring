<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<title>검색 페이지</title>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/search.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />

</head>

<body>
	<div id="header">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
	</div>
	<c:if test="${searchDto.category != null}">
		<div class="category-result">${searchDto.category}</div>
	</c:if>

	<div class="search-result-text">
		<c:if test="${searchDto.searchContent != null}">
			'<span class="search-term">${searchDto.searchContent}</span>' 에 대한 검색 결과입니다.
		</c:if>
	</div>

	<div class="result-info">
		<div class="product-count">
			<span id="product-count-text">제품</span> <span id="product-count">13</span>개
		</div>

		<!-- 상품 정렬 -->
		<form class="toolbar-sort" method="get" action="${pageContext.request.contextPath}/product/search">
			<c:if test="${searchDto.category != null}">
				<input type="hidden" name="category" value="${searchDto.category}" />
			</c:if>
			
			<c:if test="${searchDto.searchContent != null}">
				<input type="hidden" name="search" value="${searchDto.searchContent}" />
			</c:if>
			
			<select class="toolbar-sort-select" name="sort" onchange="this.form.submit()">				
				<option>정렬방법</option>
				<option value="regDateDesc" <c:if test="${searchDto.sort == 'regDateDesc'}">selected</c:if>>신상품순</option>
				<option value="priceAsc" <c:if test="${searchDto.sort == 'priceAsc'}">selected</c:if>>낮은 가격순</option>
				<option value="priceDesc" <c:if test="${searchDto.sort == 'priceDesc'}">selected</c:if>>높은 가격순</option>
			</select>
		</form>
	</div>

	<div class="main-products">
		<div class="product-container">
			<c:forEach items="${productList}" var="product">
				<div class="product-item">
	                <div class="product-image-container">
	                    <img src="loadMainImg?productId=${product.productId}" class="product-image">
	                    <div class="product-icons">
				            <button class="icon like-icon">
				                <img src="${pageContext.request.contextPath}/resources/image/heart.png" alt="찜하기 아이콘">
				            </button>
				            <button class="icon cart-icon" onclick="location.href='${pageContext.request.contextPath}/order/basket'">
				                <img src="${pageContext.request.contextPath}/resources/image/cart_icon2.png" alt="장바구니 아이콘">
				            </button>
				            <button class="icon buy-icon" onclick="location.href='${pageContext.request.contextPath}/order/payment'">
				                <img src="${pageContext.request.contextPath}/resources/image/dollar.png" alt="구매하기 아이콘" class="payment-img">
				            </button>
	                    </div>
	                </div>
	                <div class="product-details">
	                    <p class="product-name">${product.productName}</p>
	                    <p class="product-description">${product.productSummary}</p>
	                    <p class="product-price"><span class="price-amount"><fmt:formatNumber value="${product.productPrice}" type="number" groupingUsed="true" /></span>원</p>
	                </div>
	            </div>
			</c:forEach>
		</div>
		<button class="scroll-btn-up" onclick="scrollToTop()"></button>
	</div>

	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/search.js"></script>
</body>

</html>