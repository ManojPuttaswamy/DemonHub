package edu.depaul.cdm.demonhub.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    public Wishlist getWishlistByUserId(String userId) {
        return wishlistRepository.findByUserId(userId)
                .orElseGet(() -> new Wishlist(userId));
    }

    public Wishlist addItemToWishlist(String userId, WishlistItem item) {
        Wishlist wishlist = getWishlistByUserId(userId);
        wishlist.addItem(item);
        return wishlistRepository.save(wishlist);
    }

    public Wishlist removeItemFromWishlist(String userId, String productId) {
        Wishlist wishlist = getWishlistByUserId(userId);
        wishlist.removeItem(productId);
        return wishlistRepository.save(wishlist);
    }

    public void clearWishlist(String userId) {
        Wishlist wishlist = getWishlistByUserId(userId);
        wishlist.setItems(new ArrayList<>());
        wishlistRepository.save(wishlist);
    }
}
