package edu.depaul.cdm.DemonHub.cart;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @Test
    void testAddItem() {
        Cart cart = new Cart();
        CartItem item = new CartItem("Test Product", 10.0, 2);
        cart.addItem(item);

        assertEquals(1, cart.getItems().size());
        assertTrue(cart.getItems().contains(item));
    }

    @Test
    void testRemoveItem() {
        Cart cart = new Cart();
        CartItem item = new CartItem("Test Product", 10.0, 2);
        cart.addItem(item);
        cart.removeItem(item);

        assertTrue(cart.getItems().isEmpty());
    }

    @Test
    void testGetTotalPrice() {
        Cart cart = new Cart();
        cart.addItem(new CartItem("Product A", 10.0, 2));
        cart.addItem(new CartItem("Product B", 5.0, 1));

        assertEquals(25.0, cart.getTotalPrice());
    }

    @Test
    void testClearCart() {
        Cart cart = new Cart();
        cart.addItem(new CartItem("Product A", 10.0, 2));
        cart.clearCart();

        assertTrue(cart.getItems().isEmpty());
    }
}