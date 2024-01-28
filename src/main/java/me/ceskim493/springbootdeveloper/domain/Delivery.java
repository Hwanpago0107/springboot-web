package me.ceskim493.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "delivery")
    private Order order;

    @Column(name="receiver")
    private String receiver;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public Delivery createDelivery(Address address, String receiver) {
        this.setAddress(address);
        this.setReceiver(receiver);
        this.setStatus(DeliveryStatus.READY);

        return this;
    }
}
