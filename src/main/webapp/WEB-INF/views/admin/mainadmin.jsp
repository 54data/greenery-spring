<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>관리자페이지</title>
<link href="${pageContext.request.contextPath}/resources/css/common.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/mainadmin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reviews.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sweetalert2/sweetalert2.min.css">
</head>

<body>
	<div class="section1">
		<a href="${pageContext.request.contextPath}">
			<img src="${pageContext.request.contextPath}/resources/image/logo4.png" class="logo">
		</a>
	</div>
	<!-- 관리자 페이지 목록 -->
	<div class="admin-page">
	
	<div class="section3">
		<div class="text-white" id="product">
			<img src="${pageContext.request.contextPath}/resources/image/box.png"class="box-image">
			<h6>상품 관리</h6>
		</div>
		<div class="collapse show" id="home-collapse1">
			<ul class="list-unstyled">
				<li>
					<a id="productadd" data-id="productadd" href="${pageContext.request.contextPath}/admin/productadd" class="text-white rounded section3-list-text">
						상품 등록 / 수정
					</a>
				</li>
				<li>
					<a id="productselect" data-id="productselect" href="${pageContext.request.contextPath}/admin/productselect" class="text-white text-decoration-none rounded section3-list-text">
						상품 조회
					</a>
				</li>
			</ul>
		</div>
		<div class="text-white" id="notice">
			<img
				src="${pageContext.request.contextPath}/resources/image/box.png"
				class="box-image">
			<h6>공지사항 관리</h6>
		</div>
		<div class="collapse show" id="home-collapse2">
			<ul class="list-unstyled">
				<li>
					<a id="noticeadd" data-id="noticeadd" href="${pageContext.request.contextPath}/admin/noticeadd" class="text-white text-decoration-none rounded section3-list-text">
						공지사항 등록 / 수정
					</a>
				</li>
				<li>
					<a id="noticeselect" data-id="noticeselect" href="${pageContext.request.contextPath}/admin/noticeselect" class="text-white text-decoration-none rounded section3-list-text">
						공지사항 조회
					</a>
				</li>
			</ul>
		</div>
	</div>
</body>

</html>