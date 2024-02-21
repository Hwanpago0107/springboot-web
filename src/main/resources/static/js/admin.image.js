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
    const filename = element.closest(".file_input").firstElementChild;

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
    const fileDiv = document.createElement("div");
    fileDiv.innerHTML =`
            <div class="file_input" style="display: inline-block;">
                <input type="text" readonly class="file_text">
            </div>
            <button type="button" onclick="removeFile(this);" class="btn btn-danger btn-sm del_btn">삭제</button>
        `;
    document.querySelector(".file_list").appendChild(fileDiv);
}

// 파일 삭제
function removeFile(element) {
    const fileAddBtn = element.nextElementSibling;
    if (fileAddBtn) {
        const inputs = element.previousElementSibling.querySelectorAll("input");
        inputs.forEach(input => input.value = '')
        return false;
    }
    element.parentElement.remove();
}

function uploadFile(element) {
    const fileAdd = element.nextElementSibling;
    fileAdd.click();
}