package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.Review;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.ItemListViewResponse;
import me.ceskim493.springbootdeveloper.dto.OrderListViewResponse;
import me.ceskim493.springbootdeveloper.service.ItemService;
import me.ceskim493.springbootdeveloper.service.MainService;
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

    private final ItemService itemService;
    private final OrderService orderService;
    private final UserService userService;
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
            // 내가 주문한 상품 내역
            String username = userService.getSessionUserName(sUser);
            User user = userService.findByEmail(username);
            List<OrderListViewResponse> orders = orderService.findAll(user).stream()
                    .map(OrderListViewResponse::new)
                    .toList();
            model.addAttribute("orders", orders);
            return "myOrder";
        } else if (pageName.equals("product")) {
            return "product";
        } else if (pageName.equals("myWish")) {
            return "myWish";
        }

        return "main";
    }
}
