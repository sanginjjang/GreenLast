const authBtn = document.getElementById("auth_number_send_btn");
const form = document.getElementById("findForm");


// ✅ 인증번호 전송
authBtn.addEventListener("click", (event) => {
    event.preventDefault();  // ✅ 폼 제출 방지
    const userId = form.querySelector('input[name="userId"]').value;
    const name = form.querySelector('input[name="name"]').value;
    const email = form.querySelector('input[name="email"]').value;

    if (!userId) {
        alert("아이디를 입력해주세요.");
        return;
    }
    if (!name) {
        alert("이름을 입력해주세요.");
        return;
    }
    if (!email) {
        alert("이메일을 입력해주세요.");
        return;
    }

    axios.post('/api/send-code', {name: name, email: email, userId: userId})
        .then(response => {
            alert("인증번호가 이메일로 전송되었습니다.");
        })
        .catch(error => {
            console.error('이메일 전송 실패:', error);
            alert("입력한 정보가 일치하지 않거나 오류가 발생했습니다.");
        });
});

function submitAuthCodePw() {
    const userId = form.querySelector('input[name="userId"]').value;
    const name = form.querySelector('input[name="name"]').value;
    const email = form.querySelector('input[name="email"]').value;
    const authCode = form.querySelector('input[name="authCode"]').value;

    axios.post('/api/verify-code/pw', {
        userId: userId,
        name: name,
        email: email,
        authCode: authCode
    })
        .then(response => {
            alert("인증 성공! 비밀번호 변경 페이지로 이동합니다.");
            window.location.href = `/view/resetPassword?userId=${userId}`;  // ✅ 리디렉션
        })
        .catch(error => {
            console.error('인증 실패:', error);
            alert("인증번호가 일치하지 않습니다.");
        });
}
