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
            category_id: Number($('#category option:selected').val()),
            description: document.getElementById("description").value
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
            category_id: Number($('#category option:selected').val()),
            description: document.getElementById("description").value
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

// 파일 선택
function selectFile(element) {

    const files = element.files;
    for (let i = 0; i < files.length; i++) {
        setFile(files[i], element);
        if (i == (files.length - 1)) return;
        addFile();
        element = document.getElementsByClassName("file_text")[i];
    }
}

function setFile(file, element) {
    const filename = element.closest('.file_input').firstElementChild;

    // 1. 파일 선택 창에서 취소 버튼이 클릭된 경우
    if ( !file ) {
        filename.value = '';
        return false;
    }

    // 3. 파일명 지정
    filename.value = file.name;
}

// 파일 추가
function addFile() {
    const fileDiv = document.createElement('div');
    fileDiv.innerHTML =`
            <div class="file_input" style="display: inline-block;">
                <input type="text" readonly class="file_text">
            </div>
            <button type="button" onclick="removeFile(this);" class="btn btn-danger btn-sm del_btn">삭제</button>
        `;
    document.querySelector('.file_list').appendChild(fileDiv);
}

// 파일 삭제
function removeFile(element) {
    const fileAddBtn = element.nextElementSibling;
    if (fileAddBtn) {
        const inputs = element.previousElementSibling.querySelectorAll('input');
        inputs.forEach(input => input.value = '')
        return false;
    }
    element.parentElement.remove();
}

// 등록 기능
const createImgFilesBtn = document.getElementById("create-img-files-btn");

if (createImgFilesBtn) {
    createImgFilesBtn.addEventListener("click", (event) => {

        let files = document.getElementsByName("imgFiles")[0].files;
        const formData = new FormData();
        for (const file of files) {
            formData.append("imgFiles", file);
        }

        function success() {
            alert("등록이 완료되었습니다.");
            location.replace("/images");
        }
        function fail() {
            alert("등록이 실패했습니다.");
            location.replace("/newImages");
        }

        httpRequestWithMF("POST", "/api/images", formData, success, fail);
    });
}

function uploadFile(element) {
    const fileAdd = element.nextElementSibling;
    fileAdd.click();
}