<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<section class="mypage-title">
	<div class="mypage-title-greeting">
		<img src="${pageContext.request.contextPath}/resources/image/thum.png">
		<div class="greeting-text">
			${orders.userId} 님 반갑습니다.
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
		<div class="order-item-col">
			<div class="ol-1"><fmt:formatDate value="${orderDetail.orderDate}" pattern="yyyy-MM-dd" /></div>
			<div class="ol-3">
				<div class="order-item-img">
					<img src="loadMainImg?productId=${orderDetail.productId}" class="order-img">
				</div>
				<div class="order-item-info">
					<span class="item-title">${orderDetail.productName}</span>
					<span class="item-desc">${orderDetail.productSummary}</span>
				</div>
			</div>
			<div class="ol-1">${orderDetail.productQty}</div>
			<div class="ol-1">${orderDetail.productPrice}</div>
			<div class="ol-1 order-status">
				결제완료
				<%@ include file="/WEB-INF/views/mypage/reviews.jsp" %>
			</div>
		</div>	
	</c:forEach>
</div>

<%-- 	<div class="order-item-col">
		<div class="ol-1">2024-07-20</div>
		<div class="ol-3">
			<div class="order-item-img">
				<img src="${pageContext.request.contextPath}/resources/image/productImages/4.png" class="order-img">
			</div>
			<div class="order-item-info">
				<span class="item-title">헤어 오일</span>
				<span class="item-desc">머릿결을 부드럽고 윤기 있게</span>
			</div>
		</div>
		<div class="ol-1">1</div>
		<div class="ol-1">30,000원</div>
		<div class="ol-1 order-status">
			결제완료
			<%@ include file="/WEB-INF/views/mypage/reviews.jsp" %>
		</div>
	</div> --%>