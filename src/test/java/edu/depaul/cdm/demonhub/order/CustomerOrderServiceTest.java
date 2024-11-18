package edu.depaul.cdm.demonhub.order;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerOrderServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerOrderService customerOrderService;

    private static final String BASE_URL = "/api/customer/order";

    @Test
    public void testGetOrdersByUser() throws Exception {
        OrderRequest order1 = new OrderRequest();
        order1.setId(1L);
        order1.setOrderDescription("Order 1");
        order1.setTotalAmount(100.0);
        order1.setPrice(90.0);
        order1.setDiscount(10.0);
        order1.setAddress("Address 1");
        order1.setOrderStatus(OrderStatus.PENDING);
        order1.setUserName("User 1");

        OrderRequest order2 = new OrderRequest();
        order2.setId(2L);
        order2.setOrderDescription("Order 2");
        order2.setTotalAmount(200.0);
        order2.setPrice(180.0);
        order2.setDiscount(20.0);
        order2.setAddress("Address 2");
        order2.setOrderStatus(OrderStatus.PLACED);
        order2.setUserName("User 1");

        List<OrderRequest> orders = Arrays.asList(order1, order2);

        when(customerOrderService.getOrdersByUserId("User1")).thenReturn(orders);

        mockMvc.perform(get(BASE_URL + "/User1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].orderDescription", is("Order 1")))
                .andExpect(jsonPath("$[1].orderDescription", is("Order 2")));
    }

    @Test
    public void testGetOrderById_Success() throws Exception {
        OrderRequest order = new OrderRequest();
        order.setId(1L);
        order.setOrderDescription("Order 1");
        order.setTotalAmount(100.0);
        order.setPrice(90.0);
        order.setDiscount(10.0);
        order.setAddress("Address 1");
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUserName("User 1");

        when(customerOrderService.getOrderById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get(BASE_URL + "/order/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderDescription", is("Order 1")))
                .andExpect(jsonPath("$.totalAmount", is(100.0)));
    }

    @Test
    public void testGetOrderById_NotFound() throws Exception {
        when(customerOrderService.getOrderById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + "/order/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setOrderDescription("New Order");
        request.setTotalAmount(150.0);
        request.setPrice(140.0);
        request.setDiscount(10.0);
        request.setAddress("New Address");
        request.setUserName("User 1");

        OrderRequest response = new OrderRequest();
        response.setId(3L);
        response.setOrderDescription("New Order");
        response.setTotalAmount(150.0);
        response.setPrice(140.0);
        response.setDiscount(10.0);
        response.setAddress("New Address");
        response.setOrderStatus(OrderStatus.PENDING);
        response.setUserName("User 1");

        when(customerOrderService.createOrder(Mockito.any(OrderRequest.class), Mockito.eq("User1"))).thenReturn(response);

        mockMvc.perform(post(BASE_URL + "/User1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderDescription\":\"New Order\",\"totalAmount\":150,\"price\":140,\"discount\":10,\"address\":\"New Address\",\"userName\":\"User 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.orderDescription", is("New Order")));
    }

    @Test
    public void testCancelOrder_Success() throws Exception {
        Mockito.doNothing().when(customerOrderService).cancelOrder(1L);

        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order cancelled successfully"));
    }

    @Test
    public void testCancelOrder_NotFound() throws Exception {
        Mockito.doThrow(new RuntimeException("Order not found with ID: 99")).when(customerOrderService).cancelOrder(99L);

        mockMvc.perform(delete(BASE_URL + "/99"))
                .andExpect(status().isNotFound());
    }
}