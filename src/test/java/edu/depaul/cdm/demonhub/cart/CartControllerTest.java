package edu.depaul.cdm.demonhub.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetCart() throws Exception {
        Long cartId = 1L;
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setItems(Collections.emptyList());

        Mockito.when(cartService.getCart(cartId)).thenReturn(cart);

        mockMvc.perform(get("/api/cart/{cartId}", cartId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId))
                .andExpect(jsonPath("$.items", hasSize(0)));
    }

    @Test
    void testAddItemToCart() throws Exception {
        Long cartId = 1L;
        CartItem cartItem = new CartItem("Laptop", 1500.00, 1);
        Cart updatedCart = new Cart();
        updatedCart.setId(cartId);
        updatedCart.addItem(cartItem);

        Mockito.when(cartService.addItemToCart(Mockito.eq(cartId), Mockito.any(CartItem.class)))
                .thenReturn(updatedCart);

        mockMvc.perform(post("/api/cart/{cartId}/add", cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].productName").value("Laptop"))
                .andExpect(jsonPath("$.items[0].price").value(1500.00));
    }

    @Test
    void testRemoveItemFromCart() throws Exception {
        Long cartId = 1L;
        Long cartItemId = 2L;
        Cart cart = new Cart();
        cart.setId(cartId);

        Mockito.when(cartService.removeItemFromCart(cartId, cartItemId)).thenReturn(cart);

        mockMvc.perform(delete("/api/cart/{cartId}/remove/{cartItemId}", cartId, cartItemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cartId));
    }

    @Test
    void testClearCart() throws Exception {
        Long cartId = 1L;

        mockMvc.perform(delete("/api/cart/{cartId}/clear", cartId))
                .andExpect(status().isNoContent());

        Mockito.verify(cartService, Mockito.times(1)).clearCart(cartId);
    }

    @Test
    void testGetCartTotalPrice() throws Exception {
        Long cartId = 1L;
        double totalPrice = 2000.00;

        Mockito.when(cartService.getCartTotalPrice(cartId)).thenReturn(totalPrice);

        mockMvc.perform(get("/api/cart/{cartId}/total", cartId))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(totalPrice)));
    }
}
