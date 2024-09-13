<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 상세 페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/detail-info.css">
</head>

<body>
<div id="reviewList">
	<div class="reviews">
		<div class="reviews-container">
			<c:forEach items="${reviewList}" var = "review">
				<div class="star-container">
			    <!-- 별 이미지가 들어갈 div태그 -->  
			   ${starHTML} <!-- 이미지 HTML을 여기에 추가 -->
			   <span class="star-cnt"><strong>${review.reviewScore}</strong></span>
				</div>
		
				<div class="info-container">
			    <div class="user-id">${review.userid}</div>
			    <span><fmt:formatDate value="${review.reviewRegDate}" pattern="yyyy-MM-dd"/></span>
				</div>
				
	       		<span class="review-span">${review.reviewContent}</span>
	        
	        	<div class="img-box">	 
	       			<c:if test="${!empty review.reviewImg}">
	       				<img src="loadReviewImg?reviewId=${review.reviewId}">
	       			</c:if>		
	    		</div>	    		
	    		<hr>
			</c:forEach>
	    </div>
	</div>
</div> 

	
</body>

</html>

