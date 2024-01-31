// 등록 기능
const createItemButton = document.getElementById("create-item-btn");

if (createItemButton) {
    createItemButton.addEventListener("click", (event) => {
        if ($('#category option:selected').val() == "" || $('#category option:selected').val() == null) {
            alert("카테고리를 등록해주세요. 목록이 없을 시 카테고리 먼저 등록하여 주세요.");
            return;
        }

        body = JSON.stringify({
            name: document.getElementById("name").value,
            price: document.getElementById("price").value,
            stockQuantity: document.getElementById("stockQuantity").value,
            discount: document.getElementById("discount").value,
            category_id: Number($('#category option:selected').val())
        });

        const formData = new FormData();
        const blob = new Blob([body], {type: 'application/json'});
        formData.append("request", blob);
        formData.append("file", this.imgFile.files[0]);
        function success() {
            alert("등록이 완료되었습니다.");
            location.replace("/items");
        }
        function fail() {
            alert("등록이 실패했습니다.");
            location.replace("/items");
        }

        httpRequestWithMF("POST", "/api/items", formData, success, fail);
    });
}

// 수정 기능
const modifyItemButton = document.getElementById("modify-item-btn");

if (modifyItemButton) {
    modifyItemButton.addEventListener("click", event => {
        let params = new URLSearchParams(location.search);
        let id = params.get("id");

        body = JSON.stringify({
            name: document.getElementById("name").value,
            price: document.getElementById("price").value,
            stockQuantity: document.getElementById("stockQuantity").value,
            discount: document.getElementById("discount").value,
            category_id: Number($('#category option:selected').val())
        });

        const formData = new FormData();
        const blob = new Blob([body], {type: 'application/json'});
        formData.append("request", blob);
        formData.append("file", this.imgFile.files[0]);

        function success() {
            alert("수정이 완료되었습니다.");
            location.replace("/items");
        }
        function fail() {
            alert("수정이 실패했습니다.");
            location.replace("/items");
        }

        httpRequestWithMF("PUT", "/api/items/" + id, formData, success, fail);
    });
}

// 삭제 기능
function deleteItem(id) {
    if (confirm("상품을 정말 삭제하시겠습니까?")) {
        function success() {
            alert("삭제가 완료되었습니다.");
            location.replace("/items");
        }

        function fail() {
            alert("삭제가 실패했습니다.");
            location.replace("/items");
        }

        httpRequest("DELETE", "/api/items/" + id, null, success, fail);
    }
}
