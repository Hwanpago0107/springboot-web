package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.CartViewResponse;
import me.ceskim493.springbootdeveloper.dto.ItemListViewResponse;
import me.ceskim493.springbootdeveloper.service.CartService;
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
    private final CartService cartService;

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/main")
    public String main(Model model, @LoginUser SessionUser sUser) {

        String username = userService.getSessionUserName(sUser);

        // 등록된 상품리스트
        List<ItemListViewResponse> items = itemService.findAll().stream()
                .map(ItemListViewResponse::new)
                .toList();

        // 로그인한 사용자가 가지고 있는 장바구니와 장바구니 상품 리스트
        User user = userService.findByEmail(username);
        List<CartViewResponse> cartItems = cartService.findAll(user).stream()
                .map(CartViewResponse::new)
                .toList();

        model.addAttribute("items", items);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("username", username); // session에 저장된 유저이름 setting

        return  "main";
    }
}
