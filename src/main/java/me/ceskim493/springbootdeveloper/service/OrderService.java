package me.ceskim493.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ceskim493.springbootdeveloper.domain.*;
import me.ceskim493.springbootdeveloper.dto.CreateOrderRequest;
import me.ceskim493.springbootdeveloper.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Order save(CreateOrderRequest request, User user) {

        // 장바구니에서 주문한 상품을 검색하여 주문 상품 객체를 만든다.
        List<OrderItem> orderItems = new ArrayList<>();
        List<Long> cartItemIds = request.getCart_item_id();
        for (Long id : cartItemIds) {
            OrderItem orderItem = new OrderItem();
            CartItem cartItem = cartItemRepository.findById(id).get();
            orderItem = OrderItem.createOrderItem(cartItem.getItem()
                    , (int) (cartItem.getItem().getPrice() * (1 - cartItem.getItem().getDiscount()) * cartItem.getQuantity())
                    , cartItem.getQuantity());
            orderItems.add(orderItem);
            orderItemRepository.save(orderItem);
        }

        // 배송정보를 같이 입력해준다.
        Delivery delivery = new Delivery();
        delivery.createDelivery(request.getAddress(), request.getReceiver());

        Order order = Order.createOrder(user, delivery, orderItems, request.getOrderNote(), request.getPayment());

        return orderRepository.save(order);
    }

    @Transactional
    public List<Order> findAll(User user) {
        if (orderRepository.findAllByUser(user).isEmpty()) {
            return new ArrayList<Order>();
        }

        return orderRepository.findAllByUser(user);
    }

    public void delete(long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found " + id));

        orderRepository.delete(order);
    }
}