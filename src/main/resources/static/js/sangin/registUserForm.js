// DOM 요소 가져오기
const yearSelect = document.getElementById('inputYear');
const monthSelect = document.getElementById('inputMonth');
const daySelect = document.getElementById('inputDay');
const idInput = document.getElementById('inputId');
const idCheckButton = document.getElementById('id_check');
const pwInput1 = document.getElementById('inputPw1');
const pwInput2 = document.getElementById('inputPw2');
const nameInput = document.getElementById('inputName');
const checkEng = document.getElementById('checkEng');
const checkSeq = document.getElementById('checkSeq');
const checkLen = document.getElementById('checkLen');
const emailInput = document.getElementById('inputEmail');
const phoneInput = document.getElementById('inputPhone');
const inputYear = document.getElementById('inputYear');
const inputMonth = document.getElementById('inputMonth');
const inputDay = document.getElementById('inputDay');
const inputGender = document.getElementById('inputGender');

let paramBirth = '20250101';

// 중간에 값 수정 방지용 tf
let idTf = false;
let pwTf = false;
let pwRule1 = false;
let pwRule2 = false;
let pwRule3 = false;

let nameTf = false;
let genderTf = false;
let birthTf = false;
let emailTf = false;
let phoneTf = false;
// 연도 생성 (현재 연도 기준)
const currentYear = new Date().getFullYear();
for (let i = currentYear; i >= 1900; i--) {
    const option = document.createElement('option');
    option.value = i;
    option.textContent = i;
    yearSelect.appendChild(option);
}

// 월 생성
for (let i = 1; i <= 12; i++) {
    const option = document.createElement('option');
    option.value = i;
    option.textContent = i;
    monthSelect.appendChild(option);
}

// 일 동적 생성 함수
function updateDays() {
    const selectedYear = parseInt(yearSelect.value, 10) || currentYear;
    const selectedMonth = parseInt(monthSelect.value, 10);

    if (!selectedMonth) return;

    const lastDay = new Date(selectedYear, selectedMonth, 0).getDate();
    daySelect.innerHTML = '';

    for (let i = 1; i <= lastDay; i++) {
        const option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        daySelect.appendChild(option);
    }
}

// 월 변경 시 일 업데이트
monthSelect.addEventListener('change', updateDays);
updateDays(); // 초기화 시 첫 월의 날짜 설정

// 아이디 유효성 검사 함수
function validateUserId(userId) {
    if (/\s/.test(userId)) {
        return "아이디에 공백이 포함될 수 없습니다.";
    }
    if (userId.length < 8 || userId.length > 20) {
        return "아이디는 8자 이상, 20자 이하여야 합니다.";
    }
    const hasLetter = /[a-zA-Z]/.test(userId);
    const hasNumber = /\d/.test(userId);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(userId);
    // true 값만 반환하는데 그 true 가 몇개냐? 그리고 true가 2개 이상이면 조건만족이니 통과
    const validCharTypes = [hasLetter, hasNumber].filter(Boolean).length;

    if (hasSpecialChar) {
        return "아이디에 특수문자가 포함될 수 없습니다.";
    }
    if (validCharTypes < 2) {
        return "아이디는 영문과 숫자를 조합해서 사용해야합니다.";
    }
    if (/(\w)\1\1/.test(userId)) {
        return "아이디에 연속된 동일 문자/숫자가 3번 이상 포함될 수 없습니다.";
    }
    return null;
}

// 아이디 입력 시 유효성 검사 실행 및 버튼 활성화/비활성화
function checkUserId() {
    const userId = idInput.value;
    const errorMessage = validateUserId(userId); //error 메세지 반환 or null
    const errorDisplay = document.getElementById('id_error_message');
    errorDisplay.style.fontWeight = 'bold';
    idCheckButton.disabled = true;
    idCheckButton.style.backgroundColor = '#ccc';
    idCheckButton.style.cursor = 'not-allowed';
    idTf = false;

    if (errorMessage) { //not null
        errorDisplay.textContent = errorMessage;
        errorDisplay.style.color = 'red';

    } else {//null
        errorDisplay.textContent = '중복 검사를 눌러주세요';
        errorDisplay.style.color = 'blue';
        idCheckButton.disabled = false;
        idCheckButton.style.backgroundColor = '#0092ff';
        idCheckButton.style.cursor = 'pointer';
        //     사용가능 일 때 tf1 = true 로 해놔야 나중에 중복검사 후 수정해버리는 애들없게끔 할 수 있겠지?
    }
}

// 아이디 중복검사 버튼 클릭 이벤트
function checkDuplicateId() {
    const userId = idInput.value;
    const errorDisplay = document.getElementById('id_error_message');

    if (!userId) {
        alert("아이디를 입력하세요.");
        return;
    }

    axios.get(`/api/users/check-duplicate-id?userId=${userId}`)
        .then(response => {
            if (response.data.duplicate) {
                alert(response.data.message); // "이미 사용 중인 아이디입니다."
            } else {
                alert(response.data.message); // "사용 가능한 아이디입니다."
                errorDisplay.textContent = '검사 완료!';
                idCheckButton.disabled = false;
                idCheckButton.disabled = true;
                idCheckButton.style.backgroundColor = '#ccc';
                idCheckButton.style.cursor = 'not-allowed';
                idTf = true;
            }
        })
        .catch(error => {
            console.error("오류 발생:", error);
        });

}


// 아이디 입력 이벤트 리스너 추가
idInput.addEventListener('input', checkUserId);
idCheckButton.disabled = true; // 초기 상태에서 버튼 비활성화
idCheckButton.style.backgroundColor = '#ccc';
idCheckButton.style.cursor = 'not-allowed';

//비밀번호 유효성 검사 시작.
//비밀번호도 아이디와 같이 유효성 검사 먼저 하고
//1차 비밀번호랑 2차 비밀번호가 다르면 넘어가지 않게 해야됨

function validateUserPw() {
    // length 용
    userPw1 = pwInput1.value;
    if (/\s/.test(userPw1)) {
        checkLen.style.color = 'gray';
        pwRule1 = false;
    }
    if (userPw1.length < 8 || userPw1.length > 20) {
        checkLen.style.color = 'gray';
        pwRule1 = false;
    } else if (userPw1.length >= 8 || userPw1.length <= 20) {
        checkLen.style.color = 'blue';
        pwRule1 = true;
    }
    if (/\s/.test(userPw1)) {
        checkLen.style.color = 'gray';
        pwRule1 = false;
    }
    // english 용
    const hasLetter = /[a-zA-Z]/.test(userPw1);
    const hasNumber = /\d/.test(userPw1);
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(userPw1);
    // true 값만 반환하는데 그 true 가 몇개냐? 그리고 true가 2개 이상이면 조건만족이니 통과
    const validCharTypes = [hasLetter, hasNumber, hasSpecialChar].filter(Boolean).length;
    if (validCharTypes < 2) {
        checkEng.style.color = 'gray';
        pwRule2 = false;
    } else {
        checkEng.style.color = 'blue';
        pwRule2 = true;
    }
    // seq용
    // 연속된 동일 문자 검사
    if (/(.)\1{2}/.test(userPw1) === false && userPw1.length >= 3) {
        checkSeq.style.color = 'blue'; // 동일 문자 3번 이상 반복 성공
        pwRule3 = true;
    } else {
        checkSeq.style.color = 'gray'; // 동일 문자 3번 이상 반복 실패
        pwRule3 = false;
    }

}

// 비밀번호 입력 시 유효성 검사 실행 및 버튼 활성화/비활성화
function checkUserPw2() {
    const userPw1 = pwInput1.value;
    const userPw2 = pwInput2.value;
    const errorDisplay = document.getElementById('pw_error_message2');

    errorDisplay.style.fontWeight = 'bold';

    if (userPw1 === '' || userPw2 === '') {
        errorDisplay.textContent = '';
        pwTf = false;
    } else if (userPw1 !== userPw2) {//     pw 다를시
        errorDisplay.textContent = '비밀번호가 다릅니다.';
        errorDisplay.style.color = 'red';
        pwTf = false;
    } else if (userPw1 === userPw2) {//     pw 같을시
        errorDisplay.textContent = '비밀번호 확인 완료!';
        errorDisplay.style.color = 'blue';
        pwTf = true;
        //     tf3
    }
}

// name

function validateUserName(userName) {
    // 공백 확인
    if (/\s/.test(userName)) {
        return "이름에 공백이 포함될 수 없습니다.";
    }

    // 길이 확인
    if (userName.length < 1 || userName.length > 20) {
        return "이름은 1자 이상, 20자 이하여야 합니다.";
    }

    // 한글 완성형 문자 확인 (가~힣)
    const isKoreanComplete = /^[가-힣]+$/.test(userName);
    if (!isKoreanComplete) {
        return "이름은 완성된 한글만 입력 가능합니다.";
    }

    return null; // 유효한 경우 null 반환
}


function checkUserName() {
    const userName = nameInput.value;
    const errorMessage = validateUserName(userName); //error 메세지 반환 or null
    const errorDisplay = document.getElementById('name_error_message');
    errorDisplay.style.fontWeight = 'bold';

    if (errorMessage) { //not null
        errorDisplay.textContent = errorMessage;
        errorDisplay.style.color = 'red'
        nameTf = false;
    } else {//null
        errorDisplay.textContent = '이름 기입 완료!';
        errorDisplay.style.color = 'blue';
        nameTf = true;
        //     사용가능 일 때 tf2 = true 로 해놔야 나중에 중복검사 후 수정해버리는 애들없게끔 할 수 있겠지?
    }
}

function validateUserEmail(userEmail) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegex.test(userEmail)) {
        return "올바른 이메일 형식을 입력하세요.";
    }
    return null;
}

function checkUserEmail() {
    const userEmail = emailInput.value;
    const errorMessage = validateUserEmail(userEmail);
    const errorDisplay = document.getElementById('email_error_message');

    errorDisplay.style.fontWeight = 'bold';

    if (errorMessage) { // 이메일 정규식 검증 실패
        errorDisplay.textContent = errorMessage;
        errorDisplay.style.color = 'red';
        emailTf = false;
    } else { // 이메일 정규식 검증 성공
        axios.get(`/api/users/check-duplicate-email?email=${userEmail}`)
            .then(response => {
                if (response.data.duplicate) {
                    errorDisplay.style.color = 'red';
                    errorDisplay.textContent = '중복된 이메일 입니다.';
                } else {
                    errorDisplay.textContent = '이메일 확인 완료!';
                    errorDisplay.style.color = 'blue';
                    emailTf = true;
                }
            })
            .catch(error => {
                console.error("오류 발생:", error);
            });

    }
}

function validateUserPhone(userPhone) {
    const phoneRegex = /^01[0|1|6|7|8|9]-?\d{3,4}-?\d{4}$/;
    if (!phoneRegex.test(userPhone)) {
        return "올바른 휴대전화 번호를 입력하세요. (예: 01012345678)";
    }
    return null;
}

function checkUserPhone() {
    const userPhone = phoneInput.value;
    const errorMessage = validateUserPhone(userPhone);
    const errorDisplay = document.getElementById('phone_error_message');

    errorDisplay.style.fontWeight = 'bold';

    if (errorMessage) { // 휴대폰 유효성 실패
        errorDisplay.textContent = errorMessage;
        errorDisplay.style.color = 'red';
        phoneTf = false;
    } else { // 휴대폰 유효성 성공
        axios.get(`/api/users/check-duplicate-phoneNumber?phoneNumber=${userPhone}`)
            .then(response => {
                if (response.data.duplicate) {
                    errorDisplay.style.color = 'red';
                    errorDisplay.textContent = '중복된 휴대전화 번호입니다.';
                } else {
                    errorDisplay.textContent = '휴대전화 번호 확인 완료!';
                    errorDisplay.style.color = 'blue';
                    phoneTf = true;
                }
            })
            .catch(error => {
                console.error("오류 발생:", error);
            });
    }
}

function checkGender() {
    const genderSelect = document.getElementById("inputGender");
    const selectedValue = genderSelect.value;

    if (selectedValue === "") {
        genderTf = false; // 선택 안됨
    } else {
        genderTf = true; // 남성 또는 여성 선택됨
    }

    console.log("genderTf:", genderTf); // 디버깅 용도로 출력
}

function setBirth() {
    const userYear = inputYear.value;
    let userMonth = inputMonth.value;
    let userDay = inputDay.value;

    // userMonth와 userDay가 숫자로 변환되지 않도록 문자열로 처리
    if (userMonth.length === 1) {
        userMonth = '0' + userMonth;
    }
    if (userDay.length === 1) {
        userDay = '0' + userDay;
    }

    paramBirth = userYear + userMonth + userDay;
    birthTf = true;
    console.log(paramBirth); // 디버깅용 출력
}

function checkRegistInfo() {
    console.log('       ----------------------');
    console.log('       idTf = ' + idTf);
    console.log('       pwRule1 = ' + pwRule1);
    console.log('       pwRule2 = ' + pwRule2);
    console.log('       pwRule3 = ' + pwRule3);
    console.log('       pwTf = ' + pwTf);
    console.log('       nameTf = ' + nameTf);
    console.log('       genderTf = ' + genderTf);
    console.log('       birthTf = ' + birthTf);
    console.log('       emailTf = ' + emailTf);
    console.log('       phoneTf = ' + phoneTf);

    // False인 첫 번째 항목으로 이동
    if (!idTf) {
        idInput.focus();
        alert("아이디를 확인해주세요."); // 사용자에게 경고 표시
        return;
    }
    if (!pwRule1 || !pwRule2 || !pwRule3 || !pwTf) {
        pwInput1.focus();
        alert("비밀번호를 확인해주세요."); // 사용자에게 경고 표시
        return;
    }
    if (!nameTf) {
        nameInput.focus();
        alert("이름을 확인해주세요."); // 사용자에게 경고 표시
        return;
    }
    if (!genderTf) {
        document.getElementById("inputGender").focus();
        alert("성별을 선택해주세요."); // 사용자에게 경고 표시
        return;
    }
    if (!birthTf) {
        inputYear.focus();
        alert("생년월일을 확인해주세요."); // 사용자에게 경고 표시
        return;
    }
    if (!emailTf) {
        emailInput.focus();
        alert("이메일을 확인해주세요."); // 사용자에게 경고 표시
        return;
    }
    if (!phoneTf) {
        phoneInput.focus();
        alert("휴대전화를 확인해주세요."); // 사용자에게 경고 표시
        return;
    }
    //id
    //pw
    //name
    //gender
    //birth
    //email
    //phone
    axios.post('/api/users/regist-user',
        {
            userId: idInput.value
            , password: pwInput1.value
            , name: nameInput.value
            , gender: inputGender.value
            , birth: paramBirth
            , email: emailInput.value
            , phoneNumber: phoneInput.value
        }
    )
        .then(response => {
            const data = response.data;
            console.log('데이터 = ' + data);
            location.href = "/";
        })
        .catch(error => {
            console.error("사용자 등록 실패 : ", error);
        });
}