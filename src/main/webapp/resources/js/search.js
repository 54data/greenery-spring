/*// 헤더, 푸터 파일 로드
$(document).ready(function () {
    $("#header").load("../header/header.html");
    $("#footer").load("../footer/footer.html");

    $.getJSON("../../content/products.json", function (data) {
        dataToHtml(data.products);
    }).fail(function () {
        console.error("JSON 파일을 불러오는 데 실패함");
    });

    handleQueryParams();
});*/

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

/*function dataToHtml(products) {
    if (Array.isArray(products)) {
        products.forEach(product => {
            const productHtml = `
            <div class="product-item">
                <div class="product-image-container">
                    <img src="${product.imageUrls[0]}" alt="${
                product.productName
            }" class="product-image">
                    <div class="product-icons">
                        <span class="icon like-icon">
                            <img src="../../res/images/heart.png" alt="찜하기 아이콘">
                        </span>
                        <span class="icon cart-icon">
                            <img src="../../res/images/cart_icon2.png" alt="장바구니 아이콘">
                        </span>
                        <span class="icon buy-icon">
                            <img src="../../res/images/dollar.png" alt="구매하기 아이콘">
                        </span>
                    </div>
                </div>
                <div class="product-details">
                    <p class="product-name">${product.productName}</p>
                    <p class="product-description">${
                        product.mainDescription
                    }</p>
                    <p class="product-price"><span class="price-amount">${product.price.toLocaleString()}</span>원</p>
                </div>
            </div>`;

            // 생성한 HTML을 product-container에 추가
            $(".product-container").append(productHtml);
        });
    }
}*/

/*function handleQueryParams() {
    const params = new URLSearchParams(window.location.search);
    const query = params.get("query");
    const category = params.get("category");

    if (query.length >= 1) {
        $(".search-term").html(query);
    }

    if (category) {
        $(".search-result-text").css("display", "none");
        $("#header").after(`<div class="category-result">${category}</div>`);
    }
}*/
