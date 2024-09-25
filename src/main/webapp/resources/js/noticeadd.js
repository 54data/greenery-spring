function checkValid() {
	if ($("#noticeTitle").val().length > 30) {
		Swal.fire({
			icon : "error",
			title : "오류",
			text : "제목은 띄어쓰기 포함 30글자 이내로 작성해주세요.",
		});
		return false;
	}
	
	if (!$("#noticeTitle").val()) {
		Swal.fire({
			icon : "error",
			title : "오류",
			text : "제목 입력은 필수입니다.",
		});
		return false;
	}
	
	if (!$("#noticeContent").val()) {
		Swal.fire({
			icon : "error",
			title : "오류",
			text : "내용 입력은 필수입니다.",
		});
		return false;
	}
}


function checkNoticeTitle() {
	$(document).on('keyup', '#noticeTitle', function() {
		let contentLength = $(this).val().length;
		$('.titleNum').text(contentLength);
	});
}
	
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
	
	$('.titleNum').text($('#noticeTitle').val().length);
	checkNoticeTitle();
});
