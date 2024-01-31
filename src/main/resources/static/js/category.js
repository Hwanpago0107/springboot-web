// 등록 기능
const createCateBtn = document.getElementById("create-cate-btn");

if (createCateBtn) {
    createCateBtn.addEventListener("click", (event) => {
        body = {
            name: document.getElementById("name").value,
            parent_id: Number($('#parents option:selected').val())
        }
        function success() {
            alert("등록이 완료되었습니다.");
            location.replace("/categories");
        }
        function fail() {
            alert("등록이 실패했습니다.");
            location.replace("/newCategory");
        }

        httpRequest("POST", "/api/categories", body, success, fail);
    });
}

// 수정 기능
const modifyCateBtn = document.getElementById("modify-cate-btn");

if (modifyCateBtn) {
    modifyCateBtn.addEventListener("click", event => {
        let params = new URLSearchParams(location.search);
        let id = params.get("id");

        body = {
            name: document.getElementById("name").value,
            parent_id: Number($('#parents option:selected').val())
        }

        function success() {
            alert("수정이 완료되었습니다.");
            location.replace("/categories");
        }
        function fail() {
            alert("수정이 실패했습니다.");
            location.replace("/categories");
        }

        httpRequest("PUT", "/api/categories/" + id, body, success, fail);
    });
}

// 삭제 기능
function deleteCate(id) {
    if (confirm("카테고리를 정말 삭제하시겠습니까?")) {
        function success() {
            alert("삭제가 완료되었습니다.");
            location.replace("/categories");
        }

        function fail() {
            alert("삭제가 실패했습니다.");
            location.replace("/categories");
        }

        httpRequest("DELETE", "/api/categories/" + id, null, success, fail);
    }
}
