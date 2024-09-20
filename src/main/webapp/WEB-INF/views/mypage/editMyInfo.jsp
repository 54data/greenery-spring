<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div class="mypage-myinfo-title">개인정보 수정</div>
<div class="myinfo-list">
	<div class="myinfo-id">
		<span>아이디</span> <span>${user.userId}</span>
	</div>
	<div class="myinfo-name">
		<span>이름</span> <span>${user.userName}</span>
	</div>
	<div class="myinfo-phone">
		<span>전화번호</span> <span>${user.userTel}</span>
	</div>
	<div class="myinfo-address">
		<span>주소</span> <span>${user.detailedAddress}</span>
	</div>
	<div class="myinfo-email">
		<span>이메일</span> <span>${user.userEmail}</span>
	</div>
	<div class="myinfo-edit">
		<button class="myinfo-edit-btn">개인정보 수정</button>
	</div>
</div>