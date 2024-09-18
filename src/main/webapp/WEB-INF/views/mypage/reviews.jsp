<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.min.css">    
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reviews.css">
<script src="${pageContext.request.contextPath}/resources/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/reviews.js"></script>

<form action="${pageContext.request.contextPath}/mypage/reviewInsert" method="post"  enctype="multipart/form-data"> 
<button type="button" class="review-btn" data-bs-toggle="modal" data-bs-target="#reviewModal">
    리뷰 작성
</button>

<!-- Modal -->
<div class="modal fade" id="reviewModal" tabindex="-1" aria-labelledby="reviewModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content">

            <div class="modal-header">
                <h1 class="modal-title fs-5" id="reviewModalLabel">리뷰 작성</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
			    <input type="hidden" id="productIdInput" name="productId"  value="">
			    <input type="hidden" id="userIdInput" name="userId" value="">
            	<input type="hidden" id="reviewScoreInput" name="reviewScore" value="">
            	
                <div class="product-container">
                    <img src="" alt="" id="review_img">
                    <div class="product-label">
                        <div class="product-name"><span><strong>${orderDetail.productName}</strong></span></div>
                        <div class="product-description"><span>${orderDetail.productSummary}</span></div>
                    </div>
                </div>

                <div class="star-container">
                    <span id="review-span">상품은 어떠셨나요?</span>
                    <div class="star_rating"> <!-- 별점 매기기 -->
                        <span class="star" value="1"></span>
                        <span class="star" value="2"></span>
                        <span class="star" value="3"></span>
                        <span class="star" value="4"></span>
                        <span class="star" value="5"></span>
                    </div>
                </div>

                <div class="review-tips">
                    <span>리뷰 작성 TIP</span>
                    <div class="image-container">
                        <img src="${pageContext.request.contextPath}/resources/image/reviews-tip-1.jpg" alt="이미지 1" class="review-image">
                        <img src="${pageContext.request.contextPath}/resources/image/reviews-tip-2.jpg" alt="이미지 2" class="review-image">
                        </div>
                        <span class="tip-text">나만의 스킨케어루틴과 함께 전/후 변화를 보여주세요</span>
                        <span class="tip-text" id="tip-text2">오랜 기간동안 사용한 후기라면 더 좋아요!</span>
                    </div>

                    <span>솔직한 상품 리뷰를 남겨주세요.</span>
                    <div class="textarea-wrapper">
                        <textarea class="star_box" id="reviewTextarea" name="reviewContent" maxlength="1000" placeholder="꿀팁 가득, 상세한 리뷰를 작성해보세요!
도움수가 올라가면 포인트도 받고,
탑리뷰어가 될 확률도 높아져요!

*반품, 환불 관련 내용은 고객센터로 별도 문의해주세요."></textarea>
                        <div id="charCount">0 / 1,000</div> <!-- 글자 수 표시 -->
                </div>

                <div>
                    <span>포토</span>
                    <div class="image-upload-container" onclick="document.getElementById('image-input').click();">
                        <div class="image-preview" id="image-preview">
                            <span>+</span>
                        </div>
                    </div>
                    <input type="file" id="image-input" name="reviewImage" accept="image/*" style="display: none;"
                        onchange="previewImage(event)" />
                </div>

            </div>

            <div class="modal-footer">
                <button type="button" class="btn" data-bs-dismiss="modal">닫기</button>
                <button type="submit" class="btn">리뷰 등록하기</button>
            </div>

        </div>
    </div>
</div>
</form>