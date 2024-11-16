package edu.depaul.cdm.demonhub.cart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId) {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<Cart> addItemToCart(
            @PathVariable Long cartId,
            @RequestBody CartItem cartItem) {
        Cart updatedCart = cartService.addItemToCart(cartId, cartItem);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/remove/{cartItemId}")
    public ResponseEntity<Cart> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long cartItemId) {
        Cart updatedCart = cartService.removeItemFromCart(cartId, cartItemId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<Double> getCartTotalPrice(@PathVariable Long cartId) {
        double totalPrice = cartService.getCartTotalPrice(cartId);
        return ResponseEntity.ok(totalPrice);
    }
}