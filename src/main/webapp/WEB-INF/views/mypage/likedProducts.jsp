<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<section class="mypage-title">
	<div class="mypage-title-greeting">
		<img src="${pageContext.request.contextPath}/resources/image/thum.png">
		<div class="greeting-text">
			홍길동 님 반갑습니다.
		</div>
	</div>
	<div class="mypage-title-coupon">
		<span class="coupon-status">
			쿠폰 <span class="coupon-num">1</span>개
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
		            <button class="icon cart-icon" onclick="location.href='${pageContext.request.contextPath}/order/addBasket?productId=${product.productId}'">
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
	<script src="${pageContext.request.contextPath}/resources/js/likeProducts.js"></script>
</div>