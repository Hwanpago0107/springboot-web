package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import me.ceskim493.springbootdeveloper.domain.Cart;
import me.ceskim493.springbootdeveloper.domain.CartItem;
import me.ceskim493.springbootdeveloper.domain.Item;

@Getter
public class CartResponse {

    private Long id;
    private Cart cart;
    private Item item;
    private int quantity;

    public CartResponse(CartItem cartItem) {
        this.id = cartItem.getId();
        this.cart = cartItem.getCart();
        this.item = cartItem.getItem();
        this.quantity = cartItem.getQuantity();
    }
}
