// 수정 기능
const modifyMyinfoButton = document.getElementById("modify-myinfo-btn");

if (modifyMyinfoButton) {
    modifyMyinfoButton.addEventListener("click", event => {
        body = {
            email: document.getElementById("email").value,
            name: document.getElementById("name").value,
            nickname: document.getElementById("nickname").value,
            phone: document.getElementById("phone").value,
            address: {
                zipcode: document.getElementById("zipcode").value,
                streetAddr: document.getElementById("streetAddr").value,
                detailAddr: document.getElementById("detailAddr").value
            }
        }

        function success() {
            alert("수정이 완료되었습니다.");
            location.replace("/main/myInfo");
        }
        function fail() {
            alert("수정이 실패했습니다.");
            location.replace("/main/myInfo");
        }

        httpRequest("PUT", "/api/users", body, success, fail);
    });
}

function findAddr() {
    new daum.Postcode({
        oncomplete: function (data) {
            var addr = "";
            var extraAddr = "";

            if (data.userSelectedType == "R") {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            if (data.userSelectedType == "R") {
                if (data.bname != "" && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName != "" && data.apartment == "Y") {
                    extraAddr += (extraAddr != "" ? ", " + data.buildingName : data.buildingName);
                }
                if (extraAddr != "") {
                    extraAddr = " (" + extraAddr + ")";
                }

                document.getElementById("detailAddr").value = extraAddr;
            } else {
                document.getElementById("detailAddr").value = "";
            }
            document.getElementById("zipcode").value = data.zonecode;
            document.getElementById("streetAddr").value = addr;
            document.getElementById("detailAddr").focus();
        }
    }).open();
}
