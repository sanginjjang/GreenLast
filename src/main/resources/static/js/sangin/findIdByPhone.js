const authBtn = document.getElementById("auth_number_send_btn");
const verifyBtn = document.getElementById("authenticationCheck_btn");

// ✅ 인증번호 전송
authBtn.addEventListener("click", () => {
    const name = document.querySelector('input[name="name"]').value;
    const phone = document.querySelector('input[name="phone"]').value;

    if (!name || !phone) {
        alert("이름과 휴대폰 번호를 모두 입력해주세요.");
        return;
    }

    axios.post('/api/send-sms', { name: name, phone: phone })
        .then(response => {
            alert("인증번호가 휴대폰으로 전송되었습니다.");
        })
        .catch(error => {
            console.error('인증번호 전송 실패:', error);
            alert("인증번호 전송에 실패했습니다. 다시 시도해주세요.");
        });
});

// ✅ 인증번호 확인
verifyBtn.addEventListener("click", () => {
    const name = document.querySelector('input[name="name"]').value;
    const phone = document.querySelector('input[name="phone"]').value;
    const authCode = document.querySelector('input[name="authCode"]').value;

    if (!authCode) {
        alert("인증번호를 입력해주세요.");
        return;
    }

    axios.post('/api/verify-sms', {
        name: name,
        phone: phone,
        authCode: authCode
    })
        .then(response => {
            alert("인증 성공! 아이디: " + response.data.userId);
        })
        .catch(error => {
            console.error('인증 실패:', error);
            alert("인증번호가 일치하지 않습니다.");
        });
});
