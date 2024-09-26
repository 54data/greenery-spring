function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: "smooth",
    });
}

function changeTotalPrice() {
    let sumPrice = 0;
    $('.product-price').each(function() {
    	sumPrice += $(this).children(".product-total-price").data("totalPrice");
    });
    let discountPrice = $("#discount").data("discount");
    const deliveryPrice = 2500;
    $("#sumPrice").text(sumPrice.toLocaleString() + ' 원');
    const totalPrice = sumPrice + discountPrice + deliveryPrice;
    $("#totalPrice-num").text(totalPrice.toLocaleString());
    $("#totalPrice-num").data("totalPrice", totalPrice);
}

function applyCoupon() {
	if ($("#coupon-select").val() == -1000) {
		$("#discount").data("discount", -1000);
	} 
	$("#discount").text($("#discount").data("discount").toLocaleString() + ' 원');
	changeTotalPrice();
}

function orderProducts() {
	$("#order-button").click(function () {
		let data = {};
		data["orderTotalPrice"] = $("#totalPrice-num").data("totalPrice");
		data["productId"] = Number($(".product").data("pid"));
		data["productQty"] = Number($(".product-amount").data("qty"));
		data["productPrice"] = Number($(".product-total-price").data("totalPrice"));
		data["couponStatus"] = 0;
		if ($("#discount").data("discount") == -1000) {
			data["couponStatus"] = -1000;
		}
		$.ajax({
    		url: "orderProducts",
    		type: "POST",
    		contentType: "application/json",
    		data: JSON.stringify(data),
    		success: function(response) {
    			window.location.href = "order?orderId=" + response;
    		},
    		error: function(response) {
    			console.log("주문 실패");
    		}
    	});
	});
}

$(document).ready(function () {
    scrollToTop();
    changeTotalPrice();
    orderProducts();
    $("#discount").text($("#discount").data("discount").toLocaleString() + ' 원');
});