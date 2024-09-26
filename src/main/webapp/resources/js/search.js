function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: "smooth",
    });
}

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

function cartNum() {
    $.ajax({
        url: "/miniproject/order/getCartNum", 
        datatype: "text",
        success: function (cartNum) {
        	$(".header-cart-badge").text(cartNum);
        }
    });
}

$('.cart-icon').on('click', function(event){
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
				cartNum();
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
					html : "이미 담긴 상품입니다.<br>장바구니로 이동하시겠습니까?",
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
