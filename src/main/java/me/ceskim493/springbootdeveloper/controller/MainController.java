package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.dto.ItemListViewResponse;
import me.ceskim493.springbootdeveloper.service.ItemService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String main(Model model, @LoginUser SessionUser user) {

        List<ItemListViewResponse> items = itemService.findAll().stream()
                .map(ItemListViewResponse::new)
                .toList();

        model.addAttribute("items", items);
        model.addAttribute("username", userService.getSessionUserName(user)); // session에 저장된 유저이름 setting

        return  "main";
    }
}
