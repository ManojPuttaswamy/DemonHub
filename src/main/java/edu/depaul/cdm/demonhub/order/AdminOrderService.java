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
public class AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public List<OrderRequest> getAllOrders() {
        log.info("Fetching all orders from the database");
        List<OrderRequest> orders = orderRepository.findAll().stream()
                .map(Order::getOrderRequest)
                .collect(Collectors.toList());
        log.info("Retrieved {} orders from the database", orders.size());
        return orders;
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

    public OrderRequest createOrder(OrderRequest orderRequest) {
        log.info("Request received to create a new order");
        Order order = new Order();
        order.setOrderDescription(orderRequest.getOrderDescription());
        order.setOrderDate(orderRequest.getOrderDate());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setPrice(orderRequest.getPrice());
        order.setDiscount(orderRequest.getDiscount());
        order.setAddress(orderRequest.getAddress());
        order.setOrderStatus(OrderStatus.PENDING); // Default status
        order.setTrackingId(orderRequest.getTrackingId() != null ? orderRequest.getTrackingId() : UUID.randomUUID());

        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with ID: {}", savedOrder.getId());
        return savedOrder.getOrderRequest();
    }

    public OrderRequest updateOrder(Long orderId, OrderRequest orderRequest) {
        log.info("Request received to update order with ID: {}", orderId);
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            log.debug("Existing order details: {}", order);

            order.setOrderDescription(orderRequest.getOrderDescription());
            order.setOrderDate(orderRequest.getOrderDate());
            order.setTotalAmount(orderRequest.getTotalAmount());
            order.setPrice(orderRequest.getPrice());
            order.setDiscount(orderRequest.getDiscount());
            order.setAddress(orderRequest.getAddress());
            order.setOrderStatus(orderRequest.getOrderStatus());
            order.setTrackingId(orderRequest.getTrackingId());

            Order savedOrder = orderRepository.save(order);
            log.info("Order with ID {} updated successfully", orderId);
            return savedOrder.getOrderRequest();
        }
        log.error("Order with ID {} not found. Update failed.", orderId);
        throw new RuntimeException("Order not found with ID: " + orderId);
    }

    public void deleteOrder(Long orderId) {
        log.info("Request received to delete order with ID: {}", orderId);
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            log.info("Order with ID {} deleted successfully", orderId);
        } else {
            log.error("Order with ID {} not found. Deletion failed.", orderId);
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }
}