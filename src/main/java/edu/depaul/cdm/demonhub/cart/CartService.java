package edu.depaul.cdm.demonhub.cart;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartService() {
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart addItemToCart(Long cartId, CartItem newItem) {
        Cart cart = getCart(cartId);

        for (CartItem item : cart.getItems()) {
            if (item.getProductName().equals(newItem.getProductName())) {
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                item.setPrice(newItem.getPrice());  // Update price if needed
                return cartRepository.save(cart);
            }
        }

        cart.addItem(newItem);
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long cartId, Long cartItemId) {
        Cart cart = getCart(cartId);

        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));

        cart.removeItem(itemToRemove);
        cartItemRepository.delete(itemToRemove);  // Remove item from DB
        return cartRepository.save(cart);
    }

    public void clearCart(Long cartId) {
        Cart cart = getCart(cartId);
        cart.clearCart();
        cartRepository.save(cart);
    }

    public double getCartTotalPrice(Long cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalPrice();
    }
}
