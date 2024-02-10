// 상품 주문 기능
const preOrderBtn = document.getElementById("pre-order-btn");

if (preOrderBtn) {
    preOrderBtn.addEventListener("click", (event) => {
        // 결제수단
        let elpayment = null;
        document.getElementsByName("payment").forEach((pay) => {
            if (pay.checked) elpayment = pay;
        });

        if (elpayment == "" || elpayment == null || elpayment == undefined) {
            alert("결제수단을 선택하여 주세요.");
            return;
        }

        // 이용약관
        if (!document.getElementById("terms").checked) {
            alert("이용약관을 읽고 동의를 체크하여 주세요.");
            return;
        }

        // 모달창 열기
        $("#deliveryModal").modal("toggle");
    });
}

const preOrderBtn2 = document.getElementById("pre-order-btn2");

if (preOrderBtn2) {
    preOrderBtn2.addEventListener("click", (event) => {
        // 모달창 열기
        $("#deliveryModal2").modal("toggle");
    });
}


function deleteOrder(id) {
    if (confirm("주문을 정말 취소하시겠습니까? 주문 취소 시 저장된 주문 내역은 삭제 됩니다.")) {
        function success() {
            alert("취소가 완료되었습니다.");
            location.replace("/main/myOrder");
        }

        function fail() {
            alert("취소가 실패했습니다.");
            location.replace("/main/myOrder");
        }

        httpRequest("DELETE", "/api/orders/" + id, null, success, fail);
    }
}

// 주문 생성
const createOrderBtn = document.getElementById("create-order-btn");

if (createOrderBtn) {
    createOrderBtn.addEventListener("click", (event) => {
        const paymentYn = document.getElementById("paymentYn").value;
        console.log(paymentYn);

        let payname = "";
        // 결제수단 영역이 있으면
        if (paymentYn == 'Y') {
            payname = "payment_sub";
            // 이용약관
            if (!document.getElementById("terms_sub").checked) {
                alert("이용약관을 읽고 동의를 체크하여 주세요.");
                return;
            }
        } else {
            payname = "payment";
        }

        // 결제수단
        let elpayment = null;
        document.getElementsByName(payname).forEach((pay) => {
            if (pay.checked) elpayment = pay;
        });

        if (elpayment == "" || elpayment == null || elpayment == undefined) {
            alert("결제수단을 선택하여 주세요.");
            return;
        }

        if (document.getElementById("name").value == null
                || document.getElementById("name").value == "") {
            alert("주문자 이름을 입력해주세요.");
            return;
        }

        if (document.getElementById("receiver").value == null
            || document.getElementById("receiver").value == "") {
            alert("받으시는 분 이름을 입력해주세요.");
            return;
        }

        if (document.getElementById("phone").value == null
            || document.getElementById("phone").value == "") {
            alert("받으시는 분 연락처를 입력해주세요.");
            return;
        }

        if (document.getElementById("zipcode").value == null
            || document.getElementById("zipcode").value == "") {
            alert("우편번호를 입력해주세요.");
            return;
        }

        if (document.getElementById("streetAddr").value == null
            || document.getElementById("streetAddr").value == "") {
            alert("도로명주소를 입력해주세요.");
            return;
        }

        if (document.getElementById("detailAddr").value == null
            || document.getElementById("detailAddr").value == "") {
            alert("상세주소를 입력해주세요.");
            return;
        }

        body = {
            name: document.getElementById("name").value,
            receiver: document.getElementById("receiver").value,
            phone: document.getElementById("phone").value,
            address: {
                zipcode: document.getElementById("zipcode").value,
                streetAddr: document.getElementById("streetAddr").value,
                detailAddr: document.getElementById("detailAddr").value
            },
            payment: elpayment.value,
            orderNote: document.getElementById("orderNote").value,
            cart_item_id: Array.from(document.getElementsByName("checkbox-item-id"))
                            .map(el => Number(el.value))
        }
        function success() {
            alert("상품 주문이 완료되었습니다.");
            location.replace("/main/myOrder");
        }
        function fail() {
            alert("상품 주문이 실패했습니다.");
            location.replace("/main/myCart");
        }

        httpRequest("POST", "/api/orders", body, success, fail);
    });
}

// 내정보 => 배송지 정보
const shipCheckBox = document.getElementById("shiping-address");

if (shipCheckBox) {
    shipCheckBox.addEventListener("click", (event) => {
        if (shipCheckBox.checked) {
            document.getElementById("receiver").value = document.getElementById("hiddenReceiver").value;
            document.getElementById("phone").value = document.getElementById("hiddenPhone").value;
            document.getElementById("zipcode").value = document.getElementById("hiddenZipcode").value;
            document.getElementById("streetAddr").value = document.getElementById("hiddenStreetAddr").value;
            document.getElementById("detailAddr").value = document.getElementById("hiddenDetailAddr").value;
        } else {
            document.getElementById("receiver").value = "";
            document.getElementById("phone").value = "";
            document.getElementById("zipcode").value = "";
            document.getElementById("streetAddr").value = "";
            document.getElementById("detailAddr").value = "";
        }
    });
}