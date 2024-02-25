$(document).ready(function () {
	getBrands(this);
});

$(".depth1-checkbox input[type=checkbox]").on("click", function(event) {
	getBrands(this);
});

function getBrands(em) {
	let categories = "";
	let categoryArr = [];
	$(".category_checkbox").each(function () {
		if (this.checked) categoryArr.push(this.value);
	});
	categories = categoryArr.join(",");

	let currentDepth = $("#current_depth").val();
	if (currentDepth == "3") {
		$(".brand_checkbox").each(function () {
			this.checked = true;
		});
		return;
	}

	if (categories.length < 1) {
		alert("카테고리 항목을 하나 이상 선택하여 주세요.");
		em.checked = true;
		return;
	}

	function success(datas) {
		console.log(datas);
		let htmlText = "";
		for (const data of datas) {
			htmlText += "<div class='input-checkbox depth3-checkbox'>";
			htmlText += "<input type='checkbox' class='brand_checkbox' id='brand-" + data.category_id +"' value=" + data.category_id +" ";
			htmlText += "checked=true>";
			htmlText += "<label for='brand-" + data.category_id +"'>";
			htmlText += "<span></span>";
			htmlText +=	"<h6 style='display:inline-block;'>" + data.name + "</h6>";
			htmlText +=	"<small style='display:inline-block;'>&nbsp(" + data.count + ")</small>";
			htmlText +=	"</label>";
			htmlText +=	"</div>";
		}

		$('.brand').get(0).innerHTML = htmlText;
	}

	function fail() {
		if (currentDepth != "3") alert("브랜드 카테고리를 가져오지 못하였습니다.");
	}

	httpRequestBody("GET", "/api/brands/" + categories, null, success, fail);
}
