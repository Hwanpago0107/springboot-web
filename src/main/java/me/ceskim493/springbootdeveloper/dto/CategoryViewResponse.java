package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Category;

@NoArgsConstructor
@Getter
public class CategoryViewResponse {

    private Long id;
    private String name;
    private Category parent;
    private int count;

    public CategoryViewResponse(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.parent = category.getParent();
        this.count = category.getCount();
    }
}
