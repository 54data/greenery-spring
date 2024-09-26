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
	<link href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reviews.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sweetalert2/sweetalert2.min.css">

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
			<span id="product-count-text">제품</span> <span id="product-count">
				<c:if test="${searchDto == null}">${pager.totalRows}</c:if>
				<c:if test="${searchDto != null}">${searchedRows}</c:if>
			</span>개
		</div>

		<!-- 상품 정렬 -->
		<form class="toolbar-sort" method="get" 
			<c:if test="${searchDto == null}">action="${pageContext.request.contextPath}/product/searchProductAll"</c:if>
			<c:if test="${searchDto != null}">action="${pageContext.request.contextPath}/product/search"</c:if>>
			
			<c:if test="${searchDto.category != null}">
				<input type="hidden" name="category" value="${searchDto.category}" />
			</c:if>
			
			<c:if test="${searchDto.searchContent != null}">
				<input type="hidden" name="search" value="${searchDto.searchContent}" />
			</c:if>
			
			<select class="toolbar-sort-select" name="sort" onchange="this.form.submit()">				
				<option>기본순</option>
				<option value="regDateDesc" <c:if test="${searchDto.sort == 'regDateDesc'||pager.sort == 'regDateDesc'}">selected</c:if>>신상품순</option>
				<option value="priceAsc" <c:if test="${searchDto.sort == 'priceAsc'||pager.sort  == 'priceAsc'}">selected</c:if>>낮은 가격순</option>
				<option value="priceDesc" <c:if test="${searchDto.sort == 'priceDesc'||pager.sort  == 'priceDesc'}">selected</c:if>>높은 가격순</option>
			</select>
		</form>
	</div>

	<div class="main-products">
		<div class="product-container">
			<c:forEach items="${productList}" var="product">
				<div class="product-item">
	                <div class="product-image-container">
	                    <img src="loadMainImg?productId=${product.productId}" class="product-image" onclick="location.href='${pageContext.request.contextPath}/product/detailpage?productId=${product.productId}'">
	                    <div class="product-icons">
				            <button class="icon like-icon" data-pid="${product.productId}">
				            	<c:choose>
				            		<%-- 로그인 되어 있는 경우 --%>
				            		<c:when test="${pageContext.request.userPrincipal != null}">
				            			<c:if test="${isWishlist[product.productId]}">
						               		<img src="${pageContext.request.contextPath}/resources/image/fill_heart.png" alt="찜하기 아이콘">
				            			</c:if>
				            			<c:if test="${!isWishlist[product.productId]}">
					               			<img src="${pageContext.request.contextPath}/resources/image/heart.png" alt="찜하기 아이콘">				            	
				            			</c:if>
				            		</c:when>
				            		<%-- 로그인이 안되어 있는 경우 --%>
				            		<c:when test="${pageContext.request.userPrincipal == null}">
					               		<img src="${pageContext.request.contextPath}/resources/image/heart.png" alt="찜하기 아이콘">				            	
				            		</c:when>
				            	</c:choose>
				            </button>
				            <button class="icon cart-icon" data-pid="${product.productId}">
				                <img src="${pageContext.request.contextPath}/resources/image/cart_icon2.png" alt="장바구니 아이콘">
				            </button>
				            <button class="icon buy-icon" onclick="location.href='${pageContext.request.contextPath}/order/payment?productId=${product.productId}'">
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
	<c:if test="${empty product}">
		<div class="notFound">
			<img src="${pageContext.request.contextPath}/resources/image/notFound.jpg">
			<br>
			검색된 내용이 없습니다.
		</div>
	</c:if>
	
	<c:if test="${searchDto == null}">
		<div id="pager">
			<c:if test="${pager.groupNo > 1}">
				<a href="?pageNo=${pager.startPageNo-1}&sort=${pager.sort}" class="btn btn-light">다음</a>
			</c:if>
			
			<c:forEach begin="${pager.startPageNo}" end="${pager.endPageNo}"
				step="1" var="i">
				<c:if test="${pager.pageNo == i}">
					<a href="?pageNo=${i}&sort=${pager.sort}" class="btn btn-outline-dark">${i}</a>
				</c:if>
				<c:if test="${pager.pageNo != i}">
					<a href="?pageNo=${i}&sort=${pager.sort}" class="btn btn-light">${i}</a>
				</c:if>
			</c:forEach>
			
			<c:if test="${pager.groupNo < pager.totalGroupNo}">
				<a href="?pageNo=${pager.endPageNo+1}&sort=${pager.sort}" class="btn btn-light">다음</a>
			</c:if>
		</div>
	</c:if>

	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/search.js"></script>
</body>

</html>