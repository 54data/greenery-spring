const Toast = Swal.mixin({
    toast: true,
    position: 'top',
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
	
function getContent(url) {
    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            $(".mypage-content").html(data);
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
	
	let checkResult = function() {
		let isValid = true;
		$('.errorMessage').each(function () {
			if ($(this).html() != '') {
				isValid = false;
				return false; 
			}
		});
		return isValid;
	};
	
	if (!checkResult()) {
		Toast.fire({
		    icon: 'error',
		    title: '입력값을 다시 확인해주세요.'
		});
		return;
	}
	
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
			});
        }
    });
}

function updateUserPwd() {
	const pwdData = {
		currentPwd: $(".current-pwd input").val(),
		newPwd: $(".change-pwd input").val(), 
		confirmPwd: $(".change-check-pwd input").val(),
	};
	
	let checkResult = function() {
		let isValid = true;
		$('.pwdErrorMessage').each(function () {
			if ($(this).html() != '') {
				isValid = false;
				return false; 
			}
		});
		return isValid;
	};
	
	if (!checkResult()) {
		Toast.fire({
		    icon: 'error',
		    title: '입력값을 다시 확인해주세요.'
		});
		return;
	}
	
    if (pwdData.newPwd !== pwdData.confirmPwd) {
		Toast.fire({
		    icon: 'error',
		    title: '새 비밀번호와 확인 비밀번호가 일치하지 않습니다.'
		});
        return;
    }
    
    $.ajax({
        url: "updateUserPassword", 
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(pwdData),
        success: function(response) {
        	let alertMessage = ""
            if (response === "NOT EQUAL") {
            	alertMessage = "현재 비밀번호가 일치하지 않습니다.";
            } else if (response === "SUCCESS") {
            	alertMessage = "비밀번호가 성공적으로 변경되었습니다. 서비스에서 자동 로그아웃 됩니다.";
            } else if (response === "FAIL") {
            	alertMessage = "비밀번호 변경에 실패했습니다.";
            }
    		Toast.fire({
    			icon: response === "SUCCESS" ? 'success' : 'error',
    		    title: alertMessage
    		});
    		if (response === "SUCCESS") {
                setTimeout(function() {
                    window.location.href = "../logout";
                }, 2500); 
    		}
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

                if (data.userSelectedType === 'R') { 
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

function checkUserTel() {
	$(document).on('input', '.userTelInput', function() {
		let regExp = RegExp(/^[0-9]{1,11}$/);
		if (regExp.test($(this).val())) {
			$("#inputPhoneMessage").html('');
		} else if ($(this).val() === '') {
			$("#inputPhoneMessage").html('<span>해당 입력 값은 필수입니다.</span>');
		} else {
			$("#inputPhoneMessage").html("<span>- 하이픈을 제외하고 핸드폰 번호를 입력해주세요.</span>");
		}
	});
}

function checkUserEmail() {
	$(document).on('input', '.userEmailInput', function() {
	    let regExp = RegExp(/^[^\s@]+@[^\s@]+\.[^\s@]+$/);
		if (regExp.test($(this).val())) {
			$("#inputEmailMessage").html('');
		} else if ($(this).val() === '') {
			$("#inputEmailMessage").html('<span>해당 입력 값은 필수입니다.</span>');
		} else {
			$("#inputEmailMessage").html("<span>이메일 입력을 확인해주세요.</span>");
		}
	});
}

function checkDetailedAddress() {
	$(document).on('input', '.userDetailedAddress', function() {
	    if ($(this).val() === '') {
			$("#inputDetailedAddressMessage").html('<span>해당 입력 값은 필수입니다.</span>');
		} else {
			$("#inputDetailedAddressMessage").html('');
		}
	});
}

function checkPwd() {
	let pwd = $(".userPwd").val();
    if (pwd === '') {
    	$("#pwdMessage").html('<span>해당 입력 값은 필수입니다.</span>'); 
    } else {
    	$("#pwdMessage").html(''); 
    }
}

function checkNewPwd() {
	let newPwd = $(".userNewPwd").val();
	let checkNewPwd = $(".checkUserNewPwd").val();
    let regExp = RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&_])[A-Za-z\d@$!%*?&_]{8,20}$/);
    
    if (regExp.test(newPwd)) {
    	$("#inputPwdMessage").html(''); 
    } else if (newPwd === '') {
    	$("#inputPwdMessage").html('<span>해당 입력 값은 필수입니다.</span>');
    } else {
    	$("#inputPwdMessage").html('<span>8자 이상 20자 이하의 대소문자, 숫자, 특수문자를 조합해주세요.</span>');
    }
    
    if (regExp.test(checkNewPwd)) {
    	$("#inputCheckPwdMessage").html(''); 
    } else if (checkNewPwd === '') {
    	$("#inputCheckPwdMessage").html('<span>해당 입력 값은 필수입니다.</span>');
    } else {
    	$("#inputCheckPwdMessage").html('<span>비밀번호를 확인해주세요.</span>');
    }
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
    
    // 개인정보 수정 유효성 검사
    checkUserTel();
    checkUserEmail();
    checkDetailedAddress();
    
    // 비밀번호 변경 유효성 검사
    $(document).on('input', '.userPwd, .userNewPwd, .checkUserNewPwd', function() {
        checkPwd();
        checkNewPwd();
    });
});

$('.like-icon').on('click', function(){
	let productId = $(this).data('pid');
	let heartIcon = $(this).find("img");
		
	$.ajax({
		url: "/miniproject/product/Wishlist",
		type: "get",
		data: { productId: productId },
		success: function(response){
			if(response == "fill"){
				heartIcon.attr("src", "/miniproject/resources/image/fill_heart.png");
			}
			if(response == "empty"){
				heartIcon.attr("src", "/miniproject/resources/image/heart.png")
			}
		},
		error: function() {
			console.log("전송 실패")
		}
	})
});
