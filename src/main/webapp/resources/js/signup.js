let userId = document.querySelector('#userId');
userId.addEventListener('input', inputIdCheck);

function inputIdCheck() {
    let inputIdMessage = document.querySelector('#inputIdMessage');
    let regExp = RegExp(/^[a-zA-Z0-9_]{6,16}$/);
    if (regExp.test(userId.value)) {
        inputIdMessage.innerHTML =  ''; 
    } else if (userId.value === '') {
    	inputIdMessage.innerHTML = "<span>해당 입력값은 필수입니다.</span>";
    } else {
        inputIdMessage.innerHTML = 
        "<span>아이디는 6자 이상 16자 이하만 가능합니다. (숫자, 알파벳, _ 만 가능)</span>";
    }
}

let userPwd = document.querySelector('#userPwd');
let checkUserPwd = document.querySelector('#checkUserPwd');
userPwd.addEventListener('input', inputPasswordCheck);
checkUserPwd.addEventListener('input', inputPasswordCheck);

function inputPasswordCheck() {
    let inputPasswordMessage1 = document.querySelector('#inputPasswordMessage1');
    let inputPasswordMessage2 = document.querySelector('#inputPasswordMessage2');

    let regExp = RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&_])[A-Za-z\d@$!%*?&_]{8,20}$/);
    if (regExp.test(userPwd.value)) {
    	inputPasswordMessage1.innerHTML =  ''; 
    } else if (userPwd.value === '') {
    	inputPasswordMessage1.innerHTML = "<span>해당 입력값은 필수입니다.</span>";
    } else {
        inputPasswordMessage1.innerHTML = 
        "<span>8자 이상 20자 이하의 알파벳 대소문자, 숫자, 특수문자를 조합해주세요.</span>";
    }
    
    if (userPwd.value === checkUserPwd.value) {
        inputPasswordMessage2.innerHTML =  '';
    } else if (checkUserPwd.value === '') {
    	inputPasswordMessage2.innerHTML = "<span>해당 입력값은 필수입니다.</span>";
    } else {
        inputPasswordMessage2.innerHTML =
        "<span>비밀번호를 확인해주세요.</span>";
    }
}

let inputName = document.querySelector('#userName');
inputName.addEventListener('input', inputNameCheck);

function inputNameCheck() {
    let inputNameMessage = document.querySelector('#inputNameMessage');

    let regExp = RegExp(/^[가-힣a-zA-Z]{1,20}$/);
    if (regExp.test(inputName.value)) {
        inputNameMessage.innerHTML =  ''; 
    } else if (inputName.value === '') {
    	inputNameMessage.innerHTML = "<span>해당 입력값은 필수입니다.</span>";
    } else {
        inputNameMessage.innerHTML = 
        "<span>영문 또는 한글로 입력해주세요.</span>";
    }
}

let inputPhone = document.querySelector('#userTel');
inputPhone.addEventListener('input', inputPhoneCheck);

function inputPhoneCheck() {
    let inputPhoneMessage = document.querySelector('#inputPhoneMessage');

    let regExp = RegExp(/^(01[016789]{1})[0-9]{3,4}[0-9]{4}$/);
    if (regExp.test(inputPhone.value)) {
        inputPhoneMessage.innerHTML =  ''; 
    } else if (inputPhone.value === '') {
    	inputPhoneMessage.innerHTML = "<span>해당 입력값은 필수입니다.</span>";
    } else {
        inputPhoneMessage.innerHTML = 
        "<span>유효하지 않은 전화번호입니다. (숫자만 입력)</span>";
    }
}

let inputEmail = document.querySelector('#userEmail');
inputEmail.addEventListener('input', inputEmailCheck);

function inputEmailCheck() {
    let inputEmailMessage = document.querySelector('#inputEmailMessage');

    let regExp = RegExp(/^[^\s@]+@[^\s@]+\.[^\s@]+$/);
    if (regExp.test(inputEmail.value)) {
        inputEmailMessage.innerHTML =  ''; 
    } else if (inputEmail.value === '') {
    	inputEmailMessage.innerHTML = "<span>해당 입력값은 필수입니다.</span>";
    } else {
        inputEmailMessage.innerHTML = 
        "<span>이메일 입력을 확인해주세요.</span>";
    }
}

let btnZipcode = document.querySelector('#btnZipcode');
btnZipcode.addEventListener('click', () => {
        new daum.Postcode({
            oncomplete: function(data) {
                let fullAddr = '';
                let extraAddr = '';

                if (data.userSelectedType === 'R') {
                    fullAddr = data.roadAddress;
                } else {
                    fullAddr = data.jibunAddress;
                }

                if (data.userSelectedType = 'R') {
                    if(data.bname !== '') {
                        extraAddr += data.bname
                    }
                    if (data.buildingName !== '') {
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    fullAddr += (extraAddr !== '' ? '(' + extraAddr + ')' : '');
                }

                document.formSignup.zipcode.value = data.zonecode;
                document.formSignup.roadAddress.value = fullAddr;
                
                validateZipcode();
            }
        }).open();
    }
);

document.querySelector('#iconClose').addEventListener('click', function() {
    window.location.href = '../';
})

let isIdChecked = false;

const Toast = Swal.mixin({
    toast: true,
    position: 'top',
    showConfirmButton: false,
    timer: 2500,
    timerProgressBar: true,
    didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer)
        toast.addEventListener('mouseleave', Swal.resumeTimer)
    }
});

function checkUserId() {
	let userId = $('#userId').val();
	$.ajax({
		url: "userIdCheck",
		type: "post",
        contentType: "application/x-www-form-urlencoded", 
        dataType: "json", 
		data: {userId : userId},
		success: function(checkResult) {
			if (checkResult) {
				isIdChecked = true; 
				Toast.fire({
				    icon: 'success',
				    title: '사용 가능한 아이디입니다.'
				});
			} else {
				isIdChecked = false;
				Toast.fire({
				    icon: 'error',
				    title: '이미 존재하는 아이디입니다.'
				});
			}
		}
	});
}

function checkIdStatus() {
	if (!isIdChecked) {
		Toast.fire({
		    icon: 'error',
		    title: '아이디 중복체크는 필수입니다.'
		});
		return false;
	}
	return true;
}

function isValid() {
	let errorMessageStatus = false;
	$('.errorMessage').each(function () {
		if ($(this).children('span').length != 0) {
			errorMessageStatus = true;
			return false;
		}
	});
	return errorMessageStatus;
}

function signup() {
	const signupData = $('.form-signup').serialize();
	
	if (isValid()) {
		Toast.fire({
		    icon: 'error',
		    title: '입력값을 확인해주세요.'
		});
		return;
	}

    let hasEmptyField = false; 
    $('input').each(function() {
        if ($(this).val().trim() === '') { 
            hasEmptyField = true;
            return false;
        }
    });

    if (hasEmptyField) {
        Toast.fire({
            icon: 'error',
            title: '모든 입력값은 필수입니다.'
        });
        return;
    }
	
	if (checkIdStatus()) {
		$.ajax({
			url: 'signup',
			type: 'post',
			data: signupData,
			success: function(signupResult) {
				if (signupResult) {
					Swal.fire({
					    title: '회원가입이 완료되었습니다.',
					    icon: 'success',
						cancelButtonText: '로그인하기',
						confirmButtonText: '홈으로 가기',
						showCancelButton : true,
					}).then(function(result) {
						if (result.isConfirmed) {												
							window.location.href = '../';
						} else {
							window.location.href = '../account/loginForm';
						}
					});
				}
			}
		});
	}
}

function validateZipcode() {
    const inputValue = $('#zipcode').val().trim();
    const zipcodeRegExp = /^\d{5}$/; 

    if (!zipcodeRegExp.test(inputValue)) {
        $("#inputZipcodeMessage").html("<span>유효하지 않은 우편번호입니다.</span>");
    } else if (inputValue === '') {
        $("#inputZipcodeMessage").html("<span>해당 입력 값은 필수입니다.</span>");
    } else {
        $("#inputZipcodeMessage").html('');
    }
}

$(document).ready(function () {
	$('#userId').on('input', function() {
	    isIdChecked = false;
	});
	
	$('#signup-btn').on('click', function() {
		signup();
	});
	
	$('#zipcode').on('input', validateZipcode);
	
	$('#roadAddress').on('input', function() {
	    if ($(this).val() === '') {
			$("#inputRoadAddressMessage").html("<span>해당 입력 값은 필수입니다.</span>");
		} else {
			$("#inputRoadAddressMessage").html('');
		}
	});
	
	$('#detailedAddress').on('input', function() {
	    if ($(this).val() === '') {
			$("#inputDetailedAddressMessage").html("<span>해당 입력 값은 필수입니다.</span>");
		} else {
			$("#inputDetailedAddressMessage").html('');
		}
	});
});
