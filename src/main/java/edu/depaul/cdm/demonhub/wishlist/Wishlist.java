package edu.depaul.cdm.demonhub.wishlist;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String id;
    private String userId;
    private List<WishlistItem> items = new ArrayList<>();

    public Wishlist() {}

    public Wishlist(String userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<WishlistItem> getItems() {
        return items;
    }

    public void setItems(List<WishlistItem> items) {
        this.items = items;
    }

    public void addItem(WishlistItem item) {
        this.items.add(item);
    }

    public void removeItem(String productId) {
        this.items.removeIf(item -> item.getProductId().equals(productId));
    }
}
