package edu.depaul.cdm.temp.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus status);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatus);

    List<Order> findByUserIdAndOrderStatusIn(Long userId, List<OrderStatus> status);
    
}
