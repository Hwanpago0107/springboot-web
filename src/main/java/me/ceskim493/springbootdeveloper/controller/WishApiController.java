package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.domain.WishItem;
import me.ceskim493.springbootdeveloper.dto.CreateWishRequest;
import me.ceskim493.springbootdeveloper.dto.DeleteWishRequest;
import me.ceskim493.springbootdeveloper.dto.WishResponse;
import me.ceskim493.springbootdeveloper.service.CartService;
import me.ceskim493.springbootdeveloper.service.UserService;
import me.ceskim493.springbootdeveloper.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController // http Response Body에 객체 데이터를 JSON 형식으로 변환하는 컨트롤러
public class WishApiController {

    private final CartService cartService;
    private final UserService userService;
    private final WishService wishService;

    // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    @PostMapping("/api/wishes")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<WishItem> addWishList(@RequestBody CreateWishRequest request) throws Exception {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);

        WishItem savedItem = wishService.save(request, user);

        // 요청한 자원이 성공적으로 생성되었으며 저장된 아이템 정보를 응답 객체에 담아 전송
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedItem);
    }

    @PostMapping("/api/wishes/checked")
    // @RequestBody로 요청 본문 값 매핑
    public ResponseEntity<Void> addWishListToCart(@RequestBody CreateWishRequest request) throws Exception {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);

        cartService.saveAll(request.getChecked(), user);

        // 요청한 자원이 성공적으로 생성
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/api/wishes")
    public ResponseEntity<List<WishResponse>> findAllWishItem() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);

        List<WishResponse> wishItems = wishService.findAll(user)
                .stream()
                .map(WishResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(wishItems);
    }

    @DeleteMapping("/api/wishes/checked")
    public ResponseEntity<Void> deleteWishItem(@RequestBody DeleteWishRequest request) {
        wishService.delete(request.getChecked());

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/api/wishes")
    public ResponseEntity<Void> deleteAllWishItems() {
        wishService.deleteAll();

        return ResponseEntity.ok()
                .build();
    }
}
