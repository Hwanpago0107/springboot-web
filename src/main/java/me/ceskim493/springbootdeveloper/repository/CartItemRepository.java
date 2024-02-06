package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.Cart;
import me.ceskim493.springbootdeveloper.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByCart(Cart cart);

    CartItem findCartItemByItem_Id(Long item_id);
}
