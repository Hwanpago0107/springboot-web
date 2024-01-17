// 등록 기능
const createButton = document.getElementById("create-item-btn");

if (createButton) {
    createButton.addEventListener("click", (event) => {
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