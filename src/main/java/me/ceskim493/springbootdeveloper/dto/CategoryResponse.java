package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Category;

@NoArgsConstructor
@Getter
public class CategoryResponse {

    private Long category_id;
    private Long parent_id;
    private String name;

    public CategoryResponse(Category category) {

        this.category_id = category.getId();
        this.name = category.getName();
    }
}
