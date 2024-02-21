package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.Wish;
import me.ceskim493.springbootdeveloper.domain.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishItemRepository extends JpaRepository<WishItem, Long> {
    List<WishItem> findAllByWish(Wish wish);

    WishItem findWishItemByWishAndItem_Id(Wish wish, Long item_id);

    void deleteAllByItem_IdAndWish(Long item_id, Wish wish);

    void deleteAllByWish(Wish wish);
}
