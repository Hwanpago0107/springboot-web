package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
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

        return "newItem";
    }

    @GetMapping("/items")
    public String getItems(Model model, @LoginUser SessionUser user) {
        // AdminLayout.html
        model = adminService.getAdminLayout(model, user);

        List<ItemListViewResponse> items = itemService.findAll().stream()
                .map(ItemListViewResponse::new)
                .toList();

        model.addAttribute("items", items);

        return  "itemList";
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
                    item.setAvgRating(item.getReviews().stream()
                            .mapToInt(Review::getRating)
                            .average()
                            .getAsDouble()
                    );
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
        model.addAttribute("productYn", "Y");

        return  "product";
    }

    @GetMapping("/hotdeals")
    public String getHotdeals(Model model, @LoginUser SessionUser sUser) {
        // MainLayout.html
        model = mainService.getMainLayout(model, sUser);

        // 세일상품을 갖고온다. (30% 이상 세일 상품)
        List<ItemListViewResponse> items = itemService.findByDiscountGreaterThanEqual(0.3F).stream()
                .map(item -> {
                    item.setAvgRating(item.getReviews().stream()
                            .mapToInt(Review::getRating)
                            .average()
                            .getAsDouble()
                    );
                    return item;
                })
                .map(ItemListViewResponse::new)
                .toList();

        List<Category> parents = new ArrayList<>();

        model.addAttribute("items", items);
        model.addAttribute("parents", parents);
        model.addAttribute("hotdealYn", "Y");

        return  "product";
    }

    @GetMapping("/stores")
    public String getItems(Model model, @LoginUser SessionUser sUser,
                           @RequestParam(required = false, name = "category_id") Long id,
                           @RequestParam(required = false, name = "page_number") int pageNumber,
                           @RequestParam(required = false, name = "page_limit") int pageLimit,
                           @RequestParam(required = false, defaultValue = "", name = "search_text") String search,
                           @RequestParam(required = false, name = "sort_by") String sortBy) {

        // MainLayout.html
        model = mainService.getMainLayout(model, sUser);

        // 선택한 카테고리의 브랜드(depth3)를 전부가져온다.
        List<CategoryViewResponse> brands = categoryService.findBrandsByCategory(id).stream()
                .map(CategoryViewResponse::new)
                .toList();

        int pageNum = pageNumber - 1;
        int startNumber = pageNum * pageLimit;
        int endNumber = (pageNum+1) * pageLimit;

        // 현재 속해있는 카테고리
        Category currentCate = categoryService.findById(id);
        Category depth1Cate = new Category();
        if ("1".equals(currentCate.getDepth())) {
            depth1Cate = currentCate;
        }
        // 카테고리에 해당하는 상품리스트를 갖고온다. (paging)
        List<Item> origin = categoryService.search(id, search, sortBy).stream()
                .map(item -> {
                    item.setAvgRating(item.getReviews().stream()
                            .mapToInt(Review::getRating)
                            .average()
                            .getAsDouble()
                    );
                    return item;
                })
                .toList();

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
        List<ItemListViewResponse> top5 = itemService.findBySaleCountsLimit5().stream()
                .map(ItemListViewResponse::new)
                .toList();

        model.addAttribute("brands", brands);
        model.addAttribute("parents", parents);
        model.addAttribute("items", items);
        model.addAttribute("currentCate", currentCate);
        model.addAttribute("depth1Cate", depth1Cate);
        model.addAttribute("top5", top5);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("currentLimit", pageLimit);

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
                    item.setAvgRating(item.getReviews().stream()
                            .mapToInt(Review::getRating)
                            .average()
                            .getAsDouble()
                    );
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
        model.addAttribute("productYn", "Y");

        return "product";
    }
}
