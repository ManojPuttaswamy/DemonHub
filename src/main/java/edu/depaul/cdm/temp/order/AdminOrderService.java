package edu.depaul.cdm.temp.order;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderRequest> getAllPlacedOrders() {
        List<Order> orderList = orderRepository
                .findAllByOrderStatusIn(List.of(OrderStatus.PLACED, OrderStatus.SHIPPED, OrderStatus.DELIVERED));
        return orderList.stream().map(Order::getOrderRequest).collect(Collectors.toList());
    }

    public OrderRequest changeOrderStatus(Long orderId, String status){
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            Order updatedOrder = order.get();
            if (Objects.equals(status, "SHIPPED")){
                updatedOrder.setOrderStatus(OrderStatus.SHIPPED);
            }
            else if (Objects.equals(status, "DELIVERED")){
                updatedOrder.setOrderStatus(OrderStatus.DELIVERED);
            }
            else if(Objects.equals(status, "CANCELLED")){
                updatedOrder.setOrderStatus(OrderStatus.CANCELLED);
            }

            orderRepository.save(updatedOrder);
            return updatedOrder.getOrderRequest();
        }
        return null;
    }
}
