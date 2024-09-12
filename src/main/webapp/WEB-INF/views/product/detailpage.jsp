<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>화장품 상세페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/detailpage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reviews-select.css">
<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
</head>

<body>
	<div id="header">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
	</div>
	<c:forEach items="${map.entrySet()}" var="map">
		<img src="loadProductImgs?productImgId=${map.getKey()}&productImgUsage=${map.getValue()}">
	</c:forEach>

<%-- 	<img src="loadProductImgs?productId=${product.productId}&productImgUsage=main"> --%>
<%-- 	<c:if test="${not empty map['sub1']}"> --%>
<%-- 		<img src="loadProductImgs?productId=${product.productId}&productImgUsage=sub1"> --%>
<%-- 	</c:if> --%>
<%-- 	<c:if test="${not empty map['sub2']}"> --%>
<%-- 		<img src="loadProductImgs?productId=${product.productId}&productImgUsage=sub2"> --%>
<%-- 	</c:if> --%>
	<div class="container">
		<div class="left">

			<!-- 이미지 사이드 쇼 -->
			<div class="slideshow-container">

				<!-- Full-width images with number and caption text -->
				<div class="mySlides fade">
					<img
						src=""
						alt="Slide 1">
				</div>

				<div class="mySlides fade">
					<img
						src=""
						alt="Slide 2">
				</div>

				<div class="mySlides fade">
					<img
						src=""
						alt="Slide 3">
				</div>
			</div>
		</div>

		<div class="right">
			<div class="title-container">
				<p class="brand-name">${product.category}</p>
				<p class="product-title">${product.productName}</p>
			</div>
			<div class="description-container">
				<div class="divider"></div>
				<p class="description">${product.productDetailSummary}</p>
				<div class="divider"></div>
			</div>
			<div class="product-info">
				<div class="product-quantity">
					<button onclick="decrease(this)">-</button>
					<span class="quantity-number" id="quantity">1</span>
					<button onclick="increase(this)">+</button>
				</div>
				<span class="product-price" data-price="40000">${product.productPrice}</span>
			</div>
			<div class="buttons">
				<button onclick="saveToLocalStorage(); cart();" class="add-to-cart">장바구니</button>
				<button onclick="saveToLocalStorage(); checkout();" class="checkout">바로구매</button>
				<button class="wishlist-button">
					<img src="${pageContext.request.contextPath}/resources/image/heart-icon.png" alt="wishlist"
						class="wishlist">
				</button>
			</div>
		</div>
	</div>

	<!-- 밑에 다른 이미지 -->
	<div class="sideimg">
		<!-- Next and previous buttons -->
		<a class="prev" onclick="plusSlides(-1)"> <img
			src="${pageContext.request.contextPath}/resources/image/left-icon.png" alt="Previous"
			style="width: 80%">
		</a>

		<div class="currentSlide-container">
			<span class="dot" onclick="currentSlide(1)"> <img
				src="https://i.pinimg.com/564x/db/a1/f2/dba1f28adc32bc960e89ee4dd4ffa027.jpg"
				alt="currentSlide(1)" style="width: 25%">
			</span> <span class="dot" onclick="currentSlide(2)"> <img
				src="https://i.pinimg.com/564x/d9/d3/82/d9d3829fc798b6107ca26649c3000b13.jpg"
				alt="currentSlide(2)" style="width: 25%">
			</span> <span class="dot" onclick="currentSlide(3)"> <img
				src="https://i.pinimg.com/564x/17/71/74/177174a71afb907c30f5af5563b7375d.jpg"
				alt="currentSlide(3)" style="width: 25%">
			</span>
		</div>

		<a class="next" onclick="plusSlides(1)"> <img
			src="${pageContext.request.contextPath}/resources/image/right-icon.png" alt="Next" style="width: 80%">
		</a>
	</div>

	<!-- 상세페이지, 리뷰페이지 -->
	<div class="tab-container">
		<div class="tab">
			<div class="tab-item">
				<button class="tab-button tablinks" data-target="detail-info">상세정보</button>
				<div class="detail-divider"></div>
			</div>
			<div class="tab-item">
				<button class="tab-button tablinks" data-target="reviews-select">리뷰</button>
				<div class="reviews-divider"></div>
			</div>
		</div>
	</div>

	<div id="tab-content">
		<!-- AJAX로 불러온 상세 페이지 or 리뷰 표시 -->
	</div>

	<!-- 업버튼 -->
	<div class="main-products">
		<button class="scroll-btn-up" onclick="scrollToTop()"></button>
	</div>

	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>

	<script src="${pageContext.request.contextPath}/resources/js/detailpage.js"></script>
</body>

</html>