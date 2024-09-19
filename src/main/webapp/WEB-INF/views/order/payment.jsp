<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>결제 페이지</title>
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/payment.css">
</head>

<body>
	<div id="header">
		<%@ include file="/WEB-INF/views/common/header.jsp" %>
	</div>

	<h1>결제하기</h1>
	<div class="basket" id="basket">
		<div class="product-list">
			<!-- <h1 class="payment-title">결제하기</h1> -->
			<div class="basket-list-header">
				<div class="checkbox-container">
					<input type="checkbox" id="allchk" onclick="allchk"> <label
						for="allchk">전체선택</label>
				</div>
				<div class="button-group">
					<button type="button" class="btn" onclick="deleteSelected()">선택
						삭제</button>
				</div>
			</div>

			<hr id="hr-topLine">

			<div id="productList">
				<c:if test="${not empty selectedProductList}">
					<c:forEach items="${selectedProductList}" var="product">
						<div class="product">
			                <div class="product-body">
			                    <input type="checkbox" class="product-checkbox" data-pid="${product.productId}">
			                    <div class="img">
			                    	<img src="${pageContext.request.contextPath}/order/loadMainImg?productId=${product.productId}" alt="${product.productName}" class="picture">
			                    </div>
			                    <div class="product-label">
			                        <div class="product-name">
			                        	<span><strong>${product.productName}</strong></span>
			                        </div>
			                        <div class="product-description">
			                        	<span>${product.productSummary}</span>
			                        </div>
			                    </div>
		                    	<select class="product-amount" data-pid="${product.productId}" data-qty="${product.productQty}">
		                    		<option value="1">1</option>
		                    		<option value="2">2</option>
		                    		<option value="3">3</option>
		                    		<option value="4">4</option>
		                    		<option value="5">5</option>
		                    	</select>
			                    <div class="product-price" data-price="${product.productPrice}">
									<p class="product-total-price">
	<%-- 									<fmt:formatNumber value="${cart.productQty * cart.productPrice}" type="number" groupingUsed="true"/> --%>
											${product.productQty * product.productPrice}
									</p>원
			                    </div>
<%-- 			                    <button class="basket-delete" onclick="location.href='${pageContext.request.contextPath}/order/deleteBasket?productId=${cart.productId}'"> --%>
<%-- 			                    	<img src="${pageContext.request.contextPath}/resources/image/X버튼.png" alt="삭제 버튼" class="delete-icon" style="width: 30px; height: 30px;"> --%>
<!-- 			                    </button> -->
			                </div>
		                </div>
		        	</c:forEach>
		        </c:if>
			</div>
			<button class="scroll-btn-up" onclick="scrollToTop()"></button>

			<div class="coupon-info">
				<!--  쿠폰 할인 정보 -->
				<div class="coupon-title">
					<h1>쿠폰할인정보</h1>
					<button id="have-coupon">보유쿠폰(1)</button>
				</div>
				<hr id="hr-topLine">
				<div class="coupon-input-container">
					<span class="coupon-label">쿠폰</span>
					<div class="custom-select">
						<select id="coupon-select">
							<option value="1">그리너리 회원을 위한 1,000원 할인 쿠폰</option>
						</select> <img src="${pageContext.request.contextPath}/resources/image/dropdown-icon.png" alt="dropdown-icon"
							class="dropdown-icon">
					</div>
					<button class="apply-coupon" onclick="applyCoupon()">쿠폰 적용</button>
				</div>
				<div class="coupon-discount" id="coupon-discount"></div>
			</div>

		</div>
		<div class="alert-coupon">
			<img src="${pageContext.request.contextPath}/resources/image/coupon_modal_check.png"
				class="img-alert-coupon" /> <span>쿠폰 적용 되었습니다.</span>
		</div>

		<!-- 결제 정보 창 -->
		<div class="payment-info">
			<h2>결제 예정 금액</h2>
			<div id="payment-info-body">
				<div class="payment-info-body-content1">
					<div class="orderPrice">
						<span>총 주문 금액 &nbsp;</span><span id="sumPrice">0원</span>
					</div>
					<div class="delivery">
						<span>배송비 </span><span id="deliveryPrice">2,500원</span>
					</div>
					<div class="coupon">
						<span>쿠폰 할인 금액 &nbsp;</span><span id="discount">0원</span>
					</div>
				</div>
				<div class="divider"></div>

				<div class="payment-info-body-content1">
					<div class="totalPrice" id="sum_p_price">
						<span>총 결제 금액 &nbsp;</span><span id="totalPrice-num">0</span>원
					</div>
					<div id="goOrder" class="">
						<!-- 주문버튼 -->
						<div class="clear"></div>
						<div id="product-order">
							<button id="order-button">선택 상품 결제</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="footer">
		<%@ include file="/WEB-INF/views/common/footer.jsp" %>
	</div>
	
	<script src="${pageContext.request.contextPath}/resources/js/payment.js"></script>
</body>

</html>