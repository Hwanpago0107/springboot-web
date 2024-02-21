package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.*;

@NoArgsConstructor
@Getter
public class WishViewResponse {

    private Long id;
    private Wish wish;
    private Item item;
    private String options;
    public WishViewResponse(WishItem item) {
        this.id = item.getId();
        this.wish = item.getWish();
        this.item = item.getItem();
        this.options = item.getOptions();
    }
}
