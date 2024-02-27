package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.Role;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.dto.UserViewResponse;
import me.ceskim493.springbootdeveloper.service.AdminService;
import me.ceskim493.springbootdeveloper.service.MainService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserViewController {

    private final UserService userService;
    private final AdminService adminService;
    private final MainService mainService;

    @GetMapping("/login")
    public String login(Model model, @LoginUser SessionUser sUser) {
        // MainLayout.html
        model = mainService.getMainLayout(model, sUser);

        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/users/{type}")
    public String getUsers(Model model, @PathVariable String type, @LoginUser SessionUser sUser) {
        // AdminLayout.html
        model = adminService.getAdminLayout(model, sUser);

        List<UserViewResponse> users = null;
        if ("guest".equals(type)) {
            users = userService.findAllByRole(Role.GUEST).stream()
                    .map(UserViewResponse::new)
                    .toList();
        } else {
            users = userService.findAllValidUser(1).stream()
                    .map(UserViewResponse::new)
                    .toList();
        }

        model.addAttribute("users", users);

        return "admin/userList";
    }
}
