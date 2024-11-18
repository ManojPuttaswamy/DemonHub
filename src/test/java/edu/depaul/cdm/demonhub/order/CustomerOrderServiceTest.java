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
        List<OrderRequest> orders = Arrays.asList(
                new OrderRequest(1L, "Order 1", null, 100.0, 90.0, 10.0, "Address 1", OrderStatus.PENDING, null, "User 1"),
                new OrderRequest(2L, "Order 2", null, 200.0, 180.0, 20.0, "Address 2", OrderStatus.PLACED, null, "User 1")
        );

        when(customerOrderService.getOrdersByUserId("User1")).thenReturn(orders);

        mockMvc.perform(get(BASE_URL + "/User1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].orderDescription", is("Order 1")))
                .andExpect(jsonPath("$[1].orderDescription", is("Order 2")));
    }

    @Test
    public void testGetOrderById_Success() throws Exception {
        OrderRequest order = new OrderRequest(1L, "Order 1", null, 100.0, 90.0, 10.0, "Address 1", OrderStatus.PENDING, null, "User 1");

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
        OrderRequest request = new OrderRequest(null, "New Order", null, 150.0, 140.0, 10.0, "New Address", OrderStatus.PENDING, null, "User 1");
        OrderRequest response = new OrderRequest(3L, "New Order", null, 150.0, 140.0, 10.0, "New Address", OrderStatus.PENDING, null, "User 1");

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