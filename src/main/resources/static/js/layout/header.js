document.addEventListener("DOMContentLoaded", function() {
    const headerProfileIcon = document.getElementById("header_profile_icon");
    const headerProfileDetail = document.getElementById("header_profile_detail");

    headerProfileIcon.addEventListener("click", () => {
        // alert("클릭 테스트");
        if (headerProfileDetail.style.display === "block") {
            headerProfileDetail.style.display = "none";
        } else {
            headerProfileDetail.style.display = "block";
        }
    });
});
