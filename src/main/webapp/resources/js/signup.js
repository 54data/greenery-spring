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

let userPwd = document.querySelector('#userPwd');
let checkUserPwd = document.querySelector('#checkUserPwd');
userPwd.addEventListener('input', inputPasswordCheck);
checkUserPwd.addEventListener('input', inputPasswordCheck);

function inputPasswordCheck() {
    let inputPasswordMessage1 = document.querySelector('#inputPasswordMessage1');
    let inputPasswordMessage2 = document.querySelector('#inputPasswordMessage2');

    let regExp = RegExp(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&_])[A-Za-z\d@$!%*?&_]{8,20}$/);
    if (regExp.test(userPwd.value) || userPwd.value === '') {
    	inputPasswordMessage1.innerHTML =  ''; 
    } else {
        inputPasswordMessage1.innerHTML = 
        "<span style='color:#F03F40; font-size:12px;'>8자 이상 20자 이하의 대소문자, 숫자, 특수문자를 조합해주세요</span>";
    }
    
    if (userPwd.value === checkUserPwd.value || checkUserPwd.value === '') {
        inputPasswordMessage2.innerHTML =  '';
    } else {
        inputPasswordMessage2.innerHTML =
        "<span style='color:#F03F40; font-size:12px;'>비밀번호를 확인해주세요</span>";
    }
}

let inputName = document.querySelector('#userName');
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

let inputPhone = document.querySelector('#userTel');
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

let inputEmail = document.querySelector('#userEmail');
inputEmail.addEventListener('input', inputEmailCheck);

function inputEmailCheck() {
    let inputEmailMessage = document.querySelector('#inputEmailMessage');

    let regExp = RegExp(/^[^\s@]+@[^\s@]+\.[^\s@]+$/);
    if (regExp.test(inputEmail.value) || inputEmail.value === '') {
        inputEmailMessage.innerHTML =  ''; 
    } else {
        inputEmailMessage.innerHTML = 
        "<span style='color:#F03F40; font-size:12px;'>이메일 입력을 확인해주세요</span>";
    }
}

//let idCheck = false;
//let btnInputId = document.querySelector('#btnInputId');
//btnInputId.addEventListener('click', btnInputIdCheck);
//
//function btnInputIdCheck() {
//    let regExp = RegExp(/^[A-Za-z\d@$!%*?&]{6,16}$/);
// 
//    if(userId.value == "") {
//        alert('아이디를 입력해주세요.');
//        userId.focus();
//    } else if(!regExp.test(userId.value)) {
//        alert("6자 이상 16자 이하로 영문, 숫자, 특수문자를 사용해주세요");
//        userId.value = '';
//        userId.focus();
//    } else {
//        alert("회원가입이 가능한 아이디입니다");
//        idCheck = true;
//    }
//}

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
                document.formSignup.roadAddress.value = fullAddr;
            }
        }).open();
    }
);

document.querySelector('#iconClose').addEventListener('click', function() {
    window.location.href = '../';
})

function checkUserId() {
	let userId = $('#userId').val();
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
	$.ajax({
		url: "userIdCheck",
		type: "post",
        contentType: "application/x-www-form-urlencoded", 
        dataType: "json", 
		data: {userId : userId},
		success: function(checkResult) {
			if (checkResult) {
				Toast.fire({
				    icon: 'success',
				    title: '사용 가능한 아이디입니다.'
				});
			} else {
				Toast.fire({
				    icon: 'error',
				    title: '이미 존재하는 아이디입니다.'
				});
			}
		}
	});
}
