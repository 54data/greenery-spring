<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div class="mypage-myinfo-title">개인정보 수정</div>
<form class="myinfo-list">
	<div class="myinfo-id">
		<span>아이디</span>
		<span>${user.userId}</span>
	</div>
	<div class="myinfo-name">
		<span>이름</span>
		<span>${user.userName}</span>
	</div>
	<div class="myinfo-phone">
		<span>전화번호</span>
		<input type="text" value="${user.userTel}">
	</div>
	<div class="myinfo-address">
		<span>주소</span>
		<input type="text" value="${user.detailedAddress}" disabled>
	</div>
	<div class="myinfo-email">
		<span>이메일</span>
		<input type="text" value="${user.userEmail}">
	</div>
	<div class="myinfo-edit">
		<button type="button" class="myinfo-edit-cancel-btn" onclick="location.href='${pageContext.request.contextPath}'">취소</button>
		<button type="submit" class="myinfo-edit-btn">저장</button>
	</div>
</form>