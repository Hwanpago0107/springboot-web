package me.ceskim493.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ceskim493.springbootdeveloper.Exception.NotEnoughStockException;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ColumnDefault("1")
    private float discount;

    private String fileName;

    private String filePath;

    private long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder // 빌더 패턴으로 객체 생성
    public Item(Long id, String name, int price, int stockQuantity, float discount, String fileName, String filePath, Long fileSize
                    , Category category){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.discount = discount;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.category = category;
    }

    public void addStock(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void removeStock(int stockQuantity) {
        int restStock = this.stockQuantity - stockQuantity;
        if (restStock < 0 ) throw new NotEnoughStockException("need more stock");
        this.stockQuantity = restStock;
    }

    public void update(String name, int price, int stockQuantity, float discount, String fileName, String filePath, Long fileSize, Category category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.discount = discount;
        this.fileName= fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.category = category;
    }
}
