<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css">    
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reviews.css">
<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/reviews.js"></script>

<input type="hidden" id="userIdInput" value="${userId}" />

<section class="mypage-title">
	<div class="mypage-title-greeting">
		<img src="${pageContext.request.contextPath}/resources/image/thum.png">
		<div class="greeting-text">
			${userName} 님 반갑습니다.
		</div>
	</div>
	<div class="mypage-title-coupon">
		<span class="coupon-status">
			쿠폰 <span class="coupon-num">1</span>개
		</span>
	</div>
</section>
<span class="mypage-content-title">주문 내역</span>
<div class="order-list">
	<div class="order-list-col">
		<div class="ol-1">주문일자</div>
		<div class="ol-3">상품명</div>
		<div class="ol-1">수량</div>
		<div class="ol-1">주문금액</div>
		<div class="ol-1">상태</div>
	</div>	
	<c:forEach items="${orderDetails}" var="orderDetail">
		<div>${orderDetail.review.reviewContent}</div>
	
		<div class="order-item-col">
			<div class="ol-1"><fmt:formatDate value="${orderDetail.orderDate}" pattern="yyyy-MM-dd" /></div>
			<div class="ol-3">
				<div class="order-item-img">
					<img src="loadMainImg?productId=${orderDetail.productId}" class="order-img" data-product-id="${orderDetail.productId}">
				</div>
				<div class="order-item-info">
					<span class="item-title">${orderDetail.productName}</span>
					<span class="item-desc">${orderDetail.productSummary}</span>
				</div>
			</div>
			<div class="ol-1">${orderDetail.productQty}</div>
			<div class="ol-1"><fmt:formatNumber value="${orderDetail.productPrice}" type="number" pattern="#,###"/>원</div>
			<div class="ol-1 order-status">
				결제완료
 				<c:choose>
	                <c:when test="${orderDetail.hasReview}">
	                    <a href="${pageContext.request.contextPath}/mypage/deleteReview?reviewId=${orderDetail.review.reviewId}" class="btn btn-danger">
						 	리뷰 삭제
						</a>

   						<form action="${pageContext.request.contextPath}/mypage/reviewUpdate" method="post"  enctype="multipart/form-data"> 
							<button type="button" class="update-btn" data-bs-toggle="modal" data-bs-target="#updateModal" data-review-id="${orderDetail.review.reviewId}" data-product-id="${orderDetail.productId}" data-order-id="${orderDetail.orderId}">
							    리뷰 수정
							</button>
    					</form>    
              		</c:when>
	                <c:otherwise>
	                    <%@ include file="/WEB-INF/views/mypage/reviews.jsp" %>
	                </c:otherwise>
            	</c:choose> 
				
			</div>
		</div>
	</c:forEach>
	
	<div id="modalContainer"></div>
	
</div>
