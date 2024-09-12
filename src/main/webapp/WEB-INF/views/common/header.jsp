<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/header.css" />
<script src="${pageContext.request.contextPath}/resources/js/header.js"></script>

<header>
	<div class="header-logo">
		<a href="${pageContext.request.contextPath}">
			<img
			src="${pageContext.request.contextPath}/resources/image/logo4.png"
			class="logo">
		</a>
	</div>
	<div class="header-nav">
		<sec:authorize access="isAnonymous()">
			<button class="header-login"
				onclick="location.href='${pageContext.request.contextPath}/account/loginForm'">
				<img
					src="${pageContext.request.contextPath}/resources/image/login_icon.png"
					class="header-nav-icon" /> 
					<span class="header-nav-text">로그인</span>
			</button>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<button class="header-login"
				onclick="location.href='${pageContext.request.contextPath}/logout'">
				<img
					src="${pageContext.request.contextPath}/resources/image/login_icon.png"
					class="header-nav-icon" /> 
					<span class="header-nav-text">로그아웃</span>
			</button>
		</sec:authorize>
		<sec:authorize access="!hasRole('ROLE_admin')">
			<button class="header-mypage"
				onclick="location.href='${pageContext.request.contextPath}/mypage/mypage'">
				<img
					src="${pageContext.request.contextPath}/resources/image/mypage_icon.png"
					class="header-nav-icon" /> <span class="header-nav-text">마이페이지</span>
			</button>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_admin')">
			<button class="header-mypage"
				onclick="location.href='${pageContext.request.contextPath}/admin/mainadmin'">
				<img
					src="${pageContext.request.contextPath}/resources/image/mypage_icon.png"
					class="header-nav-icon" /> <span class="header-nav-text">관리자페이지</span>
			</button>
		</sec:authorize>
		<button class="header-cart"
			onclick="location.href='${pageContext.request.contextPath}/order/basket'">
			<img
				src="${pageContext.request.contextPath}/resources/image/cart_icon.png"
				class="header-nav-icon" /> <span class="header-cart-badge">0</span>
			<span class="header-nav-text">장바구니</span>
		</button>
	</div>
</header>
<div class="header-category">
	<div class="header-category-btn" onclick="categoryOpen(this)">
		<div class="header-category-bar">
			<div class="bar"></div>
			<div class="bar"></div>
			<div class="bar"></div>
		</div>
		<span class="header-category-text">카테고리</span>
	</div>
	<div class="header-category-wrap">
		<ul class="category-wrap">
			<li>
				<a class="category-span" href="${pageContext.request.contextPath}/product/searchProductAll">
					전체상품 <img src="${pageContext.request.contextPath}/resources/image/category_right_arrow.png"
					width="5" height="10" />
				</a>
			</li>
			<li>
				<a class="category-span" href="${pageContext.request.contextPath}/product/search?category=스킨케어">
					스킨케어 <img src="${pageContext.request.contextPath}/resources/image/category_right_arrow.png"
					width="5" height="10" />
				</a>
			</li>
			<li>
				<a class="category-span" href="${pageContext.request.contextPath}/product/search?category=메이크업">
					메이크업 <img src="${pageContext.request.contextPath}/resources/image/category_right_arrow.png"
					width="5" height="10" />
				</a>
			</li>
			<li>
				<a class="category-span" href="${pageContext.request.contextPath}/product/search?category=바디케어">
					바디케어 <img src="${pageContext.request.contextPath}/resources/image/category_right_arrow.png"
					width="5" height="10" />
				</a>
			</li>
			<li>
				<a class="category-span" href="${pageContext.request.contextPath}/product/search?category=헤어케어">
					헤어케어 <img
					src="${pageContext.request.contextPath}/resources/image/category_right_arrow.png"
					width="5" height="10" />
				</a>
			</li>
			<li>
				<a class="category-span" href="${pageContext.request.contextPath}/product/search?category=미용소품">
					미용소품 <img
					src="${pageContext.request.contextPath}/resources/image/category_right_arrow.png"
					width="5" height="10" />
				</a>
			</li>
			<li>
				<a class="category-span" href="${pageContext.request.contextPath}/product/search?category=맨즈케어">
					맨즈케어 <img
					src="${pageContext.request.contextPath}/resources/image/category_right_arrow.png"
					width="5" height="10" />
				</a>
			</li>
		</ul>
	</div>
	<div class="notice">
		<a href="${pageContext.request.contextPath}/notice/notices">공지사항</a>
	</div>
</div>
<!-- 검색창 -->
<form class="header-search" method="get" action="${pageContext.request.contextPath}/product/search">
	<input type="text" name="search" class="header-search-input" />
	<button class="header-search-icon"></button>
</form>