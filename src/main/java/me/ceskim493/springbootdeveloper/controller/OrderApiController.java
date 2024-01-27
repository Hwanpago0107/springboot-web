package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Delivery;
import me.ceskim493.springbootdeveloper.domain.Order;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.CreateOrderRequest;
import me.ceskim493.springbootdeveloper.service.CartService;
import me.ceskim493.springbootdeveloper.service.ItemService;
import me.ceskim493.springbootdeveloper.service.OrderService;
import me.ceskim493.springbootdeveloper.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController // http Response Body에 객체 데이터를 JSON 형식으로 변환하는 컨트롤러
public class OrderApiController {

    private final ItemService itemService;
    private final CartService cartService;
    private final UserService userService;
    private final OrderService orderService;

    // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/orders")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) throws Exception {
        // 로그인한 유저
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);

        // 배송
        Delivery delivery = new Delivery();

        Order orderedItem = orderService.save(request, user, delivery);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderedItem);
    }

    @DeleteMapping("/api/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long id) {
        orderService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
