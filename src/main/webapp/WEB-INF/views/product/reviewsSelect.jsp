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

