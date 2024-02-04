package me.ceskim493.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class OrderListViewResponse {

    private Long id;

    private User user;

    private List<OrderItem> orderItems = new ArrayList<>();

    private Delivery delivery;

    private String orderNote;

    private LocalDateTime orderDate;

    private OrderStatus status;

    private int totalPrice;

    public OrderListViewResponse(Order order) {
        this.id = order.getId();
        this.user = order.getUser();
        this.orderItems = order.getOrderItems();
        this.delivery = order.getDelivery();
        this.orderNote = order.getOrderNote();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
    }
}
