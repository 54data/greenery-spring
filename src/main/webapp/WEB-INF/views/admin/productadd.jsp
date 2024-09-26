<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Document</title>

<link href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/resources/css/productadd.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reviews.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sweetalert2/sweetalert2.min.css">
	
<!-- summernote html editor -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>

<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"> -->
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>

<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>

<body>
		<%@ include file="mainadmin.jsp" %>
		<div class="admin-content">
			<div class="top-text-margin">
				상품관리 > <span class="text-success"><b>상품등록 / 수정</b></span>
			</div>
			<h2 class="top-text-margin">
				<b>상품등록 / 수정</b>
			</h2>
			<h5 class="top-text-margin">
				<b>기본 정보</b>
			</h5>
			<form method="post" enctype="multipart/form-data" 
				<c:if test="${param.pageUsage != '수정'}">action="productInsert"</c:if>
				<c:if test="${param.pageUsage == '수정'}">action="updateProduct"</c:if>
				onsubmit="return checkValid()">
				<c:if test="${param.pageUsage == '수정'}">
					<input type="hidden" name="productId" value="${product.productId}">
				</c:if>
				<div class="form-group">
					<label>상품명 [30자 이하]</label>
					<input name="productName" value="${product.productName}" id="productName" type="text"
						placeholder="예시) 프레시 블랙 떡솝" required>
				</div>
				
				<div class="form-group">
					<label>판매가 [10자리 이하]</label>
					<input name="productPrice" value="${product.productPrice}"
						id="productPrice" type="text" placeholder="가격을 입력해주세요" maxlength="10" required>
					<div class="form-blank">원</div>
				</div>
				
				<div class="form-group">
					<label>상품 수량 [10자리 이하]</label>
					<input name="productStock" value="${product.productStock}" id="productStock" type="text"
						placeholder="수량을 입력해주세요" required>
					<div class="form-blank">개</div>
				</div>
				
				<div class="image-thumnail">
					<label>상품 대표 이미지 (썸네일)</label>
					<div class="image-upload-container">
					
						<div class="image-preview " id="image-preview1" data-usage="Main">
							<c:if test="${mainImage}">

								<img id="MainImage" src="loadImgByUsage?productId=${product.productId}&usage=main">
							</c:if>
							<c:if test="${!mainImage}">
								<span>Main</span>
							</c:if>
						</div>
						<input class="imageInput" name="productMainImage" type="file" id="productMainImage" accept="image/*"
							style="display: none;" data-usage="Main" data-connect="image-preview1">
							
						<div class="image-preview" id="image-preview2" data-usage="Sub1">
							<c:if test="${sub1Image}">
								<div class="deleteImgButton">
									<a class="btn btn-sm text-danger" onclick="event.stopPropagation();"
										href="deleteProductImg?productId=${product.productId}&usage=sub1">X</a>								
								</div>
								<img id="Sub1Image" src="loadImgByUsage?productId=${product.productId}&usage=sub1">
							</c:if>
							<c:if test="${!sub1Image}">
								<span>sub1</span>
							</c:if>
						</div>
						<input class="imageInput" name="productSub1Image" type="file" id="productSub1Image" accept="image/*"
							style="display: none;" data-usage="Sub1" data-connect="image-preview2"/>
							
						<div class="image-preview" id="image-preview3" data-usage="Sub2">
							<c:if test="${sub2Image}">
								<div class="deleteImgButton">
									<a class="btn btn-sm text-danger" onclick="event.stopPropagation();"
										href="deleteProductImg?productId=${product.productId}&usage=sub2">X</a>								
								</div>
								<img id="Sub2Image" src="loadImgByUsage?productId=${product.productId}&usage=sub2">
							</c:if>
							<c:if test="${!sub2Image}">
								<span>sub2</span>
							</c:if>
						</div>
						<input class="imageInput" name="productSub2Image" type="file" id="productSub2Image" accept="image/*"
							style="display: none;" data-usage="Sub2" data-connect="image-preview3"/>
							
						<div class="image-preview" id="image-preview4" data-usage="Sub3">
							<c:if test="${sub3Image}">
								<div class="deleteImgButton">
									<a class="btn btn-sm text-danger" onclick="event.stopPropagation();"
										href="deleteProductImg?productId=${product.productId}&usage=sub3">X</a>								
								</div>
								<img id="Sub3Image" src="loadImgByUsage?productId=${product.productId}&usage=sub3">
							</c:if>
							<c:if test="${!sub3Image}">
								<span>sub3</span>
							</c:if>
						</div>
						<input class="imageInput" name="productSub3Image" type="file" id="productSub3Image" accept="image/*"
							style="display: none;" data-usage="Sub3" data-connect="image-preview4"/>
					</div>
				</div>
	
				<div class="form-group">
					<label>상품 대표 설명 (썸네일)<br>[40자 이하]</label>
					<input name="productSummary" value="${product.productSummary}" id="productSummary" type="text"
						placeholder="상품의 대표 설명을 입력하세요" required>
				</div>
				
				<div class="form-group">
					<label>상품 상세페이지 대표 설명<br>[80자 이하]</label>
					<input name="productDetailSummary" value="${product.productDetailSummary}" id="productDetailSummary"
						type="text" placeholder="상품의 핵심 특징을 간단히 설명하세요" required>
				</div>
				<div class="image-thumnail">
					<label>상품 상세페이지 상세정보 설명</label>
					<div class="image-upload-container">
						<div class="image-preview" id="image-preview5" data-usage="Detail">
							<c:if test="${detailImage}">
								<img id="DetailImage" src="loadImgByUsage?productId=${product.productId}&usage=detail">
							</c:if>
							<c:if test="${!detailImage}">
								<span>Detail</span>
							</c:if>
						</div>
						<input class="imageInput" name="productDetailImage" type="file" id="productDetailImage" accept="image/*"
							style="display: none;" data-usage="Detail" data-connect="image-preview5"/>
					</div>
				</div>
				
				<div class="form-group">
					<label>카테고리</label>
					<select name="category" id="category" required>
						<option value="스킨케어" <c:if test="${product.category=='스킨케어'}">selected</c:if>>스킨케어</option>
						<option value="바디케어" <c:if test="${product.category=='바디케어'}">selected</c:if>>바디케어</option>
						<option value="메이크업" <c:if test="${product.category=='메이크업'}">selected</c:if>>메이크업</option>
						<option value="미용소품" <c:if test="${product.category=='미용소품'}">selected</c:if>>미용소품</option>
						<option value="헤어케어" <c:if test="${product.category=='헤어케어'}">selected</c:if>>헤어케어</option>
						<option value="맨즈케어" <c:if test="${product.category=='맨즈케어'}">selected</c:if>>맨즈케어</option>
					</select>
				</div>
	
				<div class="btn-register-div">
						<button type="submit" class="btn-register">등록</button>
				</div>
			</form>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/resources/js/productadd.js"></script>
</body>
</html>
