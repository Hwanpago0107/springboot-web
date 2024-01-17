// 등록 기능
const createItemButton = document.getElementById("create-item-btn");

if (createItemButton) {
    createItemButton.addEventListener("click", (event) => {
        body = JSON.stringify({
            name: document.getElementById("name").value,
            price: document.getElementById("price").value,
            stockQuantity: document.getElementById("stockQuantity").value
        });
        function success() {
            alert("등록이 완료되었습니다.");
            location.replace("/items");
        }
        function fail() {
            alert("등록이 실패했습니다.");
            location.replace("/items");
        }

        httpRequest("POST", "/api/items", body, success, fail);
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
            location.replace("/adminItems");
        }
        function fail() {
            alert("수정이 실패했습니다.");
            location.replace("/adminItems");
        }

        httpRequest("PUT", "/api/items/" + id, body, success, fail);
    });
}