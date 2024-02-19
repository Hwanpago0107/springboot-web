(function($) {
	"use strict"

	// Mobile Nav toggle
	$('.menu-toggle > a').on('click', function (e) {
		e.preventDefault();
		$('#responsive-nav').toggleClass('active');
	})

	// Fix cart dropdown from closing
	$('.cart-dropdown').on('click', function (e) {
		e.stopPropagation();
	});

	/////////////////////////////////////////

	// Products Slick
	$('.products-slick').each(function() {
		var $this = $(this),
				$nav = $this.attr('data-nav');

		$this.slick({
			slidesToShow: 4,
			slidesToScroll: 1,
			autoplay: true,
			infinite: true,
			speed: 300,
			dots: false,
			arrows: true,
			appendArrows: $nav ? $nav : false,
			responsive: [{
	        breakpoint: 991,
	        settings: {
	          slidesToShow: 2,
	          slidesToScroll: 1,
	        }
	      },
	      {
	        breakpoint: 480,
	        settings: {
	          slidesToShow: 1,
	          slidesToScroll: 1,
	        }
	      },
	    ]
		});
	});

	// Products Widget Slick
	$('.products-widget-slick').each(function() {
		var $this = $(this),
				$nav = $this.attr('data-nav');

		$this.slick({
			infinite: true,
			autoplay: true,
			speed: 300,
			dots: false,
			arrows: true,
			appendArrows: $nav ? $nav : false,
		});
	});

	/////////////////////////////////////////

	// Product Main img Slick
	$('#product-main-img').slick({
    infinite: true,
    speed: 300,
    dots: false,
    arrows: true,
    fade: true,
    asNavFor: '#product-imgs',
  });

	// Product imgs Slick
  $('#product-imgs').slick({
    slidesToShow: 3,
    slidesToScroll: 1,
    arrows: true,
    centerMode: true,
    focusOnSelect: true,
		centerPadding: 0,
		vertical: true,
    asNavFor: '#product-main-img',
		responsive: [{
        breakpoint: 991,
        settings: {
					vertical: false,
					arrows: false,
					dots: true,
        }
      },
    ]
  });

	// Product img zoom
	var zoomMainProduct = document.getElementById('product-main-img');
	if (zoomMainProduct) {
		$('#product-main-img .product-preview').zoom();
	}

	/////////////////////////////////////////

	// Input number
	$('.input-number').each(function() {
		var $this = $(this),
			$input = $this.find('input[type="text"]'),
			up = $this.find('.qty-up'),
			down = $this.find('.qty-down'),
			curQty = $this.find('#product-stock'),
			$price = $this.find('.price-yn'),
			bPrice = false;
		if ($price.val() != null && $price.val() != undefined) {
			bPrice = true;
		}

		down.on('click', function () {
			if (bPrice) {
				var value = parseInt($input.val().replaceAll(",", "")) - 100;
				value = value < 1 ? 1 : value;
				$input.val(value);
				$input.change();
				updatePriceSlider($this, value)
			} else {
				var value = parseInt($input.val()) - 1;
				value = value < 1 ? 1 : value;
				$input.val(value);
				$input.change();
				updatePriceSlider($this, value)
			}
		})

		up.on('click', function () {
			if (bPrice) {
				var value = parseInt($input.val().replaceAll(",", "")) + 100;
				value = value < 1 ? 1 : value;
				$input.val(value);
				$input.change();
				updatePriceSlider($this, value);
			} else {
				var value = parseInt($input.val()) + 1;
				if (parseInt(curQty.val()) < value) {
					alert("선택하신 상품의 재고가 부족합니다.");
					return;
				}
				value = value < 1 ? 1 : value;
				$input.val(value);
				$input.change();
				updatePriceSlider($this, value);
			}
		})

		var priceInputMax = document.getElementById('price-max'),
			priceInputMin = document.getElementById('price-min');

		if (priceInputMax) {
			priceInputMax.addEventListener('change', function () {
				updatePriceSlider($(this).parent(), this.value)
			});
		}

		if (priceInputMin) {
			priceInputMin.addEventListener('change', function () {
				updatePriceSlider($(this).parent(), this.value)
			});
		}

		function updatePriceSlider(elem, value) {
			if (elem.hasClass('price-min')) {
				console.log('min')
				priceSlider.noUiSlider.set([value, null]);
			} else if (elem.hasClass('price-max')) {
				console.log('max')
				priceSlider.noUiSlider.set([null, value]);
			}
		}

		// Price Slider
		var priceSlider = document.getElementById('price-slider');
		if (priceSlider) {
			if (priceSlider.noUiSlider) {
				priceSlider.noUiSlider.destroy();
			}
			noUiSlider.create(priceSlider, {
				start: [1000, 9999999],
				connect: true,
				step: 100,
				range: {
					'min': 1000,
					'max': 9999999
				},
				format: wNumb({
					decimals: 0,
					thousand: ","
				})
			});

			priceSlider.noUiSlider.on('update', function (values, handle) {
				var value = values[handle];
				handle ? priceInputMax.value = value : priceInputMin.value = value
			});
		}
	});
})(jQuery);

$(document).on('show.bs.dropdown', function(event) {
	$('.dropdown-backdrop').off().remove();
});

function getStore(id, pageNumber, kind) {
	if (pageNumber == null || pageNumber == undefined) {
		pageNumber = 1;
	}

	let pageLimit = $('#page_limit option:selected').val();
	if (pageLimit == null || pageLimit == undefined) {
		pageLimit = 3;
	}

	let sortBy = $('#sort_by option:selected').val();
	if (sortBy == null || sortBy == undefined) {
		sortBy = "popular";
	}

	if (kind == null) {
		kind = "";
	}

	const form = document.createElement("form");
	form.setAttribute("charset", "UTF-8");
	form.setAttribute("method", "GET");
	form.setAttribute("action", "/stores");

	var input1 = document.createElement("input");
	input1.setAttribute("type", "hidden");
	input1.setAttribute("name", "category_id");
	input1.setAttribute("value", id);
	form.appendChild(input1);

	var input2 = document.createElement("input");
	input2.setAttribute("type", "hidden");
	input2.setAttribute("name", "page_number");
	input2.setAttribute("value", pageNumber);
	form.appendChild(input2);

	var input3 = document.createElement("input");
	input3.setAttribute("type", "hidden");
	input3.setAttribute("name", "page_limit");
	input3.setAttribute("value", Number(pageLimit));
	form.appendChild(input3);

	var input4 = document.createElement("input");
	input4.setAttribute("type", "hidden");
	input4.setAttribute("name", "sort_by");
	input4.setAttribute("value", sortBy);
	form.appendChild(input4);

	var input5 = document.createElement("input");
	input5.setAttribute("type", "hidden");
	input5.setAttribute("name", "kind");
	input5.setAttribute("value", kind);
	form.appendChild(input5);

	document.body.appendChild(form);
	form.submit();
}