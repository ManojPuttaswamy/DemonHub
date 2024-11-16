package edu.depaul.cdm.demonhub.order;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
    
    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/placedorders")
    public ResponseEntity<List<OrderRequest>>getAllPlacedOrders(){
        return ResponseEntity.ok(adminOrderService.getAllPlacedOrders());
    }

    @GetMapping("/order/{orderId}/{orderStatus}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId, @PathVariable String orderStatus){
        OrderRequest updatedOrder = adminOrderService.changeOrderStatus(orderId, orderStatus);
        if (updatedOrder != null){
            return ResponseEntity.ok(updatedOrder);
        }
        return ResponseEntity.notFound().build();
    }
}
