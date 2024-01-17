package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import me.ceskim493.springbootdeveloper.domain.Item;

@Getter
public class ItemResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }
}
