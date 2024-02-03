package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Category;
import me.ceskim493.springbootdeveloper.domain.Item;

@NoArgsConstructor
@Getter
public class ItemViewResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private float discount;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private Category category;
    private String description;

    public ItemViewResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.discount = item.getDiscount();
        this.fileName = item.getFileName();
        this.filePath = item.getFilePath();
        this.fileSize = item.getFileSize();
        this.category = item.getCategory();
        this.description = item.getDescription();
    }
}
