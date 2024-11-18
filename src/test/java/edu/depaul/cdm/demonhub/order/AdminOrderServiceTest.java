package edu.depaul.cdm.demonhub.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AdminOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private AdminOrderService adminOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderDescription("Order 1");
        order1.setTotalAmount(100.0);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderDescription("Order 2");
        order2.setTotalAmount(200.0);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<OrderRequest> orders = adminOrderService.getAllOrders();
        assertEquals(2, orders.size());
        assertEquals("Order 1", orders.get(0).getOrderDescription());
    }

    @Test
    public void testCreateOrder() {
        OrderRequest request = new OrderRequest();
        request.setOrderDescription("New Order");
        request.setTotalAmount(150.0);

        Order order = new Order();
        order.setId(3L);
        order.setOrderDescription("New Order");
        order.setTotalAmount(150.0);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderRequest createdOrder = adminOrderService.createOrder(request);
        assertNotNull(createdOrder);
        assertEquals("New Order", createdOrder.getOrderDescription());
    }
}