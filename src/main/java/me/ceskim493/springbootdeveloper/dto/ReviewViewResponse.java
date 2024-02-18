package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Review;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ReviewViewResponse {

    private String name;
    private String email;
    private String content;
    private int rating;
    private LocalDateTime createdAt;
    private int cnt5;
    private int cnt4 = 0;
    private int cnt3 = 0;
    private int cnt2 = 0;
    private int cnt1 = 0;
    private Double avg = 0.0;

    public ReviewViewResponse(Review review) {
        this.name = review.getName();
        this.email = review.getEmail();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.createdAt = review.getCreatedAt();
        this.cnt5 = review.getCnt5();
        this.cnt4 = review.getCnt4();
        this.cnt3 = review.getCnt3();
        this.cnt2 = review.getCnt2();
        this.cnt1 = review.getCnt1();
        this.avg = review.getAvg();
    }
}
