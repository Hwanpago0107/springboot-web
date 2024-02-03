package me.ceskim493.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateItemRequest {
    private String name;
    private int price;
    private int stockQuantity;
    private Float discount;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private Long category_id;
    private String description;
}
