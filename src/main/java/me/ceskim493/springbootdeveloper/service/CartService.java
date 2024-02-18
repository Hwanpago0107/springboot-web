package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.Cart;
import me.ceskim493.springbootdeveloper.domain.CartItem;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.domain.User;
import me.ceskim493.springbootdeveloper.dto.CreateCartRequest;
import me.ceskim493.springbootdeveloper.repository.CartItemRepository;
import me.ceskim493.springbootdeveloper.repository.CartRepository;
import me.ceskim493.springbootdeveloper.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public CartItem save(CreateCartRequest request, User user) {
        // 사용자가 담은 카트의 아이템의 개수와 실제 재고량을 체크해서 비교한다.
        Item item = itemRepository.findById(request.getItem_id())
                .orElseThrow(() -> new IllegalArgumentException("no items"));

        if (item.getStockQuantity() < 1) {
            // 재고가 없습니다.
            log.info("재고가 없음. {}", item.getId());
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
        cartItem = cartItemRepository.findCartItemByItem_Id(request.getItem_id());
        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() + 1;
            cartItem.setQuantity(newQuantity);
        } else {
            cartItem = new CartItem(cart, item, 1);
        }

        log.info("Cart : {}, CartItem: {}", cart, cartItem);

        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public Cart saveAll(List<Long> itemIds, User user) {
        // 사용자가 담은 카트의 아이템의 개수와 실제 재고량을 체크해서 비교한다.
        List<Item> newItems = new ArrayList<>();
        for (int i = 0; i < itemIds.size(); i++) {
            try {
                Item item = itemRepository.findById(itemIds.get(i)).get();
                if (item.getStockQuantity() < 1) {
                    // 재고가 없습니다.
                    throw new IllegalArgumentException("재고가 없습니다.");
                }
                // 있는 것만 장바구니에 추가해준다.
                newItems.add(item);
            } catch (Exception e) {
                log.info("위시리스트 -> 장바구니 : " + e.getMessage());
            }
        }

        // 사용자가 갖고 있는 카트가 없으면 만들어준다.
        if (cartRepository.findCartByUser(user).isEmpty()) {
            Cart cart = new Cart(user);
            cartRepository.save(cart);
        }

        Cart cart = cartRepository.findCartByUser(user).get();
        CartItem cartItem = null;

        // 저장할 상품이 동일한 경우 해당 상품의 수량만 업데이트 해준다.
        for (Item i : newItems) {
            cartItem = cartItemRepository.findCartItemByItem_Id(i.getId());
            if (cartItem != null) {
                int newQuantity = cartItem.getQuantity() + 1;
                cartItem.setQuantity(newQuantity);
            } else {
                cartItem = new CartItem(cart, i, 1);
            }

            cartItemRepository.save(cartItem);
        }

        return cart;
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
