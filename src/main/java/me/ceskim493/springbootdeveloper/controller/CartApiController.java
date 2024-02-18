package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.CartItem;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.CartResponse;
import me.ceskim493.springbootdeveloper.dto.CreateCartRequest;
import me.ceskim493.springbootdeveloper.dto.DeleteCartRequest;
import me.ceskim493.springbootdeveloper.service.CartService;
import me.ceskim493.springbootdeveloper.service.UserService;
import me.ceskim493.springbootdeveloper.util.SecurityUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // http Response Body에 객체 데이터를 JSON 형식으로 변환하는 컨트롤러
public class CartApiController {

    private final CartService cartService;
    private final UserService userService;

    // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/carts")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Void> addCartItem(@RequestBody CreateCartRequest request) throws Exception {

        User user = userService.findByEmail(SecurityUtil.getCurrentUserEmail());

        CartItem savedItem = cartService.save(request, user);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 아이템 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/api/carts")
    public ResponseEntity<List<CartResponse>> findAllCartItems() {
        User user = userService.findByEmail(SecurityUtil.getCurrentUserEmail());

        List<CartResponse> cartItems = cartService.findAll(user)
                .stream()
                .map(CartResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(cartItems);
    }

    @DeleteMapping("/api/carts/checked")
    public ResponseEntity<Void> deleteItem(@RequestBody DeleteCartRequest request) {
        cartService.delete(request.getChecked());

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/api/carts")
    public ResponseEntity<Void> deleteItem() {
        cartService.deleteAll();

        return ResponseEntity.ok()
                .build();
    }
}
