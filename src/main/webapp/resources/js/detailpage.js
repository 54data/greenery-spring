/*// 헤더, 푸터 파일 로드
$(document).ready(function () {
    $("#header").load("../header/header.html");
    $("#footer").load("../footer/footer.html");

    $.getJSON("../../content/products.json", function (data) {
        dataToHtml(data.products);
    }).fail(function () {
        console.error("JSON 파일을 불러오는 데 실패함");
    });
});
*/
// 찜 추가
const wishlistButton = document.querySelector('.wishlist-button');

wishlistButton.addEventListener('click', function () {
    const productDiv = this.closest('.product-info');
    const productNameSpan = document.querySelector('.product-title');

    if (!productNameSpan) return;
    const productName = productNameSpan.innerText.trim();

    this.classList.toggle('active'); // active 클래스를 토글

    // 추가 기능: 위시리스트에 아이템 추가/제거 로직 구현 가능
    if (this.classList.contains('active')) {
        console.log("아이템이 위시리스트에 추가되었습니다.");
        saveToWishlist(productName);
    } else {
        console.log("아이템이 위시리스트에서 제거되었습니다.");
        removeFromWishlist(productName);
    }
});
//
//function saveToWishlist(productName) {
//    let wishlist = JSON.parse(localStorage.getItem('whislist')) || [];
//    if (!wishlist.includes(productName)) {
//        wishlist.push(productName);
//    }
//    localStorage.setItem('wishlist', JSON.stringify(wishlist));
//    console.log(위시리스트에 저장된 아이템: ${wishlist.join(', ')});
//}
//
//function removeFromWishlist(productName) {
//    let wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
//    wishlist = wishlist.filter(item => item !== productName);
//    localStorage.setItem('wishlist', JSON.stringify(wishlist));
//    console.log(위시리스트에서 제거된 아이템: ${productName});
//}
//
/* 이미지 스크롤 */
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
    /* let captionText = document.getElementById("caption"); */
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
    const productDiv = button.closest('.product-info'); // 부모 요소 찾기
    if (!productDiv) return; // productDiv가 null인 경우 함수 종료

    const quantitySpan = productDiv.querySelector('.quantity-number');
    const priceSpan = productDiv.querySelector('.product-price');

    if (!quantitySpan || !priceSpan) return; // 요소가 없으면 함수 종료

    let quantity = parseInt(quantitySpan.innerText);
    quantity += 1;
    quantitySpan.innerText = quantity;

    /* 가격 업데이트 */
    const pricePerUnit = parseFloat(priceSpan.getAttribute('data-price'));
    const totalPrice = (pricePerUnit * quantity).toLocaleString() + '원';
    priceSpan.innerText = totalPrice; // 가격 업데이트

    // 해당 정보 로컬 스토리지에 저장
    saveToLocalStorage(productDiv);
    console.log("수량 증가");
}



function decrease(button) {
    const productDiv = button.closest('.product-info'); // 부모 요소 찾기
    if (!productDiv) return; // productDiv가 null인 경우 함수 종료

    const quantitySpan = productDiv.querySelector('.quantity-number');
    const priceSpan = productDiv.querySelector('.product-price');

    if (!quantitySpan || !priceSpan) return; // 요소가 없으면 함수 종료

    let quantity = parseInt(quantitySpan.innerText);
    quantity -= 1;
    if (quantity < 1) {
        quantity = 1;
    }
    quantitySpan.innerText = quantity;

    /* 가격 업데이트 */
    const pricePerUnit = parseFloat(priceSpan.getAttribute('data-price'));
    const totalPrice = (pricePerUnit * quantity).toLocaleString() + '원';
    priceSpan.innerText = totalPrice; // 가격 업데이트

    // 해당 정보 로컬 스토리지에 저장
    saveToLocalStorage(productDiv);
}

// 로컬 스토리지에 저장하는 함수
//function saveToLocalStorage(productDiv) {
//    const quantitySpan = document.querySelector('.quantity-number');
//    const priceSpan = document.querySelector('.product-price');
//    const productNameSpan = document.querySelector('.product-title');
//
//    if (!quantitySpan || !priceSpan || !productNameSpan) return; // 요소가 없으면 함수 종료
//
//    const productName = productNameSpan.innerText.trim(); // 상품명
//    const quantity = parseInt(quantitySpan.innerText); // 수량
//    const price = priceSpan.getAttribute('data-price'); // 가격 (숫자형)
//
//    const productInfo = {
//        name: productName,
//        quantity: quantity,
//        price: price
//    };
//
//    // 로컬 스토리지에 저장
//    localStorage.setItem(productName, JSON.stringify(productInfo));
//
//    console.log(저장된 상품: ${productName}, 수량: ${quantity}, 가격: ${price});
//}

// 결제 페이지로 이동 시 호출되는 함수
function checkout() {
    const cartItems = [];
    for (let i = 0; i < localStorage.length; i++) {
        const key = localStorage.key(i);
        const item = JSON.parse(localStorage.getItem(key));
        cartItems.push(item);
    }

    /* document.getElementById('add-to-cart').addEventListener('click', function() {
        window.location.href = '../payment/payment.html'; */
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