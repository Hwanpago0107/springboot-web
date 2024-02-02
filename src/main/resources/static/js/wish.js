// 등록 기능
function createWish(id) {
    body = {
        item_id: String(id),
    }
    function success(json) {
        console.log(json);
        alert("위시리스트에 상품을 추가하였습니다.");
        location.replace("/main/home");
    }

    function fail() {
        alert("위시리스트에 상품을 추가하지 못하였습니다.");
    }
    httpRequest("POST", "/api/wishes", body, success, fail);
}

// 삭제 기능
const deleteWishChkBtn = document.getElementById("delete-wish-chk-btn");

if (deleteWishChkBtn) {
    deleteWishChkBtn.addEventListener("click", (event) => {
        let checked = [];
        let elements = document.getElementsByName("checkbox-wish-item-id");
        for (let i = 0; i < elements.length; i++) {
            if (elements[i].checked) {
                checked.push(elements[i].value)
            }
        }

        body = {
            checked: checked
        }

        function success() {
            alert("상품 삭제가 완료되었습니다.");
            location.replace("/main/myWish");
        }
        function fail() {
            alert("상품 삭제가 실패했습니다.");
            location.replace("/main/myWish");
        }

        httpRequest("DELETE", "/api/wishes/checked", body, success, fail);
    });
}

// 위시리스트 to 장바구니
const createWishChkBtn = document.getElementById("create-wish-chk-btn");

if (createWishChkBtn) {
    createWishChkBtn.addEventListener("click", (event) => {
        let checked = [];
        let elements = document.getElementsByName("checkbox-wish-item-id");
        for (let i = 0; i < elements.length; i++) {
            if (elements[i].checked) {
                checked.push(elements[i].value)
            }
        }

        body = {
            checked: checked
        }

        function success() {
            alert("상품 장바구니 추가가 완료되었습니다.");
            location.replace("/main/myCart");
        }
        function fail() {
            alert("상품 장바구니 추가가 실패했습니다.");
            location.replace("/main/myWish");
        }

        httpRequest("POST", "/api/wishes/checked", body, success, fail);
    });
}

function selectAll(el) {
    const checkboxes = document.getElementsByName("checkbox-wish-item-id");
    checkboxes.forEach((checkbox) => {   checkbox.checked = el.checked; });
}
