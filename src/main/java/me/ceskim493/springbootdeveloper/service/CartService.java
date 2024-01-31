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

        if (item.getStockQuantity() < 1) {
            // 재고가 없습니다.
            throw new IllegalArgumentException("재고가 없습니다.");
        }

        // 사용자가 갖고 있는 카트가 없으면 만들어준다.
        if (cartRepository.findCartByUser(user).isEmpty()) {
            Cart cart = new Cart(user);
            cartRepository.save(cart);
        }

        Cart cart = cartRepository.findCartByUser(user).get();
        CartItem cartItem = null;

        // 저장할 상품이 동일한 경우 해당 상품의 수량만 업데이트 해준다.
        List<CartItem> cartItems = cartItemRepository.findCartItemByItem_Id(request.getItem_id());
        if (cartItems != null && cartItems.size() > 0) {
            cartItem = cartItems.get(0);
            int newQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = new CartItem(cart, item, 1);
        }

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
