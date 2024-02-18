package me.ceskim493.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "rating", nullable = false)
    private int rating;

    @CreatedDate // 엔티티가 생성될 때 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Transient
    private int cnt1;
    @Transient
    private int cnt2;
    @Transient
    private int cnt3;
    @Transient
    private int cnt4;
    @Transient
    private int cnt5;
    @Transient
    private Double avg;

    @Builder
    public Review(Item item, String name, String email, String content, int rating) {
        this.item = item;
        this.name = name;
        this.email = email;
        this.content = content;
        this.rating = rating;
    }

    public Review(Double avg, int cnt5, int cnt4, int cnt3, int cnt2, int cnt1) {
        this.avg = avg;
        this.cnt5 = cnt5;
        this.cnt4 = cnt4;
        this.cnt3 = cnt3;
        this.cnt2 = cnt2;
        this.cnt1 = cnt1;
    }
}
