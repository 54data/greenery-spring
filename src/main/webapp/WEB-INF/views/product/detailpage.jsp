<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>화장품 상세페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/detailpage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/detail-info.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reviews-select.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
</head>

<body>
   <div id="header">
      <%@ include file="/WEB-INF/views/common/header.jsp" %>
   </div>
   <input type="hidden" id="productId" data-order-id="${product.productId}" value="${product.productId}" />
   <div class="container">
      <div class="product-container">
         <div class="left">
            <!-- 이미지 사이드 쇼 -->
            <div class="slideshow-container">
               <c:forEach items="${map.entrySet()}" var="map">
                  <c:if test="${map.getValue() ne 'detail'}">
                     <div class="mySlides fade">
                        <img src="loadProductImgs?productImgId=${map.getKey()}&productImgUsage=${map.getValue()}">
                     </div>
                  </c:if>   
               </c:forEach>
            </div>
         </div>
   
         <div class="right">
            <div class="title-container">
               <p class="brand-name">${product.category}</p>
               <p class="product-title">${product.productName}</p>
            </div>
            <div class="description-container">
               <div class="description-line"></div>
               <p class="description">${product.productDetailSummary}</p>
               <div class="description-line"></div>
            </div>
            
	            <div class="product-info">
	            <c:if test="${product.productStock != 0}">
	               <div class="product-quantity">
	                  <button onclick="decrease(this)">-</button>
	                  <span class="quantity-number" id="quantity">1</span>
	                  <button onclick="increase(this)">+</button>
	               </div>
	               <span class="product-price" data-price="${product.productPrice}">
	                  <fmt:formatNumber value="${product.productPrice}" type="number" pattern="#,###"/> 원
	               </span>
	              </c:if>
	            </div>
            
            <c:if test="${product.productStock == 0}">
            	<span id="soldout">품절</span>
            	<div>해당 상품은 품절 입니다.</div>
            </c:if>
            <div class="buttons">
               <button onclick="location.href='${pageContext.request.contextPath}/order/addBasket?productId=${product.productId}'" class="add-to-cart">장바구니</button>
               <button onclick="location.href='${pageContext.request.contextPath}/order/payment?productId=${product.productId}'" class="checkout">바로구매</button>
               <button class="wishlist-button" data-pid="${product.productId}">
					<c:if test="${isWishlist}">
						<img src="${pageContext.request.contextPath}/resources/image/fullheart-icon.png" class="wishlist">
					</c:if>
					<c:if test="${!isWishlist}">
						<img src="${pageContext.request.contextPath}/resources/image/heart-icon.png" class="wishlist">
					</c:if>
               </button>
            </div>
         </div>
      </div>
      
      <!-- 밑에 다른 이미지 -->
      <div class="sideimg">
         <div class="currentSlide-container">
            <c:forEach items="${map.entrySet()}" var="map" varStatus="status">
               <c:if test="${map.getValue() ne 'detail'}">
                  <img class="dot" src="loadProductImgs?productImgId=${map.getKey()}&productImgUsage=${map.getValue()}" onclick="currentSlide(${status.index}, this)">
               </c:if>
            </c:forEach>
         </div>
      </div>
   </div>

   <!-- 상세페이지, 리뷰페이지 -->
   <div class="tab-container">
      <div class="tab">
         <div class="tab-item">
            <button class="tab-button tablinks" onclick="loadTabContent('detailInfo', ${product.productId})">상세정보</button>
            <div class="detail-divider"></div>
         </div>
         
         <div class="tab-item">
            <button class="tab-button tablinks" onclick="loadTabContent('reviewsSelect', ${product.productId}, 1)">리뷰</button>
            <div class="reviews-divider"></div>
         </div>
      </div>
   </div>

   <div id="tab-content"></div>

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