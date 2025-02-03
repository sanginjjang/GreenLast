document.addEventListener("DOMContentLoaded", function () {
    // GET 요청을 통해 데이터를 가져오기
    axios.get('/api/mypage/getUserById')
        .then(response => {
            const user = response.data;

            // 생년월일 포맷팅 (예: 20220401 -> 2022년 04월 01일)
            if (user.birth && Number.isInteger(user.birth)) {
                const birthString = user.birth.toString();
                const formattedBirth = birthString.replace(/(\d{4})(\d{2})(\d{2})/, '$1년 $2월 $3일');
                document.getElementById('content_birth').querySelector('.value').innerText = formattedBirth;
            } else {
                document.getElementById('content_birth').querySelector('.value').innerText = 'N/A';
            }

            // 이메일을 input 필드의 value에 넣기
            document.querySelector('input[name="email"]').value = user.email || 'N/A';

            // 전화번호를 input 필드의 value에 넣기
            document.querySelector('input[name="phoneNumber"]').value = user.phoneNumber || 'N/A';

            // 나머지 데이터 처리
            document.getElementById('content_name').querySelector('.value').innerText = user.name || 'N/A';
            let genderString = '';
            if (user.gender == 'F') {
                genderString = '여성';
            } else {
                genderString = '남성';
            }
            document.getElementById('content_gender').querySelector('.value').innerText = genderString || 'N/A';
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
        });

    // PUT 요청 보내기
    const modifyBtn = document.querySelector("#modify_btn");
    modifyBtn.addEventListener("click", function () {
        // 이메일과 폰넘버 input 요소에서 값을 가져오기
        const email = document.querySelector("input[name='email']").value;
        const phoneNumber = document.querySelector("input[name='phoneNumber']").value;

        // 콘솔에서 email과 phoneNumber 값을 확인
        console.log("Email:", email, "Phone:", phoneNumber);

        // axios PUT 요청 보내기
        axios.put('/api/mypage/modifyUser', {
            phoneNumber: phoneNumber,  // 폰넘버
            email: email,  // 이메일
        })
            .then(response => {
                if (response.status === 200) {
                    console.log('수정 성공');
                }
                // 수정 후에 페이지 리다이렉트
                window.location.href = "/view/mypage/infoForm";
            })
            .catch(error => {
                console.error('Error updating user data:', error);
            });
    });
});
