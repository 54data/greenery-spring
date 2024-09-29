let slideIndex = 1;    
let products = [];

function autoSlides() {     
    showSlides(slideIndex);

    setInterval(() => {
        plusSlides(1);
    }, 7000); 
}

function plusSlides(n) {    
    showSlides((slideIndex += n));
}

function currentSlide(n) {      
    showSlides((slideIndex = n));
}

function showSlides(n) {
    let slides = document.getElementsByClassName("banner_slides");
    let dots = document.getElementsByClassName("banner-indicator-btn");
    if (n > slides.length) {       
        slideIndex = 1;
    }
    if (n < 1) {
        slideIndex = slides.length;
    }
    Array.from(slides).forEach(slide => (slide.style.display = "none"));    
    Array.from(dots).forEach(dot => dot.classList.remove("active"));        
    slides[slideIndex - 1].style.display = "block";    
    dots[slideIndex - 1].className += " active";
}

function clickCategoryBtn(target) {
    let categoryBtns = $(".toolbar-category").children("button");   
    Array.from(categoryBtns).forEach(categoryBtn =>
        categoryBtn.classList.remove("active-category") 
    );
    target.addClass("active-category"); 
}

function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: "smooth",
    });
}

function setTime(name, exp) {
    let date = new Date();
    date = date.setTime(date.getTime() + exp * 24 * 60 * 60 * 1000); 
    localStorage.setItem(name, date);
    $(".modal-container").css("display", "none");
}

function isOverExp(name) {
    const status = parseInt(localStorage.getItem(name)) > new Date().getTime();
    const modal = $(".modal-container");
    if (!status) {
        localStorage.removeItem(name);
        modal.css("display", "block"); 
    }
}

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
