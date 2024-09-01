<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
	<div class="product-item">
	    <div class="product-image-container">
        	<img src="${pageContext.request.contextPath}/resources/image/productImages/1.png" alt="수분 크림" class="product-image" >
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
            <p class="product-name">수분 크림</p>
            <p class="product-description">피부에 깊은 보습을 제공합니다.</p>
            <p class="product-price"><span class="price-amount">25,000</span>원</p>
        </div>
	</div>
</div>