// 등록 기능
function createWish(id, el) {
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
        option_text: optionText
    }
    function success() {
        alert("위시리스트에 상품을 추가하였습니다.");
        location.reload();
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
                checked.push(Number(elements[i].value));
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
                checked.push(Number(elements[i].value));
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
