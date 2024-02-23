package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.CartItem;
import me.ceskim493.springbootdeveloper.domain.SessionUser;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.CartViewResponse;
import me.ceskim493.springbootdeveloper.dto.CategoryViewResponse;
import me.ceskim493.springbootdeveloper.dto.UserViewResponse;
import me.ceskim493.springbootdeveloper.dto.WishViewResponse;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class MainService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final CartService cartService;
    private final WishService wishService;

    public Model getMainLayout(Model model, SessionUser sUser) {
        // start.mainLayout
        // 로그인한 유저
        String username = userService.getSessionUserName(sUser);
        User user = userService.findByEmail(username);

        // start.카테고리 목록 가져오기(최상위 목록만)
        List<CategoryViewResponse> depth1 = categoryService.findCategoriesAndCountByDepth("1").stream()
                .map(CategoryViewResponse::new)
                .toList();
        // end.카테고리 목록 가져오기(최상위 목록만)

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
        // end.로그인한 사용자가 가지고 있는 장바구니와 장바구니 상품 리스트, 장바구니 총액

        // start.로그인한 사용자가 가지고 있는 위시리스트
        List<WishViewResponse> wishes = wishService.findAll(user).stream()
                .map(WishViewResponse::new)
                .toList();
        // end.로그인한 사용자가 가지고 있는 위시리스트

        // start.사용자의 정보를 가지고 온다.
        UserViewResponse userInfo = user.createUserView();
        // end.사용자의 정보를 가지고 온다.
        // end.mainLayout

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("depth1", depth1);
        model.addAttribute("wishes", wishes);
        model.addAttribute("username", username); // session에 저장된 유저이름 setting

        return model;
    }
}
