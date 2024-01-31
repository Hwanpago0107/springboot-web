package me.ceskim493.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.Item;

@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자 추가
@Getter
public class AddItemRequest {

    private String name;
    private int price;
    private int stockQuantity;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private Long category_id;
    private Float discount;

    public Item toEntity(Item item) { // 생성자를 사용해 객체 생성
        return Item.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .fileName(item.getFileName())
                .filePath(item.getFilePath())
                .fileSize(item.getFileSize())
                .discount(item.getDiscount())
                .category(item.getCategory())
                .build();
    }
}
