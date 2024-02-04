package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.*;
import me.ceskim493.springbootdeveloper.dto.*;
import me.ceskim493.springbootdeveloper.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CategoryViewController {

    private final CategoryService categoryService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;
    private final WishService wishService;

    @GetMapping("/newCategory")
    public String newItem(@RequestParam(required = false) Long id, Model model, @LoginUser SessionUser user) {
        if (id == null) {
            model.addAttribute("category", new CategoryViewResponse());
        } else {
            Category category = categoryService.findById(id);
            model.addAttribute("category", new CategoryViewResponse(category));
        }

        model.addAttribute("parents", categoryService.findChildCategoriesByParent(0L));
        model.addAttribute("categories", categoryService.findAll());

        model.addAttribute("userName", userService.getSessionUserName(user)); // session에 저장된 유저이름 setting

        return "newCategory";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        List<CategoryViewResponse> categories = categoryService.findAll().stream()
                .map(CategoryViewResponse::new)
                .toList();

        model.addAttribute("categories", categories);

        return  "categoryList";
    }

    @GetMapping("/category/{id}")
    public String getItemsByCategory(Model model, @PathVariable Long id, @LoginUser SessionUser sUser) {
        // start.mainLayout
        // 로그인한 유저
        String username = userService.getSessionUserName(sUser);
        User user = userService.findByEmail(username);

        // start.카테고리 목록 가져오기(최상위 목록만)
        List<CategoryViewResponse> categories = categoryService.findChildCategoriesByParent(0L).stream()
                .map(CategoryViewResponse::new)
                .toList();
        // end.카테고리 목록 가져오기(최상위 목록만)

        // start.로그인한 사용자가 가지고 있는 장바구니와 장바구니 상품 리스트, 장바구니 총액
        List<CartItem> carts = cartService.findAll(user);
        int totalPrice = carts.stream()
                .mapToInt(cartItem -> {
                    return (int) (cartItem.getItem().getPrice()
                            * cartItem.getQuantity()
                            * (1 - cartItem.getItem().getDiscount())
                    );
                })
                .sum();

        List<CartViewResponse> cartItems = carts.stream()
                .map(CartViewResponse::new)
                .toList();
        // end.로그인한 사용자가 가지고 있는 장바구니와 장바구니 상품 리스트, 장바구니 총액

        // start.내가 주문한 상품 내역
        List<OrderListViewResponse> orders = orderService.findAll(user).stream()
                .map(OrderListViewResponse::new)
                .toList();
        // end.내가 주문한 상품 내역

        // start.로그인한 사용자가 가지고 있는 위시리스트
        List<WishViewResponse> wishes = wishService.findAll(user).stream()
                .map(WishViewResponse::new)
                .toList();
        // end.로그인한 사용자가 가지고 있는 위시리스트

        // start.사용자의 정보를 가지고 온다.
        UserViewResponse userInfo = user.createUserView();
        // end.사용자의 정보를 가지고 온다.
        // end.mainLayout


        // 카테고리에 해당하는 상품리스트 중 하나를 갖고 온다.
        Item product = null;
        List<Item> items = categoryService.findItemsByCategory(id);
        if (items != null && items.size() > 0) {
            product = items.get(0);
        }

        Category currentCate = categoryService.findById(id);

        // 카테고리가 속해 있는 카테고리들을 전부 갖고온다.
        List<CategoryViewResponse> parents = categoryService.findParentsCategoryByCategory(id).stream()
                .map(CategoryViewResponse::new)
                .toList();

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("orders", orders);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("categories", categories);
        model.addAttribute("wishes", wishes);
        model.addAttribute("username", username); // session에 저장된 유저이름 setting
        model.addAttribute("product", product);
        model.addAttribute("parents", parents);
        model.addAttribute("items", items);
        model.addAttribute("currentCate", currentCate);
        model.addAttribute("categoryYn", "Y");


        return "product";
    }
}
