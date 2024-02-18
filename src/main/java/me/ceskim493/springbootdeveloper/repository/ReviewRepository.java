package me.ceskim493.springbootdeveloper.repository;

import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.domain.Review;
import me.ceskim493.springbootdeveloper.dto.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByItem_IdOrderByCreatedAtDesc(Long item_id);

    @Query(value = "select avg(r.rating) as avg, " +
            "(select count(*) from Review rr where rr.item = r.item and rr.rating = 5) as cnt5, " +
            "(select count(*) from Review rr where rr.item = r.item and rr.rating = 4) as cnt4, " +
            "(select count(*) from Review rr where rr.item = r.item and rr.rating = 3) as cnt3, " +
            "(select count(*) from Review rr where rr.item = r.item and rr.rating = 2) as cnt2, " +
            "(select count(*) from Review rr where rr.item = r.item and rr.rating = 1) as cnt1 " +
            "from Review r " +
            "where r.item = :item " +
            "group by r.item")
    List<Rating> findAvgRatingByItem(@Param("item") Item item);
}
