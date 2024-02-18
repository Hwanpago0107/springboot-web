package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.domain.Review;
import me.ceskim493.springbootdeveloper.dto.AddReviewRequest;
import me.ceskim493.springbootdeveloper.dto.Rating;
import me.ceskim493.springbootdeveloper.repository.ItemRepository;
import me.ceskim493.springbootdeveloper.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;

    public Review save(AddReviewRequest request) {

        // 리뷰를 등록할 상품이 없는 경우
        Item item = itemRepository.findById(request.getItem_id())
                .orElseThrow(() -> new IllegalArgumentException("no items"));

        return reviewRepository.save(request.toEntity(item));
    }

    public List<Review> findAllByItemId(Long item_id) {
        return reviewRepository.findAllByItem_IdOrderByCreatedAtDesc(item_id) == null ?
                new ArrayList<>() : reviewRepository.findAllByItem_IdOrderByCreatedAtDesc(item_id);
    }

    public List<Rating> findAvgRatingByItemId(Long item_id) {
        // 상품이 없는 경우
        Item item = itemRepository.findById(item_id)
                .orElseThrow(() -> new IllegalArgumentException("no items"));
        return reviewRepository.findAvgRatingByItem(item);
    }
}
