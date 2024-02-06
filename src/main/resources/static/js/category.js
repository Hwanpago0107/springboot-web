// 등록 기능
const createCateBtn = document.getElementById("create-cate-btn");

if (createCateBtn) {
    createCateBtn.addEventListener("click", (event) => {
        let parent_id = 0;
        if ($("#depth2 option:selected").val() != null && $("#depth2 option:selected").val() != "") {
            parent_id = $("#depth2 option:selected").val()
        } else {
            parent_id= $("#depth1 option:selected").val()
        }
        body = {
            name: document.getElementById("name").value,
            parent_id: Number(parent_id)
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

        let parent_id = 0;
        if ($("#depth2 option:selected").val() != null && $("#depth2 option:selected").val() != "") {
            parent_id = $("#depth2 option:selected").val()
        } else {
            parent_id= $("#depth1 option:selected").val()
        }
        body = {
            name: document.getElementById("name").value,
            parent_id: Number(parent_id)
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

function categoryChange(e) {
    const parent_id = Number($('#depth1 option:selected').val());
    const depth2 = document.getElementById("depth2");
    if (parent_id == 0) {
        depth2.style.display = "none";
        while(depth2.hasChildNodes()) {
            depth2.removeChild(depth2.firstChild);
        }
        return;
    }
    function success(data) {
        depth2.style.display = "inline-block";

        depth2.options.length = 0;
        var opt = document.createElement("option");
        opt.value = "";
        opt.text = "선택안함";
        depth2.appendChild(opt);

        for (x in data) {
            var opt = document.createElement("option");
            opt.value = data[x].category_id;
            opt.text = data[x].name;
            depth2.appendChild(opt);
        }
    }
    function fail() {
        alert("등록이 실패했습니다.");
    }

    httpRequestBody("GET", "/api/categories/" + parent_id, null, success, fail);
}
