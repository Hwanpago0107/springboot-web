// 등록 기능
const createReviewBtn = document.getElementById("create-review-btn");

if (createReviewBtn) {
    createReviewBtn.addEventListener("click", (event) => {

        let rating = 1;
        $("input[name='rating']:checked").each(function(){
            rating = Number($(this).val());
        });

        const item_id = Number(document.getElementById("review_item_id").value);

        body = {
            item_id: item_id,
            name: document.getElementById("review_name").value,
            email: document.getElementById("review_email").value,
            content: document.getElementById("review_content").value,
            rating: rating
        };

        function success() {
            alert("등록이 완료되었습니다.");
            getProduct(item_id, 1);
        }
        function fail() {
            alert("등록이 실패했습니다.");
            getProduct(item_id, 1);
        }

        httpRequest("POST", "/api/reviews", body, success, fail);
    });
}

function getProduct(id, pageNumber) {
    let tabNumber = $('ul.tab-nav li.active').val();
    if (tabNumber == null) {
        tabNumber = 1;
    }

    const form = document.createElement("form");
    form.setAttribute("charset", "UTF-8");
    form.setAttribute("method", "GET");
    form.setAttribute("action", "/products");

    var input1 = document.createElement("input");
    input1.setAttribute("type", "hidden");
    input1.setAttribute("name", "item_id");
    input1.setAttribute("value", id);
    form.appendChild(input1);

    var input2 = document.createElement("input");
    input2.setAttribute("type", "hidden");
    input2.setAttribute("name", "page_number");
    input2.setAttribute("value", pageNumber);
    form.appendChild(input2);

    var input3 = document.createElement("input");
    input3.setAttribute("type", "hidden");
    input3.setAttribute("name", "tab_number");
    input3.setAttribute("value", Number(tabNumber));
    form.appendChild(input3);

    document.body.appendChild(form);
    form.submit();
}

$(document).ready(function () {
    const tabnum = $("#tab_num").val();
    tabClick();
});

function tabClick(tabnum) {
    $("#product-tab-nav a[href='#tab"+tabnum+"']").tab("show");
}

$("a[href='#tab3']").on("shown.bs.tab", function(e) {
    $("#review_name").attr("tabindex", -1).focus();
});
