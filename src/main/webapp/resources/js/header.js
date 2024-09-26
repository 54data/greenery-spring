let textIndex = 0;

function categoryOpen(x) {
    x.classList.toggle("open");
}

function searchTypeText() {
    const placeholderText = "✨딥클렌징으로 모공 속 피지, 싹- 제거!✨";
    if (textIndex < placeholderText.length) {
        $(".header-search-input").attr(
            "placeholder",
            placeholderText.substring(0, textIndex + 1)
        );
        textIndex++;
        setTimeout(searchTypeText, 100);
    } else {
        setTimeout(function () {
            textIndex = 0;
            $(".header-search-input").attr("placeholder", "");
            searchTypeText();
        }, 3000);
    }
}

function cartNum() {
    $.ajax({
        url: "/miniproject/order/getCartNum", 
        datatype: "text",
        success: function (cartNum) {
        	$(".header-cart-badge").text(cartNum);
        }
    });
}

$(document).ready(() => {
	searchTypeText();
	cartNum();
});