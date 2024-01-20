package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Item;

@NoArgsConstructor
@Getter
public class ItemViewResponse {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String fileName;
    private String filePath;
    private Long fileSize;

    public ItemViewResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.fileName = item.getFileName();
        this.filePath = item.getFilePath();
        this.fileSize = item.getFileSize();
    }
}
