<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
			<div class="product-list-col">
				<div class="product-list-col-info">상품정보</div>
				<div class="product-list-col-qty">수량</div>
				<div class="product-list-col-price">구매가</div>
			</div>
			<div id="productList">
				<c:if test="${not empty selectedProductList}">
					<c:forEach items="${selectedProductList}" var="product">
						<div class="product">
			                <div class="product-body">
			                	<div class="product-info">
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
				                </div>
		                    	<div class="product-amount" data-qty="${product.productQty}">
		                    		${product.productQty}
		                    	</div>
			                    <div class="product-price">
									<span class="product-total-price" data-total-price="${product.productQty * product.productPrice}"><fmt:formatNumber value="${product.productQty * product.productPrice}" type="number" groupingUsed="true"/></span>원
			                    </div>
			                </div>
		                </div>
		        	</c:forEach>
		        </c:if>
		        <c:if test="${empty selectedProductList}">
	        		<div class="product" data-pid="${productInfo.productId}" data-price="${productInfo.productPrice}">
		                <div class="product-body">
		                	<div class="product-info">
			                    <div class="img">
			                    	<img src="${pageContext.request.contextPath}/order/loadMainImg?productId=${productInfo.productId}" alt="${productInfo.productName}" class="picture">
			                    </div>
			                    <div class="product-label">
			                        <div class="product-name">
			                        	<span><strong>${productInfo.productName}</strong></span>
			                        </div>
			                        <div class="product-description">
			                        	<span>${productInfo.productSummary}</span>
			                        </div>
			                    </div>
		                    </div>
		                    <c:if test="${productStock == null}">
	                    		<div class="product-amount" data-qty=1>1</div>
			                    <div class="product-price">
									<span class="product-total-price" data-total-price="${productInfo.productPrice}"><fmt:formatNumber value="${productInfo.productPrice}" type="number" groupingUsed="true"/></span>원
			                    </div>
	                    	</c:if>
		                    <c:if test="${productStock != null}">
	                    		<div class="product-amount" data-qty="${productStock}">${productStock}</div>
			                    <div class="product-price">
									<span class="product-total-price" data-total-price="${productInfo.productPrice * productStock}"><fmt:formatNumber value="${productInfo.productPrice * productStock}" type="number" groupingUsed="true"/></span>원
			                    </div>
	                    	</c:if>
		                </div>
	                </div>
		        </c:if>
			</div>
			<button class="scroll-btn-up" onclick="scrollToTop()"></button>

			<div class="coupon-info">
				<!--  쿠폰 할인 정보 -->
				<div class="coupon-title">
					<h1>쿠폰 할인 정보</h1>
					<button id="have-coupon">보유쿠폰 (<c:set var="coupon_cnt" value="${couponStatus ne 1 ? 0 : 1}"></c:set>${coupon_cnt})</button>
				</div>
				<hr id="hr-topLine">
				<div class="coupon-input-container">
					<div class="custom-select">
						<select id="coupon-select">
							<c:choose>
								<c:when test="${couponStatus == 1}">
									<option value=-1000>그리너리 회원을 위한 1,000원 할인 쿠폰</option>
								</c:when>
								<c:otherwise>
									<option value=0>사용 가능한 쿠폰이 없습니다.</option>
								</c:otherwise>
							</c:choose>
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
						<span>총 주문 금액 &nbsp;</span><span id="sumPrice">0</span>
					</div>
					<div class="delivery">
						<span>배송비 </span><span id="deliveryPrice">2,500 원</span>
					</div>
					<div class="coupon">
						<span>쿠폰 할인 금액 &nbsp;</span><span id="discount" data-discount=0></span>
					</div>
				</div>
				<div class="divider"></div>
				<div class="payment-info-body-content1">
					<div class="totalPrice" id="sum_p_price">
						<span>총 결제 금액 &nbsp;</span><span id="totalPrice-num" data-total-price=0 data-discount=0>0</span>원
					</div>
					<div id="goOrder">
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