package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.dto.UserViewResponse;
import me.ceskim493.springbootdeveloper.service.AdminService;
import me.ceskim493.springbootdeveloper.service.CartService;
import me.ceskim493.springbootdeveloper.service.OrderService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;
    private final AdminService adminService;

    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/users")
    public String getUsers(Model model, @LoginUser SessionUser sUser) {
        // AdminLayout.html
        model = adminService.getAdminLayout(model, sUser);

        List<UserViewResponse> users = userService.findAll().stream()
                .map(UserViewResponse::new)
                .toList();

        model.addAttribute("users", users);

        return "userList";
    }
}
