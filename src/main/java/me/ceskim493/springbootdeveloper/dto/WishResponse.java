package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import me.ceskim493.springbootdeveloper.domain.*;

@Getter
public class WishResponse {

    private Long id;
    private Wish wish;
    private Item item;
    public WishResponse(WishItem wishItem) {
        this.id = wishItem.getId();
        this.wish = wishItem.getWish();
        this.item = wishItem.getItem();
    }
}
