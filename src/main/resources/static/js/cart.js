// 등록 기능
function createCart(id, quantity, el) {
    let qt = $(el).parent().parent().find(".input-number").children().eq(0).val();
    if (qt != null) quantity = Number(qt);
    const target = $(el).parent().parent().find(".product-options");

    let optionText = "";
    let length = target.children().length;
    if (length == 0) {
        alert("VIEW OPTION을 클릭하여 옵션을 선택하여 주세요.");
        return;
    }
    target.children().each(function (index) {
        optionText = optionText.concat($(this).children().first().text());
        optionText = optionText.concat(": ");
        optionText = optionText.concat($(this).children(".input-select").find("option:selected").text());
        if (index != (length - 1)) optionText = optionText.concat(", ");
    });

    body = {
        item_id: Number(id),
        quantity: quantity,
        option_text: optionText
    }
    function success() {
        alert("장바구니에 물품을 추가하였습니다.");
        location.reload();
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
        let elements = document.getElementsByName("checkbox-id");
        for (let i = 0; i < elements.length; i++) {
            if (elements[i].checked) {
                checked.push(Number(elements[i].value));
            }
        }

        body = {
            checked: checked
        }

        console.log(checked);
        function success() {
            alert("상품 삭제가 완료되었습니다.");
            location.replace("/main/myCart");
        }
        function fail() {
            alert("상품 삭제가 실패했습니다.");
            location.replace("/main/myCart");
        }

        httpRequest("DELETE", "/api/carts/checked", body, success, fail);
    });
}

function selectCartAll(el) {
    const checkboxes = document.getElementsByName("checkbox-id");
    checkboxes.forEach((checkbox) => {   checkbox.checked = el.checked; });
}
