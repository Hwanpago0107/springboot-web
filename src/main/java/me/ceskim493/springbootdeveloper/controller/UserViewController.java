package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.CartViewResponse;
import me.ceskim493.springbootdeveloper.dto.OrderListViewResponse;
import me.ceskim493.springbootdeveloper.dto.UserViewResponse;
import me.ceskim493.springbootdeveloper.service.CartService;
import me.ceskim493.springbootdeveloper.service.OrderService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/myInfo")
    public String getMyInfo(Model model, @LoginUser SessionUser sessionUser) {
        User user = userService.findByEmail(userService.getSessionUserName(sessionUser));
        List<OrderListViewResponse> orders = orderService.findAll(user).stream()
                .map(OrderListViewResponse::new)
                .toList();
        List<CartViewResponse> cartItems = cartService.findAll(user).stream()
                .map(CartViewResponse::new)
                .toList();

        List<User> userList = new ArrayList<>();
        userList.add(user);

        List<UserViewResponse> users = userList.stream()
                .map(UserViewResponse::new)
                .toList();

        model.addAttribute("users", users);
        model.addAttribute("orders", orders);
        model.addAttribute("cartItems", cartItems);

        return "/myInfo";
    }
}
