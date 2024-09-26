<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>

<section class="mypage-title">
	<div class="mypage-title-greeting">
		<img src="${pageContext.request.contextPath}/resources/image/thum.png">
		<div class="greeting-text">
			${userInfo.userName}님 반갑습니다.
		</div>
	</div>
	<div class="mypage-title-coupon">
		<span class="coupon-status">
			쿠폰
			<span class="coupon-num">
				<c:if test="${userInfo.couponStatus == 1}">1</c:if>
				<c:if test="${userInfo.couponStatus != 1}">0</c:if>
			</span>개
		</span>
	</div>
</section>
<span class="mypage-content-title">찜한 상품</span>
<div class="product-container">
	<c:forEach items="${productList}" var="product">
		<div class="product-item">
		    <div class="product-image-container">
	        	<img src="loadMainImg?productId=${product.productId}" class="product-image">
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
<c:if test="${empty productList}">
	<div class="notFound">
		<img src="${pageContext.request.contextPath}/resources/image/notFound.jpg">
		<br> 찜한 상품이 없습니다.
	</div>
</c:if>
<div id="pager">
	<c:if test="${!empty productList}">
		<%-- [<] 1 2 3 4 5 [>] --%>
		<c:if test="${pager.groupNo > 1}">
			<button
				onclick="getContent('likedProducts?pageNo=${pager.startPageNo-1}')"
				class="btn btn-light">이전</button>
		</c:if>
	
		<c:forEach begin="${pager.startPageNo}" end="${pager.endPageNo}"
			step="1" var="i">
			<c:if test="${pager.pageNo == i}">
				<button onclick="getContent('likedProducts?pageNo=${i}')"
					class="btn btn-outline-dark">${i}</button>
			</c:if>
			<c:if test="${pager.pageNo != i}">
				<button onclick="getContent('likedProducts?pageNo=${i}')"
					class="btn btn-light">${i}</button>
			</c:if>
		</c:forEach>
	
		<c:if test="${pager.groupNo < pager.totalGroupNo}">
			<button
				onclick="getContent('likedProducts?pageNo=${pager.endPageNo+1}')"
				class="btn btn-light">다음</button>
		</c:if>
	</c:if>
</div>