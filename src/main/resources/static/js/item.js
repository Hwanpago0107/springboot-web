// 등록 기능
const createItemButton = document.getElementById("create-item-btn");

if (createItemButton) {
    createItemButton.addEventListener("click", (event) => {
        console.log("====File Upload1====");
        const formData = new FormData();
        body = JSON.stringify({
            name: document.getElementById("name").value,
            price: document.getElementById("price").value,
            stockQuantity: document.getElementById("stockQuantity").value
        });
        const blob = new Blob([body], {type: 'application/json'});
        formData.append("request", blob);
        formData.append("file", this.imgFile.files[0]);
        console.log("====File Upload2====");
        function success() {
            alert("등록이 완료되었습니다.");
            location.replace("/items");
        }
        function fail() {
            alert("등록이 실패했습니다.");
            location.replace("/items");
        }

        httpRequest("POST", "/api/items", formData, success, fail);
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
            stockQuantity: document.getElementById("stockQuantity").value
        })
        function success() {
            alert("수정이 완료되었습니다.");
            location.replace("/items");
        }
        function fail() {
            alert("수정이 실패했습니다.");
            location.replace("/items");
        }

        httpRequest("PUT", "/api/items/" + id, body, success, fail);
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
