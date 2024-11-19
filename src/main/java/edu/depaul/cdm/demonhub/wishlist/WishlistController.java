package edu.depaul.cdm.demonhub.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @GetMapping("/{userId}")
    public ResponseEntity<Wishlist> getWishlist(@PathVariable String userId) {
        Wishlist wishlist = wishlistService.getWishlistByUserId(userId);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<Wishlist> addItemToWishlist(
            @PathVariable String userId,
            @RequestBody WishlistItem item) {
        Wishlist wishlist = wishlistService.addItemToWishlist(userId, item);
        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Wishlist> removeItemFromWishlist(
            @PathVariable String userId,
            @PathVariable String productId) {
        Wishlist wishlist = wishlistService.removeItemFromWishlist(userId, productId);
        return ResponseEntity.ok(wishlist);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Void> clearWishlist(@PathVariable String userId) {
        wishlistService.clearWishlist(userId);
        return ResponseEntity.noContent().build();
    }
}
