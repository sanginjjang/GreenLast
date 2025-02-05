const authBtn = document.getElementById("auth_number_send_btn");
const form = document.getElementById("findForm");

// ✅ 인증번호 전송
authBtn.addEventListener("click", (event) => {
    event.preventDefault();  // ✅ 폼 제출 방지
    const name = form.querySelector('input[name="name"]').value;
    const email = form.querySelector('input[name="email"]').value;

    if (!name || !email) {
        alert("이름과 이메일을 모두 입력해주세요.");
        return;
    }

    axios.post('/api/send-code', {name: name, email: email})
        .then(response => {
            alert("인증번호가 이메일로 전송되었습니다.");
        })
        .catch(error => {
            console.error('이메일 전송 실패:', error);
            alert("입력한 정보가 일치하지 않거나 오류가 발생했습니다.");
        });
});

function submitAuthCodeId() {
    const name = form.querySelector('input[name="name"]').value;
    const email = form.querySelector('input[name="email"]').value;
    const authCode = form.querySelector('input[name="authCode"]').value;

    axios.post('/api/verify-code/id', {
        name : name,
        email: email,
        authCode: authCode
    })
        .then(response => {
            const user = response.data;
            alert("인증 성공! 아이디: " + user.userId);
        })
        .catch(error => {
            console.error('인증 실패:', error);
            alert("인증번호가 일치하지 않습니다.");
        });
}