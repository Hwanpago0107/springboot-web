package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Category;

@NoArgsConstructor
@Getter
public class BrandResponse {

    private Long category_id;
    private String name;
    private int count;

    public BrandResponse(Category category) {
        this.category_id = category.getId();
        this.name = category.getName();
        this.count = category.getCount();
    }
}
