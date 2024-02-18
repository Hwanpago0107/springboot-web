package me.ceskim493.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.dto.AddReviewRequest;
import me.ceskim493.springbootdeveloper.dto.ReviewViewResponse;
import me.ceskim493.springbootdeveloper.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController // http Response Body에 객체 데이터를 JSON 형식으로 변환하는 컨트롤러
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/api/reviews")
    public ResponseEntity<ReviewViewResponse> addReview(@RequestBody AddReviewRequest request) {
        ReviewViewResponse review = new ReviewViewResponse(reviewService.save(request));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(review);
    }
}
