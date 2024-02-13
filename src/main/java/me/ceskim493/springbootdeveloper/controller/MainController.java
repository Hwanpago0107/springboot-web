package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.dto.ItemListViewResponse;
import me.ceskim493.springbootdeveloper.service.*;
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
    private final CategoryService categoryService;
    private final WishService wishService;
    private final MainService mainService;

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
        // MainLayout.html
        model = mainService.getMainLayout(model, sUser);

        if (pageName.equals("home")) {
            // 등록된 상품리스트
            List<ItemListViewResponse> items = itemService.findAll().stream()
                    .map(ItemListViewResponse::new)
                    .toList();

            // 핫딜 상품리스트
            List<ItemListViewResponse> hotdeals = itemService.findByDiscountGreaterThanEqual(0.3F).stream()
                    .map(ItemListViewResponse::new)
                    .toList();

            // Top Selling (상위 5품목)
            List<ItemListViewResponse> top5 = itemService.findBySaleCountsLimit5().stream()
                    .map(ItemListViewResponse::new)
                    .toList();

            model.addAttribute("items", items);
            model.addAttribute("hotdeals", hotdeals);
            model.addAttribute("top5", top5);
        } else if (pageName.equals("myInfo")) {
            return "myInfo";
        } else if (pageName.equals("myCart")) {
            return "myCart";
        } else if (pageName.equals("myOrder")) {
            return "myOrder";
        } else if (pageName.equals("product")) {
            return "product";
        } else if (pageName.equals("myWish")) {
            return "myWish";
        }

        return "main";
    }
}
