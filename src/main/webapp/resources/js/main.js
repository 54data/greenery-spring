let slideIndex = 1;     // 슬라이드 인덱스
let products = [];

// ---------------- 슬라이드 관련 함수 ----------------
function autoSlides() {     // 슬라이드 자동으로 넘김
    showSlides(slideIndex);

    // 자동 슬라이드
    setInterval(() => {
        plusSlides(1);
    }, 7000); // 7초마다 슬라이드 변경
}

function plusSlides(n) {    // 슬라이드 인덱스를 +1 시켜서 다음 슬라이드로 넘김
    showSlides((slideIndex += n));
}

function currentSlide(n) {      // dots를 클릭하면 인덱스에 해당하는 현재 슬라이드 보여줌 
    showSlides((slideIndex = n));
}

function showSlides(n) {
    let slides = document.getElementsByClassName("banner_slides");
    let dots = document.getElementsByClassName("banner-indicator-btn"); // 배너에서 동그라미 버튼
    if (n > slides.length) {        // 슬라이드가 전체 길이보다 커지면 첫번째 슬라이드로 다시 넘어감
        slideIndex = 1;
    }
    if (n < 1) {
        slideIndex = slides.length;
    }
    Array.from(slides).forEach(slide => (slide.style.display = "none"));    // 다른 배너 상태 none
    Array.from(dots).forEach(dot => dot.classList.remove("active"));        // 다른 배너 active해제
    slides[slideIndex - 1].style.display = "block";     //none 상태를 다시 block으로 해서 보이게
    dots[slideIndex - 1].className += " active";
}

// 배너 밑의 카테고리들 
function clickCategoryBtn(target) {
    let categoryBtns = $(".toolbar-category").children("button");   // 하위 요소인 버튼들을 다 가져옴
    Array.from(categoryBtns).forEach(categoryBtn =>
        categoryBtn.classList.remove("active-category") // 클릭되지 않은 버튼 액티브 지움(비활성화)
    );
    target.addClass("active-category"); // 현재 클릭한 버튼 액티브 추가(활성화)
}

// 스크롤 올리기
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: "smooth",
    });
}

// -------------------팝업- 오늘 하루 보지 않기-------------------
// 로컬 스토리지에 현재 시간 + 1일을 ms 단위로 저장
function setTime(name, exp) {
    let date = new Date();
    date = date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000); // 일 단위를 ms 단위로 변환하여 더하기(내일 시간을 넣어줌)
    localStorage.setItem(name, date);
    $(".modal-container").css("display", "none");   // 오늘 하루 보지 않기 누르면 display none으로 해서 안보이게
}

// 하루를 넘겼는지 여부 확인
function isOverExp(name) {
    const status = parseInt(localStorage.getItem(name)) > new Date().getTime();
    const modal = $(".modal-container");
    if (!status) {
        localStorage.removeItem(name);
        modal.css("display", "block"); // 하루가 지난 상태 => 보임
    }
}

// 쿠폰 받기 시 알림창
function showAlertCoupon(couponStatus) {
    $(".modal-container").hide();
    $(".alert-coupon").addClass("show");
    let alertCouponText = "";
    if (couponStatus == "0") {
    	alertCouponText = "쿠폰 발급이 완료되었습니다!";
    } else {
    	alertCouponText = "이미 발급받은 쿠폰입니다.";
    }
    $(".alert-coupon").children(".alert-coupon-text").text(alertCouponText);
    setTimeout(() => {
        $(".alert-coupon").removeClass("show");
    }, 2000); 
}

/*function cartNum() {
    $.ajax({
        url: "/miniproject/order/getCartNum", 
        datatype: "text",
        success: function (cartNum) {
        	$(".header-cart-badge").text(cartNum);
        }
    });
}*/

$(document).ready(function () {
    autoSlides();
    scrollToTop();
    isOverExp("TodayCloseTime");
    
    $(".close").click(() => {
        $(".modal-container").hide();
    });
    $(".today-close").click(() => {
        setTime("TodayCloseTime", 1);
    });
    
    
    $(document).on('click', '.modal-image', function () {
		$.ajax({
			url: "recieveCoupon",
			success: function(couponStatus) {
				showAlertCoupon(couponStatus);
			},
	        error: function(xhr, status, error) {
	        	window.location.href = "account/loginForm";
	        }
		});
    });
});
