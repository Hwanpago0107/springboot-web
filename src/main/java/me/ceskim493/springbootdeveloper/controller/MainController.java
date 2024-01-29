package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.*;
import me.ceskim493.springbootdeveloper.dto.CartViewResponse;
import me.ceskim493.springbootdeveloper.dto.ItemListViewResponse;
import me.ceskim493.springbootdeveloper.dto.OrderListViewResponse;
import me.ceskim493.springbootdeveloper.dto.UserViewResponse;
import me.ceskim493.springbootdeveloper.service.CartService;
import me.ceskim493.springbootdeveloper.service.ItemService;
import me.ceskim493.springbootdeveloper.service.OrderService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserService userService;
    private final ItemService itemService;
    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String home() {
        return "redirect:/main/home";
    }

    @GetMapping("/main/{pageName}")
    public String main(Model model, @LoginUser SessionUser sUser, @PathVariable String pageName) {

        String username = userService.getSessionUserName(sUser);
        User user = userService.findByEmail(username);

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
        // start.로그인한 사용자가 가지고 있는 장바구니와 장바구니 상품 리스트, 장바구니 총액

        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("username", username); // session에 저장된 유저이름 setting

        if (pageName.equals("home")) {
            // 등록된 상품리스트
            List<ItemListViewResponse> items = itemService.findAll().stream()
                    .map(ItemListViewResponse::new)
                    .toList();

            // 핫딜 상품리스트
            List<ItemListViewResponse> hotdeals = itemService.findByDiscountGreaterThanEqual(0.3F).stream()
                    .map(ItemListViewResponse::new)
                    .toList();

            List<ItemListViewResponse> top5 = itemService.findBySaleCountsLimit5().stream()
                    .map(ItemListViewResponse::new)
                    .toList();

            model.addAttribute("items", items);
            model.addAttribute("hotdeals", hotdeals);
            model.addAttribute("top5", top5);
        } else if (pageName.equals("myInfo") || pageName.equals("myCart")) {
            // 사용자의 정보를 가지고 온다.
            UserViewResponse userInfo = user.createUserView();

            model.addAttribute("userInfo", userInfo);
            return pageName;
        } else if (pageName.equals("myOrder")) {
            // 내가 주문한 상품 내역
            List<OrderListViewResponse> orders = orderService.findAll(user).stream()
                    .map(OrderListViewResponse::new)
                    .toList();

            model.addAttribute("orders", orders);

            return "myOrder";
        }

        return "main";
    }
}
