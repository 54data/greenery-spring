
/* 사진 업로드 */
function previewImage(event, previewId) {
    const file = event.target.files[0]; 
    const imagePreview = document.getElementById(previewId);

    if (file) {
        const reader = new FileReader(); 
        reader.onload = function (e) {
            imagePreview.innerHTML = `<img src="${e.target.result}" />`;
        };
        reader.readAsDataURL(file);
    } else {
        imagePreview.innerHTML = `<span>+</span>`;
    }
}

$('#product-name').on('input', function () {
    var currentLength = $(this).val().length;

    var maxLength = 250;
    if (currentLength > maxLength) {
        $(this).val($(this).val().substring(0, maxLength)); // 최대 길이 초과 시 자르기
        currentLength = maxLength; // 현재 길이 업데이트
    }

    $('#charCount').text(currentLength + " / " + maxLength);
});
    
var toggleElement1 = document.getElementById('product');
var toggleElement2 = document.getElementById('notice')
var collapseElement1 = document.getElementById('home-collapse1');
var collapseElement2 = document.getElementById('home-collapse2');

document.addEventListener('DOMContentLoaded', function() {

	toggleElement1.addEventListener('click', function() {
		var bsCollapse = new bootstrap.Collapse(collapseElement1, {
			toggle: true
		});
		bsCollapse.toggle();
	});

	toggleElement2.addEventListener('click', function() {
		var bsCollapse = new bootstrap.Collapse(collapseElement2, {
			toggle: true
		});
		bsCollapse.toggle();
	});
});

    



