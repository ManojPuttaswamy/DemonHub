package edu.depaul.cdm.demonhub.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.depaul.cdm.demonhub.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public List<OrderRequest> getOrdersByUserId(String userId) {
        List<Order> orders = orderRepository.findByUserIdAndOrderStatusIn(userId, 
                List.of(OrderStatus.PENDING, OrderStatus.PLACED, OrderStatus.SHIPPED, OrderStatus.DELIVERED));
        return orders.stream().map(Order::getOrderRequest).collect(Collectors.toList());
    }

    public Optional<OrderRequest> getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(Order::getOrderRequest);
    }

    public OrderRequest createOrder(OrderRequest orderRequest, String userId) {
        Order order = new Order();
        order.setOrderDescription(orderRequest.getOrderDescription());
        order.setOrderDate(orderRequest.getOrderDate());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setPrice(orderRequest.getPrice());
        order.setDiscount(orderRequest.getDiscount());
        order.setAddress(orderRequest.getAddress());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTrackingId(UUID.randomUUID());
        //order.setUserId(userRepository.findById(userId).orElseThrow());

        orderRepository.save(order);
        return order.getOrderRequest();
    }

    public void cancelOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }
}