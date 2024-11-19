package edu.depaul.cdm.demonhub.wishlist;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    public WishlistServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWishlistByUserId() {
        String userId = "user1";
        Wishlist wishlist = new Wishlist(userId);

        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.of(wishlist));

        Wishlist result = wishlistService.getWishlistByUserId(userId);
        assertNotNull(result);
        assertEquals(userId, result.getUserId());

        verify(wishlistRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testAddItemToWishlist() {
        String userId = "user1";
        Wishlist wishlist = new Wishlist(userId);
        WishlistItem item = new WishlistItem("prod1", "Product 1", 100.0);

        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        Wishlist result = wishlistService.addItemToWishlist(userId, item);

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals("prod1", result.getItems().get(0).getProductId());

        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testRemoveItemFromWishlist() {
        String userId = "user1";
        WishlistItem item = new WishlistItem("prod1", "Product 1", 100.0);
        Wishlist wishlist = new Wishlist(userId);
        wishlist.addItem(item);

        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        Wishlist result = wishlistService.removeItemFromWishlist(userId, "prod1");

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());

        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void testClearWishlist() {
        String userId = "user1";
        WishlistItem item = new WishlistItem("prod1", "Product 1", 100.0);
        Wishlist wishlist = new Wishlist(userId);
        wishlist.addItem(item);

        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.of(wishlist));

        wishlistService.clearWishlist(userId);

        verify(wishlistRepository, times(1)).save(argThat(w -> w.getItems().isEmpty()));
    }
}
