//$.ajax({         
//	type: "get",
//	url: "../../content/notices.json",
//	dataType: "json",
//	success: function(data) {
//		console.log("통신성공");
//		let notices = data.notices;
//		let content = $('.content ');
//		notices.forEach(notices => {
//			let noticeItem = `<div class="notices-item">
//			<div class="noticeId">${notices.noticeId}</div>
//			<div class="title">${notices.title}</div>
//			<div class="registrationDate">${notices.registrationDate}</div>
//			</div>`;
//			content.append(noticeItem);
//			content.append(`<div class="divider"></div>`);
//		});
//		
//	},
//	error: function() {
//		console.log("통신에러");
//	}       	  	
//})

$(document).ready(function () {
	$(".title").click(function() {
		location.href="noticeContent";
	})
});
