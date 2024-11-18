package edu.depaul.cdm.demonhub.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderStatus(String userId, OrderStatus status);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatus);

    List<Order> findByUserIdAndOrderStatusIn(String userId, List<OrderStatus> status);
    
}
