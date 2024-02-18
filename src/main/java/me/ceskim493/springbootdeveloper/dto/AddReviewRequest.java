package me.ceskim493.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Item;
import me.ceskim493.springbootdeveloper.domain.Review;

@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자 추가
@Getter
public class AddReviewRequest {

    private Long item_id;
    private String name;
    private String email;
    private String content;
    private int rating;

    public Review toEntity(Item item) { // 생성자를 사용해 객체 생성
        return Review.builder()
                .name(name)
                .email(content)
                .content(content)
                .rating(rating)
                .item(item)
                .build();
    }
}
