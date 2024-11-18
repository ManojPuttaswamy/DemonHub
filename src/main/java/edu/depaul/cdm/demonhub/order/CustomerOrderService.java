package edu.depaul.cdm.demonhub.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.depaul.cdm.demonhub.user.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public List<OrderRequest> getOrdersByUserId(String userId) {
        log.info("Fetching orders for user with ID: {}", userId);
        List<Order> orders = orderRepository.findByUserIdAndOrderStatusIn(userId, 
                List.of(OrderStatus.PENDING, OrderStatus.PLACED, OrderStatus.SHIPPED, OrderStatus.DELIVERED));
        log.info("Retrieved {} orders for user with ID: {}", orders.size(), userId);
        return orders.stream().map(Order::getOrderRequest).collect(Collectors.toList());
    }

    public Optional<OrderRequest> getOrderById(Long orderId) {
        log.info("Fetching order with ID: {}", orderId);
        Optional<OrderRequest> orderRequest = orderRepository.findById(orderId).map(Order::getOrderRequest);
        if (orderRequest.isPresent()) {
            log.info("Order with ID {} found", orderId);
        } else {
            log.warn("Order with ID {} not found", orderId);
        }
        return orderRequest;
    }

    public OrderRequest createOrder(OrderRequest orderRequest, String userId) {
        log.info("Request received to create a new order for user with ID: {}", userId);
        Order order = new Order();
        order.setOrderDescription(orderRequest.getOrderDescription());
        order.setOrderDate(orderRequest.getOrderDate());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setPrice(orderRequest.getPrice());
        order.setDiscount(orderRequest.getDiscount());
        order.setAddress(orderRequest.getAddress());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTrackingId(UUID.randomUUID());
        
        // Uncomment and implement user mapping if needed
        // order.setUserId(userRepository.findById(userId).orElseThrow());

        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with ID: {} for user with ID: {}", savedOrder.getId(), userId);
        return savedOrder.getOrderRequest();
    }

    public void cancelOrder(Long orderId) {
        log.info("Request received to cancel order with ID: {}", orderId);
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setOrderStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
            log.info("Order with ID {} cancelled successfully", orderId);
        } else {
            log.error("Cancel order failed: Order with ID {} not found", orderId);
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }
}