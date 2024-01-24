package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Cart;
import me.ceskim493.springbootdeveloper.domain.CartItem;
import me.ceskim493.springbootdeveloper.domain.Item;

@NoArgsConstructor
@Getter
public class CartViewResponse {

    private Long id;
    private Cart cart;
    private Item item;
    private int quantity;

    public CartViewResponse(CartItem item) {
        this.id = item.getId();
        this.cart = item.getCart();
        this.item = item.getItem();
        this.quantity = item.getQuantity();
    }
}
