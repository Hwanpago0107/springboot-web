package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.Wish;
import me.ceskim493.springbootdeveloper.domain.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishItemRepository extends JpaRepository<WishItem, Long> {
    List<WishItem> findAllByWish(Wish wish);

    List<WishItem> findWishItemByItem_Id(Long item_id);
}
