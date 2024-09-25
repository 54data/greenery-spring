
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

$(".btn2").on("click", function(){
	productId = $(this).data("pid");
	
	Swal.fire({
		  text: "정말 삭제하시겠습니까?",
		  icon: "warning",
		  showCancelButton: true,
		  confirmButtonColor: "#d33",
		  cancelButtonColor: "#3085d6",
		  confirmButtonText: "삭제",
		  cancelButtonText: "취소"
		}).then((result) => {
		  if (result.isConfirmed) {
			  window.location.href ="/miniproject/admin/deleteProduct?productId="+productId;
		  }
		});
	
});
