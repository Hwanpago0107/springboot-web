package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByDiscountGreaterThanEqual(Float discount);

    @Query("select i from Item i, OrderItem o where i.id = o.item.id order by o.count limit 5")
    List<Item> findBySaleCountsLimit5();
}
