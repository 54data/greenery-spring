let slideIndex = 1;
showSlides(slideIndex);

let selectedImg = null;

function currentSlide(n, elem) {
    showSlides(slideIndex = n);
    if (selectedImg) {
    	$(selectedImg).css('border', 'none');
    }
    $(elem).css('border', '1px solid #000');
    selectedImg = elem;
}

function showSlides(n) {
    let i;
    let slides = document.getElementsByClassName("mySlides");
    let dots = document.getElementsByClassName("dot");
    if (n > slides.length) { slideIndex = 1 }
    if (n < 1) { slideIndex = slides.length }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex - 1].style.display = "block";
    dots[slideIndex - 1].className += " active";
}

// ---------------------탭 - 상세정보,  리뷰 --------------------

$(document).ready(function(){
    loadTabContent('detailInfo', $('#productId').val());
    $('.tab-button').first().addClass('active');
});


function loadTabContent(tabName, productId, pageNo) {
    console.log("loadTabContent 호출됨, productId: ", productId);
    console.log("pageNo 잘 넘어가는지?: ", pageNo);

    $.ajax({
        url: "/miniproject/product/" + tabName,  
        type: "GET",
        data: { productId: productId, pageNo: pageNo },  
        success: function(response) {
            console.log("Ajax 성공", response);
            $('#tab-content').html(response);  
            
        },
        error: function(xhr, status, errorThrown) {
            console.error("Ajax 오류 발생: ", errorThrown);
            console.log("xhr: ", xhr);
            console.log("status: ", status);
        }
    });
}


// 탭 버튼 클릭 시 색상 변경
document.addEventListener('DOMContentLoaded', function() {
	const buttons = document.querySelectorAll('.tab-button');
	const dividers = document.querySelectorAll('.divider');
	
	buttons.forEach(button => {
		button.addEventListener('click', () => {
			buttons.forEach(btn => btn.classList.remove('active'));
			dividers.forEach(div => {
				div.style.backgroundColor = '#a9a9a9';
				div.style.height = '0.2px';
			});
			
			button.classList.add('active');
			
			const index = Array.from(buttons).indexOf(button);
			const activeDivider = dividers[index];
			activeDivider.style.backgroundColor = 'black';
			activeDivider.style.height = '2px';
		});
	});
});

/* 수량 조절 */
function increase(button) {
    const productDiv = button.closest('.product-info'); 
    if (!productDiv) return; 

    const quantitySpan = productDiv.querySelector('.quantity-number');
    const priceSpan = productDiv.querySelector('.product-price');

    if (!quantitySpan || !priceSpan) return; 

    let quantity = parseInt(quantitySpan.innerText);
    quantity += 1;
    quantitySpan.innerText = quantity;
    const pricePerUnit = parseFloat(priceSpan.getAttribute('data-price'));
    const totalPrice = (pricePerUnit * quantity).toLocaleString() + ' 원';
    
    quantitySpan.setAttribute('data-stock', quantity);
    priceSpan.innerText = totalPrice; 
    let herf = $('.checkout')[0].getAttribute('onClick')
    href = herf.slice(0, herf.lastIndexOf('='));
    $('.checkout')[0].setAttribute('onClick', href + '=' + quantity + "'");
}

function decrease(button) {
    const productDiv = button.closest('.product-info'); 
    if (!productDiv) return; 

    const quantitySpan = productDiv.querySelector('.quantity-number');
    const priceSpan = productDiv.querySelector('.product-price');

    if (!quantitySpan || !priceSpan) return; 

    let quantity = parseInt(quantitySpan.innerText);
    quantity -= 1;
    if (quantity < 1) {
        quantity = 1;
    }
    quantitySpan.innerText = quantity;

    const pricePerUnit = parseFloat(priceSpan.getAttribute('data-price'));
    const totalPrice = (pricePerUnit * quantity).toLocaleString() + ' 원';
    priceSpan.innerText = totalPrice; // 가격 업데이트
    let herf = $('.checkout')[0].getAttribute('onClick').slice(0, url.lastIndexOf('='));
    $('.checkout')[0].setAttribute('onClick', href + '=' + quantity + "'");
}

// 결제 페이지로 이동 시 호출되는 함수
function checkout() {
    const cartItems = [];
    for (let i = 0; i < localStorage.length; i++) {
        const key = localStorage.key(i);
        const item = JSON.parse(localStorage.getItem(key));
        cartItems.push(item);
    }

    sessionStorage.setItem('checkout', JSON.stringify(checkout));
    window.location.href = '../order/payment'; // 결제 페이지로 이동 
}


function cart() {
    const cartItems = [];
    for (let i = 0; i < localStorage.length; i++) {
        const key = localStorage.key(i);
        const item = JSON.parse(localStorage.getItem(key));
        cartItems.push(item);
    }
    sessionStorage.setItem('add-to-cart', JSON.stringify(cart));
    window.location.href = '../order/basket'; // 장바구니 페이지로 이동 
}

function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: "smooth",
    });
}

$(".wishlist-button").on("click", function() {
	productId = $(this).data("pid");
	let heartIcon = $(this).find("img");
	
	$.ajax({
		url: "/miniproject/product/Wishlist",
		type: "get",
		data: { productId: productId },
		success: function(response){
			if(response == "notLogin"){
				location.href = "/miniproject/account/loginForm";
			}
			if(response == "fill"){
				heartIcon.attr("src", "/miniproject/resources/image/fullheart-icon.png");
			}
			if(response == "empty"){
				heartIcon.attr("src", "/miniproject/resources/image/heart-icon.png")
			}
		},
		error: function() {
			console.log("전송 실패")
		}
	})
})