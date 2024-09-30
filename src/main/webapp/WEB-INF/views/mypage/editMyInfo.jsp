<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sweetalert2/sweetalert2.min.css">
<script src="${pageContext.request.contextPath}/resources/sweetalert2/sweetalert2.min.js"></script>		
		
<div class="mypage-myinfo-title">개인정보 수정</div>
<form class="info-change-form" method="post">
	<div class="myinfo-list">
		<div class="myinfo-id">
			<span class="myinfo-col">아이디</span>
			<div class="myinfo-id-row">
				<span>${user.userId}</span>
				<button type="button" class="deactivate-btn">계정 비활성화</button>
			</div>
		</div>
		<div class="myinfo-name">
			<span class="myinfo-col">이름</span>
			<span>${user.userName}</span>
		</div>
		<div class="myinfo-phone">
			<span class="myinfo-col">전화번호</span>
			<div class="inputMessage">
				<input type="text" class="userTelInput" name="userTel" value="${user.userTel}" >
				<div id="inputPhoneMessage" class="errorMessage"></div>
			</div>
		</div>
		<div class="myinfo-email">
			<span class="myinfo-col">이메일</span>
			<div class="inputMessage">
				<input type="text" class="userEmailInput" name="userEmail" value="${user.userEmail}" >
				<div id="inputEmailMessage" class="errorMessage"></div>
			</div>
		</div>
		<div class="myinfo-address">
			<span class="myinfo-col">주소</span>
			<div class="address">
				<div class="address-zipcode">
					<input type="text" name="zipcode" value="${user.zipcode}" readonly>
					<button id="btnZipcode" type="button">우편번호 찾기</button>
				</div>
				<input type="text" name="roadAddress" value="${user.roadAddress}" readonly>
				<input type="text" class="userDetailedAddress" name="detailedAddress" value="${user.detailedAddress}" >
				<div id="inputDetailedAddressMessage" class="errorMessage"></div>
			</div>
		</div>
	</div>
	<div class="myinfo-edit">
		<button type="button" class="myinfo-edit-cancel-btn" onclick="location.href='${pageContext.request.contextPath}'">취소</button>
        <button type="button" class="myinfo-edit-btn" onclick="updateUserInfo()">저장</button>
	</div>
</form>

<div class="mypage-myinfo-title">비밀번호 변경</div>
<span class="pwd-change-info">
	개인정보 보호를 위해 비밀번호는 90일마다 변경해주세요. <br>
	타인에게 비밀번호가 노출되지 않도록 주의해 주세요. <br>
	비밀번호 변경시, 서비스에서 자동 로그아웃됩니다.
</span>
<form class="pwd-change-form" method="post">
	<div class="pwd-change">
		<div class="current-pwd">
			<span class="myinfo-col">현재 비밀번호</span>
			<div class="inputMessage">
				<input type="password" class="userPwd" >
				<div id="pwdMessage" class="pwdErrorMessage"></div>
			</div>
		</div>
		<div class="change-pwd">
			<span class="myinfo-col">새 비밀번호</span>
			<div class="inputMessage">
				<input type="password" class="userNewPwd" >
				<div id="inputPwdMessage" class="pwdErrorMessage"></div>
			</div>
		</div>
		<div class="change-check-pwd">
			<span class="myinfo-col">새 비밀번호 확인</span>
			<div class="inputMessage">
				<input type="password" class="checkUserNewPwd" >
				<div id="inputCheckPwdMessage" class="pwdErrorMessage"></div>
			</div>
			<button type="button" class="pwd-change-btn" onclick="updateUserPwd()">확인</button>
		</div>
	</div>
</form>