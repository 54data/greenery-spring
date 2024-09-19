<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt"	uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/main.css" />
	<link href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
	<title>Greenery</title>
</head>

<body>
	<div class="modal-container">
		<div class="coupon-modal">
			<img src="${pageContext.request.contextPath}/resources/image/modal.png" class="modal-image" />
			<div class="modal-text">
				<span class="today-close">오늘 그만 보기</span> <span class="close">닫기</span>
			</div>
		</div>
	</div>
	<div class="alert-coupon">
		<img src="${pageContext.request.contextPath}/resources/image/coupon_modal_check.png"
			class="img-alert-coupon" /> 
		<span class="alert-coupon-text"></span>
	</div>
	<!-- 헤더를 삽입할 위치 -->
	<div id="header">
	<%@ include file="/WEB-INF/views/common/header.jsp" %>
	</div>
	<!-- 배너 -->
	<section class="banner">
		<!-- 배너 이미지 -->
		<div class="banner_slides fade_slide">
			<img src="${pageContext.request.contextPath}/resources/image/banner1.png" alt="slide 1" />
		</div>
		<div class="banner_slides fade_slide">
			<img src="${pageContext.request.contextPath}/resources/image/banner2.png" alt="slide 2" />
		</div>
		<div class="banner_slides fade_slide">
			<img src="${pageContext.request.contextPath}/resources/image/banner3.png" alt="slide 3" />
		</div>
		<!-- 배너 이동 버튼 (왼/오) -->
		<div class="banner-control">
			<button class="banner-control-btn-left" onclick="plusSlides(-1)"></button>
			<button class="banner-control-btn-right" onclick="plusSlides(1)"></button>
		</div>
		<!-- 배너 이동 버튼 (순서) -->
		<div class="banner-indicator">
			<button class="banner-indicator-btn" onclick="currentSlide(1)"></button>
			<button class="banner-indicator-btn" onclick="currentSlide(2)"></button>
			<button class="banner-indicator-btn" onclick="currentSlide(3)"></button>
		</div>
	</section>
	<div class="main-products">
		<h3 class="coment">이달의 MD픽! 추천 상품</h3>
		<div class="product-container">
 			<c:forEach items="${recProducts}" var="product">
				<div class="product-item">
	                <div class="product-image-container">
	                    <img src="loadMainImg?productId=${product.productId}" class="product-image" onclick="location.href='${pageContext.request.contextPath}/product/detailpage?productId=${product.productId}'">
	                    <div class="product-icons">
				            <button class="icon like-icon">
				                <img src="${pageContext.request.contextPath}/resources/image/heart.png" alt="찜하기 아이콘">
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
		</div>
		<h3 class="coment">신상 업데이트</h3>
		<div class="product-container">
			<c:forEach items="${newProducts}" var="product">
				<div class="product-item">
	                <div class="product-image-container">
	                    <img src="loadMainImg?productId=${product.productId}" class="product-image" onclick="location.href='${pageContext.request.contextPath}/product/detailpage?productId=${product.productId}'">
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

	<!-- 푸터를 삽입할 위치 -->
	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>

</html>