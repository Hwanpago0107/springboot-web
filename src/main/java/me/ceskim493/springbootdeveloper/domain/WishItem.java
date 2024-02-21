package me.ceskim493.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class WishItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wish_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Wish wish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item item;

    private String options;

    @Builder
    public WishItem(Wish wish, Item item, String options) {
        this.wish = wish;
        this.item = item;
        this.options = options;
    }

    public void update(Wish wish, Item item, String options) {
        this.wish = wish;
        this.item = item;
        this.options = options;
    }
}
