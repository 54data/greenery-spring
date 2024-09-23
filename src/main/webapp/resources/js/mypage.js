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
    
    $(document).on('click', '.review-btn', function () {
    	var productId = $(this).closest('.order-item-col').find('.order-img').data('product-id');
    	var userId = $('#userIdInput').val();
    	var orderId = $(this).closest('.order-item-col').find('.orderIdInput').data('order-id');
    	
        var productName = $(this).closest('.order-item-col').find('.item-title').text();
        var productSummary = $(this).closest('.order-item-col').find('.item-desc').text();
        
        console.log("productId: " + productId);
        console.log("userId: " + userId);
        console.log("orderId: " + orderId);
        
        $('#reviewModal').find('#productIdInput').val(productId);
        $('#reviewModal').find('#userIdInput').val(userId);
        $('#reviewModal').find('.orderIdInput').val(orderId)
        
        $('#reviewModal').find('.product-name').html(productName);
        $('#reviewModal').find('.product-description').text(productSummary);      
        $('#reviewModal').find('#review_img').attr('src', 'loadMainImg?productId=' + productId);
        
        $('#reviewModal').modal('show');
    });
    
    $(document).on('click', '.star', function() {
        var score = $(this).attr('value'); 
        $('#reviewScoreInput').val(score); 
        console.log("Selected review score: " + score);
    });
    

    
/*    $(document).on('click', '.update-btn', function () {
    	var productId = $(this).closest('.order-item-col').find('.order-img').data('product-id');
    	var userId = $('#userIdInput').val();
    	var orderId = $(this).closest('.order-item-col').find('.orderIdInput').data('order-id');
    	
        var productName = $(this).closest('.order-item-col').find('.item-title').text();
        var productSummary = $(this).closest('.order-item-col').find('.item-desc').text();        
        
        console.log("productId: " + productId);
        console.log("userId: " + userId);
        console.log("orderId: " + orderId);
        
        $('#updateModal').find('#productIdInput').val(productId);
        $('#updateModal').find('#userIdInput').val(userId);
        $('#updateModal').find('.orderIdInput').val(orderId)
        
        $('#updateModal').find('.product-name').html(productName);
        $('#updateModal').find('.product-description').text(productSummary);      
        $('#updateModal').find('#review_img').attr('src', 'loadMainImg?productId=' + productId);
        
        $('#updateModal').modal('show');
    });*/
   
    
/*    $(document).on('click', '.update-btn', function () {
        var reviewId = $(this).data('review-id');
        var productId = $(this).data('product-id');
        var orderId = $(this).data('order-id');

        $.ajax({
            url: '/miniproject/mypage/loadUpdateForm',
            type: 'GET',
            data: { reviewId: reviewId, productId: productId, orderId: orderId },
            success: function(response) {
                $('#modalContainer').empty().html(response);

                var newModal = new bootstrap.Modal(document.getElementById('updateModal'));
                newModal.show();
            },
            error: function() {
                alert('리뷰 수정 폼을 불러오는 데 실패했습니다.');
            }
        });
    });
    */
    
/*    $(document).on('click', '.update-btn', function () {
        var reviewId = $(this).data('review-id');
        var productId = $(this).data('product-id');
        var orderId = $(this).data('order-id');

        $.ajax({
            url: '/miniproject/mypage/loadUpdateForm',
            type: 'GET',
            data: { reviewId: reviewId, productId: productId, orderId: orderId },
            success: function(response) {
                $('#modalContainer').empty();

                $('#modalContainer').html(response);

                var newModal = new bootstrap.Modal(document.getElementById('updateModal'));
                newModal.show();
            },
            error: function() {
                alert('리뷰 수정 폼을 불러오는 데 실패했습니다.');
            }
        });
    });*/
    
    $(document).off('click', '.update-btn');

    $(document).on('click', '.update-btn', function () {
        var reviewId = $(this).data('review-id');
        var productId = $(this).data('product-id');
        var orderId = $(this).data('order-id');

        console.log('처음 불러온것', $('#modalContainer').html());
        $('#modalContainer').empty();
        console.log('잘 지워졌는가', $('#modalContainer').html());
        
        $.ajax({
            url: '/miniproject/mypage/loadUpdateForm',
            type: 'GET',
            data: { reviewId: reviewId, productId: productId, orderId: orderId },
            success: function(response) {
            	console.log('잘 지워졌는가', $('#modalContainer').html());
                console.log('AJAX 요청 성공:', response);
                
                $('#modalContainer').html(response);
                /*$('#updateModal').modal('show');*/
                console.log('잘나옴.');
            },
            error: function(xhr, status, error) {
                console.error('AJAX 요청 오류:', error);
            }
        });
    });


    zipcodeBtn();
});
