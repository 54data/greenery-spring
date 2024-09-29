<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>회원가입</title>
	<link href="${pageContext.request.contextPath}/resources/css/signup.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
	<script
		src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/sweetalert2/sweetalert2.min.css">
	<script src="${pageContext.request.contextPath}/resources/sweetalert2/sweetalert2.min.js"></script>
</head>

<body>
	<div class="container-signup">
		<h2>회원가입</h2>
		<img id="iconClose" class="icon-close"
			src="${pageContext.request.contextPath}/resources/image/xIcon.png">
	</div>

	<div class="text-introduce">그리너리 회원이 되어 다양한 혜택을 경험해 보세요!</div>

	<form class="form-signup" name="formSignup">
		<div class="form-id">
			<input id="userId" name="userId" class="input input1" type="text" placeholder="아이디 입력 (6-16자)" autocomplete="off">
			<button id="btnInputId" class="btn btn1" type="button" onclick="checkUserId()">중복확인</button>
		</div>
		<div id="inputIdMessage" class="errorMessage"></div>

		<input id="userPwd" name="userPwd" class="input input2" type="password" placeholder="비밀번호 입력 (알파벳 대소문자, 숫자, 특수문자 포함 8-20자)" autocomplete="off">
		<div id="inputPasswordMessage1" class="errorMessage"></div>
		<input id="checkUserPwd" class="input input3" type="password" placeholder="비밀번호 재입력" autocomplete="off">
		<div id="inputPasswordMessage2" class="errorMessage"></div>
		<input id="userName" name="userName" class="input input4" type="text" placeholder="이름을 입력해주세요." autocomplete="off">
		<div id="inputNameMessage" class="errorMessage"></div>
		<input id="userTel" name="userTel" class="input input5" type="text" placeholder="휴대폰 번호 입력 ('-'제외, 최대 11자리)" autocomplete="off">
		<div id="inputPhoneMessage" class="errorMessage"></div>

		<div class="form-email">
			<input id="userEmail" name="userEmail" class="input input6" type="text" placeholder="이메일 주소" autocomplete="off"> 
		</div>
		<div id="inputEmailMessage" class="errorMessage"></div>

		<div class="form-address">
			<input class="input input8" type="text" name="zipcode" placeholder="우편번호" id="zipcode" autocomplete="off" readonly>
			<button id="btnZipcode" class="btn btn2" type="button">우편번호 찾기</button>
		</div>
		<div id="inputZipcodeMessage" class="errorMessage"></div>

		<input class="input input9" type="text" name="roadAddress" placeholder="도로명 주소" id="roadAddress" autocomplete="off" readonly> 
		<div id="inputRoadAddressMessage" class="errorMessage"></div>
		<input class="input input10" id="detailedAddress" type="text" name="detailedAddress" placeholder="상세 주소" autocomplete="off"> 
		<div id="inputDetailedAddressMessage" class="errorMessage"></div>
		<button class="btn btn3" id="signup-btn" type="button">회원가입</button>
	</form>

	<script src="${pageContext.request.contextPath}/resources/js/signup.js"></script>
</body>

</html>