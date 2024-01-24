package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Cart;
import me.ceskim493.springbootdeveloper.domain.CartItem;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.CreateCartRequest;
import me.ceskim493.springbootdeveloper.repository.CartItemRepository;
import me.ceskim493.springbootdeveloper.repository.CartRepository;
import me.ceskim493.springbootdeveloper.repository.ItemRepository;
import me.ceskim493.springbootdeveloper.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public CartItem save(CreateCartRequest request, User user) {
        // 사용자가 담은 카트의 아이템의 개수와 실제 재고량을 체크해서 비교한다.
        Item item = itemRepository.findById(request.getItem_id())
                .orElseThrow(() -> new IllegalArgumentException("no items"));

        if (item.getStockQuantity() < request.getQuantity()) {
            // 수량이 적습니다 에러 내기
            throw new IllegalArgumentException("less than real stock");
        }

        // 사용자가 갖고 있는 카트가 없으면 만들어준다.
        if (cartRepository.findCartByUser(user).isEmpty()) {
            Cart cart = new Cart(user);
            cartRepository.save(cart);
        }

        Cart cart = cartRepository.findCartByUser(user).get();
        CartItem cartItem = new CartItem(cart, item, request.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public List<CartItem> findAll(User user) {
        if (cartRepository.findCartByUser(user).isEmpty()) {
            return new ArrayList<CartItem>();
        }
        Cart cart = cartRepository.findCartByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("unexpected error"));

        return cartItemRepository.findAllByCart(cart);
    }

    @Transactional
    public void delete(List<Long> checked) {
        cartItemRepository.deleteAllById(checked);
    }

    public void deleteAll() {
        cartItemRepository.deleteAll();
    }
}
