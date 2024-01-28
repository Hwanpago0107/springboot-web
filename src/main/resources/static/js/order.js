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
        // 결제수단
        let elpayment = null;
        document.getElementsByName("payment").forEach((pay) => {
            if (pay.checked) elpayment = pay;
        });

        body = JSON.stringify({
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
        });

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
            document.getElementById("name").value = document.getElementById("hiddenName").value;
            document.getElementById("receiver").value = document.getElementById("hiddenReceiver").value;
            document.getElementById("phone").value = document.getElementById("hiddenPhone").value;
            document.getElementById("zipcode").value = document.getElementById("hiddenZipcode").value;
            document.getElementById("streetAddr").value = document.getElementById("hiddenStreetAddr").value;
            document.getElementById("detailAddr").value = document.getElementById("hiddenDetailAddr").value;
        } else {
            document.getElementById("name").value = "";
            document.getElementById("receiver").value = "";
            document.getElementById("phone").value = "";
            document.getElementById("zipcode").value = "";
            document.getElementById("streetAddr").value = "";
            document.getElementById("detailAddr").value = "";
        }
    });
}

