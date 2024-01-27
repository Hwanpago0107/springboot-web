// 등록 기능
function createCart(id) {
    const addedQuantity = document.getElementById("addedQuantity" + id);
    if (Number(addedQuantity.textContent) < 1) {
        alert("장바구니에 넣을 수량을 선택하여 주십시오.");
        return;
    }
    body = JSON.stringify({
        item_id: String(id),
        quantity: Number(addedQuantity.textContent)
    });
    function success() {
        alert("장바구니에 물품을 추가하였습니다.");
        location.replace("/main");
    }

    function fail() {
        alert("장바구니에 물품을 추가하지 못하였습니다.");
    }

    httpRequest("POST", "/api/carts", body, success, fail);
}

// 삭제 기능
const deleteCartChkBtn = document.getElementById("delete-cart-chk-btn");

if (deleteCartChkBtn) {
    deleteCartChkBtn.addEventListener("click", (event) => {
        let checked = [];
        let elements = document.getElementsByName("checkbox-item-id");
        for (let i = 0; i < elements.length; i++) {
            if (elements[i].checked) {
                checked.push(elements[i].value)
            }
        }

        body = JSON.stringify({
            checked: checked
        });

        console.log(checked);
        function success() {
            alert("상품 삭제가 완료되었습니다.");
            location.replace("/main/myCarts");
        }
        function fail() {
            alert("상품 삭제가 실패했습니다.");
            location.replace("/main/myCarts");
        }

        httpRequest("DELETE", "/api/carts/checked", body, success, fail);
    });
}

const deleteCartAllBtn = document.getElementById("delete-cart-all-btn");

if (deleteCartAllBtn) {
    deleteCartAllBtn.addEventListener("click", (event) => {
        function success() {
            alert("장바구니 삭제가 완료되었습니다.");
            location.replace("/main");
        }
        function fail() {
            alert("장바구니 삭제가 실패했습니다.");
            location.replace("/main");
        }

        httpRequest("DELETE", "/api/carts", null, success, fail);
    });
}

function plusOne(id, quantity) {
    const addedQuantity = document.getElementById("addedQuantity" + id);
    console.log(id);
    if (Number(quantity) <= Number(addedQuantity.textContent)) {
        alert("선택하신 상품의 재고가 부족합니다.");
        return;
    }
    addedQuantity.textContent =  Number(addedQuantity.textContent) + 1;
}

function minusOne(id) {
    const addedQuantity = document.getElementById("addedQuantity" + id);
    if (Number(addedQuantity.textContent) < 1) {
        return;
    }
    addedQuantity.textContent =  Number(addedQuantity.textContent) - 1;
}

function selectAll(el) {
    const checkboxes = document.getElementsByName("checkbox-item-id");
    checkboxes.forEach((checkbox) => {   checkbox.checked = el.checked; });
}
