document.addEventListener('DOMContentLoaded', function () {
    axios.get('/api/mypage/getUserById')
        .then(response => {
            const user = response.data;

            // 생년월일 포맷팅 (예: 20220401 -> 2022년 04월 01일)
            if (user.birth && Number.isInteger(user.birth)) {
                // birth 값이 정수형일 경우 문자열로 변환
                const birthString = user.birth.toString();

                // birth 포맷팅: 20220401 -> 2022년 04월 01일
                const formattedBirth = birthString.replace(/(\d{4})(\d{2})(\d{2})/, '$1년 $2월 $3일');
                document.getElementById('content_birth').querySelector('.value').innerText = formattedBirth;
            } else {
                // 유효하지 않은 경우 기본값 처리 (없다면 'N/A'로)
                document.getElementById('content_birth').querySelector('.value').innerText = 'N/A';
            }

            document.getElementById('content_phone').querySelector('.value').innerText = user.phoneNumber;

            // 나머지 데이터 처리
            document.getElementById('content_name').querySelector('.value').innerText = user.name || 'N/A';
            let genderString = '';
            if (user.gender == 'F') {
                genderString = '여성';
            } else {
                genderString = '남성';
            }
            document.getElementById('content_gender').querySelector('.value').innerText = genderString || 'N/A';
            document.getElementById('content_email').querySelector('.value').innerText = user.email || 'N/A';
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
        });
});
