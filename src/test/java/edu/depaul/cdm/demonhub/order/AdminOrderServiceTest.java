package edu.depaul.cdm.demonhub.order;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminOrderServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminOrderService adminOrderService;

    private static final String BASE_URL = "/api/admin/order";

    @Test
    public void testGetAllOrders() throws Exception {
        List<OrderRequest> orders = Arrays.asList(
                new OrderRequest(1L, "Order 1", null, 100.0, 90.0, 10.0, "Address 1", OrderStatus.PENDING, null, "User 1"),
                new OrderRequest(2L, "Order 2", null, 200.0, 180.0, 20.0, "Address 2", OrderStatus.PLACED, null, "User 2")
        );

        when(adminOrderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].orderDescription", is("Order 1")))
                .andExpect(jsonPath("$[1].orderDescription", is("Order 2")));
    }

    @Test
    public void testCreateOrder() throws Exception {
        OrderRequest request = new OrderRequest(null, "New Order", null, 150.0, 140.0, 10.0, "New Address", OrderStatus.PENDING, null, "User X");
        OrderRequest response = new OrderRequest(3L, "New Order", null, 150.0, 140.0, 10.0, "New Address", OrderStatus.PENDING, null, "User X");

        when(adminOrderService.createOrder(Mockito.any(OrderRequest.class))).thenReturn(response);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"orderDescription\":\"New Order\",\"totalAmount\":150,\"price\":140,\"discount\":10,\"address\":\"New Address\",\"userName\":\"User X\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.orderDescription", is("New Order")));
    }

    @Test
    public void testGetOrderById_NotFound() throws Exception {
        when(adminOrderService.getOrderById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOrder_Success() throws Exception {
        Mockito.doNothing().when(adminOrderService).deleteOrder(1L);

        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order deleted successfully"));
    }
}