const Toast = Swal.mixin({
    toast: true,
    position: 'center',
    showConfirmButton: false,
    timer: 1500,
    timerProgressBar: true,
    didOpen: (toast) => {
    	toast.style.width = '350px';
    	toast.style.fontSize = '14px';
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
    }
});

function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: "smooth",
    });
}

function changeTotalPrice() {
    var sumPrice = 0;
    $('.product-price').each(function() {
    	if ($(this).siblings(".product-checkbox").prop("checked")) {
    		sumPrice += Number($(this).children(".product-total-price").data("totalPrice"));
    	}
    });
    const deliveryPrice = 2500;
    $("#sumPrice").text(sumPrice.toLocaleString() + ' 원');
    $("#totalPrice-num").text((sumPrice + deliveryPrice).toLocaleString());
}

function getSelectedProducts() {
	var selectedProductList = [];
    $('.product-checkbox').each(function() {	    	
    	if ($(this).prop("checked")) {
    		selectedProductList.push($(this).data("pid"));
    	};
    });
    return selectedProductList;
}

function deleteProducts() {;
	$('.selected-delete-btn').click(function () {
    	$.ajax({
    		url: "deleteBasketProducts",
    		type: "POST",
    		contentType: "application/json",
    		data: JSON.stringify(getSelectedProducts()),
    		success: function(response) {
    			console.log("선택 상품 삭제 완료");
    			window.location.href = "../order/basket";
    		}
    	});
	});
}

function orderSelectedProducts() {
	$("#order-button").click(function () {
		if ($(".product").length == 0) {
			Toast.fire({
			    icon: 'error',
			    title: '장바구니에 담긴 상품이 없습니다.'
			});
			return;
		}
		$.ajax({
    		url: "orderSelectedProducts",
    		type: "POST",
    		contentType: "application/json",
    		data: JSON.stringify(getSelectedProducts()),
    		success: function(response) {
    			console.log("선택 상품 주문 페이지로 이동");
    			window.location.href = "../order/payment";
    		}
    	});
	})
}

$(document).ready(function() {
	$('#allchk').prop('checked', true);
	$('.product-checkbox').prop('checked', true);
	changeTotalPrice();
    
    $('.product-checkbox').each(function() {
    	$(this).click(function() {
    		changeTotalPrice();
    	});
    });
    
	$('#allchk').click(function () {
		let isChecked = $(this).is(':checked'); // 전체 선택 체크박스의 체크 상태를 true, false로 저장
		$('.product-checkbox').prop('checked', isChecked);
		changeTotalPrice();
	});
	
	$('#productList').on('change', '.product-amount', function() {
		let selectedQty = $(this).val();
		if (selectedQty === "직접입력") {
			return;
		}
		let productId = $(this).data("pid");
		let productPrice = $(this).parent().siblings('.product-price').data("price");
		let productTotalPrice = $(this).parent().siblings('.product-price').children(".product-total-price");
		let totalPrice = productPrice * selectedQty;
		productTotalPrice.data("totalPrice", totalPrice);
		productTotalPrice.text(totalPrice.toLocaleString());
		
		changeTotalPrice();
		$.ajax({
			url: "updateQty",
			type: "POST",
			data: {productId : productId, productQty : selectedQty},
			success: function(response) {
				console.log("장바구니 상품 수량 업데이트 완료");
			}
		});
	});
	
	$('.product-amount').on('change', function() {
		const inputAmount = $(this).siblings('.userInput').children('.custom-amount');
		const inputAmountBtn = $(this).siblings('.userInput').children('.custom-amount-btn');

		if ($(this).find("option:selected").text() == '직접입력') {
			inputAmount.show().val(''); 
			inputAmountBtn.show();
            $(this).hide();
		} else {
            inputAmount.hide();
            inputAmountBtn.hide();
            $(this).show();
        }
	});
	
	$('.custom-amount-btn').on('click', function() {
		const inputAmount = $(this).siblings('.custom-amount');
		const inputNum = inputAmount.val();
		const stock = $(this).data('stock');
		if (inputNum > $(this).data('stock')) {
            Swal.fire({
                icon: 'error',
                title: '상품 재고가 부족합니다.',
                text: '현재 해당 상품의 남은 수량은 ' + stock + '개 입니다.',
            });
			return;
		}
		
		const selectList = $(this).parent().siblings('.product-amount');
		const selectAmount = selectList.children('.select-amount')
		if (!Number.isNaN(inputNum) && inputNum !== '') {
			$(this).hide();
			$(this).siblings('.custom-amount').hide();
			selectList.show();
			selectAmount.val(inputNum);
			selectAmount.text(inputNum);
			selectAmount.prop('selected', true);
			selectList.trigger('change');
		}
	});
	
    $('.product-amount').each(function() {
        let productQty = $(this).data("qty"); 
        $(this).val(productQty); 
    });
		
	deleteProducts();
	orderSelectedProducts();
});