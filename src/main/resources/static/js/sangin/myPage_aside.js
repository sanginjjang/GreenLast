document.addEventListener('DOMContentLoaded', function () {
    axios.get('/api/mypage/getUserById')
        .then(response => {
            const user = response.data;
            document.getElementById('aside_name').innerText = user.name || 'N/A';
        })
        .catch(error => {
            console.error('Error fetching user data:', error);
        });
});