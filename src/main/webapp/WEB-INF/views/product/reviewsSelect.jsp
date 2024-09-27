<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 상세 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reviews-select.css">

</head>

<body>
	<div id="reviewList">
		<div class="reviews">
			<div class="reviews-container">
				<c:if test="${not empty reviewList}">
					<div class="toolbar-sort">
						<form method="get">
							<select class="toolbar-sort-select" onchange="loadTabContent('reviewsSelect', ${product.productId}, 1, this.value)">
								<option value="latest" ${pager.sort == 'latest' ? 'selected' : ''}>최신순</option>
								<option value="rating" ${pager.sort == 'rating' ? 'selected' : ''}>별점순</option>
							</select>
						</form>
					</div>
				<c:forEach items="${reviewList}" var="review">
					<div class="review-row">
						<div class="reviewLeft">
							<div class="star-container">
								    <c:set var="totalStars" value="5" />
								    <c:forEach var="i" begin="1" end="${review.reviewScore}">
								        <img src="${pageContext.request.contextPath}/resources/image/fill-star.png" alt="채워진 별" class="star">
								    </c:forEach>
								    <c:forEach var="i" begin="1" end="${totalStars - review.reviewScore}">
								        <img src="${pageContext.request.contextPath}/resources/image/empty-star.png" alt="빈 별" class="star">
								    </c:forEach>
								<span class="star-cnt"><strong>${review.reviewScore}</strong></span>
							</div>
							<div class="info-container">
								<div class="user-id">${review.userId}</div>
								<span><fmt:formatDate value="${review.reviewRegDate}" pattern="yyyy-MM-dd"/></span>
							</div>
							<span class="review-span">${review.reviewContent}</span>
							<div class="review-like">
								<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" class="review_like_action_icon" onclick="addReviewLike(this)">
									<path stroke-linecap="round" stroke-linejoin="round" d="M8.725 6.2c-.051.152-.026.32.068.45s.245.207.406.207h3.477c.061 0 .121.006.181.017.55.1.914.626.814 1.175l-.962 5.288c-.087.48-.505.83-.994.83H6.177c-.558 0-1.01-.453-1.01-1.011v-6.24c0-.241.086-.474.243-.657l3.619-4.223c.174-.202.463-.26.702-.141.21.105.312.35.237.573L8.725 6.2zM2.333 14V7.333"></path>
								</svg>
								<span class="review_like_action_text">추천</span>
								<span class="review_like_action_count">${review.reviewRecom}</span>
							</div>
						</div>
						<div class="img-box">
							<c:if test="${!empty review.reviewImg}">
								<img src="loadReviewImg?reviewId=${review.reviewId}">
							</c:if>
						</div>
					</div>
					<hr>
				</c:forEach>
					<div class="pageNum d-flex justify-content-center">
				    <c:forEach begin="${pager.startPageNo}" end="${pager.endPageNo}" var="i">
				        <c:if test="${pager.pageNo == i}">
				            <a href="javascript:void(0);" class="btn btn-outline-dark">${i}</a>
				        </c:if>
				        <c:if test="${pager.pageNo != i}">
				            <a href="javascript:loadTabContent('reviewsSelect', ${product.productId}, ${i}, '${pager.sort}')" class="btn btn-light">${i}</a>
				        </c:if>
				    </c:forEach>
					</div>
				</c:if>
				<c:if test="${empty reviewList}">
					<div class="emptyInfo">
		        		<img src="${pageContext.request.contextPath}/resources/image/info.png">
		        		<span class="emptyInfoText">해당 상품에 리뷰가 없습니다.</span>
		        	</div>
		        	<div class="blank"></div>
				</c:if>
			
			</div>
		</div>
	</div>
	
</body>
</html>

