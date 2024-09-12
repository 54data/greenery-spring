let userId = document.querySelector('#userId');
userId.addEventListener('input', inputIdCheck);

function inputIdCheck() {
    let inputIdMessage = document.querySelector('#inputIdMessage');

    let regExp = RegExp(/^[A-Za-z\d@$!%*?&]{6,16}$/);
    if (regExp.test(userId.value) || userId.value === '') {
        inputIdMessage.innerHTML =  ''; 
    } else {
        inputIdMessage.innerHTML = 
        "<span style='color:#F03F40; font-size:12px;'>6자 이상 16자 이하로 영문, 숫자, 특수문자를 사용해주세요</span>";
    }
}

let inputPassword1 = document.querySelector('#inputPassword1');
let inputPassword2 = document.querySelector('#inputPassword2');
inputPassword1.addEventListener('input', inputPasswordCheck);
inputPassword2.addEventListener('input', inputPasswordCheck);

function inputPasswordCheck() {
    let userPwd = document.querySelector('#userPwd');
    let inputPasswordMessage2 = document.querySelector('#inputPasswordMessage2');

    let regExp = RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,20}$/);
    if (regExp.test(inputPassword1.value) || inputPassword1.value === '') {
    	userPwd.innerHTML =  ''; 
    } else {
        inputPasswordMessage1.innerHTML = 
        "<span style='color:#F03F40; font-size:12px;'>6자 이상 20자 이하의 대소문자, 숫자, 특수문자를 조합해주세요</span>";
    }
    
    if (document.activeElement === inputPassword2) {
        if (inputPassword1.value === inputPassword2.value || inputPassword2.value === '') {
            inputPasswordMessage2.innerHTML =  '';
        } else {
            inputPasswordMessage2.innerHTML =
            "<span style='color:#F03F40; font-size:12px;'>비밀번호를 확인해주세요</span>";
        }
    }
}

let inputName = document.querySelector('#inputName');
inputName.addEventListener('input', inputNameCheck);

function inputNameCheck() {
    let inputNameMessage = document.querySelector('#inputNameMessage');

    let regExp = RegExp(/^[가-힣a-zA-Z]{1,20}$/);
    if (regExp.test(inputName.value) || inputName.value === '') {
        inputNameMessage.innerHTML =  ''; 
    } else {
        inputNameMessage.innerHTML = 
        "<span style='color:#F03F40; font-size:12px;'>영문 또는 한글로 입력해주세요</span>";
    }
}

let inputPhone = document.querySelector('#inputPhone');
inputPhone.addEventListener('input', inputPhoneCheck);

function inputPhoneCheck() {
    let inputPhoneMessage = document.querySelector('#inputPhoneMessage');

    let regExp = RegExp(/^[0-9]{1,11}$/);
    if (regExp.test(inputPhone.value) || inputPhone.value === '') {
        inputPhoneMessage.innerHTML =  ''; 
    } else {
        inputPhoneMessage.innerHTML = 
        "<span style='color:#F03F40; font-size:12px;'>-하이픈을 제외하고 핸드폰 번호를 입력해주세요</span>";
    }
}

let inputEmail = document.querySelector('#inputEmail')
inputEmail.addEventListener('input', inputEmailCheck);

function inputEmailCheck() {
    let inputEmailMessage = document.querySelector('#inputEmailMessage')

    let regExp = RegExp(/^@{1,}$/);
    if (regExp.test(inputEmail.value) || inputEmail.value === '') {
        inputEmailMessage.innerHTML =  ''; 
    } else {
        inputEmailMessage.innerHTML = 
        "<span style='color:#F03F40; font-size:12px;'>이메일 입력을 확인해주세요</span>";
    }
}

let idCheck = false;
let btnInputId = document.querySelector('#btnInputId');
btnInputId.addEventListener('click', btnInputIdCheck);

function btnInputIdCheck() {
    let regExp = RegExp(/^[A-Za-z\d@$!%*?&]{6,16}$/);
 
    if(userId.value == "") {
        alert('아이디를 입력해주세요.');
        userId.focus();
    } else if(!regExp.test(userId.value)) {
        alert("6자 이상 16자 이하로 영문, 숫자, 특수문자를 사용해주세요");
        userId.value = '';
        userId.focus();
    } else {
        alert("회원가입이 가능한 아이디입니다");
        idCheck = true;
    }
}

let btnZipcode = document.querySelector('#btnZipcode');
btnZipcode.addEventListener('click', () => {
        new daum.Postcode({
            oncomplete: function(data) {
                console.log(data)
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
                document.formSignup.address1.value = fullAddr;
            }
        }).open();
    }
);

document.querySelector('#iconClose').addEventListener('click', function() {
    window.location.href = '../';
})

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
				$('.valid_userID').css("display","block"); 
				$('.invalid_userID').css("display", "none");
			} else {
				$('.valid_userID').css("display","none"); 
				$('.invalid_userID').css("display", "block");
			}
		}
	});
}
