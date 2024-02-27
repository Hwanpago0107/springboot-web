// 어드민 권한 부여
function registerAdmin(id) {
    if (!confirm("사용자에게 어드민 권한을 부여하시겠습니까?")) {
        return;
    }

    function success() {
        alert("권한 부여가 완료되었습니다.");
        location.reload();
    }

    function fail() {
        alert("권한 부여가 실패하였습니다.");
        location.reload();
    }

    httpRequest("PUT", "/api/users/" + id + "/register", null, success, fail);
}

// 사용자 삭제
function modifyUser(id) {
    if (!confirm("해당 사용자를 정말 삭제하시겠습니까?")) {
        return;
    }

    function success() {
        alert("삭제가 완료되었습니다.");
        location.reload();
    }

    function fail() {
        alert("삭제가 실패했습니다.");
        location.reload();
    }

    httpRequest("PUT", "/api/users/" + id + "/disable", null, success, fail);
}

// 게스트 사용자 삭제
function deleteUser(id) {
    if (!confirm("해당 사용자를 정말 삭제하시겠습니까?")) {
        return;
    }

    function success() {
        alert("삭제가 완료되었습니다.");
        location.reload();
    }

    function fail() {
        alert("삭제가 실패했습니다.");
        location.reload();
    }

    httpRequest("DELETE", "/api/users/" + id, null, success, fail);
}