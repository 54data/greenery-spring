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
    
    
    $(document).on('click', '.delete-btn', function(){
    	var reviewId = this.value;
    	console.log(reviewId);
    	
    	Swal.fire({
    		  title: "정말 삭제하시겠습니까?",
    		  text: "삭제한 리뷰는 취소할 수 없습니다!",
    		  icon: "warning",
    		  showCancelButton: true,
    		  cancelButtonText : "취소",
    		  confirmButtonColor: "#3085d6",
    		  cancelButtonColor: "#d33",
    		  confirmButtonText: "확인"
    		}).then((result) => {
    		  if (result.isConfirmed) {
    			  $.ajax({
    				  url: '/miniproject/mypage/deleteReview',
    				  method: 'POST',
    				  data: {reviewId: reviewId},
    				  success: function(response){
    					  Swal.fire({
    		    		      title: '삭제 성공!',
    		    		      icon: 'success'
    		    		    }).then(()=>{
    		    		    	getContent('orderList');
    		    		    });
    				  },
    				  
    				  error: function(error){
    					  Swal.fire({
    		    		      title: '삭제 실패!',
    		    		      icon: 'error'
    		    		    });
    				  }
    			  })
    		  }
    		});
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
    	console.log("reviewId:", reviewId);
    	
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
    			Swal.fire({
    				  icon: 'success',                   
    				  title: '리뷰 등록이 성공 하였습니다.',    
    				}).then(() => {
    	                getContent('orderList');
    	            });
    		},
    		error: function(data){
    			Swal.fire({
  				  icon: 'error',                   
  				  title: '리뷰 등록이 실패하였습니다.',    
  				});
    			console.log(data);
    		}
    	})

    });
        
});

	$(document).on('click', '.like-icon', function(){
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
    
	$(document).on('click', '.cart-icon', function(event){
		let productId = $(this).data('pid')
		$.ajax({
			url: "/miniproject/order/addBasket",
			type: "get",
			data:{productId: productId},
			success: function(response){
				console.log(response);
				if(response == "notLogin"){
					window.location.href="account/loginForm";
				}
				if(response == "successAdd"){
					console.log("successAdd로 왔음");
					Swal.fire({
						html : "장바구니에 상품이 담겼습니다.<br>장바구니로 이동하시겠습니까?",
						cancelButtonText : "쇼핑 계속하기",
						confirmButtonText : "장바구니 확인",
						showCancelButton : true,
					}).then(function(result) {
						if (result.isConfirmed) {												
							console.log("이동!");
							window.location.href= "/miniproject/order/basket";
						}else{
							console.log("이동안함!");
						}
					});
				}
				if(response == "exist"){
					console.log("exist 왔음");
					Swal.fire({
						html : "장바구니에 상품이 담겼습니다.<br>장바구니로 이동하시겠습니까?",
						cancelButtonText : "쇼핑 계속하기",
						confirmButtonText : "장바구니 확인",
						showCancelButton : true,
					}).then(function(result) {
						if (result.isConfirmed) {												
							console.log("이동!");
							window.location.href= "/miniproject/order/basket";
						}else{
							console.log("이동안함!");
						}
					});
				}
			},
			error: function(){
				console.log("응답실패");
			}
		})	
	});
    
    zipcodeBtn();

    // 개인정보 수정 유효성 검사
    checkUserTel();
    checkUserEmail();
    checkDetailedAddress();

    // 비밀번호 변경 유효성 검사
    $(document).on('input', '.userPwd', checkPwd);
    $(document).on('input', '.userNewPwd', checkNewPwd);
    $(document).on('input', '.checkUserNewPwd', checkNewPwd);
    $(document).on('input', '.userPwd, .userNewPwd, .checkUserNewPwd', function() {
        checkPwd();
        checkNewPwd();
    });

