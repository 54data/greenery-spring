function getContent(url) {
    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            $(".mypage-content").append(data);
//            if (url === "likedProducts") {
//                getData();
//            } else if (url === "orderList") {
//                getReview();
//            }
        },
        error: function (err) {
            console.error("Error fetching product data:", err);
        },
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
    	
        var productName = $(this).closest('.order-item-col').find('.item-title').text();
        var productSummary = $(this).closest('.order-item-col').find('.item-desc').text();
        
        console.log("Product ID: " + productId);
        console.log("User ID: " + userId);
        
        $('#reviewModal').find('#productIdInput').val(productId);
        $('#reviewModal').find('#userIdInput').val(userId);
        
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
});