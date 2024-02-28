// 등록 기능
const createItemButton = document.getElementById("create-item-btn");

if (createItemButton) {
    createItemButton.addEventListener("click", (event) => {
        if ($('#category option:selected').val() == "" || $('#category option:selected').val() == null) {
            alert("카테고리를 등록해주세요. 목록이 없을 시 카테고리 먼저 등록하여 주세요.");
            return;
        }

        var optionNames = [];
        for (const optionName of document.getElementsByName("option_name").values()) {
            optionNames.push(optionName.value);
        }

        var optionValues = [];
        for (const optionValue of document.getElementsByName("option_value").values()) {
            optionValues.push(optionValue.value);
        }

        body = JSON.stringify({
            name: document.getElementById("name").value,
            price: document.getElementById("price").value,
            stockQuantity: document.getElementById("stockQuantity").value,
            discount: document.getElementById("discount").value,
            category_id: Number($('#category option:selected').val()),
            description: document.getElementById("description").value,
            optionNames: optionNames,
            optionValues: optionValues
        });

        const formData = new FormData();
        const blob = new Blob([body], {type: 'application/json'});
        formData.append("request", blob);
        formData.append("file", this.imgFile.files[0]);
        formData.append("file2", this.detailImgFile.files[0]);
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

        var optionNames = [];
        for (const optionName of document.getElementsByName("option_name").values()) {
            optionNames.push(optionName.value);
        }

        var optionValues = [];
        for (const optionValue of document.getElementsByName("option_value").values()) {
            optionValues.push(optionValue.value);
        }

        body = JSON.stringify({
            name: document.getElementById("name").value,
            price: document.getElementById("price").value,
            stockQuantity: document.getElementById("stockQuantity").value,
            discount: document.getElementById("discount").value,
            category_id: Number($('#category option:selected').val()),
            description: document.getElementById("description").value,
            optionNames: optionNames,
            optionValues: optionValues
        });

        const formData = new FormData();
        const blob = new Blob([body], {type: 'application/json'});
        formData.append("request", blob);
        formData.append("file", this.imgFile.files[0]);
        formData.append("file2", this.detailImgFile.files[0]);

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

// 파일 추가
function addOption() {
    const optionDiv = document.createElement("div");
    optionDiv.innerHTML =`
            <input type="text" class="form-control adm-small-input" placeholder="옵션명" name="option_name"
                   style="display: inline-block; width: 150px; margin-bottom: 5px;">
            <input type="text" class="form-control adm-small-input" placeholder="각 값마다 [,]로 구분하여 기입" name="option_value"
                   style="display: inline-block; width: calc(95% - 200px);">
            <button type="button" onclick="removeOption(this);" class="btn btn-danger btn-sm del_btn">삭제</button>
        `;
    optionDiv.style.width = "100%";
    document.querySelector("#option_list").appendChild(optionDiv);
}

// 파일 삭제
function removeOption(element) {
    const fileAddBtn = element.nextElementSibling;
    if (fileAddBtn) {
        const inputs = element.previousElementSibling.querySelectorAll("input");
        inputs.forEach(input => input.value = '')
        return false;
    }
    element.parentElement.remove();
}