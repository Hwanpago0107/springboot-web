package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.Category;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.domain.Review;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.dto.*;
import me.ceskim493.springbootdeveloper.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ItemViewController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final MainService mainService;
    private final AdminService adminService;
    private final ReviewService reviewService;

    @GetMapping("/new-item")
    public String newItem(@RequestParam(required = false) Long id, Model model, @LoginUser SessionUser user) {
        // AdminLayout.html
        model = adminService.getAdminLayout(model, user);

        if (id == null) {
            model.addAttribute("aItem", new ItemViewResponse());
        } else {
            Item item = itemService.findById(id);
            model.addAttribute("aItem", new ItemViewResponse(item));
        }

        model.addAttribute("categories", categoryService.findAll());

        return "admin/newItem";
    }

    @GetMapping("/items")
    public String getItems(Model model, @LoginUser SessionUser user) {
        // AdminLayout.html
        model = adminService.getAdminLayout(model, user);

        List<ItemListViewResponse> items = itemService.findAll().stream()
                .map(ItemListViewResponse::new)
                .toList();

        model.addAttribute("items", items);

        return  "admin/itemList";
    }

    @GetMapping("/items/{id}")
    public String getItem(Model model, @PathVariable Long id, @LoginUser SessionUser sUser) {
        // MainLayout.html
        model = mainService.getMainLayout(model, sUser);

        // 해당하는 상품 하나를 가져온다.
        Item product = itemService.findById(id);
        Long category_id = product.getCategory().getId();

        // 카테고리에 해당하는 상품들을 가져온다.
        List<ItemListViewResponse> items = categoryService.findItemsByCategory(category_id).stream()
                .map(item -> {
                    if (item.getReviews() != null && item.getReviews().size() > 0) {
                        item.setAvgRating(item.getReviews().stream()
                                .mapToInt(Review::getRating)
                                .average()
                                .getAsDouble()
                        );
                    }
                    return item;
                })
                .map(ItemListViewResponse::new)
                .toList();

        // 카테고리가 속해 있는 카테고리들을 전부 갖고온다.
        List<CategoryViewResponse> parents = categoryService.findParentsCategoryByCategory(category_id).stream()
                .map(CategoryViewResponse::new)
                .toList();

        int pageNum = 1 - 1;
        int startNumber = pageNum * 3;
        int endNumber = (pageNum+1) * 3;

        // 상품에 등록되어 있는 리뷰들을 갖고온다. (paging)
        List<Review> origin = reviewService.findAllByItemId(id);

        List<ReviewViewResponse> reviews = IntStream.range(0, origin.size())
                .filter(index -> index >= startNumber && index < endNumber)
                .mapToObj(origin::get)
                .collect(Collectors.toList())
                .stream().map(ReviewViewResponse::new).toList();

        int startPage = (((int) Math.ceil(((double) 1 / 3))) - 1) * 3 + 1;
        int endPage = Math.min((startPage + 3 - 1), (origin.size() - 1) / 3 + 1);

        // 해당 아이템의 리뷰 평균 별점 및 별점 카운트
        ReviewViewResponse ratings = new ReviewViewResponse();
        List<Rating> ratingList = reviewService.findAvgRatingByItemId(id);
        if (ratingList != null && ratingList.size() > 0) {
            Review review = new Review(ratingList.get(0).getAvg(), ratingList.get(0).getCnt5(),
                    ratingList.get(0).getCnt4(), ratingList.get(0).getCnt3(), ratingList.get(0).getCnt2(),
                    ratingList.get(0).getCnt1());
            ratings = new ReviewViewResponse(review);
        }


        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", 1);

        model.addAttribute("product", product);
        model.addAttribute("parents", parents);
        model.addAttribute("items", items);
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewOrigin", origin);
        model.addAttribute("tabNumber", 1);
        model.addAttribute("ratings", ratings);

        return  "product";
    }

    @GetMapping("/stores")
    public String getItems(Model model, @LoginUser SessionUser sUser,
                           @RequestParam(required = false, name = "category_id") Long id,
                           @RequestParam(required = false, name = "page_number") int pageNumber,
                           @RequestParam(required = false, name = "page_limit") int pageLimit,
                           @RequestParam(required = false, defaultValue = "", name = "search_text") String search,
                           @RequestParam(required = false, name = "sort_by") String sortBy,
                           @RequestParam(required = false, name = "kind") String kind,
                           @RequestParam(required = false, defaultValue = "", name = "price_min") String priceMin,
                           @RequestParam(required = false, defaultValue = "", name = "price_max") String priceMax,
                           @RequestParam(required = false, defaultValue = "", name = "categories") String categories) {

        // MainLayout.html
        model = mainService.getMainLayout(model, sUser);

        // 현재 속해있는 카테고리(depth1)
        Category currentCategory = categoryService.findById(id);

        List<Category> categoryList = new ArrayList<>();
        if (id == 0L && categories.length() > 0) {
            List<Long> categoryIds = Arrays.stream(categories.split(","))
                    .map(a -> Long.valueOf(a)).toList();

            categoryList = categoryService.findCategoriesByCategoryIds(categoryIds);
        } else {
            categoryList.add(currentCategory);
        }

        // 선택한 카테고리의 브랜드(depth3)를 전부가져온다.
        List<Category> brandCategories = categoryService.findBrandsByCategory(id);

        List<CategoryViewResponse> brands = brandCategories.stream()
                .map(CategoryViewResponse::new)
                .toList();

        int pageNum = pageNumber - 1;
        int startNumber = pageNum * pageLimit;
        int endNumber = (pageNum+1) * pageLimit;

        int priceMinNumber = "".equals(priceMin) ? 10000 : Integer.valueOf(priceMin.replaceAll(",", ""));
        int priceMaxNumber = "".equals(priceMax) ? 99999999 : Integer.valueOf(priceMax.replaceAll(",", ""));

        List<Item> origin = null;
        if ("hotdeal".equals(kind)) {
            // 세일상품을 갖고온다. (30% 이상 세일 상품)
            origin = itemService.findHotdealItems(0.3F, priceMinNumber, priceMaxNumber).stream()
                    .map(item -> {
                        if (item.getReviews() != null && item.getReviews().size() > 0) {
                            item.setAvgRating(item.getReviews().stream()
                                    .mapToInt(Review::getRating)
                                    .average()
                                    .getAsDouble()
                            );
                        }
                        return item;
                    })
                    .toList();
        } else {
            // 카테고리에 해당하는 상품리스트를 갖고온다.
            origin = categoryService.search(categoryList, search, sortBy, priceMinNumber, priceMaxNumber).stream()
                    .map(item -> {
                        if (item.getReviews() != null && item.getReviews().size() > 0) {
                            item.setAvgRating(item.getReviews().stream()
                                    .mapToInt(Review::getRating)
                                    .average()
                                    .getAsDouble()
                            );
                        }
                        return item;
                    })
                    .toList();
        }
        // paging
        List<ItemListViewResponse> items = IntStream.range(0, origin.size())
                .filter(index -> index >= startNumber && index < endNumber)
                .mapToObj(origin::get)
                .map(ItemListViewResponse::new)
                .collect(Collectors.toList());

        int startPage = (((int) Math.ceil(((double) pageNumber / pageLimit))) - 1) * pageLimit + 1;
        int endPage = Math.min((startPage + pageLimit - 1), (origin.size() - 1) / pageLimit + 1);

        // 카테고리가 속해 있는 부모카테고리들 까지 전부 갖고온다.
        List<CategoryViewResponse> parents = categoryService.findParentsCategoryByCategory(id).stream()
                .map(CategoryViewResponse::new)
                .toList();

        // Top Selling 목록 상위 5품목
        List<ItemListViewResponse> top5 = itemService.findBySaleCountsLimit5(brandCategories).stream()
                .map(ItemListViewResponse::new)
                .toList();

        // 선택한 카테고리 ID 리스트
        List<Long> currentCategoryIds = new ArrayList<>();
        currentCategoryIds.addAll(categoryList.stream().mapToLong(Category::getId).boxed().toList());

        model.addAttribute("brands", brands);
         model.addAttribute("parents", parents);
        model.addAttribute("items", items);
        model.addAttribute("top5", top5);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("kind", kind);
        model.addAttribute("currentLimit", pageLimit);
        model.addAttribute("currentPriceMin", priceMinNumber);
        model.addAttribute("currentPriceMax", priceMaxNumber);
        model.addAttribute("currentCategory", currentCategory);
        model.addAttribute("currentCategoryIds", currentCategoryIds);

        return "store";
    }

    @GetMapping("/products")
    public String getProduct(Model model, @LoginUser SessionUser sUser,
                           @RequestParam(required = false, name = "item_id") Long id,
                           @RequestParam(required = false, defaultValue = "1", name = "page_number") int pageNumber,
                           @RequestParam(required = false, defaultValue = "1", name = "tab_number") int tabNumber) {

        // MainLayout.html
        model = mainService.getMainLayout(model, sUser);

        // 해당하는 상품 하나를 가져온다.
        Item product = itemService.findById(id);
        Long category_id = product.getCategory().getId();


        // 카테고리에 해당하는 상품들을 가져온다.
        List<ItemListViewResponse> items = categoryService.findItemsByCategory(category_id).stream()
                .map(item -> {
                    if (item.getReviews() != null && item.getReviews().size() > 0) {
                        item.setAvgRating(item.getReviews().stream()
                                .mapToInt(Review::getRating)
                                .average()
                                .getAsDouble()
                        );
                    }
                    return item;
                })
                .map(ItemListViewResponse::new)
                .toList();

        // 카테고리가 속해 있는 카테고리들을 전부 갖고온다.
        List<CategoryViewResponse> parents = categoryService.findParentsCategoryByCategory(category_id).stream()
                .map(CategoryViewResponse::new)
                .toList();

        int pageNum = pageNumber - 1;
        int startNumber = pageNum * 3;
        int endNumber = (pageNum+1) * 3;

        // 상품에 등록되어 있는 리뷰들을 갖고온다. (paging)
        List<Review> origin = reviewService.findAllByItemId(id);

        List<ReviewViewResponse> reviews = IntStream.range(0, origin.size())
                .filter(index -> index >= startNumber && index < endNumber)
                .mapToObj(origin::get)
                .collect(Collectors.toList())
                .stream().map(ReviewViewResponse::new).toList();

        int startPage = (((int) Math.ceil(((double) pageNumber / 3))) - 1) * 3 + 1;
        int endPage = Math.min((startPage + 3 - 1), (origin.size() - 1) / 3 + 1);

        // 해당 아이템의 리뷰 평균 별점 및 별점 카운트
        ReviewViewResponse ratings = new ReviewViewResponse();
        List<Rating> ratingList = reviewService.findAvgRatingByItemId(id);
        if (ratingList != null && ratingList.size() > 0) {
            Review review = new Review(ratingList.get(0).getAvg(), ratingList.get(0).getCnt5(),
                    ratingList.get(0).getCnt4(), ratingList.get(0).getCnt3(), ratingList.get(0).getCnt2(),
                    ratingList.get(0).getCnt1());
            ratings = new ReviewViewResponse(review);
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("product", product);
        model.addAttribute("parents", parents);
        model.addAttribute("items", items);
        model.addAttribute("reviews", reviews);
        model.addAttribute("reviewOrigin", origin);
        model.addAttribute("tabNumber", tabNumber);
        model.addAttribute("ratings", ratings);

        return "product";
    }
}
