package edu.depaul.cdm.demonhub.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.depaul.cdm.demonhub.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public List<OrderRequest> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(Order::getOrderRequest)
                .collect(Collectors.toList());
    }

    public Optional<OrderRequest> getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(Order::getOrderRequest);
    }

    public OrderRequest createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderDescription(orderRequest.getOrderDescription());
        order.setOrderDate(orderRequest.getOrderDate());
        order.setTotalAmount(orderRequest.getTotalAmount());
        order.setPrice(orderRequest.getPrice());
        order.setDiscount(orderRequest.getDiscount());
        order.setAddress(orderRequest.getAddress());
        order.setOrderStatus(OrderStatus.PENDING); // Default status
        order.setTrackingId(orderRequest.getTrackingId() != null ? orderRequest.getTrackingId() : UUID.randomUUID());

        orderRepository.save(order);
        return order.getOrderRequest();
    }

    public OrderRequest updateOrder(Long orderId, OrderRequest orderRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setOrderDescription(orderRequest.getOrderDescription());
            order.setOrderDate(orderRequest.getOrderDate());
            order.setTotalAmount(orderRequest.getTotalAmount());
            order.setPrice(orderRequest.getPrice());
            order.setDiscount(orderRequest.getDiscount());
            order.setAddress(orderRequest.getAddress());
            order.setOrderStatus(orderRequest.getOrderStatus());
            order.setTrackingId(orderRequest.getTrackingId());

            orderRepository.save(order);
            return order.getOrderRequest();
        }
        throw new RuntimeException("Order not found with ID: " + orderId);
    }

    public void deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }
}