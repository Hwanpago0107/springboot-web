const postMailBtn = document.getElementById("post-mail-btn");

if (postMailBtn) {
    postMailBtn.addEventListener("click", event => {
        alert("메일을 발송 중 입니다. 잠시만 기다려주세요...");
        body = {
            mail: document.getElementById("req-email").value,
        }

        function success() {
            alert("메일 전송이 완료되었습니다.");
            location.replace("/main/home");
        }
        function fail() {
            alert("메일 전송이 실패하였습니다.");
            location.replace("/main/home");
        }

        httpRequestMail("POST", "/api/mail", body, success, fail);
    });
}