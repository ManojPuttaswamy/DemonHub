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

public class CustomerOrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private CustomerOrderService customerOrderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrdersByUserId() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderDescription("Order 1");
        order1.setTotalAmount(100.0);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderDescription("Order 2");
        order2.setTotalAmount(200.0);

        when(orderRepository.findByUserIdAndOrderStatusIn(eq("User1"), anyList()))
                .thenReturn(Arrays.asList(order1, order2));

        List<OrderRequest> orders = customerOrderService.getOrdersByUserId("User1");
        assertEquals(2, orders.size());
        assertEquals("Order 1", orders.get(0).getOrderDescription());
    }

    @Test
    public void testCancelOrder_Success() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.PENDING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        customerOrderService.cancelOrder(1L);
        verify(orderRepository, times(1)).save(order);
        assertEquals(OrderStatus.CANCELLED, order.getOrderStatus());
    }
}