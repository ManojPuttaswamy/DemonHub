package edu.depaul.cdm.temp.cart;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private CartService cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart();
    }

    @Test
    void testAddItemToCart() {
        CartItem newItem = new CartItem("Product A", 10.0, 1);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        Cart updatedCart = cartService.addItemToCart(1L, newItem);

        assertNotNull(updatedCart);
        assertEquals(1, updatedCart.getItems().size());
        verify(cartRepository).save(cart);
    }

    @Test
    void testRemoveItemFromCart() {
        CartItem item = new CartItem("Product A", 10.0, 1);
        item.setId(1L);
        cart.addItem(item);

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        Cart updatedCart = cartService.removeItemFromCart(1L, 1L);
        assertNotNull(updatedCart);
        assertTrue(updatedCart.getItems().isEmpty());
        verify(cartItemRepository).delete(item);
    }

    @Test
    void testGetCartTotalPrice() {
        cart.addItem(new CartItem("Product A", 10.0, 2));
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        double totalPrice = cartService.getCartTotalPrice(1L);

        assertEquals(20.0, totalPrice);
    }

    @Test
    void testClearCart() {
        cart.addItem(new CartItem("Product A", 10.0, 2));
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        cartService.clearCart(1L);

        assertTrue(cart.getItems().isEmpty());
        verify(cartRepository).save(cart);
    }
}