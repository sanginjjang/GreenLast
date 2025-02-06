document.getElementById("kakao-login-btn").addEventListener("click", () => {
    const kakaoRestApiKey = 'a3f955836263dbc92dd134f11969510d'; // ✅ 카카오 REST API 키
    const redirectUri = 'http://localhost:8080/oauth/kakao/callback'; // ✅ Redirect URI

    const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${kakaoRestApiKey}&redirect_uri=${redirectUri}`;

    window.location.href = kakaoAuthUrl; // 카카오 로그인 페이지로 리디렉션
});
