package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.annotation.LoginUser;
import me.ceskim493.springbootdeveloper.domain.Item;
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

import java.util.Comparator;
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
        return "redirect:/main";
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
            // 평균별점까지 포함된 등록 상품리스트
            List<Item> itemList = itemService.findAll().stream()
                    .map(item -> {
                        if (item.getReviews() != null && item.getReviews().size() > 0) {
                            item.setAvgRating(item.getReviews().stream()
                                    .mapToInt(Review::getRating)
                                    .average()
                                    .getAsDouble()
                            );
                        }
                        return item;
                    })
                    .toList();

            // 등록된 상품리스트
            List<ItemListViewResponse> items = itemList.stream()
                    .map(ItemListViewResponse::new)
                    .toList();

            // 핫딜 상품리스트
            List<ItemListViewResponse> hotdeals = itemService.findHotdealItems(0.3F, 0, 99999999).stream()
                    .map(ItemListViewResponse::new)
                    .toList();

            // Top Selling (상위 5품목)
            List<ItemListViewResponse> top5 = itemService.findBySaleCountsLimit5(null).stream()
                    .map(ItemListViewResponse::new)
                    .toList();

            // 낮은 가격순 (상위 5품목)
            List<ItemListViewResponse> row5 = itemList.stream()
                    .sorted((a,b) ->
                            (int) ((a.getPrice() * (1 - a.getDiscount())) - (b.getPrice() * (1 - b.getDiscount()))))
                    .limit(5)
                    .map(ItemListViewResponse::new)
                    .toList();

            // 평점순 (상위 5품목)
            List<ItemListViewResponse> topRating5 = itemList.stream()
                    .sorted(Comparator.comparing(Item::getAvgRating).reversed())
                    .limit(5)
                    .map(ItemListViewResponse::new)
                    .toList();

            model.addAttribute("items", items);
            model.addAttribute("hotdeals", hotdeals);
            model.addAttribute("top5", top5);
            model.addAttribute("row5", row5);
            model.addAttribute("topRating5", topRating5);
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
