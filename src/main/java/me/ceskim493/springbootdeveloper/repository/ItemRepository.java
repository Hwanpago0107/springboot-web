package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.Category;
import me.ceskim493.springbootdeveloper.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByDiscountGreaterThanEqual(Float discount);

    @Query(value = "select i from Item i, " +
            "(select i.id as item_id from Item i, OrderItem o " +
            "where i.id = o.item.id " +
            "group by i.id " +
            "order by sum(o.count) desc " +
            "limit 5) a " +
            "where i.id = a.item_id")
    List<Item> findBySaleCountsLimit5();

    List<Item> findAllByCategoryAndAndNameContainsIgnoreCase(Category category, String name);

    List<Item> findAllByNameContainsIgnoreCase(String name);

    @Query(value = "select i from Item i left join OrderItem o " +
            "on i.id = o.item.id " +
            "where i.category in (:categories) " +
            "and i.name like %:searchText% " +
            "order by " +
            "case when :sortBy = 'popular' then case when :sortBy = 'popular' then o.count end end desc, " +
            "case when :sortBy = 'price' then case when :sortBy = 'price' then i.price end end asc ")
    List<Item> search(@Param("categories") Collection<Category> categories,
                      @Param("searchText") String searchText,
                      @Param("sortBy") String sortBy);
}
