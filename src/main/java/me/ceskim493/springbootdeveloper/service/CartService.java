package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.*;
import me.ceskim493.springbootdeveloper.dto.CreateCartRequest;
import me.ceskim493.springbootdeveloper.dto.CreateWishRequest;
import me.ceskim493.springbootdeveloper.repository.*;
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
    private final WishItemRepository wishItemRepository;

    @Transactional
    public CartItem save(CreateCartRequest request, User user) {
        // 사용자가 갖고 있는 카트가 없으면 만들어준다.
        Cart cart = cartRepository.findCartByUser(user);
        if (cart == null) {
            cart = new Cart();
            cartRepository.save(cart);
        }

        // 사용자가 담은 카트의 아이템의 개수와 실제 재고량을 체크해서 비교한다.
        Item item = itemRepository.findById(request.getItem_id())
                .orElseThrow(() -> new IllegalArgumentException("no items"));

        int quantity = request.getQuantity() == 0 ? 1 : request.getQuantity();

        if (item.getStockQuantity() < quantity) {
            // 재고가 부족합니다.
            log.info("재고가 부족합니다. {}", item.getId());
            throw new IllegalArgumentException("재고가 부족합니다.");
        }

        CartItem cartItem = null;

        // 저장할 상품이 동일한 경우 해당 상품의 수량과 옵션만 업데이트 해준다.
        cartItem = cartItemRepository.findCartItemByCartAndAndItem(cart, item);
        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(newQuantity);
            cartItem.setOptions(request.getOption_text());
        } else {
            cartItem = new CartItem(cart, item, quantity, request.getOption_text());
        }

        log.info("Cart : {}, CartItem: {}", cart, cartItem);

        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public Cart saveAll(CreateWishRequest request, User user) {
        // 사용자가 갖고 있는 카트가 없으면 만들어준다.
        Cart cart = cartRepository.findCartByUser(user);
        if (cart == null) {
            cart = new Cart();
            cartRepository.save(cart);
        }

        CartItem cartItem = null;

        List<Long> ids = request.getChecked();
        // 사용자가 담은 카트의 아이템의 개수와 실제 재고량을 체크해서 비교한다.
        for (Long id : ids) {
            try {
                WishItem wishItem = wishItemRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("no wishItem"));

                Item item = itemRepository.findById(wishItem.getItem().getId())
                        .orElseThrow(() -> new IllegalArgumentException("no item"));

                if (item.getStockQuantity() < 1) {
                    // 재고가 없습니다.
                    throw new IllegalArgumentException("재고가 없습니다.");
                }

                // 저장할 상품이 동일한 경우 해당 상품의 수량과 옵션만 업데이트 해준다.
                cartItem = cartItemRepository.findCartItemByCartAndAndItem(cart, item);
                if (cartItem != null) {
                    cartItem.update(cart, item,cartItem.getQuantity() + 1, wishItem.getOptions());
                } else {
                    cartItem = new CartItem(cart, item, 1, wishItem.getOptions());
                }

                cartItemRepository.save(cartItem);

            } catch (Exception e) {
                log.info("위시리스트 -> 장바구니 : " + e.getMessage());
            }
        }

        return cart;
    }

    @Transactional
    public List<CartItem> findAll(User user) {
        return cartRepository.findCartByUser(user) == null ? new ArrayList<>() :
                cartItemRepository.findAllByCart(cartRepository.findCartByUser(user));
    }

    @Transactional
    public void delete(User user, List<Long> checked) {
        Cart cart = cartRepository.findCartByUser(user);
        for (Long check : checked) {
            cartItemRepository.deleteAllByCartAndItemId(cart, check);
        }
    }

    public void deleteAll(User user) {
        Cart cart = cartRepository.findCartByUser(user);
        cartItemRepository.deleteAllByCart(cart);
    }
}
