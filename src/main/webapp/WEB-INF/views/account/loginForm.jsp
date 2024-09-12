<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>로그인</title>
	<link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet" type="text/css"/>
	<script src="${pageContext.request.contextPath}/resources/jquery/jquery.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css" />
</head>

<body>
	<div class="container-login">
		<h2 class="text-login">로그인</h2>
		<a href="${pageContext.request.contextPath}"> <img id="IconClose"
			class="icon-close" src="${pageContext.request.contextPath}/resources/image/xIcon.png">
		</a>
	</div>

	<div class="text-introduce">그리너리의 다양한 서비스와 혜택을 누리세요</div>

	<div class="container-form1">
		<div class="container-form2">
    		<c:if test="${SPRING_SECURITY_LAST_EXCEPTION.message == 'Bad credentials'}">
    			아이디 또는 비밀번호가 틀립니다.
    		</c:if>
			<form method="post" action="${pageContext.request.contextPath}/login">
				<input id="inputId" class="input-idpassword" type="text"
					name="userId" placeholder="아이디 입력" required><br>
				<div id="inputIdMessage"></div>
				<input id="inputPassword1" class="input-idpassword" type="password"
					name="userPwd" placeholder="비밀번호 입력(영문, 숫자, 특수문자 조합)" required><br>
				<div id="inputPasswordMessage1"></div>
				<!-- <input id="btnLogin" class="btn-login" type="submit" value="로그인"> -->
				<button id="btnLogin" class="btn-login" type="submit">로그인</button>
			</form>
			<button id="boxSignup" class="box-signup" onclick="location.href='signupForm'">
				<div>아직 회원이 아니신가요?</div>
				<div>
					회원가입 <img src="${pageContext.request.contextPath}/resources/image/arrowIcon.png">
				</div>
			</button>
<!-- 			<div class="admin-login"> -->
<!-- 				<input id="checkBox" type="checkbox"> -->
<!-- 				<div>관리자로그인</div> -->
<!-- 			</div> -->
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/resources/js/login.js">
	</script>
</body>

</html>