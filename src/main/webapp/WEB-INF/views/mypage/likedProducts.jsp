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
<span class="product-container"></span>