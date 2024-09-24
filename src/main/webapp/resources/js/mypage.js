function getContent(url) {
    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            $(".mypage-content").append(data);
        }
    });
}

function updateUserInfo() {
	const userData = {
		userTel : $(".myinfo-phone input").val(),
		userEmail : $(".myinfo-email input").val(),
	    zipcode: $("input[name='zipcode']").val(), 
	    roadAddress: $("input[name='roadAddress']").val(),
	    detailedAddress: $("input[name='detailedAddress']").val()
	};
    
	const Toast = Swal.mixin({
	    toast: true,
	    position: 'top-center',
	    showConfirmButton: false,
	    timer: 2500,
	    timerProgressBar: true,
	    didOpen: (toast) => {
	    	toast.style.width = '350px';
	    	toast.style.fontSize = '14px';
	        toast.addEventListener('mouseenter', Swal.stopTimer);
	        toast.addEventListener('mouseleave', Swal.resumeTimer);
	    }
	});
	
    $.ajax({
        url: "updateMyInfo", 
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(userData),
        success: function (url) {
        	$(".mypage-content").empty();
        	getContent(url);
			Toast.fire({
			    icon: 'success',
			    title: '정보가 성공적으로 업데이트되었습니다.'
			})
        }
    });
}

function updateUserPwd() {
	const pwdData = {
		currentPwd: $(".current-pwd input").val(),
		newPwd: $(".change-pwd input").val(), 
		confirmPwd: $(".change-check-pwd input").val(),
	};
	
    if (pwdData.newPwd !== pwdData.confirmPwd) {
        alert("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        return;
    }
    
    $.ajax({
        url: "updateUserPassword", 
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(pwdData),
        success: function(response) {
            if (response === "NOT EQUAL") {
                alert("현재 비밀번호가 일치하지 않습니다.");
            } else if (response === "SUCCESS") {
                alert("비밀번호가 성공적으로 변경되었습니다.");
            } else if (response === "FAIL") {
                alert("비밀번호 변경에 실패했습니다.");
            }
            window.location.href = "../logout";
        }
    });
}

function zipcodeBtn() { 
	$(document).on('click', '#btnZipcode', function() {
        new daum.Postcode({
            oncomplete: function(data) {
                let fullAddr = '';
                let extraAddr = '';

                if (data.userSelectedType === 'R') {
                    fullAddr = data.roadAddress;
                } else {
                    fullAddr = data.jibunAddress;
                }

                if (data.userSelectedType === 'R') { // 수정된 부분
                    if (data.bname !== '') {
                        extraAddr += data.bname;
                    }
                    if (data.buildingName !== '') {
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    fullAddr += (extraAddr !== '' ? '(' + extraAddr + ')' : '');
                }

                $('input[name="zipcode"]').val(data.zonecode);
                $('input[name="roadAddress"]').val(fullAddr);
            }
        }).open();
    });
}


//function dataToHtml(products) {
//    if (Array.isArray(products)) {
//        products.forEach(product => {
//            const productHtml = `
//            <div class="product-item">
//                <div class="product-image-container">
//                    <img src="${product.imageUrls[0]}" alt="${product.productName
//                }" class="product-image">
//                    <div class="product-icons">
//                        <span class="icon like-icon active">
//                            <img src="../../res/images/fill_heart.png" alt="찜하기 아이콘">
//                        </span>
//                        <span class="icon cart-icon">
//                            <img src="../../res/images/cart_icon2.png" alt="장바구니 아이콘">
//                        </span>
//                        <span class="icon buy-icon">
//                            <img src="../../res/images/dollar.png" alt="구매하기 아이콘">
//                        </span>
//                    </div>
//                </div>
//                <div class="product-details">
//                    <p class="product-name">${product.productName}</p>
//                    <p class="product-description">${product.mainDescription
//                }</p>
//                    <p class="product-price"><span class="price-amount">${product.price.toLocaleString()}</span>원</p>
//                </div>
//            </div>`;
//
//            // 생성한 HTML을 product-container에 추가
//            $(".product-container").append(productHtml);
//        });
//    }
//}
//
//function getData() {
//    $.ajax({
//        url: "../../content/products.json",
//        method: "GET",
//        dataType: "json",
//        success: function (data) {
//            dataToHtml(data.products);
//        },
//        error: function (err) {
//            console.error("Error fetching product data:", err);
//        },
//    });
//}

$(document).ready(function () {
    getContent("likedProducts");

    // 마이페이지 로딩 시 기본적으로 찜한 상품 탭 글씨 진하게 보여줌
    $('.mypage-menu').each(function () {
        if ($(this).data('url') === 'likedProducts') {
            $(this).html('<strong>' + $(this).text() + '</strong>');
        }
    });
    
    // 메뉴 탭 클릭 시
    $(".mypage-menu").click(function () {
        $(".mypage-content").empty();
        getContent($(this).data("url"));

        $('.mypage-menu').html(function () {
            return $(this).text(); // 다른 탭 클릭 시 텍스트 원래대로 (진하게x)
        });
        $(this).html('<strong>' + $(this).text() + '</strong>'); // 클릭한 메뉴 탭 글씨 진하게 함
    });

    // 동적으로 생성된 like 아이콘에 대한 이벤트 처리
    $(document).on('click', '.icon.like-icon', function () {
        $(this).toggleClass("active");
        let heartIcon = $(this).find("img");
        if ($(this).hasClass("active")) {
            heartIcon.attr("src", "../resources/image/fill_heart.png");
        } else {
            heartIcon.attr("src", "../resources/image/heart.png")
        }
    });
    
    $(document).on('click', '.product-image', function () {
        window.location.href = '../product/detailpage';
    });
    
    $(document).on('click', '.order-img', function () {
    	var productId = $(this).data('product-id');
        
        window.location.href = '../product/detailpage?productId=' + productId;
    });
    
    zipcodeBtn();
    
    $(document).on('click', '.review-btn', function(){  	
    	
    	$("#image-input").val(''); 
        $("#reviewTextarea").val('');  
        $(".star").removeClass('on'); 
        $("#image-preview").html('<span>+</span>');
        
    	var userId = $(this).data('userId');
    	var orderId = $(this).data('orderId');
    	var productId = $(this).data('productId');
    	var productName = $(this).data('productName');
    	var productSummary = $(this).data('productSummary');
    	    	
    	$('.write-btn').data('productId', productId).data('orderId', orderId).data('userId', userId);
    	console.log($('.write-btn').data('productId'));
    	$('#review_img').attr('src', 'loadMainImg?productId='+productId);
    	$('.product-name').html('<span><strong>'+productName+'</strong></span>');
    	$('.product-description').html('<span>'+productSummary+'</span>')
		
		$('#exampleModal').modal('show');
    });
    
    $(document).on('click', '.update-btn', function(){  
        
    	$("#image-input").val(''); 
        $("#reviewTextarea").val('');  
        $(".star").removeClass('on'); 
        $("#reviewTextarea").html('');
        
    	var userId = $(this).data('userId');
    	var orderId = $(this).data('orderId');
    	var productId = $(this).data('productId');
    	var productName = $(this).data('productName');
    	var productSummary = $(this).data('productSummary');
    	var reviewId = $(this).data('reviewId');
    	
    	console.log("업데이트 버튼");
    	
    	$('.write-btn').data('productId', productId).data('orderId', orderId).data('userId', userId).data('reviewId', reviewId);
    	
    	$('#review_img').attr('src', 'loadMainImg?productId='+productId);
    	$('.product-name').html('<span><strong>'+productName+'</strong></span>');
    	$('.product-description').html('<span>'+productSummary+'</span>')
		
		$('#exampleModal').modal('show');
    	
    	$.ajax({
    		url: 'getReviewInfo',
    		method: 'GET',
    		data: { reviewId: reviewId, orderId: orderId, productId: productId },
    		success: function(review){
    			$('#reviewTextarea').val(review.reviewContent);
                $('#reviewScoreInput').val(review.reviewScore);

                if (review.reviewImgName) {
                    $('#image-preview #review-prev-img').attr('src', 'loadReviewImg?reviewId=' + reviewId);
                } else {
                    $('#image-preview').html('<span>+</span>');
                }

                var reviewScore = review.reviewScore;
                var stars = $('.star_rating > .star');
                stars.removeClass('on');
                stars.each(function(index) {
                    if (index < reviewScore) {
                        $(this).addClass('on');
                    }
                });

                $('#exampleModal').modal('show');
    		}
    	});
    });
    
    $(document).off('click', 'write-btn').on('click', '.write-btn', function(){
    	var reviewId = $(this).data('reviewId');
    	var productId = $(this).data('productId');
    	var orderId = $(this).data('orderId');
    	var userId = $(this).data('userId');
    	
    	var reviewContent = $("#reviewTextarea").val();
    	var reviewScore = $(".star_rating .on").length;
    	var reviewImg = $("#image-input")[0].files[0];

    	
        console.log("reviewId:", reviewId);
        console.log("productId:", productId);
        console.log("orderId:", orderId);
        console.log("userId:", userId);
        console.log("reviewContent:", reviewContent);
        console.log("reviewScore:", reviewScore);
        console.log("reviewImg:", reviewImg);
        
    	var formData = new FormData();
    	
    	formData.append('productId', productId);
    	formData.append('orderId', orderId);
    	formData.append('userId', userId);
		formData.append('reviewContent', reviewContent);
    	formData.append('reviewScore', reviewScore);

    	if (reviewImg) {
            formData.append('reviewImg', reviewImg);
        }

    	var ajaxUrl = '';
		if(reviewId){
			formData.append('reviewId', reviewId);
			ajaxUrl = '/miniproject/mypage/updateReview';
		} else {
			ajaxUrl = '/miniproject/mypage/reviewInsert';
		}
		
		console.log(ajaxUrl);
		
    	$.ajax({
    		url: ajaxUrl,
    		method: 'POST',
    		data: formData,
    		processData: false,
    		contentType: false,
    		success: function(e){
    			alert("리뷰 등록 성공")
    		},
    		error: function(data){
    			alert("리뷰 등록 실패");
    			console.log(data);
    		}
    	})	
    });
    
});
