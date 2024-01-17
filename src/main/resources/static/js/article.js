// 삭제 기능
const deleteButton = document.getElementById("delete-btn");

if (deleteButton) {
    deleteButton.addEventListener("click", event => {
        let id = document.getElementById("article-id").value;
        function success() {
            alert("삭제가 완료되었습니다.");
            location.replace("/articles");
        }
        function fail() {
            alert("삭제가 실패했습니다.");
            location.replace("/articles");
        }

        httpRequest("DELETE", "/api/articles/" + id, null, success, fail);
    });
}

// 수정 기능
const modifyButton = document.getElementById("modify-btn");

if (modifyButton) {
    modifyButton.addEventListener("click", event => {
        let params = new URLSearchParams(location.search);
        let id = params.get("id");

        body = JSON.stringify({
            title: document.getElementById("title").value,
            content: document.getElementById("content").value
        })
        function success() {
            alert("수정이 완료되었습니다.");
            location.replace("/articles");
        }
        function fail() {
            alert("수정이 실패했습니다.");
            location.replace("/articles");
        }

        httpRequest("PUT", "/api/articles/" + id, body, success, fail);
    });
}

// 등록 기능
const createButton = document.getElementById("create-btn");

if (createButton) {
    createButton.addEventListener("click", (event) => {
        body = JSON.stringify({
            title: document.getElementById("title").value,
            content: document.getElementById("content").value
        });
        function success() {
            alert("등록이 완료되었습니다.");
            location.replace("/articles");
        }
        function fail() {
            alert("등록이 실패했습니다.");
            location.replace("/articles");
        }

        httpRequest("POST", "/api/articles", body, success, fail);
    });
}
