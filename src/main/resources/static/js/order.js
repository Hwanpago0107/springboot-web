// 상품 주문 기능
const preOrderBtn = document.getElementById("pre-order-btn");

if (preOrderBtn) {
    preOrderBtn.addEventListener("click", (event) => {
        let keepOrderYn = window.confirm("위 상품을 주문하시겠습니까?");
        if (keepOrderYn) {
            // 배송지 입력 여부 체크
            function success() {
                alert("상품 주문이 완료되었습니다.");
                location.replace("/main");
            }
            function fail() {
                alert("상품 주문이 실패했습니다.");
                location.replace("/main");
            }
            httpRequest("POST", "/api/orders", body, success, fail);
        }
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


