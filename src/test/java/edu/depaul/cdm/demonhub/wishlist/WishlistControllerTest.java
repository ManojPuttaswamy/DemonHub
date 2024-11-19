package edu.depaul.cdm.demonhub.wishlist;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WishlistService wishlistService;

    @Test
    void testGetWishlist() throws Exception {
        String userId = "user1";
        Wishlist wishlist = new Wishlist(userId);
        wishlist.setItems(Collections.emptyList());

        Mockito.when(wishlistService.getWishlistByUserId(userId)).thenReturn(wishlist);

        mockMvc.perform(get("/api/wishlist/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.items", hasSize(0)));
    }

    @Test
    void testAddItemToWishlist() throws Exception {
        String userId = "user1";
        WishlistItem item = new WishlistItem("prod1", "Product 1", 100.0);
        Wishlist wishlist = new Wishlist(userId);
        wishlist.addItem(item);

        Mockito.when(wishlistService.addItemToWishlist(Mockito.eq(userId), Mockito.any(WishlistItem.class)))
                .thenReturn(wishlist);

        mockMvc.perform(post("/api/wishlist/{userId}/add", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[0].productId").value("prod1"));
    }

    @Test
    void testRemoveItemFromWishlist() throws Exception {
        String userId = "user1";
        String productId = "prod1";
        Wishlist wishlist = new Wishlist(userId);

        Mockito.when(wishlistService.removeItemFromWishlist(userId, productId)).thenReturn(wishlist);

        mockMvc.perform(delete("/api/wishlist/{userId}/remove/{productId}", userId, productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId));
    }

    @Test
    void testClearWishlist() throws Exception {
        String userId = "user1";

        mockMvc.perform(delete("/api/wishlist/{userId}/clear", userId))
                .andExpect(status().isNoContent());

        Mockito.verify(wishlistService, Mockito.times(1)).clearWishlist(userId);
    }
}
