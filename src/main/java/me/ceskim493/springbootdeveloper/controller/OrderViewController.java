package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.CartViewResponse;
import me.ceskim493.springbootdeveloper.dto.OrderListViewResponse;
import me.ceskim493.springbootdeveloper.service.CartService;
import me.ceskim493.springbootdeveloper.service.ItemService;
import me.ceskim493.springbootdeveloper.service.OrderService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderViewController {

    private final ItemService itemService;
    private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/orders")
    public String getOrders(Model model, @LoginUser SessionUser sessionUser) {
        User user = userService.findByEmail(userService.getSessionUserName(sessionUser));
        List<OrderListViewResponse> orders = orderService.findAll(user).stream()
                .map(OrderListViewResponse::new)
                .toList();
        List<CartViewResponse> cartItems = cartService.findAll(user).stream()
                .map(CartViewResponse::new)
                .toList();

        model.addAttribute("orders", orders);
        model.addAttribute("cartItems", cartItems);

        return  "orderList";
    }
}
